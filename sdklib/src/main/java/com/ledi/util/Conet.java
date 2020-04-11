package com.ledi.util;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.media.MediaPlayer;

import com.ledi.bean.GameInformation;
import com.ledi.bean.User;
import com.ledi.floatwindow.util.Rebate;

import java.util.ArrayList;

public class Conet {
//	public static String APP_ID;
	public static int verify = 4;//由服务端判断是否显示实名认证：1开启,2不开启
	public static int login_way;//腾讯登录方式，1是QQ，2是微信
	public static String strBl;//bl为1：第三方，2:44755
	public static String payBl;//此账号是否4次支付了  0是不到4次，1是大于4次
	public static int userWay = 1; // 0代表传递userName和昵称 , 1代表传递uid
	public static String sdkTypes;//判断是哪种sdk
	public static String yue;
	public static String vip;
	public static String jibie;
	public static String gonghui;
	public static String roleName;
	public static String roleId;
	public static String serverName;
	public static String sid_;
	public static int num;
	public static ArrayList<User> usersData; // 用户集合
	public static String[] formatCheapStr;// 温馨提示
	public static User user;// 当前用户
	public static GameInformation gameInfor;// 游戏信息
	// public static String uid = "12312"; // 玩家id
	public static String uid = "79792"; // 玩家id
	public static String login_uid = "";
	public static String login_username="";
	public static int taskIndex = 0;
	public static String session_id = "3ph30mcea5jkv979s5bo9g2mg1"; // 密钥
	public static String uid_;//自己生成的uid
	public static String session_;
	public static String userName = "shizu"; // 玩家登陆用户名
	public static String nickName = ""; // 玩家昵称
	public static String WallMoney = "0";
	public static String wallet_key = "";// 钱包key值
	public static String platAvailableMoney = "0";// 可用礼券
	public static int rebate = 100;// zhekou
	public static boolean loginback = false;// 默认没登陆
	public static String coupon = "0";// 礼券总额；
	public static String mStatus = "1";// 平台币是否成功
	public static String PhoneinfoVERSION = "";// 手机版本
	public static String showSize = ""; // 屏幕尺寸
	public static String version = ""; // 版本信息
	public static String qid = ""; // 渠道id
	public static String orderIdTencent;//44755后台生成的订单号，针对腾讯
	public static String pf;//tencent
	public static String pf_key;//tencent
	public static int platform;//tencent
	public static String accessToken;//tencent 如果是微信登录传递这个
	public static String payToken;//应用宝支付时候 如果是QQ登录传递这个
	public static String openid;//腾讯登录后得到的openid
	public static String reyunAppid= "";//热云提供appid
	
	public static String imei = ""; // 手机串号
	public static String ip_imei = "117.100.237.236";//获取手机连网ip
	public static String imei2 = "";
	public static String phone = "";// 手机号码
	public static int mReDiscount = 10;// 折扣大小
	public static boolean is_discount; // 是否开启打折显示 1:打折 0:不打折
	/**
	 * 华为
	 */
	public static String playerId;
	public static String playerLecel;
	public static String isAuth;
	public static String ts;
	public static String gameAuthSign;
	//uc的sid
	public static String uc_sid;//uc账号登录后返回的sid
	public static String accountId;//uc登录之后44755验证返回的uc_uid
	//三星
	public static String signValue;
	public static String orderId9;
	public static String waresid9;
	//联想
	public static String tokenLogin;
	//魅族
	public static String mz_uid;
	public static String mz_session;
	public static String mz_name;
	//努比亚
	public static String nby_uid;
	public static String nby_session;
	
	/**
	 * 折扣后的mMoney
	 */
	
	public static int mMoney = 0;


	public static int sid = 1; // 玩家登陆游戏服务器id
	public static String server_name = "";// 玩家登陆游戏区服名称

	public static String extra;// cp方传入的参数，原样传回
	public static String orderID = ""; // 订单号
	public static String detailsName = ""; // 商品名称
	public static String desName = "";// 产品描述
	public static int money = 1; // 交易金额
	public static int card_money = 1;// 卡的金额
	public static String gameMoney = ""; // 游戏金币数量
	public static String cardNo = "0111001406120263593";// 卡号
	public static String cardPasswd = "110240425335796830";// 卡密
	public static String cardMoney = "10";// 选择卡的钱数
	public static String WXH5_url;//微信返回的url
	// * 卡面额组
	// * @param pa7_cardAmt
	// * 卡号组
	// * @param pa8_cardNo

	public static String DirName = "gameinformation";// 在terraceInfor初始化后赋值
	public static StateListDrawable drawable = null;// selector选项
	public static BitmapDrawable bdrawable = null;// selector选项为true

	public static String pathUrl = "";// 下载通知栏地址
	public static String titlename = "";// 下载通知栏名字
	public static String uirInit = "http://www.44755.com/sdk-requests";//初始化热云相关接口
	public static String TerraceUrl5 = "http://api.44755.com/cpgame/init";//////////////////////////////////////
//	public static String TerraceUrl5 = "http://testapi.44755.com/cpgame/init";
	
	public static MediaPlayer player; // 背景音乐 ayer player; // 背景音乐
	public static String TerraceUrl = "http://www.44755.com/sdk-index";//正式
	public static String TerraceUrl2 = "http://www.44755.com/sdk-reglogs";//热云统计登录注册接口
	public static int timer;
	public static String weburl = "";
	public static int webstate;
	
