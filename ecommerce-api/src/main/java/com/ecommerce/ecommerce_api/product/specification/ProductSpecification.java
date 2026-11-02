package com.ecommerce.ecommerce_api.product.specification;

import com.ecommerce.ecommerce_api.product.entity.Product;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.Locale;

public class ProductSpecification {

    //Specification is a funcational interface with one method named toPredicate that is being implemented in the below lambda
    public static Specification<Product> filterProducts(
            String name,
            BigDecimal minPrice,
            BigDecimal maxPrice
    ){

        /**
         * predicate represents the conditions being combined.
         * criteriaBuilder.and() combines conditions using SQL AND.
         * criteriaBuilder.lower() makes the name search case-insensitive.
         * **/

        //query – The query being constructed
        //root  the starting point of your database query to query the entity
        //criteriaBuilder – The tool that creates SQL conditions
        return (root, query, criteriaBuilder) -> {

            //Always exclude inactive products
            Predicate predicate = criteriaBuilder.isTrue(
                    root.get("active")
            );

            // Fillter by product name
            if(name!=null && !name.isBlank()){

                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.like(
                                criteriaBuilder.lower(root.get("name")),
                                "%" + name.trim().toLowerCase() + "%"
                        )
                );
            }

            //Filter by minimum price
            if(minPrice!=null){
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.greaterThanOrEqualTo(
                                root.get("price"),
                                minPrice
                        )
                );
            }

            //Filter by max price
            if(maxPrice!=null){
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.lessThanOrEqualTo(
                                root.get("price"),
                                maxPrice
                        )
                );
            }

            return predicate;
        };
    }
}
