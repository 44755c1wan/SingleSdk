package com.ledi.bean;

public class User {
	private int _id;
	private String username;// 用户名
	private String password; // 密码
	private String nickname; // 昵称
	private int isLastTime; // 是否是上次登录 0:否 1:是
	private int isQuick; // 是否是快速注册 0:否 1:是
	private int state; //  0:不记住密码 1:记住密码
    private String bindid;//绑定的id
    private int ispaytime;//上次充值的时间
    private String uid;//用户id
    private String phonecode;
	private String mobile;//传递手机号
    public User(){}
    public User(String uid,String username, String password, String nickname,
			int isLastTime, int isQuick, int state,String bindid,int ispaytime) {
    	this.uid=uid;
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.isLastTime = isLastTime;
		this.isQuick = isQuick;
		this.state = state;
		this.bindid=bindid;
		this.ispaytime=ispaytime;
	}
    
	public User(String uid, String username, String password, String nickname,
			int isLastTime, int isQuick, int state, String bindid,
			int ispaytime,  String mobile) {
		this.uid=uid;
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.isLastTime = isLastTime;
		this.isQuick = isQuick;
		this.state = state;
		this.bindid = bindid;
		this.ispaytime = ispaytime;
		this.mobile = mobile;
	}
	public User(String uid, String username, String password, String nickname,
			int isLastTime, int isQuick, int state, String bindid,
			int ispaytime,  String mobile,String phonecode) {
		this.uid=uid;
		this.username = username;
		this.password = password;
		this.nickname = nickname;
		this.isLastTime = isLastTime;
		this.isQuick = isQuick;
		this.state = state;
		this.bindid = bindid;
		this.ispaytime = ispaytime;
		this.mobile = mobile;
		this.phonecode=phonecode;
	}
	public String getPhonecode() {
		return phonecode;
	}
	public void setPhonecode(String phonecode) {
		this.phonecode = phonecode;
	}
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getBindid() {
		return bindid;
	}

	public void setBindid(String bindid) {
		this.bindid = bindid;
	}

	public int getIspaytime() {
		return ispaytime;
	}

	public void setIspaytime(int ispaytime) {
		this.ispaytime = ispaytime;
	}

	

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getIsLastTime() {
		return isLastTime;
	}

	public void setIsLastTime(int isLastTime) {
		this.isLastTime = isLastTime;
	}

	public int getIsQuick() {
		return isQuick;
	}

	public void setIsQuick(int isQuick) {
		this.isQuick = isQuick;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getState() {
		return state;
	}
	@Override
	public String toString() {
		return "User [_id=" + _id + ", username=" + username + ", password="
				+ password + ", nickname=" + nickname + ", isLastTime="
				+ isLastTime + ", isQuick=" + isQuick + ", state=" + state
				+ ", bindid=" + bindid + ", ispaytime=" + ispaytime + ", uid="
				+ uid + ", mobile=" + mobile + "]";
	}

}
