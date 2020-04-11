package com.ledi.bean;

import java.io.Serializable;

public class LoadData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2388899170008138245L;
	private int game_id; // 游戏id
	private String screen; // 分辨率
	private String path; // 数据包下载地址
	private int create_time; // 创建时间
	private String version; // 版本号
	private int isUpdate;// 是否更新海报. 0: 不更新 1:更新

	public int getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(int isUpdate) {
		this.isUpdate = isUpdate;
	}

	public int getGame_id() {
		return game_id;
	}

	public void setGame_id(int game_id) {
		this.game_id = game_id;
	}

	public String getScreen() {
		return screen;
	}

	public void setScreen(String screen) {
		this.screen = screen;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getCreate_time() {
		return create_time;
	}

	public void setCreate_time(int create_time) {
		this.create_time = create_time;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
