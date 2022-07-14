package com.hjxlog.thread.create;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Huang JX
 * @date: 2022/7/10
 */
@Slf4j
public class ThreadTest1 extends Thread {
    @Override
    public void run() {
        log.debug("执行线程");
    }

    public static void main(String[] args) {
        ThreadTest1 threadTest1 = new ThreadTest1();
        threadTest1.start();
    }
}
