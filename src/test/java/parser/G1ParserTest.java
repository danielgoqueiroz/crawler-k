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
		assertEquals("Vacina contra Covid-19 em crian�as: Pfizer diz n�o ter prazo de entrega e que est� atuando junto ao governo", news.getTitle());
		assertEquals("A Anvisa autorizou a aplica��o da vacina da Pfizer contra Covid-19 em crian�as de 5 a 11 anos na quinta-feira (16)", news.getCaption());
		assertEquals("1 de 2 Frascos da vacina da Pfizer em vers�o pedi�trica (laranja) e a partir dos 12 anos (roxa) � Foto: Tobias Schwarz/AFP Frascos da vacina da Pfizer em vers�o pedi�trica (laranja) e a partir dos 12 anos (roxa) � Foto: Tobias Schwarz/AFP A Pfizer informou nesta sexta-feira (17) que ainda n�o � poss�vel determinar a data de entrega de doses pedi�tricas da sua vacina contra a Covid-19 ao Brasil. Em nota, a farmac�utica disse que est� fazendo \"todos os esfor�os para que as doses cheguem ao pa�s o mais rapidamente poss�vel\" (veja abaixo a nota completa). A empresa tamb�m disse que est� atuando junto ao governo brasileiro para definir as pr�ximas etapas do processo. Vacina contra Covid-19 em crian�as no Brasil: veja o que se sabeVacina��o de crian�as contra a Covid: veja orienta��es A Ag�ncia Nacional de Vigil�ncia Sanit�ria (Anvisa) autorizou na quinta-feira (16) a aplica��o da vacina da Pfizer contra Covid-19 em crian�as de 5 a 11 anos. A aprova��o dada pela ag�ncia permite que a vacina j� seja usada no pa�s para essa faixa. No entanto, a chegada do imunizante aos postos depende do Minist�rio da Sa�de. A vacina da Pfizer para esse p�blico � um pouco diferente da dispon�vel para os maiores de 12 anos. O frasco da vacina para crian�as tamb�m ter� uma cor diferente daquela aplicada em adultos, para ajudar os profissionais de sa�de na hora de aplicar a vacina. \"A redu��o na dosagem para a faixa de 5 a 11 anos se respaldou nos estudos de Fase 1 e 2, que mostraram que essa dosagem (10 microgramas) foi o suficiente para gerar altos t�tulos de anticorpos com perfil de seguran�a bastante favor�vel para a popula��o pedi�trica\", informou a Pfizer. 2 de 2 Diferen�as entre as vacinas de adultos e crian�as � Foto: Reprodu��o/Anvisa Diferen�as entre as vacinas de adultos e crian�as � Foto: Reprodu��o/Anvisa Veja a nota completa Sobre o prazo da entrega das doses pedi�tricas da ComiRNAty ao Brasil, a Pfizer esclarece: Tendo recebido ontem a aprova��o do uso da vacina da Pfizer/BioNTech contra a COVID-19 em crian�as de 5 a 11 anos, ainda n�o � poss�vel determinar a data de entrega de doses pedi�tricas ao Brasil. A companhia est� fazendo todos os esfor�os para que doses cheguem ao pa�s o mais rapidamente poss�vel e atuando junto ao governo para definir as pr�ximas etapas desse processo. Dezembro de 2021Pfizer Brasil V�DEOS mais vistos do g1 200 v�deos", news.getContent());
		assertEquals("g1", news.getAuthor());
		assertEquals("17/12/2021 10h47", news.getDate());
		assertEquals("https://g1.globo.com/saude/coronavirus/vacinas/noticia/2021/12/17/vacina-contra-covid-19-em-criancas-pfizer-diz-nao-ter-prazo-e-que-esta-atuando-junto-ao-governo.ghtml", news.getUrl());
	}
	
}
