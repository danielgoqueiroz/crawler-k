package com.danielqueiroz.app.runnable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danielqueiroz.app.crawler.G1Crawler;

public class G1Runnable implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(G1Runnable.class);
	
	public void run() {
		try {
			logger.info("Iniciado G1 crawler");
			G1Crawler crawler = new G1Crawler();
			crawler.crawle();
			logger.info("Terminou");			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
