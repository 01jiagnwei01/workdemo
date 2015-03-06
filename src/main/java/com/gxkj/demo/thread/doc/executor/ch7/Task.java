package com.gxkj.demo.thread.doc.executor.ch7;

import java.util.Date;
import java.util.concurrent.Callable;

public class Task implements Callable<String> {

	private String name;
	public Task(String name) {
		this.name=name;
	 }

	public String call() throws Exception {
		System.out.printf("%s: Starting at : %s\n",name,new Date());
		return "Hello, world";
	 }


}
