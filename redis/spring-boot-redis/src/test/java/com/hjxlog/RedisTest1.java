package com.hjxlog;

import cn.hutool.json.JSONUtil;
import com.hjxlog.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author: Huang JX
 * @date: 2022/7/13
 */
@SpringBootTest
public class RedisTest1 {

    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Test
    void testString() {
        // 插入一条数据
        stringRedisTemplate.opsForValue().set("name", "王五");
        // 取出数据
        Object name = stringRedisTemplate.opsForValue().get("name");
        System.out.println("name = " + name);

    }

    @Test
    void testSaveUser() {
        User user = new User(1, "张三", 22, "1233");
        // 手动序列化e
        String jsonString = JSONUtil.toJsonStr(user);

        stringRedisTemplate.opsForValue().set("user:1", jsonString);
        String json = stringRedisTemplate.opsForValue().get("user:1");

        // 手动反序列化
        User parse = JSONUtil.toBean(json, User.class);
        System.out.println("parse = " + parse);
    }

    @Test
    void testHash() {
        stringRedisTemplate.opsForHash().put("user:2", "name", "李四");
        stringRedisTemplate.opsForHash().put("user:2", "age", "12");

        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries("user:2");
        System.out.println("map = " + map);
    }

}
