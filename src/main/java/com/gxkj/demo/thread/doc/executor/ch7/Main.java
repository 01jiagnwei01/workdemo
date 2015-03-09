package com.gxkj.demo.thread.doc.executor.ch7;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * 参看网址：
 * http://ifeve.com/thread-executors-7/
 * 标题：执行者延迟运行一个任务
 * 
 * @author admin
 *
 */
public class Main {

	public static void main(String[] args) {
		//使用Executors类的newScheduledThreadPool()方法，创建ScheduledThreadPoolExecutor类的一个执行者。传入参数1。
		ScheduledThreadPoolExecutor executor=(ScheduledThreadPoolExecutor)Executors.newScheduledThreadPool(1);
		//使用ScheduledThreadPoolExecutor实例的schedule()方法，初始化和开始一些任务（本例中5个任务）
		System.out.printf("Main: Starting at: %s\n",new Date());
		for (int i=0; i<5; i++) {
		Task task=new Task("Task "+i);
		/**
		 * schedule方法参数解释：
		 *  你想要执行的任务
		 *  你想要让任务在执行前等待多长时间
		 *  时间单位，指定为TimeUnit类的常数
		 *  */
		executor.schedule(task,i+1 , TimeUnit.SECONDS);
		}
//使用shutdown()方法关闭执行者。
		executor.shutdown();
		
		//使用执行者的awaitTermination()方法，等待所有任务完成。
		try {
			executor.awaitTermination(1, TimeUnit.DAYS);
			} catch (InterruptedException e) {
			e.printStackTrace();
			}
		
		System.out.printf("Main: Ends at: %s\n",new Date());

	}

}
