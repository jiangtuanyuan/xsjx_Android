package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.json.entity.SchoolNewsEntity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 
 * 一定要关闭 db 数据库操作！！！！！！！！！！！！！！！！！！！！！！！！！！！ db.close(); 还有数据集的操作
 */
public class UserInfoDo {

	SqliteDo dao;
	public UserInfoDo(Context context) {
		dao = new SqliteDo(context);
	}

	/**
	 * 添加用户
	 */
	public boolean AddUserInfo(int id, String UserName, String UserPwd, String UserSex, String UserTel,
			String UserHeadPortrait) {
		SQLiteDatabase db = dao.getSQLiteDatabase();
		ContentValues values = new ContentValues();
		values.put("id", id);
		values.put("username", UserName);
		values.put("userpwd", UserPwd);
		values.put("sex", UserSex);
		values.put("tel", UserTel);
		values.put("HeadPortrait", UserHeadPortrait);

		long flag = 0;

		flag = db.insert(SqliteDo.TB_USERINFO, null, values);

		db.close();

		if (flag > 0)
			return true;

		else
			return false;

	}
/**
 * 查询指定ID的用户数据
 */
	
	
	
	
	/**
	 * 查询是否存在id为的用户 如果存在就返回 false 如果不存在 就返回true
	 */
	public boolean selectID(String id) {

		SQLiteDatabase db = dao.getSQLiteDatabase();

		Cursor cursor = db.query(SqliteDo.TB_USERINFO, null, "id=?", new String[] { id }, null, null, null);

		if (cursor.moveToNext()) {
			cursor.close();
			db.close();

			return false;
		} else {
			cursor.close();
			db.close();

			return true;
		}
	}

	/**
	 * 删除指定ID的用户数据
	 */
	public boolean delectUserAll(String id) {
		SQLiteDatabase db = dao.getSQLiteDatabase();
		int index = db.delete(SqliteDo.TB_USERINFO, "id=?", new String[] { id });
		db.close();
		return index > 0 ? true : false;
	}

	/**
	 * 删除全部用户数据
	 */
	public boolean delectUserAll() {
		SQLiteDatabase db = dao.getSQLiteDatabase();
		int index = db.delete(SqliteDo.TB_USERINFO, "id>0", null);
		db.close();
		return index > 0 ? true : false;
	}

}
