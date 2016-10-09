package com.microweekend.mumu.microweekend.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MkDB extends SQLiteOpenHelper {

	public MkDB(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table mkinfo ("
			+ "_id integer primary key autoincrement," 
			+ "user varchar(50), content txt, userinfo txt, sended txt, joined txt, collect txt )";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
