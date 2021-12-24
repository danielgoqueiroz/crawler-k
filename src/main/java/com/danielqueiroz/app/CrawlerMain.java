package com.danielqueiroz.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danielqueiroz.app.runnable.G1Runnable;
import com.danielqueiroz.app.runnable.InfomoneyRunnable;

	
public class CrawlerMain {
	
	private static Logger logger = LoggerFactory.getLogger(CrawlerMain.class);
	
	public static void main(String[] args) {
		logger.info("Inciado Crawler");
		Thread g1 = new Thread(new G1Runnable());
		Thread infomoneyRun = new Thread(new InfomoneyRunnable());
		g1.start();
		infomoneyRun.start();
	}
}
