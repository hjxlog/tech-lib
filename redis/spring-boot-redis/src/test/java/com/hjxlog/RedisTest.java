package com.hjxlog;

import com.hjxlog.domain.User;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author: Huang JX
 * @date: 2022/7/13
 */
@SpringBootTest
public class RedisTest {

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Test
    void testString() {
        // 插入一条数据
        redisTemplate.opsForValue().set("name", "李四");
        // 取出数据
        Object name = redisTemplate.opsForValue().get("name");
        System.out.println("name = " + name);

    }

    @Test
    void testSaveUser(){
//        User user = new User(1,"张三",22);
//        redisTemplate.opsForValue().set("user:1",user);
//        User rlt = (User)redisTemplate.opsForValue().get("user:1");
//        System.out.println("rlt = " + rlt);
    }

}
