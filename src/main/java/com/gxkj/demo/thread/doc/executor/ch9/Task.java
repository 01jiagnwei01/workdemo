package com.gxkj.demo.thread.doc.executor.ch9;

import java.util.concurrent.Callable;
/**
 * 1.创建Task类，指定实现Callable接口，并参数化为String类型。实现call()方法，写入一条信息到控制台，
 * 并使这个线程在循环中睡眠100毫秒。
 *
 */
public class Task implements Callable<String> {

	public String call() throws Exception {
		while (true){
			System.out.printf("Task: Test\n");
			Thread.sleep(100);
		}
	}

}
