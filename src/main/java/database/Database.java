package database;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import model.News;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public class Database {

	private static AmazonDynamoDB client;
	private static DynamoDB dynamoDB;
	
	public Database() {

		if (client == null) {
			client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
					new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
					.build(); 		
			
//			AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard();
//			client = AmazonDynamoDBClientBuilder.standard()
////	                .withRegion(Regions.DEFAULT_REGION)
//	                .withCredentials(new ProfileCredentialsProvider("profile_daniel"))
//	                .withEndpointConfiguration(
//	    					new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "local"))
//	                .build();
			
		}
	}

	public void save(News news ) {
		dynamoDB = new DynamoDB(client);
		Table table = dynamoDB.getTable("news");
		
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, AttributeValue> convertValue = mapper.convertValue(news, Map.class);
		
		Item item = new Item();
		item.withPrimaryKey("id", news.getId());
		item.withString("url", news.getUrl());
		item.withString("author", news.getAuthor());
		item.withString("captation", news.getCaption());
		item.withString("content", news.getContent());
		item.withString("title", news.getTitle());
		item.withLong("date", news.getDate().getTime());
		
		PutItemOutcome putItem = table.putItem(item);
		System.out.println(putItem);
		
	}
	
	public Item get(String id) {
		dynamoDB = new DynamoDB(client);

		Table table = dynamoDB.getTable("news");

		Item item = table.getItem("id", id);
		return item;
		
	}
	
	public void saveLocal(News news) throws ParseException, IOException {

		File file = new File("target/news/" + news.getId());

		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}

		if (file.exists()) {
			System.out.println("Notícia já salva");
			return;
		}
		try {
			FileUtils.write(file, new Gson().toJson(news), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
