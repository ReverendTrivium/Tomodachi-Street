package org.tomodachi.authetication;

import java.util.UUID;

public class TokenGenerator {
    // Default Generator
    public TokenGenerator() {
        UUID authToken = UUID.randomUUID();
        System.out.println("Generated AUTH_TOKEN: " + authToken.toString());
    }
}
