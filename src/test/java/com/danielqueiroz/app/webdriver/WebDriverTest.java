package com.danielqueiroz.app.webdriver;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import com.danielqueiroz.webdriver.WebDriver;


public class WebDriverTest {

	private static WebDriver driver;
	
	@Test
	void deveClicarEmBotaoVerMais() throws InterruptedException{
		driver = new WebDriver();
		driver.get("https://www.infomoney.com.br/mercados/");
		driver.scroolToToElementId("infinite-handle");
		driver.executeScript("infiniteScroll.scroller.refresh()", 5);
		int linksBefore= driver.getElements("//div[@class=\"row py-3 item\"]").size();
		int linksAfter = driver.getElements("//div[@class=\"row py-3 item\"]").size();
		while (linksAfter == linksBefore) {
			System.out.println("Esperando carregar itens");
			linksAfter = driver.getElements("//div[@class=\"row py-3 item\"]").size();
			Thread.sleep(3000);
		}
		assertTrue(linksBefore < linksAfter);
		driver.quit();
	}
	
	@Test
	void deveCarregarUrl() throws IOException {
		driver = new WebDriver();
		String html = driver.get("http://g1.globo.com/jornal-nacional/noticia/2016/10/pf-investigou-policia-do-senado-por-cinco-meses.html");
		Document doc = Jsoup.parse(html);
		FileUtils.write(new File("target/teste.html"), html, "UTF-8");
		Elements selectXpath = doc.selectXpath("//h1[@class=\"entry-title\"]");
		System.out.println(selectXpath.text());
		assertNotNull(selectXpath);
		driver.close();
	}
	
}
