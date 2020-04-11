package com.ledi.floatwindow.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.View;

import java.io.File;

public class ScreenShot {

	public static Bitmap takeScreenShot(Activity activity) {
		// View that you need screenshot
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap b1 = view.getDrawingCache();
		if (null == b1) {
			return null;
		}
		// get the title's height
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;
		// int statusBarHeight = 0;
		// get the screen width and height
		int width = activity.getWindowManager().getDefaultDisplay().getWidth();
		int height = activity.getWindowManager().getDefaultDisplay()
				.getHeight();
		// b1=Bitmap.createBitmap(width, height, Config.ARGB_4444);
		// Log.e("",
		// "width=" + width + " height=" + height + "   " + b1.getWidth()
		// + "  " + b1.getHeight() + "  statusBarHeight="
		// + statusBarHeight);
		// remove title
		Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight,
				(width > b1.getWidth() ? b1.getWidth() : width),
				(height > b1.getHeight() ? b1.getHeight() : height)
						- statusBarHeight);
		// Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, b1.getWidth(),
		// b1.getHeight() - statusBarHeight);
		// Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, b1.getWidth(),
		// b1.getHeight());

		view.destroyDrawingCache();
		return b;
	}

	public static boolean shoot(Activity a, File filePath) {
		if (filePath == null) {
			return false;
		}
		if (!filePath.getParentFile().exists()) {
			filePath.getParentFile().mkdirs();
		}
		return FileUtil.saveBitmap(takeScreenShot(a), filePath);
	}
}