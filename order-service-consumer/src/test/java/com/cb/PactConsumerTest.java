package com.cb;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.V4Pact;
import au.com.dius.pact.core.model.annotations.Pact;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * This class contains tests for the Order Consumer using Pact to verify interactions with the Inventory Provider.
 */
@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "InventoryProvider")
public class PactConsumerTest {

    /**
     * The expected response from the Inventory Provider.
     */
    String expectedResponse = """
            {
              "productId": "b52833df-eaf7-4efc-8a96-ee8534a4e24a",
              "quantity": 100
            }
            """;

    /**
     * Defines the Pact between the Order Consumer and the Inventory Provider.
     *
     * @param builder the Pact DSL builder
     * @return the Pact
     */
    @Pact(consumer = "OrderConsumer")
    public V4Pact getInventoryById(PactDslWithProvider builder) {
        return builder
                .given("Inventory exists")
                .uponReceiving("A request for inventory")
                .path("/api/inventory")
                .method("POST")
                .body("""
                        {
                          "productId": "b52833df-eaf7-4efc-8a96-ee8534a4e24a"
                        }
                        """)
                .willRespondWith()
                .body(expectedResponse)
                .status(200)
                .toPact(V4Pact.class);
    }

    /**
     * Tests the interaction defined in the Pact.
     *
     * @param mockServer the mock server provided by Pact
     */
    @Test
    @PactTestFor(pactMethod = "getInventoryById", port = "8081")
    public void testById(MockServer mockServer) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("""
                {
                  "productId": "b52833df-eaf7-4efc-8a96-ee8534a4e24a"
                }
                """, headers);
        ResponseEntity<String> response = new RestTemplate()
                .postForEntity(mockServer.getUrl() + "/api/inventory", request, String.class);
        assert response.getStatusCodeValue() == 200;
        assert response.getBody().equals(expectedResponse);
    }
}