package crawler;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import database.Database;
import model.News;
import parser.G1Parser;

public class G1CrawlerSitemap {

	private static String URL = "https://g1.globo.com";
	private static Database database; 
	static String SITEMAP_URL = URL + "/sitemap/g1/sitemap.xml";
	
	public G1CrawlerSitemap() {
		database = new Database();
	}

	public void crawle() throws IOException {
		findUrls(SITEMAP_URL);
	}

	List<String> findUrls(String url) {
		try {
			List<String> urls = extractUrl(url);
			urls.forEach(u -> {
				if (u.endsWith(".xml")) {
					System.out.println("XML: " + url);
					findUrls(url);
				} else {
					extractHtml(u);
				}
			});
			return urls;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void extractHtml(String url) {
		if (!url.endsWith("xml")) {
			News news;
			try {
				news = new G1Parser(url).parse();
				if (news != null) {
					try {
						database.save(news);
						System.out.println("Site salvo: " + news.getTitle());
					} catch (Exception e) {
						e.fillInStackTrace();
					}
				}
			} catch (ParseException | IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private List<String> extractUrl(String url) throws IOException {
		Document documentRaw;
		documentRaw = Jsoup.connect(url).get();
		Document docXml = Jsoup.parse(documentRaw.html(), Parser.xmlParser());
		Elements locElements = docXml.getElementsByTag("loc");

		List<Element> subList = locElements.subList(0, locElements.size());
		List<String> urls = subList.stream().map(el -> el.text().toString()).collect(Collectors.toList());
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
