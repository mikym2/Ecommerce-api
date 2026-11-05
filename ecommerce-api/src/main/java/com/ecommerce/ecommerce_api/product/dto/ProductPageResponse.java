package com.ecommerce.ecommerce_api.product.dto;

import java.util.List;

//record to be used for Page caching on redis.
// caching a record instead of caching the spring's Page is to avoid depending on page implementation
//and have a predictable json structure
public record ProductPageResponse(
        List<ProductResponse> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last
) {
}
