package com.ledi.biz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ledi.bean.User;

import java.util.ArrayList;

public class UserDao {
//	private DBOpenHelper helper;

	private Context mContext;
	private SQLiteDatabase db;

	public UserDao(Context context) {
		mContext = context;
	}

	public void open() {
		if (null == db) {
			db = new DBOpenHelper(mContext).getWritableDatabase();
		}

	}

	private void close() {
		if (null != db) {
			db.close();
			db = null;
		}
	}
	/**
	 * 查询表中的全部信息
	 */
	public ArrayList<User> getUsers() {
		open();
		ArrayList<User> users = new ArrayList<User>();
		Cursor c = db.rawQuery("select * from usertbl", null);

		if (c != null && c.getCount() > 0) {
			while (c.moveToNext()) {
				User user = new User();
				user.set_id(c.getInt(c.getColumnIndex("_id")));
				user.setUsername(c.getString(c.getColumnIndex("username")));
				user.setPassword(c.getString(c.getColumnIndex("password")));
				user.setNickname(c.getString(c.getColumnIndex("nickname")));
				user.setIsLastTime(c.getInt(c.getColumnIndex("isLastTime")));
				user.setIsQuick(c.getInt(c.getColumnIndex("isQuick")));
				user.setState(c.getInt(c.getColumnIndex("state")));
                user.setBindid(c.getString(c.getColumnIndex("bindId")));
                user.setIspaytime(c.getInt(c.getColumnIndex("isLastPayTime")));
                user.setUid(c.getString(c.getColumnIndex("uid")));
				users.add(user);
			}
			c.close();
		}

//		db.close();
		close();
		return users;
	}
	
	public User getCurrect(String uid){
		open();
		
		Cursor c = db
				.rawQuery(
						"SELECT username,password ,nickname,isLastTime,isQuick,state,bindId,isLastPayTime,uid FROM usertbl WHERE uid = ? ",
						new String[] { uid });
		User user = null;
		if (c.moveToNext()) {
		   user=new User(c.getString(0), c.getString(1),c.getString(2), c.getString(3), c.getInt(4),c.getInt(5),c.getInt(6), c.getString(7),c.getInt(8));
//			user = new User(c.getString(0), c.getLong(1),
//					c.getString(2), c.getString(3), c.getLong(4),
//					c.getString(5), c.getInt(6), c.getString(7), c.getInt(8));
		}
//		if (null != record)
		close();
		return user;
	}

	/**
	 * 添加新用户
	 * 
	 * @param user
	 * @return
	 */
	public long insert(User user) {
		open();
		long id = 0;
//		SQLiteDatabase db = helper.getWritableDatabase();// context.openOrCreateDatabase("user.db",
															// Context.MODE_PRIVATE,
															// null);
		ContentValues values = new ContentValues();
		values.put("username", user.getUsername());
		values.put("password", user.getPassword());
		values.put("nickname", user.getNickname());
		values.put("isLastTime", user.getIsLastTime());
		values.put("isQuick", user.getIsQuick());
		values.put("state", user.getState());
        values.put("bindId", user.getBindid());
        values.put("isLastPayTime", user.getIspaytime());
        values.put("uid", user.getUid());
		id = db.insert("usertbl", "state", values);
		/*
		 * 表名 列名 列值
		 * 
		 * insert into 表名(列名) values(列值) insert into 表名(phonenum) values(null)
		 */
//		db.close();
		close();
		return id;
	}

	public int update(User user) {
		open();
		int count = 0;
//		SQLiteDatabase db = helper.getWritableDatabase();// context.openOrCreateDatabase("user.db",
															// Context.MODE_PRIVATE,
															// null);
		ContentValues values = new ContentValues();
		values.put("username", user.getUsername());
		values.put("password", user.getPassword());
		values.put("nickname", user.getNickname());
		values.put("isLastTime", user.getIsLastTime());
		values.put("isQuick", user.getIsQuick());
		values.put("state", user.getState());
	      values.put("bindId", user.getBindid());
	        values.put("isLastPayTime", user.getIspaytime());
	        values.put("uid", user.getUid());
		count = db.update("usertbl", values, "_id=?",
				new String[] { "" + user.get_id() });
		/*
		 * 表名 tablename修改的列名 和 新值 values条件条件中的占位符的值
		 * 
		 * update 表名 set 列名=新值,... where 条件
		 */

//		db.close();
		close();
		return count;
	}

//	public int delete(int id) {
//		open();
//		int count = 0;
////		SQLiteDatabase db = helper.getWritableDatabase();// context.openOrCreateDatabase("user.db",
//															// Context.MODE_PRIVATE,
//															// null);
//		count = db.delete("usertbl", "_id=?",
//				new String[] { String.valueOf(id) });
//		/*
//		 * 表名条件条件的参数
//		 * 
//		 * delete from 表名 where 条件
//		 */
//
//		db.close();
//		close();
//		return count;
//	}
	
	/*
	 * uid来进行删除
	 */
	public int delete(int uid) {
		open();
		int count = 0;
//		SQLiteDatabase db = helper.getWritableDatabase();// context.openOrCreateDatabase("user.db",
															// Context.MODE_PRIVATE,
															// null);
		count = db.delete("usertbl", "uid=?",
				new String[] { String.valueOf(uid) });
		/*
		 * 表名条件条件的参数
		 * 
		 * delete from 表名 where 条件
		 */

//		db.close();
		close();
		return count;
	}
	
}
