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
		assertEquals("Vacina contra Covid-19 em crianças: Pfizer diz não ter prazo de entrega e que está atuando junto ao governo", news.getTitle());
		assertEquals("Anvisa autorizou a aplicação da vacina da Pfizer contra Covid-19 em crianças de 5 a 11 anos na quinta-feira (16)", news.getCaption());
		assertEquals("1 de 2 Frascos da vacina da Pfizer em versão pediátrica (laranja) e a partir dos 12 anos (roxa) — Foto: Tobias Schwarz/AFP Frascos da vacina da Pfizer em versão pediátrica (laranja) e a partir dos 12 anos (roxa) — Foto: Tobias Schwarz/AFP A Pfizer informou nesta sexta-feira (17) que ainda não é possível determinar a data de entrega de doses pediátricas da sua vacina contra a Covid-19 ao Brasil. Em nota, a farmacêutica disse que está fazendo \"todos os esforços para que as doses cheguem ao país o mais rapidamente possível\" (veja abaixo a nota completa). A empresa também disse que está atuando junto ao governo brasileiro para definir as próximas etapas do processo. Vacina contra Covid-19 em crianças no Brasil: veja o que se sabe Vacinação de crianças contra a Covid: veja orientações A Agência Nacional de Vigilância Sanitária (Anvisa) autorizou na quinta-feira (16) a aplicação da vacina da Pfizer contra Covid-19 em crianças de 5 a 11 anos. A aprovação dada pela agência permite que a vacina já seja usada no país para essa faixa. No entanto, a chegada do imunizante aos postos depende do Ministério da Saúde. Sobre a declaração do governador João Doria de comprar a vacina diretamente da empresa, a Pfizer também se posicionou afirmando que seu foco é atender o terceiro contrato de 100 milhões de doses já fechado com o governo federal. \"Esse contrato já engloba o fornecimento de novas versões da vacina, inclusive para diferentes faixas etárias\", declarou a empresa. \"Entendemos que a vacina da Pfizer/BioNTech contra a COVID-19 é um bem que deve ser oferecido à população em geral, por isso sempre estivemos comprometidos a trabalhar em colaboração com os governos em todo o mundo para que a ComiRNaty seja uma opção na luta contra a pandemia, como parte dos programas nacionais de imunização\", informou a empresa. A vacina da Pfizer para as crianças de 5 a 11 anos é um produto diferente do disponível para os maiores de 12 anos. Além da mudança na dosagem indicada, o próprio frasco tem cor diferente daquele usado na aplicação em adultos. \"A redução na dosagem para a faixa de 5 a 11 anos se respaldou nos estudos de Fase 1 e 2, que mostraram que essa dosagem (10 microgramas) foi o suficiente para gerar altos títulos de anticorpos com perfil de segurança bastante favorável para a população pediátrica\", informou a Pfizer. 2 de 2 Diferenças entre as vacinas de adultos e crianças — Foto: Reprodução/Anvisa Diferenças entre as vacinas de adultos e crianças — Foto: Reprodução/Anvisa VÍDEOS mais vistos do g1 200 vídeos", news.getContent());
		assertEquals("g1", news.getAuthor());
		assertEquals(new Date(1639748820000l), news.getDate());
		assertEquals("https://g1.globo.com/saude/coronavirus/vacinas/noticia/2021/12/17/vacina-contra-covid-19-em-criancas-pfizer-diz-nao-ter-prazo-e-que-esta-atuando-junto-ao-governo.ghtml", news.getUrl());
	}
	
}
