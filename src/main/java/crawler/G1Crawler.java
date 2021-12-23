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

import Utils.Utils;
import database.Database;
import model.News;
import model.NewsUnknow;
import parser.G1Parser;
import webdriver.WebDriver;

public class G1Crawler extends Crawler {

	public G1Crawler() {
		super("https://g1.globo.com", "/sitemap/g1/sitemap.xml");
	}

	public G1Crawler(TYPE type) {
		super("https://g1.globo.com", "/sitemap/g1/sitemap.xml");
	}

	WebDriver getDriverInstance() {
		if (getDriver()== null) {
			setDriver(new WebDriver());
		}
		return getDriver();
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
			getDriver().close();
			getDriver().quit();
		}
		
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
			List<String> urlsFiltred = urls.stream().filter(u-> !getDatabase().exist(Utils.getHash(u))).collect(Collectors.toList());
			urlsFiltred.forEach(u -> {
				findUrls(u);
			});
		}
	}

	private News processHtml(String url, Set<String> urls) throws ParseException, IOException {
		G1Parser parser = new G1Parser(url);
		News news = parser.parse();
		List<String> links = parser.getLinks();
		urls.addAll(links);
		System.out.println("Encontrados " + links.size() + " links adicionais");
		return news;
	}

	private void saveResult(String url, News news) {
		if (news != null) {
			System.out.println("Salvando página");
			getDatabase().save(news);
		} else {
			NewsUnknow unknoew = new NewsUnknow(url);
			System.out.println("Não encontrou conteúdo. Salvando url como visitada para ser ignorada ou ser revisitada.");
			getDatabase().save(unknoew);
		}
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

}
