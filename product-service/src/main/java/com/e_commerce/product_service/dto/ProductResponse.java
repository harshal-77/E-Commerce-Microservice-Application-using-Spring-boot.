package com.e_commerce.product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private String id;
    private String productId;
    private String name;
    private String category;
    private String slug;
    private BigDecimal price;
    private Boolean active;
}

