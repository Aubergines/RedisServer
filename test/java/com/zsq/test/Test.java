package com.zsq.test;

import com.zsq.redis.RedisPool;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.SortingParams;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Test {

	public static void main(String[] args) {
		Test t = new Test();
//		Date date = new Date("Thu Mar 12 20:00:00 CST 2015");
//		System.err.println(date);
//		System.err.println(date.getTime());
//
//		long buildId = buildId(1426212000, 0, 53, 4);
//		System.err.println(buildId);
//		System.err.println(parseId(buildId));
		t.testSort5();
	}
	
	public static long buildId(long second, long microSecond, long shardId,
			long seq) {
		long miliSecond = (second * 1000 + microSecond / 1000);
		return (miliSecond << (12 + 10)) + (shardId << 10) + seq;
	}

	public static List<Long> parseId(long id) {
		long miliSecond = id >>> 22;
		// 2 ^ 12 = 0xFFF
		long shardId = (id & (0xFFF << 10)) >> 10;
		long seq = id & 0x3FF;

		List<Long> re = new ArrayList<Long>(4);
		re.add(miliSecond);
		re.add(shardId);
		re.add(seq);
		return re;
	}

	public void testSort5() {
		// 排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较
		Jedis jedis = RedisPool.getResource();
		// 一般SORT用法 最简单的SORT使用方法是SORT key。
		jedis.lpush("mylist", "1");
		jedis.lpush("mylist", "4");
		jedis.lpush("mylist", "6");
		jedis.lpush("mylist", "3");
		jedis.lpush("mylist", "0");
		// List<String> list = redis.sort("sort");// 默认是升序
		SortingParams sortingParameters = new SortingParams();
		sortingParameters.desc();
		// sortingParameters.alpha();//当数据集中保存的是字符串值时，你可以用 ALPHA
		// 修饰符(modifier)进行排序。
		// sortingParameters.limit(0, 2);//可用于分页查询

		// 没有使用 STORE 参数，返回列表形式的排序结果. 使用 STORE 参数，返回排序结果的元素数量。

		jedis.sort("mylist", sortingParameters, "mylist");// 排序后指定排序结果到一个KEY中，这里讲结果覆盖原来的KEY

		List<String> list = jedis.lrange("mylist", 0, -1);
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i));
		}

		jedis.sadd("tom:friend:list", "123"); // tom的好友列表
		jedis.sadd("tom:friend:list", "456");
		jedis.sadd("tom:friend:list", "789");
		jedis.sadd("tom:friend:list", "101");

		jedis.set("score:uid:123", "1000"); // 好友对应的成绩
		jedis.set("score:uid:456", "6000");
		jedis.set("score:uid:789", "100");
		jedis.set("score:uid:101", "5999");

		jedis.set("uid:123", "{'uid':123,'name':'lucy'}"); // 好友的详细信息
		jedis.set("uid:456", "{'uid':456,'name':'jack'}");
		jedis.set("uid:789", "{'uid':789,'name':'jay'}");
		jedis.set("uid:101", "{'uid':101,'name':'jolin'}");

		sortingParameters = new SortingParams();
		// sortingParameters.desc();
		sortingParameters.get("#");// GET 还有一个特殊的规则—— "GET #"
		// ，用于获取被排序对象(我们这里的例子是 user_id )的当前元素。
		sortingParameters.by("score:uid:*");
		jedis.sort("tom:friend:list", sortingParameters, "tom:friend:list");
		List<String> result = jedis.lrange("tom:friend:list", 0, -1);
		for (String item : result) {
			System.out.println("item..." + item);
		}

	}


	public void testSort6() {
		// 排序默认以数字作为对象，值被解释为双精度浮点数，然后进行比较
		Jedis jedis = RedisPool.getResource();
		// 一般SORT用法 最简单的SORT使用方法是SORT key。
//		jedis.lpush("mylist", "1");
//		jedis.lpush("mylist", "4");
//		jedis.lpush("mylist", "6");
//		jedis.lpush("mylist", "3");
//		jedis.lpush("mylist", "0");
		// List<String> list = redis.sort("sort");// 默认是升序
		SortingParams sortingParameters = new SortingParams();
		sortingParameters.desc();
		// sortingParameters.alpha();//当数据集中保存的是字符串值时，你可以用 ALPHA
		// 修饰符(modifier)进行排序。
		// sortingParameters.limit(0, 2);//可用于分页查询

		// 没有使用 STORE 参数，返回列表形式的排序结果. 使用 STORE 参数，返回排序结果的元素数量。

		jedis.sort("mylist", sortingParameters, "mylist");// 排序后指定排序结果到一个KEY中，这里讲结果覆盖原来的KEY

//		List<String> list = jedis.lrange("mylist", 0, -1);
//		for (int i = 0; i < list.size(); i++) {
//			System.out.println(list.get(i));
//		}

//		jedis.sadd("tom:friend:list", "123"); // tom的好友列表
//		jedis.sadd("tom:friend:list", "456");
//		jedis.sadd("tom:friend:list", "789");
//		jedis.sadd("tom:friend:list", "101");
//
//		jedis.set("score:uid:123", "1000"); // 好友对应的成绩
//		jedis.set("score:uid:456", "6000");
//		jedis.set("score:uid:789", "100");
//		jedis.set("score:uid:101", "5999");
//
//		jedis.set("uid:123", "{'uid':123,'name':'lucy'}"); // 好友的详细信息
//		jedis.set("uid:456", "{'uid':456,'name':'jack'}");
//		jedis.set("uid:789", "{'uid':789,'name':'jay'}");
//		jedis.set("uid:101", "{'uid':101,'name':'jolin'}");

		List list = new ArrayList();
		Set<String> hkeys = jedis.hkeys("a:cb:*");
		for (String hkey : hkeys) {
			Map<String, String> stringStringMap = jedis.hgetAll(hkey);
			list.add(stringStringMap);
		}
		sortingParameters = new SortingParams();
		// sortingParameters.desc();
		sortingParameters.get("#");// GET 还有一个特殊的规则—— "GET #"
		// ，用于获取被排序对象(我们这里的例子是 user_id )的当前元素。
//		sortingParameters.by("score:uid:*");
		sortingParameters.by("a:cb:*->customerId");
		sortingParameters.limit(0,20);
		jedis.sort("tom:friend:list", sortingParameters);
		List<String> result = jedis.lrange("tom:friend:list", 0, -1);
		for (String item : result) {
			System.out.println("item..." + item);
		}

//		jedis.flushDB();
	}
}
