package model;

import java.util.Date;

public class News {

	private String url;
	private String title;
	private String caption;
	private String author;
	private Date date;
	private String content;
	
	public News() {
		super();
	}

	public News(String url, String title, String caption, String author, Date date, String content) {
		super();
		this.url = url;
		this.title = title;
		this.caption = caption;
		this.author = author;
		this.date = date;
		this.content = content;
	}
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	

}
