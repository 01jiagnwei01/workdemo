package com.gxkj.demo.netty.nettypsring;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.socks.SocksInitRequestDecoder;
import io.netty.handler.codec.socks.SocksMessageEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import com.gxkj.demo.netty.socksproxy.SocksServerHandler;
/**
 * 
 * 继承channel
 *
 */
public class MySocksServerInitializer extends ChannelInitializer<SocketChannel>{

 
	
	protected void initChannel(SocketChannel socketChannel) throws Exception {
		 
		  ChannelPipeline channelPipeline = socketChannel.pipeline();
		  
		  channelPipeline.addLast(new LoggingHandler(LogLevel.INFO));
		  /**
		   * 解码器
		   */
		  channelPipeline.addLast(SocksInitRequestDecoder.getName(), new SocksInitRequestDecoder());
		  
		  /**
		   * 编码器
		   */
		  channelPipeline.addLast(SocksMessageEncoder.getName(),  new SocksMessageEncoder());
		  
		  /**
		   * 消息处理器
		   */
		  channelPipeline.addLast(SocksServerHandler.getName(), new MySocksServerHandler());
	}

}
