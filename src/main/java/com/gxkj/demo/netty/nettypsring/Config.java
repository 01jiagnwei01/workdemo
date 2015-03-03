package com.gxkj.demo.netty.nettypsring;

import org.springframework.stereotype.Component;

@Component("config")
public class Config {
	private int port = 8099;
	private String serverUrl = "localhost";
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public String getServerUrl() {
		return serverUrl;
	}
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	
	
}
