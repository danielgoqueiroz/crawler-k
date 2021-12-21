package parser;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import model.News;

public class InfomoneyParser {

	private static final String DATE_FORMAT = "dd MMMM yyyy HH'h'mm";
	private static final String XPATH_ARTICLE = "//div[contains(@class, \"article-content\")]";
	private static final String XPATH_DATE = "//time[@datetime]";
	private static final String XPATH_TITLE = "//h1[@class=\"page-title-1\"]";
	private static final String XPATH_SUBTITLE = "//p[@class=\"article-lead\"]";
	private static final String XPATH_AUTHOR = "//span[@class=\"author-name\"]//a[text()]";	
	
	private String url;
	private News news; 
	
	InfomoneyParser(String url) {
		news = new News();
		news.setUrl(this.url);
		this.url = url;
	}
	
	public News parse() throws ParseException, IOException {
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		
		Document doc = Jsoup.connect(this.url).get();
		Element articleEl = doc.selectXpath(XPATH_ARTICLE).get(0);
		Element titleEl = doc.selectXpath(XPATH_TITLE).get(0);
		Element dateEl = doc.selectXpath(XPATH_DATE).get(0);
		Element subtitleEl = doc.selectXpath(XPATH_SUBTITLE).get(0);
		Element authorEl = doc.selectXpath(XPATH_AUTHOR).get(0);
		
		news.setUrl(this.url);
		news.setContent(articleEl.text());
		news.setTitle(titleEl.text());
		news.setCaption(subtitleEl.text());
		news.setAuthor(authorEl.text());
		news.setDate(format.parse(dateEl.text()));
		System.out.println(news.getDate().getTime());
		return this.news;
	}

	
	
}
