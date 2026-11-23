package com.ecommerce.ecommerce_api.product.controller;

import com.ecommerce.ecommerce_api.product.dto.*;
import com.ecommerce.ecommerce_api.product.service.ProductService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class ProductControllerTest {
    @Test
    void delegatesAllOperations() {
        ProductService service = mock(ProductService.class);
        ProductController controller = new ProductController(service);
        CreateProductRequestt create = mock(CreateProductRequestt.class);
        UpdateProductRequest update = mock(UpdateProductRequest.class);
        ProductResponse response = new ProductResponse(1L, "Phone", "Desc",
                new BigDecimal("10"), 5, null, null);
        ProductPageResponse page = new ProductPageResponse(List.of(response), 0, 20, 1, 1, true, true);

        when(service.getProducts(0, 20, "name", "asc", "p",
                new BigDecimal("1"), new BigDecimal("20"))).thenReturn(page);
        when(service.createProduct(create)).thenReturn(response);
        when(service.updateProduct(1L, update)).thenReturn(response);
        when(service.deleteProduct(1L)).thenReturn(response);
        when(service.getProductId(1L)).thenReturn(response);

        assertSame(page, controller.getProducts(0, 20, "name", "asc", "p",
                new BigDecimal("1"), new BigDecimal("20")));
        assertSame(response, controller.createProduct(create));
        assertSame(response, controller.updateProduct(1L, update));
        assertSame(response, controller.deleteProduct(1L));
        assertSame(response, controller.getProduct(1L));
    }
}
