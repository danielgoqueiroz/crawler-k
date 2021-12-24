package com.danielqueiroz.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.danielqueiroz.utils.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

public class News {

	@SuppressWarnings("unused")
	private String id;
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
		this.id = getId();
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
	public void setDate(String date, String dateFormat) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		try {
			this.date = format.parse(date);
		} catch (ParseException e) {
			this.date = new Date();
			e.printStackTrace();
		}
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

	public String getId() {
        return UUID.nameUUIDFromBytes(getUrl().getBytes()).toString();
	}
	
	
	@SuppressWarnings({ "unchecked", "unused" })
	public static Item getItem(News news) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, AttributeValue> convertValue = mapper.convertValue(news, Map.class);

		Item item = new Item();
		if (news.getId() != null) {
			item.withPrimaryKey("id", news.getId());
		}
		if (news.getDate() != null) {
			item.withLong("date", news.getDate().getTime());
		}
		
		Utils.extratcValueIfNotNull(news.getUrl(), item, "url");
		Utils.extratcValueIfNotNull(news.getAuthor(), item, "author");
		Utils.extratcValueIfNotNull(news.getCaption(), item, "captation");
		Utils.extratcValueIfNotNull(news.getContent(), item, "content");
		Utils.extratcValueIfNotNull(news.getTitle(), item, "title");

		return item;
	}

	
	
	public static News itemToNews(Item item) {
		News news = new News();
		news.setUrl(item.getString("url"));
		news.setAuthor(item.getString("author"));
		news.setCaption(item.getString("captation"));
		news.setContent(item.getString("content"));
		news.setTitle(item.getString("title"));
		news.setDate(new Date(item.getLong("date")));
		return news;
	}

	public static News itemToNews(Map<String, AttributeValue> item) {
		News news = new News();
		news.setUrl(item.get("url").getS());
		news.setAuthor(item.get("author").getS());
		news.setCaption(item.get("captation").getS());
		news.setContent(item.get("content").getS());
		news.setTitle(item.get("title").getS());
		news.setDate(new Date(Long.valueOf(item.get("date").getN())));
		return news;
	}

}
