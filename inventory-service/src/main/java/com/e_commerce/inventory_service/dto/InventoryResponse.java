package com.e_commerce.inventory_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InventoryResponse {

    private String productId;
    private Integer quantityAvailable;
}