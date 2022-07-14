package com.hjxlog.thread.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author: Huang JX
 * @date: 2022/7/9
 */
@Slf4j(topic = "c.Test04")
public class Test04 {

    public static void main(String[] args) {
        Thread t1 = new Thread("t1") {
            @Override
            public void run() {
                log.debug("t1 进入睡眠...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    log.debug("t1被唤醒....");
                    e.printStackTrace();
                }
                log.debug("t1 结束执行");
            }
        };
        t1.start();

        try {
            Thread.sleep(500);  // 在哪个线程调用，就休眠哪个线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.debug("t1 state {}",t1.getState()); // [main] c.Test03 - t1 state TIMED_WAITING

        log.debug("准备打断线程睡眠");
        t1.interrupt(); // 叫醒t1


//        TimeUnit.SECONDS.sleep(1); // 也可以用这个，增加可读性
    }

}
