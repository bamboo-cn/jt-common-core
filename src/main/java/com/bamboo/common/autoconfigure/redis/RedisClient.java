package com.bamboo.common.autoconfigure.redis;

import com.alibaba.fastjson.JSON;
import com.bamboo.common.constant.PublicCode;
import com.bamboo.common.exception.GlobException;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class RedisClient {

    private JedisPool jedisPool;

    public RedisClient setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
        return this;
    }

    /***************STRING *******************/
    public void set(String key, String value) {
        this.set(key, value, (Integer)null);
    }

    public void set(String key, String value, Integer expire) {
        this.setex(key, value, expire);
    }

    public void setex(String key, String value, Integer seconds) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var5 = null;

        try {
            if (Objects.nonNull(seconds)) {
                jedis.setex(key, seconds, value);
            } else {
                jedis.set(key, value);
            }
        } catch (Throwable var14) {
            var5 = var14;
            throw var14;
        } finally {
            finallyClose(jedis,var5);

        }

    }

    public void del(String key) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var3 = null;

        try {
            jedis.del(key);
        } catch (Throwable var12) {
            var3 = var12;
            throw var12;
        } finally {
            finallyClose(jedis,var3);

        }

    }

    public String get(String key, String defualtValue) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var4 = null;

        String var6;
        try {
            String value = jedis.get(key);
            if (value == null) {
                value = defualtValue;
            }

            var6 = value;
        } catch (Throwable var15) {
            var4 = var15;
            throw var15;
        } finally {
            finallyClose(jedis,var4);

        }

        return var6;
    }

    public String get(String key) {
        return (String)this.get(key, (String)null, String.class);
    }

    public Long ttl(String key) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var3 = null;

        Long var5;
        try {
            Long ttl = jedis.ttl(key);
            var5 = ttl;
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            finallyClose(jedis,var3);

        }

        return var5;
    }

    public <T> T get(String key, String defualtValue, Class<T> clazz) {
        String value = this.get(key, defualtValue);
        if (null == value) {
            return null;
        } else {
            return clazz == String.class ? (T)value : JSON.parseObject(value, clazz);
        }
    }

    public <T> T get(String key, Class<T> clazz) {
        return this.get(key, (String)null, clazz);
    }

    public Long incr(String key) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var3 = null;

        Long var5;
        try {
            Long value = jedis.incr(key);
            var5 = value;
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            finallyClose(jedis,var3);

        }

        return var5;
    }

    public Long decr(String key) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var3 = null;

        Long var5;
        try {
            Long value = jedis.decr(key);
            var5 = value;
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            finallyClose(jedis,var3);

        }

        return var5;
    }

    public void sadd(String key, String value) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var4 = null;

        try {
            jedis.sadd(key, new String[]{value});
        } catch (Throwable var13) {
            var4 = var13;
            throw var13;
        } finally {
            finallyClose(jedis,var4);

        }

    }

    public void sadd(String key, String... value) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var4 = null;

        try {
            jedis.sadd(key, value);
        } catch (Throwable var13) {
            var4 = var13;
            throw var13;
        } finally {
            finallyClose(jedis,var4);

        }

    }

    public void srem(String key, String... value) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var4 = null;

        try {
            jedis.srem(key, value);
        } catch (Throwable var13) {
            var4 = var13;
            throw var13;
        } finally {
            finallyClose(jedis,var4);

        }

    }

    public String spop(String key) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var3 = null;

        String var4;
        try {
            var4 = jedis.spop(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            finallyClose(jedis,var3);

        }

        return var4;
    }

    public <T> T spop(String key, Class<T> clazz) {
        String value = this.spop(key);
        return clazz == String.class ? (T)value : JSON.parseObject(value, clazz);
    }

    public Long scard(String key) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var3 = null;

        Long var4;
        try {
            var4 = jedis.scard(key);
        } catch (Throwable var13) {
            var3 = var13;
            throw var13;
        } finally {
            finallyClose(jedis,var3);

        }

        return var4;
    }

    public String hget(String key, String field) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var4 = null;

        String var5;
        try {
            var5 = jedis.hget(key, field);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            finallyClose(jedis,var4);

        }

        return var5;
    }

    public <T> T hget(String key, String field, Class<T> clazz) {
        String value = this.hget(key, field);
        if (null == value) {
            return null;
        } else {
            return clazz == String.class ? (T)value : JSON.parseObject(value, clazz);
        }
    }

    public Long hset(String key, String field, String value) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var5 = null;

        Long var6;
        try {
            var6 = jedis.hset(key, field, value);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            finallyClose(jedis,var5);

        }

        return var6;
    }

    public Long hdel(String key, String... fields) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var4 = null;

        Long var5;
        try {
            var5 = jedis.hdel(key, fields);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            finallyClose(jedis,var4);

        }

        return var5;
    }

    public Long zadd(String key, String member, double score) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var6 = null;

        Long var7;
        try {
            var7 = jedis.zadd(key, score, member);
        } catch (Throwable var16) {
            var6 = var16;
            throw var16;
        } finally {
            finallyClose(jedis,var6);

        }

        return var7;
    }

    public Set<String> zrange(String key, Long start, Long end) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var5 = null;

        Set var6;
        try {
            var6 = jedis.zrange(key, start, end);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            finallyClose(jedis,var5);

        }

        return var6;
    }

    public Set<String> zrevrange(String key, Long start, Long end) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var5 = null;

        Set var6;
        try {
            var6 = jedis.zrevrange(key, start, end);
        } catch (Throwable var15) {
            var5 = var15;
            throw var15;
        } finally {
            finallyClose(jedis,var5);

        }

        return var6;
    }

    public Double zincrby(String key, String member, double score) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var6 = null;

        Double var7;
        try {
            var7 = jedis.zincrby(key, score, member);
        } catch (Throwable var16) {
            var6 = var16;
            throw var16;
        } finally {
            finallyClose(jedis,var6);

        }

        return var7;
    }

    public Long microsecond() {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var2 = null;

        Long var5;
        try {
            List<String> time = jedis.time();
            String micsecond = String.format("%06d", Integer.valueOf((String)time.get(1)));
            var5 = Long.valueOf((String)time.get(0) + micsecond);
        } catch (Throwable var14) {
            var2 = var14;
            throw var14;
        } finally {
            finallyClose(jedis,var2);

        }

        return var5;
    }

    public static void main(String[] args) {
        System.out.println(String.format("%06d", Integer.valueOf("123")));
    }

    public Long zrem(String key, String... member) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var4 = null;

        Long var5;
        try {
            var5 = jedis.zrem(key, member);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            finallyClose(jedis,var4);

        }

        return var5;
    }

    public String lock(String key) {
        return this.lock(key, 1000L);
    }

    public String lock(String key, Long expectMs) {
        Long now = System.currentTimeMillis();
        String id = now.toString();
        Jedis jedis = this.jedisPool.getResource();
        Throwable var6 = null;

        try {
            while(System.currentTimeMillis() - now <= expectMs) {
                if (jedis.setnx(key, id) == 1L) {
                    this.retryExpire(jedis, key, 30);
                    String var7 = id;
                    return var7;
                }

                try {
                    Thread.sleep(10L);
                } catch (Exception var18) {
                    ;
                }
            }
        } catch (Throwable var19) {
            var6 = var19;
            throw var19;
        } finally {
            finallyClose(jedis,var6);

        }

        throw new GlobException(PublicCode.repeat_commit_error.error());
    }

    public void unlock(String key, String value) {
        if (value != null) {
            Jedis jedis = this.jedisPool.getResource();
            Throwable var4 = null;

            try {
                jedis.watch(new String[]{key});
                if (value.equals(jedis.get(key))) {
                    Transaction t = jedis.multi();
                    t.del(key);
                    t.exec();
                } else {
                    jedis.unwatch();
                }
            } catch (Throwable var13) {
                var4 = var13;
                throw var13;
            } finally {
                finallyClose(jedis,var4);

            }

        }
    }

    public Long expires(String key, Integer expires_in) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var4 = null;

        Long var5;
        try {
            var5 = jedis.expire(key, expires_in);
        } catch (Throwable var14) {
            var4 = var14;
            throw var14;
        } finally {
            finallyClose(jedis,var4);

        }

        return var5;
    }

    public <T> T execute(JedisCallback<T> callback) {
        Jedis jedis = this.jedisPool.getResource();

        T var3;
        try {
            var3 = callback.execute(jedis);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return var3;
    }

/*    public void execute(JedisCallbackVoid callback) {
        Jedis jedis = null;

        try {
            callback.execute(jedis = this.jedis());
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

    }

    public List<Object> pipelineAndReturn(PiplelineCallback callback) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var3 = null;

        List var5;
        try {
            Pipeline pipelined = jedis.pipelined();
            callback.execute(pipelined);
            var5 = pipelined.syncAndReturnAll();
        } catch (Throwable var14) {
            var3 = var14;
            throw var14;
        } finally {
            finallyClose(jedis,var3);
        }

        return var5;
    }

    public void pipeline(PiplelineCallback callback) {
        Jedis jedis = this.jedisPool.getResource();
        Throwable var3 = null;

        try {
            Pipeline pipelined = jedis.pipelined();
            callback.execute(pipelined);
            pipelined.sync();
        } catch (Throwable var12) {
            var3 = var12;
            throw var12;
        } finally {
            finallyClose(jedis,var3);
        }

    }*/

    private boolean retryExpire(Jedis jedis, String key, int second) {
        int var4 = 5;

        while(var4-- >= 0) {
            try {
                jedis.expire(key, second);
                return true;
            } catch (Exception var8) {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException var7) {
                    ;
                }
            }
        }

        return false;
    }

    /**
     *  使用完记得关闭jedis
     * @return
     */
    public Jedis getJedis() {
        return this.jedisPool.getResource();
    }

    private  void  finallyClose(Jedis jedis,Throwable var3){
        if (jedis != null) {
            if (var3 != null) {
                try {
                    jedis.close();
                } catch (Throwable var11) {
                    var3.addSuppressed(var11);
                }
            } else {
                jedis.close();
            }
        }
    }



}
