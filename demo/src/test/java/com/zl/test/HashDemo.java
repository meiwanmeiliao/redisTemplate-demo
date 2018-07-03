package com.zl.test;

import com.zl.Application;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @AUTH zhuolin
 * @DATE 2018/7/2
 * @description:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HashDemo {

    private Logger logger = LoggerFactory.getLogger(HashDemo.class);

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private static final String REDIS_PREFIX = "hash:";

    @Test
    public void demo() {
        String demo = "demo:";
        // 新增hashMap值
        stringRedisTemplate.opsForHash().put(REDIS_PREFIX + demo + "put", "map1.key", "map1.value");
        // 以map集合的形式添加键值对
        Map<String, String> map = new HashMap();
        map.put("map1.key", "map1.value");
        map.put("map2.key", "map2.value");
        map.put("map3.key", "map3.value");
        stringRedisTemplate.opsForHash().putAll(REDIS_PREFIX + demo + "putAll", map);
        // 获取指定变量中的hashMap值
        logger.info(stringRedisTemplate.opsForHash().values(REDIS_PREFIX + demo + "putAll").toString());
        // 获取变量中的键值对
        logger.info(stringRedisTemplate.opsForHash().entries(REDIS_PREFIX + demo + "putAll").toString());
        //  获取变量中的指定map键是否有值,如果存在该map键则获取值，没有则返回null
        logger.info(stringRedisTemplate.opsForHash().get(REDIS_PREFIX + demo + "putAll", "map1.key").toString());
        // 判断变量中是否有指定的map键
        logger.info(stringRedisTemplate.opsForHash().hasKey(REDIS_PREFIX + demo + "putAll", "map1").toString());
        // 获取变量中的键
        logger.info(stringRedisTemplate.opsForHash().keys(REDIS_PREFIX + demo + "putAll").toString());
        // 获取变量的长度
        logger.info(stringRedisTemplate.opsForHash().size(REDIS_PREFIX + demo + "putAll").toString());
        // 使变量中的键以double值的大小进行自增长
        logger.info(stringRedisTemplate.opsForHash().increment(REDIS_PREFIX + demo + "increment", "map1", 3).toString());
        // 以集合的方式获取变量中的值
        List keys = Lists.newArrayList("map1.key", "map2.key");
        logger.info(stringRedisTemplate.opsForHash().multiGet(REDIS_PREFIX + demo + "putAll", keys).toString());
        // 如果变量值存在，在变量中可以添加不存在的的键值对，如果变量不存在，则新增一个变量，同时将键值对添加到该变量
        logger.info(stringRedisTemplate.opsForHash().putIfAbsent(REDIS_PREFIX + demo + "putAll", "map1.key", "map1.key--1").toString());
        logger.info(stringRedisTemplate.opsForHash().putIfAbsent(REDIS_PREFIX + demo + "putAll", "map4.key", "map4.key").toString());
        // 删除变量中的键值对，可以传入多个参数，删除多个键值对
        stringRedisTemplate.opsForHash().delete(REDIS_PREFIX + demo + "putAll", "map1.key", "map2.key");
    }


}
