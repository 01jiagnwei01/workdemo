package com.gxkj.demo.thread.doc.executor.ch6;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
/**
 * 参考网址：http://ifeve.com/thread-executors-6/
 * 标题：运行多个任务并处理所有结果
 * 副标题：并发运行多个任务，获得所有任务的结果继续后续工作
 *
 */
public class Main {
	public static void main(String[] args) {
		ExecutorService executor = (ExecutorService) Executors
				.newCachedThreadPool();

		List<Task> taskList = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			Task task = new Task(i + "");
			taskList.add(task);
		}

		List<Future<Result>> resultList = null;
		// 调用ThreadPoolExecutor类的invokeAll()方法。这个类将会返回之前创建的Future对象列表。
		try {
			resultList = executor.invokeAll(taskList);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 使用shutdown()方法结束执行者。
		executor.shutdown();

		// 写入处理Future对象列表任务的结果。
		System.out.println("Main: Printing the results");
		for (int i = 0; i < resultList.size(); i++) {
			Future<Result> future = resultList.get(i);
			try {
				Result result = future.get();
				System.out.println(result.getName() + ": " + result.getValue());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
}
