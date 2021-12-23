package crawler;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.print.attribute.HashAttributeSet;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import database.Database;
import model.News;
import parser.G1Parser;
import webdriver.WebDriver;

public class G1Crawler {

	enum TYPE {
		SITEMAP, NAVIGATION;
	}

	private static String URL = "https://g1.globo.com";
	private static Database database;
	private WebDriver driver;

	static String SITEMAP_URL = URL + "/sitemap/g1/sitemap.xml";
	private static String XPATH_CATEGORIES = "//nav//a[contains(href, menu-item)]";
	private TYPE type;

	public G1Crawler() {
		new G1Crawler(TYPE.SITEMAP);
	}

	public G1Crawler(TYPE type) {
		database = new Database();
		this.type = type;
	}

	private WebDriver getDriverInstance() {
		if (driver == null) {
			driver = new WebDriver();
		}
		return driver;
	}

	public void crawle() {
		try {
			System.out.println("Iniciando coleta do tipo: " + type);
			if (type == TYPE.SITEMAP) {
				findUrls(SITEMAP_URL);
			} else if (type == TYPE.NAVIGATION) {
				findUrls(URL);
			} 
		}
		catch (Exception e) {
			e.getStackTrace();
		}

	}

	List<String> findUrls(String url) {
		WebDriver driver = getDriverInstance();
		driver.get(url);
		Set<String> urls = new HashSet<String>();
		try {

			System.out.println("Navegando até " + url);
			System.out.println("Tipo de navegação " + type);

			if (url.contentEquals(URL)) {
				List<String> extractSubcategories = extractSubcategories();
				System.out.println("Encontrados " + extractSubcategories.size() + " links na página " + url);
				urls.addAll(extractSubcategories);
			} else if (url.endsWith(".xml")) {
				try {
					List<String> urlsXml = extractSitemapUrls(url);
					urlsXml.forEach(u -> {
						if (u.endsWith(".xml")) {
							System.out.println("XML: " + u);
							findUrls(u);
						} else {
							urls.add(u);
							List<String> linksFromHrml = extractHtml(u);
							urls.addAll(linksFromHrml);
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			return new ArrayList<String>(urls);
		} finally {
			urls.forEach(u -> {
				findUrls(u);
			});
		}
	}

	List<String> extractSubcategories() {
		List<WebElement> elements = getDriverInstance().getElements(XPATH_CATEGORIES);
		List<String> categories = elements.stream().map(item -> item.getAttribute("href")).filter(item -> item != null)
				.collect(Collectors.toList());
		return categories;
	}

	private List<String> extractHtml(String url) {
		if (!url.endsWith("xml")) {
			News news;
			try {
				G1Parser parser = new G1Parser(url);
				news = parser.parse();
				if (news != null) {
					try {
						database.save(news);
						System.out.println("Site salvo: " + news.getTitle());
					} catch (Exception e) {
						e.fillInStackTrace();
					}
				}
				return parser.extractLinks();
			} catch (ParseException | IOException e1) {
				e1.printStackTrace();
			}
		}
		return Collections.emptyList();
	}

	private List<String> extractSitemapUrls(String url) throws IOException {
		System.out.println("Extraindo links de sitemap: " + url);
		Document documentRaw;
		documentRaw = Jsoup.connect(url).get();
		Document docXml = Jsoup.parse(documentRaw.html(), Parser.xmlParser());
		Elements locElements = docXml.getElementsByTag("loc");

		List<Element> subList = locElements.subList(0, locElements.size());
		List<String> urls = subList.stream().map(el -> el.text().toString()).collect(Collectors.toList());
		System.out.println("Encontrados: " + urls.size() + " itens.");
		return urls;
	}

	private void saveError(String url, Exception e) throws IOException {
		FileUtils.write(new File("target/error/" + getId(url)), url + "\n" + e.getMessage(), "UTF-8");
		System.out.println("Erro ao processar url: " + url + " Erro: " + e.getMessage());
	}

	private String getId(String u) {
		UUID uuid = UUID.nameUUIDFromBytes(u.getBytes());
		return uuid.toString();
	}

	public static String getSITEMAP_URL() {
		return SITEMAP_URL;
	}

	static void setSITEMAP_URL(String url) {
		SITEMAP_URL = url;
	}

}
