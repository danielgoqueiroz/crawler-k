package crawler;

import crawler.Crawler.TYPE;
import database.Database;
import webdriver.WebDriver;

public class Crawler {
	
	enum TYPE {
		SITEMAP, NAVIGATION;
	}
	
	private String url;
	private String sitemapUrl;
	private TYPE type;
	private static Database database;
	private WebDriver driver;

	public Crawler(String url, String sitemapUrl) {
		this.url = url;
		this.sitemapUrl = url + sitemapUrl;
		this.type = TYPE.NAVIGATION;
		database = new Database();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSitemapUrl() {
		return sitemapUrl;
	}

	public void setSitemapUrl(String sitemapUrl) {
		this.sitemapUrl = sitemapUrl;
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public static Database getDatabase() {
		return database;
	}

	public static void setDatabase(Database database) {
		Crawler.database = database;
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}
	
}
