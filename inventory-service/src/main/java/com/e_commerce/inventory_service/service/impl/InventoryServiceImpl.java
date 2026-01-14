package com.e_commerce.inventory_service.service.impl;

import com.e_commerce.inventory_service.dto.InventoryResponse;
import com.e_commerce.inventory_service.model.Inventory;
import com.e_commerce.inventory_service.repository.InventoryRepository;
import com.e_commerce.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Override
    public InventoryResponse getStock(String productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for product: " + productId));

        return new InventoryResponse(inventory.getProductId(), inventory.getQuantityAvailable());
    }

    @Override
    public InventoryResponse addStock(String productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElse(new Inventory(null, productId, 0, null));

        inventory.updateQuantity(quantity);
        inventoryRepository.save(inventory);

        return new InventoryResponse(inventory.getProductId(), inventory.getQuantityAvailable());
    }

    @Override
    public InventoryResponse reduceStock(String productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for product: " + productId));

        if (inventory.getQuantityAvailable() < quantity) {
            throw new RuntimeException("Insufficient stock for product: " + productId);
        }

        inventory.updateQuantity(-quantity);
        inventoryRepository.save(inventory);

        return new InventoryResponse(inventory.getProductId(), inventory.getQuantityAvailable());
    }

    @Override
    public boolean isStockAvailable(String productId, int quantity) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for product: " + productId));

        return inventory.getQuantityAvailable() >= quantity;
    }
}
