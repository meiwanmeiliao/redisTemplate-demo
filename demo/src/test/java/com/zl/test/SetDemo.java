package com.zl.test;

import com.google.common.collect.Sets;
import com.zl.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @AUTH zhuolin
 * @DATE 2018/6/28
 * @description:
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SetDemo {

    private Logger logger = LoggerFactory.getLogger(SetDemo.class);

    @Autowired
    RedisTemplate redisTemplate;

    private static final String REDIS_PREFIX = "set:";

    /**
     * set 元素保证不会重复
     */
    @Test
    public void addDemo() {
        String add = "add:";
        // 向变量中批量添加值
        redisTemplate.opsForSet().add(REDIS_PREFIX + add + "add", "1");
        redisTemplate.opsForSet().add(REDIS_PREFIX + add + "add_list", "1", "2", "3", "4");
    }

    @Test
    public void remove() {
        //
        String remove = "remove:";
        redisTemplate.opsForSet().add(REDIS_PREFIX + remove + "remove", "1", "2", "3", "4");
        redisTemplate.opsForSet().remove(REDIS_PREFIX + remove + "remove", "1");
        redisTemplate.opsForSet().remove(REDIS_PREFIX + remove + "remove", "1", "2", "3");
    }

    @Test
    public void other() {
        String other = "other:";
        redisTemplate.opsForSet().add(REDIS_PREFIX + other + "other", "1", "2", "3", "4", "3");
        redisTemplate.opsForSet().add(REDIS_PREFIX + other + "other2", "3");// 获取变量中的值
        logger.info(redisTemplate.opsForSet().members(REDIS_PREFIX + other + "other").toString());
        // 获取变量中值的长度
        logger.info(redisTemplate.opsForSet().size(REDIS_PREFIX + other + "other").toString());
        // 随机获取变量中的元素
        logger.info(redisTemplate.opsForSet().randomMember(REDIS_PREFIX + other + "other").toString());
        // 随机获取变量中指定个数的元素
        logger.info(redisTemplate.opsForSet().randomMembers(REDIS_PREFIX + other + "other", 10).toString());
        // 检查给定的元素是否在变量中
        logger.info(redisTemplate.opsForSet().isMember(REDIS_PREFIX + other + "other", "10").toString());
        // 转移变量的元素值到目的变量
        logger.info(redisTemplate.opsForSet().move(REDIS_PREFIX + other + "other", "1", REDIS_PREFIX + other + "other1").toString());
        // 弹出变量中的元素
        logger.info(redisTemplate.opsForSet().pop(REDIS_PREFIX + other + "other").toString());
        // 通过给定的key求2个set变量的差值
        logger.info(redisTemplate.opsForSet().difference(REDIS_PREFIX + other + "other", REDIS_PREFIX + other + "other1").toString());
        // 将求出来的差值元素保存
        logger.info(redisTemplate.opsForSet().differenceAndStore(REDIS_PREFIX + other + "other",
                REDIS_PREFIX + other + "other1", REDIS_PREFIX + other + "other3").toString());
        // 通过集合求差值
        logger.info(redisTemplate.opsForSet()
                .difference(REDIS_PREFIX + other + "other", Sets.newHashSet(REDIS_PREFIX + other + "other1",
                        REDIS_PREFIX + other + "other2")).toString());
        // 通过集合求差值保存
        logger.info(redisTemplate.opsForSet()
                .difference(REDIS_PREFIX + other + "other", Sets.newHashSet(REDIS_PREFIX + other + "other1",
                        REDIS_PREFIX + other + "other2")).toString(), REDIS_PREFIX + other + "other5");
        // 获取去重的随机元素
        logger.info(redisTemplate.opsForSet().distinctRandomMembers(REDIS_PREFIX + other + "other", 1).toString());
        // 获取2个变量中的交集
        logger.info(redisTemplate.opsForSet().intersect(REDIS_PREFIX + other + "other1",
                REDIS_PREFIX + other + "other2").toString());
        // 获取多个变量之间的交集
        logger.info(redisTemplate.opsForSet().intersect(REDIS_PREFIX + other + "other",
                Sets.newHashSet(REDIS_PREFIX + other + "other1", REDIS_PREFIX + other + "other2", REDIS_PREFIX + other + "other3")).toString());
        // 获取2个变量交集后保存到最后一个参数上
        logger.info(redisTemplate.opsForSet().intersectAndStore(REDIS_PREFIX + other + "other",
                REDIS_PREFIX + other + "other1", REDIS_PREFIX + other + "other4").toString());
    }
}
