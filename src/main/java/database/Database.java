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
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import model.News;

public class Database {

	private static AmazonDynamoDB client;
	
	public Database() {
		System.setProperty("sqlite4java.library.path", "native-libs");

		if (client == null) {
			
			AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard();
			client = AmazonDynamoDBClientBuilder.standard()
//	                .withRegion(Regions.DEFAULT_REGION)
	                .withCredentials(new ProfileCredentialsProvider("profile_daniel"))
	                .withEndpointConfiguration(
	    					new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "local"))
	                .build();
			
		}
	}

	public void save(News news ) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, AttributeValue> convertValue = mapper.convertValue(news, Map.class);
		
		PutItemRequest item = new PutItemRequest("news", convertValue);
		client.putItem(item);
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
