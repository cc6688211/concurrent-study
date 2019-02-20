package pers.cc.cas;

import java.util.concurrent.atomic.AtomicBoolean;

/*
 * 原子布尔类型实践
 */
public class AtomicBooleanTest {
    /**
     * 定义一个线程安全的Integer并设定初始值为0
     */
    static AtomicBoolean atomicBoolean = new AtomicBoolean(true);

    public static void main(String[] args) throws InterruptedException {
        Thread successThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 若atomicBoolean为false则自旋直到其他线程修改atomicBoolean为true；
                // 这里要注意weakCompareAndSet本身是不会自旋的，就是一次性操作，修改成功或失败
                while (!atomicBoolean.weakCompareAndSet(true, false)) {
                    System.out.println(System.currentTimeMillis()
                            + "successThread:" + atomicBoolean.get());
                }
                System.out.println(System.currentTimeMillis() + "successThread:"
                        + atomicBoolean.get());
            }
        });
        Thread errorThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // 若atomicBoolean为false则修改为true
                atomicBoolean.compareAndSet(false, true);
                System.out.println(System.currentTimeMillis() + "errorThread:"
                        + atomicBoolean.get());
            }
        });
        // 修改atomicBoolean为false
        System.out.println(atomicBoolean.getAndSet(false));
        successThread.start();
        Thread.sleep(3);
        errorThread.start();

    }
}
