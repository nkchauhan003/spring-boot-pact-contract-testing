package com.cb;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.StateChangeAction;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This class contains tests for the Inventory Provider using Pact to verify interactions with the Order Consumer.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("InventoryProvider")
@PactBroker(url = "https://org-cb.pactflow.io", authentication = @PactBrokerAuth(token = "k-XpYTGvrMFNJaKsCZLRWe"))
public class PactProviderTest {

    /**
     * Sets up the Pact verification context before each test.
     *
     * @param context the Pact verification context
     */
    @BeforeEach
    void setup(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", 8081));
        System.setProperty("pact.verifier.publishResults", "true");
    }

    /**
     * Verifies the interactions defined in the Pact.
     *
     * @param context the Pact verification context
     */
    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTest(PactVerificationContext context) {
        context.verifyInteraction();
    }

    /**
     * Sets up the state where inventory exists.
     */
    @State(value = "Inventory exists", action = StateChangeAction.SETUP)
    public void inventoryExists() {
        // Setup code for the state where inventory exists
    }

    /**
     * Tears down the state where inventory does not exist.
     */
    @State(value = "Inventory does not exist", action = StateChangeAction.TEARDOWN)
    public void inventoryDoesNotExist() {
        // Teardown code for the state where inventory does not exist
    }
}
