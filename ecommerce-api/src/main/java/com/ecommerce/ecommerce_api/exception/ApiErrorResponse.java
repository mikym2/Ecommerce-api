package com.ecommerce.ecommerce_api.exception;

import java.util.Map;

public record ApiErrorResponse(
        int status,
        String error,
        String message,
        Map<String, String> details
) {
}
