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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @AUTH zhuolin
 * @DATE 2018/6/27
 * @description:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTemplateDemo {

    private Logger logger = LoggerFactory.getLogger(RedisTemplateDemo.class);

    // opsFroHash stringRedisTemplate性能好于redisTemplate
    // opsFroZSet redisTemplate性能好于stringRedisTemplate
    // opsForSet redisTemplate性能好于stringRedisTemplate
    // opsForList stringRedisTemplate性能好于redisTemplate
    // opsForValue stringRedisTemplate性能略好于redisTemplate

    @Autowired
    RedisTemplate redisTemplate;

    private static final String REDIS_PREFIX = "demo:";

    @Test
    public void test() {
        redisTemplate.opsForValue().set("1111", "11111");
        redisTemplate.opsForValue().set("1113", "11111");
        // 判断redis是否含有key值
        logger.info(redisTemplate.hasKey("1111").toString());
        // 查询所有符合条件的key
        logger.info(redisTemplate.keys("111" + "*").toString());
        // 重命名键值
        redisTemplate.rename("1111", "1112");
        // 设置过期时间
        logger.info(redisTemplate.expire("1112", 100, TimeUnit.MINUTES).toString());
        // 设置过期时间
        logger.info(redisTemplate.expireAt("1112", new Date(2018, 10, 1)).toString());
        // 获取链接客户端
        logger.info(redisTemplate.getClientList().toString());
        // 获取默认的序列化方式
        logger.info(redisTemplate.getDefaultSerializer().toString());
        // 获取过期时间
        logger.info(redisTemplate.getExpire("1112").toString());
        // 删除键值
        redisTemplate.delete(Lists.newArrayList("1112", "1113"));
    }
}
