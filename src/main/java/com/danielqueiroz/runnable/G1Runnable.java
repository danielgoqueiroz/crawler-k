package com.danielqueiroz.runnable;

import com.danielqueiroz.crawler.G1Crawler;

public class G1Runnable implements Runnable {

	public void run() {
		G1Crawler crawler = new G1Crawler();
		crawler.crawle();
	}
}
