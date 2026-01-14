package com.e_commerce.inventory_service.repository;

import com.e_commerce.inventory_service.model.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface InventoryRepository extends MongoRepository<Inventory,String> {
    Optional<Inventory> findByProductId(String productId);

    boolean existsByProductId(String productId);
}

