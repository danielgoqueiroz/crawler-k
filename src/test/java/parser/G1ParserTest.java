package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import model.News;


public class G1ParserTest {

	@Test
	void deveRealizarParserDePaginaDeNoticia() throws IOException {
		G1Parser parser = new G1Parser("https://g1.globo.com/saude/coronavirus/vacinas/noticia/2021/12/17/vacina-contra-covid-19-em-criancas-pfizer-diz-nao-ter-prazo-e-que-esta-atuando-junto-ao-governo.ghtml");
		News news  = parser.parsePage();
		assertEquals("", news.getAuthor());
		assertEquals("", news.getCaption());
		assertEquals("", news.getContent());
		assertEquals("", news.getDate());
		assertEquals("", news.getTitle());
		assertEquals("", news.getUrl());
	}
	
}
