package com.zsq.test;

import com.alibaba.fastjson.JSON;

import java.util.*;

import static com.zsq.redis.RedisObject.scanKeys;

/**
 * Created by Aubergine on 2016/8/20.
 */
public class ScanTest {

    public static void main(String[] args) {

        List<String> response_data_list = null;
        try {
            Set<String> set = new HashSet<String>();
            String scanRet = "0";
            do {
                scanRet = scanKeys(scanRet, "a:doc:baseinfo:139*",set);
            } while (!scanRet.equals("0"));
            if (set != null && set.size() > 0) {
                response_data_list = new ArrayList<String>();
                Iterator<String> i = set.iterator();
//                while (i.hasNext()) {
//                    String keyStr = i.next();
//                    String string = RedisObject.hgetall(keyStr);
//                    response_data_list.add(string);
//                }
            }
            System.out.println(JSON.toJSONString(set));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
