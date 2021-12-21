package database;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;

import model.News;

public class DatabaseTest {

	private static Database database;

	@BeforeAll
	static void before() {
		database = new Database();
	}

	@Test
	void deveSalvarUrlParaSerVisitada() {
		News news = getNews();
		database.save(news);
		//deve salvar url
		//deve buscar url e o registro n�o retornar conte�do de not�cia
	}

	
	@Test
	void deveVerificarSeUrlJaFoiVisitadaERotornarFalse() {
		//deve salvar url
		//deve realizar busca de conte�do para a url
		//deve atualizar registro com conteudo
		//deve retornar conte�do para registro
		//a url deve constar como visitada
	}
	
	@Test
	void deveSalvarItem() {
		
	}

	@Test
	void deveVerificarSeUrlJaFoiVisitada() {
		//deve salvar url
		//deve verificar conteudo de registro salvo
		//deve obter conteudo de url 
		//deve atualizar registro com conteudo retornado
		//deve verificar se registro foi atualizado com sucesso 
	}
	
	private News getNews() {
		News news = new News();
		news.setUrl("https://g1.globo.com/pe/pernambuco/educacao/noticia/2021/12/07/justica-federal-suspende-processo-seletivo-para-cursos-tecnicos-de-nivel-medio-integrado-do-ifpe.ghtml");
		news.setAuthor("g1 PE");
		news.setContent("1 de 1 Em sua decis�o, o magistrado destacou que \"o crit�rio adotado para a sele��o � incompat�vel com a isonomia\". � Foto: Pedro Alves/G1 Em sua decis�o, o magistrado destacou que \"o crit�rio adotado para a sele��o � incompat�vel com a isonomia\". � Foto: Pedro Alves/G1 Uma decis�o liminar da Justi�a Federal em Pernambuco (JFPE) suspendeu, nesta ter�a (7), o processo seletivo para ingresso de novos alunos nos cursos t�cnicos de n�vel m�dio integrado no ano letivo 2022.1 do Instituto Federal de Educa��o, Ci�ncia e Tecnologia de Pernambuco (IFPE), incluindo Proeja. Ela � resultado de um mandado de seguran�a impetrado por um candidato inscrito no certame. Reitores de institui��es federais buscam caminhos para lidar com cortes no or�amento Em 11 anos, or�amento do MEC para as universidades federais cai 37% O IFPE anunciou em outubro o edital do Processo de Ingresso 2022.1 e que, pelo segundo ano, devido � pandemia, n�o haver� provas e que a sele��o ser� feita por meio de an�lise do hist�rico escolar dos ensinos fundamental e m�dio, a depender da modalidade de curso, e da nota geral do Exame Nacional do Ensino M�dio (Enem). De acordo com o autor da a��o, esse tipo de sele��o \"n�o submete os concorrentes �s mesmas condi��es de avalia��o\". Ele alegou, ainda, que \"nem todos os professores s�o igualmente rigorosos nas corre��es de suas provas e que alguns col�gios usam de trabalhos e atividades extras para 'complementar a nota', al�m de as notas serem sujeitas a influ�ncias externas e internas\". Ele tamb�m alegou que \"se a 'r�gua' � retirada, todo o sistema � posto em descr�dito e, ao final, a sele��o n�o ser� dos melhores alunos, mas daqueles que tiveram mais oportunidades, professores menos exigentes, melhor n�vel de relacionamento\". Outro argumento � que os alunos aprovados ter�o aulas presenciais, o que demonstra que a pandemia de Covid n�o justifica a altera��o da regra tradicional. Em sua decis�o, o juiz da 21� da JFPE, Francisco Ant�nio de Barros e Silva Neto, destacou que \"o crit�rio adotado para a sele��o � incompat�vel com a isonomia, pois incapaz de medir o conhecimento dos candidatos e candidatas �s vagas\". Ele tamb�m diz que � n�tida \"a desigualdade entre as institui��es escolares (quer p�blicas, quer privadas) no que tange aos projetos pedag�gicos e �s metodologias de ensino e de avalia��o\". Na decis�o, o juiz acrescentou, ainda, que a metodologia de avalia��o adotada pelas escolas n�o � sujeita a qualquer controle institucional nem social, o que impossibilita o seu uso como crit�rio decisivo em uma sele��o regida pelos princ�pios gerais da administra��o p�blica. \"Da� a necessidade de aplica��o de provas impessoais, transparentes e uniformes, � semelhan�a dos exames vestibulares e do Exame Nacional do Ensino M�dio (ENEM)\", destacou. O juiz acrescentou que a pandemia de Covid-19 n�o serve como justificativa para a flexibiliza��o do sistema objetivo de acesso �s vagas, j� que foi retomada a aplica��o presencial do Enem, a realiza��o de concursos p�blicos e as aulas presenciais na pr�pria rede federal de ensino. Em nota, o IFPE informou que suspendeu a sele��o para cursos t�cnicos presenciais, seguindo a decis�o judicial, e que, com isso \"a lista final das inscri��es e o resultado preliminar do processo referentes aos cursos t�cnicos n�o ser�o publicados nesta ter�a-feira, 7 de dezembro de 2021, como previsto\". \"Est� mantida, conforme o cronograma, a publica��o da lista final das inscri��es e o resultado preliminar do processo referentes ao Processo de Ingresso 2022.1 para cursos superiores presenciais\", disse ainda, mas n�o informou como fica o restante da sele��o. V�DEOS: Mais assistidos de Pernambuco nos �ltimos 7 dias 200 v�deos");
		news.setCaption("Decis�o liminar para sele��o do ano letivo 2022.1 � resultado de um mandado de seguran�a impetrado por candidato, que alega que sistema de m�dias escolares adotado pela institui��o n�o � justo.");
		news.setTitle("Justi�a Federal suspende processo seletivo para cursos t�cnicos do IFPE");
		news.setDate("07/12/2021 18h55", "dd/MM/yyyy HH'h'mm");
		return news;
	}

}
