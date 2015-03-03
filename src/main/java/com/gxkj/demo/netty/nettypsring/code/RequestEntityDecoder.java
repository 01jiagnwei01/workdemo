package com.gxkj.demo.netty.nettypsring.code;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

public class RequestEntityDecoder extends ReplayingDecoder<RequestEntityDecoder.State>{
	
	public RequestEntityDecoder(){
		super(RequestEntityDecoder.State.READ_LENGTH);
	}

	private int length  = 0;
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf,
			List<Object> out) throws Exception {
		switch (state()) {
		
		case READ_LENGTH:
			     length = buf.readInt();
			  checkpoint(State.READ_CONTENT); 
		case READ_CONTENT:
			 ByteBuf frame = buf.readBytes(length);
			 checkpoint(State.READ_LENGTH);
			 out.add(frame);
			default:
				throw new Error("Shouldn't reach here.");
			
		
		}
	}
	
	public enum State {
		    READ_LENGTH,
		    READ_CONTENT;
	}

}
