package pers.cc.curriculum1.wait;

public class TestHealth {
    private static HealthPush healthPush = new HealthPush(35, 100);

    public static void main(String[] args) throws InterruptedException {
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

        Thread.sleep(10000);
        // healthPush.changeWeight(80);// 体重发生变化
        healthPush.changeHeight(140);// 身高发生变化
    }
}
