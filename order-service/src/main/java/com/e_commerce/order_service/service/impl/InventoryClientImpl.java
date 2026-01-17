package com.e_commerce.order_service.service.impl;

import com.e_commerce.order_service.service.InventoryClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryClientImpl implements InventoryClient {

    private final WebClient inventoryServiceWebClient;

    public boolean checkStock(String productId, int quantity) {

        Boolean response = inventoryServiceWebClient
                .get()
                .uri("/api/inventory/check?productId={productId}&quantity={quantity}",
                        productId, quantity)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

        return Boolean.TRUE.equals(response);
    }
}
