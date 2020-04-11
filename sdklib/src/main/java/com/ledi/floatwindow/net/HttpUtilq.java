package com.ledi.floatwindow.net;

import android.os.Handler;
import android.os.Message;

import com.ledi.floatwindow.util.Rebate;
import com.ledi.util.Conet;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import com.quicksdk.entity.GameRoleInfo;

public class HttpUtilq {
	public static String STRINGVALID = "success";
	


	public static String getData(String url, NameValuePair[] list) {
//		System.out.println("HttpUtil200--url:" + url);
		String result = "";
		String line = "";
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestBody(list);

		try {
			new HttpClient().executeMethod(postMethod);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int code = 0;
		if (null != postMethod) {
			try {
				code = postMethod.getStatusCode();
			} catch (Exception e) {
			}
			if (code == 200) {
				try {// 读取请求结果
					BufferedReader br = new BufferedReader(
							new InputStreamReader(
									postMethod.getResponseBodyAsStream(),
									"utf-8"));
					while (null != (line = br.readLine())) {// 如果读取值不为空，则返回结果为读取值

						result += line;
					}
					br.close();
//					System.out.println("result====url:" + url);
					return result;
					// result = postMethod.getResponseBodyAsString();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			} else {// 响应码错误则不返回任何结果
				return "";
			}
		}

		return result;
	}
	
//	public static String getData1(String url, GameRoleInfo roleInfo) {
////		System.out.println("HttpUtil200--url:" + url);
//		String result = "";
//		String line = "";
//		PostMethod postMethod = new PostMethod(url);
//		postMethod.setRequestEntity((RequestEntity) roleInfo);
//
//		try {
//			new HttpClient().executeMethod(postMethod);
//
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		int code = 0;
//		if (null != postMethod) {
//			try {
//				code = postMethod.getStatusCode();
//			} catch (Exception e) {
//			}
//			if (code == 200) {
//				try {// 读取请求结果
//					BufferedReader br = new BufferedReader(
//							new InputStreamReader(
//									postMethod.getResponseBodyAsStream(),
//									"utf-8"));
//					while (null != (line = br.readLine())) {// 如果读取值不为空，则返回结果为读取值
//
//						result += line;
//					}
//					br.close();
////					System.out.println("result====url:" + url);
//					return result;
//					// result = postMethod.getResponseBodyAsString();
//				} catch (IOException e) {
//					
//					e.printStackTrace();
//				}
//			} else {// 响应码错误则不返回任何结果
//				return "";
//			}
//		}
//
//		return result;
//	}

	public static String sendGetRequest(String urls, Map<String, String> params) {
		String line;
		String result = null;
		BufferedReader reader = null;
		HttpURLConnection connection = null;

		StringBuilder path = new StringBuilder(urls);
		path.append("?");
		for (Map.Entry<String, String> entry : params.entrySet()) {

			path.append(entry.getKey());
			path.append("=");
			path.append(entry.getValue());
			path.append("&");
		}
		path.deleteCharAt(path.length() - 1); // 去掉最后一个参数后面多余的&

		try {
			// String str=URLEncoder.encode(path.toString(), "UTF-8");
			URL url = new URL(path.toString());
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(10000);
			connection.setReadTimeout(30000);//
			connection.setRequestMethod("GET");

			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				while (null != (line = reader.readLine())) {
					result = line;
				}
				return result;
			}

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		} finally {
			if (null != connection) {
				connection.disconnect();
			}
			if (null != reader) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	/**
	 * 判断字符是否有效
	 * 
	 * @param userName
	 *            用户名
	 * @param newPwd
	 *            新密码
	 * @param oldPwd
	 *            旧密码
	 * @return
	 */
	public static String judgePassInvalid(String userName, String newPwd,
			String oldPwd) {
		if (null == newPwd || "".equals(newPwd)) {
			return "密码不能为空";
		}
		if (newPwd.equals(oldPwd)) {
			return "新密码不能与旧密码一致";
		}
		if (newPwd.equals(userName)) {
			return "用户名与密码不能一致";
		}
		Pattern pattern = Pattern.compile("^_.*");
		Matcher matcher = pattern.matcher(newPwd);
		if (matcher.matches()) {
			return "密码不能以“_”开头";
		}

		if (newPwd.length() < 6 || newPwd.length() > 20) {
			return "密码长度必须大于6位,小于20位";
		}

		Pattern pattern2 = Pattern.compile("[a-zA-Z0-9_]{6,20}");
		Matcher matcher2 = pattern2.matcher(newPwd);
		if (!matcher2.matches()) {
			return "非法字符";
		}
		return STRINGVALID;
	}

	public static String judgePhoneNumberInvalid(String phoneNumber) {
		if (null == phoneNumber || "".equals(phoneNumber)) {
			return "手机号码不能为空";
		}
		// Pattern p = Pattern
		// .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		// Matcher m = p.matcher(phoneNumber);
		// if (!m.matches()) {
		// return "请输入正确的手机号";
		// }
		boolean valid = phoneNumber.matches("^(13|14|15|17|18)\\d{9}$");
		if (!valid) {
			return "请输入正确的手机号";
		}
		return STRINGVALID;
	}

	/**
	 * <h1>获取平台币，折扣，优惠券等信息</h1>
	 * <p>
	 * ①充值金额(元)小于100的不能使用优惠券[返回值里也不会有usable_coupon,coupon这两个参数] 充值金额X：
	 * 100<=X<500 500<=X<1000 1000<=X<3000 3000<=X<5000 5000<=X<1w 1w及以上
	 * 可使用优惠券总的百分比：5%
	 * </p>
	 * 
	 * @param uid
	 *            UID
	 * @param sid
	 *            Sess_id
	 * @param cash
	 *            充值金额
	 * @param handler
	 * @return
	 */
	public static void getPlatformCoin(final String uid, final String sid,
			final String cash, final Handler handler, final int msgWhat) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Map<String, Object> _objRes = new HashMap<String, Object>();
				_objRes.put("status", "0");
				String _url = "http://api.44755.com/wallet/mMoney";
				NameValuePair[] _pair = { new NameValuePair("uid", uid),
						new NameValuePair("sid", sid),
						new NameValuePair("cash", cash),
						new NameValuePair("channel_id", Conet.qid), // 为了充值折扣
						new NameValuePair("game_id", Conet.gid+""), // 为了充值折扣
						new NameValuePair("device", "android") };
				String _response = getData(_url, _pair);
				Message msg = new Message();
				msg.what = msgWhat;
				try {
					if (null != _response && !"".equals(_response)) {

						JSONObject _obj = new JSONObject(_response);
						if (_obj.has("status")) {// 1成功，0失败
							_objRes.put("status", _obj.getInt("status") + "");
						}
						if (_obj.has("rebates")) {// 折扣比例
							JSONArray array = _obj.getJSONArray("rebates");
							ArrayList<Rebate> mRebate = parseList(array);
							_objRes.put("rebates", mRebate);
							// Log.e("json_array",
							// "json_array= " + array.toString());
						}
						if (_obj.has("usable_coupon")) {// 可用优惠券金额①
							_objRes.put("usable_coupon",
									_obj.getInt("usable_coupon") + "");
						}
						if (_obj.has("coupon")) {// 优惠券总额
							_objRes.put("coupon", _obj.getString("coupon"));
						}
						if (_obj.has("money")) {// 平台币余额
							_objRes.put("money", _obj.getString("money"));
						}
					}
					msg.obj = _objRes;

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				handler.sendMessage(msg);
			}
		}).start();
	}

