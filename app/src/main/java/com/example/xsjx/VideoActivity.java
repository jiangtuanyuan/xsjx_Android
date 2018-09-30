package com.example.xsjx;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.VideoAdapter;
import com.example.adapter.VpAdapter;
import com.example.base.Base2Activity;
import com.example.entity.Video;
import com.example.internet.AccessInternet;
import com.example.internet.NetConn;
import com.example.utils.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class VideoActivity extends Base2Activity {
	public ImageView fanhui;
	public TextView mark;
	public ViewPager vp;
	public LayoutInflater inflater;
	public List<View> vplist = new ArrayList<View>();
	public RadioGroup RG;
	private int[] rbitem = new int[] { R.id.Video_rb1, R.id.Video_rb2 };

	ProgressDialog dialog;

	private void showDialog() {
		dialog = ProgressDialog.show(this, "", "数据加载中..");
	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_video);
		showDialog();
		
		if (NetConn.isNetworkAvailable(this)) {
			AccessInternet.getVideoAll(handler);
			
			
		} else {
			Utils.showToast(this, "无法连接到互联网");
			dialog.dismiss();
		}

		fanhui = (ImageView) findViewById(R.id.Video_fanhui);
		mark = (TextView) findViewById(R.id.Video_tv_mark);
		vp = (ViewPager) findViewById(R.id.Video_vp);
		RG = (RadioGroup) findViewById(R.id.Video_rg);
	}

	int width;
	int vpmoren = 0;
	View video1, video2;

	@SuppressWarnings("deprecation")
	@Override
	protected void initData() {
		fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				VideoActivity.this.finish();
			}
		});

		width = MainApplication.phoneWidth;
		LayoutParams layout = new LayoutParams(width / 2, 10);
		mark.setLayoutParams(layout);

		inflater = LayoutInflater.from(this);

		video1 = inflater.inflate(R.layout.video_1, null);

		video2 = inflater.inflate(R.layout.video_2, null);
		
		
		
		vplist.add(video1);
		vplist.add(video2);
		
		
		
		vp.setAdapter(new VpAdapter(this, vplist));

		vp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				RadioButton rb = (RadioButton) RG.getChildAt(arg0);
				rb.setChecked(true);

				Log.v("vp", "vp=" + arg0);
				if (arg0 == vpmoren) {
					setAnim(3);
					vpmoren = 2;
				}
				if (arg0 == 1)
					setAnim(0);
				else
					setAnim(1);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});

	}

	@Override
	protected void initListener() {
		RG.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				for (int i = 0; i < rbitem.length; i++) {
					if (rbitem[i] == arg1) {
						vp.setCurrentItem(i);
					}
				}
			}
		});

	}

	// 设置平移动画
	public void setAnim(int index) {
		switch (index) {
		case 0:
			//
			TranslateAnimation animation = new TranslateAnimation(0, width / 2, 0, 0);
			animation.setFillAfter(true);
			animation.setDuration(700);
			mark.startAnimation(animation);
			break;
		case 1:
			TranslateAnimation animation2 = new TranslateAnimation(width / 2, 0, 0, 0);
			animation2.setFillAfter(true);
			animation2.setDuration(700);

			mark.startAnimation(animation2);
			break;
		case 3:
			break;
		}
	}

	Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				dialog.dismiss();
				// 设置科目二 科目三的视频数据
				list.clear();
				list.addAll((List<Video>) msg.obj);

				if(list.size()==0)
				{
				Video1SR.setRefreshing(false);
				Video3SR.setRefreshing(false);
				
				Utils.showToast(VideoActivity.this, "暂无视频数据!");
				}else{
				
					setVideo2inidata(video1);
					setVideo3inidata(video2);
				Video1SR.setRefreshing(false);
				Video3SR.setRefreshing(false);
				}
				
			    //video1adapter.notifyDataSetChanged();
				//video3adapter.notifyDataSetChanged();
				
				if (srtf)
					Utils.showToast(VideoActivity.this, "数据加载完毕!");

				break;
			case 1:
				dialog.dismiss();
				
				Utils.showToast(VideoActivity.this, "" + msg.obj + "");
				
				
				
				break;
			case 3:
				// 下拉刷新视频数据
				AccessInternet.getVideoAll(handler);
				srtf = true;
				break;
			default:
				break;
			}
		}

	};

	/**
	 * 设置科目二科目三的List数据
	 */
	private void setke2ke3data() {
		v2list.clear();
		v3list.clear();
		for (Video v : list) {
			if (v.ke == 2) {
				v2list.add(v);
			}

			if (v.ke == 3)
				v3list.add(v);
		}

	}

	List<Video> list = new ArrayList<Video>();
	List<Video> v2list = new ArrayList<Video>();

	SwipeRefreshLayout Video1SR;
	boolean srtf = false;
	ListView Video1List;
	VideoAdapter video1adapter;

	// 科目二的视频列表
	public void setVideo2inidata(View v) {
		Video1SR = (SwipeRefreshLayout) v.findViewById(R.id.videt_1_swipeLayout);
		
		Video1List = (ListView) v.findViewById(R.id.videt_1_lv);
		video1adapter = new VideoAdapter(this, v2list);
		Video1List.setAdapter(video1adapter);
		
		setke2ke3data();
		video1adapter.notifyDataSetChanged();

		Video1SR.setColorSchemeColors(Color.BLACK, Color.BLUE);
		// 设置手指在屏幕下拉多少距离会触发下拉刷新
		Video1SR.setDistanceToTriggerSync(300);
		// 设定下拉圆圈的背景
		Video1SR.setProgressBackgroundColorSchemeColor(Color.WHITE);

		// 设置圆圈的大小
		Video1SR.setSize(SwipeRefreshLayout.LARGE);

		Video1SR.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {

				handler.sendEmptyMessageDelayed(3, 2000);

			}
		});

	}

	List<Video> v3list = new ArrayList<Video>();
	SwipeRefreshLayout Video3SR;
	boolean srtf3 = false;
	ListView Video3List;
	VideoAdapter video3adapter;

	// 科目三视频数据列表
	public void setVideo3inidata(View v) {
		Video3SR = (SwipeRefreshLayout) v.findViewById(R.id.videt_2_swipeLayout);
		Video3List = (ListView) v.findViewById(R.id.videt_2_lv);

		video3adapter = new VideoAdapter(this, v3list);
		Video3List.setAdapter(video3adapter);
		
		setke2ke3data();
		video3adapter.notifyDataSetChanged();

		Video3SR.setColorSchemeColors(Color.BLACK,Color.BLUE);
		// 设置手指在屏幕下拉多少距离会触发下拉刷新
		Video3SR.setDistanceToTriggerSync(300);
		// 设定下拉圆圈的背景
		Video3SR.setProgressBackgroundColorSchemeColor(Color.WHITE);
		// 设置圆圈的大小
		Video3SR.setSize(SwipeRefreshLayout.LARGE);

		Video3SR.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {

				handler.sendEmptyMessageDelayed(3, 2000);

			}
		});
	}
}
