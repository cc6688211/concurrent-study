/**
 * @模块名：study_1_thread_basic
 * @包名：pers.cc.curriculum1.syn
 * @描述：SynClass.java 
 * @版本：1.0
 * @创建人：cc
 * @创建时间：2019年1月21日下午12:26:37
 */

package pers.cc.curriculum1.syn;

import pers.cc.tools.SleepTools;

/**
 * @模块名：study_1_thread_basic
 * @包名：pers.cc.curriculum1.syn
 * @类名称： SynClass
 * @类描述：【类描述】synchronized关键字对象锁的用法:类锁，实际上锁的就是类的对象
 * @版本：1.0
 * @创建人：cc
 * @创建时间：2019年1月21日下午12:26:37
 */

public class SynClass {
    /**
     * 
     * @模块名：study_1_thread_basic
     * @包名：pers.cc.curriculum1.syn
     * @类名称： ClassSyn
     * @类描述：【类描述】使用类锁的线程
     * @版本：1.0
     * @创建人：cc
     * @创建时间：2019年1月21日下午12:24:02
     */
    private static class ClassSyn extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()
                    + " is running...");
            instanceStatic();
        }
    }

    /**
     * 
     * @方法名：instanceStatic
     * @方法描述【方法功能描述】锁类;实际上锁的是类的对象
     * @修改描述【修改描述】
     * @版本：1.0
     * @创建人：cc
     * @创建时间：2019年1月21日 下午12:21:15
     * @修改人：cc
     * @修改时间：2019年1月21日 下午12:21:15
     */
    private static synchronized void instanceStatic() {
        SleepTools.second(1);
        System.out.println(Thread.currentThread().getName() + " is going...");
        SleepTools.second(2);
        System.out.println(Thread.currentThread().getName() + " ended ");
    }
    /**
     * 
     * @模块名：study_1_thread_basic
     * @包名：pers.cc.curriculum1.syn
     * @类名称： ClassSyn
     * @类描述：【类描述】使用类锁的线程
     * @版本：1.0
     * @创建人：cc
     * @创建时间：2019年1月21日下午12:24:02
     */
    private static class ClassSyn1 extends Thread {
        private SynClass synClass;

        public ClassSyn1(SynClass synClass) {
            this.synClass = synClass;
        }
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()
                    + " is running...");
            synClass.instanceStatic1();
        }
    }
    /**
     * 
     * @方法名：instanceStatic1
     * @方法描述【方法功能描述】
     * @修改描述【修改描述】代码块锁，但代码块锁的是类的对象
     * @版本：1.0
     * @创建人：cc
     * @创建时间：2019年1月21日 下午2:23:52
     * @修改人：cc
     * @修改时间：2019年1月21日 下午2:23:52
     */
    private void instanceStatic1() {
        //锁的是类的对象
        synchronized (SynClass.class) {
            SleepTools.second(1);
            System.out.println(Thread.currentThread().getName()
                    + " is going...");
            SleepTools.second(2);
            System.out.println(Thread.currentThread().getName() + " ended ");
        }
    }

    public static void main(String[] args) {
        // 测试静态方法锁；锁的是类的对象
        ClassSyn cls = new ClassSyn();
        cls.start();
        ClassSyn cls2 = new ClassSyn();
        cls2.start();
        // 测试代码块锁类的实例对象
        SynClass synClass=new SynClass();
        ClassSyn1 cls11 = new ClassSyn1(synClass);
        cls11.start();
        SynClass synClass2=new SynClass();
        ClassSyn1 cls12 = new ClassSyn1(synClass2);
        cls12.start();
    }
}
