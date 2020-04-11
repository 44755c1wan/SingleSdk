package com.ledi.floatwindow.util;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

	public static File createFile(String filePath,String fileName){
		File file=new File(filePath);
		if(!file.exists()){
			file.mkdirs();
		}
		return new File(filePath+"/"+fileName);
	}
	
	@SuppressLint("NewApi")
	public static boolean saveBitmap(Bitmap b, File filePath) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filePath);
			if (null != fos) {
				b.compress(Bitmap.CompressFormat.WEBP, 60, fos);
				fos.flush();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			return false;
		} catch (IOException e) {
			// e.printStackTrace();
			return false;
		}
		return true;
	}
}
