package com.zsq.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapUtil {

    // Map --> Bean 1: 利用Introspector,PropertyDescriptor实现 Map --> Bean
    public static void transMap2Bean(Map<String, Object> map, Object obj) {

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo
                    .getPropertyDescriptors();

            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                if (map.containsKey(key)) {
                    Object value = map.get(key);
                    // 得到property对应的setter方法
                    Method setter = property.getWriteMethod();
                    setter.invoke(obj, value);
                }

            }

        } catch (Exception e) {
            System.out.println("transMap2Bean Error " + e);
        }

        return;

    }

    // Bean --> Map 1: 利用Introspector和PropertyDescriptor 将Bean --> Map
    public static Map<String, Object> transBean2Map(Object obj) {

        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo
                    .getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = getter.invoke(obj);

                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            System.out.println("transBean2Map Error " + e);
        }

        return map;

    }

    //JSON字符串转化为嵌套的map
    public static Map parserToMap(String s) {
        Map map = new HashMap();
        JSONObject jsonObject = JSON.parseObject(s);
        Set<String> strings = jsonObject.keySet();
        for (String string : strings) {
            String value = jsonObject.get(string).toString();
            if (value.startsWith("{") && value.endsWith("}")) {
                map.put(string, parserToMap(value));
            } else {
                map.put(string, value);
            }
        }

        return map;
    }

    /**
     * 解析出入参数.并转化为sendSiteId
     *
     * @param paramMap
     */
    public static void changeVisitSiteId(Map paramMap) {
        String visitSiteId = StringTool.getMapString(paramMap,
                "visitSiteId") != "" ? StringTool.getMapString(paramMap,
                "visitSiteId") : "10";
        if (!visitSiteId.isEmpty()) {
            paramMap.put("sendSiteId", visitSiteId);
        }
    }

    /**
     * 解析传入参数，并赋值给新map
     */
    public static void changeVisitSiteId(Map newMap, Map paramMap) {
        String visitSiteId = StringTool.getMapString(paramMap,
                "visitSiteId") != "" ? StringTool.getMapString(paramMap,
                "visitSiteId") : "10";
        String opIp = StringTool.getMapString(paramMap, "opIp");
        String opAdvice = StringTool.getMapString(paramMap, "opAdvice");

        if (!visitSiteId.isEmpty()) {
            newMap.put("sendSiteId", visitSiteId);
        }
        if (!opIp.isEmpty()) {
            newMap.put("opIp", opIp);
        }
        if (!opAdvice.isEmpty()) {
            newMap.put("opAdvice", opAdvice);
        }
    }
}
