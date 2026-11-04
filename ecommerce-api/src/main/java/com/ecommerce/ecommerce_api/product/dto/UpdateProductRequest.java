package com.ecommerce.ecommerce_api.product.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class UpdateProductRequest {

    @NotBlank
    @Size(max = 150, message = "Product name cannot exceed 150 characters")
    private String name;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters\"")
    private String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Stock quantity is required")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private Integer stockQuantity;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }
}
