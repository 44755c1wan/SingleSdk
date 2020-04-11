package com.ledi.pays;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class HttpUtilsPay {
	public static final int HTTP_GET = 0;
	
	public static final int HTTP_POST = 1;
	
	public static final int HTTP_CONTACT = 2;
	
	public static final int HTTP_UPLOAD = 3;
	
	public static final int HTTP_SIGN_UPLOAD = 4;
	
	/**
	 * 
	 * @param urlStr
	 * @param sendParam 
	 * @param charSet 
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> httpGet(String urlStr, String sendParam, String charSet) {
        PrintWriter writer=null;
        HttpURLConnection httpConn=null;
        Map<String, String> res= new HashMap<String, String>();
        try {
        	
            byte[] data=sendParam.getBytes(charSet);
            URL url=new URL(urlStr);
            httpConn=(HttpURLConnection)url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.setRequestProperty("ContentType", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Content-Length", String.valueOf(data.length));
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
 
            httpConn.setConnectTimeout(30000);//
            httpConn.setReadTimeout(30000);// 
            httpConn.connect();
            OutputStream os=httpConn.getOutputStream();
            os.write(data);
            os.flush();
        	           
            // 閿熸枻鎷烽敓鏂ゆ嫹閿熸帴蠄鍒�
            int responseCode=httpConn.getResponseCode();
            if(HttpURLConnection.HTTP_OK == responseCode) {
                byte[] buffer=new byte[2048];
                int len=-1;
                InputStream is=httpConn.getInputStream();
                ByteArrayOutputStream bos=new ByteArrayOutputStream();
                while((len=is.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }
                res.put("errno", "1");
                //res.put("result", bos.toString(charSet));
//                res.put("result", MessageUtil.DesEncrypt(is));
                is.close();
            } else {
            	res.put("errno", httpConn.getResponseCode()+"");
            	res.put("result","");
            }
        } catch(Exception ex) {
        	ex.printStackTrace();
        	res.put("errno", "0");
            res.put("result", ex.toString());
            //System.err.println(urlStr);
        } finally {
            if(null != writer) {
                writer.close();
            }
            if(null != httpConn) {
                httpConn.disconnect();
            }
        }
        return res;
    }
	
	/**
	 * @param urlStr
	 * @param postData 
	 * @param charSet
	 * @return 
	 * @throws Exception
	 */
    public static Map<String, String> httpPost(String urlStr, String postData, String charSet) {
        PrintWriter writer=null;
        HttpURLConnection httpConn=null;
        Map<String, String> res= new HashMap<String, String>();
        try {
            
//            byte[] data = MessageUtil.Encrypt(postData.getBytes(charSet));//进行加密
            byte[] data = postData.getBytes();
            
            URL url=new URL(urlStr);
            httpConn=(HttpURLConnection)url.openConnection();
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("ContentType", "application/x-www-form-urlencoded");
            httpConn.setRequestProperty("Content-Length", String.valueOf(data.length));
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            
            httpConn.setConnectTimeout(4000);// 
            httpConn.setReadTimeout(8000);//
            httpConn.connect();
            
            OutputStream os=httpConn.getOutputStream();
            os.write(data);
            os.flush();
           
            int responseCode=httpConn.getResponseCode();
            if(HttpURLConnection.HTTP_OK == responseCode) {
                InputStream is=httpConn.getInputStream();
                res.put("errno", "1");
                
//                res.put("result", MessageUtil.DesEncrypt(is));
                is.close();
                
            } else {
            	res.put("errno",responseCode+"");
                res.put("result","serverResponse->"+responseCode);            
            }
        } catch(Exception ex) {
            ex.printStackTrace();
            res.put("errno","0");
            res.put("result", ex.toString()); 
        } finally {
            if(null != writer) {
                writer.close();
            }
            if(null != httpConn) {
                httpConn.disconnect();
            }
        }
        return res;
    }
}
