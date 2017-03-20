package com.nop.redis;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import redis.clients.jedis.Jedis;

import java.util.*;

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
        assertSame(null, jedis.get("k2"));
    }

    @Test
    public void remainTimeBeforeExpired() throws InterruptedException {
        jedis.set("ttl", "123");
        jedis.expire("ttl", 5);
        Thread.sleep(3000);
        Long ttl = jedis.ttl("ttl");
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
    public void hmset(){
        Map<String,String> map=new HashMap<>();
        map.put("name","yzj");
        map.put("birth","1988-10-05");
        System.out.println(jedis.hmset("map",map));
        List<String> values=jedis.hmget("map","name","birth");
        assertTrue(values.containsAll(Lists.newArrayList("yzj","1988-10-05")));
        assertTrue(1==jedis.del("map"));
    }

    @Test
    public void typeOfMap() {
        assertEquals("hash", jedis.type("map"));
    }

    @Test
    public void lpush() {
        Long size = jedis.lpush("list", "123", "456");
        assertEquals(size, jedis.llen("list"));
    }

    @Test
    public void lrange() {
        List<String> list = jedis.lrange("list", 0, 1);
        Joiner joiner = Joiner.on(",");
        assertEquals(joiner.join(list), "456,123");
    }

    @Test
    public void blockedRPop() {
        jedis.rpush("list", "456");
        List<String> list = jedis.brpop(3, "list");
        String v = list.get(1);
        assertEquals(v, "456");
    }

    @Test
    /**
     * when last element was removed from redis aggregate data type ,the key is automatically destroyed.
     */
    public void isKeyAutoDestroyedWhenLastElementRemoved() {
        jedis.lpush("lst", "yzj");
        jedis.rpop("lst");
        assertTrue(!jedis.exists("lst"));
    }

    @Test
    public void listType(){
        jedis.lpush("lst", "yzj");
        assertEquals("list",jedis.type("lst") );
        assertTrue(jedis.del("lst","list")==2);
    }
}
