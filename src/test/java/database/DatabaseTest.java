package database;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazonaws.services.dynamodbv2.document.Item;

import Utils.TestUtils;
import model.News;

public class DatabaseTest {

	private static Database database;

	@BeforeEach
	void before() {
		TestUtils.DynamoServer();
		database = new Database();
		database.reset("news");
	}
	
	@Test
	void deveSalvarNews() {
		int sizeExpected = database.getAll().size() + 1;
		News news = TestUtils.getNews();
		database.save(news);
		Item item = database.get(news.getId());
		News newsSaved = News.itemToNews(item);
		assertNotNull(newsSaved);
		int size = database.getAll().size();
		assertEquals(sizeExpected, size);
	}
	
	@Test
	void deveRetornarNewsSalva() {
		News news = TestUtils.getNews();
		database.save(news);
		Item item = database.get(news.getId());
		News newsSaved = News.itemToNews(item);
		assertNotNull(newsSaved);
		List<News> items = database.getAll();
		assertEquals(1, items.size());
	}
	
}
