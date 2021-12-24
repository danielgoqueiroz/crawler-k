package com.danielqueiroz.app;


import com.danielqueiroz.app.database.Database;
import com.danielqueiroz.app.runnable.G1Runnable;
import com.danielqueiroz.app.runnable.InfomoneyRunnable;
	
public class CrawlerMain {
	
	public static void main(String[] args) {
		Database.dynamoServer();
		System.out.println("Configurando banco de dados");
		Database database = new Database();
		System.out.println("Criando tabela de notícias");
		database.createTable();
		
		System.out.println("Inciado Crawler");
		Thread g1 = new Thread(new G1Runnable());
		Thread infomoneyRun = new Thread(new InfomoneyRunnable());
		g1.start();
		infomoneyRun.start();
	}
	
}
