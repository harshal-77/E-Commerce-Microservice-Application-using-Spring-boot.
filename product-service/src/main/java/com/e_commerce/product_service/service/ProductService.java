package com.e_commerce.product_service.service;

import com.e_commerce.product_service.dto.CreateProductRequest;
import com.e_commerce.product_service.dto.ProductResponse;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(CreateProductRequest request);

    ProductResponse getProductById(String productId);

    List<ProductResponse> getAllProducts();

    ProductResponse updateProduct(String productId, CreateProductRequest request);

    void deleteProduct(String productId);
}
