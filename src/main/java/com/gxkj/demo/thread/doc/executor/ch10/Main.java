package com.gxkj.demo.thread.doc.executor.ch10;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
/**
 * 执行者控制一个任务完成
 * FutureTask类提供一个done()方法，允许你在执行者执行任务完成后执行一些代码。你可以用来做一些后处理操作，生成一个报告，通过e-mail发送结果，或释放一些资源
 *
 */
public class Main {

	public static void main(String[] args) {
		ExecutorService executor = (ExecutorService) Executors
				.newCachedThreadPool();
		ResultTask resultTasks[] = new ResultTask[5];
		for (int i = 0; i < 5; i++) {
			ExecutableTask executableTask = new ExecutableTask("Task " + i);
			resultTasks[i] = new ResultTask(executableTask);
			executor.submit(resultTasks[i]);
		}
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		//取消你提交给执行者的所有任务。
		for (int i = 0; i < resultTasks.length; i++) {
			resultTasks[i].cancel(true);
		}
		//将没有被使用ResultTask对象的get()方法取消的任务的结果写入到控制台
		for (int i = 0; i < resultTasks.length; i++) {
			try {
				if (!resultTasks[i].isCancelled()) { 
					System.out.printf("%s\n", resultTasks[i].get());
				}
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}

		}

		executor.shutdown();
	}
}

