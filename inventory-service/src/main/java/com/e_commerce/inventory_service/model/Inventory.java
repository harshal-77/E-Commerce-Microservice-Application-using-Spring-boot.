package com.e_commerce.inventory_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "inventory")
public class Inventory {

    @Id
    private String id;          // Mongo ObjectId

    private String productId;   // Mongo ProductService product ID

    private Integer quantityAvailable;

    private LocalDateTime lastUpdated;

    public void updateQuantity(int quantityChange) {
        if (this.quantityAvailable == null) this.quantityAvailable = 0;
        this.quantityAvailable += quantityChange;
        this.lastUpdated = LocalDateTime.now();
    }
}
