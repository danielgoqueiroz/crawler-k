package com.danielqueiroz.app.runnable;



import com.danielqueiroz.app.crawler.G1Crawler;

public class G1Runnable implements Runnable {


	public void run() {
		System.out.println("Iniciado G1 crawler");
		G1Crawler crawler = new G1Crawler();
		crawler.crawle();
		System.out.println("Terminou");
	}
}
