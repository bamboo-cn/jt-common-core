package com.bamboo.common.cache.redis;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.nio.charset.Charset;

/**
 * @author zhanghanlong
 * @eamil zjcjava@163.com
 * @time 2020/1/9 17:53
 */
@Component
public class StringRedisSerializer implements RedisSerializer<String> {

    private final Logger logger =  LoggerFactory.getLogger ( this.getClass () );

    @Autowired
    private CacheConfigProperties cacheConfigProperties;

    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    public StringRedisSerializer() {
        this ( Charset.forName ( "UTF8" ) );
    }

    public StringRedisSerializer(Charset charset) {
        Assert.notNull ( charset, "charset must not be null!" );
    }

    @Override
    public String deserialize(byte[] bytes) {
        String keyPrefix = cacheConfigProperties.getPrefix();
        String saveKey = new String ( bytes, DEFAULT_CHARSET );
        int indexOf = saveKey.indexOf ( keyPrefix );
        if (indexOf > 0) {
            logger.info ( "key缺少前缀" );
        } else {
            saveKey = saveKey.substring ( indexOf );
        }
        logger.info ( "saveKey:{}",saveKey);
        return (saveKey.getBytes () == null ? null : saveKey);
    }

    @Override
    public byte[] serialize(String string) {
        logger.info ( "cacheConfigProperties={}", JSON.toJSONString(cacheConfigProperties));
        String keyPrefix = cacheConfigProperties.getPrefix();
        String key = keyPrefix +":"+ string;
        logger.info ( "redis serialize key={}",key);
        return (key == null ? null : key.getBytes ( DEFAULT_CHARSET ));
    }
}
