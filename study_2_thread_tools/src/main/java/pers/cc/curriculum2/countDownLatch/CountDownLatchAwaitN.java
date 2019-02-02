package pers.cc.curriculum2.countDownLatch;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch测试类：10个运动员一一准备，等待统一听发令枪一起起跑
 * 
 * 
 * @author cc
 *
 */
public class CountDownLatchAwaitN {
    // 定义一个总数为14的计数器
    static CountDownLatch latch = new CountDownLatch(14);

    private static class InitThread implements Runnable {
        private String name;

        public InitThread(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println(name + "已准备好！");
            // 初始化线程完成工作了，调用countDown方法扣减一次；
            latch.countDown();
            try {
                // 堵塞所有子进程等候计数器号令
                latch.await();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + "启动：" + System.currentTimeMillis());

        }

    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 10; i++) {
            Thread thread = new Thread(new InitThread("运动员" + i));
            thread.start();
        }
        Thread.sleep(1000);
        System.out.println("3");
        latch.countDown();
        System.out.println("2");
        latch.countDown();
        System.out.println("1");
        latch.countDown();
        System.out.println("啪");
        latch.countDown();
    }
}
