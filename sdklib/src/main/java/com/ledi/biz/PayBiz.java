package com.ledi.biz;

import com.ledi.bean.GameInformation;
import com.ledi.bean.GameServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.util.ArrayList;


public class PayBiz {

	/**
	 * 解析数据 游戏列表信息
	 * 
	 * @param json
	 * @return
	 */
	public ArrayList<GameInformation> getGameList(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			// JSONObject objData = obj.getJSONObject("data");
			// int total = objData.getInt("total"); // 游戏列表总数
			ArrayList<GameInformation> entities = new ArrayList<GameInformation>();
			GameInformation entity = null;
			JSONObject objGame = null;
			JSONArray arr = obj.getJSONArray("list");
			for (int i = 0; i < arr.length(); i++) {
				objGame = arr.getJSONObject(i);
				entity = new GameInformation();
				entity.setGid(objGame.getInt("id"));
				entity.setGameName(URLDecoder.decode(objGame.getString("name")));
				entity.setCurrency(URLDecoder.decode(objGame
						.getString("currency")));
				entity.setExchange(objGame.getInt("exchange"));
				entity.setIcon(URLDecoder.decode(objGame.getString("icon")));
				entity.setHas_pay_rate(URLDecoder.decode(objGame
						.getString("has_pay_rate")));
				entities.add(entity);
			}
			return entities;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return null;
		}
	}

	/**
	 * 解析数据 游戏区服列表信息
	 * 
	 * @param json
	 * @return
	 */
	public ArrayList<GameServer> getGameServeList(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			// JSONObject objData = obj.getJSONObject("data");
			// int total = objData.getInt("total"); // 游戏区服列表总数
			ArrayList<GameServer> entities = new ArrayList<GameServer>();
			GameServer entity = null;
			JSONObject objGame = null;
			JSONArray arr = obj.getJSONArray("list");
			for (int i = 0; i < arr.length(); i++) {
				objGame = arr.getJSONObject(i);
				entity = new GameServer();
				entity.setCode(objGame.getInt("code"));
				entity.setName(URLDecoder.decode(objGame.getString("name")));
				entity.setSid(objGame.getInt("sid"));
				entities.add(entity);
			}
			return entities;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return null;
		}
	}

}
