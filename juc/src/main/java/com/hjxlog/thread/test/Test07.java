package com.hjxlog.thread.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Huang JX
 * @date: 2022/7/9
 */
@Slf4j(topic = "c.Test07")
public class Test07 {

    static int r = 0;
    public static void main(String[] args) throws InterruptedException {
        test1();
    }
    private static void test1() throws InterruptedException {
        log.debug("开始");
        Thread t1 = new Thread(() -> {
            log.debug("开始");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.debug("结束");
            r = 10;
            System.out.println("r = " + r);
        },"t1");
        t1.start();

//        t1.join(); // 在主线程里等待t1结束，才会继续向下执行，也就是同步阻塞了
        log.debug("结果为:{}", r);
        log.debug("结束");
    }

}
