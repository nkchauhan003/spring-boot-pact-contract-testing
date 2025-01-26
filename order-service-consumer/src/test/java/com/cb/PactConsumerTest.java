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

@SpringBootTest
@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "InventoryProvider")
public class PactConsumerTest {

    String expectedResponse = """
            {
              "productId": "b52833df-eaf7-4efc-8a96-ee8534a4e24a",
              "quantity": 100
            }
            """;

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
