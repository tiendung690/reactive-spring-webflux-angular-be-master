package org.edu.kushima.config;

import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

@EnableReactiveMongoRepositories
public class MongoDbReactiveConfig extends AbstractReactiveMongoConfiguration {
 
	@Override
	public MongoClient reactiveMongoClient() {
		return MongoClients.create();
	}
 
	@Override
	protected String getDatabaseName() {
		return "webflux_angular";
	}
 
}