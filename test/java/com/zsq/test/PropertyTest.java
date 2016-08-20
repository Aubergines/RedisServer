package com.zsq.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zsq.redis.RedisObject;
import com.zsq.redis.RedisPool;
import com.zsq.util.GenericJedis;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Aubergine on 2016/8/3.
 */
public class PropertyTest {
    public static final String ALLINMD_SERVICE_URI = "http://192.168.1.32:18080/services/";
    public static void main(String[] args) {
        GenericJedis genericJedis = new GenericJedis(RedisPool.getJedis());
        RestTemplate restTemplate = new RestTemplate();
        Map map = new HashMap();
        map.put("propertyTypeId","1");
        map.put("isValid","1");
        Map paraMap = new HashMap();
        paraMap.put("queryJson", JSON.toJSONString(map));
        List resultList = restTemplate.getForObject(ALLINMD_SERVICE_URI + "comm/data/property/v2/getList", List.class,paraMap);
        for (Object o : resultList) {
            JSONObject jsonObject1 = JSONObject.parseObject(o.toString());
            Object id = jsonObject1.get("id");
            RedisObject.setValue("a:cdp:"+id.toString(),jsonObject1);
        }

    }
}
