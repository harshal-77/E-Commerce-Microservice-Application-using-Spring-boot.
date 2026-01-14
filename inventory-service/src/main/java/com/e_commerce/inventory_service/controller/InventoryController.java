package com.e_commerce.inventory_service.controller;

import com.e_commerce.inventory_service.dto.InventoryRequest;
import com.e_commerce.inventory_service.dto.InventoryResponse;
import com.e_commerce.inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponse> getStock(@PathVariable String productId) {
        return ResponseEntity.ok(inventoryService.getStock(productId));
    }

    @PostMapping("/add")
    public ResponseEntity<InventoryResponse> addStock(@Valid @RequestBody InventoryRequest request) {
        return ResponseEntity.ok(inventoryService.addStock(request.getProductId(), request.getQuantity()));
    }

    @PostMapping("/reduce")
    public ResponseEntity<InventoryResponse> reduceStock(@Valid @RequestBody InventoryRequest request) {
        return ResponseEntity.ok(inventoryService.reduceStock(request.getProductId(), request.getQuantity()));
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> isStockAvailable(@RequestParam String productId,
                                                    @RequestParam int quantity) {
        return ResponseEntity.ok(inventoryService.isStockAvailable(productId, quantity));
    }
}
