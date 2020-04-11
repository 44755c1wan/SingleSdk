package com.ledi.bean;

import java.io.Serializable;

/**
 * 游戏信息
 * com.ledi.bean.GameInformation
 * @author bigNewBie
 * create at 2015-9-10 上午9:51:22
 */
public class GameInformation implements Serializable {

	private static final long serialVersionUID = -2978557638852288754L;
	private int gid; // 游戏id
	private String gameName; // 游戏的名称
	private String currency; // 人民币兑换币种名称
	private int exchange; // 人民币兑换币种比例
	private String icon; // 游戏图标
	private String path; // apk下载路径
	private String catName; // 游戏类型
	private int filesize; // 文件大小
	private String game_version; // 游戏版本信息
	private String sdk_version; // sdk版本信息
	private String version_intro; // 版本说明信息
	private String has_pay_rate; // 有无充值方式兑换比例
	private String onelineMsg; // 维护时,消息机制
	private int isOnline; // 判断维护状态. 1 表示上线，0表示下线
	private int isUpdate;// 是否更新apk程序 0:不更新 1:不强制更新 2:强制更新
	private String notice;// 消息通知字符串
	private int isNotice;// 是否有消息通知 ...1表示开启公告，为0表示关闭公告
	private int status;// 是否开始盒子
	private String title;// 显示盒子的显示内容
	private String url;// 显示url，去下载的地址
	private int gift_status;// 登录送钱是否显示
	private String gift_title;// 登录送钱显示的内容
	private String cheapTips;// 温馨提示内容
	private String discount;//额度大小
	private int appmarketStatus; //点击更多游戏时加载的网页 0：默认，即本地 1：加载获取的url
	private String label;//更多游戏顶部标识
	private String appmarket_url;//需要加载的url，只有appmarketStatus为1时才会用到
	private int noticeStatus;//是否有公告  0：不显示公告  1：显示公告
	private String noticeMsg;//公告内容
	private String logout_img;//点击退出按钮得到的图片
	private String logout_url;//活动图片的链接
	private int type;//判断是那种类型的图片（1是引入webview，2是下载apk)
	
	private String sdkType;// sdk类型 yyb : 应用宝, xm: 小米
	private String bl;// 开启哪种充值方式 1：渠道, 2：44755的充值方式
	//通用的id,key,秘钥
	private String appId_xiaomi;
	private String appKey_xiaomi;
	private String AppSecret_xiaomi;
	private String wx_appid;
	private String wx_appkey;
	private String tx_xianwangkey;
	public String getTx_xianwangkey() {
		return tx_xianwangkey;
	}

	public void setTx_xianwangkey(String tx_xianwangkey) {
		this.tx_xianwangkey = tx_xianwangkey;
	}

	public String getWx_appid() {
		return wx_appid;
	}

	public void setWx_appid(String wx_appid) {
		this.wx_appid = wx_appid;
	}

	public String getWx_appkey() {
		return wx_appkey;
	}

	public void setWx_appkey(String wx_appkey) {
		this.wx_appkey = wx_appkey;
	}

	public String getSdkType() {
		return sdkType;
	}

	public void setSdkType(String sdkType) {
		this.sdkType = sdkType;
	}

	public String getBl() {
		return bl;
	}

	public void setBl(String bl) {
		this.bl = bl;
	}

	public String getAppId_xiaomi() {
		return appId_xiaomi;
	}

	public void setAppId_xiaomi(String appId_xiaomi) {
		this.appId_xiaomi = appId_xiaomi;
	}

	public String getAppKey_xiaomi() {
		return appKey_xiaomi;
	}

	public void setAppKey_xiaomi(String appKey_xiaomi) {
		this.appKey_xiaomi = appKey_xiaomi;
	}

	public String getAppSecret_xiaomi() {
		return AppSecret_xiaomi;
	}

	public void setAppSecret_xiaomi(String appSecret_xiaomi) {
		AppSecret_xiaomi = appSecret_xiaomi;
	}

