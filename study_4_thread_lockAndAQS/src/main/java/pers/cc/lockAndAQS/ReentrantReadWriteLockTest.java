package pers.cc.lockAndAQS;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import pers.cc.tools.SleepTools;

/**
 * 读写锁实例：ReadWriteLock维护一对相关锁，一个用于只读操作，一个用于写入。只要没有写入器，多个读取器线程就可以同时持有读锁。写锁是独占的。
 * 
 * @author cc
 *
 */
public class ReentrantReadWriteLockTest {
    static ReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();

    static Lock rLock = reentrantReadWriteLock.readLock();

    static Lock wLock = reentrantReadWriteLock.writeLock();

    static AtomicLong time = new AtomicLong();

    static boolean boo = false;

    /***
     * 获取当前布尔值
     * 
     * @return
     */
    public static boolean getL() {
        long t = System.currentTimeMillis();
        // 多个线程可以共用同一个读锁
        rLock.lock();
        System.out.println(Thread.currentThread().getId() + "获取读锁"
                + System.currentTimeMillis());
        try {
            SleepTools.ms(5);
            return boo;
        }
        finally {
            System.out.println("总耗时"
                    + time.addAndGet(System.currentTimeMillis() - t));
            rLock.unlock();
        }

    }

    /***
     * 随机修改当前布尔值
     * 
     * @return
     */
    public static void setL() {
        long t = System.currentTimeMillis();
        // 当写锁被获取后，所有想获得读锁和写锁的线程全部等待
        wLock.lock();
        System.out.println(Thread.currentThread().getId() + "获取写锁"
                + System.currentTimeMillis());
        try {
            SleepTools.ms(5);
            Random r = new Random();
            boo = r.nextBoolean();
        }
        finally {
            System.out.println("总耗时"
                    + time.addAndGet(System.currentTimeMillis() - t));
            wLock.unlock();
        }

    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 每20次才修改布尔值
                    if (Thread.currentThread().getId() % 20 == 0) {
                        setL();
                    }
                    else {
                        getL();
                    }
                }
            }).start();
        }
        SleepTools.second(100);
    }
}
