package crawler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;

import Utils.TestUtils;
import database.Database;
import webdriver.WebDriver;

public class G1CrawlerTest {

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
			G1Crawler crawler = new G1Crawler(Crawler.TYPE.NAVIGATION);
			crawler.crawle();
			crawler.getDriverInstance().quit();
			int size = database.getAll().size();
			assertTrue(size > 3);
		}
	}
	
	@Test
	void deveNavegarNosLinksEncontrados() throws IOException {
		G1Crawler crawler = new G1Crawler(Crawler.TYPE.SITEMAP);
		crawler.setSitemapUrl("http://pox.globo.com/sitemap/g1/2006/09/11_1.xml");
		crawler.crawle();
		crawler.getDriverInstance().quit();

		int sizeExtual = database.getAll().size();
		assertEquals(10, sizeExtual);

	}

	@Test
	void deveExtrairLinksXmlDeSitemapLink() throws IOException {
		G1Crawler crawler = new G1Crawler(Crawler.TYPE.SITEMAP);
		List<String> xmls = crawler.findUrls(crawler.getSitemapUrl());
		crawler.getDriverInstance().quit();
		assertTrue(xmls.size() > 0);

		String url = xmls.get(0);
		assertNotNull(url);
		assertTrue(url.startsWith("http://pox.globo.com/sitemap/g1/"));
		assertTrue(url.endsWith(".xml"));
	}

	@Test
	void deveExtrairLinksHtmlDeSitemapLink() throws IOException {
		G1Crawler crawler = new G1Crawler(Crawler.TYPE.SITEMAP);
		List<String> urls = crawler.findUrls("http://pox.globo.com/sitemap/g1/2021/12/20_1.xml");
		crawler.getDriverInstance().quit();
		assertTrue(urls.size() > 0);

		String url = urls.get(0);
		assertNotNull(url);
		assertTrue(url.startsWith("https://g1.globo.com/"));
		assertTrue(url.endsWith(".ghtml"));

	}

	@Test
	@Ignore("Debug")
	void debug() throws IOException {
		G1Crawler crawler = new G1Crawler(Crawler.TYPE.SITEMAP);
		List<String> urls = crawler.findUrls("http://pox.globo.com/sitemap/g1/2021/12/07_1.xml");
		crawler.getDriverInstance().quit();
		
		assertTrue(urls.size() > 0);

		String url = urls.get(0);
		assertNotNull(url);
		assertTrue(url.startsWith("https://g1.globo.com/"));
		assertTrue(url.endsWith(".ghtml"));

	}

}
