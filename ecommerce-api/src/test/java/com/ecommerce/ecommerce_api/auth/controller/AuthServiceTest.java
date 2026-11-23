package com.ecommerce.ecommerce_api.auth.controller;

import com.ecommerce.ecommerce_api.user.dto.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    @Test
    void authenticateUsesUsernameAndPassword() {
        AuthenticationManager manager = mock(AuthenticationManager.class);
        Authentication result = mock(Authentication.class);
        when(manager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(result);

        Authentication actual = new AuthService(manager).authenticate(
                new LoginRequest("alice", "password123"));

        assertSame(result, actual);
        verify(manager).authenticate(argThat(token ->
                token.getPrincipal().equals("alice")
                        && token.getCredentials().equals("password123")));
    }
}
