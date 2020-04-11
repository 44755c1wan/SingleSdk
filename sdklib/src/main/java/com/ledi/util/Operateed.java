package com.ledi.util;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.huawei.hms.pps.AdChannelInfoClient;
import com.ledi.floatwindow.net.HttpUtilq;

import org.apache.commons.httpclient.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class Operateed {
	static Application activity2;
	public static boolean huaweiflag = false;
	private static Activity activity;
    private static SharedPreferences sp;


	public static Intent startServiceIntent;////1111111111111111111111111111111111111111111

	public static void roleInfo1(Activity context,String serverID, 
			String serverName, 
			String gameRoleName, 
			String gameRoleID, 
			String gameUserLevel, 
			String VipLevel, 
//			String GameBalance, 
//			String GameUserLevel, 
//			String PartyName, 
			String roleCreateTime 
//			String PartyId,
//			String GameRoleGender,
//			String GameRolePower,
//			String PartyRoleId,
//			String PartyRoleName, 
//			String ProfessionId,
//			String Profession,
//			String Friendlist
			){
		activity = context;
		Conetq.ServerID = serverID;
		Conetq.ServerName = serverName;
		Conetq.GameRoleName = gameRoleName;
		Conetq.GameRoleID = gameRoleID;
		Conetq.GameUserLevel = gameUserLevel;
		Conetq.VipLevel = VipLevel;
//		Conetq.GameBalance = GameBalance;
//		Conetq.PartyName = PartyName;
		Conetq.RoleCreateTime = roleCreateTime;
//		Conetq.PartyId = PartyId;
//		Conetq.GameRoleGender = GameRoleGender;
//		Conetq.GameRolePower = GameRolePower;
//		Conetq.PartyRoleId = PartyRoleId;
//		Conetq.PartyRoleName = PartyRoleName;
//		Conetq.ProfessionId = ProfessionId;
//		Conetq.Profession = Profession;
//		Conetq.Friendlist = Friendlist;
		Conetq.phonetell = sp_get("phonetell", "").toString();
		Conetq.imei2 = sp_get("imei", "").toString();
		Conetq.sdkTypes = sp_get("sdkTypes", "").toString();
		Conetq.gameid = sp_get("gid", "").toString();
		Conetq.qId = sp_get("qid", "").toString();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				NameValuePair[] roleInfo_pair = {						
						new NameValuePair("sid", Conetq.ServerID),
						new NameValuePair("sName",UtilOther.gbEncoding(Conetq.ServerName)),
						new NameValuePair("roleName", UtilOther.string2Unicode(Conetq.GameRoleName)),
						new NameValuePair("roleId", Conetq.GameRoleID),
						new NameValuePair("roleLevel", Conetq.GameUserLevel),
						new NameValuePair("vip", Conetq.VipLevel),
//						new NameValuePair("yuanbao", Conetq.GameBalance),
						new NameValuePair("roleLevel", Conetq.GameUserLevel),
//						new NameValuePair("partyName", UtilOther.string2Unicode(Conetq.PartyName )),
						new NameValuePair("roleCreateTime", Conetq.RoleCreateTime),
//						new NameValuePair("partyId", Conetq.PartyId),
//						new NameValuePair("gameRoleGender", UtilOther.string2Unicode(Conetq.GameRoleGender )),
//						new NameValuePair("gameRolePower", Conetq.GameRolePower),
//						new NameValuePair("partyRoleId", Conetq.PartyRoleId),
//						new NameValuePair("partyRoleName", UtilOther.string2Unicode(Conetq.PartyRoleName)),
//						new NameValuePair("professionId", Conetq.ProfessionId),
//						new NameValuePair("profession", UtilOther.string2Unicode(Conetq.Profession)),
//						new NameValuePair("friendlist", Conetq.Friendlist),
						new NameValuePair("imei", Conetq.imei2),
						new NameValuePair("package", Conetq.sdkTypes),
						new NameValuePair("productCode",Conetq.productCode),
						new NameValuePair("ProductKey",Conetq.ProductKey),
                        new NameValuePair("type","3"),
                        new NameValuePair("gid", Conetq.gameid),
                        new NameValuePair("qid", Conetq.qId),
                        new NameValuePair("phonetel",Conetq.phonetell),
                        new NameValuePair("login_uid", Conet.login_uid),//登录后服务器返回的UID，用户唯一标识
				};
				Log.i("roleInfo_pair", roleInfo_pair+"");
				String _roleInfo = HttpUtilq.getData(Conetq.roleInfo, roleInfo_pair);
				
				try {
					JSONObject json = new JSONObject(_roleInfo);//将返回的数据转为json对象
					int status1 = json.getInt("status");	
					if(_roleInfo.equals("") || status1 != 1){//如果返回参数为空或者status1不等于1的，开始for循环请求
						for (int i = 0; i < 7; i++) {//7次请求开始
							
							_roleInfo = HttpUtilq.getData(Conetq.roleInfo, roleInfo_pair);//发起请求获取返回数据
							if(!_roleInfo.equals("")){//判断循环中的请求是否为空，如果不为空
								try {
									JSONObject jsonObject = new JSONObject(_roleInfo);//将返回的数据转为json对象
									int status = jsonObject.getInt("status");//解析status数据
									if(status == 1){//如果数据为1，将i赋值为7，结束循环
										i = 7;
									}
									
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				Log.i("roleInfo", _roleInfo+"上报成功");
			}
		}).start();
	}
	
	
	public static void Payment(Activity context,String serverID, /////////////1111111111111111111111
			String serverName, 
			String gameRoleName, 
			String gameRoleID, 
//			String gameUserLevel,
//			String VipLevel, 
//			String GameBalance, 
//			String PartyName,
			String CpOrderID,
			String GoodsName,
			int Count,
			double Amount,
			String GoodsID,
			String ExtrasParams,
			String oderstatus){
		activity = context;
		Conetq.ServerID = serverID;//服务器ID
		Conetq.ServerName = serverName;//服务器名称
		Conetq.GameRoleName = gameRoleName;//角色名称
		Conetq.GameRoleID = gameRoleID;//角色ID
//		Conetq.GameUserLevel = gameUserLevel;//等级
//		Conetq.VipLevel = VipLevel;//设置当前用户vip等级，必须为整型字符串
//		Conetq.GameBalance = GameBalance;//角色现有金额
//		Conetq.PartyName = PartyName;//设置帮派，公会名称
		Conetq.CpOrderID = CpOrderID;
		Conetq.GoodsName = GoodsName;
		Conetq.Count = Count;
		Conetq.Amount = Amount;
		Conetq.GoodsID = GoodsID;
		Conetq.ExtrasParams = ExtrasParams;
		Conetq.oderstatus = oderstatus;
		Conetq.phonetell = sp_get("phonetell", "").toString();
		Conetq.imei2 = sp_get("imei", "").toString();
		Conetq.sdkTypes = sp_get("sdkTypes", "").toString();
		Conetq.gameid = sp_get("gid", "").toString();
		Conetq.qId = sp_get("qid", "").toString();
		
		//Conetq.phoentell
		new Thread(new Runnable() {
			@Override
			public void run() {
				NameValuePair[] roleInfo_pair = {						
						new NameValuePair("sid", Conetq.ServerID),
						new NameValuePair("sName",UtilOther.gbEncoding(Conetq.ServerName)),
						new NameValuePair("roleName", UtilOther.string2Unicode(Conetq.GameRoleName)),
						new NameValuePair("roleId", Conetq.GameRoleID),
//						new NameValuePair("roleLevel", Conetq.GameUserLevel),
//						new NameValuePair("vip", Conetq.VipLevel),
//						new NameValuePair("yuanbao", Conetq.GameBalance),
//						new NameValuePair("roleLevel", Conetq.GameUserLevel),
//						new NameValuePair("partyName", UtilOther.string2Unicode(Conetq.PartyName )),
						new NameValuePair("roleLevel", Conetq.GameUserLevel),
						new NameValuePair("cpOrderID", Conetq.CpOrderID),
						new NameValuePair("goodsName", UtilOther.string2Unicode(Conetq.GoodsName )),
						new NameValuePair("Count",Conetq.Count+""),
						new NameValuePair("Amount",Conetq.Amount+""),
						new NameValuePair("goodsID", Conetq.GoodsID),
						new NameValuePair("extrasParams", Conetq.ExtrasParams),
						new NameValuePair("imei", Conetq.imei2),
						new NameValuePair("package", Conetq.sdkTypes),
						new NameValuePair("productCode",Conetq.productCode),
						new NameValuePair("productKey",Conetq.ProductKey),
                        new NameValuePair("type","3"),
                        new NameValuePair("gid", Conetq.gameid),
                        new NameValuePair("qid", Conetq.qId),
                        new NameValuePair("login_uid", Conet.login_uid),//登录后服务器返回的UID，用户唯一标识
                        new NameValuePair("phonetel",Conetq.phonetell),
                        new NameValuePair("orderStaus",Conetq.oderstatus),
				};
				Log.i("Payment", roleInfo_pair+"");
				String _roleInfo = HttpUtilq.getData(Conetq.payinfo, roleInfo_pair);
				
				try {
					JSONObject json = new JSONObject(_roleInfo);//将返回的数据转为json对象
					int status1 = json.getInt("status");	
					if(_roleInfo.equals("") || status1 != 1){//如果返回参数为空或者status1不等于1的，开始for循环请求
						for (int i = 0; i < 7; i++) {//7次请求开始
							
							_roleInfo = HttpUtilq.getData(Conetq.payinfo, roleInfo_pair);//发起请求获取返回数据
							if(!_roleInfo.equals("")){//判断循环中的请求是否为空，如果不为空
								try {
									JSONObject jsonObject = new JSONObject(_roleInfo);//将返回的数据转为json对象
									int status = jsonObject.getInt("status");//解析status数据
									if(status == 1){//如果数据为1，将i赋值为7，结束循环
										i = 7;
									}
									
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				Log.i("Payment", _roleInfo+"成功支付");
			}
		}).start();
	}
	
	public static void dologin(///////////////111111111111111111111111 
			Activity context,String UID,String UserName){
		activity = context;
		Conetq.UID = UID;
		Conetq.Username =UserName;
		Conetq.imei2 = sp_get("imei", "").toString();
		Conetq.sdkTypes = sp_get("sdkTypes", "").toString();
		Conetq.gameid = sp_get("gid", "").toString();
		Conetq.qId = sp_get("qid", "").toString();
		
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				NameValuePair[] roleInfo_pair = {						
						new NameValuePair("uID", Conetq.UID ),
						new NameValuePair("username", UtilOther.gbEncoding(Conetq.Username)),
						new NameValuePair("imei", Conetq.imei2),
						new NameValuePair("package", Conetq.sdkTypes),
						new NameValuePair("productCode",Conetq.productCode),
						new NameValuePair("productKey",Conetq.ProductKey),
                        new NameValuePair("type","3"),
                        new NameValuePair("gid", Conetq.gameid),
                        new NameValuePair("qid", Conetq.qId),
						new NameValuePair("hwpps_tracking_id", Conetq.channelInfo),
						new NameValuePair("hwpps_install_timestamp", Conetq.installTimestamp+""),
//                        new NameValuePair("type","3"),
//                        new NameValuePair("gid", Conetq.gameid),
//                        new NameValuePair("qid", Conetq.qId),
				};
				Log.i("CCCCC", "hai");
				Log.i("dologin", roleInfo_pair+""); 
				String _roleInfo = HttpUtilq.getData(Conetq.logininfo, roleInfo_pair);
				Log.i("dologin", _roleInfo+"登录成功");
				if(null != _roleInfo){
					try {
						JSONObject obj = new JSONObject(_roleInfo);
						if(obj.has("status" ) && obj.getInt("status") == 1){
							Conet.login_uid = obj.getString("login_uid");
							Conet.login_username = obj.getString("login_username");
							if(!obj.getString("phone").isEmpty()){
								Conetq.phonetell = obj.getString("phone");
							}
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
//	//这是sdk里面的类，， Context  这个是上下文  因为这个是一个单纯的类，也没有定义上下文，所以，我早这里传了一个上下文  Context context  我测试一下 啥情况
//    public static void getChannelInfo(Context context){
//        try {
//        	AdChannelInfoClient.Info info = AdChannelInfoClient.getAdChannelInfo(context, false);
//        	if (info == null) {
//				Log.i("CCCCK", "kong");
//			}
//            Log.i("CCCCC", "wolaolea");
//            if (info != null){
//            	Log.i("CCCCC", "llllllllll");
//                String channelInfo = info.getChannelInfo(); // 创建广告任务时填入或生成的转化跟踪参数
//                long installTimestamp = info.getInstallTimestamp(); // App安装时间
//                Conetq.channelInfo = channelInfo;
//                Conetq.installTimestamp = installTimestamp;
//                Log.i("CCCCC", channelInfo+":"+installTimestamp+"");
////                Log.i("MainActivity", "getChannelInfo, channelInfo:" + channelInfo + ", installTime:" + installTimestamp);
//                // TODO成功获取转化跟踪参数后，在激活消息中携带该参数发送给广告主分析平台，用于广告主转化归因，一定要确保上报成功。
//            }
//        } catch (Exception e) {
//            Log.i("", "getChannelInfo exception", e);
//        }
//    }
    
    
    
	//这是sdk里面的类，， Context  这个是上下文  因为这个是一个单纯的类，也没有定义上下文，所以，我早这里传了一个上下文  Context context  我测试一下 啥情况
    public static void getChannelInfo(Context context){
    	String huaweippsflag = MetaDataUtil.getMetaDataValue("huaweippsflag", context);
    	Log.i("sjj","奇天乐地"+ huaweippsflag);
    	if(huaweippsflag.equals("false")){
    		huaweiflag = false;
    	}else{
    		huaweiflag = true;
    	}
        try {
        	AdChannelInfoClient.Info info = AdChannelInfoClient.getAdChannelInfo(context, huaweiflag);
        	if (info == null) {
				Log.i("CCCCK", "kong");
			}
            Log.i("CCCCC", "wolaolea");
            if (info != null){
            	Log.i("CCCCC", "llllllllll");
                String channelInfo = info.getChannelInfo(); // 创建广告任务时填入或生成的转化跟踪参数
                long installTimestamp = info.getInstallTimestamp(); // App安装时间
                Conetq.channelInfo = channelInfo;
                Conetq.installTimestamp = installTimestamp;
                Log.i("CCCCC", channelInfo+":"+installTimestamp+"");
                Log.i("CCCC",Conetq.channelInfo+"===="+Conetq.installTimestamp);
//                Log.i("MainActivity", "getChannelInfo, channelInfo:" + channelInfo + ", installTime:" + installTimestamp);
                // TODO成功获取转化跟踪参数后，在激活消息中携带该参数发送给广告主分析平台，用于广告主转化归因，一定要确保上报成功。
            }
        } catch (Exception e) {
            Log.i("", "getChannelInfo exception", e);
        }
    }
    
    public static void sp_put(String k, Object v){
        sp = activity.getSharedPreferences("installtip", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if(v instanceof String){
            edit.putString(k, (String) v);
        }else if(v instanceof Integer){
            edit.putInt(k, (Integer) v);
        }else if(v instanceof Long) {
            edit.putLong(k, (Long) v);
        }else if(v instanceof Boolean) {
            edit.putBoolean(k, (Boolean) v);
        }else if(v instanceof Float) {
            edit.putFloat(k, (Float) v);
        }else {
            return;
        }
        edit.commit();
    }

    public static Object sp_get(String k, Object v){
        sp = activity.getSharedPreferences("installtip", Context.MODE_PRIVATE);
        if(v instanceof String){
            return sp.getString(k, (String) v);
        }else if(v instanceof Integer) {
            return sp.getInt(k, (Integer) v);
        }else if(v instanceof Long) {
            return sp.getLong(k, (Long) v);
        }else if(v instanceof Boolean) {
            return sp.getBoolean(k, (Boolean) v);
        }else if(v instanceof Float) {
            return sp.getFloat(k, (Float) v);
        }
        return null;
    }


}
