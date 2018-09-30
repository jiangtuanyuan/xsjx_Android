package com.example.dao;
import java.util.ArrayList;
import java.util.List;

import com.example.json.entity.SchoolNewsEntity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 
 * һ��Ҫ�ر� db ���ݿ����������������������������������������������������������  db.close();  �������ݼ��Ĳ���
 */
public class NewsDo {

	SqliteDo dao;
	public NewsDo(Context context) {
		dao = new SqliteDo(context);	
		
	}
	
	/**
	 * ������Ż���
	 */
	public boolean AddNews(int id,String newstitle,String newsContent,String newsUrl,String newsauthor,String newsdate,String newsUpdate){
		SQLiteDatabase db=dao.getSQLiteDatabase();
		ContentValues values = new ContentValues();
		values.put("id", id);
		values.put("newstitle", newstitle);
		values.put("newscontent", newsContent);
		values.put("newsurl", newsUrl);
		values.put("newsauthor", newsauthor);
		values.put("newsdate", newsdate);
		values.put("newsupdate", newsUpdate);
		
		long flag=0;
		
		flag=db.insert(SqliteDo.TB_SCHOOLNEW, null, values);
	
		db.close();
		
		if(flag>0)
			return true;
	
		else return false;
		
	
	}
	/**
	 * ��ѯ�Ƿ����idΪ������   ������ھͷ��� false   ��������� �ͷ���true
	 */
	public boolean selectID(String id) {

		SQLiteDatabase db = dao.getSQLiteDatabase();

		Cursor cursor = db.query(SqliteDo.TB_SCHOOLNEW, null, "id=?", new String[] { id }, null, null, null);

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
	 * ���ȫ�����Ż�������
	 */
	public List<SchoolNewsEntity> getnewsAll(){
		List<SchoolNewsEntity> list=new ArrayList<SchoolNewsEntity>();
		SQLiteDatabase db =	dao.getSQLiteDatabase();
		Cursor cursor =db.query(SqliteDo.TB_SCHOOLNEW, null, null, null, null, null, null);
		while(cursor.moveToNext()){
			SchoolNewsEntity entity=new SchoolNewsEntity();
			entity.id=cursor.getInt(0);
		    entity.newsTitle=cursor.getString(1);
		    entity.newsContent=cursor.getString(2);
		    entity.newsUrl=cursor.getString(3);
		    entity.newsAuthor=cursor.getString(4);
		    entity.newsDate=cursor.getString(5);
		    entity.newsUpdate=cursor.getString(6);
		    list.add(entity);	
		}	
		cursor.close();
		db.close();
		return list;
		
	}
	/**
	 * ɾ��ָ��ID��������
	 */
	public boolean delectNewsAll(String id){
		SQLiteDatabase db =	dao.getSQLiteDatabase();
		int index=db.delete(SqliteDo.TB_SCHOOLNEW, "id=?", new String[] { id });
		db.close();
		return index>0?true:false;
	}
	
	/**
	 * ɾ��ȫ����������
	 */
	public boolean delectNewsAll(){
		SQLiteDatabase db =	dao.getSQLiteDatabase();
		int index=db.delete(SqliteDo.TB_SCHOOLNEW, "id>0",null);
		db.close();
		return index>0?true:false;
	}
	
	
}
