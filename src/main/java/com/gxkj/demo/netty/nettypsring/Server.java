package com.gxkj.demo.netty.nettypsring;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.gxkj.demo.netty.socksproxy.SocksServerInitializer;

@Component("server")
public class Server implements IServer {
	
	private static final Log log = 
			LogFactory.getLog(Server.class);
	
	private ServerBootstrap serverBootstrap = null; 
	
	private int localPort = 8099;
	/**
	 * 启动服务
	 * @throws InterruptedException 
	 */
	public void start() throws InterruptedException {
		log.info("启动服务");
		
//		EventLoopGroup bossGroup = new NioEventLoopGroup();
//		EventLoopGroup  workerGroup = new NioEventLoopGroup();
//        try {
//        	System.out.println("执行中");
//        	serverBootstrap = new ServerBootstrap();
//        	serverBootstrap.group(bossGroup, workerGroup)
//             .channel(NioServerSocketChannel.class)
//             .childHandler(new SocksServerInitializer());
//            
//            
//        	serverBootstrap.bind(localPort).sync().channel().closeFuture().sync();
//        } finally {
//            bossGroup.shutdownGracefully();
//            workerGroup.shutdownGracefully();
//        }

	}
	/**
	 * 重启
	 * @throws InterruptedException 
	 */
	public void restart() throws InterruptedException {
		this.stop();
		this.start();
	}

	 /**
	  * 关闭
	  */
	public void stop() {
		
		log.info("关闭服务");
//		serverBootstrap.clone();
	}

}
