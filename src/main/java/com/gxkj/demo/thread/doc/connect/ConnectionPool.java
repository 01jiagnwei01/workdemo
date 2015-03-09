package com.gxkj.demo.thread.doc.connect;

import java.util.LinkedList;
/**
 * 主要看一下fecthConnection，它提供了完全超时的实现，主要是通过计算出将要超时的时间点futureTime，
 * 和超时的时间距离deltaTime，在这个基础上复用了仅点的同步、while和do的结构，只不过是在while的不通过
 * 条件中增加了时间距离的消耗判断，如果小于0直接返回，当然面对过早通知，将会更新deltaTime。
 * 当执行从pool.wait方法中返回后，有可能是超时，也有可能是已经满足了池中有连接的状况，因此如果有连接则直接返回，否则返回空。
 *
 */
public class ConnectionPool {
	private LinkedList<Connection> pool     = new LinkedList<Connection>();
    private static final int       MAX_SIZE = 20;

    public ConnectionPool(int initialSize){
        if (initialSize > 0) {
            for (int i = 0; i < initialSize; i++) {
                pool.addLast(new Connection());
            }
        }
    }

    public void releaseConnection() throws InterruptedException {
        synchronized (pool) {
            while (pool.size() >= MAX_SIZE) {
                pool.wait();
            }

            // 添加后需要进行通知，这样其他消费者能够感知到链接池中已经增加了一个链接
            pool.addLast(new Connection());
            pool.notifyAll();
        }
    }

    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool) {
            // 完全超时
            if (mills <= 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }

                return pool.removeFirst();
            } else {
                long futureTime = System.currentTimeMillis() + mills;
                long deltaTime = mills;
                /**
                 * 当没有可用链接且处理剩余时间没有超时时 等待
                 * 注意顺序
                 */
                while (pool.isEmpty() && deltaTime > 0) {
                    pool.wait(deltaTime);
                    
                    deltaTime = futureTime - System.currentTimeMillis();
                }

                Connection result = null;
                if (!pool.isEmpty()) {
                    result = pool.removeFirst();
                }

                return result;
            }
        }
    }
}
