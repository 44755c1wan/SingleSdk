package com.ledi.floatwindow.util;

import android.content.Context;
import android.os.Environment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Const {
	public static String EXTRA_BOOTPATH = Environment.getExternalStorageDirectory()
			.getAbsolutePath();
	public static String BOOTPATH = EXTRA_BOOTPATH + "/ledi";
	public static  String BOOTPATH_SCREENSHOT = BOOTPATH+"/Screenshots";
	public static String INAGE_FORMAT = ".png";

	public static String dateFormat() {
		Date _data = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(_data);
	}
	
	/**
	 * px 2 dip
	 * 
	 * @param pContext
	 * @param px
	 * @return
	 */
	public static int px2dip(Context pContext, int px) {
		final float scale = pContext.getResources().getDisplayMetrics().density;
		return (int) (px / scale + 0.5f);
	}
}
