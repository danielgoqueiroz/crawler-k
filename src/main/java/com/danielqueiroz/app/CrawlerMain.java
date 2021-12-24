package com.danielqueiroz.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danielqueiroz.app.database.Database;
import com.danielqueiroz.app.runnable.G1Runnable;
import com.danielqueiroz.app.runnable.InfomoneyRunnable;
	
public class CrawlerMain {
	
	private static Logger logger = LoggerFactory.getLogger(CrawlerMain.class);
	
	public static void main(String[] args) {
		Database.dynamoServer();
		logger.info("Configurando banco de dados");
		Database database = new Database();
		logger.info("Criando tabela de notícias");
		database.createTable();
		
		logger.info("Inciado Crawler");
		Thread g1 = new Thread(new G1Runnable());
		Thread infomoneyRun = new Thread(new InfomoneyRunnable());
		g1.start();
		infomoneyRun.start();
	}
	
}
