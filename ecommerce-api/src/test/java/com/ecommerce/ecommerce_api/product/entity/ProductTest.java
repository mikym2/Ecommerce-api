package com.ecommerce.ecommerce_api.product.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {
    @Test
    void constructorAndLifecycleMethodsSetValues() {
        Product product = new Product("Phone", "Desc", new BigDecimal("10"), 3);

        assertEquals("Phone", product.getName());
        assertEquals("Desc", product.getDescription());
        assertEquals(new BigDecimal("10"), product.getPrice());
        assertEquals(3, product.getStockQuantity());
        assertTrue(product.getActive());

        product.setName("Tablet");
        product.setDescription("Updated");
        product.setPrice(new BigDecimal("20"));
        product.setStockQuantity(5);
        product.setActive(false);

        assertEquals("Tablet", product.getName());
        assertEquals("Updated", product.getDescription());
        assertEquals(new BigDecimal("20"), product.getPrice());
        assertEquals(5, product.getStockQuantity());
        assertFalse(product.getActive());
    }
}
