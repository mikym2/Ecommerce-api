package com.ecommerce.ecommerce_api.product.specification;

import com.ecommerce.ecommerce_api.product.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductSpecificationTest {
    @Test
    void buildsSpecificationWithFilters() {
        Specification<Product> specification = ProductSpecification.filterProducts(
                " phone ", new BigDecimal("100"), new BigDecimal("500"));

        assertNotNull(specification);
    }

    @Test
    void buildsSpecificationWithoutOptionalFilters() {
        assertNotNull(ProductSpecification.filterProducts(null, null, null));
    }
}
