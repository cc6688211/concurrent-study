package pers.cc.curriculum1.sleep;

import pers.cc.tools.SleepTools;

/**
 * sleep方法的作用是让当前线程暂停指定的时间（毫秒），sleep方法只是暂时让出CPU的执行权，并不释放锁。而wait方法则需要释放锁。
 * 
 * @author cc
 *
 */
public class SleepTest {
    /**
     * 线程睡眠不释放锁但让出cpu执行权
     */
    public synchronized void sleepMethod() {
        System.out.println("sleepMethod start");
        try {
            Thread.sleep(1000);
            System.out.println("sleepMethod end");
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 线程等待，让出锁，等待被唤醒或自动唤醒
     */
    public synchronized void waitMethod() {
        System.out.println("waitMethod start");
        try {
            wait(1000);
            System.out.println("waitMethod end");
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        System.out.println("测试sleep");
        SleepTest test1 = new SleepTest();
        for (int i = 0; i < 3; i++) {
            new Thread() {
                public void run() {
                    test1.sleepMethod();
                }
            }.start();
        }
        SleepTools.ms(5000);
        System.out.println("测试wait");
        SleepTest test2 = new SleepTest();
        for (int i = 0; i < 3; i++) {
            new Thread() {
                public void run() {
                    test2.waitMethod();
                }
            }.start();
        }
    }
}
