package com.ecommerce.ecommerce_api.product.repository;

import com.ecommerce.ecommerce_api.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    Page<Product> findByActiveTrue(Pageable pageable);

    Page<Product> findByNameContainingIgnoreCase(
            String name,
            Pageable pageable
    );

    Page<Product>findByActiveTrueAndPriceGreaterThanEqual(
            BigDecimal price,
            Pageable pageable
    );

    Page<Product> findByActiveTrueAndPriceLessThanEqual(
            BigDecimal price,
            Pageable pageable
    );

    Page<Product> findByActiveTrueAndPriceBetween(
            BigDecimal minPrice,
            BigDecimal maxPrice,
            Pageable pageable
    );
}
