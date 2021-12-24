package com.danielqueiroz.webdriver;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebDriver {

	private ChromeDriver driver;

	public WebDriver(ChromeDriver driver) {
		this.driver = driver;
	}

	public WebDriver() {
		if (driver == null) {
			driver = newDrive();
		}
	}

	public static ChromeDriver newDrive() {
		WebDriverManager.chromedriver().setup();

		ChromeDriver chromeDriver = null;
		System.setProperty("webdriver.chrome.whitelistedIps", "");
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_settings.popups", 0);
		options.addArguments("disable-extensions");
		options.setExperimentalOption("prefs", prefs);
		options.addArguments("chrome.switches", "--disable-extensions");
		options.addArguments("--headless");
		options.addArguments("--test-type");

		chromeDriver = new ChromeDriver(options);
		return chromeDriver;
	}
	
	public ChromeDriver getDriver() {
		return this.driver;
	}

	public void close() {
		driver.close();
	}

	public void quit() {
		driver.quit();
	}

	public void navigate(String url) {
		driver.get(url);
	}

	public List<WebElement> getElements(String xpath) {
		try {
			List<WebElement> findElements = this.driver.findElements(By.xpath(xpath));
			return findElements;
		} catch (Exception e) {
			return Collections.emptyList();
		}
	}

	public String getText(String xpath) {
		return driver.findElement(By.xpath(xpath)).getText();
	}

	public String get(String url) {
		driver.get(url);
		String html = driver.getPageSource();
		return html;
	}
	
	public void scroolToToElementId(String id) {
		driver.executeScript("window.scrollTo(0, document.getElementById('"+ id +"').getBoundingClientRect().y)");
	}
	
	public void executeScript(String script, int timeoutInSecounds) {
		try {
			Thread.sleep(timeoutInSecounds * 1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		driver.executeScript(script);
	}

	public void click(String id) {
		System.out.print("Buscando botão 'ver mais': ");
		try {
			driver.wait(1000);
			Actions actions = new Actions(driver);
			WebElement button = driver.findElement(By.id(id)).findElement(By.tagName("button"));
			actions.moveToElement(button).click().perform();
			System.out.println("Clicado.");
		} catch (Exception e) {
			System.out.println("Não encontrado.");
		}
	}

}
