package com.bamboo.common.autoconfigure.redis;

import redis.clients.jedis.Jedis;

public interface JedisCallback <T> {
    T execute(Jedis var1);
}