	// 信用卡，储蓄卡的请求
//	public static void getEpayUrl(final String name, final String phone,
//			final String cash, final String way, final Handler handler,
//			final int msgWhat) {
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				String objRes = null;
//				String orderid = null;
//				String url = "http://api.44755.com/yeewallet/wapSubmitOrder";
//				Map<String, String> parm = new HashMap<String, String>();
//				parm.put("to_username", name);
//				parm.put("phone", phone);
//				parm.put("cash", cash);
//				parm.put("way", way);
//				parm.put("device", "android");
//				String _response = sendGetRequest(url, parm);
//				Message msg = new Message();
//				msg.what = msgWhat;
//				try {
//					if (null != _response && !"".equals(_response)) {
//
//						JSONObject _obj = new JSONObject(_response);
//
//						if (_obj.has("url")) {// 平台币余额
//							objRes = _obj.getString("url");
//						}
//						if (_obj.has("orderid")) {
//							orderid = _obj.getString("orderid");
//						}
//						Bundle b = new Bundle();
//						b.putString("url", objRes);
//						b.putString("orderid", orderid);
//						msg.setData(b);
//					}
//					// msg.obj = objRes;
//
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				handler.sendMessage(msg);
//			}
//		}).start();
//	}

//	public static void getAlipayUrl(final String name, final String phone,
//			final String cash, final String way, final Handler handler,
//			final int msgWhat) {
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				String objRes = null;
//				String url = "http://api.44755.com/walletalipaynew/mPay";
//				PayInfobean _payInfobean = null;
//				NameValuePair[] _pair = {
//						new NameValuePair("to_username", name),
//						new NameValuePair("phone", phone),
//						new NameValuePair("cash", cash),
//						new NameValuePair("device", "android"),
//						new NameValuePair("way", way) };
//				String _response = getData(url, _pair);
//
//				Message msg = new Message();
//				msg.what = msgWhat;
//				try {
//					if (null != _response && !"".equals(_response)) {
//						_payInfobean = new PayInfobean();
//						JSONObject _obj = new JSONObject(_response);
//
//						if (_obj.has("orderid")) {
//							_payInfobean.setOrderid(_obj.getString("orderid"));
//						}
//						if (_obj.has("subject")) {
//							// paybean.setSubject("test");
//							if (_obj.getString("subject").equals("")) {
//								_payInfobean.setSubject(_payInfobean
//										.getGame_name()
//										+ _payInfobean.getServer_name()
//										+ "充值"
//										+ _payInfobean.getAmount()
//										+ _payInfobean.getCurrency());
//							} else {
//								_payInfobean.setSubject(_obj
//										.getString("subject"));
//							}
//						}
//
//						if (_obj.has("signinfo")) {
//							_payInfobean.setSigninfo(_obj.getString("signinfo"));
//						}
//					}
//					msg.obj = _payInfobean;
//
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				handler.sendMessage(msg);
//			}
//		}).start();
//	}

//	public static void getWXpayUrl(final Context mContext,
//			final String to_username, final String phone, final String cash,
//			final String way, final String game_id, final String channel_id,
//			final String server_id, final String server_name,
//			final Handler handler, final int msgWhat) {
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//				String money = null;
//				String notify_url = null;
//				String currency = null;
//				String partner = null;
//				String app_id = null;
//				String key = null;
//				String orderid = null;
//				String url = "http://api.44755.com/wallet/mpay";// 正式接口
//				NameValuePair[] _pair = { new NameValuePair("way", way),
//						new NameValuePair("to_username", to_username),
//						new NameValuePair("cash", cash),
//						new NameValuePair("phone", phone),
//						new NameValuePair("game_id", game_id),
//						new NameValuePair("server_id", server_id),
//						new NameValuePair("channel_id", channel_id),
//						new NameValuePair("server_name", server_name),
//						new NameValuePair("device", "android") };
//				String _response = Post.getData(url, _pair);
//				System.out.println("微信充值乐币响应值" + _response);
//				Message msg = new Message();
//				msg.what = msgWhat;
//				try {
//					if (null != _response || !"".equals(_response)) {
//						String Msg = null;
//						JSONObject _obj = new JSONObject(_response);
//						// WxPayUtile.getInstance(mContext,
//						// obj.getString("money").toString(),
//						// obj.getString("notify_url").toString(),
//						// obj.getString("currency").toString(), genOutTradNo())
//						// .doPay();
//
//						if (_obj.has("money")) {
//							money = _obj.getString("money");
//						}
//						if (_obj.has("notify_url")) {
//							notify_url = _obj.getString("notify_url");
//						}
//						if (_obj.has("currency")) {
//							notify_url = _obj.getString("currency");
//						}
//						if (_obj.has("app_id")) {
//							app_id = _obj.getString("app_id");
//						}
//						if (_obj.has("partner")) {
//							partner = _obj.getString("partner");
//						}
//						if (_obj.has("key")) {
//							key = _obj.getString("key");
//						}
//						if (_obj.has("orderid")) {
//							orderid = _obj.getString("orderid");
//						}
//						Bundle b = new Bundle();
//						b.putString("money", money);
//						b.putString("notify_url", notify_url);
//						b.putString("currency", currency);
//						b.putString("notify_url", notify_url);
//						b.putString("app_id", app_id);
//						b.putString("partner", partner);
//						b.putString("key", key);
//						b.putString("orderid", orderid);
//						msg.setData(b);
//						handler.sendMessage(msg);
//
//					} else {
//						Toast.makeText(mContext, "出意外了，请重试！", 1000).show();
//					}
//					// msg.obj = objRes;
//
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//			}
//		}).start();
//	}


	public static ArrayList<Rebate> parseList(JSONArray array) {
		ArrayList<Rebate> _rebateList = new ArrayList<Rebate>();
		int len = array.length();
		for (int i = 0; i < len; i++) {
			JSONObject obj = (JSONObject) array.opt(i);
			try {
				Rebate _rebate = new Rebate();
				if (obj.has("min")) {
					_rebate.min = obj.getInt("min");
				}
				if (obj.has("max")) {
					_rebate.max = obj.getInt("max");
				}
				if (obj.has("rebate")) {
					_rebate.rebate = obj.getInt("rebate");
				}
				_rebateList.add(_rebate);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return _rebateList;
	}

}
