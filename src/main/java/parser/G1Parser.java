package parser;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.News;

public class G1Parser {
	
	private static final String DATE_FORMAT = "dd/MM/yyyy HH'h'mm";
	private static final String XPATH_ARTICLE = "//article";
	private static final String XPATH_DATE = "//p[@class=\"content-publication-data__updated\"]";
	private static final String XPATH_TITLE = "//h1[@class=\"content-head__title\"]";
	private static final String XPATH_SUBTITLE = "//h2[@class=\"content-head__subtitle\"]";
	private static final String XPATH_AUTHOR = "//p[@class=\"content-publication-data__from\"]";	
	
	private String url;
	
	public G1Parser(String url) {
		this.url = url;
	} 
	
	public News parse() throws ParseException, IOException {
		News news = new News();

		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		
		Document doc = Jsoup.connect(this.url).get();
		
		Elements articleEls = doc.selectXpath(XPATH_ARTICLE);
		if (articleEls.isEmpty()) {
			System.out.println("Página sem conteúdo válido: " + url);
			return null;
		}
		
		String dateText = getValue(doc, XPATH_DATE);
		
		news.setUrl(this.url);
		news.setContent(getValue(doc, XPATH_ARTICLE));
		news.setTitle(getValue(doc, XPATH_TITLE));
		news.setCaption(getValue(doc, XPATH_SUBTITLE));
		news.setAuthor(getValue(doc, XPATH_AUTHOR).replace("Por ", ""));
		news.setDate(format.parse(dateText));
		
		return news;
	}

	private String getValue(Document doc, String xpath) {
		Elements selectXpath = doc.selectXpath(xpath);
		if (selectXpath.isEmpty()) {
			return "";
		}
		Element element = selectXpath.get(0);
		if (element == null) {
			return "";
		}
		return element.text();
	}
	
	

}
