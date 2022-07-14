package com.hjxlog.thread.test;

import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;

/**
 * @author: Huang JX
 * @date: 2022/7/9
 */
@Slf4j(topic = "c.Test01")
public class Test01 {

    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug(Thread.currentThread().getName());
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.run();   // 直接调用run，都是主线程main来执行的
        log.debug("do other things ...");
    }

}
