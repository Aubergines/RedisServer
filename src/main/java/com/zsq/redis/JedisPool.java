package com.zsq.redis;

import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

/**
 * @Author Aubergine(chinazhaoshuangquan@gmail.com)
 * Created on 2016/6/27 13:11
 */
public class JedisPool {

    @Resource
    private  JedisPool jedisPool;



    public synchronized  Jedis getResource() {
        try {
            if (jedisPool != null) {
                Jedis resource = jedisPool.getResource();
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
