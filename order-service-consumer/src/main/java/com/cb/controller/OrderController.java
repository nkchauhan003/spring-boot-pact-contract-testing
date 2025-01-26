package com.cb.controller;

import com.cb.client.InventoryClient;
import com.cb.client.Product;
import com.cb.model.Item;
import com.cb.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private InventoryClient inventoryClient;

    @GetMapping("/{orderId}")
    public Order getOrder(@PathVariable String orderId) {
        Product product = inventoryClient.getProduct(UUID.randomUUID().toString());
        Item item = new Item(product, 99.99);
        List<Item> items = List.of(item);
        return new Order(orderId, items);
    }
}