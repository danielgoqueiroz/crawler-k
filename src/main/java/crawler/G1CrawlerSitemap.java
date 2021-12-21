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
	static String SITEMAP_URL = URL + "/sitemap/g1/sitemap.xml";

	public G1CrawlerSitemap() {
	}

	public void crawle() throws IOException {
		findUrls(SITEMAP_URL);
	}

	List<String> findUrls(String url) {
		Document documentRaw;
		try {
			documentRaw = Jsoup.connect(url).get();
			Document docXml = Jsoup.parse(documentRaw.html(), Parser.xmlParser());
			Elements locElements = docXml.getElementsByTag("loc");

			List<Element> subList = locElements.subList(0, locElements.size());
			List<String> urls = subList.stream().map(el -> el.text().toString()).collect(Collectors.toList());
			urls.forEach(u -> {
				try {
					findMoreLinks(u);
				} catch (Exception e) {
						try {
							saveError(url, e);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					e.printStackTrace();
				}
			});
			return urls;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	void findMoreLinks(String url) throws IOException, ParseException {
		if (url.endsWith(".xml")) {
			try {
				System.out.println("XML: " + url);
				findUrls(url);
				Thread.sleep(100l);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (url.endsWith("html")) {
			UUID uuid = UUID.nameUUIDFromBytes(url.getBytes());
			File file = new File("target/news/" + uuid.toString());
			if (!file.exists()) {
				News news = new G1Parser(url).parse();
				if (news != null) {
					Database database = new Database();
					database.saveLocal(news);
					try {
						System.out.println("Site: " + news.getTitle());
					} catch (Exception e) {
						e.fillInStackTrace();
					}
				}				
			}
		}
	}
	

	private void saveError(String url, Exception e) throws IOException {
		FileUtils.write(new File("target/error/" + getId(url)),
				url + "\n" + e.getMessage(), "UTF-8");
		System.out.println("Erro ao processar url: " + url + " Erro: " + e.getMessage());
	}

	private String getId(String u) {
		UUID uuid = UUID.nameUUIDFromBytes(u.getBytes());
		return uuid.toString();
	}
	
	

}
