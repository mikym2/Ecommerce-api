package com.ecommerce.ecommerce_api.config;

import com.ecommerce.ecommerce_api.user.config.PasswordConfig;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

class ConfigClassesTest {
    @Test
    void passwordConfigCreatesWorkingEncoder() {
        PasswordEncoder encoder = new PasswordConfig().passwordEncoder();

        String encoded = encoder.encode("password123");

        assertNotEquals("password123", encoded);
        assertTrue(encoder.matches("password123", encoded));
    }

    @Test
    void redisCacheNamesAreDefined() {
        assertEquals("products", RedisCacheNames.PRODUCTS);
        assertEquals("productById", RedisCacheNames.PRODUCT_BY_ID);
    }
}
