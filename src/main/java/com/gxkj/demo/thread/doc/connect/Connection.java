package com.gxkj.demo.thread.doc.connect;

public class Connection {
	public void sendStatement() {
        try {
            Thread.sleep(10);
            System.out.println(Thread.currentThread() + " Send Statement");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
