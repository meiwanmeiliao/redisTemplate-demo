package com.zl.test;

import com.zl.Application;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @AUTH zhuolin
 * @DATE 2018/7/2
 * @description:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ValueDemo {

    private Logger logger = LoggerFactory.getLogger(ValueDemo.class);

    @Autowired
    StringRedisTemplate redisTemplate;

    private static final String REDIS_PREFIX = "value:";

    @Test

    public void test() throws InterruptedException {
        String add = "test";
        // 新增一个值,key是键，value是值
        redisTemplate.opsForValue().set(REDIS_PREFIX + add, "add_demo");
        // 获取key键对应的值
        logger.info(redisTemplate.opsForValue().get(REDIS_PREFIX + add).toString());
        // 在原有的值基础上新增字符串到末尾
        logger.info(redisTemplate.opsForValue().append(REDIS_PREFIX + add, "_append").toString());
        // 截取key键对应值得字符串，从开始下标位置开始到结束下标的位置(包含结束下标)的字符串
        logger.info(redisTemplate.opsForValue().get(REDIS_PREFIX + add, 2, 3).toString());
        // 获取原来key键对应的值并重新赋新值
        logger.info(redisTemplate.opsForValue().getAndSet(REDIS_PREFIX + add, "add_reset").toString());
        // 获取指定字符串的长度。
        logger.info(redisTemplate.opsForValue().size(REDIS_PREFIX + add).toString());
        // 以增量的方式将long值存储在变量中
        logger.info(redisTemplate.opsForValue().increment(REDIS_PREFIX + ":increment", 5).toString());
        //设置值和超时时间
        redisTemplate.opsForValue().set(REDIS_PREFIX + ":outTime", "123", 1000, TimeUnit.MILLISECONDS);
        logger.info(redisTemplate.hasKey(REDIS_PREFIX + ":outTime").toString());
        Thread.sleep(1000L);
        logger.info(redisTemplate.hasKey(REDIS_PREFIX + ":outTime").toString());
        // 覆盖从指定位置开始的值
        redisTemplate.opsForValue().set(REDIS_PREFIX + add, "reset-add", 0);
        // 设置map集合到redis
        Map valueMap = new HashMap();
        valueMap.put(REDIS_PREFIX + "multiSet:1", "map1");
        valueMap.put(REDIS_PREFIX + "multiSet:2", "map2");
        valueMap.put(REDIS_PREFIX + "multiSet:3", "map3");
        redisTemplate.opsForValue().multiSet(valueMap);
        // 根据集合取出对应的value值
        logger.info(redisTemplate.opsForValue().multiGet(
                Lists.newArrayList(REDIS_PREFIX + "multiSet:1", REDIS_PREFIX + "multiSet:2", REDIS_PREFIX + "multiSet:3")).toString());
        // 如果对应的map集合名称不存在，则添加，如果存在则不做修改。
        Map valueInfoMap = new HashMap();
        valueInfoMap.put(REDIS_PREFIX + "multiSet:4", "map4");
//        valueInfoMap.put(REDIS_PREFIX + "multiSet:1", "map_1");
        logger.info(redisTemplate.opsForValue().multiSetIfAbsent(valueInfoMap).toString());
    }
}
