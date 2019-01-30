package pers.cc.curriculum1.pool;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 
 * @author cc 模拟一个连接池
 *
 */
public class DBPool {
    // 数据库池的容器
    private static LinkedList < Connection > pool = new LinkedList <>();

    /**
     * 初始化连接池
     * 
     * @param initalSize
     */
    public DBPool(int initalSize) {
        if (initalSize > 0) {
            for (int i = 0; i < initalSize; i++) {
                pool.addLast(SqlConnectImpl.fetchConnection());
            }
        }
    }

    /**
     * 获取数据库连接
     * 
     * @param mills
     *            超时毫秒数
     * @return 在mills时间内还拿不到数据库连接，返回一个null
     * @throws InterruptedException
     */
    public Connection fetchConn(long mills) throws InterruptedException {
        // 先获得同步锁
        synchronized (pool) {
            // 如果等待时间小于0则无线等待
            if (mills < 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            }
            else {
                long overtime = System.currentTimeMillis() + mills;
                long remain = mills;
                // 无数据库连接且等待时间大于0则进入等待
                while (pool.isEmpty() && remain > 0) {
                    pool.wait(remain);
                    // 唤醒后更新可等待时间
                    remain = overtime - System.currentTimeMillis();
                }
                // 若无连接则返回null,否则取第一个
                Connection result = null;
                if (!pool.isEmpty()) {
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }

    /**
     * 释放数据库连接，并唤醒等待线程
     * 
     * @param conn
     */
    public void releaseConn(Connection conn) {
        if (conn != null) {
            // 唤醒前需先获得锁
            synchronized (pool) {
                pool.addLast(conn);
                pool.notifyAll();
            }
        }
    }
}
