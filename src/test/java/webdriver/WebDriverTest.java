package webdriver;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;


public class WebDriverTest {

	private static WebDriver driver;
	
	@Test
	void deveCarregarUrl() throws IOException {
		driver = new WebDriver();
		String html = driver.get("http://g1.globo.com/jornal-nacional/noticia/2016/10/pf-investigou-policia-do-senado-por-cinco-meses.html");
		Document doc = Jsoup.parse(html);
		FileUtils.write(new File("target/teste.html"), html, "UTF-8");
		Elements selectXpath = doc.selectXpath("//h1[@class=\"entry-title\"]");
		System.out.println(selectXpath.text());
		assertNotNull(selectXpath);
	}
	
}
