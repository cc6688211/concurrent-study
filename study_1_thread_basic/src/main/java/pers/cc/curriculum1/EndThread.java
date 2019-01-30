package pers.cc.curriculum1;

/**
 * 
 * @模块名：study_1_thread_basic
 * @包名：pers.cc.curriculum1
 * @类名称： EndThread
 * @类描述：【类描述】如何中断线程
 * @版本：1.0
 * @创建人：cc
 * @创建时间：2019年1月17日下午3:08:25
 */
public class EndThread {

    /**
     * 
     * @模块名：study_1_thread_basic
     * @包名：pers.cc.curriculum1
     * @类名称： UseThread
     * @类描述：【类描述】使用interrupt()安全中断线程
     * @版本：1.0
     * @创建人：cc
     * @创建时间：2019年1月17日下午3:55:23
     */
    private static class UseThread extends Thread {
        public UseThread(String name) {
            super(name);
        }

        public void run() {
            // 判定线程是否中断，不修改中断标志位
            while (!isInterrupted()) {
                try {
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName()
                            + " isInterrupted is " + isInterrupted());
                    interrupt();
                    e.printStackTrace();
                }
                System.out
                        .println(Thread.currentThread().getName() + " is run");
            }
            System.out.println(Thread.currentThread().getName()
                    + " isInterrupted is " + isInterrupted());
        }
    }

    // stop();//不建议使用，强制结束，线程资源可能不会释放
    // suspend();//不建议使用，使线程睡眠，一直占用线程资源，会导致死锁
    // interrupt();//并不是强行关闭这个线程，只是跟这个线程打个招呼，将线程的中断标志位置为true，线程是否中断，由线程本身决定。
    // isInterrupted();//判定线程是否中断，不修改中断标志位
    // interrupted();//判定当前线程是否处于中断状态，同时中断标志位改为false。
    public static void main(String[] args) {

        // 继承Thread类可直接运行
        UseThread useThread = new UseThread("testThread");
         useThread.start();
        try {
            Thread.sleep(500);
        }
        catch (InterruptedException e) {
            System.out.println(e);
        }
        useThread.interrupt();// 中断线程
        // useThread.setPriority(newPriority);//设置线程优先级，1-10，默认为5；不建议使用

    }
}
