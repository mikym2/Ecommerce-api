package com.ecommerce.ecommerce_api.product.service;

import com.ecommerce.ecommerce_api.product.entity.Product;
import com.ecommerce.ecommerce_api.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        if(page<1 || page > 100){
            throw  new IllegalArgumentException(
                    "Page size must be bewteen 1 and 100"
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
        if(minPrice!=null && maxPrice!=null & minPrice.compareTo(maxPrice)>0){
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

        String searchName =
                name == null ? "" : name.trim();

        if(minPrice!=null && maxPrice!=null){
            return repository
                    .findByActiveTrueAndPriceBetween(
                            minPrice,
                            maxPrice,
                            pageable
                    );
        }

        if(minPrice!=null){
            return repository.findByActiveTrueAndPriceGreaterThanEqual(
                    minPrice,
                    pageable
            );
        }

        if(maxPrice!=null){
            return repository
                    .findByActiveTrueAndPriceLessThanEqual(
                            maxPrice,
                            pageable
                    );
        }

        if(!searchName.isEmpty()){
            return repository
                    .findByNameContainingIgnoreCase(
                            searchName,
                            pageable
                    );
        }

        return repository.findByActiveTrue(pageable);

    }
}
