package com.bamboo.common.autoconfigure.redis;


import org.springframework.boot.context.properties.ConfigurationProperties;



@ConfigurationProperties(
        prefix = "spring.redis"
)
public class RedisProperties {
    private String host = "localhost";//IP
    private int port = 6379;//端口
    private String password;//密码
    private int database = 0;//库编号

    private int timeout = 2000;//超时
    private int maxTotal = 8;//线程池最大数量
    private int maxIdle = 8;//最大空闲时间
    private int maxWaitMillis = 100000;//最大等待时间

    public String getHost() {
        return host;
    }

    public RedisProperties setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public RedisProperties setPort(int port) {
        this.port = port;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RedisProperties setPassword(String password) {
        this.password = password;
        return this;
    }

    public int getDatabase() {
        return database;
    }

    public RedisProperties setDatabase(int database) {
        this.database = database;
        return this;
    }

    public int getTimeout() {
        return timeout;
    }

    public RedisProperties setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public int getMaxTotal() {
        return maxTotal;
    }

    public RedisProperties setMaxTotal(int maxTotal) {
        this.maxTotal = maxTotal;
        return this;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public RedisProperties setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
        return this;
    }

    public int getMaxWaitMillis() {
        return maxWaitMillis;
    }

    public RedisProperties setMaxWaitMillis(int maxWaitMillis) {
        this.maxWaitMillis = maxWaitMillis;
        return this;
    }
}