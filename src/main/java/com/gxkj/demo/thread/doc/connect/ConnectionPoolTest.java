package com.gxkj.demo.thread.doc.connect;

import java.util.concurrent.CountDownLatch;


public class ConnectionPoolTest {
		/**
		 * 连接池
		 */
	 	static ConnectionPool pool  = new ConnectionPool(10);
	 	
	 	/**
	 	 * 计数器
	 	 */
	    static CountDownLatch latch = new CountDownLatch(1);

	    /**
	     * <pre>
	     * Thread[Thread-5,5,main] put a connection.
	     * Thread[Thread-6,5,main] put a connection.
	     * Thread[Thread-4,5,main] got a connection
	     * Thread[Thread-3,5,main] got a connection
	     * Thread[Thread-5,5,main] put a connection.
	     * Thread[Thread-6,5,main] put a connection.
	     * Thread[Thread-1,5,main] got a connection
	     * Thread[Thread-4,5,main] got a connection
	     * </pre>
	     *
	     * @param args
	     */
	    public static void main(String[] args) {
	        for (int i = 0; i < 5; i++) {
	        	/**
	        	 * 消费者
	        	 */
	            Consumer p = new Consumer(latch);
	            Thread t = new Thread(p);
	            t.start();
	        }

	        for (int i = 0; i < 2; i++) {
	        	/**
	        	 * 生产者
	        	 */
	            Producer p = new Producer(latch);
	            Thread t = new Thread(p);
	            t.start();
	        }

	        latch.countDown();
	    }

	    static class Producer implements Runnable {

	        private CountDownLatch latch;

	        public Producer(CountDownLatch latch){
	            this.latch = latch;
	        }

	        public void run() {
	            try {
	                latch.await();
	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	            }
	            while (true) {
	                try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }

	                try {
	                    pool.releaseConnection();
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }

	                System.out.println(Thread.currentThread() + " put a connection.");
	            }
	        }
	    }

	    static class Consumer implements Runnable {

	    	private final static int featchConnectWaitTime = 20;
	        private CountDownLatch latch;

	        public Consumer(CountDownLatch latch){
	            this.latch = latch;
	        }

	        public void run() {
	            try {
	                latch.await();
	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	            }
	            while (true) {
	                try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }

	                try {
	                    Connection connection = pool.fetchConnection(featchConnectWaitTime);

	                    if (connection == null) {
	                        System.out.println(Thread.currentThread() + " can not got a connection");
	                    } else {
	                        System.out.println(Thread.currentThread() + " got a connection");
	                    }
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }

	            }
	        }
	    }
}
