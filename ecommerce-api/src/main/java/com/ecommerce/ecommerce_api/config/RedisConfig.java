package com.ecommerce.ecommerce_api.config;

import com.ecommerce.ecommerce_api.product.dto.ProductPageResponse;
import com.ecommerce.ecommerce_api.product.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Value("${redis.ttl:5}")
    private int redisTtl;

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory){

        //using a json serializer to store json objects in the cache, for easier readability and making it easier to integrate
        // node js apps. if not specified it would use a java serializer by default that stores in bytes

        // Serialization for ProductPageResponse.class
        JacksonJsonRedisSerializer<ProductPageResponse> pageSerializer =
                new JacksonJsonRedisSerializer<>(ProductPageResponse.class);

        // Serialization for ProductResponse.class
        JacksonJsonRedisSerializer<ProductResponse> productSerializer =
                new JacksonJsonRedisSerializer<>(ProductResponse.class);

        // configuration for ProductPageResponse
        RedisCacheConfiguration pageCacheConfiguration =
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(redisTtl)) //every cache entry will expire after 20 minutes
                        .disableCachingNullValues() //prevents Spring Cache from trying to serialize queries that results in null values
                        .serializeValuesWith(
                                RedisSerializationContext.SerializationPair
                                        .fromSerializer(pageSerializer)
                        );

        //Configuration for ProductResponse
        RedisCacheConfiguration productCacheConfiguration =
                RedisCacheConfiguration.defaultCacheConfig()
                        .entryTtl(Duration.ofMinutes(redisTtl))
                        .disableCachingNullValues()
                        .serializeValuesWith(
                                RedisSerializationContext.SerializationPair
                                        .fromSerializer(productSerializer)
                        );

        return RedisCacheManager.builder(connectionFactory)
                .withCacheConfiguration(RedisCacheNames.PRODUCTS, pageCacheConfiguration)
                .withCacheConfiguration(RedisCacheNames.PRODUCT_BY_ID, productCacheConfiguration)
                .disableCreateOnMissingCache() //This ensures Spring won't silently create another cache with a
                                               // different, default serialization configuration. Any future cache,
                                               // such as productById, will need its own explicitly configured serializer
                .build();
    }
}
