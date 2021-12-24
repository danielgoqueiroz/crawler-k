package com.danielqueiroz.app.runnable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danielqueiroz.app.crawler.InfomoneyCrawler;

public class InfomoneyRunnable implements Runnable{

	private static Logger logger = LoggerFactory.getLogger(InfomoneyRunnable.class);
	
	public void run() {
		try {
			logger.info("Iniciado Infomoney crawler");
			InfomoneyCrawler crawler = new InfomoneyCrawler();
			crawler.crawle();
			logger.info("Terminou");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
}
