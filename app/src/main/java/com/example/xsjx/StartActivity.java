package com.example.xsjx;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.VpAdapter;
import com.example.utils.SharedUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/*
 * 时间：2017.9.18
 * 作者：蒋团圆
 * 作用：引导页对对应的Activity  第一次运行时候启动的
 * */
public class StartActivity extends Activity {

	public ViewPager vp;
	public RadioGroup rg;
	public Button btn;
	boolean flag = true;
	public int i = 3;
	// 保存在viewpager中滑动的view
	public List<View> list = new ArrayList<View>();
	public int[] img = new int[] { R.drawable.yingdao1, R.drawable.yingdao2, R.drawable.yingdao3 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		 setContentView(R.layout.activity_start);
		 initState();
		if (SharedUtils.getInstance().getInt("start", 0) == 0) {
			SharedUtils.getInstance().putInt("start", 1);
			findView();

		} else {
			Intent intent = new Intent(this, LogoActivity.class);
			startActivity(intent);
			this.finish();
		}

	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				if (msg.arg1 < 0) {
					flag = false;
					Intent intent = new Intent(StartActivity.this, LogoDoActivity.class);
					startActivity(intent);
					StartActivity.this.finish();
				} else
					btn.setText("    开启星胜驾校之门 (" + msg.arg1 + "s)    ");

			}

		}

	};

	@SuppressWarnings("deprecation")
	public void findView() {
		rg = (RadioGroup) findViewById(R.id.rg);
		vp = (ViewPager) findViewById(R.id.vp);
		intiView();
		VpAdapter adapter = new VpAdapter(this, list);

		vp.setAdapter(adapter);
		vp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				setPosition(arg0);
				if (arg0 == 3) {
					startTime();
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	@SuppressLint("InflateParams")
	public void intiView() {
		LayoutInflater inflater = LayoutInflater.from(this);
		View vp01 = inflater.inflate(R.layout.vp_01, null);
		vp01.setBackgroundResource(img[0]);

		View vp02 = inflater.inflate(R.layout.vp_01, null);
		vp02.setBackgroundResource(img[1]);

		View vp03 = inflater.inflate(R.layout.vp_01, null);
		vp03.setBackgroundResource(img[2]);

		View vp04 = inflater.inflate(R.layout.vp_2, null);
		btn = (Button) vp04.findViewById(R.id.btn_start);
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent(StartActivity.this, LogoDoActivity.class);
				startActivity(intent);
				StartActivity.this.finish();
			     i=-1;flag=false;
			}
		});

		list.add(vp01);
		list.add(vp02);
		list.add(vp03);
		list.add(vp04);
	}

	public void setPosition(int position) {
		RadioButton rb = (RadioButton) rg.getChildAt(position);
		rb.setChecked(true);
	}

	public void startTime() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (flag) {

						Message msg = new Message();
						msg.what = 1;
						msg.arg1 = i;
						handler.sendMessage(msg);
						i--;
						Thread.sleep(1000);
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}).start();
	}
	//隐藏状态栏
		@SuppressLint("InlinedApi")
		private void initState() {
	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
	            //透明状态栏
	           getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
	            //透明导航栏
	            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
	        }
	    }
}
