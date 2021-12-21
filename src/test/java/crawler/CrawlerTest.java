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
	
	// Encontre todas as notícias nos sites "https://g1.globo.com/" e "https://www.infomoney.com.br/mercados/"
	
	//O programa precisa navegar em pelo menos 2 seções do site, além do link passado (ex.: https://g1.globo.com/economia/);
	
	// O site infomoney tem um botão "Carregar mais", onde é possível encontrar mais matérias. O programa precisa acessar matérias de pelo menos 2 páginas adicionais a partir desse botão (Essas páginas não são consideradas como sessões extras para o item anterior);
	
	//Para cada notícia encontrada, extrair:
	//	A URL da notícia;
	//	O título da notícia;
	//	O subtítulo da notícia;
	//	O Autor da notícia;
	//	A data de publicação da notícia (escolha um formato padrão);
	//	O conteúdo da notícia, sem tags html e sem quebras de linha.
	
	//As notícias devem ser armazenadas no AWS DynamoDB. Utilize a versão local de desenvolvimento;
	
	// O programa deve ser empacotado em um Docker. Deixe a Dockerfile na raiz do projeto com instruções para o build;
	
	//Os sites devem ser processados em paralelo;



}
