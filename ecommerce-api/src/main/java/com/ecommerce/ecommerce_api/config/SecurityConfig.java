package com.ecommerce.ecommerce_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http     // Disable CSRF for our stateless REST API. It's only needed for sessions that use cookies here
                // we're going to be using JWT tokens for authentication plus APIs are stateless aka don't save user info with cookies
                .csrf(csrf -> csrf.disable())

                // Configure authorization
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()
                )

                //Disable HTTP Basic authentication and browser login form
                .httpBasic(basic -> basic.disable())
                .formLogin(form -> form.disable())

                //Do not create HTTP sessions. This is important for JWT authentication.
                //Spring Security won't create an HTTP session to store the user's authentication state
                //Instead, each authenticated request will carry its JWT.
                .sessionManagement(session -> session
                        .sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        ));

        return http.build();
    }
}
