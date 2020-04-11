package com.ledi.biz;

import com.ledi.http.HttpTool;
import com.ledi.util.Conet;
import com.ledi.util.ParseData;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;


public class FatherBiz {
	/**
	 * 请求数据
	 * 
	 * @param userInforMation
	 * @param Terrace true:get请求  false:post请求
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getString(String userInforMation, boolean Terrace) {
		ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		BasicNameValuePair value = new BasicNameValuePair("i", userInforMation);
		params.add(value);
		String str = "";
		String result = "";
		try {
			if (Terrace) {
			//返回请求结果
				result = HttpTool.toSring(Conet.TerraceUrl, params,
						HttpTool.GET);
			
			} else {	
	
				result = HttpTool.toSring(Conet.TerraceUrl, params,
						HttpTool.POST);
			
			}
			if (result != null) {
				str = ParseData.deciphering(result);//返回解密后的字符串
				
				
			}
		} catch (IOException e) {
//			System.out.println("exception1==="+e.toString().substring(10));
			// TODO Auto-generated catch block
			// e.printStackTrace();
			str = "-1"; // 表示连接错误
		} catch (ClassNotFoundException e) {
//			System.out.println("exception2"+e.toString().substring(10));
			// TODO Auto-generated catch block
			// e.printStackTrace();
			str = "-1"; // 表示连接错误
		}
		return str;
	}

	/**
	 * 返回获取数据是否成功
	 * 
	 * @param str
	 * @return
	 */
	public static int getResult(String str) {
		int result = -1;
		try {
		
			JSONObject obj = new JSONObject(str);
			result = obj.getInt("r");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public static String getResultData(String str) {
		String result = null ;
		try {
		
			JSONObject obj = new JSONObject(str);
			result = obj.getString("data");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public static String getResultBl(String str) {
		String result = null ;
		try {
		
			JSONObject obj = new JSONObject(str);
			result = obj.getString("bl");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public static String getResult_oppo(String str) {
		String result = null ;
		try {
		
			JSONObject obj = new JSONObject(str);
			result = obj.getString("result");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
