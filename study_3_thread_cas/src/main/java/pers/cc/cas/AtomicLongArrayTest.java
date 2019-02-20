package pers.cc.cas;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLongArray;

/**
 * AtomicLongArray:一个长数组，其中的元素可以原子地更新。
 * 
 * @author cc
 *
 */
public class AtomicLongArrayTest {
    static long[] arr = new long[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };

    /**
     * 初始化一个可原子操作的数组
     */
    static AtomicLongArray atomicLongArray = new AtomicLongArray(arr);

    public static void main(String[] args) {
        /**
         * 指定游标设值
         */
        atomicLongArray.set(0, 9);
        System.out.println(atomicLongArray.get(0));
        System.out.println(arr[0]);
        /**
         * 指定游标进行比较进行变更
         */
        atomicLongArray.compareAndSet(1, 1, 8);
        /**
         * 指定游标进行减1
         */
        System.out.println(atomicLongArray.decrementAndGet(9));

    }
}
