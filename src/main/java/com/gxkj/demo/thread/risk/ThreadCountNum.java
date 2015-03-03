package com.gxkj.demo.thread.risk;

public class ThreadCountNum
{
    private int count;
    public ThreadCountNum(int count){
    this.count = count;
    }
    public synchronized void countDown(){
    count--;
    }
    public synchronized boolean hasNext(){
    return (count > 0);
    }
    public int getCount() {
    return count;
    }
    public void setCount(int count) {
    this.count = count;
    }
}
