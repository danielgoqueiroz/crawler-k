package com.danielqueiroz.app.crawler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.danielqueiroz.app.utils.TestUtils;
import com.danielqueiroz.crawler.Crawler;
import com.danielqueiroz.crawler.InfomoneyCrawler;
import com.danielqueiroz.database.Database;
import com.danielqueiroz.webdriver.WebDriver;

public class InfoMoneyCrawlerTest {

	private Database database;

	@BeforeEach
	void before() {
		new WebDriver().quit();
		TestUtils.DynamoServer();
		database = new Database();
		database.reset("news");
	}

	@Test
	void deveNavegarNosLinksEncontradosPorSitePrioncipalPor30SegundosESalvarResultados() throws IOException {
		long start = System.currentTimeMillis();
		long end = start + 10*1000;
		while (System.currentTimeMillis() < end) {
			InfomoneyCrawler crawler = new InfomoneyCrawler(Crawler.TYPE.NAVIGATION);
			crawler.crawle();
			crawler.getDriverInstance().quit();
			int size = database.getAll().size();
			assertTrue(size > 3);
		}
	}
	
	@Test
	void deveNavegarNosLinksEncontrados() throws IOException {
		InfomoneyCrawler crawler = new InfomoneyCrawler(Crawler.TYPE.SITEMAP);
		crawler.setSitemapUrl("https://www.infomoney.com.br/edicao-sitemap.xml");
		crawler.crawle();
		crawler.getDriverInstance().quit();

		int sizeExtual = database.getAll().size();
		assertEquals(10, sizeExtual);

	}

	@Test
	void deveExtrairLinksXmlDeSitemapLink() throws IOException {
		InfomoneyCrawler crawler = new InfomoneyCrawler(Crawler.TYPE.SITEMAP);
		List<String> xmls = crawler.findUrls(crawler.getSitemapUrl());
		crawler.getDriverInstance().quit();
		assertTrue(xmls.size() > 0);

		String url = xmls.get(0);
		assertNotNull(url);
		assertTrue(url.startsWith("https://www.infomoney.com.br/"));
		assertTrue(url.endsWith(".xml"));
	}

	@Test
	void deveExtrairLinksHtmlDeSitemapLink() throws IOException {
		InfomoneyCrawler crawler = new InfomoneyCrawler(Crawler.TYPE.SITEMAP);
		List<String> urls = crawler.findUrls("https://www.infomoney.com.br/post-sitemap.xml");
		crawler.getDriverInstance().quit();
		assertTrue(urls.size() > 0);

		String url = urls.get(0);
		assertNotNull(url);
		assertTrue(url.startsWith("https://www.infomoney.com.br/"));
		assertTrue(url.endsWith("/"));

	}

	@Test
	@Ignore("Debug")
	void debug() throws IOException {
		InfomoneyCrawler crawler = new InfomoneyCrawler(Crawler.TYPE.SITEMAP);
		List<String> urls = crawler.findUrls("http://pox.globo.com/sitemap/g1/2021/12/07_1.xml");
		crawler.getDriverInstance().quit();
		
		assertTrue(urls.size() > 0);

		String url = urls.get(0);
		assertNotNull(url);
		assertTrue(url.startsWith("https://g1.globo.com/"));
		assertTrue(url.endsWith(".ghtml"));

	}

}
