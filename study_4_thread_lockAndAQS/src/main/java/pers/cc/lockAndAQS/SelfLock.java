package pers.cc.lockAndAQS;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import pers.cc.tools.SleepTools;

/**
 * 实现AQS构造一个独占锁
 * 
 * @author cc
 *
 */
public class SelfLock implements Lock {
    /**
     * 构建一个内部类来实现AQS
     * 
     * @author cc
     *
     */
    private static class Sync extends AbstractQueuedSynchronizer {
        /**
         * 判断state()大小是否为1，为1则被占用
         */
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        /**
         * 获取信号量
         */
        protected boolean tryAcquire(int arg) {
            // 原子操作设置state值为1，成功返回true
            if (compareAndSetState(0, 1)) {
                // 设置独占线程
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        /**
         * 释放信号量。若当前信号量未被占用则抛出异常，反之清空占用线程，设置state为0
         */
        protected boolean tryRelease(int arg) {
            if (getState() == 0) {
                throw new UnsupportedOperationException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        Condition newCondition() {
            return new ConditionObject();
        }
    }

    private final Sync sycn = new Sync();

    @Override
    public void lock() {
        // 调用框架方法获取1个信号量
        sycn.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sycn.acquireInterruptibly(1);

    }

    @Override
    public boolean tryLock() {
        return sycn.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit)
            throws InterruptedException {
        return sycn.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sycn.release(1);

    }

    @Override
    public Condition newCondition() {
        return sycn.newCondition();
    }

    public static void main(String[] args) {
        Lock lock = new SelfLock();

        class Worker extends Thread {
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        SleepTools.second(1);
                        System.out.println(Thread.currentThread().getName());
                        SleepTools.second(1);
                    }
                    finally {
                        lock.unlock();
                    }
                    SleepTools.second(2);
                }
            }
        }
        // 启动10个子线程
        for (int i = 0; i < 10; i++) {
            Worker w = new Worker();
            w.setDaemon(true);
            w.start();
        }
        // 主线程每隔1秒换行
        for (int i = 0; i < 20; i++) {
            SleepTools.second(1);
            System.out.println();
        }
    }
}
