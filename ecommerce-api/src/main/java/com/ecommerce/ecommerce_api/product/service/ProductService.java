package com.ecommerce.ecommerce_api.product.service;

import com.ecommerce.ecommerce_api.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce_api.product.dto.CreateProductRequestt;
import com.ecommerce.ecommerce_api.product.dto.ProductResponse;
import com.ecommerce.ecommerce_api.product.dto.UpdateProductRequest;
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

    private final ProductRepository repository;

    public ProductService(ProductRepository repository){
        this.repository = repository;
    }

    public Page<ProductResponse> getProducts(
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
        Page<Product> products = repository.findAll(specification, pageable);

        //convert each product in the Page to a productResponse
        return products.map(ProductResponse::fromEntity);

    }


    public ProductResponse createProduct(CreateProductRequestt request){

        Product product = new Product(
                request.getName(),
                request.getDescription(),
                request.getPrice(),
                request.getStockQuantity()
        );

         repository.save(product);

         return  ProductResponse.fromEntity(product);
    }

    public ProductResponse updateProduct(Long id, UpdateProductRequest request){
        //find the product
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " +id));

        //update its fields
        product.setName(request.getName().trim());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStockQuantity(request.getStockQuantity());

        repository.save(product);
        return ProductResponse.fromEntity(product);
    }

    public ProductResponse deleteProduct(Long id){
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        product.setActive(false);

        repository.save(product);
        return ProductResponse.fromEntity(product);
    }

    public ProductResponse getProductId(Long id){
        Product product = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        if(!product.getActive()){
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }else{
            return ProductResponse.fromEntity(product);
        }




    }
}
