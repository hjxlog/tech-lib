package com.hjxlog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Huang JX
 * @date: 2022/6/26
 */
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        return "hello world";
    }

}
