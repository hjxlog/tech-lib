package com.hjxlog.thread.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Huang JX
 * @date: 2022/7/9
 */
@Slf4j(topic = "c.Test03")
public class Test03 {

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
                log.debug("t1 结束执行");
            }
        };
        System.out.println("t1.getState() = " + t1.getState());
        t1.start();
        System.out.println("t1.getState() = " + t1.getState());

        try {
            Thread.sleep(500);  // 在哪个线程调用，就休眠哪个线程
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.debug("t1 state {}",t1.getState()); // [main] c.Test03 - t1 state TIMED_WAITING

    }

}
