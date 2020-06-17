package com.example.demo;

import com.example.demo.config.RedisConfig;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class JavaRedisDemoApplicationTests {

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private StringRedisTemplate stringRedisTemplate; // 较为常用

	@Test
	void contextLoads() {
	}

    /**
     * redis string测试
     */
	@Test
	void redisString(){
		redisTemplate.opsForValue().set("k1", "v1");
		Object k1 = redisTemplate.opsForValue().get("k1");
		Assert.assertEquals("v1", k1);
		stringRedisTemplate.opsForValue().set("k2", "v2");
		Object k2 = stringRedisTemplate.opsForValue().get("k2");
		Assert.assertEquals("v2", k2);
	}

    /**
     * redis hash测试
     */
	@Test
	void redisHash(){
		Map map1 = new HashMap();
		map1.put("a", 1);
		map1.put("b", 2);
		map1.put("c", 3);
		redisTemplate.opsForHash().putAll("m1", map1);
		Map<Object, Object> m1 = redisTemplate.opsForHash().entries("m1");
		Assert.assertEquals(map1, m1);
		Map<Object, Object> n1 = redisTemplate.opsForHash().entries("n1");
		Assert.assertTrue(n1.isEmpty());

		Map map2 = new HashMap();
		map2.put("a", "1");
		map2.put("b", "2");
		map2.put("c", "3");
		stringRedisTemplate.opsForHash().putAll("m2", map2);
		Map<Object, Object> m2 = stringRedisTemplate.opsForHash().entries("m2");
		Assert.assertEquals(map2, m2);
		Map<Object, Object> n2 = stringRedisTemplate.opsForHash().entries("n2");
		Assert.assertTrue(n2.isEmpty());
	}

    /**
     * 发布与订阅测试
     * redisTemplate为json序列化
     * stringRedisTemplate没有自定义,使用的string序列化
     */
	@Test
	void redisPublish(){
	    // 发布端使用json序列化,消费端也要使用json序列化
		redisTemplate.convertAndSend(RedisConfig.Channel.A.toString(), "message test ...");
        // 发布端使用string序列化,消费端也要使用string序列化
		stringRedisTemplate.convertAndSend(RedisConfig.Channel.B.toString(), "message test ...");
	}


}
