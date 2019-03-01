package pers.cc.lockAndAQS;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 控制器测试等待唤醒
 * 
 * @author cc
 *
 */
public class ConditionTest {
    /**
     * 创建一个可重入锁
     */
    static Lock lock = new ReentrantLock();

    static Condition cond = lock.newCondition();

    static AtomicInteger pernum = new AtomicInteger();

    /**
     * 等待触发，当初上人数满45人出发
     * 
     * @throws InterruptedException
     */
    public static void waitGo() throws InterruptedException {
        lock.lock();
        try {
            cond.await();
            System.out.println("汽车满员出发");
        }
        finally {
            lock.unlock();
        }
    }

    /**
     * 没到达一个人数加1
     */
    public static void arrive() {

        lock.lock();
        try {
            int i = pernum.incrementAndGet();
            System.out.println("现有人数" + i);
            if (i == 45) {
                cond.signal();
            }
        }
        finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 等待出发
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    waitGo();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
        for (int i = 0; i < 45; i++) {
            new Thread(new Runnable() {

                @Override
                public void run() {
                    arrive();
                }
            }).start();
        }
    }
}
