package com.nop.redis;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;

/**
 * Created by yangzijun on 17-3-6.
 */
public class BaseAPI {

    private Jedis jedis;

    @Before
    public void init() {
        jedis = new Jedis("localhost", 6379);
    }

    @Test
    public void setString() {
        System.out.println(jedis.set("k", "v"));
        assertTrue("v".equals(jedis.get("k")));
    }

    @Test
    public void setStringWithExpire() throws InterruptedException {
        //set the key if it doesn't exists,EX:the time unit which means second
        jedis.set("k2", "v2", "NX", "EX", 1);
        Thread.sleep(1100);
        assertSame(null,jedis.get("k2"));
    }

    @Test
    public void remainTimeBeforeExpired() throws InterruptedException {
        jedis.set("ttl","123");
        jedis.expire("ttl",5);
        Thread.sleep(3000);
        Long ttl=jedis.ttl("ttl");
        assertTrue(ttl.equals(2L));
    }

    @Test
    public void typeOfSet() {
        assertEquals("string", jedis.type("k"));
    }

    @Test
    public void setMap() {
        System.out.println(jedis.hset("map", "name", "yzj"));
        assertTrue(jedis.hexists("map", "name"));
        jedis.hsetnx("map", "name", "xl");
        assertTrue(!"xl".equals(jedis.hget("map", "name")));
    }

    @Test
    public void typeOfMap(){
        assertEquals("hash",jedis.type("map"));
    }
}
