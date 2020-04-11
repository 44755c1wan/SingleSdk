package com.ledi.bean;

public class Orderlist {
private String order;
private String keys;
private String uid;
private int moneylist;
private long time;

public long getTime() {
	return time;
}
public void setTime(long time) {
	this.time = time;
}
public String getOrder() {
	return order;
}
public void setOrder(String order) {
	this.order = order;
}
public String getKeys() {
	return keys;
}
public void setKeys(String keys) {
	this.keys = keys;
}
public String getUid() {
	return uid;
}
public void setUid(String uid) {
	this.uid = uid;
}
public int getMoneylist() {
	return moneylist;
}
public void setMoneylist(int moneylist) {
	this.moneylist = moneylist;
}
public Orderlist(String order, String keys, String uid, int moneylist, long time) {
	super();
	this.order = order;
	this.keys = keys;
	this.uid = uid;
	this.moneylist = moneylist;
	this.time = time;
}
public Orderlist() {
	super();
	// TODO Auto-generated constructor stub
}





}
