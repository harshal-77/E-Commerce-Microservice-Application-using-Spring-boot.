package com.e_commerce.product_service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;

@Configuration
public class MongoProductDbDebugConfig {
    @Bean
    CommandLineRunner mongoDbNamePrinter(MongoDatabaseFactory factory) {
        return args -> {
            System.out.println(">>> MongoDB database in use: "
                    + factory.getMongoDatabase().getName());
        };
    }
}
