package com.zsq.redis;

import com.alibaba.fastjson.JSON;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanParams;
import redis.clients.jedis.ScanResult;

import java.util.Set;

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

    public static String scanKeys(String cursor,String pattern,Set<String> res) {
        Jedis jedis = null;
        String cursorString = "0";
        try {
            jedis =  RedisPool.getResource();
            ScanParams scanParams = new ScanParams();
            scanParams.match(pattern);
            scanParams.count(10000);
            ScanResult<String> result = jedis.scan(cursor, scanParams);
            cursorString = result.getStringCursor();
            res.addAll(result.getResult());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisPool.returnResource(jedis);
        }
        return cursorString;
    }

    public static String hgetall(String key) {
        Jedis jedis = null;
        String res = null;
        try {
            jedis =  RedisPool.getResource();
            res = getValue(key);
        } catch (Exception e) {
            RedisPool.getJedis().returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            RedisPool.returnResource(jedis);
        }
        return res;
    }
}
