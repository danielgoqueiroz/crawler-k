package crawler;

import static crawler.G1CrawlerSitemap.SITEMAP_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Utils.TestUtils;
import database.Database;

public class G1CrawlerSitemapTest {

	private Database database;
	
	@BeforeEach
	void before() {
		TestUtils.DynamoServer();
		database = new Database();
		database.reset("news");
	}
	
	@Test
	void deveNavegarNosLinksEncontrados() throws IOException {
		
		G1CrawlerSitemap crawler = new G1CrawlerSitemap();
		crawler.setSITEMAP_URL("http://pox.globo.com/sitemap/g1/2006/09/25_1.xml");
		crawler.crawle();
		
		int sizeExtual = database.getAll().size();
		assertEquals(10, sizeExtual);
		
	}
	
	@Test
	void deveExtrairLinksXmlDeSitemapLink() throws IOException {
		G1CrawlerSitemap crawler = new G1CrawlerSitemap();
		List<String> xmls = crawler.findUrls(SITEMAP_URL);
		
		assertTrue(xmls.size() > 0);
		
		String url = xmls.get(0);
		assertNotNull(url);
		assertTrue(url.startsWith("http://pox.globo.com/sitemap/g1/"));
		assertTrue(url.endsWith(".xml"));
	}
	
	@Test
	void deveExtrairLinksHtmlDeSitemapLink() throws IOException {
		G1CrawlerSitemap crawler = new G1CrawlerSitemap();
		List<String> urls = crawler.findUrls("http://pox.globo.com/sitemap/g1/2021/12/20_1.xml");
		
		assertTrue(urls.size() > 0);
		
		String url = urls.get(0);
		assertNotNull(url);
		assertTrue(url.startsWith("https://g1.globo.com/"));
		assertTrue(url.endsWith(".ghtml"));
		
	}
	
	@Test
	void debug() throws IOException {
		G1CrawlerSitemap crawler = new G1CrawlerSitemap();
		List<String> urls = crawler.findUrls("http://pox.globo.com/sitemap/g1/2021/12/07_1.xml");
		
		assertTrue(urls.size() > 0);
		
		String url = urls.get(0);
		assertNotNull(url);
		assertTrue(url.startsWith("https://g1.globo.com/"));
		assertTrue(url.endsWith(".ghtml"));
		
	}
	
}
