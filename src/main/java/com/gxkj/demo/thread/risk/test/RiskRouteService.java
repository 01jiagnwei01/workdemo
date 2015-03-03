package com.gxkj.demo.thread.risk.test;

import java.util.Vector;

import com.gxkj.demo.thread.risk.Task;

public class RiskRouteService extends Task {
	private int id = 0;
	
	public RiskRouteService(int id) {
		super();
		this.id = id;
	}

	public void run() {
		System.out.println(String.format("RiskRouteService——%d执行", id));
		Main.dbDataV.add(this.id);
	}
}
