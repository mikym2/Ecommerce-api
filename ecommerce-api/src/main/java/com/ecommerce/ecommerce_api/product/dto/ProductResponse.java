package com.ecommerce.ecommerce_api.product.dto;

import com.ecommerce.ecommerce_api.product.entity.Product;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

public record ProductResponse(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        Instant createdAt,
        Instant updatedAt
) {

    public static ProductResponse fromEntity(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
