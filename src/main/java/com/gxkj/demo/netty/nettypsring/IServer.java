package com.gxkj.demo.netty.nettypsring;

public interface IServer {
	 /**
	  * 启动服务器
	 * @throws InterruptedException 
	  */
	 public void start() throws InterruptedException;
	 /**
	  * 重启程序
	 * @throws InterruptedException 
	  */
	 public void restart() throws InterruptedException;
	 
	 /**
	  * 停止程序运行
	  */
	 public void stop();
}
