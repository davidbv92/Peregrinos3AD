package com.luisdbb.tarea3AD2024base.mongoDB;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

@Configuration
public class DataConnectionMongoDB {

	@Value("${spring.data.mongodb.uri}")
	private String uri;

	@Value("${spring.data.mongodb.database}")
	private String bd;
	
	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create(uri);
	}
	
	@Bean
	public MongoDatabase mongoDatabase(MongoClient mongoClient) {
		return mongoClient.getDatabase(bd);
	}

}
