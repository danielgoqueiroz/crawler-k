package webdriver;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriver {

	private ChromeDriver driver;
	
	public WebDriver() {
		if (driver == null) {
			driver = newDrive();
		}
	}
	
	public static ChromeDriver newDrive() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");	
		System.setProperty("webdriver.chrome.whitelistedIps", "");
		
		ChromeOptions options = new ChromeOptions();
		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("profile.default_content_settings.popups", 0);
		options.addArguments("disable-extensions");
		prefs.put("credentials_enable_service", false);
		prefs.put("password_manager_enabled", false);
		options.setExperimentalOption("prefs", prefs);
		options.addArguments("chrome.switches","--disable-extensions");
//		options.addArguments("--headless");
		options.addArguments("--test-type");
		
		return new ChromeDriver(options);
	}
	
	public void close() {
		driver.close();
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
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return html;
	}
	
	
}
