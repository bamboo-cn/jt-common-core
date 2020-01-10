package com.bamboo.common.cache.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhanghanlong
 * @eamil zjcjava@163.com
 * @time 2020/1/9 18:01
 */


@Component
@ConfigurationProperties(prefix = "spring.cache")
public class CacheConfigProperties {

    private String prefix = "spring-cache";
    private int ttlTime = 3600 ;//1h

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getTtlTime() {
        return ttlTime;
    }

    public void setTtlTime(int ttlTime) {
        this.ttlTime = ttlTime;
    }
}
