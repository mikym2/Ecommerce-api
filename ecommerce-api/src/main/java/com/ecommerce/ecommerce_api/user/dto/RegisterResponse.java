package com.ecommerce.ecommerce_api.user.dto;

import com.ecommerce.ecommerce_api.user.entity.Role;
import com.ecommerce.ecommerce_api.user.entity.User;

public record RegisterResponse(
        Long id,
        String username,
        String email,
        Role role
) {

    public static RegisterResponse formEntity(User user){
        return new RegisterResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole()
        );
    }
}
