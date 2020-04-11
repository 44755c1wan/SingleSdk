package com.ledi.bean;

import java.util.TimerTask;

public class MsgTask extends TimerTask {
	private  String PayStatus;
	private String messages;
	
	 private long deltaTime;  //时间增量，及任务执行等待时间
	 private MsgKey msgKey;
	 private String keys;
	 private String orderid;
	 private MsgProcessor msgProcessor = new MsgProcessor();
	   int i=1;
	 private int indexsize;
	 
	 public int getIndexsize() {
		return indexsize;
	}

	public void setIndexsize(int indexsize) {
		this.indexsize = indexsize;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getOrderid() {
		return orderid;
	}

	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}



	public MsgTask(long deltaTime, MsgKey msgKey, String keys, String orderid,
			int indexsize) {
		super();
		this.deltaTime = deltaTime;
		this.msgKey = msgKey;
		this.keys = keys;
		this.orderid = orderid;
		this.indexsize = indexsize;
	}

	public long getDeltaTime() {
	  return deltaTime;
	 }

	 public void setDeltaTime(long deltaTime) {
	  this.deltaTime = deltaTime;
	 }

	 public MsgKey getMsgKey() {
	  return msgKey;
	 }

	 public void setMsgKey(MsgKey msgKey) {
	  this.msgKey = msgKey;
	 }

	 @Override
	 public void run() {//等待时间到了以后，就执行		
					
	 }

		

	}
