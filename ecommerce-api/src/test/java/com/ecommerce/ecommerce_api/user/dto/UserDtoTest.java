package com.ecommerce.ecommerce_api.user.dto;

import com.ecommerce.ecommerce_api.user.entity.Role;
import com.ecommerce.ecommerce_api.user.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDtoTest {
    @Test
    void loginRequestAndResponseRecordsWork() {
        LoginRequest request = new LoginRequest("alice", "password123");
        LoginResponse response = new LoginResponse("token", "Bearer", 3600);

        assertEquals("alice", request.username());
        assertEquals("password123", request.password());
        assertEquals("token", response.accessToken());
        assertEquals("Bearer", response.tokenType());
        assertEquals(3600, response.expiresIn());
    }

    @Test
    void registerResponseMapsUser() {
        User user = new User("alice", "alice@example.com", "encoded", Role.CUSTOMER);
        RegisterResponse response = RegisterResponse.formEntity(user);

        assertEquals("alice", response.username());
        assertEquals("alice@example.com", response.email());
        assertEquals(Role.CUSTOMER, response.role());
    }

    @Test
    void registerRequestStoresValues() {
        RegisterRequest request = new RegisterRequest("alice", "alice@example.com", "password123");
        assertEquals("alice", request.username());
        assertEquals("alice@example.com", request.email());
        assertEquals("password123", request.password());
    }
}
