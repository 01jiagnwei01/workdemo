package com.gxkj.demo.thread.risk.executor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * 这个方法不实用，推荐不要使用。但是里面的阻塞对列类ExecutorService可以参考
 *  
 *
 */

public class MainThread {
	static ExecutorService executorService = Executors.newFixedThreadPool(1);
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		SubThread thread = new SubThread();
		/**
		 * sumbit的过程就是把一个Runnable接口对象包装成一个 Callable接口对象, 
		 * 然后放到 workQueue里等待调度执行
		 */
		Future future = executorService.submit(thread);
		mainThreadOtherWork();
		System.out.println("now waiting sub thread done.");
		future.get();
		 
		System.out.println("now all done.");
		/**
		 * 执行shutdown方法以示退出使用线程池
		 */
		executorService.shutdown();

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
