package pers.cc.curriculum1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @模块名：study_1_thread_basic
 * @包名：pers.cc.curriculum1
 * @类名称： NewThread
 * @类描述：【类描述】java新启线程的三种方式
 * @版本：1.0
 * @创建人：cc
 * @创建时间：2019年1月17日上午10:41:34
 */

public class NewThread {

    /**
     * 
     * @模块名：study_1_thread_basic
     * @包名：pers.cc.curriculum1
     * @类名称： UseThread
     * @类描述：【类描述】继承Thread类
     * @版本：1.0
     * @创建人：cc
     * @创建时间：2019年1月17日下午2:01:07
     */
    private static class UseThread extends Thread {
        public void run() {
            super.run();
            System.out.println(Thread.currentThread().getName()
                    + " UseThread is run");
        }
    }

    /**
     * 
     * @模块名：study_1_thread_basic
     * @包名：pers.cc.curriculum1
     * @类名称： UseRunnable
     * @类描述：【类描述】实现Runnable接口
     * @版本：1.0
     * @创建人：cc
     * @创建时间：2019年1月17日下午2:01:54
     */
    public static class UseRunnable implements Runnable {
        public void run() {
            System.out.println(Thread.currentThread().getName()
                    + " UseRunnable is run");
        }
    }

    /**
     * 
     * @模块名：study_1_thread_basic
     * @包名：pers.cc.curriculum1
     * @类名称： UseCallable
     * @类描述：【类描述】实现Callable接口
     * @版本：1.0
     * @创建人：cc
     * @创建时间：2019年1月17日下午2:02:16
     */
    private static class UseCallable implements Callable < String > {

        @Override
        public String call() throws Exception {
            System.out.println(Thread.currentThread().getName()
                    + " UseCallable is run");
            return "UseCallable";
        }

    }

    public static void main(String[] args) {
        // 继承Thread类可直接运行
        UseThread useThread = new UseThread();
        useThread.start();
        // 实现Runnable接口，需借助Thread运行
        UseRunnable useRunnable = new UseRunnable();
        new Thread(useRunnable).start();

        UseCallable useCallable = new UseCallable();
        // 1.首先运用futureTask包装Callable
        FutureTask < String > futureTask = new FutureTask <>(useCallable);
        // 2.借助Thread运行
        new Thread(futureTask).start();
        try {
            // 3.从FutureTask获取结果
            System.out.println("result:" + futureTask.get());
        }
        catch (InterruptedException | ExecutionException e) {
            System.out.println(e);
        }
    }
}
