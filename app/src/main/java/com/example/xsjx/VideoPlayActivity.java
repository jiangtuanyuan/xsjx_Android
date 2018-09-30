package com.example.xsjx;

import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import java.io.File;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.util.FileUtil;
import org.xutils.http.RequestParams;

import com.example.base.Base2Activity;
import com.example.entity.Video;
import com.example.utils.FileUtils;
import com.example.utils.Filepath;
import com.example.utils.ToastUtil;
import com.example.utils.Utils;
import com.maning.mnvideoplayerlibrary.listener.OnCompletionListener;
import com.maning.mnvideoplayerlibrary.listener.OnNetChangeListener;
import com.maning.mnvideoplayerlibrary.listener.OnScreenOrientationListener;
import com.maning.mnvideoplayerlibrary.player.MNViderPlayer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * 
 * @Description:   TODO 
 * @ClassName:     VideoPlayActivity.java
 * @author          蒋团圆
 * @version         V1.0  
 * @Date           2018年1月4日 下午5:18:44
 */
public class VideoPlayActivity extends Activity {
	private VideoView video;// 视频容器
	// private ProgressBar pBar;// 进度条
	private MediaController mMediaCtrl;
	private ImageButton playBtn;// 图片播放按钮

	private ImageView bgimg;// 视频背景图片
	private TextView videoName, ksyq, ppbz, czff, zysx;// 介绍内容。。。
	private Button fanhui;// 返回
	private Video v;

