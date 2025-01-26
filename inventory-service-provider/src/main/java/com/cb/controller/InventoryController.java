package com.cb.controller;

import com.cb.dto.InventoryRequest;
import com.cb.model.Product;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class InventoryController {

    @PostMapping("/api/inventory")
    public Product getInventory(@RequestBody InventoryRequest request) {
        return new Product(UUID.fromString(request.productId()), 100);
    }
}
