package com.hjxlog.thread.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: Huang JX
 * @date: 2022/7/9
 */
@Slf4j(topic = "c.Test09")
public class Test09 {

    public static void main(String[] args) {
        TwoPhaseTermination s = new TwoPhaseTermination();
        s.start();

        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        s.stop();
    }

}

@Slf4j(topic = "c.TwoPhaseTermination")
// 两阶段终止模式
class TwoPhaseTermination{
    private Thread moniter;

    // 启动监控线程
    public void start(){
        moniter = new Thread(()->{
            while (true){
                Thread current = Thread.currentThread();
                if(current.isInterrupted()){
                    log.debug("被打断，释放资源...");
                    break;
                }
                try {
                    Thread.sleep(1000);
                    log.debug("执行监控记录");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    // 重新设置打断标记 , 不设置的话，停不下来
                    current.interrupt();
                }

            }
        });
        moniter.start();
    }

    public void stop(){
        moniter.interrupt();
    }
}