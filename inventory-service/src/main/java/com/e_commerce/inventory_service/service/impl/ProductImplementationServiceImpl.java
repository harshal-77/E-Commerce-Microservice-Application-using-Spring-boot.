package com.e_commerce.inventory_service.service.impl;

import com.e_commerce.inventory_service.model.Inventory;
import com.e_commerce.inventory_service.model.ProductCreatedEvent;
import com.e_commerce.inventory_service.repository.InventoryRepository;
import com.e_commerce.inventory_service.service.ProductImplementationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImplementationServiceImpl implements ProductImplementationService {

    private final InventoryRepository inventoryRepository;


    @Override
    @KafkaListener(topics = "${kafka.topic.name}", groupId = "product-processor-group")
    public void processProduct(ProductCreatedEvent event) {

        if (inventoryRepository.existsByProductId(event.getProductId())) {
            return; // idempotency
        }

        Inventory inventory = Inventory.builder()
                .productId(event.getProductId())
                .quantityAvailable(0)
                .build();

        inventoryRepository.save(inventory);
    }
}
