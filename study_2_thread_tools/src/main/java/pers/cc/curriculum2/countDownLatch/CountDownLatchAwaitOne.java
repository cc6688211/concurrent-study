package pers.cc.curriculum2.countDownLatch;

import java.util.concurrent.CountDownLatch;

import pers.cc.tools.SleepTools;

/**
 * CountDownLatch测试类：装满指定个数货物才能进行配送
 * 
 * 
 * @author cc
 *
 */
public class CountDownLatchAwaitOne {
    // 定义一个总数为30的计数器
    static CountDownLatch latch = new CountDownLatch(30);

    private static class InitThread implements Runnable {

        @Override
        public void run() {
            // 每个人装3个货物
            for (int i = 1; i <= 3; i++) {
                SleepTools.ms(200);
                System.out.println("Thread_" + Thread.currentThread().getId()
                        + "已准备好一个货物！");
                // 初始化线程完成工作了，调用countDown方法扣减一次；
                latch.countDown();
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        // 10个人装，每个人装3个货物
        for (int i = 1; i <= 10; i++) {
            Thread thread = new Thread(new InitThread());
            thread.start();
        }
        latch.await();
        System.out.println("货物装好出发");
    }
}
