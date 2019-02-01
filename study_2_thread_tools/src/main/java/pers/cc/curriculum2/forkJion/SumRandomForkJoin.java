package pers.cc.curriculum2.forkJion;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import pers.cc.tools.SleepTools;

/**
 * 使用forkjoin模式进行指定个数的随机数相加并打印随机数
 * 
 * @author cc
 *
 */
public class SumRandomForkJoin {
    /**
     * 使用RecursiveTask同步返回结果
     * 
     * @author cc
     *
     */
    private static class SumTask extends RecursiveTask < Long > {
        // 定义阈值
        private final static int THRESHOLD = 1000;

        // 定义任务起始坐标
        private int fromIndex;

        // 定义任务终止坐标
        private int endIndex;

        public SumTask(int fromIndex, int endIndex) {
            this.fromIndex = fromIndex;
            this.endIndex = endIndex;
        }

        /**
         * 实现执行方法
         */
        @Override
        protected Long compute() {
            // new一个随机数发生器
            Random r = new Random();
            if (endIndex - fromIndex <= THRESHOLD) {
                Long result = (long) 0;
                // 左闭右开
                for (int i = 0; i < endIndex - fromIndex; i++) {
                    // 三位数内的随机数相加
                    int ran = r.nextInt(1000);
                    System.out.println(ran);
                    result = result + ran;
                    // 模拟业务场景耗时
                    SleepTools.ms(2);
                }
                return result;
            }
            else {
                // 将运算分成两个线程进行
                int mid = (fromIndex + endIndex) / 2;
                SumTask left = new SumTask(fromIndex, mid);
                SumTask right = new SumTask(mid + 1, endIndex);
                // 注意的是这里并不是只能分成两个线程，invokeAll方法可以是两个线程，也可以是多个线程，也可以是一个线程集合
                invokeAll(left, right);
                return left.join() + right.join();
            }
        }

    }

    public static void main(String[] args) {
        // 起始时间
        long start = System.currentTimeMillis();
        // 计算个数
        int count = 100000;
        // 定义一个任务集合
        SumTask task = new SumTask(0, count - 1);
        // 定义一个forkjoin线程池
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);
        // 打印结果
        System.out.println(task.join());
        System.out.println("The count is " + count + " spend time:"
                + (System.currentTimeMillis() - start) + "ms");

    }
}
