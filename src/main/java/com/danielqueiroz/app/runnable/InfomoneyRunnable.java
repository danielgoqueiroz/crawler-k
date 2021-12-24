package com.danielqueiroz.app.runnable;

import com.danielqueiroz.app.crawler.InfomoneyCrawler;

public class InfomoneyRunnable implements Runnable {

	public void run() {
		System.out.println("Iniciado Infomoney crawler");
		InfomoneyCrawler crawler = new InfomoneyCrawler();
		crawler.crawle();
		System.out.println("Terminou");
	}

}
