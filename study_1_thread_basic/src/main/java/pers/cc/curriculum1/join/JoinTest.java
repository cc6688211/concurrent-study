package pers.cc.curriculum1.join;

import pers.cc.tools.SleepTools;

/**
 * 进行join测试
 * 
 * @author cc
 *
 */
public class JoinTest {
    /**
     * 进行线程嵌套
     * 
     * @author cc
     *
     */
    static class JumpQueue implements Runnable {
        private Thread thread;// 用来插队的线程

        public JumpQueue(Thread thread) {
            this.thread = thread;
        }

        public void run() {
            try {
                System.out.println(thread.getName() + " will be join before "
                        + Thread.currentThread().getName());
                thread.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out
                    .println(Thread.currentThread().getName() + " terminted.");
        }
    }

    public static void main(String[] args) {
        Thread previous = Thread.currentThread();// 现在是主线程
        for (int i = 0; i < 10; i++) {
            // 新建线程并命名为i嵌套在线程previous下
            Thread thread = new Thread(new JumpQueue(previous),
                    String.valueOf(i));
            // System.out.println(previous.getName() +
            // " jump a queue the thread:"
            // + thread.getName());
            thread.start();
            // 更换主嵌套线程
            previous = thread;
        }
        SleepTools.second(2);// 让主线程休眠2秒
        System.out.println(Thread.currentThread().getName() + " terminate.");
    }
}
