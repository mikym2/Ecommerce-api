package com.ecommerce.ecommerce_api.user.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void constructorAndSettersWork() {
        User user = new User("alice", "alice@example.com", "encoded", Role.CUSTOMER);

        assertEquals("alice", user.getUsername());
        assertEquals("alice@example.com", user.getEmail());
        assertEquals("encoded", user.getPassword());
        assertEquals(Role.CUSTOMER, user.getRole());

        user.setUsername("bob");
        user.setEmail("bob@example.com");
        user.setPassword("new");
        user.setRole(Role.ADMIN);

        assertEquals("bob", user.getUsername());
        assertEquals("bob@example.com", user.getEmail());
        assertEquals("new", user.getPassword());
        assertEquals(Role.ADMIN, user.getRole());
    }
}
