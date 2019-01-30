/**
 * @模块名：study_1_thread_basic
 * @包名：pers.cc.curriculum1.wait
 * @描述：HealthPush.java
 * @版本：1.0
 * @创建人：cc
 * @创建时间：2019年1月30日下午2:59:43
 */

package pers.cc.curriculum1.wait;

/**
 * @模块名：study_1_thread_basic
 * @包名：pers.cc.curriculum1.wait
 * @类名称： HealthPush
 * @类描述：【类描述】标准通知方式演示
 * @版本：1.0
 * @创建人：cc
 * @创建时间：2019年1月30日下午2:59:43
 */

public class HealthPush {
    public final static int HEIGHTFORMAN = 120;

    private static String name;

    // 体重
    private int weight;

    // 身高
    private int height;

    public HealthPush() {

    }

    public HealthPush(int weight, int height) {
        this.weight = weight;
        this.height = height;
    }

    /**
     * 体重变更，唤醒所有等待中线程进
     * 
     * @param weight
     */
    public synchronized void changeWeight(int weight) {
        this.weight = weight;
        notifyAll();
        // 其他的业务代码
    }

    /**
     * 身高发生变化，随机唤醒一个线程进行业务处理
     * 
     * @param height
     */
    public synchronized void changeHeight(int height) {
        this.height = height;
        notify();
    }

    public synchronized void waitWeight() {
        while (this.weight <= 70) {
            try {
                System.out.println("check weight thread["
                        + Thread.currentThread().getId() + "] is be wait.");
                wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out
                .println("the weight is" + this.weight + ",I will change db.");

    }

    public synchronized void waitHeight() {
        while (this.height < HealthPush.HEIGHTFORMAN) {
            try {
                System.out.println("check height thread["
                        + Thread.currentThread().getId() + "] is be wait.");
                wait();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out
                .println("the height is" + this.height + ",I will call user.");
    }

    public static void main(String[] args) throws InterruptedException {
        HealthPush healthPush = new HealthPush(35, 100);
        for (int i = 0; i < 3; i++) {
            // 三个线程监控体重
            new Thread(new Runnable() {
                @Override
                public void run() {
                    healthPush.waitWeight();
                }
            }).start();
            // 三个线程监控身高
            new Thread() {
                @Override
                public void run() {
                    healthPush.waitHeight();
                }
            }.start();
        }

        Thread.sleep(1000);
        healthPush.changeWeight(80);// 体重发生变化
        // healthPush.changeHeight(140);// 身高发生变化
    }
}
