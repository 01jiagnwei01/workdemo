package com.gxkj.demo.thread.risk.test;

import java.util.Vector;


public class Main {
	 public static Vector<Integer> dbDataV = new Vector<Integer>();
	 
	public static void main(String[] args) {
		int l = 20;
		for(int i=0;i<l;i++){
			RiskRouteService s = new RiskRouteService(i+1);
			Thread thread = new Thread(s);
			thread.start();
		}
	}
}
