package app;
import runneble.G1Runnable;
import runneble.InfomoneyRunnable;

public class CrawlerMain {
	
	public static void main(String[] args) {
		Thread g1 = new Thread(new G1Runnable());
		Thread infomoneyRun = new Thread(new InfomoneyRunnable());
		g1.start();
		infomoneyRun.start();
	}
}
