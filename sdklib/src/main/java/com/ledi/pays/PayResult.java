package com.ledi.pays;

import android.text.TextUtils;

public class PayResult {
	private String resultStatus;
	private String result;
	private String memo;

	public PayResult(String rawResult) {

		if (TextUtils.isEmpty(rawResult))
			return;

		String[] resultParams = rawResult.split(";");
//		System.out.println("---------支付返回参数0："+resultParams.toString());
		for (String resultParam : resultParams) {
//			System.out.println("---------支付返回参数1："+resultParam.toString());
			if (resultParam.startsWith("resultStatus")) {
				resultStatus = gatValue(resultParam, "resultStatus");
//				System.out.println("---------支付返回参数："+resultStatus);
			}
			if (resultParam.startsWith("result")) {
				result = gatValue(resultParam, "result");
			}
			if (resultParam.startsWith("memo")) {
				memo = gatValue(resultParam, "memo");
			}
		}
	}

	@Override
	public String toString() {
//		System.out.println("resultStatus={" + resultStatus + "};memo={" + memo
//				+ "};result={" + result + "}");
		return "resultStatus={" + resultStatus + "};memo={" + memo
				+ "};result={" + result + "}";
	}

	private String gatValue(String content, String key) {
		String prefix = key + "={";
		return content.substring(content.indexOf(prefix) + prefix.length(),
				content.lastIndexOf("}"));
	}

	/**
	 * @return the resultStatus
	 */
	public String getResultStatus() {
		return resultStatus;
	}

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @return the result
	 */
	public String getResult() {
		return result;
	}
}
