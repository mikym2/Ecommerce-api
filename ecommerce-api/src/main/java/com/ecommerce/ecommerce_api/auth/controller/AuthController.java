package com.ecommerce.ecommerce_api.auth.controller;

import com.ecommerce.ecommerce_api.user.dto.RegisterRequest;
import com.ecommerce.ecommerce_api.user.dto.RegisterResponse;
import com.ecommerce.ecommerce_api.user.entity.User;
import com.ecommerce.ecommerce_api.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService){
        this.userService  = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest request
            ){
        User user = userService.register(request);

        RegisterResponse registerResponse
                = RegisterResponse.formEntity(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(registerResponse);
    }
}
