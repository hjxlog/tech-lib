package com.hjxlog.thread.create;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Huang JX
 * @date: 2022/7/8
 */
@Slf4j(topic = "c.ThreadTest")
public class ThreadTest {

    public static void main(String[] args) {
        Thread thread = new Thread(){
            // 匿名内部类
            @Override
            public void run() {
                log.debug("running");
            }
        };
        thread.setName("t1");
        thread.start();

        log.debug("main running");
    }

}
