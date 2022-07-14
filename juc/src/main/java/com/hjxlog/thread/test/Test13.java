package com.hjxlog.thread.test;

import com.hjxlog.thread.create.ThreadTest;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Huang JX
 * @date: 2022/7/9
 */
@Slf4j
public class Test13 {

    public static void main(String[] args) throws InterruptedException {

        log.debug("main...start");
        Test13 test13 = new Test13();

        Thread t1 = new Thread(() -> {
            log.debug("t1 start...");
            List<String> data = null;
            try {
                data = test13.getData();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("data = " + data);
            log.debug("t1 end...");
        });

        Thread t2 = new Thread(() -> {
            int count = 0;
            try {
                count = test13.getCount();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("count = " + count);
        });


        t1.start();
        t2.start();

        t1.join();
        t2.join();

        log.debug("main...end");


    }

    public List<String> getData() throws InterruptedException {
        List<String> list = new ArrayList<>();
        Thread.sleep(2000);
        list.add("hello");
        list.add("world");
        return list;
    }

    public int getCount() throws InterruptedException {
        Thread.sleep(5000);
        return 100;
    }

}
