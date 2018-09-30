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
 * @author          ����Բ
 * @version         V1.0  
 * @Date           2018��1��4�� ����5:18:44
 */
public class VideoPlayActivity extends Activity {
	private VideoView video;// ��Ƶ����
	// private ProgressBar pBar;// ������
	private MediaController mMediaCtrl;
	private ImageButton playBtn;// ͼƬ���Ű�ť

	private ImageView bgimg;// ��Ƶ����ͼƬ
	private TextView videoName, ksyq, ppbz, czff, zysx;// �������ݡ�����
	private Button fanhui;// ����
	private Video v;

	MNViderPlayer mnVideoplayer;
	LinearLayout li;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��������
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_video_play);
		initState();
		initView();
		initData();
		initListener();
	}


	private void initView() {
		getdata();// ��ȡ���ݹ���������
		bgimg = (ImageView) findViewById(R.id.video_image);
		videoName = (TextView) findViewById(R.id.video_name_text);
		fanhui = (Button) findViewById(R.id.videoplay_fanhui);
		ksyq = (TextView) findViewById(R.id.video_play_ksyq);
		ppbz = (TextView) findViewById(R.id.video_play_ppbz);
		czff = (TextView) findViewById(R.id.video_play_czff);
		zysx = (TextView) findViewById(R.id.video_play_zysx);

		// --�ָ���
		video = (VideoView) findViewById(R.id.video_play_view);
		// pBar = (ProgressBar) findViewById(R.id.video_play_Pbar);
		playBtn = (ImageButton) findViewById(R.id.video_play_btn);

		mnVideoplayer=(MNViderPlayer)findViewById(R.id.mn_videoplayer);
		li=(LinearLayout)findViewById(R.id.li);
		requestPermission();
		initVideo();
	}

	private void initData() {
		x.image().bind(bgimg, v.imgurl, Utils.Ximage());// ����ͼƬ
		videoName.setText(v.name);
		ksyq.setText(v.ksyq.replace("\\n", "\n"));
		ppbz.setText(v.ppbz.replace("\\n", "\n"));
		czff.setText(v.czff.replace("\\n", "\n"));
		zysx.setText(v.zysx.replace("\\n", "\n"));
		// ����

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

		// ���Ű�ť�¼�
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
				builder.setTitle("��ʾ");
				builder.setMessage("��Ƶ���ŵ���������Ҫ����Ȩ��");
				builder.setNegativeButton("ȡ��", null);
				builder.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
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
		//��ʼ����ز���(�������Playǰ��)
		mnVideoplayer.setWidthAndHeightProportion(16, 9);   //���ÿ�߱�
		mnVideoplayer.setIsNeedBatteryListen(true);         //���õ�������
		mnVideoplayer.setIsNeedNetChangeListen(true);//�����������
		//��һ�ν�������������
		Log.e(TAG,"URL="+v.url+":name="+v.name);
		mnVideoplayer.setDataSource(v.url, v.name);
		//������ɼ���
		mnVideoplayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mediaPlayer) {

			}
		});

		//�������
		mnVideoplayer.setOnNetChangeListener(new OnNetChangeListener() {
			@Override
			public void onWifi(MediaPlayer mediaPlayer) {
			}

			@Override
			public void onMobile(MediaPlayer mediaPlayer) {
				ToastUtil.showToast("��ע��,��ǰ����״̬�л�Ϊ3G/4G����");
			}

			@Override
			public void onNoAvailable(MediaPlayer mediaPlayer) {
				ToastUtil.showToast("��ǰ���粻����,�����������");
			}
		});

		mnVideoplayer.setOnScreenOrientationListener(new OnScreenOrientationListener() {
			@Override
			public void orientation_landscape() {
				ToastUtil.showToast("����");
//                btnBack.setVisibility(View.GONE);
				li.setVisibility(View.GONE);

			}

			@Override
			public void orientation_portrait() {
				ToastUtil.showToast("����");
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
	//�˳�����
	@Override
	protected void onDestroy() {
		//һ��Ҫ�ǵ�����View
		if (mnVideoplayer != null) {
			mnVideoplayer.destroyVideo();
			mnVideoplayer = null;
		}
		super.onDestroy();
	}
	//---------------

	public void startVideo() {
		
		playBtn.setVisibility(View.GONE); // ���ؿؼ�
		videoName.setVisibility(View.GONE);// ���ؿؼ�
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

				// ��ת����ҳ���� Ӧ������ҳ�ļ� ������� ����ת����ҳ���Բ���
				Intent webintent = new Intent(VideoPlayActivity.this, WebActivity.class);
				webintent.putExtra("Url", v.url);
				webintent.putExtra("Webname", "��������Ƶ");
				startActivity(webintent);

				video.setVisibility(View.GONE);
				playBtn.setVisibility(View.VISIBLE); // ���ؿؼ�
				videoName.setVisibility(View.VISIBLE);// ���ؿؼ�
				bgimg.setVisibility(View.VISIBLE);

				// VideoPlayActivity.this.finish();

				return true;
			}
		});

		video.setVisibility(View.VISIBLE);
		video.start();

	}

	// ��ȡ���ݹ�����ֵ
	public void getdata() {
		v= new Video();
		
		v.id = getIntent().getIntExtra("id", 1);
		v.name = getIntent().getStringExtra("name");// ��Ƶ����
		v.imgurl = getIntent().getStringExtra("img");// ͼƬ��ַ
		v.url = getIntent().getStringExtra("url");// ��Ƶ��ַ
		v.ksyq = getIntent().getStringExtra("ksyq");// ����Ҫ��
		v.ppbz = getIntent().getStringExtra("ppbz");// ���б�׼
		v.czff = getIntent().getStringExtra("czff");// ��������
		v.zysx = getIntent().getStringExtra("zysx");// ע������
		
		//��Android 7.0 ϵͳ�����ж�
		
	}

	// ȫ��ģʽ������
	public void setVideoViewLayoutParams(int paramsType) {
		// ȫ��ģʽ
		if (1 == paramsType) {
			// ���ó�������������
			RelativeLayout.LayoutParams LayoutParams = new RelativeLayout.LayoutParams(
					RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
			// ��������ڸ������ı߶���
			LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
			LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
			LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			// ΪVideoView�������
			video.setLayoutParams(LayoutParams);
		} else {
			// ����ģʽ
			// ��ȡ������Ļ�Ŀ��
			DisplayMetrics DisplayMetrics = new DisplayMetrics();
			this.getWindowManager().getDefaultDisplay().getMetrics(DisplayMetrics);
			// ���ô���ģʽ����߿�50
			int videoHeight = DisplayMetrics.heightPixels - 50;
			int videoWidth = DisplayMetrics.widthPixels - 50;
			RelativeLayout.LayoutParams LayoutParams = new RelativeLayout.LayoutParams(videoWidth, videoHeight);
			// ���þ���
			LayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			// ΪVideoView�������
			video.setLayoutParams(LayoutParams);
		}
	}

	/**
	 * ������Ƶ
	 */
	ProgressDialog progressDialog;
	String downloadName;

	public void downloadvideo() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("������..");
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
				// ���سɹ�
				progressDialog.cancel();
				startVideo();
			}

			@Override
			public void onLoading(long total, long current, boolean arg2) {

				// ��ǰ���Ⱥ��ļ��ܴ�С
				progressDialog.setMax((int) total);
				progressDialog.setProgress((int) current);

			}

			@Override
			public void onStarted() {

			}

			@Override
			public void onWaiting() {
				// ���翪ʼ֮ǰ�ص�

			}

		});
	}

	Builder bu1 = null;

	public void showpopo() {
		
		if (bu1 == null) {
			bu1 = new Builder(VideoPlayActivity.this);
			bu1.setTitle("��ʾ:");
			bu1.setMessage("�Ƿ�����:" + v.name + "?\n\n(��������Ժ�,�ٴιۿ�������������������!)");
			bu1.setNegativeButton("����", null);
			bu1.setPositiveButton("����", new DialogInterface.OnClickListener() {
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
			b2.setTitle("��ʾ:");
			b2.setCancelable(false);
		}
		b2.setMessage(msg);
		b2.setPositiveButton("�ر�", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				VideoPlayActivity.this.finish();
			}
		});
		b2.create();

		b2.show();

	}

	// ���������¼�
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			VideoPlayActivity.this.finish();

		}
		return super.onKeyDown(keyCode, event);
	}

	int intPositionWhenPause = -1;

	/**
	 * ҳ����ͣЧ������
	 */
	@Override
	protected void onPause() {
		super.onPause();
		// �����ǰҳ����ͣ�򱣴浱ǰ����λ�ã�ȫ�ֱ�������
		intPositionWhenPause = video.getCurrentPosition();
		// ֹͣ�ط���Ƶ�ļ�
		video.stopPlayback();
	}

	/**
	 * ҳ�����ͣ�лָ�
	 */
	@Override
	protected void onResume() {
		super.onResume();
		// ��ת����ͣʱ�����λ��
		if (intPositionWhenPause >= 0) {
			video.seekTo(intPositionWhenPause);
			// ��ʼ����λ��
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
