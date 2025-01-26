package com.cb.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InventoryClient {

    private final RestTemplate restTemplate;
    private static final String INVENTORY_API_URL = "http://localhost:8081/api/inventory";

    public InventoryClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public Product getProduct(String productId) {

        InventoryRequest request = new InventoryRequest(productId);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        HttpEntity<InventoryRequest> entity = new HttpEntity<>(request, headers);
        ResponseEntity<Product> response = restTemplate.exchange(INVENTORY_API_URL, HttpMethod.POST, entity, Product.class);
        return response.getBody();
    }
}