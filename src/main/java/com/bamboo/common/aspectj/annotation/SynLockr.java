package com.bamboo.common.aspectj.annotation;


import java.lang.annotation.*;
import java.lang.annotation.ElementType;


/**
 * @author: bamboo,zjcjava@163.com
 * @time: 2018/7/30
 *<p>分布式锁:REDIS实现方式</p>
 *
 **/

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@Inherited
public @interface SynLockr {

    /**
     * 分布式锁的路径
     */
    String path();

    ;
    /**
     * 锁释放时间 默认1秒
     */
    long lockMs() default 1000L;

    /**
     * 分布式锁的key使用的属性值
     */
    String[] indexProps() default {};
}

