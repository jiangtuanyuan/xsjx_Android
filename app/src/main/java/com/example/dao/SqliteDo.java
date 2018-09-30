package com.example.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.os.Environment;
import android.util.Log;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteDo {
//�����ṩ���ݿ��������
	public SqliteDo(Context context) {
		this.context = context;
	}
	
	private Context context;
	
	public static String DB_NAME = "xsjx.db";
	public static String TB_SCHOOLNEW = "schoolnew";
	public static String TB_USERINFO="userinfo";
	public static XsjxOpenHelper open = null;
	//�����ṩ���ݿ��������
	public SQLiteDatabase getSQLiteDatabase() {
		if(open==null)
		  open = new XsjxOpenHelper(context, DB_NAME, null, 1);
		
		SQLiteDatabase db = open.getReadableDatabase();
		return db;
	}

	
	//����һ�����ݿ�򿪰�����SQLiteOpenHelper
	public class XsjxOpenHelper extends SQLiteOpenHelper {
		private static final String TAG = "XsjxOpenHelper"; 
		//У����Ѷ�����
		String sql = "create table " + TB_SCHOOLNEW
				+ " (id INTEGER,"
				+ "newstitle varchar(512) ," + "newscontent text,"
				+ "newsurl varchar(512)," + "newsauthor varchar(20),"
				+ "newsdate varchar(30)," + "newsupdate varchar(30) )";
		
		//�û���Ϣ�����
		String userinfoSql="create table " + SqliteDo.TB_USERINFO
				+ " (id INTEGER,"
				+ "username varchar(20) ," + "userpwd varchar(12),"
				+ "tel varchar(11)," + "sex varchar(4),"
				+ "HeadPortrait varchar(512) )";
		
		
	
		public XsjxOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}
		
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			// ����һ�δ������ݿ��ʱ�򣬵��ø÷���
			Log.i(TAG, "create Database------------->"); 
			db.execSQL(sql);
		
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.i(TAG, "update Database------------->");
			db.execSQL(userinfoSql);
			Log.v("onUpgrade", "�����˱�");
		}

	}

	
	/**
	 *ɾ����
	 */
public static boolean deletable(String tabName){
		 boolean result = false;  
         if(tabName == null){  
                 return false;  
         }  
         SQLiteDatabase db = null;  
         Cursor cursor = null;  
         try {  
                 db = open.getReadableDatabase();//��this�Ǽ̳�SQLiteOpenHelper��õ���  
                 String sql = "DROP TABLE "+tabName;  
                 cursor = db.rawQuery(sql, null);  
                 if(cursor.moveToNext()){  
                         int count = cursor.getInt(0);  
                         if(count>0){  
                                 result = true;  
                         }  
                 }  
             	db.close(); 
             	cursor.close();
                   
         } catch (Exception e) {  

 }
		return result;  
		
	}

	
	/**
	 * �жϱ��Ƿ��Ѿ�����
	 */
	 public static boolean tabIsExist(String tabName){  
         boolean result = false;  
         if(tabName == null){  
                 return false;  
         }  
         SQLiteDatabase db = null;  
         Cursor cursor = null;  
         try {  
                 db = open.getReadableDatabase();//��this�Ǽ̳�SQLiteOpenHelper��õ���  
                 String sql = "select count(*) as c from sqlite_master where type ='table' and name ='"+tabName.trim()+"' ";  
                 cursor = db.rawQuery(sql, null);  
                 if(cursor.moveToNext()){  
                         int count = cursor.getInt(0);  
                         if(count>0){  
                                 result = true;  
                         }  
                 }  
             	db.close(); 
             	cursor.close();
                   
         } catch (Exception e) {  

 }
		return result;  
 }
   

}
