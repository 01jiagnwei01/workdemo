package com.gxkj.demo.thread.doc;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * Piped这个词就是管道，相当于从一端入一端出的输入输出流。
 * 只是不是从网络和文件上读入内容，而是在线程之间传递数据，而传输的媒介为内存。
 *
 */
public class PipedTest {
	static class Print implements Runnable {
		private PipedInputStream in;

		public Print(PipedInputStream in) {
			this.in = in;
		}

		@Override
		public void run() {
			int receive = 0;
			try {
				while ((receive = in.read()) != -1) {
					System.out.println(receive);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		PipedOutputStream out = new PipedOutputStream();
		PipedInputStream in = new PipedInputStream();

		/**
		 * 对于Piped类型的流，必须要进行connect，如果没有绑定，对于该流的访问会抛出异常。
		 */
		// Out ==> In
		out.connect(in);

		Thread t = new Thread(new Print(in));
		t.start();

		int receive = 0;

		while ((receive = System.in.read()) != -1) {
			out.write(receive);
		}
	}
}
