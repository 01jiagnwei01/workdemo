package com.gxkj.demo.netty.nettypsring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ServerMain {

	public static void main(String[] args) throws BeansException, InterruptedException {
		 
		 ApplicationContext context = new ClassPathXmlApplicationContext(  new String[] { "netty_spring/applicationContext.xml" });
		  IServer server = ((IServer)context.getBean("server"));
		  server.start();
		  
		  //Thread.sleep(3);
		  server.stop();
		}
}
