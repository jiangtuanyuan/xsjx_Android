package com.example.xsjx;

import java.io.File;

import org.xutils.x;

import com.example.service.XsjxService;
import com.example.utils.Filepath;
import com.example.utils.SharedUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;


/**
 * 时间：2017.9.14
 * 作者：蒋团圆
 * 作用：全局变量
 * */


public class MainApplication extends Application {
public static Context baseContext;

public static String XSJX="http://www.jtyrl.cn/XSJX/";

public static String XSJX_jianjie="http://mp.weixin.qq.com/s/Z5PhN07acxzD9n2zIG0xzA";

public static String csXSJX="http://119.29.218.46/XSJX/";// 192.168.3.170:8080  http://192.168.43.109:8080/XSJX/
public static String XSJX_index=csXSJX+"index.jsp";//星胜官网主页

public static String XSJX_Run_img=csXSJX+"XSJX_Run_Servlet";//启动页面的图片接口
public static String XSJX_UPDATE_APK=csXSJX+"UpdateAPKServlet";//更新APK接口
public static String XSJX_SchoolTopImgServlet=csXSJX+"SchoolTopImgServlet";//校内轮播图片接口
public static String XSJX_SchoolNewsServlet=csXSJX+"SchoolNewsServlet";//校新闻资讯接口
public static String XSJX_JzInfoServlet=csXSJX+"JzInfoServlet";//获取报名驾照信息和电话号码
public static String XSJX_UserJZServlet=csXSJX+"UserJZServlet";//获取用户报名的驾照类型id
public static String XSJX_AddUserEnrolInfo=csXSJX+"AddUserEnrolInfo";//获取用户报名的驾照类型id

public static String XSJX_Postingtity=csXSJX+"Postingtity";//我要发帖界面的用户信息和发帖模式

public static String XSJX_VideoAll=csXSJX+"VideoServlet";//获取视频数据

public static String XSJX_UpLoadPosting=csXSJX+"UpLoadPosting";//发帖接口
public static String XSJX_PostingServlet=csXSJX+"PostingServlet";//获得发帖接口
public static String XSJX_PostingComments=csXSJX+"PostingComments";//获得发帖评论接口
public static String XSJX_UpLoadPostingComments=csXSJX+"UpLoadPostingComments";//发表评论

public static String XSJX_GetStudentPosting=csXSJX+"GetStudentPosting";//获取某个学员发帖


public static String banquan="星胜驾校 v1.0";//@www.jtyrl.cn 2017-03-01
//手机屏幕的宽度和高度
public static int phoneWidth;
public static int phoneHeight;
/*  -------------------------------   */

@Override
public void onCreate() {
	super.onCreate();
	baseContext=this;
	TestSD();// 测试是否存在可读写的内置SD卡
	startXSJXService();//启动Service服务
    getPhoneWH();//获取手机屏幕的高宽
    
    x.Ext.init(this);//Xutils初始化 加载X框架
    
    //配置UIL的图片框架
    DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
			.showImageForEmptyUri(R.drawable.imgurlno)
			.showImageOnFail(R.drawable.imgurlno)
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.build();
	ImageLoaderConfiguration config = new ImageLoaderConfiguration
	.Builder(getApplicationContext())//
			.defaultDisplayImageOptions(defaultOptions)//
			.discCacheSize(50 * 1024 * 1024)//
			.discCacheFileCount(100)
			.writeDebugLogs()//
			.build();//
	ImageLoader.getInstance().init(config);
	  //配置UIL的图片框架
    
}





public void TestSD(){
	// 测试是否存在可读写的内置SD卡 的方法
	String state;
	String path;
	state = Environment.getExternalStorageState();
	if(state.equals(Environment.MEDIA_MOUNTED)){
	   path = Environment.getExternalStorageDirectory().getAbsolutePath();
	   Log.v("MainAppLication SDPath", path);
	}
	
}

/**
 * 创建系统文件夹
 * @param filePath
 */
public static void newFile(String filePath){
	 File file=new File(filePath);
	 if(!file.exists())   
	 {  file.mkdir();          
	   Log.v("MainApplication-newFile", "创建文件夹："+filePath);
	 }
	 else   Log.v("MainApplication-newFile", "文件夹："+filePath+"已存在!(不创建)");
    
	 /*
	 if(!file.getParentFile().exists())
	   {
		  file.getParentFile().mkdirs();
	      Log.v("MainApplication", "创建文件夹："+filePath);
	   }
	 else  Log.v("MainApplication", "文件夹："+filePath+"已存在!(不创建)");*/
}
/**
 * 启动服务
 */

public void startXSJXService(){
	Intent service = new Intent(this, XsjxService.class);
	startService(service);
}

/**getMetrics 方法作用
获取当前屏幕的宽度 int   px 
MainApplication.getMetrics(MainApplication.baseContext).widthPixels
高度  .heightPixels;
float density = metric.density; 屏幕密度（0.75 / 1.0 / 1.5）：
 屏幕密度DPI（120 / 160 / 240）：metric.densityDpi;*/
public static DisplayMetrics getMetrics(Context context) {
    DisplayMetrics metrics = new DisplayMetrics();
    WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    manager.getDefaultDisplay().getMetrics(metrics);
    return metrics;
}

@SuppressWarnings("unused")
private void getPhoneWH(){
	phoneWidth=(int)getMetrics(baseContext).widthPixels;
	phoneHeight=(int)getMetrics(baseContext).heightPixels;
}

	
}
