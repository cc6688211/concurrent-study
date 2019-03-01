package pers.cc.lockAndAQS;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

import pers.cc.tools.SleepTools;

public class ReentrantLockTest {
    // 公平锁
    static ReentrantLock reentrantLock = new ReentrantLock(true);

    static AtomicLong time = new AtomicLong();

    // 非公平锁
    static ReentrantLock failReentrantLock = new ReentrantLock();

    static AtomicLong time1 = new AtomicLong();

    public static void main(String[] args) {
        for (int i = 0; i < 10000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    long t = System.currentTimeMillis();
                    reentrantLock.lock();
                    try {
                        SleepTools.ms(1);
                    }
                    finally {
                        reentrantLock.unlock();
                        System.out.println("公平锁"
                                + Thread.currentThread().getId()
                                + ":"
                                + time.addAndGet(System.currentTimeMillis() - t));
                        ;
                    }

                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    long t = System.currentTimeMillis();
                    failReentrantLock.lock();
                    try {
                        SleepTools.ms(1);
                    }
                    finally {
                        failReentrantLock.unlock();

                        System.out.println("非公平锁"
                                + Thread.currentThread().getId()
                                + ":"
                                + time1.addAndGet(System.currentTimeMillis()
                                        - t));
                    }

                }
            }).start();
        }
    }
}
