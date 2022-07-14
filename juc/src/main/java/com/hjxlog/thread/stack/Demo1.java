package com.hjxlog.thread.stack;

/**
 * @author: Huang JX
 * @date: 2022/7/9
 */
public class Demo1 {

    /**
     * 通过debug可以看到，每个线程的栈内存是独立的
     * @param args
     */
    public static void main(String[] args) {
        Thread t1 = new Thread(){
            @Override
            public void run() {
                System.out.println("来到t1线程...");
                method1(20);
            }
        };
        t1.setName("t1");
        t1.start();
//        t1.start();  // 不能多次调用，报错IllegalThreadStateException
        method1(10);
    }

    public static void method1(int x) {
        int y = x + 1;
        Object o = method2(y + "");
        System.out.println(o);
    }

    public static Object method2(String s) {
        System.out.println(s);
        return new Object();
    }

}
