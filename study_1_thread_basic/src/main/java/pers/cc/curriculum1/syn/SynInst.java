/**
 * @模块名：study_1_thread_basic
 * @包名：pers.cc.curriculum1.syn
 * @描述：SynInst.java
 * @版本：1.0
 * @创建人：cc
 * @创建时间：2019年1月21日上午10:58:58
 */

package pers.cc.curriculum1.syn;

import pers.cc.tools.SleepTools;

/**
 * @模块名：study_1_thread_basic
 * @包名：pers.cc.curriculum1.syn
 * @类名称： SynInst
 * @类描述：【类描述】 synchronized关键字对象锁的用法:对象锁
 * @版本：1.0
 * @创建人：cc
 * @创建时间：2019年1月21日上午10:58:58
 */

public class SynInst {
    /**
     * 
     * @模块名：study_1_thread_basic
     * @包名：pers.cc.curriculum1.syn
     * @类名称： InstanceSyn
     * @类描述：【类描述】
     * @版本：1.0
     * @创建人：cc
     * @创建时间：2019年1月21日上午11:01:10
     */
    private static class InstanceSyn extends Thread {
        private SynInst synInst;

        public InstanceSyn(SynInst synInst) {
            this.synInst = synInst;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()
                    + " is running..." + synInst);
            synInst.instance();
        }
    }

    /**
     * 
     * @方法名：instance
     * @方法描述【方法功能描述】锁对象,即需要先new一个SynInst1对象，且只锁住这个对象；若new出多个SynInst1对象，相互之间是不共享锁的
     * @修改描述【修改描述】
     * @版本：1.0
     * @创建人：cc
     * @创建时间：2019年1月21日 上午11:11:25
     * @修改人：cc
     * @修改时间：2019年1月21日 上午11:11:25
     */
    private synchronized void instance() {
        SleepTools.second(1);
        System.out.println(Thread.currentThread().getName() + " is going..."
                + this.toString());
        SleepTools.second(2);
        System.out.println(Thread.currentThread().getName() + " ended "
                + this.toString());
    }

    /**
     * 
     * @模块名：study_1_thread_basic
     * @包名：pers.cc.curriculum1.syn
     * @类名称： InstanceSyn
     * @类描述：【类描述】
     * @版本：1.0
     * @创建人：cc
     * @创建时间：2019年1月21日下午2:42:08
     */
    private static class InstanceSyn1 extends Thread {
        private SynInst synInst;

        public InstanceSyn1(SynInst synInst) {
            this.synInst = synInst;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()
                    + " is running..." + synInst);
            synInst.instance1();
        }
    }

    /**
     * 
     * @方法名：instance1
     * @方法描述【方法功能描述】代码块锁定实例对象
     * @修改描述【修改描述】
     * @版本：1.0
     * @创建人：cc
     * @创建时间：2019年1月21日 下午2:39:53
     * @修改人：cc
     * @修改时间：2019年1月21日 下午2:39:53
     */
    private void instance1() {
        // 锁的是实例对象
        synchronized (this) {
            SleepTools.second(1);
            System.out.println(Thread.currentThread().getName()
                    + " is going..." + this.toString());
            SleepTools.second(2);
            System.out.println(Thread.currentThread().getName() + " ended "
                    + this.toString());
        }
    }

    public static void main(String[] args) {
        SynInst synInst1 = new SynInst();
        InstanceSyn instanceSyn = new InstanceSyn(synInst1);
        instanceSyn.start();
        // 线程instanceSyn和instanceSyn2共用一个对象
        InstanceSyn instanceSyn2 = new InstanceSyn(synInst1);
        instanceSyn2.start();
        // 线程instanceSyn和其他连个线程不共用对象
        SynInst synInst2 = new SynInst();
        InstanceSyn instanceSyn3 = new InstanceSyn(synInst2);
        instanceSyn3.start();

        SynInst synInst11 = new SynInst();
        InstanceSyn1 instanceSyn11 = new InstanceSyn1(synInst11);
        instanceSyn11.start();
        // 线程instanceSyn和instanceSyn2共用一个对象
        InstanceSyn1 instanceSyn21 = new InstanceSyn1(synInst11);
        instanceSyn21.start();
        // 线程instanceSyn和其他连个线程不共用对象
        SynInst synInst21 = new SynInst();
        InstanceSyn1 instanceSyn31 = new InstanceSyn1(synInst21);
        instanceSyn31.start();

    }
}
