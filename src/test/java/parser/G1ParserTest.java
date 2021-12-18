package parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.junit.jupiter.api.Test;

import model.News;


public class G1ParserTest {

	@Test
	void deveRealizarParserDePaginaDeNoticia() throws IOException, ParseException {
		G1Parser parser = new G1Parser("https://g1.globo.com/saude/coronavirus/vacinas/noticia/2021/12/17/vacina-contra-covid-19-em-criancas-pfizer-diz-nao-ter-prazo-e-que-esta-atuando-junto-ao-governo.ghtml");
		News news  = parser.parsePage();
		assertEquals("Vacina contra Covid-19 em crian�as: Pfizer diz n�o ter prazo de entrega e que est� atuando junto ao governo", news.getTitle());
		assertEquals("Anvisa autorizou a aplica��o da vacina da Pfizer contra Covid-19 em crian�as de 5 a 11 anos na quinta-feira (16)", news.getCaption());
		assertEquals("1 de 2 Frascos da vacina da Pfizer em vers�o pedi�trica (laranja) e a partir dos 12 anos (roxa) � Foto: Tobias Schwarz/AFP Frascos da vacina da Pfizer em vers�o pedi�trica (laranja) e a partir dos 12 anos (roxa) � Foto: Tobias Schwarz/AFP A Pfizer informou nesta sexta-feira (17) que ainda n�o � poss�vel determinar a data de entrega de doses pedi�tricas da sua vacina contra a Covid-19 ao Brasil. Em nota, a farmac�utica disse que est� fazendo \"todos os esfor�os para que as doses cheguem ao pa�s o mais rapidamente poss�vel\" (veja abaixo a nota completa). A empresa tamb�m disse que est� atuando junto ao governo brasileiro para definir as pr�ximas etapas do processo. Vacina contra Covid-19 em crian�as no Brasil: veja o que se sabe Vacina��o de crian�as contra a Covid: veja orienta��es A Ag�ncia Nacional de Vigil�ncia Sanit�ria (Anvisa) autorizou na quinta-feira (16) a aplica��o da vacina da Pfizer contra Covid-19 em crian�as de 5 a 11 anos. A aprova��o dada pela ag�ncia permite que a vacina j� seja usada no pa�s para essa faixa. No entanto, a chegada do imunizante aos postos depende do Minist�rio da Sa�de. Sobre a declara��o do governador Jo�o Doria de comprar a vacina diretamente da empresa, a Pfizer tamb�m se posicionou afirmando que seu foco � atender o terceiro contrato de 100 milh�es de doses j� fechado com o governo federal. \"Esse contrato j� engloba o fornecimento de novas vers�es da vacina, inclusive para diferentes faixas et�rias\", declarou a empresa. \"Entendemos que a vacina da Pfizer/BioNTech contra a COVID-19 � um bem que deve ser oferecido � popula��o em geral, por isso sempre estivemos comprometidos a trabalhar em colabora��o com os governos em todo o mundo para que a ComiRNaty seja uma op��o na luta contra a pandemia, como parte dos programas nacionais de imuniza��o\", informou a empresa. A vacina da Pfizer para as crian�as de 5 a 11 anos � um produto diferente do dispon�vel para os maiores de 12 anos. Al�m da mudan�a na dosagem indicada, o pr�prio frasco tem cor diferente daquele usado na aplica��o em adultos. \"A redu��o na dosagem para a faixa de 5 a 11 anos se respaldou nos estudos de Fase 1 e 2, que mostraram que essa dosagem (10 microgramas) foi o suficiente para gerar altos t�tulos de anticorpos com perfil de seguran�a bastante favor�vel para a popula��o pedi�trica\", informou a Pfizer. 2 de 2 Diferen�as entre as vacinas de adultos e crian�as � Foto: Reprodu��o/Anvisa Diferen�as entre as vacinas de adultos e crian�as � Foto: Reprodu��o/Anvisa V�DEOS mais vistos do g1 200 v�deos", news.getContent());
		assertEquals("g1", news.getAuthor());
		assertEquals(new Date(1639748820000l), news.getDate());
		assertEquals("https://g1.globo.com/saude/coronavirus/vacinas/noticia/2021/12/17/vacina-contra-covid-19-em-criancas-pfizer-diz-nao-ter-prazo-e-que-esta-atuando-junto-ao-governo.ghtml", news.getUrl());
	}
	
}
