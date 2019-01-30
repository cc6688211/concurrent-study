package pers.cc.curriculum1.pool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据库连接测试
 * 
 * @author cc
 *
 */
public class DBpoolTest {
    // 定义一个连接池，大小为10
    static DBPool pool = new DBPool(10);

    // 定义一个发令枪
    static CountDownLatch end;

    // 定义线程数量
    static int threadCount = 50;

    // 定义每个线程操作次数
    static int count = 50;

    // //计数器：统计可以拿到连接的线程
    static AtomicInteger got = new AtomicInteger();

    // 计数器：统计没有拿到连接的线程
    static AtomicInteger notGot = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {
        // 初始化发令枪
        end = new CountDownLatch(DBpoolTest.threadCount);
        for (int i = 0; i < DBpoolTest.threadCount; i++) {
            Thread thread = new Thread(new Worker(count, got, notGot),
                    "worker_" + i);
            thread.start();
        }
        // main线程在此处等待所有子线程结束
        end.await();
        System.out.println("总共尝试了: " + (threadCount * count));
        System.out.println("拿到连接的次数：  " + got);
        System.out.println("没能连接的次数： " + notGot);
    }

    static class Worker implements Runnable {
        int count;

        AtomicInteger got;

        AtomicInteger notGot;

        public Worker(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        public void run() {
            while (count > 0) {
                try {
                    // 从线程池中获取连接，如果1000ms内无法获取到，将会返回null
                    // 分别统计连接获取的数量got和未获取到的数量notGot
                    Connection connection = pool.fetchConn(1000);
                    if (connection != null) {
                        // 模拟数据库连接操作
                        try {
                            connection.createStatement();
                            connection.commit();
                        }
                        finally {
                            pool.releaseConn(connection);
                            got.incrementAndGet();
                        }
                    }
                    else {
                        notGot.incrementAndGet();
                        System.out.println(Thread.currentThread().getName()
                                + "等待超时!");
                    }
                }
                catch (Exception ex) {
                }
                finally {
                    count--;
                }
            }
            // 线程准备完成，发令枪计数减少
            end.countDown();
        }
    }
}
