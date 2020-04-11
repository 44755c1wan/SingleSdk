package com.ledi.sdk;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ledi.util.Conet;
import com.ledi.util.Util;

import java.util.Timer;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1) @SuppressLint({ "SetJavaScriptEnabled", "NewApi" }) 
public class SdkWeb extends Activity{
	private WebView mWebView;
	String webView_url = Conet.weburl;
	private Handler mHandler = new Handler();
	private Dialog dialog;
	private Timer timer;
	private String urls;
	private String orderid;
	private boolean ispaySuccess = false;
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(Util.getResID(this, "webview", "layout"));
		
		mWebView = (WebView) findViewById(Util.getResID(this, "webview", "id"));
		mWebView.loadUrl(webView_url);
		WebSettings webSettings = mWebView.getSettings();
		final JSHook js = new JSHook();
		mWebView.addJavascriptInterface(js, "hello");
		mWebView.setBackgroundColor(0);
//		mWebView.getBackground().setAlpha(0);
		webSettings.setJavaScriptEnabled(true); // 设置支持javascript脚本
		webSettings.setAllowFileAccess(true); // 允许访问文件
		webSettings.setBuiltInZoomControls(true); // 设置显示缩放按钮 
		webSettings.setSupportZoom(false); // 支持缩放
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);  
		webSettings.setUseWideViewPort(true);//关键点  
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);  
		webSettings.setDisplayZoomControls(false);  
		webSettings.setLoadWithOverviewMode(true); 
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);  
		webSettings.setSupportMultipleWindows(true); 
		webSettings.setAppCacheEnabled(true);//是否使用缓存
		webSettings.setDomStorageEnabled(true);//DOM Storage			
		webSettings.getMediaPlaybackRequiresUserGesture();
		mWebView.getSettings().setMediaPlaybackRequiresUserGesture(false);
		mWebView.setWebViewClient(new WebViewClient(){
        	@Override
        	public boolean shouldOverrideUrlLoading(WebView view, String url) {
        		//处理http和https开头的url
        		if (url.startsWith("http:")||url.startsWith("https:")) {
        			view.loadUrl(url);
        			System.out.println("url1111: "+url);
					return true;
				}else if(url.contains("alipays://platformapi/startApp"))
        		{
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    System.out.println("url1: "+url);	
                    
        			
				}
        		else{
					Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                    System.out.print("url1"+url);
                    
				}
        		return false;
        	};

        	 
			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);

				// timer.cancel();
				// timer.purge();
			}
		});
		mWebView.setOnKeyListener(new View.OnKeyListener() {  
            @Override  
            public boolean onKey(View v, int keyCode, KeyEvent event) {  
                if (event.getAction() == KeyEvent.ACTION_DOWN) {  
                    if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {  //表示按返回键
 

                    	mWebView.goBack();   //后退  
 
                        //webview.goForward();//前进
                        return true;    //已处理  
                    }  
                }  
                return false;  
            }  
        });

		// TODO Auto-generated method stub

	}
	
    final public class JSHook{
    	@JavascriptInterface
        public void javaMethod(String p){
//            Log.d(tag , "JSHook.JavaMethod() called! + "+p);
        }
    	@JavascriptInterface
        public void closeweb(){
//            final String info = "11111111111";
    		SdkWeb.this.runOnUiThread(new Runnable(){
                @Override
                public void run() {
   				 new Handler().postDelayed(new Runnable(){  
				     public void run() {  
				     //execute the task  
				    	 finish();
				     }  
				  }, 500);                	
                 }
            });
        }
    	
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// Operate.payfinish(orderid);

			this.finish();
		}
		return true;
	}
}
