package com.ledi.http;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

@SuppressWarnings("deprecation")
public class HttpTool {
	public static final int GET = 0;
	public static final int POST = 1;

	public static String toSring(String uri,
			ArrayList<BasicNameValuePair> params, int method)
			throws IOException {
		String content = null;
		HttpEntity entity = getEntity(uri, params, method);
		if (entity != null)
			content = EntityUtils.toString(entity);
		return content;
	}

	public static byte[] getBytes(String uri,
			ArrayList<BasicNameValuePair> params, int method)
			throws IOException {
		byte[] bytes = null;
		HttpEntity entity = getEntity(uri, params, method);
		if (entity != null)
			bytes = EntityUtils.toByteArray(entity);
		return bytes;
	}

	public static InputStream getStream(String uri,
			ArrayList<BasicNameValuePair> params, int method)
			throws IOException {
		InputStream in = null;
		HttpEntity entity = getEntity(uri, params, method);
		if (entity != null)
			in = entity.getContent();
		return in;
	}

	public static HttpEntity getEntity(String uri,
			ArrayList<BasicNameValuePair> params, int method)
			throws SocketTimeoutException, IOException {
		HttpEntity entity = null;

		HttpClient client = new DefaultHttpClient();

		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		


		HttpUriRequest request = null;
		switch (method) {
		case GET:
			StringBuilder sb = new StringBuilder(uri);
			if (params != null && !params.isEmpty()) {
				sb.append('?');
				for (BasicNameValuePair pair : params) {
					sb.append(pair.getName()).append('=')
							.append(pair.getValue()).append('&');
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			request = new HttpGet(sb.toString());
			break;
		case POST:
			request = new HttpPost(uri);
			((HttpPost) request).setEntity(new UrlEncodedFormEntity(params));
			break;
		}

		HttpResponse response = client.execute(request);
		if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			entity = response.getEntity();
		}
		return entity;
	}
}
