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
        wrapper.eq("user_id", 101);
        wrapper.eq("id", 1L);
        List<Object> list = userMapper.selectObjs(wrapper);
        for (Object user : list) {
            System.out.println(user);
        }
    }


}
