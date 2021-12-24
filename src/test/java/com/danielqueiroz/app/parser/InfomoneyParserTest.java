package com.danielqueiroz.app.parser;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.text.ParseException;

import org.junit.jupiter.api.Test;

import com.danielqueiroz.app.utils.TestUtils;
import com.danielqueiroz.app.model.News;
import com.danielqueiroz.app.parser.InfomoneyParser;

public class InfomoneyParserTest {

	@Test
	void deveRealizarParserEClicarEmBotaoVerMais() throws IOException, ParseException {
		InfomoneyParser parser = new InfomoneyParser("https://www.infomoney.com.br/mercados/");
		parser.parse();
		parser.getLinks();
		assertTrue(parser.getLinks().size() > 10);
	}
	
	@Test
	void deveRealizarParserDePaginaDeNoticia() throws IOException, ParseException {
		InfomoneyParser parser = new InfomoneyParser("https://www.infomoney.com.br/colunistas/convidados/instituicoes-invadem-o-metaverso-nike-apple-microsoft-e-mais-disputam-espaco-no-setor/");
		News news  = parser.parse();
		assertEquals("Institui��es invadem o metaverso: Nike, Apple, Microsoft e mais disputam espa�o no setor", news.getTitle());
		assertEquals("Nas �ltimas semanas, o mundo cripto e as empresas tradicionais se viram diante de um grande marco com a mudan�a de nome do Facebook", news.getCaption());
		assertEquals("�Gradualmente� at� que de repente�: a express�o descreve uma percep��o comum nos processos de inova��o. Empreendedores passam anos desafiando paradigmas tecnol�gicos, anunciando esporadicamente algumas conquistas incrementais, at� que algum grande marco ganha a aten��o p�blica e d� a impress�o de que tudo aconteceu muito r�pido. Nas �ltimas semanas, o mundo cripto e as empresas tradicionais se viram diante de um grande marco, a transi��o do gigante Facebook para o metaverso, agora com o nome Meta. Mark Zuckerberg anunciou que, agora, sua empresa estar� focada na cria��o de experi�ncias com Realidade Virtual (RV) e Realidade Aumentada (RA) para seus usu�rios interagirem online como se estivessem reunidos fisicamente. O p�blico respondeu � altura: poucos dias ap�s a not�cia, as buscas por �metaverso� chegaram a crescer 900%, segundo dados do Google Trends, e os criptoativos ligados ao setor valorizaram 151% de acordo com o CoinMarketCap, denotando um senso de urg�ncia t�pico do varejo. Reverbera��es no mundo corporativo No meio corporativo, a not�cia tem pouco de novidade: gigantes de tecnologia, como Nvidia, Tencent, Microsoft e Apple j� t�m suas pr�prias iniciativas com RV e RA. A Apple, em especial, representa bem o aspecto gradual dessa revolu��o tecnol�gica. A empresa, percebida como a principal candidata para desbloquear a economia do metaverso, j� havia registrado em 2012 um �capacete� com tela para experi�ncias virtuais, tem investido paulatinamente no desenvolvimento de dispositivos e, aparentemente, est� muito perto de finalmente lan�ar um produto do tipo. Mesmo que n�o seja a primeira a alcan�ar ado��o em massa com seus dispositivos de RV e RA, especialistas acreditam que a empresa poder� usar sua impressionante expertise no desenvolvimento de usabilidade e marketing de produtos para liderar a nova tend�ncia. A Epic Games, outra gigante na corrida pelo metaverso, j� deu importantes passos no �mbito de software. A empresa est� utilizando seu popular jogo Fortnite para promover experi�ncias com RV, tendo alcan�ado mais de um milh�o de espectadores nos shows virtuais do rapper Travis Scott e da cantora pop Ariana Grande. Outras empresas aproveitaram o momento gerado pela Meta para impulsionarem suas iniciativas. A Nike come�ou a representar virtualmente seus t�nis vendidos como NFTs, para que estes possam ser usados em jogos e outras experi�ncias no Nikeland, metaverso sendo desenvolvido pela marca. No �ltimo dia 13 de dezembro, a companhia comprou uma produtora de t�nis digitais para acelerar sua jornada no ecossistema. J� os g�meos Winklevoss, conhecidos por disputar os direitos de fundadores do Facebook contra Zuckerberg, captaram US$ 400 milh�es para sua pr�pria iniciativa. Os empres�rios, que, at� agora, empreendiam com recursos pr�prios, criticam o modelo de rede social baseado na comercializa��o de dados dos usu�rios e prometem um metaverso com �tecnologia que protege os direitos e a dignidade dos indiv�duos�. No Brasil, uma das maiores institui��es financeiras do pa�s anunciou seus projetos no metaverso. Segundo o Banco do Brasil, ser�o oferecidas experi�ncias virtuais simulando o dia-a-dia das opera��es do banco, como abastecimento dos caixas eletr�nicos e a dire��o de carros-fortes, e um tour virtual � exposi��o �Egito Antigo� no Centro Cultural Banco do Brasil Rio de Janeiro (CCBB-RJ). Entenda o que � Metaverso e por que est� chamando a aten��o dos investidores Reverbera��es no mundo cripto No mundo cripto, o an�ncio de Zuckerberg trouxe um f�lego impressionante. Com bilh�es de d�lares sendo investidos em projetos cripto para o metaverso, os sinais de otimismo se viram principalmente na aprecia��o dos criptoativos relacionados ao mercado de bens virtuais. Assim como os irm�os Winklevoss, os empreendedores do mundo cripto demonstram preocupa��o com o desenvolvimento de projetos focados na explora��o de dados dos usu�rios, o que pode impulsionar iniciativas descentralizadas em paralelo com o cen�rio institucional. Aprecia��o dos tokens A onda de confian�a trouxe grande valoriza��o dos projetos relacionados ao metaverso. Como pode ser visto no gr�fico abaixo, no dia 25 de novembro, o Metaverse Index (MVI, �ndice de criptoativos do setor) chegou a registrar 151% de retorno com rela��o ao dia do an�ncio. Nas semanas seguintes, o segmento cedeu � press�o baixista protagonizada pelo Bitcoin � embora mantendo, ainda, retorno superior a 50% ante o dia 28 de outubro. Gr�fico de desemprenho do �ndice de criptoativos relacionados ao metaverso (fonte: CoinMarketCap) Nesse contexto, os tokens MANA, GALA e SAND ganharam destaque, com retornos superiores a 450% no per�odo. Aqui, destaca-se mais uma vez o car�ter gradual do surgimento desse ecossistema: Decentraland (MANA) e The Sandbox (SAND) s�o projetos sendo desenvolvidos desde 2015 e 2017, respectivamente. Enquanto o primeiro j� est� em fase operacional desde 2017, mas com ainda pouca tra��o entre os usu�rios, The Sandbox lan�ou sua vers�o alpha no �ltimo dia 29 de novembro. Vendas internas Ainda mais importante que a aprecia��o dos tokens, as �ltimas semanas assistiram a uma onda de investimentos na economia interna dos projetos de metaverso. Essa tend�ncia ficou clara com a valoriza��o de terras virtuais. No dia 23 de novembro, um terreno no Decentraland foi comprado pelo equivalente a mais de US$ 2,4 milh�es pelo Metaverse Group, uma companhia de incorpora��o de im�veis virtuais. No dia seguinte, um peda�o de terra no Axie Infinity trocou de m�os por US$ 2,3 milh�es. E o maior movimento ainda estava por vir: ap�s 4 anos de desenvolvimento, a primeira vers�o do projeto The Sandbox atraiu grande interesse do varejo e de investidores institucionais. Em apenas quatro dias, as vendas internas nesse ecossistema j� haviam somado cerca de US$ 86 milh�es. O destaque foi para o valor recorde de US$ 4,3 milh�es por um terreno comprado pela Republic Realm, incorporadora virtual que j� possui cerca de 2.500 peda�os de terra em 19 mundos virtuais. Em parte do terreno rec�m-comprado, a empresa planeja construir em parceria com a Atari. Conclus�o A recente onda de otimismo no metaverso certamente n�o � a causa da transforma��o tecnol�gica que est� por vir. Essa vir�, sim, gra�as ao trabalho paulatino de empreendedores e desenvolvedores no setor. Mas, ao atrair tamanho volume de aten��o e recursos, esse momento do mercado pode acelerar o surgimento de solu��es no setor e a ado��o dos usu�rios. � de se esperar que, nesse contexto, se intensifique a disputa dos produtores de hardware para navega��o no metaverso at� um equil�brio oligopolista � em que tenderiam a se destacar a Apple, a Microsoft e o Google, que j� desenvolvem este tipo de solu��o h� anos e t�m larga vantagem no paradigma atual dos dispositivos m�veis. J� na ind�stria de software, � prov�vel que haja mais espa�o para a competi��o � exceto, talvez, no que se refere aos sistemas operacionais e tecnologias de base para o paradigma. Aqui, poder�o disputar n�o s� as mais conhecidas empresas de tecnologia, como tamb�m aquelas dos ramos de jogos virtuais e os projetos descentralizados. Para o sucesso das iniciativas cripto no setor � especialmente importante a capacidade de integra��o com as solu��es de base, como dispositivos, sistemas operacionais e protocolos de comunica��o. O segmento poder� ser alavancado pelo amadurecimento do mundo cripto, com maior aceita��o e colabora��o das institui��es, pelo poder das comunidades e pelo reconhecimento da import�ncia da privacidade de dados dos usu�rios.", news.getContent());
		assertEquals("Hashdex", news.getAuthor());
		assertEquals(TestUtils.parseDate("17 dez 2021 16h37", "dd MMMM yyyy HH'h'mm"), news.getDate());
		assertEquals("https://www.infomoney.com.br/colunistas/convidados/instituicoes-invadem-o-metaverso-nike-apple-microsoft-e-mais-disputam-espaco-no-setor/", news.getUrl());
	}
	
}
