package com.gxkj.demo.thread.doc.executor.ch3;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
/**
 * ThreadPoolExecutor 类和一般执行者的一个关键方面是，你必须明确地结束它。如果你没有这么做，这个执行者会继续它的执行，
 * 并且这个程序不会结束。如果执行者没有任务可执行， 它会继续等待新任务并且不会结束它的执行。一个Java应用程序将不会结束，除非
 * 所有的非守护线程完成它们的执行。所以，如果你不结束这个执行者，你的应用程序将不会结束。
 *
 */
public class Server {
	private ThreadPoolExecutor executor;
	/**
	 * 缓存线程池的缺点是，为新任务不断创建线程， 所以如果你提交过多的任务给执行者，会使系统超载
	 * 注意事项：
	 * 使用通过newCachedThreadPool()方法创建的执行者，只有当你有一个合理的线程数或任务有一个很短的执行时间。
	 * 方法解释：
	 * getPoolSize()：此方法返回线程池实际的线程数。
	 * getActiveCount()：此方法返回在执行者中正在执行任务的线程数。
	 * getCompletedTaskCount()：此方法返回执行者完成的任务数。
	 * getTaskCount():获取已经提交给执行者的任务数
	 */
	public Server(){
		/**
		 * 使用Executors类初始化ThreadPoolExecutor对象。
		 * java并发API提供Executors类来构造执行者和其他相关对象,推荐使用Executors类
		 * 使用newFixedThreadPool()方法创建执行者并传入5作为参数
		 */
		executor=(ThreadPoolExecutor)Executors.newFixedThreadPool(5);
	}
	/**
	 * 实现executeTask()方法，接收Task对象作为参数并将其提交到执行者。首先，写入一条信息到控制台，表明有一个新的任务到达。
	 */
	public void executeTask(Task task){
		System.out.printf("Server: A new task has arrived\n");
		System.out.printf("Server: Task Count: %d\n ",executor.getTaskCount());
		//调用执行者的execute(）方法来提交这个任务。
		executor.execute(task);
		//将执行者的数据写入到控制台来看它们的状态。
		System.out.printf("Server: 线程池大小: %d\n",executor.getPoolSize());
		System.out.printf("Server: :活跃数 %d\n",executor.getActiveCount());
		System.out.printf("Server: 已完成任务数 : %d\n",executor.getCompletedTaskCount());
	}
	//实现endServer()方法，在这个方法中，调用执行者的shutdown()方法来结束任务执行。
	public void endServer() {
		executor.shutdown();
	}
	
	public ThreadPoolExecutor getExecutor(){
		return this.executor;
	}
}
