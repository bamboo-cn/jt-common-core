package com.bamboo.common.aspectj;

import com.bamboo.common.aspectj.annotation.SynRedisLock;
import com.bamboo.common.autoconfigure.redis.RedisTemplateLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Aspect
@Component
public class SynRedisLockAspect {
    private static final Logger log = LoggerFactory.getLogger(SynRedisLockAspect.class);
    @Autowired
    private RedisTemplateLock redisTemplateLock;

    public SynRedisLockAspect() {
    }

    @Around("@annotation(com.bamboo.common.aspectj.annotation.SynRedisLock)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String lockId = null;
        SynRedisLock syncLock = null;
        String path = null;

        Object var7;
        try {
            syncLock = (SynRedisLock) this.getAnnotation(joinPoint, SynRedisLock.class);
            path = this.lockPath(joinPoint, syncLock);
            long lockMs = syncLock.lockMs();
            lockId = this.redisTemplateLock.tryLockScript(path,lockMs > 0L ? lockMs : 9223372036854775807L);
            log.info("分布式锁SynRedisLock\t {}={}",path,lockMs);
            var7 = joinPoint.proceed(joinPoint.getArgs());
        } finally {

            if (lockId != null && path != null) {
                this.redisTemplateLock.releaseLock(path, lockId);
            }

        }

        return var7;
    }

    private <T extends Annotation> T getAnnotation(ProceedingJoinPoint jp, Class<T> clazz) {
        MethodSignature sign = (MethodSignature) jp.getSignature();
        Method method = sign.getMethod();
        return method.getAnnotation(clazz);
    }

    private String lockPath(ProceedingJoinPoint jp, SynRedisLock syncLock) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String path = this.getPath(jp.getArgs(), syncLock.path(), syncLock.indexProps());
        return path;
    }

    private String getPath(Object[] args, String path, String[] indexProps) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (path != null && path.length() > 0 && indexProps != null && indexProps.length > 0) {
            String[] pathArgs = new String[indexProps.length];

            for (int i = 0; i < indexProps.length; ++i) {
                String indexProp = indexProps[i];
                if (indexProp.indexOf(":") < 0) {
                    pathArgs[i] = args[Integer.valueOf(indexProp)] + "";
                } else {
                    int index = Integer.valueOf(indexProp.split(":")[0]);
                    pathArgs[i] = this.getPNameValue(args[index], indexProp.split(":")[1]);
                }
            }

            return String.format(path, pathArgs);
        } else {
            return path;
        }
    }

    private String getPNameValue(Object o, String pName) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String methodName = "get" + pName.substring(0, 1).toUpperCase() + pName.substring(1);
        Method m = o.getClass().getMethod(methodName);
        Object r = m.invoke(o, (Object[]) null);
        return r == null ? null : r + "";
    }
}