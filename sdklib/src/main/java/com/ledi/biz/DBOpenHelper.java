package com.ledi.biz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {
	private static final String DBNAME = "user.db";
	private static final int VERSION = 2;

	public DBOpenHelper(Context context) {
		super(context, DBNAME, null, VERSION);
	}

	public DBOpenHelper(Context context, String dbName) {
		super(context, dbName, null, VERSION);
	}

	public DBOpenHelper(Context context, String dbName, int version) {
		super(context, dbName, null, version);
	}

	public DBOpenHelper(Context context, int version) {
		super(context, DBNAME, null, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub

		String sql = "create table usertbl("
				+ "_id integer primary key autoincrement,"
				+ "username text not null," + "password text not null,"
				+ "nickname text not null," + "isLastTime integer not null,"
				+ "isQuick integer not null," + "state integer not null," + "bindId text not null," + "isLastPayTime integer not null," + "uid text not null"+")";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	  
		db.execSQL("DROP TABLE IF EXISTS usertbl");
		onCreate(db);
	}

}
