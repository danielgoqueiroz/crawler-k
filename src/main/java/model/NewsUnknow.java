package model;

import java.util.Map;
import java.util.UUID;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.fasterxml.jackson.databind.ObjectMapper;

import Utils.Utils;

public class NewsUnknow {

	private String id;
	private String url;
	
	public NewsUnknow() {
		super();
	}

	public NewsUnknow(String url) {
		super();
		this.url = url;
	}
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
		this.id = getId();
	}

	public String getId() {
        return UUID.nameUUIDFromBytes(getUrl().getBytes()).toString();
	}
	
	
	public static Item getItem(NewsUnknow news) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, AttributeValue> convertValue = mapper.convertValue(news, Map.class);

		Item item = new Item();
		if (news.getId() != null) {
			item.withPrimaryKey("id", news.getId());
		}
		Utils.extratcValueIfNotNull(news.getUrl(), item, "url");

		return item;
	}

	
	
	public static NewsUnknow itemToNews(Item item) {
		NewsUnknow news = new NewsUnknow();
		news.setUrl(item.getString("url"));	
		return news;
	}

	public static NewsUnknow itemToNews(Map<String, AttributeValue> item) {
		NewsUnknow news = new NewsUnknow();
		news.setUrl(item.get("url").getS());
		return news;
	}

}
