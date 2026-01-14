package com.e_commerce.inventory_service.service;

import com.e_commerce.inventory_service.dto.InventoryResponse;

public interface InventoryService {

    InventoryResponse getStock(String productId);

    InventoryResponse addStock(String productId, int quantity);

    InventoryResponse reduceStock(String productId, int quantity);

    boolean isStockAvailable(String productId, int quantity);
}