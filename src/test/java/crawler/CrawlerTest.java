package crawler;

import static crawler.G1CrawlerSitemap.SITEMAP_URL;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CrawlerTest {

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
	
	// Encontre todas as not�cias nos sites "https://g1.globo.com/" e "https://www.infomoney.com.br/mercados/"
	
	//O programa precisa navegar em pelo menos 2 se��es do site, al�m do link passado (ex.: https://g1.globo.com/economia/);
	
	// O site infomoney tem um bot�o "Carregar mais", onde � poss�vel encontrar mais mat�rias. O programa precisa acessar mat�rias de pelo menos 2 p�ginas adicionais a partir desse bot�o (Essas p�ginas n�o s�o consideradas como sess�es extras para o item anterior);
	
	//Para cada not�cia encontrada, extrair:
	//	A URL da not�cia;
	//	O t�tulo da not�cia;
	//	O subt�tulo da not�cia;
	//	O Autor da not�cia;
	//	A data de publica��o da not�cia (escolha um formato padr�o);
	//	O conte�do da not�cia, sem tags html e sem quebras de linha.
	
	//As not�cias devem ser armazenadas no AWS DynamoDB. Utilize a vers�o local de desenvolvimento;
	
	// O programa deve ser empacotado em um Docker. Deixe a Dockerfile na raiz do projeto com instru��es para o build;
	
	//Os sites devem ser processados em paralelo;



}
