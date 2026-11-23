package com.ecommerce.ecommerce_api.security;

import com.ecommerce.ecommerce_api.user.entity.Role;
import com.ecommerce.ecommerce_api.user.entity.User;
import com.ecommerce.ecommerce_api.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CustomUserDetailsServiceTest {
    @Test
    void loadsSpringSecurityUserDetails() {
        UserRepository repository = mock(UserRepository.class);
        when(repository.findByUsername("alice"))
                .thenReturn(Optional.of(new User("alice", "alice@example.com", "encoded", Role.ADMIN)));

        UserDetails details = new CustomUserDetailsService(repository).loadUserByUsername("alice");

        assertEquals("alice", details.getUsername());
        assertEquals("encoded", details.getPassword());
        assertTrue(details.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void throwsWhenUserMissing() {
        UserRepository repository = mock(UserRepository.class);
        when(repository.findByUsername("missing")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> new CustomUserDetailsService(repository).loadUserByUsername("missing"));
    }
}
