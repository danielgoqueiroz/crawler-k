package parser;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
		this.url = url;
	} 
	
	public News parsePage() throws IOException {
		Document doc = Jsoup.connect(this.url).get();
		Element articleEl = doc.selectXpath(XPATH_ARTICLE).get(0);
		
		String content = articleEl.text();
	
		news.setContent(content);
		return this.news;
	}

}
