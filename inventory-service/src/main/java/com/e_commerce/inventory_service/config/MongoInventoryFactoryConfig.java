package com.e_commerce.product_service.config;

import com.mongodb.client.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoProductFactoryConfig {
    @Bean
    MongoDatabaseFactory mongoDatabaseFactory(
            MongoClient mongoClient,
            Environment env
    ) {
        String database = env.getProperty("spring.data.mongodb.database");

        if (database == null) {
            String uri = env.getProperty("spring.data.mongodb.uri");
            database = uri.substring(uri.lastIndexOf("/") + 1);
        }

        return new SimpleMongoClientDatabaseFactory(mongoClient, database);
    }
}