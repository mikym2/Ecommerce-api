package com.ecommerce.ecommerce_api.product.service;

import com.ecommerce.ecommerce_api.product.entity.Product;
import com.ecommerce.ecommerce_api.product.repository.ProductRepository;
import com.ecommerce.ecommerce_api.product.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public ProductService(ProductRepository repository){
        this.repository = repository;
    }

    public Page<Product> getProducts(
            int page,
            int size,
            String sortBy,
            String direction,
            String name,
            BigDecimal minPrice,
            BigDecimal maxPrice
    ){
        if(page<0){
            throw new IllegalArgumentException(
                    "Page number cannot be negative"
            );
        }
        if(size<1 || size > 100){
            throw  new IllegalArgumentException(
                    "Page size must be between 1 and 100"
            );
        }
        if(minPrice!=null && minPrice.signum()<0){
            throw new IllegalArgumentException(
                    "Minimum price cannot be negative"
            );
        }
        if(maxPrice!=null && maxPrice.signum()<0){
            throw  new IllegalArgumentException(
                    "Max price cannot be negative"
            );
        }
        if(minPrice!=null && maxPrice!=null && minPrice.compareTo(maxPrice)>0){
            throw new IllegalArgumentException(
                    "Max price cannot be smaller than min price"
            );
        }

        if(!"name".equals(sortBy) && !"price".equals(sortBy) && !"createdAt".equals(sortBy)){
            throw new IllegalArgumentException("Invalid sort field");
        }


        Sort.Direction sortDirection =
                "desc".equalsIgnoreCase(direction)
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(sortDirection, sortBy)
        );


       //Build dynamic filtering conditions
        Specification specification = ProductSpecification.filterProducts(
                name,
                minPrice,
                maxPrice
        );

        //Execute query
        return repository.findAll(specification, pageable);

    }
}