	MNViderPlayer mnVideoplayer;
	LinearLayout li;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_video_play);
		initState();
		initView();
		initData();
		initListener();
	}


	private void initView() {
		getdata();// 获取传递过来的数据
		bgimg = (ImageView) findViewById(R.id.video_image);
		videoName = (TextView) findViewById(R.id.video_name_text);
		fanhui = (Button) findViewById(R.id.videoplay_fanhui);
		ksyq = (TextView) findViewById(R.id.video_play_ksyq);
		ppbz = (TextView) findViewById(R.id.video_play_ppbz);
		czff = (TextView) findViewById(R.id.video_play_czff);
		zysx = (TextView) findViewById(R.id.video_play_zysx);

		// --分割线
		video = (VideoView) findViewById(R.id.video_play_view);
		// pBar = (ProgressBar) findViewById(R.id.video_play_Pbar);
		playBtn = (ImageButton) findViewById(R.id.video_play_btn);

		mnVideoplayer=(MNViderPlayer)findViewById(R.id.mn_videoplayer);
		li=(LinearLayout)findViewById(R.id.li);
		requestPermission();
		initVideo();
	}

	private void initData() {
		x.image().bind(bgimg, v.imgurl, Utils.Ximage());// 加载图片
		videoName.setText(v.name);
		ksyq.setText(v.ksyq.replace("\\n", "\n"));
		ppbz.setText(v.ppbz.replace("\\n", "\n"));
		czff.setText(v.czff.replace("\\n", "\n"));
		zysx.setText(v.zysx.replace("\\n", "\n"));
		// 返回

		// mc
		mMediaCtrl = new MediaController(this);
		mMediaCtrl.setAnchorView(video);
		mMediaCtrl.setKeepScreenOn(true);
		mMediaCtrl.setMediaPlayer(video);

		video.setMediaController(mMediaCtrl);

	}

	private void initListener() {
		fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				VideoPlayActivity.this.finish();
				// setVideoViewLayoutParams(1);
			}
		});

		// 播放按钮事件
		playBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				downloadName = Filepath.Video + "/" + Utils.isUrlFileName(v.url);
				if (FileUtils.isFileExist(downloadName)) {
					startVideo();
				} else {

					showpopo();
				}
				// startVideo();

			}
		});

	}

	private void requestPermission() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			if (!Settings.System.canWrite(this)) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setTitle("提示");
				builder.setMessage("视频播放调节亮度需要申请权限");
				builder.setNegativeButton("取消", null);
				builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
								Uri.parse("package:" + getPackageName()));
						intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivityForResult(intent, 100);
					}
				});
				builder.show();
			}
		}
	}
      //-------------
	String TAG="VideoPlayActivity";
	private void initVideo() {
		//初始化相关参数(必须放在Play前面)
		mnVideoplayer.setWidthAndHeightProportion(16, 9);   //设置宽高比
		mnVideoplayer.setIsNeedBatteryListen(true);         //设置电量监听
		mnVideoplayer.setIsNeedNetChangeListen(true);//设置网络监听
		//第一次进来先设置数据
		Log.e(TAG,"URL="+v.url+":name="+v.name);
		mnVideoplayer.setDataSource(v.url, v.name);
		//播放完成监听
		mnVideoplayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mediaPlayer) {

			}
		});

		//网络监听
		mnVideoplayer.setOnNetChangeListener(new OnNetChangeListener() {
			@Override
			public void onWifi(MediaPlayer mediaPlayer) {
			}

			@Override
			public void onMobile(MediaPlayer mediaPlayer) {
				ToastUtil.showToast("请注意,当前网络状态切换为3G/4G网络");
			}

			@Override
			public void onNoAvailable(MediaPlayer mediaPlayer) {
				ToastUtil.showToast("当前网络不可用,检查网络设置");
			}
		});

		mnVideoplayer.setOnScreenOrientationListener(new OnScreenOrientationListener() {
			@Override
			public void orientation_landscape() {
				ToastUtil.showToast("横屏");
//                btnBack.setVisibility(View.GONE);
				li.setVisibility(View.GONE);

			}

			@Override
			public void orientation_portrait() {
				ToastUtil.showToast("竖屏");
//                btnBack.setVisibility(View.VISIBLE);
				li.setVisibility(View.VISIBLE);
			}
		});
	}

	@Override
	public void onBackPressed() {
		if (mnVideoplayer.isFullScreen()) {
			mnVideoplayer.setOrientationPortrait();
			return;
		}
		super.onBackPressed();
	}
	//退出销毁
	@Override
	protected void onDestroy() {
		//一定要记得销毁View
		if (mnVideoplayer != null) {
			mnVideoplayer.destroyVideo();
			mnVideoplayer = null;
		}
		super.onDestroy();
	}
	//---------------

	public void startVideo() {
		
		playBtn.setVisibility(View.GONE); // 隐藏控件
		videoName.setVisibility(View.GONE);// 隐藏控件
		bgimg.setVisibility(View.GONE);

		// video.setVideoURI(Uri.parse(v.url));
		video.setVideoPath(downloadName);
		video.requestFocus();
		video.setOnPreparedListener(new OnPreparedListener() {

			@Override
			public void onPrepared(MediaPlayer arg0) {

			}
		});
		video.setOnErrorListener(new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer arg0, int arg1, int arg2) {

				// 跳转到网页播放 应该是网页文件 如果出错 就跳转到网页试试播放
				Intent webintent = new Intent(VideoPlayActivity.this, WebActivity.class);
				webintent.putExtra("Url", v.url);
				webintent.putExtra("Webname", "第三方视频");
				startActivity(webintent);

				video.setVisibility(View.GONE);
				playBtn.setVisibility(View.VISIBLE); // 隐藏控件
				videoName.setVisibility(View.VISIBLE);// 隐藏控件
				bgimg.setVisibility(View.VISIBLE);

				// VideoPlayActivity.this.finish();

				return true;
			}
		});

		video.setVisibility(View.VISIBLE);
		video.start();

	}

	// 获取传递过来的值
	public void getdata() {
		v= new Video();
		
		v.id = getIntent().getIntExtra("id", 1);
		v.name = getIntent().getStringExtra("name");// 视频名称
		v.imgurl = getIntent().getStringExtra("img");// 图片地址
		v.url = getIntent().getStringExtra("url");// 视频地址
		v.ksyq = getIntent().getStringExtra("ksyq");// 考试要求
		v.ppbz = getIntent().getStringExtra("ppbz");// 评判标准
		v.czff = getIntent().getStringExtra("czff");// 操作方法
		v.zysx = getIntent().getStringExtra("zysx");// 注意事项
		
		//对Android 7.0 系统进行判断
		
	}

	// 全屏模式的设置
	public void setVideoViewLayoutParams(int paramsType) {
		// 全屏模式
		if (1 == paramsType) {
			// 设置充满整个父布局
			RelativeLayout.LayoutParams LayoutParams = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			// 设置相对于父布局四边对齐
			LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			// 为VideoView添加属性
			video.setLayoutParams(LayoutParams);
		} else {
			// 窗口模式
			// 获取整个屏幕的宽高
			DisplayMetrics DisplayMetrics = new DisplayMetrics();
			this.getWindowManager().getDefaultDisplay().getMetrics(DisplayMetrics);
			// 设置窗口模式距离边框50
			int videoHeight = DisplayMetrics.heightPixels - 50;
			int videoWidth = DisplayMetrics.widthPixels - 50;
			RelativeLayout.LayoutParams LayoutParams = new RelativeLayout.LayoutParams(videoWidth, videoHeight);
			// 设置居中
			LayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			// 为VideoView添加属性
			video.setLayoutParams(LayoutParams);
		}
	}

	/**
	 * 下载视频
	 */
	ProgressDialog progressDialog;
	String downloadName;

	public void downloadvideo() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("下载中..");
		progressDialog.setCancelable(false);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// STYLE_HORIZONTAL
		progressDialog.setIndeterminate(false);
		progressDialog.show();

		
		RequestParams params = new RequestParams(v.url);
		params.setSaveFilePath(downloadName);
		params.setAutoRename(true);
		x.http().post(params, new Callback.ProgressCallback<File>() {
			@Override
			public void onCancelled(CancelledException arg0) {

			}

			@Override
			public void onError(Throwable arg0, boolean arg1) {

			}

			@Override
			public void onFinished() {

				progressDialog.cancel();
			}

			@Override
			public void onSuccess(File arg0) {
				// 下载成功
				progressDialog.cancel();
				startVideo();
			}

			@Override
			public void onLoading(long total, long current, boolean arg2) {

				// 当前进度和文件总大小
				progressDialog.setMax((int) total);
				progressDialog.setProgress((int) current);

			}

			@Override
			public void onStarted() {

			}

			@Override
			public void onWaiting() {
				// 网络开始之前回调

			}

		});
	}

	Builder bu1 = null;

	public void showpopo() {
		
		if (bu1 == null) {
			bu1 = new Builder(VideoPlayActivity.this);
			bu1.setTitle("提示:");
			bu1.setMessage("是否下载:" + v.name + "?\n\n(下载完成以后,再次观看将不会消耗您的流量!)");
			bu1.setNegativeButton("不了", null);
			bu1.setPositiveButton("下载", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {

					downloadvideo();
				}
			});
			bu1.create();
		}

		bu1.show();
	}

	Builder b2 = null;

	public void closeVideoPlay(String msg) {
		if (b2 == null) {
			b2 = new Builder(this);
			b2.setTitle("提示:");
			b2.setCancelable(false);
		}
		b2.setMessage(msg);
		b2.setPositiveButton("关闭", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				VideoPlayActivity.this.finish();
			}
		});
		b2.create();

		b2.show();

	}

	// 监听返回事件
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			VideoPlayActivity.this.finish();

		}
		return super.onKeyDown(keyCode, event);
	}

	int intPositionWhenPause = -1;

	/**
	 * 页面暂停效果处理
	 */
	@Override
	protected void onPause() {
		super.onPause();
		// 如果当前页面暂停则保存当前播放位置，全局变量保存
		intPositionWhenPause = video.getCurrentPosition();
		// 停止回放视频文件
		video.stopPlayback();
	}

	/**
	 * 页面从暂停中恢复
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// 跳转到暂停时保存的位置
		if (intPositionWhenPause >= 0) {
			video.seekTo(intPositionWhenPause);
			// 初始播放位置
			intPositionWhenPause = -1;
		}
	}
	public void initState() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}

}
