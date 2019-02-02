package pers.cc.curriculum2.cyclesBarrier;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;

import pers.cc.tools.SleepTools;

/**
 * CyclesBarrier:一组线程相互等待到达共同的屏障点,可选择先执行一个Runnable操作，也可立即进行线程的后续操作
 * 
 * @author cc
 *
 */
public class CyclesBarrierTest {
    // 场景1：定义一组线程相互等待后立即执行后续
    private static CyclicBarrier barrier1 = new CyclicBarrier(5);

    // 场景2：定义一组线程相互等待后先执行CollectThread线程再执行各自后续
    private static CyclicBarrier barrier2 = new CyclicBarrier(5,
            new CollectThread());

    // 存放子线程工作结果的容器
    private static ConcurrentHashMap < String, Long > resultMap = new ConcurrentHashMap <>();

    /**
     * 场景1初始线程
     * 
     * @author cc
     *
     */
    private static class InitThread1 implements Runnable {
        @Override
        public void run() {
            long id = Thread.currentThread().getId();
            try {
                Thread.sleep(200 + id);
                System.out.println(id + "....is await");
                barrier1.await();
                System.out.println("Thread_" + id + " ....do its business ");
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            catch (BrokenBarrierException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 场景2初始线程
     * 
     * @author cc
     *
     */
    private static class InitThread2 implements Runnable {

        @Override
        public void run() {
            long id = Thread.currentThread().getId();
            // 线程本身的处理结果
            resultMap.put(Thread.currentThread().getId() + "", id);
            // 随机决定工作线程的是否睡眠
            Random r = new Random();
            try {
                if (r.nextBoolean()) {
                    Thread.sleep(2000 + id);
                }
                System.out.println(id + "....is await");
                barrier2.await();
                System.out.println("Thread_" + id + " ....do its business ");
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    // 负责屏障开放以后的工作
    private static class CollectThread implements Runnable {

        @Override
        public void run() {
            StringBuilder result = new StringBuilder();
            for (Map.Entry < String, Long > workResult : resultMap.entrySet()) {
                result.append("[" + workResult.getValue() + "]");
            }
            System.out.println(" the result = " + result);
            System.out.println("do other business........");
        }
    }

    public static void main(String[] args) {
        System.out
                .println("——————————————————————场景1——————————————————————————");
        for (int i = 0; i < 5; i++) {
            new Thread(new InitThread1()).start();
        }
        // 休眠两秒
        SleepTools.second(2);
        System.out
                .println("——————————————————————场景2——————————————————————————");
        for (int i = 0; i < 5; i++) {
            new Thread(new InitThread2()).start();
        }
    }
}
