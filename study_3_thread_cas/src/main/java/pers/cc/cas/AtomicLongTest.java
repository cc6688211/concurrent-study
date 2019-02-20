package pers.cc.cas;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongBinaryOperator;

/**
 * 可以自动更新的Long。AtomicLong用于原子递增的序列号等应用程序中，不能用作Long的替代品。但是，这个类确实扩展了Number，
 * 允许处理基于数字的类的工具和实用程序进行统一访问
 * 
 * @author cc
 *
 */
public class AtomicLongTest {
    /**
     * 默认为0
     */
    static AtomicLong atomicLong = new AtomicLong();

    public static void main(String[] args) {
        /**
         * 原子地将给定值添加到当前值。
         */
        System.out.println(atomicLong.addAndGet(5));
        /**
         * 如果当前值==期望值，则原子化地将该值设置为给定的更新值。
         */
        System.out.println(atomicLong.compareAndSet(5, 6));
        /**
         * 原子性地将当前值减1。
         */
        System.out.println(atomicLong.decrementAndGet());
        /**
         * 使用将给定函数应用于当前值和给定值的结果原子地更新当前值，并返回更新后的值。
         */
        TestLongBinaryOperator testLongBinaryOperator = new TestLongBinaryOperator();
        System.out.println(atomicLong.accumulateAndGet(5,
                testLongBinaryOperator));
        /**
         * 原子地将给定值添加到当前值。并返回原有值
         */
        System.out.println(atomicLong.getAndAdd(5));
        /**
         * 原子地将当前值增加1。
         */
        System.out.println(atomicLong.incrementAndGet());
    }
}
