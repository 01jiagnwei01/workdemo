package com.gxkj.demo.thread.doc.executor.ch11;

import java.util.concurrent.CompletionService;

public class ReportRequest implements Runnable {
	
	private String name;
	
	//声明私有的、CompletionService类型的属性service。CompletionService接口是个参数化接口，使用String类型参数化它。

	private CompletionService<String> service;
	
	public ReportRequest(String name, CompletionService<String> service){
		this.name=name;
		this.service=service;
	}
	
	@Override
	public void run() {
	ReportGenerator reportGenerator=new ReportGenerator(name,"Report");
	service.submit(reportGenerator);
	}


	
}
