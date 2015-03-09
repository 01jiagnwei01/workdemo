package com.gxkj.demo.thread.doc.executor.ch4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
/**
 * 参考网址
 * http://ifeve.com/thread-executors-4/
 *  主题：执行者执行返回结果的任务
 *  副标题：主程序等到所有子程序执行完毕，并获取执行结果
 */
public class Main {

	public static void main(String[] args) {
		// 使用Executors类的newFixedThreadPool()方法创建ThreadPoolExecutor来运行任务。传入参数2。
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(2);
		// 创建Future<Integer>对象的数列。
		List<Future<Integer>> resultList = new ArrayList<>();
		// 创建Random类产生的随机数。
		Random random = new Random();

		// 生成0到10之间的10个随机数。
		for (int i = 0; i < 10; i++) {

			Integer number = random.nextInt(10);
			// 创建一个FactorialCaculator对象，传入随机数作为参数。
			FactorialCalculator calculator = new FactorialCalculator(number);
			// 调用执行者的submit()方法来提交FactorialCalculator任务给执行者。
			// 这个方法返回Future<Integer>对象来管理任务，并且最终获取它的结果。
			Future<Integer> result = executor.submit(calculator);
			// 添加Future对象到之前创建的数列。
			resultList.add(result);
		}

		// 创建一个do循环来监控执行者的状态。
		do {
			System.out.printf("Main: Number of Completed Tasks:%d\n",
					executor.getCompletedTaskCount());
			// 然后，对于数列中的10个Future对象，使用isDone()方法，将信息写入（到控制台）表明它们所管理的任务是否已经完成
			for (int i = 0; i < resultList.size(); i++) {
				Future<Integer> result = resultList.get(i);
				System.out.printf("Main: Task %d: %s\n", i, result.isDone());
			}
			try {
				TimeUnit.MILLISECONDS.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 如果执行者中的已完成任务数小于10，重复这个循环。
		} while (executor.getCompletedTaskCount() < resultList.size());

		// .将获得的每个任务的结果写入控制台。对于每个Future对象，通过它的任务使用get()方法获取返回的Integer对象

		System.out.printf("Main: Results\n");
		for (int i = 0; i < resultList.size(); i++) {
			Future<Integer> result = resultList.get(i);
			Integer number = null;
			try {
				number = result.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			// 在控制台打印这个数。
			System.out.printf("Main: Task %d: %d\n", i, number);
		}
		// 最后，调用执行者的shutdown()方法来结束这个执行者。
		executor.shutdown();
	}

}
