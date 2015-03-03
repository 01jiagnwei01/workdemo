package com.gxkj.demo.thread.risk.test;

import java.util.Vector;
import java.util.concurrent.CountDownLatch;

import com.gxkj.demo.thread.risk.Task;

public class RiskRouteService extends Task {
	private int id = 0;
	/**
	 * 多线程技术器
	 */
	CountDownLatch latch ; 
	
	public RiskRouteService(CountDownLatch latch,int id) {
		super();
		this.id = id;
		this.latch = latch;
	}

	public void run() {
		System.out.println(String.format("RiskRouteService——%d执行", id));
		Main.dbDataV.add(this.id);
		
		 latch.countDown();//工人完成工作，计数器减一  
	}
}
