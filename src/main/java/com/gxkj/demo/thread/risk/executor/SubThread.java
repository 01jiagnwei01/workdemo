package com.gxkj.demo.thread.risk.executor;

public class SubThread extends Thread{
	public void run() {
		working();
	}

	private void working() {
		System.out.println("sub thread start working.");
		busy();
		System.out.println("sub thread stop working.");
	}

	private void busy() {
		try {
			sleep(5000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
