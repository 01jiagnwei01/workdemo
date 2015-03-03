package com.gxkj.demo.thread.risk;

import java.util.Date;
import java.util.Vector;


import org.apache.log4j.Logger;



public class Task  implements Runnable
{
    
   

    /**
     * @return the bExit
     */
    public boolean isbExit()
    {
        return bExit;
    }

    /**
     * @param bExit the bExit to set
     */
    public void setbExit(boolean bExit)
    {
        this.bExit = bExit;
    }

    /**
     * @return the c
     */
    public ThreadCountNum getC()
    {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(ThreadCountNum c)
    {
        this.c = c;
    }

    /**
     * @return the runType
     */
    public int getRunType()
    {
        return runType;
    }

    /**
     * @param runType the runType to set
     */
    public void setRunType(int runType)
    {
        this.runType = runType;
    }

  

    /**
     * @return the taskId
     */
    public String getTaskId()
    {
        return taskId;
    }

    /**
     * @param taskId the taskId to set
     */
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }

    /**
     * @param generateTime the generateTime to set
     */
    public void setGenerateTime(Date generateTime)
    {
        this.generateTime = generateTime;
    }

    private static Logger logger = Logger.getLogger(Task.class);
    /* 产生时间 */
    private Date generateTime = null;
    /* 提交执行时间 */
    private Date submitTime = null;
    /* 开始执行时间 */
    private Date beginExceuteTime = null;
    /* 执行完成时间 */
    private Date finishTime = null;

    private String taskId;
    private String name;
    private int taskNum;
    private int runType;
    private int outTime;
    private boolean bExit;
    private int workstatus=0;
    public int getOutTime() {
		return outTime;
	}

	public void setOutTime(int outTime) {
		this.outTime = outTime;
	}

	private ThreadCountNum c;
  

	

	public int init()
    {
	
	    return 0;
	
    }
   
    public Task() 
    {
        this.generateTime = new Date();
    }

    /**
     * 任务执行入口
     */
     public void run() 
     {
        
     }

   

    public Date getGenerateTime() {
        return generateTime;
    }

    public Date getBeginExceuteTime() {
        return beginExceuteTime;
    }

    public void setBeginExceuteTime(Date beginExceuteTime) {
        this.beginExceuteTime = beginExceuteTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    /**
     * @return the iDataType
     */
    public int getiDataType()
    {
	return iDataType;
    }

    /**
     * @param iDataType
     *            the iDataType to set
     */
    public void setiDataType(int iDataType)
    {
	this.iDataType = iDataType;
    }

    private int iDataType;// 待数据数据类型 1 同步 2 异步

    private Vector<Object> dataVector=new Vector<Object>();
    
    
    /**
     * @return the dataVector
     */
    public Vector<Object> getDataVector()
    {
        return dataVector;
    }

    /**
     * @param dataVector the dataVector to set
     */
    public void setDataVector(Vector<Object> dataVector)
    {
        this.dataVector = dataVector;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the taskNum
     */
    public int getTaskNum()
    {
        return taskNum;
    }

    /**
     * @param taskNum the taskNum to set
     */
    public void setTaskNum(int taskNum)
    {
        this.taskNum = taskNum;
    }

    /**
     * @return the workstatus
     *  public synchronized int modifyWorkstatus(int work,int status)
     */
    public  int modifyWorkstatus(int work,int status)
    {
	//工作状态设置  status:1 工作 0空闲
	// work:1 设置  2获取
	
	if(work==1) workstatus=status;
	
	return workstatus;
    }


   
    
}
