package com.ledi.biz;

import com.ledi.bean.GameInformation;
import com.ledi.util.Conet;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;


public class GameInforBiz {
	/**
	 * 解析数据 游戏信息
	 * 
	 * @param json
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public GameInformation getGameInformation(String json) {
		try {
			// Log.e("json","json= "+json);
 			JSONObject obj = new JSONObject(json);
			GameInformation entity = new GameInformation();
			JSONObject objGame = obj.getJSONObject("data");
			entity.setGid(objGame.getInt("id"));
			entity.setGameName(URLDecoder.decode(objGame.getString("name")));
			entity.setCurrency(URLDecoder.decode(objGame.getString("currency")));
//			System.out.println(".......................currency:"+URLDecoder.decode(objGame.getString("currency")));
			entity.setExchange(objGame.getInt("exchange"));
			Conet.currency = URLDecoder.decode(objGame.getString("currency"));
			Conet.exchange = objGame.getInt("exchange");
			entity.setPath(URLDecoder.decode(objGame.getString("path")));
			entity.setIcon(URLDecoder.decode(objGame.getString("icon")));
			entity.setCatName(URLDecoder.decode(objGame.getString("catname")));
			entity.setGame_version(URLDecoder.decode(objGame
					.getString("version")));
			entity.setSdk_version(URLDecoder.decode(objGame.getString("sdk")));
			entity.setVersion_intro(URLDecoder.decode(objGame
					.getString("version_intro")));
			entity.setFilesize(objGame.getInt("filesize"));
			entity.setOnelineMsg(URLDecoder.decode(objGame
					.getString("online_msg")));
			entity.setIsOnline(objGame.getInt("online"));
			entity.setIsUpdate(objGame.getInt("updated"));
			entity.setIsNotice(objGame.getInt("noticed"));
			entity.setNotice(URLDecoder.decode(objGame.getString("notice")));

			String objdiscount=obj.getString("discount");
			entity.setDiscount(objdiscount.toString());
			
			JSONObject objGame_box = obj.getJSONObject("box");
			entity.setStatus(objGame_box.getInt("status"));
			entity.setTitle(objGame_box.getString("title"));
			entity.setUrl(objGame_box.getString("url"));
			
			JSONObject objGame_activity = obj.getJSONObject("activity");
			entity.setGift_status(objGame_activity.getInt("status"));
			entity.setGift_title(objGame_activity.getString("title"));
		    
			JSONObject objGame_share = obj.getJSONObject("share");
			entity.setShareType(objGame_share.getInt("type"));
			entity.setShareText(objGame_share.getString("text"));
			entity.setShareImage(objGame_share.getString("image"));
			
			String objGame_remittance = obj.getString("remittance");
			entity.setCheapTips(objGame_remittance.toString());
//			System.out.println("entity.setcheaptips==="+objGame_remittance.toString());
			JSONObject objBl = obj.getJSONObject("bl");//区分支付黑白包的json数据
//			System.out.println("sdktype..................................."+objBl.getString("sdktype"));
			entity.setBl(objBl.getString("bl"));
//			if (objBl.getString("bl").equals("1")) {
				entity.setAppId_xiaomi(objBl.getString("appid"));
//				Conet.appid_ = objBl.getString("appid");
				entity.setAppKey_xiaomi(objBl.getString("appkey"));
				entity.setAppSecret_xiaomi(objBl.getString("AppSecretKey"));
				entity.setWx_appid(objBl.getString("wx_appid"));
				entity.setWx_appkey(objBl.getString("wx_appkey"));
				entity.setTx_xianwangkey(objBl.getString("pay_appkey"));
				entity.setSdkType(objBl.getString("sdktype"));
//			}
//			System.out.println("entity.setcheaptips==="+objGame_remittance.toString());
			
			if (objGame.equals("")||objGame.equals(null)) {
				Conet.gamedata="";
			}else{
				Conet.gamedata=objGame.toString();
			}
			String is_discount = obj.getString("discount_status");
			
			if("0".equals(is_discount)){
				Conet.is_discount = false;
			}else if("1".equals(is_discount)){
				Conet.is_discount = true;
			}
			JSONObject appmarketObject = obj.optJSONObject("appmarket");
			entity.setAppmarketStatus(appmarketObject.getInt("status"));
			entity.setAppmarket_url(appmarketObject.getString("appmarket_url"));
			entity.setLabel(appmarketObject.getString("label"));
			//点击退出按钮所需要的图片
			entity.setLogout_img(appmarketObject.getString("logout_img"));
			
			//点击图片跳转对应的链接网页
			entity.setLogout_url(appmarketObject.getString("logout_url"));
			//点击图片跳转对应的链接网页判断（1是引入webview，2是下载apk)
			entity.setType(appmarketObject.getInt("type"));
			//获取公告状态及信息
			JSONObject noticeObject = obj.optJSONObject("message");
			entity.setNoticeStatus(noticeObject.getInt("status"));
			entity.setNoticeMsg(noticeObject.getString("msg"));
			return entity;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			 e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取平台相关信息
	 * 
	 * @param json
	 * @return
	 */
//	public GameTerraceInfor getGameTerraceInfor(String json) {
//		try {
//			JSONObject obj = new JSONObject(json);
//			GameTerraceInfor infor = new GameTerraceInfor();
//			JSONObject objGame = obj.getJSONObject("data");
//			infor.setId(objGame.getInt("id"));
//			infor.setPid(objGame.getInt("pid"));
//			infor.setP_app_url(URLDecoder.decode(objGame.getString("p_app_url")));
//			infor.setP_pay_url(URLDecoder.decode(objGame.getString("p_pay_url")));
//			infor.setP_message_url(URLDecoder.decode(objGame
//					.getString("p_message_url")));
//			infor.setP_name(URLDecoder.decode(objGame.getString("p_name")));
//			infor.setP_name_english(URLDecoder.decode(objGame
//					.getString("p_name_english")));
//			infor.setP_icon_name(URLDecoder.decode(objGame
//					.getString("p_icon_name")));
//			infor.setP_kf_qq(URLDecoder.decode(objGame.getString("p_kf_qq")));
//			infor.setP_kf_tel(URLDecoder.decode(objGame.getString("p_kf_tel")));
//			infor.setCreate_time(objGame.getInt("create_time"));
//			infor.setUpdate_time(objGame.getInt("update_time"));
//			infor.setP_message_port(objGame.getInt("p_message_port"));
//			infor.setP_game_g_url(URLDecoder.decode(objGame
//					.getString("p_game_g_url")));
//			infor.setP_game_l_url(URLDecoder.decode(objGame
//					.getString("p_game_l_url")));
//			infor.setP_game_x_url(URLDecoder.decode(objGame
//					.getString("p_game_x_url")));
//			infor.setP_game_h_url(URLDecoder.decode(objGame
//					.getString("p_game_h_url")));
//			infor.setP_game_more_url(URLDecoder.decode(objGame
//					.getString("p_game_more_url")));
//			infor.setP_pic_path(URLDecoder.decode(objGame.getString("icon")));
//			return infor;
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			// e.printStackTrace();
//			return null;
//		}
//	}
}
