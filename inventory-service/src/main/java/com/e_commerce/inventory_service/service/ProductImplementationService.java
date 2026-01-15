package com.e_commerce.inventory_service.service;

import com.e_commerce.inventory_service.model.ProductCreatedEvent;

public interface ProductImplementationService {
    void processProduct(ProductCreatedEvent product);
}
