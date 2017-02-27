package com.nop.redis;

import redis.clients.jedis.Jedis;

/**
 * Created by yangzijun on 17-2-24.
 */
public class RedisDemo {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("localhost",6379);
        jedis.set("foo", "bar");
        String value = jedis.get("yzj");
        System.out.println(value);
    }
}
