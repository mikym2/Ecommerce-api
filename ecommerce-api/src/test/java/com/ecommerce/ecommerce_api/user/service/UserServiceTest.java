package com.ecommerce.ecommerce_api.user.service;

import com.ecommerce.ecommerce_api.exception.DuplicateResourceException;
import com.ecommerce.ecommerce_api.user.dto.RegisterRequest;
import com.ecommerce.ecommerce_api.user.entity.Role;
import com.ecommerce.ecommerce_api.user.entity.User;
import com.ecommerce.ecommerce_api.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock private UserRepository repository;
    @Mock private PasswordEncoder passwordEncoder;

    @Test
    void registerCreatesCustomerUser() {
        RegisterRequest request = new RegisterRequest("alice", "alice@example.com", "password123");
        when(passwordEncoder.encode("password123")).thenReturn("encoded");
        when(repository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User user = new UserService(repository, passwordEncoder).register(request);

        assertEquals("alice", user.getUsername());
        assertEquals("alice@example.com", user.getEmail());
        assertEquals("encoded", user.getPassword());
        assertEquals(Role.CUSTOMER, user.getRole());
    }

    @Test
    void registerRejectsExistingUsername() {
        RegisterRequest request = new RegisterRequest("alice", "alice@example.com", "password123");
        when(repository.existsByUsername("alice")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> new UserService(repository, passwordEncoder).register(request));
        verify(repository, never()).save(any());
    }

    @Test
    void registerRejectsExistingEmail() {
        RegisterRequest request = new RegisterRequest("alice", "alice@example.com", "password123");
        when(repository.existsByEmail("alice@example.com")).thenReturn(true);

        assertThrows(DuplicateResourceException.class,
                () -> new UserService(repository, passwordEncoder).register(request));
    }
}
