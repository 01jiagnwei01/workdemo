package com.gxkj.demo.thread.doc.executor.ch5;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class UserValidator {
	private String name;
	//实现UserValidator类的构造器，初始化这个属性。
	public UserValidator(String name) {
		this.name=name;
	}
	//实现validate()方法。接收你想要验证用户的两个String类型参数，一个为name，一个为password
	public boolean validate(String name, String password) {
		Random random=new Random();
		//等待个随机时间，用来模拟用户验证的过程。
		try {
			long duration=(long)(Math.random()*10);
			System.out.printf("Validator %s: Validating a user during %d seconds\n",this.name,duration);
			TimeUnit.SECONDS.sleep(duration);
			} catch (InterruptedException e) {
			return false;
		}
		//返回一个随机Boolean值。如果用户验证通过，这个方法将返回true，否则，返回false。
		return random.nextBoolean();
	}
	//实现getName()方法，返回name属性值
	public String getName(){
		return name;
	 }

}
