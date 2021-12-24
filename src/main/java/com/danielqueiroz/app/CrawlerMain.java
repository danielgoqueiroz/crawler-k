package com.danielqueiroz.app;
import com.danielqueiroz.runnable.G1Runnable;
import com.danielqueiroz.runnable.InfomoneyRunnable;

public class CrawlerMain {
	
	public static void main(String[] args) {
		Thread g1 = new Thread(new G1Runnable());
		Thread infomoneyRun = new Thread(new InfomoneyRunnable());
		g1.start();
		infomoneyRun.start();
	}
}
