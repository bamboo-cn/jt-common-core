package com.bamboo.common.autoconfigure.redis;

import com.bamboo.common.constant.PublicCode;
import com.bamboo.common.exception.GlobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author zhanghanlong
 * @eamil zjcjava@163.com
 * @time 2020/1/3 17:35
 */
@Component
public class RedisTemplateLock {

    private static final Long RELEASE_SUCCESS = 1L;
    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "EX";
    private static final String ADD_LOCK_SCRIPT = "local key     = KEYS[1]\n" +
            "local content = ARGV[1]\n" +
            "local ttl     = tonumber(ARGV[2])\n" +
            "local lockSet = redis.call('setnx', key, content)\n" +
            "if lockSet == 1 then\n" +
            "  redis.call('PEXPIRE', key, ttl)\n" +
            "else\n" +
            "  local value = redis.call('get', key)\n" +
            "  if(value == content) then\n" +
            "    lockSet = 1;\n" +
            "    redis.call('PEXPIRE', key, ttl)\n" +
            "  end\n" +
            "end\n" +
            "return lockSet";
    private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    // 在缓存LUA脚本后，使用该变量保存Redis返回的32位的SHA1编码，使用它去执行缓存的LUA脚本[加入这句话]
    String sha1 = null;


    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 该加锁方法仅针对单实例 Redis主备 可实现分布式加锁
     * 对于 Redis 集群则无法使用,集群数据同步有延迟，锁不一定可靠
     *
     * 支持重复，线程安全
     *
     * @param lockKey   加锁键
     * @param clientId  加锁客户端唯一标识(采用UUID)
     * @param seconds   锁过期时间
     * @return
     */
    public Boolean tryLock(String lockKey, String clientId, long seconds) {
       // redisTemplate.opsForValue().set();
        return redisTemplate.execute((RedisCallback<Boolean>) redisConnection -> {
            Jedis jedis = (Jedis) redisConnection.getNativeConnection();
            String result = jedis.set(lockKey, clientId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, seconds);
            if (LOCK_SUCCESS.equals(result)) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        });
    }

    /**
     * script方式
     * @param lockKey
     * @param expectMs
     * @return
     */
    public String tryLockScript(String lockKey, Long expectMs) {

        Long now = System.currentTimeMillis();
        String lockId =  UUID.randomUUID().toString().replaceAll("-", "");

        try {
            // 如果脚本没有加载过，那么进行加载，这样就会返回一个sha1编码
            // 执行脚本，返回结果
            List<String> keys = new ArrayList<String>();
            keys.add(lockKey);
            List<String> args = new ArrayList<String>();
            args.add(lockId);
            args.add(expectMs.toString());


            while(System.currentTimeMillis() - now <= expectMs) {
                DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(ADD_LOCK_SCRIPT, Long.class);
                Long result =  stringRedisTemplate.execute(redisScript, keys, lockId,expectMs.toString());
                if (RELEASE_SUCCESS.equals(result)) {
                    return lockId;
                }
                try {
                    Thread.sleep(10L);
                } catch (Exception var18) {
                    ;
                }
            }

        } catch (Throwable var19) {
            throw var19;
        }  finally {

        }
        throw new GlobException(PublicCode.repeat_commit_error.error());
    }



    /**
     * 与 tryLock 相对应，用作释放锁
     *
     * @param lockKey
     * @param clientId
     * @return
     */
    public Boolean releaseLock(String lockKey, String clientId) {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>(RELEASE_LOCK_SCRIPT, Long.class);
        Long result =stringRedisTemplate.execute(redisScript, Collections.singletonList(lockKey), clientId);
        if (RELEASE_SUCCESS.equals(result)) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }
}
