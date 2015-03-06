package com.gxkj.demo.thread.doc.executor.ch3;
/**
 * 参考网址：
 * http://ifeve.com/thread-executors-3/
 *
 */
public class Main {

	public static void main(String[] args) throws InterruptedException {
		Server server=new Server();
		for (int i=0; i<100; i++){
			Task task=new Task("Task "+i);
			server.executeTask(task);
		}
		 
		 server.endServer();
	}

}
