package com.ecommerce.ecommerce_api.user.dto;

/**
 * accessToken	The JWT generated for the authenticated user.
 * tokenType	Usually Bearer.
 * expiresIn	Token lifetime in seconds.
 * */
public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresIn
) {
}
