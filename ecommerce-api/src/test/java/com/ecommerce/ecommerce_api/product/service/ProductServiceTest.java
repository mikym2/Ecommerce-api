package com.ecommerce.ecommerce_api.product.service;

import com.ecommerce.ecommerce_api.exception.ResourceNotFoundException;
import com.ecommerce.ecommerce_api.product.dto.CreateProductRequestt;
import com.ecommerce.ecommerce_api.product.dto.ProductPageResponse;
import com.ecommerce.ecommerce_api.product.dto.ProductResponse;
import com.ecommerce.ecommerce_api.product.dto.UpdateProductRequest;
import com.ecommerce.ecommerce_api.product.entity.Product;
import com.ecommerce.ecommerce_api.product.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Test
    void getProductsReturnsPageResponse() {
        Product product = new Product("Phone", "Smart phone", new BigDecimal("499.99"), 10);
        when(repository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(product)));

        ProductPageResponse response = new ProductService(repository)
                .getProducts(0, 20, "name", "asc", "phone",
                        new BigDecimal("100"), new BigDecimal("500"));

        assertEquals(1, response.content().size());
        assertEquals("Phone", response.content().get(0).name());
    }

    @Test
    void getProductsValidatesArguments() {
        ProductService service = new ProductService(repository);
        assertThrows(IllegalArgumentException.class,
                () -> service.getProducts(-1, 20, "name", "asc", null, null, null));
        assertThrows(IllegalArgumentException.class,
                () -> service.getProducts(0, 101, "name", "asc", null, null, null));
        assertThrows(IllegalArgumentException.class,
                () -> service.getProducts(0, 20, "invalid", "asc", null, null, null));
        assertThrows(IllegalArgumentException.class,
                () -> service.getProducts(0, 20, "name", "asc", null,
                        new BigDecimal("-1"), null));
    }

    @Test
    void createProductSavesAndReturnsResponse() {
        CreateProductRequestt request = mock(CreateProductRequestt.class);
        when(request.getName()).thenReturn("Phone");
        when(request.getDescription()).thenReturn("Smart phone");
        when(request.getPrice()).thenReturn(new BigDecimal("499.99"));
        when(request.getStockQuantity()).thenReturn(10);

        ProductResponse response = new ProductService(repository).createProduct(request);

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(repository).save(captor.capture());
        assertEquals("Phone", captor.getValue().getName());
        assertEquals("Phone", response.name());
    }

    @Test
    void updateProductUpdatesExistingProduct() {
        Product product = new Product("Old", "Old", new BigDecimal("10"), 1);
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        UpdateProductRequest request = mock(UpdateProductRequest.class);
        when(request.getName()).thenReturn("  New  ");
        when(request.getDescription()).thenReturn("Updated");
        when(request.getPrice()).thenReturn(new BigDecimal("20"));
        when(request.getStockQuantity()).thenReturn(5);

        ProductResponse response = new ProductService(repository).updateProduct(1L, request);

        assertEquals("New", product.getName());
        assertEquals("Updated", response.description());
        verify(repository).save(product);
    }

    @Test
    void deleteProductMarksInactive() {
        Product product = new Product("Phone", "Desc", new BigDecimal("10"), 1);
        when(repository.findById(1L)).thenReturn(Optional.of(product));

        new ProductService(repository).deleteProduct(1L);

        assertFalse(product.getActive());
        verify(repository).save(product);
    }

    @Test
    void getProductIdRejectsMissingAndInactiveProducts() {
        ProductService service = new ProductService(repository);
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> service.getProductId(1L));

        Product inactive = new Product("Phone", "Desc", new BigDecimal("10"), 1);
        inactive.setActive(false);
        when(repository.findById(2L)).thenReturn(Optional.of(inactive));
        assertThrows(ResourceNotFoundException.class, () -> service.getProductId(2L));
    }
}
