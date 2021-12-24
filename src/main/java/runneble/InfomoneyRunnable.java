package runneble;

import crawler.InfomoneyCrawler;

public class InfomoneyRunnable implements Runnable{

	public void run() {
		InfomoneyCrawler crawler = new InfomoneyCrawler();
		crawler.crawle();
	}
	
}
