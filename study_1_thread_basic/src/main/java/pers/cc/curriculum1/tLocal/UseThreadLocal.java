/**
 * @模块名：study_1_thread_basic
 * @包名：pers.cc.curriculum1.tLocal
 * @描述：UseThreadLocal.java
 * @版本：1.0
 * @创建人：cc
 * @创建时间：2019年1月29日下午5:01:45
 */

package pers.cc.curriculum1.tLocal;

/**
 * @模块名：study_1_thread_basic
 * @包名：pers.cc.curriculum1.tLocal
 * @类名称： UseThreadLocal
 * @类描述：【类描述】 ThreadLocal叫做线程本地变量，也有些地方叫做线程本地存储；ThreadLocal为变量在每个线程中都创建了一个副本，
 *            那么每个线程可以访问自己内部的副本变量。
 * @版本：1.0
 * @创建人：cc
 * @创建时间：2019年1月29日下午5:01:45
 */

public class UseThreadLocal {
    // 定义一个ThreadLocal并定义初始方法
    static ThreadLocal < Integer > threadLocal = new ThreadLocal < Integer >() {
        protected Integer initialValue() {
            return 1;
        }
    };

    public static class TestThread implements Runnable {
        int id;

        public TestThread(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            id = id + threadLocal.get();
            threadLocal.set(id);
            System.out.println("id:" + id + "  threadLocal:"
                    + threadLocal.get());
        }

    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new TestThread(i));
            t.start();
        }
    }
}
