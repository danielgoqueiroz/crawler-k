package com.danielqueiroz.app.crawler;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danielqueiroz.app.model.News;
import com.danielqueiroz.app.model.NewsUnknow;
import com.danielqueiroz.app.webdriver.WebDriver;
import com.danielqueiroz.app.CrawlerMain;
import com.danielqueiroz.app.database.Database;

public class Crawler {
	
	public enum TYPE {
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
	
	public WebDriver getDriverInstance() {
		if (getDriver()== null) {
			setDriver(new WebDriver());
		}
		return getDriver();
	}


	void saveResult(String url, News news) {
		if (news != null) {
			System.out.println("Salvando p?gina");
			getDatabase().save(news);
		} else {
			NewsUnknow unknoew = new NewsUnknow(url);
			System.out.println("N?o encontrou conte?do. Salvando url como visitada para ser ignorada ou ser revisitada.");
			getDatabase().save(unknoew);
		}
	}

	@SuppressWarnings("unused")
	private void saveError(String url, Exception e) throws IOException {
		FileUtils.write(new File("target/error/" + getId(url)), url + "\n" + e.getMessage(), "UTF-8");
		System.out.println("Erro ao processar url: " + url + " Erro: " + e.getMessage());
	}

	private String getId(String u) {
		UUID uuid = UUID.nameUUIDFromBytes(u.getBytes());
		return uuid.toString();
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
