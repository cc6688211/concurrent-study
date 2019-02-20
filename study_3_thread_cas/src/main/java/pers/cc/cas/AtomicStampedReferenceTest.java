package pers.cc.cas;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * AtomicStampedReference维护一个对象引用和一个整数“戳记”，该“戳记”可以以原子方式更新。
 * 实现注意:此实现通过创建表示“装箱”[引用、整数]对的内部对象来维护带戳记的引用。
 * 
 * @author cc
 *
 */
public class AtomicStampedReferenceTest {
    /**
     * 定义一个原子对象，类型为String ,初始版本号为0
     */
    static AtomicStampedReference < String > asr = new AtomicStampedReference <>(
            "cc", 0);

    public static void main(String[] args) throws InterruptedException {
        /**
         * 获取初始版本号
         */
        final int oldStamp = asr.getStamp();
        /**
         * 获取初始值
         */
        final String oldReferenc = asr.getReference();

        System.out.println(oldReferenc + "===========" + oldStamp);

        Thread rightStampThread = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()
                        + "当前变量值："
                        + oldReferenc
                        + "当前版本戳："
                        + oldStamp
                        + "-"
                        + asr.compareAndSet(oldReferenc, oldReferenc + "Java",
                                oldStamp, oldStamp + 1));

            }

        });

        Thread errorStampThread = new Thread(new Runnable() {

            @Override
            public void run() {
                String reference = asr.getReference();
                System.out.println(Thread.currentThread().getName()
                        + "当前变量值："
                        + reference
                        + "当前版本戳："
                        + asr.getStamp()
                        + "-"
                        + asr.compareAndSet(reference, reference + "C",
                                oldStamp, oldStamp + 1));

            }

        });

        rightStampThread.start();
        rightStampThread.join();
        errorStampThread.start();
        errorStampThread.join();
        System.out.println(asr.getReference() + "===========" + asr.getStamp());

    }
}
