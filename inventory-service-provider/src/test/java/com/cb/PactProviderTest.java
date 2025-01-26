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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Provider("InventoryProvider")
@PactBroker(url = "https://org-cb.pactflow.io", authentication = @PactBrokerAuth(token = "j-ZoHEwkZDBUOuBjeQOQDg"))
public class PactProviderTest {

    @BeforeEach
    void setup(PactVerificationContext context) {
        context.setTarget(new HttpTestTarget("localhost", 8081));
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTest(PactVerificationContext context) {
        context.verifyInteraction();
    }

    @State(value = "Inventory exists", action = StateChangeAction.SETUP)
    public void inventoryExists() {

    }

    @State(value = "Inventory does not exist", action = StateChangeAction.TEARDOWN)
    public void inventoryDoesNotExist() {

    }
}
