package com.bamboo.common.cache.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * spring cache 注解统一管理方法缓存
 * @author zhanghanlong
 * @eamil zjcjava@163.com
 * @time 2020/1/9 11:11
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    private final Logger logger = LoggerFactory.getLogger ( this.getClass () );

    @Autowired
    private CacheConfigProperties cacheConfigProperties;
    @Autowired
    private StringRedisSerializer stringRedisSerializer;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;




    @Override
    @Bean
    /* cache key generation strategy */
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                logger.info("default keyGenerator={}",sb.toString());
                return sb.toString();
            }
        };
    }

    @Bean
    /*  redis-cache configuration */
    public CacheManager redisCacheManager() {

        //使用FastJson序列化
        FastJsonRedisSerializer<Object> valueSerializer = new FastJsonRedisSerializer<Object>(Object.class);

        // 配置序列化（解决乱码的问题）
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(cacheConfigProperties.getTtlTime()))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(stringRedisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer))
                .disableCachingNullValues();

        //上面实现的缓存读写
        CustomerRedisCacheWriter customerCachaWriter = new CustomerRedisCacheWriter(redisConnectionFactory);

        RedisCacheManager cacheManager = RedisCacheManager.builder(customerCachaWriter)
                .cacheDefaults(config)
                .build();
        return cacheManager;
    }



    @Override
    public CacheErrorHandler errorHandler() {
        CacheErrorHandler cacheErrorHandler = new CacheErrorHandler() {

            @Override
            public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
                RedisErrorException(exception, key);
            }

            @Override
            public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {
                RedisErrorException(exception, key);
            }

            @Override
            public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
                RedisErrorException(exception, key);
            }

            @Override
            public void handleCacheClearError(RuntimeException exception, Cache cache) {
                RedisErrorException(exception, null);
            }
        };
        return cacheErrorHandler;
    }

    protected void RedisErrorException(Exception exception,Object key){
        logger.error("redis异常：key=[{}], exception={}", key, exception.getMessage());
    }



}
