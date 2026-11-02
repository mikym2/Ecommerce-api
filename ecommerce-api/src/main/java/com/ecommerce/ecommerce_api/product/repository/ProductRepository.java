package com.ecommerce.ecommerce_api.product.repository;

import com.ecommerce.ecommerce_api.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long>,
        JpaSpecificationExecutor<Product> {

}
