package com.gxkj.demo.thread.risk.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;


public class Main {
	 public static Vector<Integer> dbDataV = new Vector<Integer>();
	 final static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	public static void main(String[] args) throws InterruptedException {
		int threadNum = 20;
		
		CountDownLatch latch = new CountDownLatch(threadNum);//初始化countDown  
		long begintime = System.currentTimeMillis();
		for(int i=0;i<threadNum;i++){
			RiskRouteService s = new RiskRouteService(latch,i+1);
			Thread thread = new Thread(s);
			thread.start();
		}
		long endintime = System.currentTimeMillis();
		 latch.await();//等待所有工人完成工作 
		 System.out.println("所有工作结束："+sdf.format(new Date())+";耗时："+(endintime-begintime));;  
	}
}
