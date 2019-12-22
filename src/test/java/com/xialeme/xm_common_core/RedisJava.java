package com.xialeme.xm_common_core;

import redis.clients.jedis.Jedis;

/**
 * @program: xm-common-core
 * @description: redis过滤器的使用
 * @author: Bamboo zjcjava@163.com
 * @create: 2019-10-13 22:32
 **/
public class RedisJava {

    public static void main(String[] args) {
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        System.out.println("连接成功");
        //设置 redis 字符串数据
        jedis.set("runoobkey", "www.runoob.com");
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: "+ jedis.get("runoobkey"));
    }
}
