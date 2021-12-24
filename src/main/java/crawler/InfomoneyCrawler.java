package crawler;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import Utils.Utils;
import model.News;
import parser.G1Parser;
import parser.InfomoneyParser;

public class InfomoneyCrawler extends Crawler {
	
	public InfomoneyCrawler() {
		super("https://www.infomoney.com.br/mercados/", "post-sitemap1.xml");
	}
	
	public InfomoneyCrawler(TYPE type) {
		super("https://www.infomoney.com.br/mercados/", "post-sitemap1.xml");
		setType(type);
	}
	
	public void crawle() {
		try {
			System.out.println("Iniciando coleta do tipo: " + getType());
			if (getType() == TYPE.SITEMAP) {
				findUrls(getSitemapUrl());
			} else if (getType() == TYPE.NAVIGATION) {
				findUrls(getUrl());
			}
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			if (getDriver() != null) {
				getDriver().close();
			}
		}
	}
	
	public List<String> extractSitemapUrls(String url) throws IOException {
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
	
	List<String> findUrls(String url) {
		Set<String> urls = new HashSet<String>();
		try {
			if (url.endsWith(".xml")) {
				try {
					List<String> urlsXml = extractSitemapUrls(url);
					urls.addAll(urlsXml);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (url.contentEquals(getUrl())) {
				System.out.println("Coletando links de página root: " + url);
				processHtml(url, urls);
			} else {
				System.out.println("Coletando página: " + url);
				News news = processHtml(url, urls);
				saveResult(url, news);
			}
			return new ArrayList<String>(urls);
		} catch (Exception e) {
			e.getStackTrace();
			return Collections.emptyList();
		} finally {
			List<String> urlsOnDomain = urls.stream().filter(u -> u.contains(getUrl())).collect(Collectors.toList());
			List<String> urlsNew = urlsOnDomain.stream().filter(u-> !getDatabase().exist(Utils.getHash(u))).collect(Collectors.toList());
			
			urlsNew.forEach(u -> {
				findUrls(u);
			});
		}
	}
	
	private News processHtml(String url, Set<String> urls) throws ParseException, IOException {
		InfomoneyParser parser = new InfomoneyParser(url);
		News news = parser.parse();
		List<String> links = parser.getLinks();
		urls.addAll(links);
		System.out.println("Encontrados " + links.size() + " links adicionais");
		return news;
	}
}
