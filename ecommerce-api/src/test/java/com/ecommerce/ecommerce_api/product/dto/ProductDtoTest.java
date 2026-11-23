package com.ecommerce.ecommerce_api.product.dto;

import com.ecommerce.ecommerce_api.product.entity.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductDtoTest {
    @Test
    void productResponseMapsEntity() {
        Product product = new Product("Phone", "Desc", new BigDecimal("10"), 3);

        ProductResponse response = ProductResponse.fromEntity(product);

        assertEquals("Phone", response.name());
        assertEquals("Desc", response.description());
        assertEquals(new BigDecimal("10"), response.price());
        assertEquals(3, response.stockQuantity());
    }

    @Test
    void pageResponseStoresValues() {
        ProductResponse item = new ProductResponse(1L, "Phone", "Desc",
                new BigDecimal("10"), 3, Instant.now(), Instant.now());
        ProductPageResponse response = new ProductPageResponse(
                List.of(item), 0, 20, 1, 1, true, true);

        assertEquals(1, response.content().size());
        assertEquals(0, response.page());
        assertTrue(response.first());
        assertTrue(response.last());
    }
}
