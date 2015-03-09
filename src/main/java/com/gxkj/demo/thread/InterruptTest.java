package com.gxkj.demo.thread;
/**
 * 线程中断测试
 * @author admin
 *
 */
public class InterruptTest {

	public static void main(String[] args) throws InterruptedException {
		InterruptedJob ij = new InterruptedJob();
        ij.setName("InterruptedJobThread ");
        ij.start();
 
        Thread.sleep(2000);
 
        // 中断
        ij.interrupt();
        System.out.println("INTERRUPTED IJ");
 
        Thread.sleep(2000);

	}
	/**
	 * 一旦抛出InterruptedException，当前线程的中断状态就被清除，但是也可以调用Thread.interrupted()来清除当前的中断状态。
	 * @author admin
	 *
	 */
	 static class InterruptedJob extends Thread {
		 	        @Override
		 	        public void run() {
		 	            try {
		 	                while (true) {
		 	                    Thread.sleep(1000);
		 	                }
		 	            } catch (InterruptedException e) {
		 	                System.out.println("CURRENT INTERRUPT STATUS IS "
		 	                        + Thread.currentThread().getName()
		 	                        + Thread.currentThread().isInterrupted());
		 	                // 再次进行中断
		 	                Thread.currentThread().interrupt();
		 	 
		 	                System.out.println("CURRENT INTERRUPT STATUS IS "
		 	                        + Thread.currentThread().getName()
		 	                        + Thread.currentThread().isInterrupted());
		 	            }
		 	        }
		 	    }

}
