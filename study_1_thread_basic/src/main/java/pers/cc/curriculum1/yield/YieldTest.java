package pers.cc.curriculum1.yield;

/**
 * 进行yield测试
 * 
 * @author cc
 *
 */
public class YieldTest implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
            // 进行线程切换调用
            Thread.yield();
        }

    }

    public static void main(String[] args) {
        new Thread(new YieldTest(), "first").start();
        new Thread(new YieldTest(), "second").start();
    }
}
