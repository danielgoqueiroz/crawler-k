package parser;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.W3CDom;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;

import model.News;
import webdriver.WebDriver;

public class G1Parser {

	public enum PageFormat {

		F2021("//article", // artigo
				"//p[@class=\"content-publication-data__updated\"]", // date
				"//h1[@class=\"content-head__title\"]", // title
				"//h2[@class=\"content-head__subtitle\"]", // subtitle
				"//p[@class=\"content-publication-data__from\"]", // author
				"dd/MM/yyyy HH'h'mm" // dateFormat
		),
		F2006("//div[@class=\"entry\"]", // artigo
				"//div[@class=\"time\"]", // date
				"//div[@class=\"post\"]//h2", // title
				"//div[@class=\"post\"]//h2", // subtitle
				"//div[@class=\"auto\"]", // author
				"EEE, dd/MM/yy" // dateFormat
		),
		F2016("//div[@class=\"entry\"]", // artigo
				"//div[@class=\"time\"]", // date
				"//h1[@class=\"entry-title\"]", // title
				"//div[@class=\"post\"]//h2", // subtitle
				"//div[@class=\"auto\"]", // author
				"EEE, dd/MM/yy" // dateFormat
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
	private WebDriver driver;
	
	public G1Parser(String url) {
		driver = new WebDriver();
		this.url = url;
	}

	public News parse() throws ParseException, IOException {
		try {
			
			News news = new News();
			
			driver.navigate(url);
			
			PageFormat pageFormat = getFormat(driver);
			
			SimpleDateFormat format = new SimpleDateFormat(pageFormat.DATE_FORMAT);
			String article = driver.getText(pageFormat.XPATH_ARTICLE);
			if (article.isEmpty()) {
				System.out.println("Página sem conteúdo válido: " + url);
				return null;
			}
			
			String dateText = driver.getText(pageFormat.XPATH_DATE);
			
			news.setUrl(this.url);
			news.setContent(driver.getText(pageFormat.XPATH_ARTICLE));
			news.setTitle(driver.getText(pageFormat.XPATH_TITLE));
			news.setCaption(driver.getText(pageFormat.XPATH_SUBTITLE));
			news.setAuthor(driver.getText(pageFormat.XPATH_AUTHOR).replace("Por ", ""));
			news.setDate(format.parse(dateText));
			return news;
		} finally {
			driver.close();
		}
	}

	private PageFormat getFormat(WebDriver driver) {
		
		for (PageFormat format : PageFormat.values()) {
			String xPath = format.XPATH_TITLE;
			String value = driver.getText(xPath);
			if (!value.isEmpty()) {
				return format;
			}
		}
		return null;
	}

}
