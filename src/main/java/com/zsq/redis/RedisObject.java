package com.zsq.redis;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;

/**
 * Created by Aubergine on 2016/6/3.
 */
public class RedisObject {

    public static void setValue(String key,Object value){
        Jedis resource = RedisPool.getResource();
        resource.set(key,JSON.toJSONString(value));
        RedisPool.returnResource(resource);
    }

    public static String getValue(String key){
        Jedis resource = RedisPool.getResource();
        String data = resource.get(key);
        RedisPool.returnResource(resource);
        return data;
    }

    public static void deleteByKey(String key){
        Jedis resource = RedisPool.getResource();
        Long del = resource.del(key);
        RedisPool.returnResource(resource);
    }
}
