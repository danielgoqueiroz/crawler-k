package com.danielqueiroz.app.crawler;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import com.danielqueiroz.app.model.News;
import com.danielqueiroz.app.parser.G1Parser;
import com.danielqueiroz.app.utils.Utils;

public class G1Crawler extends Crawler {

	public G1Crawler() {
		super("https://g1.globo.com", "/sitemap/g1/sitemap.xml");
	}

	public G1Crawler(TYPE type) {
		super("https://g1.globo.com", "/sitemap/g1/sitemap.xml");
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
			System.out.println(e.getMessage());
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

	public List<String> findUrls(String url) {
		Set<String> urls = new HashSet<String>();
		try {
			if (url.endsWith(".xml")) {
				try {
					List<String> urlsXml = extractSitemapUrls(url);
					urls.addAll(urlsXml);
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			} else if (url.contentEquals(getUrl())) {
				System.out.println("Coletando links de p?gina root: " + url);
				processHtml(url, urls);
			} else {
				System.out.println("Coletando p?gina: " + url);
				News news = processHtml(url, urls);
				saveResult(url, news);
			}
			return new ArrayList<String>(urls);
		} catch (Exception e) {
			e.getStackTrace();
			return Collections.emptyList();
		} finally {
			List<String> urlsFiltred = urls.stream().filter(u -> !getDatabase().exist(Utils.getHash(u)))
					.collect(Collectors.toList());
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

}
