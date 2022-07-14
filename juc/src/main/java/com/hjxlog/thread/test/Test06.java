package com.hjxlog.thread.test;

/**
 * @author: Huang JX
 * @date: 2022/7/9
 */
public class Test06 {

    public static void main(String[] args) throws InterruptedException {
        while (true){
            Thread.sleep(500);  // 如果不加sleep() 会导致线程一直占用cpu，其他线程就干不了活了
        }
    }

}
