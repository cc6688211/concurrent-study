package pers.cc.curriculum2.forkJion;

import java.util.Random;

import pers.cc.tools.SleepTools;

/**
 * 单线程计算指定个数的随机值之和
 * 
 * @author cc
 *
 */
public class SumRandomTradition {

    /**
     * 计算指定个数的随机数之和并打印随机数
     * 
     * @param length
     * @return
     */
    public static Long sumRandom(int length) {
        // new一个随机数发生器
        Random r = new Random();
        Long result = (long) 0;
        for (int i = 0; i < length; i++) {
            // 三位数内的随机数相加
            int ran = r.nextInt(1000);
            System.out.println(ran);
            result = result + ran;
            // 模拟业务场景耗时
            SleepTools.ms(2);
        }
        return result;

    }

    public static void main(String[] args) {
        // 起始时间
        long start = System.currentTimeMillis();
        // 计算个数
        int count = 100000;
        System.out.println(SumRandomTradition.sumRandom(count));
        System.out.println("The count is " + count + " spend time:"
                + (System.currentTimeMillis() - start) + "ms");
    }
}
