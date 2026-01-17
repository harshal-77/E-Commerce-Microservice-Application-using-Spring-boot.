package com.e_commerce.order_service.service;

public interface InventoryClient {
    boolean checkStock(String productId, int quantity);
}
