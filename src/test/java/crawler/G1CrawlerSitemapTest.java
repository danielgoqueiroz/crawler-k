package crawler;

import static crawler.G1CrawlerSitemap.SITEMAP_URL;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Queue;

import org.junit.jupiter.api.Test;

public class G1CrawlerSitemapTest {

	@Test
	void deveNavegarNosLinksEncontrados() throws IOException {
		G1CrawlerSitemap crawler = new G1CrawlerSitemap();
		crawler.crawle();
		
		File newsFolder = new File("target/news/");
		if (!newsFolder.exists()) {
			newsFolder.mkdirs();
		} else {
			newsFolder.delete();
			newsFolder.mkdirs();
		}
		
		File[] newsFiles = newsFolder.listFiles();
		
		assertTrue(newsFiles.length > 0);
		
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
