package crawler;

import static crawler.G1Crawler.SITEMAP_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Utils.TestUtils;
import crawler.G1Crawler.TYPE;
import database.Database;

public class G1CrawlerSitemapTest {

	private Database database;

	@BeforeEach
	void before() {
		TestUtils.DynamoServer();
		database = new Database();
		database.reset("news");
	}

	// Encontre todas as notícias nos sites "https://g1.globo.com/" e
	// "https://www.infomoney.com.br/mercados/"

	// O programa precisa navegar em pelo menos 2 seções do site, além do link
	// passado (ex.: https://g1.globo.com/economia/);

	// O site infomoney tem um botão "Carregar mais", onde é possível encontrar mais
	// matérias. O programa precisa acessar matérias de pelo menos 2 páginas
	// adicionais a partir desse botão (Essas páginas não são consideradas como
	// sessões extras para o item anterior);

	// Para cada notícia encontrada, extrair:
	// A URL da notícia;
	// O título da notícia;
	// O subtítulo da notícia;
	// O Autor da notícia;
	// A data de publicação da notícia (escolha um formato padrão);
	// O conteúdo da notícia, sem tags html e sem quebras de linha.

	// As notícias devem ser armazenadas no AWS DynamoDB. Utilize a versão local de
	// desenvolvimento;

	// O programa deve ser empacotado em um Docker. Deixe a Dockerfile na raiz do
	// projeto com instruções para o build;

	// Os sites devem ser processados em paralelo;
	
	@Test
	void deveNavegarNosLinksEncontradosPorSitePrioncipal() throws IOException {
		G1Crawler crawler = new G1Crawler(TYPE.NAVIGATION);
		crawler.crawle();
	}
	
	@Test
	void deveNavegarNosLinksEncontrados() throws IOException {
		G1Crawler crawler = new G1Crawler(TYPE.SITEMAP);
		crawler.setSITEMAP_URL("http://pox.globo.com/sitemap/g1/2006/09/11_1.xml");
		crawler.crawle();

		int sizeExtual = database.getAll().size();
		assertEquals(10, sizeExtual);

	}

	@Test
	void deveExtrairLinksXmlDeSitemapLink() throws IOException {
		G1Crawler crawler = new G1Crawler(TYPE.SITEMAP);
		List<String> xmls = crawler.findUrls(SITEMAP_URL);

		assertTrue(xmls.size() > 0);

		String url = xmls.get(0);
		assertNotNull(url);
		assertTrue(url.startsWith("http://pox.globo.com/sitemap/g1/"));
		assertTrue(url.endsWith(".xml"));
	}

	@Test
	void deveExtrairLinksHtmlDeSitemapLink() throws IOException {
		G1Crawler crawler = new G1Crawler(TYPE.SITEMAP);
		List<String> urls = crawler.findUrls("http://pox.globo.com/sitemap/g1/2021/12/20_1.xml");

		assertTrue(urls.size() > 0);

		String url = urls.get(0);
		assertNotNull(url);
		assertTrue(url.startsWith("https://g1.globo.com/"));
		assertTrue(url.endsWith(".ghtml"));

	}

	@Test
	void deveExtrairLinksDeCategorias() {
		G1Crawler crawler = new G1Crawler(TYPE.SITEMAP);
		List<String> urls = crawler.extractSubcategories();

		assertTrue(urls.size() > 0);

		String url = urls.get(0);
		assertNotNull(url);
		assertTrue(url.startsWith("https://g1.globo.com/"));
		assertTrue(url.endsWith(".ghtml"));

	}

	@Test
	void debug() throws IOException {
		G1Crawler crawler = new G1Crawler(TYPE.SITEMAP);
		List<String> urls = crawler.findUrls("http://pox.globo.com/sitemap/g1/2021/12/07_1.xml");

		assertTrue(urls.size() > 0);

		String url = urls.get(0);
		assertNotNull(url);
		assertTrue(url.startsWith("https://g1.globo.com/"));
		assertTrue(url.endsWith(".ghtml"));

	}

}
