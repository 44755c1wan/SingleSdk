package com.ledi.bean;

import android.util.Log;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class MsgManager {
	 private static Timer timer = new Timer();
	 private static Map<MsgKey, MsgTask> msgTasks = Collections
	   .synchronizedMap(new HashMap<MsgKey, MsgTask>());

	 public static void putMsgTask(MsgKey msgKey,
	   MsgTask msgTask) {
	  synchronized (msgTasks) {
	   msgTasks.put(msgKey, msgTask);
	  }
	 }

	 public static void startMsgTask(MsgKey msgKey,
	   MsgTask msgTask) {
	  putMsgTask(msgKey, msgTask);
	  timer.schedule(msgTask, msgTask.getDeltaTime());
	  Log.d("zyr",""+msgKey.getIndex());
	 }

	 public static MsgTask removeMsgTask(MsgKey msgKey) {
	  MsgTask msgTask = null;
	  synchronized (msgTasks) {
	   msgTask = msgTasks.remove(msgKey);
	  }
	  return msgTask;
	 }

	 public static boolean stopMsgTask(MsgKey msgKey) {
	  MsgTask msgTask = removeMsgTask(msgKey);
	  if(msgTask != null)
	  {
	   msgTask.cancel();
	   return true;
	  }
	  return false;
	 }

	}
