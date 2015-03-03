package com.gxkj.demo.thread.risk;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;






public class ThreadPool extends ThreadGroup
{

    /**
     * @return the threadCount
     */
    public ThreadCountNum getThreadCount()
    {
	return threadCount;
    }

    /**
     * @param threadCount
     *            the threadCount to set
     */
    public void setThreadCount(ThreadCountNum threadCount)
    {
	this.threadCount = threadCount;
    }

    /**
     * @return the threadPoolID
     */
    public String getThreadPoolID()
    {
	return threadPoolID;
    }

    /**
     * @param threadPoolID
     *            the threadPoolID to set
     */
    public void setThreadPoolID(String threadPoolID)
    {
	this.threadPoolID = threadPoolID;
    }

    private static Logger logger = Logger.getLogger(ThreadPool.class);
    // private static Logger taskLogger = Logger.getLogger("TaskLogger");

    // private static boolean debug = taskLogger.isDebugEnabled();
    // private static boolean debug = taskLogger.isInfoEnabled();
    /* 单例 */
    private static ThreadPool instance = null;

    public final int SYSTEM_BUSY_TASK_COUNT = 150;
    /* 默认池中线程数 */
    public int worker_num = 5;
    private ThreadCountNum threadCount;
    /* 已经处理的任务数 */
    private int taskCounter = 0;

    public boolean systemIsBusy = false;

    private List<Task> taskQueue = Collections.synchronizedList(new LinkedList<Task>());

    private String threadPoolID = "1"; // 线程池的id
    private boolean poolType = true; // false:线程退出 true 循环

    private int now_id = 0;
  //  private int jj = 0;
    private int kk=0;

    public synchronized void setTaskData(String jsonData)
    {
	
	Task task = null;
	while (task == null&&kk<worker_num)
	{
	    if (now_id == worker_num)now_id = 0;
	    task = workers[now_id].getR();

	    if (task != null)
	    {
		Vector vector = task.getDataVector();
		vector.add(jsonData);
		now_id++;
		kk=0;
		break;
	    }
	    now_id++;
	    kk++;
	}
	
	

    }

    public boolean getPoolType()
    {
	return poolType;
    }

    public void setPoolType(boolean poolType)
    {
	this.poolType = poolType;
    }

    /* 池中的所有线程 */
    public PoolWorker[] workers;

    public ThreadPool(String threadPoolId)
    {
	super(threadPoolId);
	setThreadPoolID(threadPoolId);
	workers = new PoolWorker[5];
	for (int i = 0; i < workers.length; i++)
	{
	    workers[i] = new PoolWorker(i);
	    workers[i].setPoolType(this.poolType);
	}
    }

    public ThreadPool(String threadPoolId, int pool_worker_num)
    {
	super(threadPoolId);
	setThreadPoolID(threadPoolId);
	worker_num = pool_worker_num;
	workers = new PoolWorker[worker_num];
	for (int i = 0; i < workers.length; i++)
	{
	    workers[i] = new PoolWorker(i);
	    workers[i].setPoolType(poolType);
	}
    }

    public ThreadPool(String threadPoolId, int pool_worker_num, boolean poolType)
    {
	super(threadPoolId);
	setThreadPoolID(threadPoolId);
	setPoolType(poolType);
	worker_num = pool_worker_num;
	workers = new PoolWorker[worker_num];
	for (int i = 0; i < workers.length; i++)
	{
	    workers[i] = new PoolWorker(i);
	    workers[i].setPoolType(poolType);
	}
    }

    public void startAllWorkThread()
    {
	for (int i = 0; i < worker_num; i++)
	{
	    workers[i].start();
	  
	}
	// taskQueue.notifyAll();

    }

    public static synchronized ThreadPool getInstance(int pool_worker_num)
    {
	if (instance == null)
	{
	    if (pool_worker_num > 0)
	    {
		return new ThreadPool("1", pool_worker_num);
	    } else
	    {
		return new ThreadPool("1");
	    }
	}
	return instance;
    }

    /**
     * 增加新的任务 每增加一个新任务，都要唤醒任务队列
     * 
     * @param newTask
     */
    public void addTask(Task newTask)
    {
	synchronized (taskQueue)
	{
	    newTask.setTaskNum(++taskCounter);
	    newTask.setSubmitTime(new Date());
	    taskQueue.add(newTask);
	    /* 唤醒队列, 开始执行 */
	    // taskQueue.notifyAll();
	}
	// logger.info("Submit Task<" + newTask.getTaskId() + ">: ");
    }

