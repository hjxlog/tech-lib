package com.hjxlog.thread.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Huang JX
 * @date: 2022/7/9
 */
@Slf4j(topic = "c.Test02")
public class Test02 {

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
        System.out.println("t1.getState() = " + t1.getState());
        t1.start();
        System.out.println("t1.getState() = " + t1.getState());
        log.debug("do other things ...");
    }

}
