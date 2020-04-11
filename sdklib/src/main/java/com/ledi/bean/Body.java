package com.ledi.bean;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * 快捷支付的bindid方法
 */
public class Body implements Parcelable {
	private String bindid;// 绑定的id
	private String card_name;// 银行的名字
	private String card_last;// 尾号
	private String card_type;// 银行卡类型

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getCard_last() {
		return card_last;
	}

	public void setCard_last(String card_last) {
		this.card_last = card_last;
	}

	public String getBindid() {
		return bindid;
	}

	public void setBindid(String bindid) {
		this.bindid = bindid;
	}

	public String getCard_name() {
		return card_name;
	}

	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}

	public Body() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeString(bindid);
		dest.writeString(card_last);
		dest.writeString(card_name);
		dest.writeString(card_type);
		// dest.writeString(bindid);
	}

	public static final Creator<Body> CREATOR = new Creator<Body>() {
		// 实现从source中创建出类的实例的功能
		@Override
		public Body createFromParcel(Parcel source) {
			Body body = new Body();
			body.bindid = source.readString();
			body.card_last = source.readString();
			body.card_name = source.readString();
			body.card_type = source.readString();
			return body;
		}

		@Override
		public Body[] newArray(int size) {
			return new Body[size];
		}
	};

}
