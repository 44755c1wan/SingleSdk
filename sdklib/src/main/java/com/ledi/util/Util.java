package com.ledi.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.ledi.bean.User;
import com.ledi.biz.FatherBiz;
import com.ledi.biz.UserDao;
import com.permission.Action;
import com.permission.AndPermission;
import com.permission.runtime.Permission;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URLDecoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
    // 一键注册截图用
    static SimpleDateFormat df = new SimpleDateFormat("hh_mm_ss");// 日期格式
    static Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
    static String str = df.format(curDate);// 获取时间用于显示截图时间

    public static final String TAG = "LOG";

    public static int getWidth(Activity content) {
        DisplayMetrics dm = new DisplayMetrics();
        content.getWindowManager().getDefaultDisplay().getMetrics(dm);

        int screenWidth = dm.widthPixels;
        return screenWidth;
    }

    public static int getHeight(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);

        int screenHeight = dm.heightPixels;
        return screenHeight;
    }

    /**
     * 初始化信息 ,获取imei号 ,获取版本号 ,获取渠道id ,发送imei,渠道id信息 ,播放背景音乐,下载海报数据包,下载背景音乐
     */
    public static void init(SharedPreferences pfData, Context context) {
        Conet.imei = pfData.getString("imei", "");

        if (TextUtils.isEmpty(Conet.imei)) {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            Conet.imei = getImei(context);
            // Conet.imei=tm.getLine1Number();
        }
        Conet.version = Util.getXmlData(context, "Version"); // 获取版本号
        Util.initPhoneNumber(pfData, context);
        Conet.ip = Util.getid(context);
        Util.getShowSize((Activity) context); // 获取屏幕分辨率
        // Util.getBitMap(context); // 获取图标图片
        // 向服务器发送渠道信息和imeis信息

        new Thread() {
            public void run() {
                int result = 0;
                HashMap<String, String> data = new HashMap<String, String>();
                data.put("g", "open");
                data.put("gid", Conet.gid + "");
                data.put("qid", Conet.qid);
                data.put("imei", Conet.imei);
                data.put("screen", Conet.showSize);
                try {
                    String str = Util.SplitJointStr(data);
                    String strResult = FatherBiz.getString(str, false);
                    result = FatherBiz.getResult(strResult);
                    // if (result==1) {
                    //
                    // }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            ;
        }.start();

        // startPlayer(context); // 播放背景音乐
        // ISMusic((Activity) context, preferences);// 下载背景音乐
        // 下载海报更新数据包
        // ISLoad((Activity) context, pfData);
        // 启动消息服务Service
        // context.startService(new Intent(context, MyMessageService.class));
        // 启动广告服务
//		context.startService(new Intent(context, NotificationService.class));
    }

    /**
     * 检查网络
     */
    public static boolean checkInter(Context context, boolean isWiFi) {
        ConnectivityManager con = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = con.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isConnected()) {
            Toast.makeText(context, "无网络连接,请检查网络..", Toast.LENGTH_LONG).show();
            return true;
        }
        if (isWiFi) {
            boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .isConnectedOrConnecting();
            if (!wifi) { // 提示使用wifi
                Toast.makeText(context, "建议您使用WiFi以减少流量！", Toast.LENGTH_LONG)
                        .show();
            }
        }
        return false;
    }

    /**
     * 启动设置网络界面
     */
    public static void startWifiSetting(final Activity activity) {
        Builder b = new Builder(activity).setTitle("网络设置提示")
                .setMessage("网络连接不可用,是否进行设置？");
        b.setPositiveButton("设置", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent intent = null;
                // 判断手机系统的版本 即API大于10 就是3.0或以上版本
                if (Build.VERSION.SDK_INT > 10) {
                    intent = new Intent(
                            Settings.ACTION_WIRELESS_SETTINGS);
                } else {
                    intent = new Intent();
                    ComponentName component = new ComponentName(
                            "com.android.settings",
                            "com.android.settings.WirelessSettings");
                    intent.setComponent(component);
                    intent.setAction("android.intent.action.VIEW");
                }
                activity.startActivity(intent);
                dialog.cancel();
                activity.finish();
            }
        }).setNeutralButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
                activity.finish();
            }
        }).show();
    }

    /**
     * 获取密钥和uid,并 保存用户名密码
     *
     * @param str
     */
    public static void saveUserInfor(String str) {
        try {
            JSONObject obj = new JSONObject(str);
            Conet.session_id = URLDecoder.decode(obj.getString("session_id"));
            Conet.uid = URLDecoder.decode(obj.getString("uid"));

            if (Conet.userWay == 0) {
                Conet.nickName = URLDecoder.decode(obj.getString("nickname"));
            }

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 获取所有用户数据
     *
     * @param context
     */
    public static void GetUsersData(Context context) {
        UserDao userDao = new UserDao(context);
        Conet.usersData = userDao.getUsers();
    }

    /**
     * 更新用户登录状态
     *
     * @param context
     */
    public static boolean updataUserLastTime(Context context, UserDao userDao,
                                             String userName, int state) {
        boolean isExist = false;
        User user = null;
        for (int i = 0; i < Conet.usersData.size(); i++) {
            user = Conet.usersData.get(i);
            if (userName.equals(user.getUsername())) {
                Conet.user = user;
                user.setIsLastTime(1);
                user.setState(state);
                isExist = true;
            } else {
                user.setIsLastTime(0);
            }
            userDao.update(user);
        }
        GetUsersData(context);
        return isExist;
    }

    /**
     * 得到是否有bindid
     *
     * @param context
     */
    public static boolean GetUserbindid(Context context, UserDao userDao,
                                        String userName) {
        boolean isExist = false;
        User user = null;
        for (int i = 0; i < Conet.usersData.size(); i++) {
            user = Conet.usersData.get(i);
            if (userName.equals(user.getUsername())) {
                Conet.user = user;
                if (null != Conet.user.getBindid()) {
                    isExist = true;
                }

            }
        }

        return isExist;
    }

    /**
     * 更新用户登录状态
     *
     * @param userDao
     * @param userName
     * @param state
     */
    public static void updataUserState(UserDao userDao, String userName,
                                       int state) {
        User user = null;
        for (int i = 0; i < Conet.usersData.size(); i++) {
            user = Conet.usersData.get(i);
            if (userName.equals(user.getUsername())) {
                user.setState(state);
                userDao.update(user);
            }
        }
    }

    /**
     * 修改密码
     *
     * @param userDao
     * @param userName
     * @param password
     */
    public static void updataUserPassword(UserDao userDao, String userName,
                                          String password) {
        User user = null;
        for (int i = 0; i < Conet.usersData.size(); i++) {
            user = Conet.usersData.get(i);
            if (userName.equals(user.getUsername())) {
                user.setPassword(password);
                userDao.update(user);
            }
        }
    }

    /**
     * 修改bindid
     *
     * @param userDao
     * @param userName
     * @param bindid
     * @param paytime
     */
    public static void updataUserBindid(UserDao userDao, String userName,
                                        String bindid, int paytime) {
        User user = null;
        for (int i = 0; i < Conet.usersData.size(); i++) {
            user = Conet.usersData.get(i);
            if (userName.equals(user.getUsername())) {
                user.setBindid(bindid);
                user.setIspaytime(paytime);
                userDao.update(user);
            }
        }
    }

    /**
     * 保存用户名密码
     *
     * @param user
     * @param context
     */
    public static void saveUserInfor(User user, Context context) {
        UserDao userDao = new UserDao(context);

        userDao.insert(user);
        Conet.user = user;
    }

    /**
     * 拼接字符串,用于发送至服务器 返回加密后的字符串数据
     *
     * @param data
     * @return
     * @throws IOException
     */
    public static String SplitJointStr(HashMap<String, String> data)
            throws IOException {
        JSONObject jsonObject = new JSONObject();
        try {
            for (String m : data.keySet()) {
                jsonObject.put(m, data.get(m));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ParseData.encryption(jsonObject.toString().trim());
    }

    /**
     * 读取res/xml/tuiguang_id.xml 中的TuiguangID的你内容，并返回对应的id字符串
     *
     * @param context
     * @return
     */
    public static String getXmlData(Context context, String name) {
        XmlResourceParser xrp = context.getResources().getXml(
                Util.getResID(context, "ledi_tuiguang_id", "xml"));

        boolean flag = false;
        try {
            while (xrp.getEventType() != XmlPullParser.END_DOCUMENT) {
                // 到达title节点时标记一下
                if (xrp.getEventType() == XmlPullParser.START_TAG) {
                    if (xrp.getName().equals(name)) {
                        flag = true;
                    }
                }
                // 如过到达标记的节点则取出内容
                if (xrp.getEventType() == XmlPullParser.TEXT) {
                    if (flag) {
                        flag = false;
                        return xrp.getText();
                    }
                }
                xrp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getXmlData(Context context, String name, int i) {
        ApplicationInfo appInfo = null;
        try {
            appInfo = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
        } catch (NameNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        Object msg = appInfo.metaData.get("TuiguangID");
        if (null == msg) {
            msg = 0;
        }
        return msg.toString();
    }

    /**
     * 获取手机串号--登录界面出现的时候
     *
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getImei(Context context) {
//		Conet.imei = pfData.getString("imei", "");
//
//		if (Conet.imei.equals("")) {
//			TelephonyManager tm = (TelephonyManager) context
//					.getSystemService(Context.TELEPHONY_SERVICE);
//			Conet.imei = tm.getDeviceId();
//			// Conet.imei=tm.getLine1Number();
//		}
        if (!TextUtils.isEmpty(Conet.imei)) {
            return Conet.imei;
        }

        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        if (AndPermission.hasPermissions(context, new String[]{
                Manifest.permission.READ_PHONE_STATE
                , Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_NUMBERS
        })) {
            Conet.imei = tm.getDeviceId();
            Conet.imei = tm.getLine1Number();
            return Conet.imei;
        }


        String serial;
        String m_szDevIDShort = "35" +
                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +

                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +

                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +

                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +

                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +

                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +

                Build.USER.length() % 10; //13 位

        try {
            serial = Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
            return MD5.getMD5(new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString());
        } catch (Exception exception) {
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        Conet.imei = MD5.getMD5(new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString());
        return Conet.imei;
    }

    /**
     * 获取屏幕尺寸
     *
     * @param activity
     */
    public static void getShowSize(Activity activity) {
        // DisplayMetrics 一个描述普通显示信息的结构，例如显示大小、密度、字体尺寸
        // 获取手机分辨率
        DisplayMetrics displaysMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay()
                .getMetrics(displaysMetrics);
        Conet.showSize = displaysMetrics.widthPixels + "x"
                + displaysMetrics.heightPixels;
    }

    /**
     * 检测版本更新
     */
    // public static void detectionVersion(Context context) {
    // GameInformation gameIn = Conet.gameInfor;
    // if (gameIn != null) {
    // if (!gameIn.getSdk_version().equals(Conet.version)
    // && !gameIn.getPath().equals("")
    // && gameIn.getIsUpdate() != 0) {
    // Intent intent = new Intent(context, DownLoadActivity.class);
    // intent.putExtra("path", gameIn.getPath());
    // intent.putExtra("content", gameIn.getVersion_intro());
    // context.startActivity(intent);
    // }
    // }
    // }

    /**
     * 下载海报数据包整体模块
     */
    // public static void ISLoad(final Activity activity,
    // final SharedPreferences pfData) {
    // if (checkInter(activity, false)) {
    // return;
    // }
    // // 发送请求,查看是否有更新数据
    // new Thread() {
    // public void run() {
    // int result = -1;
    // HashMap<String, String> data = new HashMap<String, String>();
    // data.put("g", "res");
    // data.put("gid", Game.gid);
    // data.put("screen", Conet.showSize);
    // try {
    // String str = Util.SplitJointStr(data);
    // String strResult = FatherBiz.getString(str, false);
    // result = FatherBiz.getResult(strResult);
    // if (0 == result) {
    // UtilBiz biz = new UtilBiz();
    // LoadData loadData = biz.getLoadData(strResult);
    // if (loadData != null) {
    // // 获取上次更新时间
    // int oldTime = Integer.parseInt(pfData.getString(
    // "create_time", "0"));
    // // 判断,如果有更新. 下载更新包
    //
    // if (oldTime < loadData.getCreate_time()
    // && loadData.getIsUpdate() != 0) {
    //
    // int num = biz.DownLoadData(loadData.getPath(),
    // "load_data.zip");
    // if (num == 0) {
    // String SDCard = Environment
    // .getExternalStorageDirectory() + "";
    // String dirPath = SDCard + "/"
    // + Conet.DirName + "/"
    // + "load_data.zip";//
    // // 文件夹路径
    // String filePath = "/data/data/"
    // + activity.getPackageName()
    // + "/skin/";
    // try {
    // Util.UnZipFolder(dirPath, filePath);
    // Editor editor = pfData.edit();
    // editor.putString("create_time",
    // loadData.getCreate_time() + "");
    // editor.commit();
    // } catch (Exception e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
    // }
    // }
    // }
    // } catch (IOException e1) {
    // // TODO Auto-generated catch block
    // e1.printStackTrace();
    // }
    // };
    // }.start();
    // }

    // /**
    // * 设置背景图片
    // */
    // public static void setBG(int index, LinearLayout layout, Context context,
    // LinearLayout second_layout) {
    // String path = "";
    // switch (index) {
    // case 0:
    // path = "/data/data/" + context.getPackageName()
    // + "/skin/landscape/0.jpg";
    // break;
    // case 1:
    // if (Conet.userWay == 0) {
    // if (second_layout != null) {
    // second_layout
    // .setBackgroundResource(R.drawable.landing_layout_bg);
    // }
    // }
    // path = "/data/data/" + context.getPackageName()
    // + "/skin/portrait/0.jpg";
    // break;
    // }
    // File file = new File(path);
    // if (file.exists()) {
    // Bitmap bmp = BitmapFactory.decodeFile(path);
    // BitmapDrawable bd = new BitmapDrawable(bmp);
    // layout.setBackgroundDrawable(bd);
    // }
    // }

    /**
     * 设置首页海报动画
     *
     * @param index
     * @param iv
     * @param context
     * @return
     */
    public static AnimationDrawable setBG(int index, ImageView iv,
                                          Context context) {
        AnimationDrawable anim = (AnimationDrawable) iv.getBackground();
        String path = "";
        switch (index) {
            case 0:
                // 横屏
                path = "/data/data/" + context.getPackageName() + "/skin/landscape";
                break;
            case 1:
                // 竖屏
                path = "/data/data/" + context.getPackageName() + "/skin/portrait";
                break;
        }
        File dir = new File(path);
        File[] files = dir.listFiles();
        if (files == null) {
            return anim;
        }
        if (dir.exists() || files.length != 0) {
            anim = new AnimationDrawable();
            anim.setOneShot(false);
            Drawable db = null;
            for (int i = 0; i < files.length; i++) {
                db = Drawable.createFromPath(files[i].getPath());
                anim.addFrame(db, 300);
            }
            iv.setBackgroundDrawable(anim);
        }
        return anim;
    }

    /**
     * 验证 用户名 是否符合格式 用户名:字母开头 / 4-20位
     */
    public static boolean checkUserName(String userName) {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9_]{4,20}");
        Matcher matcher = pattern.matcher(userName);
        return matcher.matches();
    }

    //验证姓名2-10个汉字
    public static boolean checkUserRealName(String userName) {
        Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5]{2,10}$");
//		^[\u4e00-\u9fa5],{0,}$
        Matcher matcher = pattern.matcher(userName);
        return matcher.matches();
    }

    //验证身份证
    public static boolean checkUserRealName2(String userName) {
//		Pattern pattern = java.util.regex.Pattern.compile("^\\d{15}|^\\d{17}([0-9]|X|x)$");
        Pattern pattern = Pattern.compile(
                "^[1-9][0-9]{5}(19[0-9]{2}|200[0-9]|2010)(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[0-9]{3}[0-9xX]$");

//		Pattern pattern = java.util.regex.Pattern.compile("^\\d{18}$)|(^\\d{15}$");
        Matcher matcher = pattern.matcher(userName);
        return matcher.matches();
    }

    /**
     * 验证 密码是否符合格式 密码： 6－20个字符
     */
    public static boolean checkPassWord(String passWord) {
        if (passWord.length() < 6 || passWord.length() > 20) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 验证 电子信箱是否符合格式 邮箱： 符合邮箱的格式
     */
    public static boolean checkEmail(String email) {
        Pattern pattern = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * 验证手机号码是否符合格式
     *
     * @param phoneNum
     * @return
     */
    public static boolean isMobileNO(String phoneNum) {
        Pattern pattern = Pattern
//				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
//				.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9])|(17[0-8])|(147))\\d{8}$");
                .compile("^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147))\\d{8}$");
        Matcher matcher = pattern.matcher(phoneNum);
        return matcher.matches();
    }

    public static String[] ZONGYOU = new String[]{"5", "10", "15", "30",
            "50", "100"};// '纵游点卡'
    public static String[] TIANXIA = new String[]{"10", "15", "20", "30",
            "50", "100"};// '天下点卡'
    public static String[] SZX = new String[]{"10", "20", "30", "50", "100",
            "300", "500"};// '移动充值卡'
    public static String[] TELECOM = new String[]{"50", "100"};// '电信充值卡'
    public static String[] UNICOM = new String[]{"20", "30", "50", "100",
            "300", "500"};// '联通充值卡'
    public static String[] SNDACARD = new String[]{"10", "15", "30", "50",
            "100"};// '盛大点卡'
    public static String[] ZHENGTU = new String[]{"10", "15", "20", "30",
            "50", "60", "68", "120", "150", "180", "208", "300", "468"};// '征途点卡'
    public static String[] NETEASE = new String[]{"10", "15", "20", "30",
            "50"};// '网易点卡'
    public static String[] JUNNET = new String[]{"10", "15", "30", "50",
            "100"};// '骏网点卡'
    public static String[] JIUYOU = new String[]{"10", "30", "50"};// '九游点卡'
    public static String[] WANMEI = new String[]{"15", "30", "50", "100"};// '完美点卡'
    public static String[] ALIPAY = new String[]{"100", "10", "30", "50",
            "200", "300", "400", "500", "1000", "2000", "3000", "4000", "5000",
            "6000", "8000", "9999"};// '支付宝'
    public static String[] BANK = new String[]{"100", "10", "30", "50",
            "200", "300", "400", "500", "1000", "2000", "3000", "4000", "5000",
            "6000", "8000", "9999"};// '网银'
    public static String[] TENPAY = new String[]{"100", "10", "30", "50",
            "200", "300", "400", "500", "1000", "2000", "3000", "4000", "5000",
            "6000", "8000", "9999"};// '财付通'
    public static String[] HUIKUAN = new String[]{"100", "10", "30", "50",
            "200", "300", "400", "500", "1000", "2000", "3000", "4000", "5000",
            "6000", "8000", "9999"};// '汇款'
    public static String[] QQCARD = new String[]{"5", "10", "15", "20", "30",
            "60", "100", "200"};// 'Q币'
    public static String[] WALLGAME = new String[]{"10", "20", "30", "50",
            "100", "200", "300", "500", "1000", "2000", "3000", "5000", "8000"};// '钱包'

    public static void setData(HashMap<String, ArrayList<String>> map) {
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry me = (Map.Entry) (it.next());
            String key = (String) me.getKey();
            ArrayList<String> list = (ArrayList<String>) me.getValue();
            setHowMoney(key, list);
        }
    }

    private static String[] getListString(ArrayList<String> list) {
        String[] strs = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            strs[i] = list.get(i);
        }
        return strs;
    }

    private static void setHowMoney(String key, ArrayList<String> list) {

        if ("SZX".equals(key)) {
            SZX = getListString(list);
        } else if ("TELECOM".equals(key)) {
            TELECOM = getListString(list);
        } else if ("UNICOM".equals(key)) {
            UNICOM = getListString(list);
        } else if ("SNDACARD".equals(key)) {
            SNDACARD = getListString(list);
        } else if ("ZHENGTU".equals(key)) {
            ZHENGTU = getListString(list);
        } else if ("NETEASE".equals(key)) {
            NETEASE = getListString(list);
        } else if ("JUNNET".equals(key)) {
            JUNNET = getListString(list);
        } else if ("JIUYOU".equals(key)) {
            JIUYOU = getListString(list);
        } else if ("ALIPAY".equals(key)) {
            ALIPAY = getListString(list);
        } else if ("WANMEI".equals(key)) {
            WANMEI = getListString(list);
        } else if ("BANK".equals(key)) {
            BANK = getListString(list);
        } else if ("TENPAY".equals(key)) {
            TENPAY = getListString(list);
        } else if ("HUIKUAN".equals(key)) {
            HUIKUAN = getListString(list);
        } else if ("TIANXIA".equals(key)) {
            TIANXIA = getListString(list);
        } else if ("ZONGYOU".equals(key)) {
            ZONGYOU = getListString(list);
        } else if ("QQCARD".equals(key)) {
            QQCARD = getListString(list);
        } else if ("WALLET".equals(key)) {
            WALLGAME = getListString(list);
        } else {
            // FUCK
        }
    }

    public static String[] getHowMoney(String data) {
        if ("SZX".equals(data)) {
            return SZX;
        } else if ("TELECOM".equals(data)) {
            return TELECOM;
        } else if ("UNICOM".equals(data)) {
            return UNICOM;
        } else if ("SNDACARD".equals(data)) {
            return SNDACARD;
        } else if ("ZHENGTU".equals(data)) {
            return ZHENGTU;
        } else if ("NETEASE".equals(data)) {
            return NETEASE;
        } else if ("JUNNET".equals(data)) {
            return JUNNET;
        } else if ("JIUYOU".equals(data)) {
            return JIUYOU;
        } else if ("ALIPAY".equals(data)) {
            return ALIPAY;
        } else if ("WANMEI".equals(data)) {
            return WANMEI;
        } else if ("BANK".equals(data)) {
            return BANK;
        } else if ("TENPAY".equals(data)) {
            return TENPAY;
        } else if ("HUIKUAN".equals(data)) {
            return HUIKUAN;
        } else if ("TIANXIA".equals(data)) {
            return TIANXIA;
        } else if ("ZONGYOU".equals(data)) {
            return ZONGYOU;
        } else if ("QQCARD".equals(data)) {
            return QQCARD;
        } else if ("WALLET".equals(data)) {
            return WALLGAME;
        } else {
            return new String[]{};
        }
    }

    /**
     * 播放背景音乐
     */
    public static void startPlayer(Context context) {
        File file = new File("/mnt/sdcard/" + Conet.DirName + "/mile.mp3");
        if (file.exists()) {
            Conet.player = new MediaPlayer();
            if (Conet.player == null) {
                return;
            }
            Conet.player.reset();
            try {
                Conet.player.setDataSource(file.getPath());
                Conet.player.prepare();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            Conet.player = MediaPlayer.create(context, null); // 音乐在raw文件夹下的音乐文件路径
            if (Conet.player == null) {
                return;
            }
            Conet.player.stop();
            try {
                Conet.player.prepare();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        Conet.player.start();
        Conet.player.setOnCompletionListener(new OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                try {
                    Conet.player.stop();
                    Conet.player.prepare();
                    Conet.player.start();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 判断背景音乐是否在运行
     */
    public static void isPlaying() {
        if (Conet.player == null) {
            return;
        }
        if (!Conet.player.isPlaying()) {
            Conet.player.start();
        } else {
            Conet.player.pause();
        }
    }


    /**
     * 获取图标的图片
     */
    // public static void getBitMap(Context context) {
    // AsyncImageLoader loader = new AsyncImageLoader();
    // Bitmap map = BitmapFactory.decodeResource(context.getResources(),
    // R.drawable.top_include_main_up);
    // if (map == null) {
    // return;
    // }
    // String screen = map.getWidth() + "x" + map.getHeight();
    // String path = Conet.terraceInfor.getP_pic_path() + "icon_"
    // + Conet.terraceInfor.getPid() + "_" + screen + "_";
    // String path_up = path + "up.png";
    // String path_down = path + "down.png";
    // Bitmap bm_up = loader.loadImage(path_up, new Callback() {
    // public void imageLoaded(String path, Bitmap bitmap) {
    // // TODO Auto-generated method stub
    // }
    // });
    // Bitmap bm_down = loader.loadImage(path_down, new Callback() {
    // public void imageLoaded(String path, Bitmap bitmap) {
    // // TODO Auto-generated method stub
    // }
    // });
    // if (bm_up != null && bm_down != null) {
    // BitmapDrawable bd_up = new BitmapDrawable(bm_up);
    // BitmapDrawable bd_down = new BitmapDrawable(bm_down);
    // StateListDrawable drawable = new StateListDrawable();
    // drawable.addState(new int[] { android.R.attr.state_pressed },
    // bd_down);
    // drawable.addState(new int[] { android.R.attr.state_checkable },
    // bd_down);
    // drawable.addState(new int[] { android.R.attr.state_checked,
    // android.R.attr.state_window_focused }, bd_down);
    // drawable.addState(new int[] {}, bd_up);
    // Conet.drawable = drawable;
    // Conet.bdrawable = bd_down;
    // }
    // }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static void adaptive(int drawable, Activity activity, ImageView view) {

        // Bitmap bitmap = BitmapFactory.decodeFile(imageFile);
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight(); // 组件的高
        int width = view.getMeasuredWidth(); // 组件的宽

        BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();

        bitmapFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(),
                drawable, bitmapFactoryOptions);// 获取bitmap
        int yRatio = bitmapFactoryOptions.outHeight / height;
        int xRatio = bitmapFactoryOptions.outWidth / width;
        if (yRatio > 1 || xRatio > 1) {
            if (yRatio > xRatio)
                bitmapFactoryOptions.inSampleSize = yRatio;
            else
                bitmapFactoryOptions.inSampleSize = xRatio;
        }
        bitmapFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeResource(activity.getResources(),
                drawable, bitmapFactoryOptions);
        view.setImageBitmap(bitmap);
    }

    public static Drawable setRadioBackGround(Activity activity, int id,
                                              View view) {
        BitmapDrawable bitmapDrawable = null;
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight(); // 组件的高
        int width = view.getMeasuredWidth(); // 组件的宽
        BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
        view.setLayoutParams(new RadioGroup.LayoutParams(width, height));
        bitmapFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(),
                id, bitmapFactoryOptions);// 获取bitmap
        int yRatio = bitmapFactoryOptions.outHeight / height;
        int xRatio = bitmapFactoryOptions.outWidth / width;
        if (yRatio > 1 || xRatio > 1) {
            if (yRatio > xRatio)
                bitmapFactoryOptions.inSampleSize = yRatio;
            else
                bitmapFactoryOptions.inSampleSize = xRatio;
        }
        bitmapFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeResource(activity.getResources(), id,
                bitmapFactoryOptions);
        if (bitmap != null) {
            bitmapDrawable = new BitmapDrawable(bitmap);
        }
        return bitmapDrawable;
    }

    public static void setLinearBackGround(Activity activity, int id, View view) {
        BitmapDrawable bitmapDrawable = null;
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight(); // 组件的高
        int width = view.getMeasuredWidth(); // 组件的宽
        BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
        view.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        bitmapFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(),
                id, bitmapFactoryOptions);// 获取bitmap
        int yRatio = bitmapFactoryOptions.outHeight / height;
        int xRatio = bitmapFactoryOptions.outWidth / width;
        if (yRatio > 1 || xRatio > 1) {
            if (yRatio > xRatio)
                bitmapFactoryOptions.inSampleSize = yRatio;
            else
                bitmapFactoryOptions.inSampleSize = xRatio;
        }
        bitmapFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeResource(activity.getResources(), id,
                bitmapFactoryOptions);
        if (bitmap != null) {
            bitmapDrawable = new BitmapDrawable(bitmap);
        }
        view.setBackgroundDrawable(bitmapDrawable);
    }

    public static void setFullscreen(Activity activity) {

    }

    // 获取手机号后缓存起来
    public static void initPhoneNumber(final SharedPreferences userpdf,
                                       final Context context) {
        Conet.phonenumber = userpdf.getString("phone", "");
        if (!TextUtils.isEmpty(Conet.phonenumber)) {


            AndPermission.with(context)
                    .runtime()
                    .permission(Permission.READ_PHONE_STATE)
                    .onGranted(new Action<List<String>>() {
                        @SuppressLint("MissingPermission")
                        @Override
                        public void onAction(List<String> permissions) {
                            final TelephonyManager telephonyManager = (TelephonyManager) context
                                    .getSystemService(Context.TELEPHONY_SERVICE);
                            Conet.phonenumber = telephonyManager.getLine1Number();
                            if (null != Conet.phonenumber) {
                                Editor editor = userpdf.edit();
                                editor.putString("phone", Conet.phonenumber);
                                editor.apply();
                            }
                        }
                    })
                    .onDenied(new Action<List<String>>() {
                        @Override
                        public void onAction(List<String> permissions) {
                            Toast.makeText(context, "读取手机号失败", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .start();

        }
    }

    // 获取手机运营商
    public static String getProvidersName(Context context) {

        return null;
    }

    public static String getid(Context contexts) {
        String id;
        ConnectivityManager con = (ConnectivityManager) contexts
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = con.getActiveNetworkInfo();

        if (networkinfo == null || !networkinfo.isAvailable()) {
            Toast.makeText(contexts, "无网络连接,请检查网络..", Toast.LENGTH_LONG).show();
            return "";
        } else {
            boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                    .isConnectedOrConnecting();
            if (!wifi) { // 提示使用wifi
                if (Build.VERSION.SDK_INT > 14) {
                    id = getLocalIpAddressV4();

                } else {
                    id = GetHostIp();

                }
                return id;
            } else {
                id = getwifiip(contexts);
                return id;
            }
        }
    }

    // 3g

    public static String GetHostIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> ipAddr = intf.getInetAddresses(); ipAddr
                        .hasMoreElements(); ) {
                    InetAddress inetAddress = ipAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }

                }
            }
        } catch (SocketException ex) {
        } catch (Exception e) {
        }
        return " ";
    }

    // 4.0
    public static String getLocalIpAddressV4() {
        String ip = "";
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) // 这里做了一步IPv4的判定
                    {
                        ip = inetAddress.getHostAddress().toString();
                        return ip;
                    }
                }
            }
        } catch (SocketException e) {
            // Log.i("SocketException--->", ""+e.getLocalizedMessage());
            return "ip is error";
        }
        return ip;
    }

    public static String getwifiip(Context contexts) {
        WifiManager wifimanger = (WifiManager) contexts.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        if (null == wifimanger) {
            return "";
        }
        WifiInfo wifiinfo = wifimanger.getConnectionInfo();
        // 注：getIpAddress获取的为int型需要用intToIp方法
        String ip = intToIp(wifiinfo.getIpAddress());
        return ip;
    }

    private static String intToIp(int i) {
        return (i & 0xFF) + "." + ((i >> 8) & 0xFF) + "." + ((i >> 16) & 0xFF)
                + "." + (i >> 24 & 0xFF);
    }

    // 判断系统当前的时间
    public static int getCurrentTime() {
        int time;

        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy年MM月dd日    hh:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
        String str = formatter.format(curDate);
        time = Integer.parseInt(str);
        return time;
    }

    public static void Toast(Context context, String string) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    public static String formatString(String formatstr) {
        String lastFormat = "";
        // Conet.formatCheapStr=formatstr.split("|");
        lastFormat = formatstr.replace("|", "\n").replace("{", "")
                .replace("}", "");
        return lastFormat;
    }

    public static int getResID(Context context, String resName, String defType) {
        Resources res = context.getResources();

        return res.getIdentifier(resName, defType, context.getPackageName());
    }

    /**
     * 下载apk相关
     */
    @Deprecated
    public static boolean isRooted() {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("su");
            OutputStream outputStream = process.getOutputStream();
            InputStream inputStream = process.getInputStream();
            outputStream.write("id\n".getBytes());
            outputStream.flush();
            outputStream.write("exit\n".getBytes());
            outputStream.flush();
            process.waitFor();
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream));
            String s = bufferedReader.readLine();
            if (s.contains("uid=0"))
                return true;
        } catch (IOException e) {
            Log.e(TAG, "没有root权限");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (process != null)
                process.destroy();
        }
        return false;
    }

    public static boolean checkRooted() {
        boolean result = false;
        try {
            result = new File("/system/bin/su").exists()
                    || new File("/system/xbin/su").exists();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 点击一键注册，
     * 截图保存到相册
     */
    // 获取和保存当前截图
    public static void GetandCurrentImage(Dialog dialog, Activity a) {
        // 1.构建bitmap
        // WindowManager mWindowManager=activity.getWindowManager();
        // Display mDisplay=mWindowManager.getDefaultDisplay();
        // int w=mDisplay.getWidth();
        // int h=mDisplay.getHeight();
        // Bitmap bmp=Bitmap.createBitmap(w,h,
        // Config.ARGB_8888);
        // //2.获取屏幕
        // View decorview=activity.getWindow().getDecorView();
        // decorview.setDrawingCacheEnabled(true);
        // bmp = decorview.getDrawingCache();
        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = getSDCardPath() + "/AndyDemo/ScreenImages"; // use your desired path
            // create bitmap screen capture
            View v1 = dialog.getWindow().getDecorView().getRootView();

            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
//	        File imageFile = new File(mPath);

//	        FileOutputStream outputStream = new FileOutputStream(imageFile);
//	        int quality = 100;
//	        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
//	        outputStream.flush();
//	        outputStream.close();
            File path = new File(mPath);
            // 文件
//				System.out.println(str);
            Log.i("LOG", str);
            String filePath = mPath + str + ".png";
//				System.out.println("filePath:" + filePath);
            File file = new File(filePath);
            if (!path.exists()) {
                path.mkdirs();
            }
            if (!file.exists()) {

                file.createNewFile();
            }
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            if (null != fos) {
                // 第一参数是图片格式，第二个是图片质量，第三个是输出流
//	****				bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
                // Toast.makeText(this,
                // "截屏文件已保存至/storage/emulated/0/AndyDemo/ScreenImage/下",
                // Toast.LENGTH_LONG).show();
				/*	Toast.makeText(a, "截屏已保存到相册", Toast.LENGTH_LONG).show();
					Intent intent = new Intent(
							Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
					Uri uri = Uri.fromFile(file);
					intent.setData(uri);
					a.sendBroadcast(intent);*/
                //这个广播的目的就是更新图库，发了这个广播进入相册就可以找到你保存的图片了！，记得要传你更新的file哦
                ContentValues values = new ContentValues();
                values.put(ImageColumns.TITLE, "title");
                values.put(ImageColumns.DISPLAY_NAME, "filename.jpg");
                values.put(ImageColumns.DATE_TAKEN, System.currentTimeMillis());
                values.put(ImageColumns.MIME_TYPE, "image/jpeg");
                values.put(ImageColumns.ORIENTATION, 0);
                values.put(ImageColumns.DATA, filePath);
                values.put(ImageColumns.WIDTH, bitmap.getWidth());
                values.put(ImageColumns.HEIGHT, bitmap.getHeight());

                Uri mUri = a.getContentResolver().insert(
                        Images.Media.EXTERNAL_CONTENT_URI, values);
                OutputStream out = a.getContentResolver().openOutputStream(mUri);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                out.flush();
                out.close();

                // update file size in the database
                values.clear();
                values.put(ImageColumns.SIZE, new File(filePath).length());
                a.getContentResolver().update(mUri, values, null, null);
                Toast.makeText(a, "截屏已保存到相册", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getSDCardPath() {
        File sdcardDir = null;
        // 判断SDCard是否存在
        boolean sdcardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdcardExist) {
            sdcardDir = Environment.getExternalStorageDirectory();
        }
        return sdcardDir.toString();
    }

    /**
     * 显示进度圈
     */
    static Dialog dialog;

    public static void dialogShow(Context context) {
        dialog = new Dialog(context, Util.getResID(context,
                "ledi_myDialogTheme", "style"));
        dialog.setContentView(Util.getResID(context, "ledi_load_dialog",
                "layout"));
        dialog.setCanceledOnTouchOutside(false);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.show();
    }

    public static void dialogDismiss(Context context) {
        if (null != dialog) {
            dialog.dismiss();
        }
    }

    public static String getAndroidId(Context context) {
        String androidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidID;
    }
}
