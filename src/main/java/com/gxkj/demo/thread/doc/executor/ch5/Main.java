package com.gxkj.demo.thread.doc.executor.ch5;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * http://ifeve.com/thread-executors-5/
 * @author admin
 *标题：运行多个任务并处理第一个结果
 *副标题：多个运行方案，只要一个成功即可
 */
public class Main {
	public static void main(String[] args) {
		String username="test";
		String password="test";
		
		UserValidator ldapValidator=new UserValidator("LDAP");
		UserValidator dbValidator=new UserValidator("DataBase");
		
		TaskValidator ldapTask=new TaskValidator(ldapValidator,username, password);
		TaskValidator dbTask=new TaskValidator(dbValidator,username,password);
		
		List<TaskValidator> taskList=new ArrayList<>();
		taskList.add(ldapTask);
		taskList.add(dbTask);
		
		ExecutorService executor=(ExecutorService)Executors.newCachedThreadPool();
		String result;
		/**
		 * 们有两个任务，可以返回true值或抛出异常。有以下4种情况：
		 * 两个任务都返回ture。invokeAny()方法的结果是第一个完成任务的名称。
		 * 第一个任务返回true，第二个任务抛出异常。invokeAny()方法的结果是第一个任务的名称。
		 * 第一个任务抛出异常，第二个任务返回true。invokeAny()方法的结果是第二个任务的名称。
		 * 两个任务都抛出异常。在本例中，invokeAny()方法抛出一个ExecutionException异常
		 */
		//调用executor对象的invokeAny()方法。该方法接收taskList参数，返回String类型。
		//返回完成时没有抛出异常的第一个 任务的结果
		try {
			result = executor.invokeAny(taskList);
			System.out.printf("Main: Result: %s\n", result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		executor.shutdown();
		System.out.printf("Main: End of the Execution\n");



	}
}
