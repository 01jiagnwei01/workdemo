package com.gxkj.demo.netty.nettypsring;

import io.netty.bootstrap.ServerBootstrap;
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
	
	private EventLoopGroup bossGroup = null;
	
	private EventLoopGroup workerGroup = null;
	
	private int localPort = 109999;
	/**
	 * 启动服务
	 * @throws InterruptedException 
	 */
	public void start() throws InterruptedException {
		log.info("启动服务");
		
		  bossGroup = new NioEventLoopGroup();
          workerGroup = new NioEventLoopGroup();
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
		 

	}

}
