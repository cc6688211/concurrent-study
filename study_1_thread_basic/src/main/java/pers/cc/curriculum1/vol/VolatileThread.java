/**
 * @模块名：study_1_thread_basic
 * @包名：pers.cc.curriculum1.vol
 * @描述：VolatileThread.java
 * @版本：1.0
 * @创建人：cc
 * @创建时间：2019年1月21日下午5:45:25
 */

package pers.cc.curriculum1.vol;

import pers.cc.tools.SleepTools;

/**
 * @模块名：study_1_thread_basic
 * @包名：pers.cc.curriculum1.vol
 * @类名称： VolatileThread 使用volatile关键字修饰的变量在复合操作下不具有原子性
 * @类描述：【类描述】
 * @版本：1.0
 * @创建人：cc
 * @创建时间：2019年1月21日下午5:45:25
 */

public class VolatileThread {
    private static volatile Integer i = 0;

    private static Integer j = 0;

    public static class SetRunnable implements Runnable {
        public void run() {
            // 使用volatile关键字修饰的变量在复合操作下不具有原子性
            i = i + 1;
            synchronized (j) {
                j = j + 1;
            }

        }
    }

    public static class ReadRunnable implements Runnable {
        public void run() {
            System.out.println(" ReadRunnable is run i:" + i);
            System.out.println(" ReadRunnable is run j:" + j);
        }
    }

    public static void main(String[] args) {
        for (int k = 0; k < 1000; k++) {
            SetRunnable setRunnable = new SetRunnable();
            new Thread(setRunnable).start();
        }
        SleepTools.second(10);
        ReadRunnable readRunnable1 = new ReadRunnable();
        new Thread(readRunnable1).start();

    }
}
