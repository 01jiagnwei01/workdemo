package com.gxkj.demo.thread.risk.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
/**
 * 
 * 主线程等待子线程结束再执行的实现方法3：使用阻塞对列
 * 这里是得益于我们用了一个阻塞队列, 他的put操作和take操作都会阻塞(同步), 
 * 在满足条件的情况下.当我们调用take()方法时, 由于子线程还没结束, 队列是空的, 
 * 所以这里的take操作会阻塞, 直到子线程结束的时候, 往队列里面put了个元素, 表明自己结束了. 
 * 这时候主线程的take()就会返回他拿到的数据. 当然, 他拿到什么我们是不必去关心的.
 *  
 *针对子线程只有1个的时候. 当子线程有多个的时候, 情况就不妙了.
 *
 *这个方法不实用，推荐不要使用。但是里面的阻塞对列类BlockingQueue可以参考
 */
public class Threads {
	static final BlockingQueue queue = new ArrayBlockingQueue(1);

	public static void main(String[] args) throws InterruptedException {
		QueueSubThread thread = new QueueSubThread(queue);
		thread.start();
		mainThreadOtherWork();
		System.out.println("now waiting sub thread done.");
		queue.take();

		System.out.println("now all done.");

	}

	private static void mainThreadOtherWork() {
		System.out.println("main thread work start");
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("main thread work done.");
	}
}
