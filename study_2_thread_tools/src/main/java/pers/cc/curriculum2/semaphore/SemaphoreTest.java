package pers.cc.curriculum2.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import pers.cc.tools.SleepTools;

/**
 * 主要用来测试计数信号量的使用
 * 
 * @author cc
 *
 */
public class SemaphoreTest {
    /**
     * 定义数量为10
     */
    private static final int MAX_AVAILABLE = 5;

    /**
     * 设置计数信号量获取顺序为false，即不保证线程获得许可的顺序
     */
    private final Semaphore available = new Semaphore(MAX_AVAILABLE, false);

    /**
     * 定义数量为1，即为互斥锁
     */
    private static final int MAX_AVAILABLE_2 = 1;

    private final Semaphore availableLock = new Semaphore(MAX_AVAILABLE_2, true);

    /**
     * 等待获取许可证，可获取多个
     * 
     * @throws InterruptedException
     */
    public void faliExcepleam() throws InterruptedException {
        for (int i = 0; i < 8; i++) {
            if (i == 5) {
                new Thread() {
                    public void run() {
                        try {
                            System.out.println(Thread.currentThread().getId()
                                    + "准备完成");
                            // 获取3个许可证
                            available.acquire(3);
                            System.out.println(Thread.currentThread().getId()
                                    + "获取许可证");
                            SleepTools.ms(200);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 释放3个许可证
                        available.release(3);
                    }
                }.start();
            }
            else {
                new Thread() {
                    public void run() {
                        try {
                            System.out.println(Thread.currentThread().getId()
                                    + "准备完成");
                            available.acquire();
                            System.out.println(Thread.currentThread().getId()
                                    + "获取许可证");
                            SleepTools.ms(250);
                        }
                        catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        available.release();
                        System.out.println(Thread.currentThread().getId()
                                + "释放许可证");
                    }
                }.start();
            }
            // 保证线程按序等待
            SleepTools.ms(10);
        }

        SleepTools.ms(500);

    }

    /**
     * 互斥
     * 
     * @throws InterruptedException
     */
    public void lock() throws InterruptedException {
        new Thread() {
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getId() + "准备完成");
                    availableLock.acquire();
                    System.out
                            .println(Thread.currentThread().getId() + "获取许可证");
                    SleepTools.ms(500);
                    System.out.println(Thread.currentThread().getId() + "线程结束"
                            + System.currentTimeMillis());
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread() {
            public void run() {
                try {
                    System.out.println(Thread.currentThread().getId()
                            + "准备获取许可证" + System.currentTimeMillis());
                    // 线程在给定的等待时间内可用且当前线程未被中断，则从该信号量获得一个许可或者超时直接执行。
                    availableLock.tryAcquire(300, TimeUnit.MILLISECONDS);
                    System.out.println(Thread.currentThread().getId() + "获取许可证"
                            + System.currentTimeMillis());
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public static void main(String[] args) throws InterruptedException {
        SemaphoreTest semaphoreTest = new SemaphoreTest();
        System.out
                .println("—————————————————————测试faliExcepleam—————————————————————");
        semaphoreTest.faliExcepleam();
        SleepTools.ms(1000);
        System.out.println("—————————————————————测试lock—————————————————————");
        semaphoreTest.lock();
    }
}
