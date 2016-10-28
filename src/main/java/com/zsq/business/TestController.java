package com.zsq.business;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zsq.redis.RedisObject;
import com.zsq.redis.RedisPool;
import com.zsq.util.GenericJedis;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/hello")
public class TestController {


    public static final String REST_SERVICE_URI = "http://127.0.0.1:8089/";
    public static final String ALLINMD_SERVICE_URI = "http://192.168.1.174:18080/services/";

    @RequestMapping(value="/topicList", produces = "application/json; charset=utf-8")
    public @ResponseBody String getTopicList(HttpServletRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        Map<String,Object> paramMap = new HashMap<String,Object>();
        paramMap.put("firstResult",0);
        paramMap.put("maxResult",10);
        String string = JSON.toJSONString(paramMap);
        Map<String,String> queryMap = new HashMap<String,String>();
        queryMap.put("queryJson",string);
        System.out.println(string);
//        ResponseEntity forEntity = restTemplate.getForEntity(ALLINMD_SERVICE_URI + "cms/topic/v2/getList?queryJson=" + string, CmsTopic.class);
        List forObject = restTemplate.getForObject(ALLINMD_SERVICE_URI + "cms/topic/v2/getList?queryJson={'firstResult':{firstResult},'maxResult':{maxResult}}", List.class,paramMap);

//        System.out.println(o);
        return JSON.toJSONString(forObject);
    }


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
        String o = restTemplate.getForObject(ALLINMD_SERVICE_URI + "qiniu/storage/v2/getToken", String.class);
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
        paramMap.put("searchParam", "关节");
        paramMap.put("treeLevel", "2_3");
        ResponseEntity forEntity = restTemplate.getForEntity(ALLINMD_SERVICE_URI + "comm/data/tag/v2/getMapList/", String.class, paramMap);

        String o = restTemplate.getForObject(ALLINMD_SERVICE_URI + "qiniu/storage/v2/getToken", String.class);

        System.out.println(o);
        return JSON.toJSONString(forEntity);

    }
    @RequestMapping(value="/get", produces = "application/json; charset=utf-8")
    public @ResponseBody String getJSON(HttpServletRequest request) {
        for (int i = 0;i < 100; i++){
            RedisObject.setValue(String.valueOf(i),System.currentTimeMillis());
        }
        return "OK";
    }

    @RequestMapping(value="/getJedis", produces = "application/json; charset=utf-8")
    public @ResponseBody String getJSONByJedis(HttpServletRequest request) {
        System.out.println("=================");
        return "=======================";
    }

    @RequestMapping(value="/delete", produces = "application/json; charset=utf-8")
    public @ResponseBody String deleteRedis(HttpServletRequest request) {
        for (int i = 0;i < 100; i++){
            RedisObject.deleteByKey(String.valueOf(i));
        }
        return "=======================";
    }

    @RequestMapping(value="/initProperty", produces = "application/json; charset=utf-8")
    public @ResponseBody String InitProperty(HttpServletRequest request) {
        GenericJedis genericJedis = new GenericJedis(RedisPool.getJedis());
        RestTemplate restTemplate = new RestTemplate();
        Map map = new HashMap();
        map.put("propertyTypeId",1);
        map.put("firstResult",0);
        map.put("maxResult",100);
        map.put("isValid",1);
        Map paraMap = new HashMap();
        paraMap.put("queryJson", JSON.toJSONString(map));
//        ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(ALLINMD_SERVICE_URI + "comm/data/property/v2/getList", Object[].class,paraMap);
//        Object[] objects = responseEntity.getBody();
//        MediaType contentType = responseEntity.getHeaders().getContentType();
//        HttpStatus statusCode = responseEntity.getStatusCode();
//        System.out.println(JSON.toJSONString(objects));
        Object[] str = restTemplate.getForObject(ALLINMD_SERVICE_URI + "comm/data/property/v2/getList", Object[].class,paraMap);
        List searchList= Arrays.asList(str);
        for (Object o : searchList) {
            JSONObject jsonObject1 = JSONObject.parseObject(o.toString());
            Object id = jsonObject1.get("id");
            RedisObject.setValue("a:cdp:"+id.toString(),jsonObject1);
        }
        return "OK";
    }
}
