/**
 * @模块名：study_1_thread_basic
 * @包名：pers.cc.curriculum1.syn
 * @描述：SynMethod.java
 * @版本：1.0
 * @创建人：cc
 * @创建时间：2019年1月21日下午1:46:50
 */

package pers.cc.curriculum1.syn;

import pers.cc.tools.SleepTools;

/**
 * @模块名：study_1_thread_basic
 * @包名：pers.cc.curriculum1.syn
 * @类名称： SynMethod
 * @类描述：【类描述】成员对象锁，锁的是成员的实例对象
 * @版本：1.0
 * @创建人：cc
 * @创建时间：2019年1月21日下午1:46:50
 */

public class SynMember {
    private String str = "cc";

    private static String str1 = "cc";

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
    private static class MemberSyn extends Thread {
        private SynMember synMember;

        public MemberSyn(SynMember synMember) {
            this.synMember = synMember;
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()
                    + " is running...");
            synchronized (synMember.getStr()) {
                SleepTools.second(1);
                System.out.println(Thread.currentThread().getName()
                        + " is going...");
                SleepTools.second(2);
                System.out
                        .println(Thread.currentThread().getName() + " ended ");
            }
        }
    }

    /**
     * 
     * @模块名：study_1_thread_basic
     * @包名：pers.cc.curriculum1.syn
     * @类名称： MemberSyn1
     * @类描述：【类描述】局部变量测试
     * @版本：1.0
     * @创建人：cc
     * @创建时间：2019年1月21日下午3:13:57
     */
    private static class MemberSyn1 extends Thread {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()
                    + " is running...");
            String str = "cc";
            synchronized (str) {
                System.out.println(str);
                str = "cccc";
                SleepTools.second(1);
                System.out.println(Thread.currentThread().getName()
                        + " is going...");
                SleepTools.second(2);
                System.out
                        .println(Thread.currentThread().getName() + " ended ");
            }
        }
    }

    /**
     * 
     * @模块名：study_1_thread_basic
     * @包名：pers.cc.curriculum1.syn
     * @类名称： MemberSyn2
     * @类描述：【类描述】静态变量加锁
     * @版本：1.0
     * @创建人：cc
     * @创建时间：2019年1月21日下午3:46:15
     */
    private static class MemberSyn2 extends Thread {

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName()
                    + " is running...");
            synchronized (SynMember.str1) {
                // System.out.println(SynMember.str1);
                // SynMember.str1="cccc";
                SleepTools.second(1);
                System.out.println(Thread.currentThread().getName()
                        + " is going...");
                SleepTools.second(2);
                System.out
                        .println(Thread.currentThread().getName() + " ended ");
            }
        }
    }

    /**
     * @return the str
     */

    public String getStr() {
        return this.str;
    }

    public static void main(String[] args) {
        // 成员变量加锁
        SynMember synMember = new SynMember();
        // 线程一
        MemberSyn memberSyn = new MemberSyn(synMember);
        memberSyn.start();
        // 线程二
        MemberSyn memberSyn2 = new MemberSyn(synMember);
        memberSyn2.start();

        // 线程三
        SynMember synMember2 = new SynMember();
        MemberSyn memberSyn3 = new MemberSyn(synMember2);
        memberSyn3.start();

        // 局部变量加锁
        // MemberSyn1 memberSyn11 = new MemberSyn1();
        // memberSyn11.start();
        //
        // MemberSyn1 memberSyn12 = new MemberSyn1();
        //memberSyn12.start();

        // 静态变量加锁
        // MemberSyn2 memberSyn21 = new MemberSyn2();
        // memberSyn21.start();
        //
        // MemberSyn2 memberSyn22 = new MemberSyn2();
        // memberSyn22.start();

    }

}
