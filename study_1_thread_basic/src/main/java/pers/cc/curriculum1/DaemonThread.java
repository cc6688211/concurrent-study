/**
 * @模块名：study_1_thread_basic
 * @包名：pers.cc.curriculum1
 * @描述：DaemonThread.java
 * @版本：1.0
 * @创建人：cc
 * @创建时间：2019年1月21日上午9:37:56
 */

package pers.cc.curriculum1;

/**
 * @模块名：study_1_thread_basic
 * @包名：pers.cc.curriculum1
 * @类名称： DaemonThread
 * @类描述：【类描述】守护线程
 * @版本：1.0
 * @创建人：cc
 * @创建时间：2019年1月21日上午9:37:56
 */

public class DaemonThread {
    /**
     * 守护线程和主线程共死，finally不能保证一定执行
     */
    private static class UseThread extends Thread {
        public void run() {
            // 判定线程是否中断，不修改中断标志位
            try {

                while (!isInterrupted()) {
                    System.out.println(Thread.currentThread().getName()
                            + " is run");
                }
                // 守护线程
            }
            finally {
                System.out.println("finally is run");
            }
            System.out.println(Thread.currentThread().getName()
                    + " isInterrupted is " + isInterrupted());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        // 继承Thread类可直接运行
        UseThread useThread = new UseThread();
        useThread.setDaemon(true);// 设置为守护线程，一定要在start()之前调用
        useThread.start();
        Thread.sleep(5);
    }
}
