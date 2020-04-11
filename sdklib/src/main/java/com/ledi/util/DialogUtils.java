package com.ledi.util;


import android.app.Dialog;
import android.content.Context;
import android.view.Window;


public class DialogUtils {

	public static Dialog dialog;

	public static void showDialog(Context context){
		dialog = new Dialog(context);
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(Util.getResID(context, "webview", "layout"));
		dialog.setCancelable(false);
		dialog.show();
	}
	
    public static void disDialog(){
    	dialog.dismiss();
    }
}
