package com.hjxlog.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hjxlog.domain.User;
import com.hjxlog.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static com.hjxlog.constants.RedisConstants.*;

/**
 * @author: Huang JX
 * @date: 2022/7/13
 */
@RestController
@Slf4j
@RequestMapping("/redis-login/user")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping("/sendCode")
    public String sendCode(String phone) {
        // 1.校验手机号
        // 2.生成验证码
        if (StringUtils.isBlank(phone)) {
            throw new RuntimeException("电话号码为空");
        }
        String code = RandomUtil.randomNumbers(6);
        // 4.保存验证码到 session ,key login:code:phone  过期时间 2分钟
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + phone, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);
        // 5.发送验证码
        log.debug("发送短信验证码成功，验证码：{}", code);
        // 返回ok
        return "success";
    }

    @PostMapping("/login")
    public String login(@RequestBody User user){
        String phone = user.getPhone();
        // 1.校验手机号

        // 2.如果不符合，返回错误信息

        // 3.从redis获取验证码并校验
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + phone);

        // 不一致，报错

        // 4.一致，根据手机号查询用户 select * from tb_user where phone = ?
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.lambda().eq("phone",phone);

        User user1 = userMapper.selectOne(queryWrapper);

        // 5.判断用户是否存在
        if (user1 == null) {
            // 6.不存在，创建新用户并保存
            return "fail";
        }

        // 7.保存用户信息到 redis中
        // 7.1.随机生成token，作为登录令牌
        String token = UUID.randomUUID().toString(true);
        // 7.2.将User对象转为HashMap存储

        Map<String, Object> userMap = BeanUtil.beanToMap(user1, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));

        // 7.3.存储
        String tokenKey = LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        // 7.4.设置token有效期
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);

        // 8.返回token
        return "success";

    }

}
