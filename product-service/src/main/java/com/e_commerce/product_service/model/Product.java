package com.e_commerce.product_service.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document(collection = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@CompoundIndex(
        name = "unique_name_category",
        def = "{'name': 1, 'category': 1}",
        unique = true
)
public class Product {

    @Id
    private String id; // Mongo ObjectId

    @Indexed(unique = true)
    private String productCode; // MOB-001, LAP-002

    private String name;

    private String category; // MOBILE, LAPTOP, etc.

    @Indexed(unique = true)
    private String slug; // mobile-phone-apple

    private String description;
    private BigDecimal price;
    private Boolean active = true;
}
