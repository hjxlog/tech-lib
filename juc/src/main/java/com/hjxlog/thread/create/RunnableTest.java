package com.hjxlog.thread.create;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Huang JX
 * @date: 2022/7/8
 */
@Slf4j
public class RunnableTest {
    public static void main(String[] args) {
        // 走的是静态代理
        Runnable runnable = new Runnable() {
            public void run() {
                // 要执行的任务
                log.debug("running");
            }
        };
        // 创建线程对象
        Thread t = new Thread(runnable);
        // 启动线程
        t.start();
        log.debug("running");
    }
}
