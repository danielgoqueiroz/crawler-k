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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danielqueiroz.app.model.News;
import com.danielqueiroz.app.parser.InfomoneyParser;
import com.danielqueiroz.app.utils.Utils;

public class InfomoneyCrawler extends Crawler {
	
	private static Logger logger = LoggerFactory.getLogger(InfomoneyCrawler.class);

	public InfomoneyCrawler() {
		super("https://www.infomoney.com.br/mercados/", "post-sitemap1.xml");
	}
	
	public InfomoneyCrawler(TYPE type) {
		super("https://www.infomoney.com.br/mercados/", "post-sitemap1.xml");
		setType(type);
	}
	
	public void crawle() {
		try {
			logger.info("Iniciando coleta do tipo: " + getType());
			if (getType() == TYPE.SITEMAP) {
				findUrls(getSitemapUrl());
			} else if (getType() == TYPE.NAVIGATION) {
				findUrls(getUrl());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (getDriver() != null) {
				getDriver().close();
			}
		}
	}
	
	public List<String> extractSitemapUrls(String url) throws IOException {
		logger.info("Extraindo links de sitemap: " + url);
		Document documentRaw;
		documentRaw = Jsoup.connect(url).get();
		Document docXml = Jsoup.parse(documentRaw.html(), Parser.xmlParser());
		Elements locElements = docXml.getElementsByTag("loc");

		List<Element> subList = locElements.subList(0, locElements.size());
		List<String> urls = subList.stream().map(el -> el.text().toString()).collect(Collectors.toList());
		logger.info("Encontrados: " + urls.size() + " itens.");
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
					e.printStackTrace();
				}
			} else if (url.contentEquals(getUrl())) {
				logger.info("Coletando links de p�gina root: " + url);
				processHtml(url, urls);
			} else {
				logger.info("Coletando p�gina: " + url);
				News news = processHtml(url, urls);
				saveResult(url, news);
			}
			return new ArrayList<String>(urls);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return Collections.emptyList();
		} finally {
			logger.info("Pr�ximo link");
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
		logger.info("Encontrados " + links.size() + " links adicionais");
		return news;
	}
}
