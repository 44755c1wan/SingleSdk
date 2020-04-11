package com.ledi.biz;

import android.os.Environment;

import com.ledi.bean.LoadData;
import com.ledi.util.Conet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;

public class UtilBiz {
	/**
	 * 解析数据 游戏更新包数据
	 * 
	 * @param json
	 * @return
	 */
	public LoadData getLoadData(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			JSONObject objData = obj.getJSONObject("data");
			LoadData data = new LoadData();
			data.setGame_id(objData.getInt("game_id"));
			data.setIsUpdate(objData.getInt("updated"));
			data.setScreen(URLDecoder.decode(objData.getString("screen")));
			data.setPath(URLDecoder.decode(objData.getString("path")));
			data.setCreate_time(objData.getInt("create_time"));
			data.setVersion(URLDecoder.decode(objData.getString("version")));
			return data;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return null;
		}
	}

	/**
	 * 下载数据包
	 */
	public int DownLoadData(String urlStr, String fileName) {
		int result = -1;
		OutputStream output = null;
		try {
			URL url = new URL(urlStr);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			String SDCard = Environment.getExternalStorageDirectory() + "";
			String dirPath = SDCard + "/" + Conet.DirName;// 文件夹路径
			String filePath = dirPath + "/" + fileName;
			File dir = new File(dirPath);
			if (!dir.exists()) {
				dir.mkdir();// 新建文件夹
			}
			File file = new File(filePath);
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();// 新建文件
			InputStream input = conn.getInputStream();
			output = new FileOutputStream(file);
			// 读取文件
			byte[] buffer = new byte[1024];
			int len;
			while ((len = input.read(buffer)) != -1) {
				output.write(buffer, 0, len);
			}
			output.flush();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
					result = 0;
				} else {
					result = 1;
				}
			} catch (IOException e) {
				// e.printStackTrace();
				result = 1;
			}
		}
		return result;
	}
}
