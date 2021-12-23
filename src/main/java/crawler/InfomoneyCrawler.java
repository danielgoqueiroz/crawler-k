package crawler;

public class InfomoneyCrawler extends Crawler {

	public InfomoneyCrawler(String url, String sitemapUrl) {
		super("https://www.infomoney.com.br/mercados/", "post-sitemap1.xml");
	}
	
}
