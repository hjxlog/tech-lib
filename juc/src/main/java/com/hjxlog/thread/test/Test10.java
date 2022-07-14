package com.hjxlog.thread.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * @author: Huang JX
 * @date: 2022/7/9
 */
@Slf4j(topic = "c.Test10")
public class Test10 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("park...");
            LockSupport.park();
            log.debug("unpark...");
            log.debug("打断状态：{}", Thread.currentThread().isInterrupted());

//            LockSupport.park();
//            log.debug("unpark...");

        }, "t1");
        t1.start();

        Thread.sleep(500);

        t1.interrupt();
    }
}
