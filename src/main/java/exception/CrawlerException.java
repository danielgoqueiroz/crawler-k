package exception;

public class CrawlerException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	public CrawlerException(String message) {
		this.message = message;
	}

}
