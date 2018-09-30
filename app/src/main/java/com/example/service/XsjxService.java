package com.example.service;

import com.example.internet.AccessInternet;

import com.example.json.entity.Run_img_json_entity;
import com.example.utils.Filepath;
import com.example.utils.SharedUtils;
import com.example.utils.UrlGoImg;
import com.example.utils.UserInfo;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class XsjxService extends Service {
	public static Run_img_json_entity imgentity;
	private static final String TAG = "XsjxService";

	@Override
	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "XsjxService 服务启动 -onCreate()");
	
		/*获得JSon
		 * this.stopSelf(); Log.v("RunImg_service", "关闭");
		 */
	}

	public static void downloadRunImg() {
		Log.v(TAG, "XsjxService-downloadRunImg()");
		AccessInternet.XSJXRunServlet(Imghandler);
	}

	public static void downloadAPK() {
		Log.v(TAG, "XsjxService-downloadAPK()");

	}
	public static void updateUserInfo(){
		Log.v(TAG, "XsjxService-updateUserInfo()");
	 	//UserInfo.updateUserInfo();//更新用户信息
	}
	
	static Handler Imghandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				imgentity = (Run_img_json_entity) msg.obj;
				if (imgentity.Error_code == 0) {
					if (SharedUtils.getInstance().getString("RunImgName").equals(imgentity.ImgName)) {
						SharedUtils.getInstance().putString("RunImgName", imgentity.ImgName);
					} else {
						Log.v("RunImg_service", "下载图片");
						UrlGoImg.ImgDoload(imgentity.UrlStr + imgentity.ImgName,
								Filepath.imgPath + "/" + imgentity.ImgName, Imghandler);
					}
				}
				break;
			case 1:
				Log.v("RunImg_service", "Imghandler=" + msg.obj);
				break;
			case 3:
				Log.v("RunImg_service", "Imghandler=" + msg.obj);
				break;
			case 4:
				SharedUtils.getInstance().putString("RunImgName", imgentity.ImgName);
				Log.v("RunImg_service", "Imghandler=" + msg.obj);
				break;
			default:
				break;
			}
		}
	};

	
	@Override
	public void onStart(Intent intent, int startId) {
		Log.i(TAG, "XsjxService-onStart()");
		super.onStart(intent, startId);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		Log.i(TAG, "XsjxService-onBind()");
		return null;
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "ExampleService-onDestroy()");
		super.onDestroy();
	}

}
