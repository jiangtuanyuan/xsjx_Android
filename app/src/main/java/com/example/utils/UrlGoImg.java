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
/*作者：蒋团圆
 * 时间 ： 2017-10-18
 * 作用：通过一个URL 地址 加载并且下载一张图片  保存到本地文件夹
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
						Log.v("Potho", "图片OK"+url.toString());
						
					InputStream is = url.openStream();
				    Bitmap = BitmapFactory.decodeStream(is);
					is.close();
					 Log.v("Potho", "图片下载成功！"+url.toString());
					}
					
					else {Bitmap=null; Log.v("Potho", "图片下失败！"+url.toString());}
					
					
					/*/ 下载图片到指定位置
					is = url.openStream();
					String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/img/01.jpg";
					File file = new File(path);
					if (!file.getParentFile().exists())
						file.getParentFile().mkdirs();
					FileOutputStream out = new FileOutputStream(file);
					int len = 0;
					byte[] buf = new byte[1024];
					
					while ((len = is.read(buf)) > 0) {
						out.write(buf, 0, len);// 把buf的内容从第1个开始，写len个长度到out文件里面

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
	
    /* 蒋团圆
     * 方法作用：通过一个指定的Url 下载到指定文件夹
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
						 InputStream in = url.openStream();//打开图片资源
						 
						 File file=new File(LoadImgPath);
						 Log.v("filepath=", file.toString()+"");
						 Log.v("fileexists", file.getParentFile().exists()+"");
					
						   if(!file.getParentFile().mkdirs())
						   {
						     Log.v("ImgDoload", LoadImgPath+"=不存在,系统自动创建!");
						     Log.v("ImgDoload", "图片下载中..");

						     
						 	 FileOutputStream out=new FileOutputStream(file);
						 
						 	 
						 	 int len=0;
							 byte[] buf=new byte[1024];
							 while((len=in.read(buf))>0)
								   out.write(buf, 0, len);
							 Log.v("ImgDoload", "图片下载成功");
							 
							 
							 out.close();
							 in.close();
							
							  Message msg=new Message();
							  msg.what=4;
							  msg.obj="图片下载成功";
							  handler.sendMessage(msg);
						
						   }
						   
					    else  {  
					    	

							 int size = 0;
							 FileInputStream fis =new FileInputStream(file);
							 size = fis.available();
							 fis.close();
							 
							 if(!(size==conn.getContentLength()))
							 {
								 Log.v("ImgDoload", "图片大小不一致，重新下载!");
								 FileOutputStream out=new FileOutputStream(file);
								 
							 	 
							 	 int len=0;
								 byte[] buf=new byte[1024];
								 while((len=in.read(buf))>0)
									   out.write(buf, 0, len);
								 Log.v("ImgDoload", "下载成功");
								 
								  Message msg=new Message();
								  msg.what=4;
								  msg.obj="图片下载成功";
								  handler.sendMessage(msg);
								  
								 out.close();
								 in.close();
								 
							 }
							 
							 else {
					    	
					    	  Message msg=new Message();
							  msg.what=3;
							  msg.obj="图片存在!而且大小一致!不下载!";
							  handler.sendMessage(msg);
							 }
							 
					    }	 
						   
					}  else  {  
						  Message msg=new Message();
						  msg.what=3;
						  msg.obj="所指定图片打不开或者不存在!";
						  
						  handler.sendMessage(msg);
						  Log.v("ImgDoload", "打开网站失败!");   }	 
				
				} catch (MalformedURLException e) {
				
					e.printStackTrace();
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				
				
				
			}	
		}.start();;
		
		
	}	
	
	
}
