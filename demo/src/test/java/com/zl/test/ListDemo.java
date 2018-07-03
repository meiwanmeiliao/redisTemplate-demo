package com.zl.test;

import com.zl.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @AUTH zhuolin
 * @DATE 2018/6/27
 * @description:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ListDemo {

    private Logger logger = LoggerFactory.getLogger(ListDemo.class);

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private static final String REDIS_PREFIX = "list:";

    /**
     * leftPush为倒叙插入 rightPush为正序插入
     * leftPush  ○-> ① ② ③
     * rightPush ① ② ③ <-○
     */
    @Test
    public void pushTest() {
        String push = "push:";
        // leftPush 后push的将放在第一位
        redisTemplate.opsForList().leftPush(REDIS_PREFIX + push + "left_push", 1);
        redisTemplate.opsForList().leftPush(REDIS_PREFIX + push + "left_push", 2);
        redisTemplate.opsForList().leftPush(REDIS_PREFIX + push + "left_push", 3);
        // leftPushAll 按照都相当顺序leftPush
        redisTemplate.opsForList().leftPushAll(REDIS_PREFIX + push + "left_push_all", 1, 2, 3);
        // rightPush 先push的将放在第一位
        redisTemplate.opsForList().rightPush(REDIS_PREFIX + push + "right_push", 1);
        redisTemplate.opsForList().rightPush(REDIS_PREFIX + push + "right_push", 2);
        redisTemplate.opsForList().rightPush(REDIS_PREFIX + push + "right_push", 3);
        // rightPushAll 按照都相当顺序rightPush
        redisTemplate.opsForList().rightPushAll(REDIS_PREFIX + push + "right_push_all", 1, 2, 3);

        // 若redisKey存在则插入否则不插入
        redisTemplate.opsForList().leftPushIfPresent(REDIS_PREFIX + push + "left_push", "3.141592653");

    }

    /**
     * 取出元素 redis中的元素会消失
     * 不存在key则会返回为null
     */
    @Test
    public void popTest() {
        String pop = "pop:";
        logger.info(redisTemplate.opsForList().leftPushAll(REDIS_PREFIX + pop + "left_pop_list", 1, 2, 3, 4).toString());
        // 取出第一个元素
        logger.info(redisTemplate.opsForList().leftPop(REDIS_PREFIX + pop + "left_pop_list").toString());
        // 取出最后一个元素
        logger.info(redisTemplate.opsForList().rightPop(REDIS_PREFIX + pop + "left_pop_list").toString());
        // 将一个redisKey中最后有一个置于另一个redisKey的第一位
        redisTemplate.opsForList().rightPopAndLeftPush(REDIS_PREFIX + pop + "left_pop_list", REDIS_PREFIX + pop + "left_pop_list_1");

        redisTemplate.delete(REDIS_PREFIX + pop + "left_pop_list");
    }

    @Test
    public void other() {
        String other = "other:";
        redisTemplate.opsForList().leftPushAll(REDIS_PREFIX + other + "left_other_list", 1, 2, 3, 4, 5, 6, 7, 8);
        // redisKey的值的数量
        logger.info(redisTemplate.opsForList().size(REDIS_PREFIX + other + "left_other_list").toString());
        // 获取指定范围的值
        logger.info(redisTemplate.opsForList().range(REDIS_PREFIX + other + "left_other_list", 0, 3L).toString());
        // 获取指定位置的值 如果下表不存在 NPE
        logger.info(redisTemplate.opsForList().index(REDIS_PREFIX + other + "left_other_list", 0L).toString());
        // 替换指定位置上的值 若第二个值超过值的数量 会报错ERR no such key
        redisTemplate.opsForList().set(REDIS_PREFIX + other + "left_other_list", 0, 10);
        // 移除key中值为value的i个
        redisTemplate.opsForList().remove(REDIS_PREFIX + other + "left_other_list", 1, 6);
        // 删除除了[start,end]以外的所有元素
        redisTemplate.opsForList().trim(REDIS_PREFIX + other + "left_other_list", 0, 2);
    }


}
