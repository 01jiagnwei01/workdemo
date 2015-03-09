package com.gxkj.demo.thread.doc.executor.ch8;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
/**
 * 参考网址：
 * http://ifeve.com/thread-executors-8/
 * 
 * 标题：固定频率调用线程
 * 副标题：一个线程结束后相隔固定时间再调用该线程
 *
 */
public class Main {

	public static void main(String[] args) {
		//使用Executors类的newScheduledThreadPool()方法，创建ScheduledThreadPoolExecutor。
		//传入参数1给这个方法。
		ScheduledExecutorService executor=Executors.newScheduledThreadPool(1);
		
		System.out.printf("主程序: Starting at: %s\n",new Date());
		
		Task task=new Task("Task");
		// 使用scheduledAtFixRate()方法把它提交给执行者.
		//使用前面创建的任务，数字1，数字2和常量TimeUnit.SECONDS作为参数。
		//这个方法返回ScheduledFuture对象，它可以用来控制任务的状态。
		/**
		 * 说明1
		 * 参数1：需要执行的任务
		 * 参数2：开始执行时需要延迟的时间
		 * 参数3：两次执行之间的间隔期间
		 * 参数4：时间单位
		 * 
		 * 说明2
		 * 很重要的一点需要考虑的是两次执行之间的（间隔）期间，是这两个执行开始之间的一段时间。
		 * 如果你有一个花5秒执行的周期性任务，而你给一段3秒时间，同一时刻，你将会有两个任务在执行。
		 * 
		 * 说明3
		 * ScheduledFuture是一个参数化接口（校对注：ScheduledFuture<V>）。
		 * 在这个示例中，由于任务是非参数化的Runnable对象，必须使用 问号作为参数
		 * 
		 * 说明4：
		 * 可选方法： scheduledWithFixedRate()方法中，参数决定任务执行结束与下次执行开始之间的一段时间。
		 */
		ScheduledFuture<?> result=executor.scheduleAtFixedRate(task,1, 2, TimeUnit.SECONDS);
		
		//创建10个循环步骤，写入任务下次执行的剩余时间。在循环中，使用ScheduledFuture对象的getDelay()方法，获取任务下次执行的毫秒数。
		for (int i = 0; i < 10; i++) {
			System.out.printf("Main: Delay: %d\n",
					result.getDelay(TimeUnit.MILLISECONDS));
			// 线程睡眠500毫秒
			try {
				TimeUnit.MILLISECONDS.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		executor.shutdown();
		//使线程睡眠5秒，检查周期性任务是否完成。
		try {
			TimeUnit.SECONDS.sleep(5);
			System.out.println(String.format("调度任务结束?%s",result.isDone()));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//写入一条信息到控制台，表明程序结束。
		System.out.printf("主程序: Finished at: %s\n",new Date());

	
	}

}
