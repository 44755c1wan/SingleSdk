package com.ledi.bean;

//才能方便启动和结束，下面的MsgKey就是用来标记任务的实体类：
public class MsgKey {
	 private int index;

	 public int getIndex() {
	  return index;
	 }

	 public void setIndex(int index) {
	  this.index = index;
	 }
	 
	 @Override
	 public boolean equals(Object obj) {
	  if (obj instanceof MsgKey) {
	   MsgKey msgKey = (MsgKey) obj;

	   return this.index == msgKey.getIndex();
	  } else {
	   return false;
	  }
	 }

	 @Override
	 public int hashCode() {
	  return String.valueOf(this.index).hashCode();
	 }

	}