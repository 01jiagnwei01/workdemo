package com.gxkj.demo.netty.nettypsring.pojo;

public class RequestEntity {
	
	/**
	 * 字符串总长度 8位
	 */
	private int totalLength = 0;
	
	/**
	 * 指令码 6位 与Command_ID对应
	 */
	 private String cmdType;
	 
	 /**
	  * 压缩码4位  与enc_compress对应
	  */
	 private String encCompress;
	 /**
	  * 扩展码10位  与expand对应
	  */
	 private String expand;
	 /**
	  * 消息体
	  */
	 private String body;
	 
	public int getTotalLength() {
		return totalLength;
	}
	public void setTotalLength(int totalLength) {
		this.totalLength = totalLength;
	}
	public String getCmdType() {
		return cmdType;
	}
	public void setCmdType(String cmdType) {
		this.cmdType = cmdType;
	}
	public String getEncCompress() {
		return encCompress;
	}
	public void setEncCompress(String encCompress) {
		this.encCompress = encCompress;
	}
	public String getExpand() {
		return expand;
	}
	public void setExpand(String expand) {
		this.expand = expand;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String boyd) {
		this.body = boyd;
	}
	 
	 
	 
}
