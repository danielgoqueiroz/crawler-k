package com.danielqueiroz.app.parser;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danielqueiroz.app.model.News;
import com.danielqueiroz.app.webdriver.WebDriver;

public class InfomoneyParser {

	private static Logger logger = LoggerFactory.getLogger(InfomoneyParser .class);
	
	public enum PageFormat {

		F2021("//div[contains(@class, \"article-content\")]", //
				"//time[@datetime]", //
				"//h1[@class=\"page-title-1\"]", //
				"//p[@class=\"article-lead\"]", //
				"//span[@class=\"author-name\"]//a[text()]", //
				"dd MMMM yyyy HH'h'mm" // dateFormat
		);

		public String DATE_FORMAT;
		public String XPATH_ARTICLE;
		public String XPATH_DATE;
		public String XPATH_TITLE;
		public String XPATH_SUBTITLE;
		public String XPATH_AUTHOR;

		PageFormat(String article, String date, String title, String subtitle, String author, String dateFormat) {
			this.DATE_FORMAT = dateFormat;
			this.XPATH_ARTICLE = article;
			this.XPATH_DATE = date;
			this.XPATH_TITLE = title;
			this.XPATH_SUBTITLE = subtitle;
			this.XPATH_AUTHOR = author;
		}

	}

	private String url;
	private News news;
	private List<String> links;

	public InfomoneyParser(String url) {
		this.links = new ArrayList<String>();
		news = new News();
		news.setUrl(url);
		this.url = url;
	}

	public News parse() throws ParseException, IOException {

		WebDriver webDriver = new WebDriver();

		try {
			News news = new News();
			webDriver.navigate(url);
			extractLinks(webDriver);

			PageFormat pageFormat = getFormat(webDriver);
			if (pageFormat == null) {
				return null;
			}

			SimpleDateFormat format = new SimpleDateFormat(pageFormat.DATE_FORMAT);
			String article = webDriver.getText(pageFormat.XPATH_ARTICLE);
			if (article.isEmpty()) {
				logger.info("Página sem conteúdo válido: " + url);
				return null;
			}

			String dateText = webDriver.getText(pageFormat.XPATH_DATE);

			news.setUrl(this.url);
			news.setContent(webDriver.getText(pageFormat.XPATH_ARTICLE).replaceAll("\\n", " ").replaceAll("\\r", ""));
			news.setTitle(webDriver.getText(pageFormat.XPATH_TITLE));
			news.setCaption(webDriver.getText(pageFormat.XPATH_SUBTITLE));
			news.setAuthor(webDriver.getText(pageFormat.XPATH_AUTHOR).replace("Por ", ""));
			news.setDate(format.parse(dateText));
			return news;
		} finally {
			webDriver.quit();
		}
	}
	
	private List<String> extractLinks(WebDriver webDriver) {
		scroolDownAndClickOnSeeMore(webDriver);
		try {
			List<WebElement> elements = webDriver.getElements("//a[@href]");
			Set<String> links = elements.stream().map(item -> item.getAttribute("href")).collect(Collectors.toSet());
			this.links.addAll(links);
			return new ArrayList<String>(links);
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	private void scroolDownAndClickOnSeeMore(WebDriver webDriver) {
		if (webDriver.getElements("//div[@id=\"infinite-handle\"]").isEmpty()) {
			return;
		}
		logger.info("Descendo na página.");
		webDriver.scroolToToElementId("infinite-handle");
		int linksBefore= webDriver.getElements("//div[@class=\"row py-3 item\"]").size();
		int linksAfter = webDriver.getElements("//div[@class=\"row py-3 item\"]").size();
		logger.info("Executando ação 'veja mais'");
		webDriver.executeScript("infiniteScroll.scroller.refresh()", 5);
		while (linksAfter == linksBefore) {
			logger.info("Esperando carregar itens");
			linksAfter = webDriver.getElements("//div[@class=\"row py-3 item\"]").size();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				System.err.println("Algo deu errado enquanto esperava.");
				e.printStackTrace();
			}
		}
	}

	private PageFormat getFormat(WebDriver driver) {

		for (PageFormat format : PageFormat.values()) {
			String xPath = format.XPATH_TITLE;
			try {
				String value = driver.getText(xPath);
				if (!value.isEmpty()) {
					return format;
				}
			} catch (Exception e) {
			}
		}
		return null;
	}

	public List<String> getLinks() {
		return links;
	}

}
