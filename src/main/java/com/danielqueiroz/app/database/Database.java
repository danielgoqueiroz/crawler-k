package com.danielqueiroz.app.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.danielqueiroz.app.model.News;
import com.danielqueiroz.app.model.NewsUnknow;
import com.google.gson.Gson;

public class Database {

	private static Logger logger = LoggerFactory.getLogger(Database.class);

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
	
	public static void dynamoServer() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("docker", "run", "-p", "8000:8000", "amazon/dynamodb-local");

		try {

			Process process = processBuilder.start();

			StringBuilder output = new StringBuilder();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

			int exitVal = process.waitFor();
			if (exitVal == 0) {
				System.out.println("Iniciando banco dynamoDB!");
				System.out.println(output);
				System.exit(0);
			} else {
				// abnormal...
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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
				logger.info("Sucesso.  Status da tebela: " + table.getDescription().getTableStatus());
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
	
	public Item save(NewsUnknow newsUnknow) {
		Table table = dynamoDB.getTable(NEWS);
		Item item = NewsUnknow.getItem(newsUnknow);
		PutItemOutcome putItem = table.putItem(item);
		Item itemSaved = putItem.getItem();
		logger.info("Notícia sem conteúdo"+ newsUnknow.getId() + " salva no banco.");
		return itemSaved;
	}
	
	public Item save(News news) {
		Table table = dynamoDB.getTable(NEWS);
		Item item = News.getItem(news);
		PutItemOutcome putItem = table.putItem(item);
		Item itemSaved = putItem.getItem();
		logger.info("Notícia "+ news.getId() + " salva no banco.");
		return itemSaved;
	}
	
	public boolean exist(String id) {
			Table table = dynamoDB.getTable(NEWS);
			Item item = table.getItem("id", id);
			return item != null;
	}

	public Item get(String id) {
		Table table = dynamoDB.getTable(NEWS);
		Item item = table.getItem("id", id);
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
			logger.info("Notícia já salva");
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
