package com.e_commerce.product_service.utility;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProductCodeGenerator {

    public String generateCode(String name, String category) {
        return category.substring(0, 3).toUpperCase()
                + "-" + name.replaceAll("\\s+", "").toUpperCase()
                + "-" + UUID.randomUUID().toString().substring(0, 4);
    }

    public String generateSlug(String name, String category) {
        return (category + "-" + name)
                .toLowerCase()
                .replaceAll("[^a-z0-9]+", "-");
    }
}

