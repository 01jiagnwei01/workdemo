package com.gxkj.demo.thread.doc.executor.ch8;

import java.util.Date;
import java.util.concurrent.Callable;

public class Task   implements Runnable {

	private String name;
	
	public Task(String name) {
		super();
		this.name = name;
	}

	public String call() throws Exception {
		System.out.printf("%s: Starting at : %s\n",name,new Date());
		return "Hello, world";
	}

	public void run() {
		System.out.printf("run()  %s: Starting at : %s\n",name,new Date());
	}

}
