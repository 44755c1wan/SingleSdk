package com.ledi.util;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;

public class ParseData {
	/**
	 * 字符串加密处
	 * 
	 * @param data
	 * @return
	 */
	public static String encryption(String data) throws IOException {
		@SuppressWarnings("deprecation")
		String encodeStr = URLEncoder.encode(data);
		String baseStr = Base64.encodeBytes(encodeStr.getBytes());
		String endStr = MD5(baseStr).substring(0, 8);
		return endStr + baseStr;
	}

	/**
	 * 字符串解密处
	 * 
	 * @param data
	 * @return
	 */
	public static String deciphering(String data)
			throws ClassNotFoundException, IOException {
		String firstStr = data.substring(8);
		String baseStr = new String(Base64.decode(firstStr));
		return URLDecoder.decode(baseStr);
	}

	// MD5加密
	public static String MD5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	// 可�?的加密算�?
	// public static String encryptmd5(String str) {
	// char[] a = str.toCharArray();
	// for (int i = 0; i < a.length; i++) {
	// a[i] = (char) (a[i] ^ 'l');
	// }
	// String s = new String(a);
	// return s;
	// }

}
