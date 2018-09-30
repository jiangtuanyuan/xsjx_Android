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
 * ʱ�䣺2017.9.14
 * ���ߣ�����Բ
 * ���ã�ȫ�ֱ���
 * */


public class MainApplication extends Application {
public static Context baseContext;

public static String XSJX="http://www.jtyrl.cn/XSJX/";

public static String XSJX_jianjie="http://mp.weixin.qq.com/s/Z5PhN07acxzD9n2zIG0xzA";

public static String csXSJX="http://119.29.218.46/XSJX/";// 192.168.3.170:8080  http://192.168.43.109:8080/XSJX/
public static String XSJX_index=csXSJX+"index.jsp";//��ʤ������ҳ

public static String XSJX_Run_img=csXSJX+"XSJX_Run_Servlet";//����ҳ���ͼƬ�ӿ�
public static String XSJX_UPDATE_APK=csXSJX+"UpdateAPKServlet";//����APK�ӿ�
public static String XSJX_SchoolTopImgServlet=csXSJX+"SchoolTopImgServlet";//У���ֲ�ͼƬ�ӿ�
public static String XSJX_SchoolNewsServlet=csXSJX+"SchoolNewsServlet";//У������Ѷ�ӿ�
public static String XSJX_JzInfoServlet=csXSJX+"JzInfoServlet";//��ȡ����������Ϣ�͵绰����
public static String XSJX_UserJZServlet=csXSJX+"UserJZServlet";//��ȡ�û������ļ�������id
public static String XSJX_AddUserEnrolInfo=csXSJX+"AddUserEnrolInfo";//��ȡ�û������ļ�������id

public static String XSJX_Postingtity=csXSJX+"Postingtity";//��Ҫ����������û���Ϣ�ͷ���ģʽ

public static String XSJX_VideoAll=csXSJX+"VideoServlet";//��ȡ��Ƶ����

public static String XSJX_UpLoadPosting=csXSJX+"UpLoadPosting";//�����ӿ�
public static String XSJX_PostingServlet=csXSJX+"PostingServlet";//��÷����ӿ�
public static String XSJX_PostingComments=csXSJX+"PostingComments";//��÷������۽ӿ�
public static String XSJX_UpLoadPostingComments=csXSJX+"UpLoadPostingComments";//��������

public static String XSJX_GetStudentPosting=csXSJX+"GetStudentPosting";//��ȡĳ��ѧԱ����


public static String banquan="��ʤ��У v1.0";//@www.jtyrl.cn 2017-03-01
//�ֻ���Ļ�Ŀ�Ⱥ͸߶�
public static int phoneWidth;
public static int phoneHeight;
/*  -------------------------------   */

@Override
public void onCreate() {
	super.onCreate();
	baseContext=this;
	TestSD();// �����Ƿ���ڿɶ�д������SD��
	startXSJXService();//����Service����
    getPhoneWH();//��ȡ�ֻ���Ļ�ĸ߿�
    
    x.Ext.init(this);//Xutils��ʼ�� ����X���
    
    //����UIL��ͼƬ���
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
	  //����UIL��ͼƬ���
    
}





public void TestSD(){
	// �����Ƿ���ڿɶ�д������SD�� �ķ���
	String state;
	String path;
	state = Environment.getExternalStorageState();
	if(state.equals(Environment.MEDIA_MOUNTED)){
	   path = Environment.getExternalStorageDirectory().getAbsolutePath();
	   Log.v("MainAppLication SDPath", path);
	}
	
}

/**
 * ����ϵͳ�ļ���
 * @param filePath
 */
public static void newFile(String filePath){
	 File file=new File(filePath);
	 if(!file.exists())   
	 {  file.mkdir();          
	   Log.v("MainApplication-newFile", "�����ļ��У�"+filePath);
	 }
	 else   Log.v("MainApplication-newFile", "�ļ��У�"+filePath+"�Ѵ���!(������)");
    
	 /*
	 if(!file.getParentFile().exists())
	   {
		  file.getParentFile().mkdirs();
	      Log.v("MainApplication", "�����ļ��У�"+filePath);
	   }
	 else  Log.v("MainApplication", "�ļ��У�"+filePath+"�Ѵ���!(������)");*/
}
/**
 * ��������
 */

public void startXSJXService(){
	Intent service = new Intent(this, XsjxService.class);
	startService(service);
}

/**getMetrics ��������
��ȡ��ǰ��Ļ�Ŀ�� int   px 
MainApplication.getMetrics(MainApplication.baseContext).widthPixels
�߶�  .heightPixels;
float density = metric.density; ��Ļ�ܶȣ�0.75 / 1.0 / 1.5����
 ��Ļ�ܶ�DPI��120 / 160 / 240����metric.densityDpi;*/
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
