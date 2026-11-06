package com.ecommerce.ecommerce_api.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PasswordConfig {

    /**
     * passwordEncoder provides a standard Spring interface for password hashing.
     * BCryptPasswordEncoder is one implementation of that interface. It produces hash for the users passwrod
     * that will be saved in the DB.
     * We inject the interface rather than directly depending on the implementation:
     * **/
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
