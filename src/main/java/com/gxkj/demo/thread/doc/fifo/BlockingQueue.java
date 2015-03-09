package com.gxkj.demo.thread.doc.fifo;

public class BlockingQueue<E> {
	/**
     * 默认队列长度
     */
    private static final int DEFAULT_SIZE = 10;
    /**
     * 队列数组
     */
    private Object[]         array;
    /**
     * 当前的长度
     */
    private int              size;
    /**
     * 将要放置的位置
     */
    private int              head;
    /**
     * 将要移除的位置
     */
    private int              tail;

    public BlockingQueue(int size){
        array = size > 0 ? new Object[size] : new Object[DEFAULT_SIZE];
    }

    public BlockingQueue(){
        this(DEFAULT_SIZE);
    }

    public int getCapacity() {
        return array.length;
    }

    /**
     * @return
     */
    public int getSize() {
        synchronized (array) {
            return size;
        }
    }

    public <E> E take(long millis) throws InterruptedException {
        long waitTime = millis > 0 ? millis : 0;
        synchronized (array) {
            Object result = null;
            if (waitTime == 0) {
                while (size <= 0) {
                    array.wait();
                }

                result = array[tail];
                size--;
                tail = (tail + 1) % getCapacity();

            } else {
                long future = System.currentTimeMillis() + waitTime;
                long remain = waitTime;

                while (size <= 0 && remain > 0) {
                    array.wait(remain);
                    remain = future - System.currentTimeMillis();
                }

                if (size > 0) {
                    result = array[tail];
                    size--;
                    tail = (tail + 1) % getCapacity();

                }

            }

            array.notifyAll();
            return (E) result;
        }
    }

    public E take() throws InterruptedException {
        return take(0);
    }

    public boolean offer(E e, long mills) throws InterruptedException {
        long waitTime = mills > 0 ? mills : 0;
        boolean result = false;
        if (e != null) {
            synchronized (array) {
                if (waitTime <= 0) {
                    while (size >= getCapacity()) {
                        array.wait();
                    }

                    array[head] = e;
                    size++;
                    head = (head + 1) % getCapacity();

                    result = true;
                } else {
                    long future = System.currentTimeMillis() + waitTime;
                    long remain = waitTime;

                    while (size >= getCapacity() && remain > 0) {
                        array.wait(remain);
                        remain = future - System.currentTimeMillis();
                    }

                    if (size < getCapacity()) {
                        array[head] = e;
                        size++;
                        head = (head + 1) % getCapacity();

                        result = true;
                    }
                }

                array.notifyAll();
            }
        }

        return result;
    }

    public boolean offer(E e) throws InterruptedException {
        return offer(e, 0);
    }

    public void printQueue() {
        synchronized (array) {
            System.out.println("======================");
            for (int i = 0; i < size; i++) {
                System.out.println("[" + i + "]" + array[i]);
            }
            System.out.println("[head]" + head);
            System.out.println("[tail] " + tail);
            System.out.println("[size]" + size);
            System.out.println("======================");
        }
    }
}
