package com.gxkj.demo.thread.doc.executor.ch11;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * http://ifeve.com/thread-executors-11/
 *执行者分离任务的启动和结果的处理
 */
public class Main {
	public static void main(String[] args) {
		ExecutorService executor=(ExecutorService)Executors.newCachedThreadPool();
		//创建CompletionService，使用前面创建的执行者作为构造器的参数。
		CompletionService<String> service=new ExecutorCompletionService<>(executor);
		
		ReportRequest faceRequest=new ReportRequest("Face", service);
		ReportRequest onlineRequest=new ReportRequest("Online",service);
		Thread faceThread=new Thread(faceRequest);
		Thread onlineThread=new Thread(onlineRequest);
		//创建一个ReportProcessor对象，并用线程执行它。
		ReportProcessor processor=new ReportProcessor(service);
		Thread senderThread=new Thread(processor);
		
		System.out.printf("Main: Starting the Threads\n");
		faceThread.start();
		onlineThread.start();
		senderThread.start();
		//等待ReportRequest线程的结束
		try {
			System.out.printf("Main: Waiting for the report generators.\n");
			faceThread.join();
			onlineThread.join();
			} catch (InterruptedException e) {
			e.printStackTrace();
			}
		processor.setEnd(true);
		System.out.println("Main: Ends");

	}
}
