package com.gxkj.demo.thread.doc;
/**
 * 个标准的wait和notify的例子，这个例子有两个线程，第一个等待共享的一个值为false，当为false时它进行print，
 * 另外一个在睡眠了一段时间后，将这个值由原有的true改为false并notify。
 *
 */
public class WaitThreadTest {
	static boolean flag = true;
	static Object OBJ = new Object();
	public static void main(String[] args) {
		Thread t1 = new Thread(new Waiter());
		t1.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Thread t2 = new Thread(new Notifier());
		t2.start();
	}

	/**
	 * 等待，如果flag为false则打印
	 */
	static class Waiter implements Runnable {

		@Override
		public void run() {
			// 加锁，拥有OBJ的Monitor
			synchronized (OBJ) {
				// 当条件不满足时，继续wait，同时释放了OBJ的锁
				while (flag) {
					try {
						System.out.println(Thread.currentThread()
								+ " still true. wait......");
						OBJ.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				// 条件满足时，完成工作
				System.out
						.println(Thread.currentThread() + " is false. doXXX.");
			}
		}
	}

	static class Notifier implements Runnable {

		@Override
		public void run() {
			synchronized (OBJ) {

				// 获取OBJ的锁，然后进行通知，通知时不会释放OBJ的锁
				// 这也类似于过早通知
				OBJ.notifyAll();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				flag = false;
				OBJ.notifyAll();
			}
		}
	}
}
