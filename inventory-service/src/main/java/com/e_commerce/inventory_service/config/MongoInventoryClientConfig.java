package com.e_commerce.product_service.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class MongoProductClientConfig {
    @Bean
    MongoClient mongoClient(Environment env) {
        String uri = env.getProperty("spring.data.mongodb.uri");
        return MongoClients.create(uri);
    }
}