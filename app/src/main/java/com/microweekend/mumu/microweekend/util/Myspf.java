package com.microweekend.mumu.microweekend.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Myspf {

	public static final String NAME="microweekend_spf";

	public static void saveUserName(Context context, String name) {
		SharedPreferences preferences=context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		preferences.edit().putString("user_name", name).apply();
	}
	public static String getUserName(Context context) {
		SharedPreferences preferences=context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		return preferences.getString("user_name","");
	}

	public static void saveUUID(Context context, String name) {
		SharedPreferences preferences=context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		preferences.edit().putString("uuid", name).apply();
	}
	public static String getUUID(Context context) {
		SharedPreferences preferences=context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		return preferences.getString("uuid","");
	}
	
	public static void saveLoginFlag(Context context, boolean login){
		SharedPreferences preferences=context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		preferences.edit().putBoolean("islogin", login).apply();
	}

	public static boolean  getLoginFlag(Context context){
		SharedPreferences preferences=context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		return preferences.getBoolean("islogin", false);
	}
	public static void saveFirstFlag(Context context){
		SharedPreferences preferences=context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		preferences.edit().putBoolean("isfirst", false).apply();
	}

	public static boolean  getFirstFlag(Context context){
		SharedPreferences preferences=context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		return preferences.getBoolean("isfirst", true);
	}
	
	/**
	 * 将没有发送的微博保存草稿箱
	 * @param context
	 * @param msg
	 */
	public static void saveMsgBox(Context context,String msg){
		SharedPreferences preferences=context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		preferences.edit().putString("msg", msg).commit();
	}
	/**
	 * 读取保存的微博
	 * @param context
	 * @return
	 */
	public static String getMsgBox(Context context){
		SharedPreferences preferences=context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
		return preferences.getString("msg", "");
	}
	
	
	
	
}
