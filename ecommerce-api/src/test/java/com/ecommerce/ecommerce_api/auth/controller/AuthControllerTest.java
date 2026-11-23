package com.ecommerce.ecommerce_api.auth.controller;

import com.ecommerce.ecommerce_api.user.dto.RegisterRequest;
import com.ecommerce.ecommerce_api.user.dto.RegisterResponse;
import com.ecommerce.ecommerce_api.user.entity.Role;
import com.ecommerce.ecommerce_api.user.entity.User;
import com.ecommerce.ecommerce_api.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {
    @Test
    void registerReturnsCreatedResponse() {
        UserService service = mock(UserService.class);
        RegisterRequest request = new RegisterRequest("alice", "alice@example.com", "password123");
        User user = new User("alice", "alice@example.com", "encoded", Role.CUSTOMER);
        when(service.register(request)).thenReturn(user);

        var response = new AuthController(service).register(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("alice", response.getBody().username());
    }
}
