package com.example.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.example.xsjx.RunActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
/*���ߣ�����Բ
 * ʱ�� �� 2017-10-18
 * ���ã�ͨ��һ��URL ��ַ ���ز�������һ��ͼƬ  ���浽�����ļ���
 * 
 * */
public class UrlGoImg { 
	public static Bitmap Bitmap;
	public static Bitmap ImgDoload(final String urla) {
		new Thread() {
			public void run() {
				try {
					URL url = new URL(urla);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					
					conn.setReadTimeout(5000);
					conn.setConnectTimeout(5000);
					if(conn.getResponseCode() == 200){
						Log.v("Potho", "ͼƬOK"+url.toString());
						
					InputStream is = url.openStream();
				    Bitmap = BitmapFactory.decodeStream(is);
					is.close();
					 Log.v("Potho", "ͼƬ���سɹ���"+url.toString());
					}
					
					else {Bitmap=null; Log.v("Potho", "ͼƬ��ʧ�ܣ�"+url.toString());}
					
					
					/*/ ����ͼƬ��ָ��λ��
					is = url.openStream();
					String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/img/01.jpg";
					File file = new File(path);
					if (!file.getParentFile().exists())
						file.getParentFile().mkdirs();
					FileOutputStream out = new FileOutputStream(file);
					int len = 0;
					byte[] buf = new byte[1024];
					
					while ((len = is.read(buf)) > 0) {
						out.write(buf, 0, len);// ��buf�����ݴӵ�1����ʼ��дlen�����ȵ�out�ļ�����

					    }  
					is.close();
					out.close();*/

				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}.start();
		
		
	return Bitmap;
	}
	
    /* ����Բ
     * �������ã�ͨ��һ��ָ����Url ���ص�ָ���ļ���
     * return fase true 
     * 
     * */// 
	public static void ImgDoload(final String UrlStr,final String LoadImgPath,final Handler handler){
		new Thread(){
			public void run(){
				try {
					//http://www.jtyrl.cn/XSJX/Run_Images/Run1.jpg
					
					URL url = new URL(UrlStr);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setReadTimeout(8000);
					conn.setConnectTimeout(8000);
					if(conn.getResponseCode() == 200)
					{
						 Log.v("ImgDoload", "url(ok)="+url.toString());
						 InputStream in = url.openStream();//��ͼƬ��Դ
						 
						 File file=new File(LoadImgPath);
						 Log.v("filepath=", file.toString()+"");
						 Log.v("fileexists", file.getParentFile().exists()+"");
					
						   if(!file.getParentFile().mkdirs())
						   {
						     Log.v("ImgDoload", LoadImgPath+"=������,ϵͳ�Զ�����!");
						     Log.v("ImgDoload", "ͼƬ������..");

						     
						 	 FileOutputStream out=new FileOutputStream(file);
						 
						 	 
						 	 int len=0;
							 byte[] buf=new byte[1024];
							 while((len=in.read(buf))>0)
								   out.write(buf, 0, len);
							 Log.v("ImgDoload", "ͼƬ���سɹ�");
							 
							 
							 out.close();
							 in.close();
							
							  Message msg=new Message();
							  msg.what=4;
							  msg.obj="ͼƬ���سɹ�";
							  handler.sendMessage(msg);
						
						   }
						   
					    else  {  
					    	

							 int size = 0;
							 FileInputStream fis =new FileInputStream(file);
							 size = fis.available();
							 fis.close();
							 
							 if(!(size==conn.getContentLength()))
							 {
								 Log.v("ImgDoload", "ͼƬ��С��һ�£���������!");
								 FileOutputStream out=new FileOutputStream(file);
								 
							 	 
							 	 int len=0;
								 byte[] buf=new byte[1024];
								 while((len=in.read(buf))>0)
									   out.write(buf, 0, len);
								 Log.v("ImgDoload", "���سɹ�");
								 
								  Message msg=new Message();
								  msg.what=4;
								  msg.obj="ͼƬ���سɹ�";
								  handler.sendMessage(msg);
								  
								 out.close();
								 in.close();
								 
							 }
							 
							 else {
					    	
					    	  Message msg=new Message();
							  msg.what=3;
							  msg.obj="ͼƬ����!���Ҵ�Сһ��!������!";
							  handler.sendMessage(msg);
							 }
							 
					    }	 
						   
					}  else  {  
						  Message msg=new Message();
						  msg.what=3;
						  msg.obj="��ָ��ͼƬ�򲻿����߲�����!";
						  
						  handler.sendMessage(msg);
						  Log.v("ImgDoload", "����վʧ��!");   }	 
				
				} catch (MalformedURLException e) {
				
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
				
				
			}	
		}.start();;
		
		
	}	
	
	
}
