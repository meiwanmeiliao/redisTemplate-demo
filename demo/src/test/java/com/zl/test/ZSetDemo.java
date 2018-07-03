package com.zl.test;

import com.google.common.collect.Sets;
import com.zl.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * @AUTH zhuolin
 * @DATE 2018/7/2
 * @description:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ZSetDemo {

    private Logger logger = LoggerFactory.getLogger(ZSetDemo.class);

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private static final String REDIS_PREFIX = "zSet:";

    @Test
    public void demo() {
        String demo = "demo:";
        // 添加元素到变量中同时指定元素的分值
        stringRedisTemplate.opsForZSet().add(REDIS_PREFIX + demo + "add", "1", 1);
        stringRedisTemplate.opsForZSet().add(REDIS_PREFIX + demo + "add", "2", 2);
        stringRedisTemplate.opsForZSet().add(REDIS_PREFIX + demo + "add", "3", 3);
        stringRedisTemplate.opsForZSet().add(REDIS_PREFIX + demo + "add", "4", 4);
        stringRedisTemplate.opsForZSet().add(REDIS_PREFIX + demo + "add", "5", 5);
        stringRedisTemplate.opsForZSet().add(REDIS_PREFIX + demo + "add", "11", 1);
        stringRedisTemplate.opsForZSet().add(REDIS_PREFIX + demo + "add", "22", 2);
        stringRedisTemplate.opsForZSet().add(REDIS_PREFIX + demo + "add", "33", 3);
        stringRedisTemplate.opsForZSet().add(REDIS_PREFIX + demo + "add", "44", 4);
        stringRedisTemplate.opsForZSet().add(REDIS_PREFIX + demo + "add", "55", 5);
        // 获取指定区间元素的值
        logger.info(stringRedisTemplate.opsForZSet().range(REDIS_PREFIX + demo + "add", 0, -1).toString());
        //  用于获取满足非score的排序取值。这个排序只有在有相同分数的情况下才能使用，如果有不同的分数则返回值不确定
        logger.info(stringRedisTemplate.opsForZSet().rangeByLex(REDIS_PREFIX + demo + "add",
                new RedisZSetCommands.Range().gt(1)).toString());
        // 用于获取满足非score的设置下标开始的长度排序取值
        logger.info(stringRedisTemplate.opsForZSet().rangeByLex(REDIS_PREFIX + demo + "add",
                new RedisZSetCommands.Range().gt(1)).toString(),
                new RedisZSetCommands.Limit().count(1).offset(1));
        // 根据设置的score获取区间值
        logger.info(stringRedisTemplate.opsForZSet()
                .rangeByScore(REDIS_PREFIX + demo + "add", 1, 5).toString());
        // 根据设置的score获取区间值从给定下标和给定长度获取最终值
        logger.info(stringRedisTemplate.opsForZSet()
                .rangeByScore(REDIS_PREFIX + demo + "add", 1, 5, 1, 3).toString());
        // 获取区间值的个数
        logger.info(stringRedisTemplate.opsForZSet()
                .count(REDIS_PREFIX + demo + "add", 1, 100).toString());
        //  获取变量中元素的索引,下标开始位置为0 若无则为null
        logger.info(stringRedisTemplate.opsForZSet()
                .rank(REDIS_PREFIX + demo + "add", "1").toString());
        // 获取元素的分值 若无返回null
        logger.info(stringRedisTemplate.opsForZSet()
                .score(REDIS_PREFIX + demo + "add", "2").toString());
        // 获取变量中元素的个数
        logger.info(stringRedisTemplate.opsForZSet().zCard(REDIS_PREFIX + demo + "add").toString());
        // 修改变量中的元素的分值 如果没有级直接加入
        logger.info(stringRedisTemplate.opsForZSet()
                .incrementScore(REDIS_PREFIX + demo + "add", "4", 44).toString());
        //  索引倒序排列指定区间元素
        logger.info(stringRedisTemplate.opsForZSet()
                .reverseRange(REDIS_PREFIX + demo + "add", 0, -1).toString());
        // 倒序排列指定分值区间元素
        logger.info(stringRedisTemplate.opsForZSet()
                .reverseRangeByScore(REDIS_PREFIX + demo + "add", 1, 5).toString());
        // 倒序排列从给定下标和给定长度分值区间元素
        logger.info(stringRedisTemplate.opsForZSet()
                .reverseRangeByScore(REDIS_PREFIX + demo + "add", 1, 5, 1, 2).toString());
        // 获取倒序排列的索引值
        logger.info(stringRedisTemplate.opsForZSet()
                .reverseRank(REDIS_PREFIX + demo + "add", "3").toString());
        // 批量移除元素根据元素值
        logger.info(stringRedisTemplate.opsForZSet()
                .remove(REDIS_PREFIX + demo + "add", "1", "2").toString());
        // 根据索引值移除区间元素
        logger.info(stringRedisTemplate.opsForZSet()
                .removeRange(REDIS_PREFIX + demo + "add", 1, 2).toString());
        // 根据分值移除区间元素
        logger.info(stringRedisTemplate.opsForZSet()
                .removeRangeByScore(REDIS_PREFIX + demo + "add", 1, 2).toString());
    }
}
