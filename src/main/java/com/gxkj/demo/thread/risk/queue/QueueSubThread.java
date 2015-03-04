package com.gxkj.demo.thread.risk.queue;

import java.util.concurrent.BlockingQueue;

public class QueueSubThread extends Thread {
	private BlockingQueue queue;

	/**
	 * @param queue
	 */
	public QueueSubThread(BlockingQueue queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			working();
		} finally {
			try {
				queue.put(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

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