    public void waitFinish()
    {
	synchronized (this)
	{
	    notifyAll(); // 唤醒所有还在getTask()方法中等待任务的工作线程
	}
	Thread[] threads = new Thread[activeCount()]; // activeCount()
						      // 返回该线程组中活动线程的估计值。
	int count = enumerate(threads); // enumerate()方法继承自ThreadGroup类，根据活动线程的估计值获得线程组中当前所有活动的工作线程
	for (int i = 0; i < count; i++)
	{ // 等待所有工作线程结束
	    try
	    {

		threads[i].join(); // 等待工作线程结束

	    } catch (InterruptedException ex)
	    {

	    }
	}

    }

    /**
     * 批量增加新任务
     * 
     * @param taskes
     */
    public void batchAddTask(Task[] taskes)
    {
	if (taskes == null || taskes.length == 0)
	{
	    return;
	}
	synchronized (taskQueue)
	{
	    for (int i = 0; i < taskes.length; i++)
	    {
		if (taskes[i] == null)
		{
		    continue;
		}
		taskes[i].setTaskNum(++taskCounter);
		taskes[i].setSubmitTime(new Date());
		taskQueue.add(taskes[i]);
	    }
	    /* 唤醒队列, 开始执行 */
	    taskQueue.notifyAll();
	}
	for (int i = 0; i < taskes.length; i++)
	{
	    if (taskes[i] == null)
	    {
		continue;
	    }
	    logger.info("Submit Task<" + taskes[i].getTaskId() + ">: ");
	}
    }

    /**
     * 线程池信息
     * 
     * @return String
     */
    public String getInfo()
    {
	StringBuffer sb = new StringBuffer();
	sb.append("\nTask Queue Size:" + taskQueue.size());
	for (int i = 0; i < workers.length; i++)
	{
	    sb.append("\nWorker " + i + " is " + ((workers[i].isWaiting()) ? "Waiting." : "Running."));
	}
	return sb.toString();
    }

    /**
     * 销毁线程池
     */
    public synchronized void closePool()
    {
	for (int i = 0; i < worker_num; i++)
	{
	    workers[i].stopWorker();
	    workers[i] = null;
	}
	taskQueue.clear();
	// super.destroy();
    }

    /**
     * 池中工作线程
     * 
     * @author obullxl
     */
    private class PoolWorker extends Thread
    {
	private int index = -1;
	/* 该工作线程是否有效 */
	private boolean isRunning = true;
	/* 该工作线程是否可以执行新任务 */
	private boolean isWaiting = true;
	private boolean poolType = false; // ture:线程退出 false 循环

	public boolean isPoolType()
	{
	    return poolType;
	}

	public void setPoolType(boolean poolType)
	{
	    this.poolType = poolType;
	}

	public PoolWorker(int index)
	{
	    super(ThreadPool.this, index + "");
	    this.index = index;
	    // start();
	}

	public void stopWorker()
	{
	    this.isRunning = false;
	}

	public boolean isWaiting()
	{
	    return this.isWaiting;
	}

	/**
	 * 循环执行任务 这也许是线程池的关键所在
	 */

	private int i = 0;
	private Task r = null;

	public void run()
	{
	    while (isRunning)
	    {

		synchronized (taskQueue)
		{
		    while (taskQueue.isEmpty() && isRunning)
		    {

			if (poolType == false)
			{
			    i = 1;
			    isRunning = poolType;
			    break;
			}

			try
			{
			    /* 任务队列为空，则等待有新任务加入从而被唤醒 */
			    taskQueue.wait(5);
			} catch (InterruptedException ie)
			{
			    logger.error(ie);
			}
		    }

		    /* 取出任务执行 */
		    if (i == 0)
			r = (Task) taskQueue.remove(0);
		}
		
		if (i==0&&r != null)
		{
		    isWaiting = false;
		    try
		    {
			if (r.getRunType() == 1)
			    r.run();
			else
			    new Thread(r).start();

		    } catch (Exception e)
		    {
			System.out.println(r.getClass() + "线程池异常" + e);
			logger.error(r.getClass() + "线程池异常" + e);
		    }
		    isWaiting = true;
		    // r = null;
		}
	    }

	}

	/**
	 * @return the r
	 */
	public Task getR()
	{
	    return r;
	}

	/**
	 * @param r
	 *            the r to set
	 */
	public void setR(Task r)
	{
	    this.r = r;
	}

    }

    /**
     * @return the taskCounter
     */
    public int getTaskCounter()
    {
        return taskCounter;
    }

    /**
     * @param taskCounter the taskCounter to set
     */
    public void setTaskCounter(int taskCounter)
    {
        this.taskCounter = taskCounter;
    }

    /**
     * @return the taskQueue
     */
    public List<Task> getTaskQueue()
    {
        return taskQueue;
    }

    /**
     * @param taskQueue the taskQueue to set
     */
    public void setTaskQueue(List<Task> taskQueue)
    {
        this.taskQueue = taskQueue;
    }
}
