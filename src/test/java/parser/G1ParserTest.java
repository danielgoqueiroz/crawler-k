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
		assertEquals("Vacina contra Covid-19 em crianças: Pfizer diz não ter prazo de entrega e que está atuando junto ao governo", news.getTitle());
		assertEquals("A Anvisa autorizou a aplicação da vacina da Pfizer contra Covid-19 em crianças de 5 a 11 anos na quinta-feira (16)", news.getCaption());
		assertEquals("1 de 2 Frascos da vacina da Pfizer em versão pediátrica (laranja) e a partir dos 12 anos (roxa) — Foto: Tobias Schwarz/AFP Frascos da vacina da Pfizer em versão pediátrica (laranja) e a partir dos 12 anos (roxa) — Foto: Tobias Schwarz/AFP A Pfizer informou nesta sexta-feira (17) que ainda não é possível determinar a data de entrega de doses pediátricas da sua vacina contra a Covid-19 ao Brasil. Em nota, a farmacêutica disse que está fazendo \"todos os esforços para que as doses cheguem ao país o mais rapidamente possível\" (veja abaixo a nota completa). A empresa também disse que está atuando junto ao governo brasileiro para definir as próximas etapas do processo. Vacina contra Covid-19 em crianças no Brasil: veja o que se sabeVacinação de crianças contra a Covid: veja orientações A Agência Nacional de Vigilância Sanitária (Anvisa) autorizou na quinta-feira (16) a aplicação da vacina da Pfizer contra Covid-19 em crianças de 5 a 11 anos. A aprovação dada pela agência permite que a vacina já seja usada no país para essa faixa. No entanto, a chegada do imunizante aos postos depende do Ministério da Saúde. A vacina da Pfizer para esse público é um pouco diferente da disponível para os maiores de 12 anos. O frasco da vacina para crianças também terá uma cor diferente daquela aplicada em adultos, para ajudar os profissionais de saúde na hora de aplicar a vacina. \"A redução na dosagem para a faixa de 5 a 11 anos se respaldou nos estudos de Fase 1 e 2, que mostraram que essa dosagem (10 microgramas) foi o suficiente para gerar altos títulos de anticorpos com perfil de segurança bastante favorável para a população pediátrica\", informou a Pfizer. 2 de 2 Diferenças entre as vacinas de adultos e crianças — Foto: Reprodução/Anvisa Diferenças entre as vacinas de adultos e crianças — Foto: Reprodução/Anvisa Veja a nota completa Sobre o prazo da entrega das doses pediátricas da ComiRNAty ao Brasil, a Pfizer esclarece: Tendo recebido ontem a aprovação do uso da vacina da Pfizer/BioNTech contra a COVID-19 em crianças de 5 a 11 anos, ainda não é possível determinar a data de entrega de doses pediátricas ao Brasil. A companhia está fazendo todos os esforços para que doses cheguem ao país o mais rapidamente possível e atuando junto ao governo para definir as próximas etapas desse processo. Dezembro de 2021Pfizer Brasil VÍDEOS mais vistos do g1 200 vídeos", news.getContent());
		assertEquals("g1", news.getAuthor());
		assertEquals("17/12/2021 10h47", news.getDate());
		assertEquals("https://g1.globo.com/saude/coronavirus/vacinas/noticia/2021/12/17/vacina-contra-covid-19-em-criancas-pfizer-diz-nao-ter-prazo-e-que-esta-atuando-junto-ao-governo.ghtml", news.getUrl());
	}
	
}
