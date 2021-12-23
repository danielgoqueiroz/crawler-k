package webdriver;

import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebDriver {

	private static ChromeDriver driver;
	
	public WebDriver() {
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
		options.addArguments("--headless");
		options.addArguments("--test-type");
		
		driver = new ChromeDriver(options);
		
	}
	
	public void close() {
		driver.close();
	}
	
	public void navigate(String url) {
		driver.get(url);
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
		close();
		return html;
	}
	
	
}
