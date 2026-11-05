package com.ecommerce.ecommerce_api.config;

import com.ecommerce.ecommerce_api.product.dto.ProductResponse;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RedisSerializationTest {

    @Test
    void shouldSerializeAndDeserializeProductResponse() {

        // Create the serializer
        GenericJacksonJsonRedisSerializer serializer =
                GenericJacksonJsonRedisSerializer.builder().build();

        // Create a sample product response
        Instant now = Instant.now();

        ProductResponse original = new ProductResponse(
                1L,
                "Wireless Headphones",
                "Bluetooth headphones",
                new BigDecimal("149.99"),
                25,
                now,
                now
        );

        // Convert the Java object into JSON bytes
        byte[] serialized = serializer.serialize(original);

        // Convert the JSON bytes back into a Java object
        Object deserialized = serializer.deserialize(serialized, ProductResponse.class);

        // Verify the result
        assertNotNull(deserialized);
        assertInstanceOf(ProductResponse.class, deserialized);
        assertEquals(original, deserialized);

        // Display the JSON representation
        System.out.println(new String(serialized));
    }
}