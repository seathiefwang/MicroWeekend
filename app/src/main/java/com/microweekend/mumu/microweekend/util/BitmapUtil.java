package com.microweekend.mumu.microweekend.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BitmapUtil {

	public static final String CACHESPATH = "mnt/sdcard/anjoyweibo/cache/";

	/**
	 * 将网络图片 转换成Bitmap
	 * 
	 * @param path
	 * @return
	 */
	public static Bitmap getBit(String path) {
		//从SD卡获取缓存到本地的 图片
		Bitmap bitmap =getbitfromfile(path);
		if (bitmap == null) {
			try {
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true);
				conn.connect();
				bitmap = BitmapFactory.decodeStream(conn.getInputStream());
				saveBitmap(path, bitmap);
				conn.disconnect();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bitmap;
	}
	public static Bitmap getbitfromfile(String url) {
		return BitmapFactory.decodeFile(CACHESPATH + MD5Util.MD5(url) + ".png");
	}

	/**
	 * 保存图片
	 * @param url
	 * @param bitmap
	 */
	public static  void saveBitmap(String url, Bitmap bitmap) {
		File file = new File(CACHESPATH + MD5Util.MD5(url) + ".png");
//		file.getParentFile().length()
		if (!file.exists()) {
			file.getParentFile().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FileOutputStream os;
		try {
			os = new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 100, os);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public static boolean makesureSizeNotTooLarge(Rect rect) {
		int FIVE_M = 5242880;
		return rect.width() * rect.height() * 2 <= 5242880;
	}

	public static int getSampleSizeOfNotTooLarge(Rect rect) {
		int FIVE_M = 5242880;
		double ratio = (double)rect.width() * (double)rect.height() * 2.0D / 5242880.0D;
		return ratio >= 1.0D?(int)ratio:1;
	}

	public static int getSampleSizeAutoFitToScreen(int vWidth, int vHeight, int bWidth, int bHeight) {
		if(vHeight != 0 && vWidth != 0) {
			int ratio = Math.max(bWidth / vWidth, bHeight / vHeight);
			int ratioAfterRotate = Math.max(bHeight / vWidth, bWidth / vHeight);
			return Math.min(ratio, ratioAfterRotate);
		} else {
			return 1;
		}
	}

	public static boolean verifyBitmap(byte[] datas) {
		return verifyBitmap((InputStream)(new ByteArrayInputStream(datas)));
	}

	public static boolean verifyBitmap(InputStream input) {
		if(input == null) {
			return false;
		} else {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			Object input1 = input instanceof BufferedInputStream ?input:new BufferedInputStream(input);
			BitmapFactory.decodeStream((InputStream)input1, (Rect)null, options);

			try {
				((InputStream)input1).close();
			} catch (IOException var3) {
				var3.printStackTrace();
			}

			return options.outHeight > 0 && options.outWidth > 0;
		}
	}

	public static boolean verifyBitmap(String path) {
		try {
			return verifyBitmap((InputStream)(new FileInputStream(path)));
		} catch (FileNotFoundException var2) {
			var2.printStackTrace();
			return false;
		}
	}

}
