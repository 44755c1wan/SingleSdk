package com.ledi.pays;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Post {
	public static HttpClient _client = new HttpClient();


	public static String getData(String url, NameValuePair[] list) {
		String result = "";
		String line = "";
		PostMethod postMethod;
		//网络延迟
//		_client.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
		try {
			postMethod = new PostMethod(url);
			postMethod.setRequestBody(list);

			_client.executeMethod(postMethod);

		} catch (Exception e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		int code = 0;
		if (null != postMethod) {
			code = postMethod.getStatusCode();
			if (code == 200) {
				try {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(
									postMethod.getResponseBodyAsStream(),
									"utf-8"));
					while (null != (line = br.readLine())) {
						result += line;
//						System.out.println("vivo200:"+result);
					}
					return result;
					// result = postMethod.getResponseBodyAsString();
					// System.out.println("result====" + code);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "";
				}
			} else {
//				System.out.println("vivo不是200:"+code);
				return "";
			}
		}

		return result;
	}

}
