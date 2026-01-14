package com.e_commerce.product_service.service.impl;

import com.e_commerce.product_service.dto.CreateProductRequest;
import com.e_commerce.product_service.dto.ProductResponse;
import com.e_commerce.product_service.model.Product;
import com.e_commerce.product_service.repository.ProductRepository;
import com.e_commerce.product_service.service.ProductService;
import com.e_commerce.product_service.utility.ProductCodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCodeGenerator productCodeGenerator;

    @Override
    public ProductResponse createProduct(CreateProductRequest request) {

        String slug = productCodeGenerator.generateSlug(
                request.getName(),
                request.getCategory()
        );

        if (productRepository.existsBySlug(slug)) {
            throw new RuntimeException("Product already exists in this category");
        }

        Product product = new Product();
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setSlug(slug);
        product.setProductCode(
                productCodeGenerator.generateCode(
                        request.getName(),
                        request.getCategory()
                )
        );
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setActive(true);

        return mapToResponse(productRepository.save(product));
    }

    @Override
    public ProductResponse getProductById(String productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        return mapToResponse(product);
    }

    @Override
    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ProductResponse updateProduct(String productId, CreateProductRequest request) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product updatedProduct = productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(String productId) {

        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found with id: " + productId);
        }

        productRepository.deleteById(productId);
    }

    private ProductResponse mapToResponse(Product product) {

        return new ProductResponse(
                product.getId(),
                product.getProductCode(),
                product.getName(),
                product.getCategory(),
                product.getSlug(),
                product.getPrice(),
                product.getStock(),
                product.getActive()
        );
    }

}