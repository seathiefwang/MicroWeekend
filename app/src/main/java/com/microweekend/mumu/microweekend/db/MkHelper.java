package com.microweekend.mumu.microweekend.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.microweekend.mumu.microweekend.util.MkConstants;

public class MkHelper {

	String DBNAME = "microweekend.db";
	String table_name = "mkinfo";
	SQLiteDatabase db;
	MkDB helper;

	public MkHelper(Context con) {
		helper = new MkDB(con, DBNAME, null, 1);
	}

	public void insertContent(String string) {
		db = helper.getReadableDatabase();
		int id = getId(MkConstants.USER_NAME);
		if (id > 0) {
			ContentValues cv = new ContentValues();
			cv.put("content", string);
			db.update(table_name, cv, "_id=?", new String[]{String.valueOf(id)});
		}
		db.close();
	}
	public String getContent() {
		db = helper.getReadableDatabase();
		int id = getId(MkConstants.USER_NAME);
		Cursor c = db.query(table_name, null, "_id=?", new String[]{String.valueOf(id)}, null, null, null);
		String info="";
		while (c.moveToNext()) {
			info = c.getString(2);
		}
		c.close();
		db.close();
		return info;
	}

	public void insertUser(String string) {
		db = helper.getReadableDatabase();
		int id = getId(MkConstants.USER_NAME);
		if (id > 0) {
			ContentValues cv = new ContentValues();
			cv.put("userinfo", string);
			db.update(table_name, cv, "_id=?", new String[]{String.valueOf(id)});
		}
		db.close();
	}

	public String getUser() {
		db = helper.getReadableDatabase();
		int id = getId(MkConstants.USER_NAME);
		Cursor c = db.query(table_name, null, "_id=?", new String[]{String.valueOf(id)}, null, null, null);
		String info="";
		while (c.moveToNext()) {
			info = c.getString(3);
		}
		c.close();
		db.close();
		return info;
	}

	public void insertSended(String string) {
		db = helper.getReadableDatabase();
		int id = getId(MkConstants.USER_NAME);
		if (id > 0) {
			ContentValues cv = new ContentValues();
			cv.put("sended", string);
			db.update(table_name, cv, "_id=?", new String[]{String.valueOf(id)});
		}
		db.close();
	}

	public String getSended() {
		db = helper.getReadableDatabase();
		int id = getId(MkConstants.USER_NAME);
		Cursor c = db.query(table_name, null, "_id=?", new String[]{String.valueOf(id)}, null, null, null);
		String info="";
		while (c.moveToNext()) {
			info = c.getString(4);
		}
		c.close();
		db.close();
		return info;
	}

	public void insertJoined(String string) {
		db = helper.getReadableDatabase();
		int id = getId(MkConstants.USER_NAME);
		if (id > 0) {
			ContentValues cv = new ContentValues();
			cv.put("joined", string);
			db.update(table_name, cv, "_id=?", new String[]{String.valueOf(id)});
		}
		db.close();
	}

	public String getJoined() {
		db = helper.getReadableDatabase();
		int id = getId(MkConstants.USER_NAME);
		Cursor c = db.query(table_name, null, "_id=?", new String[]{String.valueOf(id)}, null, null, null);
		String info="";
		while (c.moveToNext()) {
			info = c.getString(5);
		}
		c.close();
		db.close();
		return info;
	}

	private int getId(String userName) {
		if (TextUtils.isEmpty(userName)) return 0;
		int id = 0;
		Cursor c = db.query(table_name, null, "user=?", new String[]{userName}, null, null, null);
		while (c.moveToNext()) {
			id = c.getInt(0);
		}
		c.close();
		if (id < 1) {
			ContentValues cv = new ContentValues();
			cv.put("user", userName);
			db.insert(table_name, null, cv);

			c = db.query(table_name, null, "user=?", new String[]{userName}, null, null, null);
			while (c.moveToNext()) {
				id = c.getInt(0);
			}
			c.close();
		}
		return id;
	}

	public void delete() {
		db = helper.getReadableDatabase();
		db.delete(table_name, null, null);
		db.close();
	}

}
