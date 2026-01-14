package com.e_commerce.inventory_service.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class InventoryRequest {

    @NotBlank
    private String productId;

    @Min(0)
    private Integer quantity;
}