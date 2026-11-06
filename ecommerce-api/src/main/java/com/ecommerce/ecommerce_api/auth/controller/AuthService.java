package com.ecommerce.ecommerce_api.auth.controller;

import com.ecommerce.ecommerce_api.user.dto.LoginRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    public AuthService( AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    public Authentication authenticate(LoginRequest request){

        //- UsernamePasswordAuthenticationToken creates an authentication object containing the credentials supplied by the user.
        //  At this point, it is not authenticated, it simply represents an authentication request.
        //- authenticationManager.authenticate: If the credentials are incorrect, Spring throws an AuthenticationException, typically BadCredentialsException
        //  If authentication succeeds, we receive an authenticated Authentication object.
        return  authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
    }
}
