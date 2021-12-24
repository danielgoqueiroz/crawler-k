package com.danielqueiroz.parser;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.WebElement;

import com.danielqueiroz.model.News;
import com.danielqueiroz.webdriver.WebDriver;

public class G1Parser {

	public enum PageFormat {

		F2021("//article", // artigo
				"//p[@class=\"content-publication-data__updated\"]", // date
				"//h1[@class=\"content-head__title\"]", // title
				"//h2[@class=\"content-head__subtitle\"]", // subtitle
				"//p[@class=\"content-publication-data__from\"]", // author
				"dd/MM/yyyy HH'h'mm" // dateFormat
		), F2006("//div[@class=\"entry\"]", // artigo
				"//div[@class=\"time\"]", // date
				"//div[@class=\"post\"]//h2", // title
				"//div[@class=\"post\"]//h2", // subtitle
				"//div[@class=\"auto\"]", // author
				"EEE, dd/MM/yy" // dateFormat
		), F2016("//div[@id=\"materia-letra\"]", // artigo
				"//abbr[@class=\"published\"]", // date
				"//div[@class=\"materia-titulo\"]//h1", // title
				"//div[@class=\"materia-titulo\"]//h2", // subtitle
				"//span[@class=\"texto gui-color-primary\"]", // author
				"dd/MM/yyyy HH'h'mm" // dateFormat
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
	private List<String> links;

	public G1Parser(String url) {
		links = new ArrayList<String>();
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
				System.out.println("Página sem conteúdo válido: " + url);
				return null;
			}

			String dateText = webDriver.getText(pageFormat.XPATH_DATE);

			news.setUrl(this.url);
			news.setContent(webDriver.getText(pageFormat.XPATH_ARTICLE));
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
		try {
			List<WebElement> elements = webDriver.getElements("//a[@href]");
			Set<String> links = elements.stream().map(item -> item.getAttribute("href")).collect(Collectors.toSet());
			this.links.addAll(links);
			return new ArrayList<String>(links);
		} catch (Exception e) {
			return Collections.emptyList();
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
