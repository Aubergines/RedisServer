package com.zsq.business;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zsq.redis.RedisObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/hello")
public class TestController {


    public static final String REST_SERVICE_URI = "http://115.28.87.77:8089/";
    public static final String ALLINMD_SERVICE_URI = "http://android1.api.allinmd.cn:18080/services/";


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

    @RequestMapping(value="/getToken", produces = "application/json; charset=utf-8")
    public @ResponseBody String getTokenJSON(HttpServletRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        String o = restTemplate.getForObject(ALLINMD_SERVICE_URI + "qiniu/storage/v2/getToken",String.class);

        System.out.println(o);
        return o;
    }

    @RequestMapping(value="/hotList", produces = "application/json; charset=utf-8")
    public @ResponseBody String getHotListJSON(HttpServletRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        Map paramMap = new HashMap();
        ResponseEntity forEntity = restTemplate.getForEntity(ALLINMD_SERVICE_URI + "log/customer/keyword/v2/getHotList/", String.class, paramMap);

        String o = restTemplate.getForObject(ALLINMD_SERVICE_URI + "qiniu/storage/v2/getToken",String.class);

        System.out.println(o);
        return JSON.toJSONString(forEntity);
    }

    @RequestMapping(value="/keyword", produces = "application/json; charset=utf-8")
    public @ResponseBody String getKeywordJSON(HttpServletRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        Map paramMap = new HashMap();
//        paramMap.put("firstResult", 0);
//        paramMap.put("visitSiteId", 6);
//        paramMap.put("isValid", 1);
//        paramMap.put("pageSize", 10);
//        paramMap.put("sortType", 1);
//        paramMap.put("pageIndex", 1);
//        paramMap.put("maxResult", 10);
        paramMap.put("searchParam", "关节");
        paramMap.put("treeLevel", "2_3");
        ResponseEntity forEntity = restTemplate.getForEntity(ALLINMD_SERVICE_URI + "comm/data/tag/v2/getMapList/", String.class, paramMap);

        String o = restTemplate.getForObject(ALLINMD_SERVICE_URI + "qiniu/storage/v2/getToken", String.class);

        System.out.println(o);
        return JSON.toJSONString(forEntity);

    }
    @RequestMapping(value="/get", produces = "application/json; charset=utf-8")
    public @ResponseBody String getJSON(HttpServletRequest request) {
        ShardedJedis resource = shardedJedisPool.getResource();
        String s = resource.get("1397586886832");
        System.out.println(s);
        return s;
    }

}
