package database;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.google.gson.Gson;

import model.News;

public class Database {

	private static final String NEWS = "news";
	private static AmazonDynamoDB client;
	private static DynamoDB dynamoDB;

	public Database() {
		if (client == null) {
			client = AmazonDynamoDBClientBuilder.standard().withEndpointConfiguration(
					new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2")).build();
		}
		if (dynamoDB == null) {
			dynamoDB = new DynamoDB(client);
		}
	}
	
	public void reset(String tableName)  {
		deleteTable();
		createTable();
	}
	
	public void createTable() {
		ListTablesResult listTables = client.listTables();
		boolean containsTable = listTables.getTableNames().stream().filter(item -> item.contentEquals(NEWS)).findFirst()
				.isPresent();
		if (!containsTable) {
			Table table = dynamoDB.createTable(NEWS, Arrays.asList(new KeySchemaElement("id", KeyType.HASH)),
					Arrays.asList(new AttributeDefinition("id", ScalarAttributeType.S)),
					new ProvisionedThroughput(1L, 1L));
			try {
				table.waitForActive();
				System.out.println("Sucesso.  Status da tebela: " + table.getDescription().getTableStatus());
			} catch (InterruptedException e) {
				e.printStackTrace();
				System.err.println("Erro ao criar tabela: " + table.getDescription().getTableStatus());
			}
			
		}
	}

	private void deleteTable() {
		ListTablesResult listTables = client.listTables();
		boolean containsTable = listTables.getTableNames().stream().filter(item -> item.contentEquals(NEWS)).findFirst()
				.isPresent();
		if (containsTable) {
			client.deleteTable(NEWS);
		}
	}
	
	public Item save(News news) {
		Table table = dynamoDB.getTable(NEWS);
		Item item = News.getItem(news);
		PutItemOutcome putItem = table.putItem(item);
		Item itemSaved = putItem.getItem();
		System.out.println("Notícia "+ news.getId() + " salva no banco.");
		return itemSaved;
	}

	public Item get(String id) {
		Table table = dynamoDB.getTable(NEWS);
		Item item = table.getItem("id", id);
		System.out.println(item);
		return item;

	}

	public List<News> getAll() {
		List<News> items = new ArrayList<News>();
		ScanRequest scanRequest = new ScanRequest().withTableName(NEWS);
		ScanResult result = client.scan(scanRequest);
		for (Map<String, AttributeValue> item : result.getItems()) {
			items.add(News.itemToNews(item));
		}
		return items;
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

	public void removeAll() {
		dynamoDB.getTable(NEWS).delete();
	}

}
