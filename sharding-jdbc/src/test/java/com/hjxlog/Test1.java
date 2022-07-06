package com.hjxlog;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hjxlog.domain.User;
import com.hjxlog.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

/**
 * 功能描述：
 *
 * @author: Huang JX
 * @date: 2022年07月05日 15:47
 */
@SpringBootTest
public class Test1 {

    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {
        for (int i = 0; i < 100; i++) {
            User user = new User();
//            user.setId((long) i);
            user.setName("test" + i);
            user.setUserId(i + 100);
            userMapper.insert(user);
        }
    }

    @Test
    void search() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", 102);
        wrapper.eq("id", 1544317255182626818L);
        List<Object> list = userMapper.selectObjs(wrapper);
        for (Object user : list) {
            System.out.println(user);
        }
        int count = userMapper.selectCount(null);
        System.out.println("count = " + count);
    }

    @Test
    void search1() {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getId, 1544317255182626818L)
            .eq(User::getUserId,102);
        List<User> list = userMapper.selectList(wrapper);
        for (User user : list) {
            System.out.println(user);
        }
    }


}
