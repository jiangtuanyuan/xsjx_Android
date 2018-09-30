package com.example.xsjx;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import com.example.internet.AccessInternet;
import com.example.json.entity.Run_img_json_entity;
import com.example.service.XsjxService;
import com.example.utils.Filepath;
import com.example.utils.SharedUtils;
import com.example.utils.UrlGoImg;
import com.example.utils.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

/*
 * 时间：2017.9.18
 * 作者：蒋团圆
 * 作用：启动页的全屏图片所对应的Activity
 * */

@SuppressWarnings("unused")
public class RunActivity extends Activity implements OnClickListener{
	private final int SPLASH_DISPLAY_LENGHT = 3200; // 延迟三秒
	public static Bitmap Bitmap;
	public ImageView img;
    public Run_img_json_entity imgentity;
    
    View main;  
	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		    main = getLayoutInflater().inflate(R.layout.activity_run, null);  
	        main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);  
	        main.setOnClickListener(this);  
		 
	        
		    setContentView(R.layout.activity_run);
		    initState();
		    
		img = (ImageView) findViewById(R.id.Run_Img);


		/*
		String xRunimgurl=SharedUtils.getInstance().getString("XrunImgUrl");
		XsjxService.downloadRunImg();
		
		if(xRunimgurl!=null && !xRunimgurl.equals(""))
		x.image().bind(img, xRunimgurl,Utils.XRunimag());
		else{
			img.setImageResource(R.drawable.xsjx);
		}
		*/
	
	  
	  
		if (SharedUtils.getInstance().getInt("start", 0) == 0)
		     img.setImageResource(R.drawable.xsjx);
		else if(SharedUtils.getInstance().getString("RunImgName")!=null)
			 	{ 
	  Bitmap bitmap = BitmapFactory.decodeFile(Filepath.imgPath +"/"+SharedUtils.getInstance().getString("RunImgName"));
		     if(bitmap!=null)
	          img.setImageBitmap(bitmap);
		     else  {img.setImageResource(R.drawable.xsjx);SharedUtils.getInstance().putString("RunImgName", "");XsjxService.downloadRunImg();}
		     
			 	} else  img.setImageResource(R.drawable.xsjx);	
	  
	  
		
		
// 4秒跳转到主页面
 new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (SharedUtils.getInstance().getInt("start", 0) == 0) {
					
					SharedUtils.getInstance().putString("enroll","false");//报名界面弹出报名须知
					SharedUtils.getInstance().putInt("WebShowWinddos", 0);

					AccessInternet.APPDownloadInfoServlet();//调用服务器接口  做下载记录
					
					Intent mainIntent = new Intent(RunActivity.this, StartActivity.class);
					RunActivity.this.startActivity(mainIntent);
					RunActivity.this.finish();
				} else {
					Intent intent = new Intent(RunActivity.this, LogoDoActivity.class);
					startActivity(intent);
					RunActivity.this.finish();
				}
			}
		}, SPLASH_DISPLAY_LENGHT);

	}
	// URL url = new URL("http://www.jtyrl.cn/Training/images/"+filename);
	// Bitmap tBitmap = ThumbnailUtils.extractThumbnail(bitmap, 120, 120);
	
	// 隐藏状态栏
	@SuppressLint("InlinedApi")
	private void initState() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明导航栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}

		/**

		 * 隐藏pad底部虚拟键

		Window _window;
		_window = getWindow();

		WindowManager.LayoutParams params = _window.getAttributes();
		params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;
		_window.setAttributes(params);
		
		 */
		
	}
	
	
	
	@Override
	public void onClick(View v) {
		 int i = main.getSystemUiVisibility();  
	        if (i == View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) {//2  
	            main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);  
	        } else if (i == View.SYSTEM_UI_FLAG_VISIBLE) {//0  
	            main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);  
	        } else if (i == View.SYSTEM_UI_FLAG_LOW_PROFILE) {//1  
	            main.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);  
	        }  
	}

}