	public int getmShareType() {
		return mShareType;
	}

	public void setmShareType(int mShareType) {
		this.mShareType = mShareType;
	}

	public String getmShareText() {
		return mShareText;
	}

	public void setmShareText(String mShareText) {
		this.mShareText = mShareText;
	}

	public String getmShareImage() {
		return mShareImage;
	}

	public void setmShareImage(String mShareImage) {
		this.mShareImage = mShareImage;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getLogout_url() {
		return logout_url;
	}

	public void setLogout_url(String logout_url) {
		this.logout_url = logout_url;
	}

	public String getLogout_img() {
		return logout_img;
	}

	public void setLogout_img(String logout_img) {
		this.logout_img = logout_img;
	}

	public int getNoticeStatus() {
		return noticeStatus;
	}

	public void setNoticeStatus(int noticeStatus) {
		this.noticeStatus = noticeStatus;
	}

	public String getNoticeMsg() {
		return noticeMsg;
	}

	public void setNoticeMsg(String noticeMsg) {
		this.noticeMsg = noticeMsg;
	}

	public int getAppmarketStatus() {
		return appmarketStatus;
	}

	public void setAppmarketStatus(int appmarketStatus) {
		this.appmarketStatus = appmarketStatus;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getAppmarket_url() {
		return appmarket_url;
	}

	public void setAppmarket_url(String appmarket_url) {
		this.appmarket_url = appmarket_url;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	/**
	 * 1 是文字 2是图片 3是兼有 
	 */
	private int mShareType=0;
	private String mShareText, mShareImage;

	public String getCheapTips() {
		return cheapTips;
	}

	public void setCheapTips(String cheapTips) {
		this.cheapTips = cheapTips;
	}

	public int getGift_status() {
		return gift_status;
	}

	public void setGift_status(int gift_status) {
		this.gift_status = gift_status;
	}

	public String getGift_title() {
		return gift_title;
	}

	public void setGift_title(String gift_title) {
		this.gift_title = gift_title;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public int getIsNotice() {
		return isNotice;
	}

	public void setIsNotice(int isNotice) {
		this.isNotice = isNotice;
	}

	public int getIsUpdate() {
		return isUpdate;
	}

	public void setIsUpdate(int isUpdate) {
		this.isUpdate = isUpdate;
	}

	public String getOnelineMsg() {
		return onelineMsg;
	}

	public void setOnelineMsg(String onelineMsg) {
		this.onelineMsg = onelineMsg;
	}

	public int getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(int isOnline) {
		this.isOnline = isOnline;
	}

	public String getHas_pay_rate() {
		return has_pay_rate;
	}

	public void setHas_pay_rate(String has_pay_rate) {
		this.has_pay_rate = has_pay_rate;
	}

	public String getCatName() {
		return catName;
	}

	public void setCatName(String catName) {
		this.catName = catName;
	}

	public int getFilesize() {
		return filesize;
	}

	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}

	public String getGame_version() {
		return game_version;
	}

	public void setGame_version(String game_version) {
		this.game_version = game_version;
	}

	public String getSdk_version() {
		return sdk_version;
	}

	public void setSdk_version(String sdk_version) {
		this.sdk_version = sdk_version;
	}

	public String getVersion_intro() {
		return version_intro;
	}

	public void setVersion_intro(String version_intro) {
		this.version_intro = version_intro;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getGid() {
		return gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public int getExchange() {
		return exchange;
	}

	public void setExchange(int exchange) {
		this.exchange = exchange;
	}

	public String getShareText() {
		return mShareText;
	}

	public void setShareText(String mShareText) {
		this.mShareText = mShareText;
	}

	public int getShareType() {
		return mShareType;
	}

	public void setShareType(int mShareType) {
		this.mShareType = mShareType;
	}

	public String getShareImage() {
		return mShareImage;
	}

	public void setShareImage(String mShareImage) {
		this.mShareImage = mShareImage;
	}

}
