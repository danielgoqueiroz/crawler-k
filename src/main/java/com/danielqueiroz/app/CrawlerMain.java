package com.danielqueiroz.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danielqueiroz.app.runnable.G1Runnable;
import com.danielqueiroz.app.runnable.InfomoneyRunnable;

	
public class CrawlerMain {
	
	private static Logger logger = LoggerFactory.getLogger(CrawlerMain.class);
	
	public static void main(String[] args) {
		DynamoServer();
		logger.info("Inciado Crawler");
		Thread g1 = new Thread(new G1Runnable());
		Thread infomoneyRun = new Thread(new InfomoneyRunnable());
		g1.start();
		infomoneyRun.start();
	}
	
	public static void DynamoServer() {
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
}
