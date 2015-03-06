package com.gxkj.demo.thread.doc.executor.ch3;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Task implements Runnable {

	private Date initDate;
	private String name;
	
	public Task(String name){
		initDate=new Date();
		this.name=name;
	}
	
	public void run() {
		System.out.printf("%s: Task %s: Created on: %s\n",Thread.currentThread().getName(),name,initDate);
		System.out.printf("%s: Task %s: Started on: %s\n",Thread.currentThread().getName(),name,new Date());
		
		try {
			/**
			 * 使任务睡眠一个随机时间。
			 */
			Long duration=(long)(Math.random()*10);
			System.out.printf("%s: Task %s: Doing a task during %dseconds\n",Thread.currentThread().getName(),name,duration);
			TimeUnit.SECONDS.sleep(duration);
			} catch (InterruptedException e) {
			e.printStackTrace();
			}
		/**
		 * 将任务完成时间写入控制台
		 */
		System.out.printf("%s: Task %s: Finished on: %s\n",Thread.currentThread().getName(),name,new Date());
	}

}
