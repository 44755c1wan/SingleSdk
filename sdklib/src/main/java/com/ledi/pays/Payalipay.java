package com.ledi.pays;

import android.app.Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Payalipay {
	private String payInfo = "";

	private static HttpURLConnection connection;
	private static BufferedReader reader;

	public static String sendGetRequest(String urls,
			Map<String, String> params, Activity activity) {
		String line;
		String result = null;
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
	 * @param urlStr
	 * @param postData
	 * @param charSet
	 * @return
	 * @throws Exception
	 */
//	public static String httpPost(String urlStr, String postData,
//			Activity activity) {
//		PrintWriter writer = null;
//		HttpURLConnection httpConn = null;
//		Map<String, String> res = new HashMap<String, String>();
//		try {
//
//			// byte[] data =
//			// MessageUtil.Encrypt(postData.getBytes(charSet));//进行加密
//			byte[] data = postData.getBytes();
//
//			URL url = new URL(urlStr);
//			httpConn = (HttpURLConnection) url.openConnection();
//			httpConn.setRequestMethod("POST");
//			httpConn.setRequestProperty("ContentType",
//					"application/x-www-form-urlencoded");
//			httpConn.setRequestProperty("Content-Length",
//					String.valueOf(data.length));
//			httpConn.setDoInput(true);
//			httpConn.setDoOutput(true);
//
//			httpConn.setConnectTimeout(4000);//
//			httpConn.setReadTimeout(8000);//
//			httpConn.connect();
//
//			OutputStream os = httpConn.getOutputStream();
//			os.write(data);
//			os.flush();
//
//			int responseCode = httpConn.getResponseCode();
//
//			if (HttpURLConnection.HTTP_OK == responseCode) {
//				InputStream is = httpConn.getInputStream();
//				if (is != null) {
//					StringBuilder sb = new StringBuilder();
//					String line;
//					try {
//						BufferedReader reader = new BufferedReader(
//								new InputStreamReader(is, "UTF-8"));
//						while ((line = reader.readLine()) != null) {
//							sb.append(line).append("\n");
//						}
//					} finally {
//						is.close();
//					}
//					is.close();
//					return sb.toString();
//				} else {
//					return "";
//				}
//
//				// res.put("result", MessageUtil.DesEncrypt(is));
//
//			} else {
//				res.put("errno", responseCode + "");
//				res.put("result", "serverResponse->" + responseCode);
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			res.put("errno", "0");
//			res.put("result", ex.toString());
//		} finally {
//			if (null != writer) {
//				writer.close();
//			}
//			if (null != httpConn) {
//				httpConn.disconnect();
//			}
//		}
//		return res.get("result");
//	}
//	public static String httpPost(String urlStr, Map<String, String> params,
//			Activity activity) {
//		PrintWriter writer = null;
//		HttpURLConnection httpConn = null;
//		Map<String, String> res = new HashMap<String, String>();
//		PostMethod postMethod = new PostMethod(urlStr);
//		NameValuePair[] orderInfo = {
//				new NameValuePair("PHPSESSION", "" + SessioniD),
//				new NameValuePair("name", "user_report_list.xml." + SessioniD),
//				new NameValuePair("content", ""),
//				new NameValuePair("aaaa", "submit") };
//		String key,value;
//		for (Map.Entry<String, String> entry : params.entrySet()) {
//            key=entry.getKey();
//            value=entry.getValue();
////			path.append(entry.getKey());
////			path.append("=");
////			path.append(entry.getValue());
////			path.append("&");
//		}
//		try {
//		
//		
//			BasicNameValuePair value = new BasicNameValuePair("i", userInforMation);
//			params.add(value);
//			// byte[] data =
//			// MessageUtil.Encrypt(postData.getBytes(charSet));//进行加密
//			byte[] data = postData.getBytes();
//
//			URL url = new URL(urlStr);
//			httpConn = (HttpURLConnection) url.openConnection();
//			httpConn.setRequestMethod("POST");
//			httpConn.setRequestProperty("ContentType",
//					"application/x-www-form-urlencoded");
//			httpConn.setRequestProperty("Content-Length",
//					String.valueOf(data.length));
//			httpConn.setDoInput(true);
//			httpConn.setDoOutput(true);
//
//			httpConn.setConnectTimeout(4000);//
//			httpConn.setReadTimeout(8000);//
//			httpConn.connect();
//
//			OutputStream os = httpConn.getOutputStream();
//			os.write(data);
//			os.flush();
//
//			int responseCode = httpConn.getResponseCode();
//
//			if (HttpURLConnection.HTTP_OK == responseCode) {
//				InputStream is = httpConn.getInputStream();
//				if (is != null) {
//					StringBuilder sb = new StringBuilder();
//					String line;
//					try {
//						BufferedReader reader = new BufferedReader(
//								new InputStreamReader(is, "UTF-8"));
//						while ((line = reader.readLine()) != null) {
//							sb.append(line).append("\n");
//						}
//					} finally {
//						is.close();
//					}
//					is.close();
//					return sb.toString();
//				} else {
//					return "";
//				}
//
//				// res.put("result", MessageUtil.DesEncrypt(is));
//
//			} else {
//				res.put("errno", responseCode + "");
//				res.put("result", "serverResponse->" + responseCode);
//			}
//		} catch (Exception ex) {
//			ex.printStackTrace();
//			res.put("errno", "0");
//			res.put("result", ex.toString());
//		} finally {
//			if (null != writer) {
//				writer.close();
//			}
//			if (null != httpConn) {
//				httpConn.disconnect();
//			}
//		}
//		return res.get("result");
//	}
	
}