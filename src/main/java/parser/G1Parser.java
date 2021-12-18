package parser;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import model.News;

public class G1Parser {
	
	private static final String XPATH_ARTICLE = "//article";
	private static final String XPATH_DATE = "//p[@class=\"content-publication-data__updated\"]";
	private static final String XPATH_TITLE = "//h1[@class=\"content-head__title\"]";
	private static final String XPATH_SUBTITLE = "//h2[@class=\"content-head__subtitle\"]";
	private static final String XPATH_AUTHOR = "//p[@class=\"content-publication-data__from\"]";	
	
	private String url;
	private News news;
	
	G1Parser(String url) {
		this.news = new News();
		this.url = url;
	} 
	
	public News parsePage() throws IOException, ParseException {
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH'h'mm");
		
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
		news.setAuthor(authorEl.text().replace("Por ", ""));
		news.setDate(format.parse(dateEl.text()));
		
		return this.news;
	}

}
