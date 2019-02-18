package pers.cc.curriculum2.exchanger;

import java.util.concurrent.Exchanger;

/**
 * 交换器的使用
 * 
 * @author cc
 *
 */
public class ExchangerTest {
    // 创建交换器
    private static Exchanger < Long > exchanger = new Exchanger < Long >();

    /**
     * 将当前线程id塞入交换器，同时获取交换器中的线程id
     */
    public void update() {
        new Thread() {
            public void run() {
                try {
                    Long i = exchanger.exchange(Thread.currentThread().getId());
                    System.out.println(Thread.currentThread().getId()
                            + "进行交换的线程：" + i);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    public static void main(String[] args) {
        ExchangerTest exchangerTest = new ExchangerTest();
        for (int i = 0; i < 5; i++) {
            exchangerTest.update();
        }
    }
}
