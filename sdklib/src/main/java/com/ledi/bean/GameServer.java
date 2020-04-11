package com.ledi.bean;

import java.io.Serializable;


public class GameServer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7302650030443871743L;
	private int code;// 服务器id
	private String name;// 服务器名称
	private int sid;// 游戏方提供服务器id

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}
	
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
