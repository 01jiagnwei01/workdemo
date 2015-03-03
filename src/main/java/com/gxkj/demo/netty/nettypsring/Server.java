package com.gxkj.demo.netty.nettypsring;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gxkj.demo.netty.echo.EchoServerHandler;
import com.gxkj.demo.netty.socksproxy.SocksServerInitializer;

@Component("server")
public class Server implements IServer {
	
	private static final Log log = 
			LogFactory.getLog(Server.class);
	@Autowired
	private Config config;
	
	private ChannelFuture channelFuture =  null;
	
	 
	/**
	 * 启动服务
	 * @throws InterruptedException 
	 */
	public void start() throws InterruptedException {
		log.info("启动服务");
		
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup  workerGroup = new NioEventLoopGroup();
        try {
        	ServerBootstrap serverBootstrap = new ServerBootstrap();
        	serverBootstrap.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .option(ChannelOption.SO_BACKLOG, 100)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 public void initChannel(SocketChannel ch) throws Exception {
                     ch.pipeline().addLast(
                             new LoggingHandler(LogLevel.INFO),
                             new EchoServerHandler());
                 }
             });

            // Start the server.
            channelFuture = serverBootstrap.bind(config.getPort()).sync();

            // Wait until the server socket is closed.
            channelFuture.channel().closeFuture().sync();
        	 
        } finally {
        	System.out.println("优雅关闭");
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
		
		log.info("关闭服务");
		channelFuture.channel().close();
	}

}
