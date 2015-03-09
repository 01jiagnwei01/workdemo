package com.gxkj.demo.thread.vectors;

import java.util.Vector;

public class VectorTest {
public static void main(String[] args) {
	//使用Vector的构造方法进行创建
	Vector v = new Vector(4); 
	//向Vector中添加元素
	//使用add方法直接添加元素
	v.add("Test0");
	sayCapacity(v);
	saySize(v);
	v.add("Test1");
	sayCapacity(v);
	saySize(v);
	v.add("Test0");
	sayCapacity(v);
	saySize(v);
	v.add("Test2");
	sayCapacity(v);
	saySize(v);
	v.add("Test3");
	sayCapacity(v);
	saySize(v);

	//从Vector中删除第一个匹配的元素
	v.remove("Test0"); //删除指定内容的元素
	sayCapacity(v);
	saySize(v);
	String removeString = (String) v.remove(0); //按照索引号删除元素
	System.out.println("被移除的元素为:"+removeString);
	sayCapacity(v);
	saySize(v);

	//获得Vector中已有元素的个数
	int size = v.size();
	System.out.println("size:" + size);

	//遍历Vector中的元素
	for(int i = 0;i < v.size();i++){
		System.out.println(v.get(i));
		sayCapacity(v);
		saySize(v);
	}
	  
}
public static void sayCapacity(Vector v){
	System.out.println("capacity= "+v.capacity());
}
public static void saySize(Vector v){
	System.out.println("size= "+v.size());
}
}
