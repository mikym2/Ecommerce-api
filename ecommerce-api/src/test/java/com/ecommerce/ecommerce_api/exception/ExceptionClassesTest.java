package com.ecommerce.ecommerce_api.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionClassesTest {
    @Test
    void customExceptionsPreserveMessages() {
        assertEquals("missing", new ResourceNotFoundException("missing").getMessage());
        assertEquals("duplicate", new DuplicateResourceException("duplicate").getMessage());
    }

    @Test
    void globalHandlerMapsExceptions() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        ResponseEntity<ApiErrorResponse> missing =
                handler.handleResourceNotFound(new ResourceNotFoundException("missing"));
        assertEquals(404, missing.getStatusCode().value());

        ResponseEntity<ApiErrorResponse> duplicate =
                handler.handleDuplicateResource(new DuplicateResourceException("duplicate"));
        assertEquals(409, duplicate.getStatusCode().value());

        ResponseEntity<ApiErrorResponse> illegal =
                handler.handleIllegalArgument(new IllegalArgumentException("bad"));
        assertEquals(400, illegal.getStatusCode().value());
        assertEquals("bad", illegal.getBody().message());
    }

    @Test
    void apiErrorResponseStoresFields() {
        ApiErrorResponse response = new ApiErrorResponse(
                400, "Bad Request", "Validation failed", java.util.Map.of("name", "required"));
        assertEquals(400, response.status());
        assertEquals("Bad Request", response.error());
        assertEquals("required", response.details().get("name"));
    }
}
