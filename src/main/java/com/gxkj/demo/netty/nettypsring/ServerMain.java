package com.gxkj.demo.netty.nettypsring;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import com.gxkj.demo.netty.socksproxy.SocksServerInitializer;

public class ServerMain {
	private  int localPort  =  0;
	public void setLocalPort(int localPort) {
		this.localPort = localPort;
	}
	public void run() throws InterruptedException{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
        	System.out.println("执行中");
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .childHandler(new SocksServerInitializer());
            b.bind(localPort).sync().channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
	}


	public static void main(String[] args) throws BeansException, InterruptedException {
		 
		  ApplicationContext factory = new ClassPathXmlApplicationContext(  new String[] { "netty_spring/applicationContext.xml" });
		 ((ServerMain)factory.getBean("serverMain")).run();
		 
		  
		}
}
