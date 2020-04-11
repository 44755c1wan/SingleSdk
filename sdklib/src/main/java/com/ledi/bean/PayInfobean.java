package com.ledi.bean;

public class PayInfobean {
//  {"orderid":"201407211002599377",
//	  "to_username":"shizu",
//	  "to_uid":"79792",
//	  "cash":"10.00",
//	  "amount":"100",
//	  "game_id":"2001",
//	  "game_name":"\u897f\u6e38\u964d\u9b54(\u81f3\u5c0a\u7248)"
//		  ,"server_id":"19","server_name":"19.\u4e49\u7ed3\u9547\u5143",
//		  "rolename":"",
//		  "way":"ALIPAY",
//		  "way_name":"\u652f\u4ed8\u5b9d",
//		  "currency":"\u5143\u5b9d",
//		  "sn":"","pass":"","msg":"",
//		  "phone":"12345678912",
//		  "notify_url":"http:\/\/api.ledi.com\/alipay\/mCallback",
//		  "partner":"2088801985601732",
//		  "seller_id":"wangyou@g1wan.com",
//		  "show_url":"",
//		  "subject":"","body":""}
	private String orderid;
	private String to_username;
	private String to_uid;
	private double cash;
	private int amount;
	private String game_id;
	private String game_name;
	private int server_id;
	private String server_name;
	private String rolename;
	private String way;
	private String way_name;
	private String currency;//充值名字 eg元宝
	private String sn;//充值卡，卡号
	private String pass;//mima
	private String phone;
	private String notify_url;
	private String partner;
	private String seller_id;
	private String showurl;
	private String subject;
	private String body;
	private String msg;
	private double discountMoney;//打折后的money

	private String signinfo;//新支付宝签名后的信息
	
	
	public String getSigninfo() {
		return signinfo;
	}
	public void setSigninfo(String signinfo) {
		this.signinfo = signinfo;
	}
	public void setDiscountMoney(double discountMoney) {
		this.discountMoney = discountMoney;
	}
	public double getDiscountMoney() {
		return discountMoney;
	}
	public void setDiscountMoney(Double discountMoney) {
		this.discountMoney = discountMoney;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public PayInfobean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getTo_username() {
		return to_username;
	}
	public void setTo_username(String to_username) {
		this.to_username = to_username;
	}
	public String getTo_uid() {
		return to_uid;
	}
	public void setTo_uid(String to_uid) {
		this.to_uid = to_uid;
	}
	public double getCash() {
		return cash;
	}
	public void setCash(double cash) {
		this.cash = cash;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getGame_id() {
		return game_id;
	}
	public void setGame_id(String game_id) {
		this.game_id = game_id;
	}
	public String getGame_name() {
		return game_name;
	}
	public void setGame_name(String game_name) {
		this.game_name = game_name;
	}
	public int getServer_id() {
		return server_id;
	}
	public void setServer_id(int server_id) {
		this.server_id = server_id;
	}
	public String getServer_name() {
		return server_name;
	}
	public void setServer_name(String server_name) {
		this.server_name = server_name;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public String getWay() {
		return way;
	}
	public void setWay(String way) {
		this.way = way;
	}
	public String getWay_name() {
		return way_name;
	}
	public void setWay_name(String way_name) {
		this.way_name = way_name;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getPartner() {
		return partner;
	}
	public void setPartner(String partner) {
		this.partner = partner;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String getShowurl() {
		return showurl;
	}
	public void setShowurl(String showurl) {
		this.showurl = showurl;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	
			
}
