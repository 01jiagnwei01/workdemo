package com.gxkj.demo.thread.doc.executor.ch9;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		ThreadPoolExecutor executor=(ThreadPoolExecutor)Executors.newCachedThreadPool();
		
		Task task=new Task();
		System.out.printf("Main: Executing the Task\n");
		Future<String> result=executor.submit(task);
		
		try {
			TimeUnit.SECONDS.sleep(2);
			} catch (InterruptedException e) {
			e.printStackTrace();
			}
		
		System.out.printf("Main: Canceling the Task\n");
		/**
		 * 如果这个任务已经完成或之前的已被取消或由于其他原因不能被取消，那么这个方法将会返回false并且这个任务不会被取消。
		 * 如果这个任务正在等待执行者获取执行它的线程，那么这个任务将被取消而且不会开始它的执行。
		 * 如果这个任务已经正在运行，则视方法的参数情况而定。 cancel()方法接收一个Boolean值参数。
		 * 如果参数为true并且任务正在运行，那么这个任务将被取消。
		 * 如果参数为false并且任务正在运行，那么这个任务将不会被取消。
		 * 
		 * 如果你使用Future对象的get()方法来控制一个已被取消的任务，这个get()方法将抛出CancellationException异常
		 */
		result.cancel(true);
		//将isCancelled()方法和isDone()的调用结果写入控制台，验证任务已取消，因此，已完成。
		
		System.out.printf("Main: Canceled: %s\n",result.isCancelled());
		System.out.printf("Main: Done: %s\n",result.isDone());

		
		//使用shutdown()方法结束执行者，写入信息（到控制台）表明程序结束
		
		executor.shutdown();
		System.out.printf("Main: The executor has finished\n");


	}

}