	//填写验证身份证接口
	public static String getrealnameUrl = "http://www.44755.com/user-api_realauth";
	//判断是否认证接口
	public static String getrealnameSure = "http://www.44755.com/user-api_realinfo";
	public static String realName_="";//身份验证 姓名
	public static String realName2_="";//身份证
	// 获取验证码地址-----找回密码
	public static String getIdentifyCode = "http://api.44755.com/ucenter/sendvcode";
	public static String submitNewPassword = "http://api.44755.com/ucenter/resetpwd";// 提交新密码地址
	// 获取验证码地址-------手机号注册
	public static String getIdentifyCodePhone = "http://api.44755.com/mobileres/sendvcode";
	//提交手机号注册新地址
	public static String getRegisterCodePhone = "";
	// QQ登陆需要的QQid,腾讯申请
	public static String QQID = "100364916";
	public static String status_ = "http://api.44755.com/payment/orderStatus";

	public static String YeeUrlBindStr = "http://api.44755.com/yeepayment/payment/bindList";// 请求binds列表
	// 储蓄卡，信用卡正常请求地址
	public static String YeeUrlStr = "http://api.44755.com/yeepayment/payment/wapSubmitOrder";
	public static String WXPayUrlTest = "http://api.44755.com/payment/mpay";// 微信支付正式接口
	public static String WXPayUrlTest2 = "https://api.44755.com/loveiphone/chinese";// 微信支付接口
	public static String AlipayUrlStr = "http://api.44755.com/paymentalipay/mPay";
	public static String platform_payUrlStr = "http://api.44755.com/payment/mPay";//
	public static String platformCardUrl = "http://api.44755.com/wallet/mMoney";//operate
	public static String _url = "http://api.44755.com/walletalipaynew/mMoney";//operate
	public static String roleInfo = "http://api.44755.com/cpgame/role";
	public static String payinfo = "http://api.44755.com/cpgame/mpay";
	public static String roleInfo_sign="123456";


	public static String size;
	public static String giftUrl;
	public static String currency;//道具名称
	public static int exchange;//返回比例
	public static String gid1 = "2001";//cp填写传过来的，不准确
	public static int gid;//真的用到的
	public static String gName = "";//游戏名字
	public static String packageName = "";
	public static String mainActivity = "";
	public static ArrayList<Rebate> mRebateList;
	public static String rebateStr;
	public static String ip = "114.112.88.162";
	public static String bindid = "";
	public static int state = 0;// 没绑定
	public static final String PARTNER = "";
	public static final String SELLER = "";
	public static final String RSA_PRIVATE = "";

	
	public static String phonenumber;// root后的玩家能够得到手机号，用于排序获得手机号

	public static String getP_icon_name = "ledi";
	// TODO Auto-generated method stub

	public static int isFirstIndex = 0;

	public static String gamedata = "";

	public static boolean usePlatAvailable = false;// 是否可用平台币礼券

	public static String moneylist = "";
	public static String baidu_uid;//百度uid
	public static String appid_;//初始化得到appid
	
	
	
	
	//--------------quickSDK角色信息
	public static String ServerID = "";// 服务器ID
	public static String ServerName = "";// 服务器名称
	public static String GameRoleName = "";// 角色名称
	public static String GameRoleID = "";// 角色ID
	public static String GameUserLevel = "";// 等级
	public static String VipLevel = "";//设置当前用户vip等级，必须为整型字符串
	public static String GameBalance = "";// 角色现有金额
	public static String PartyName = "";// 设置帮派，公会名称
	public static String RoleCreateTime = ""; // UC与1881渠道必传，值为10位数时间戳
	public static String PartyId = "";// 360渠道参数，设置帮派id，必须为整型字符串
	public static String GameRoleGender = "";// 360渠道参数
	public static String GameRolePower = "";// 360渠道参数，设置角色战力，必须为整型字符串
	public static String PartyRoleId = ""; // 360渠道参数，设置角色在帮派中的id
	public static String PartyRoleName = "";// 360渠道参数，设置角色在帮派中的名称
	public static String ProfessionId = "";// 360渠道参数，设置角色职业id，必须为整型字符串
	public static String Profession = "";// 360渠道参数，设置角色职业名称
	public static String Friendlist = "";
	public static String CpOrderID = "";
	public static String GoodsName = "";
	public static int Count;
	public static double Amount;
	public static String GoodsID = "";
	public static String ExtrasParams = "";
	public static String UID;
	public static String Username;
	public static String logininfo ="http://www.44755.com/user-api_cplogin";
//	public static String logininfo ="http://www.44755.com/user-api_cplogintest";//测试地址
	
	public static String productCode = "";// 360渠道参数，设置角色职业名称
	public static String ProductKey = "";
	
	public static String hwpps_tracking_id = "";
	
	
	public static String gameid = "";//官网游戏id
	public static String qId = "";//官网渠道id
	
	
	public static String CpOrderId;//开发方订单号
	public static String CpUserInfo;//给网游透传参数
	public static int AmountM;//amount	
	
	
	public static String channelInfo = "";//华为pps转化追踪参数
	public static long installTimestamp = 0;//华为pps检测安装时间
	
	
}
