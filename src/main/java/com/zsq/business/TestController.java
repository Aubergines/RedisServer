package com.zsq.business;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zsq.redis.RedisObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/hello")
public class TestController {


    public static final String REST_SERVICE_URI = "http://localhost:8089/";


    @Resource
    ShardedJedisPool shardedJedisPool;


    @RequestMapping(value="/foo", produces = "application/json; charset=utf-8")
    public @ResponseBody String initCustomerData(HttpServletRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        String o = restTemplate.getForObject(REST_SERVICE_URI + "customer/allCustomer",String.class);
//        System.out.println(JSON.toJSONString(o));
        if (null != o){
            JSONArray objects = JSONArray.parseArray(o);
            for (Object object : objects) {
                if (null != object){
                    JSONObject jsonObject = JSONObject.parseObject(object.toString());
                    Object customerId = jsonObject.get("customerId");
                    RedisObject.setValue(customerId.toString(),object);
                }
            }

        }
        return "OK";
    }

    @RequestMapping(value="/getId", produces = "application/json; charset=utf-8")
    public @ResponseBody String getShopInJSON(HttpServletRequest request) {
        String id = request.getParameter("id");
        String value = RedisObject.getValue(id);
        JSONObject jsonObject = JSONObject.parseObject(value);
        Object o = jsonObject.get("areasExpertise");
        System.out.println(o);
        return value;
    }

    @RequestMapping(value="/get", produces = "application/json; charset=utf-8")
    public @ResponseBody String getJSON(HttpServletRequest request) {
        ShardedJedis resource = shardedJedisPool.getResource();
        String s = resource.get("key:__rand_int__");
        System.out.println(s);
        return "OK";
    }

}
