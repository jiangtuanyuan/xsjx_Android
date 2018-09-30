package com.example.Coach.Activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.xutils.x;
import org.xutils.common.Callback;
import org.xutils.common.Callback.CancelledException;
import org.xutils.http.RequestParams;

import com.example.Coach.Utils.CoachInfo;
import com.example.Coach.Utils.DaySeasonDate;
import com.example.fragment.CoachMain_FragmentKe2;
import com.example.fragment.CoachMain_FragmentKe3;
import com.example.fragment.FragMent_1;
import com.example.fragment.FragMent_3;
import com.example.fragment.FragMent_4;
import com.example.internet.AccessInternet;
import com.example.internet.NetConn;
import com.example.utils.AndroidWorkaround;
import com.example.utils.FileUtils;
import com.example.utils.Filepath;
import com.example.utils.MarqueeTextView;
import com.example.utils.NoDoubleClickListener;
import com.example.utils.SharedUtils;
import com.example.utils.UserInfo;
import com.example.utils.Utils;
import com.example.xsjx.EnrollActivity;
import com.example.xsjx.LogoDoActivity;
import com.example.xsjx.MainApplication;
import com.example.xsjx.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class CoachMainActivity extends FragmentActivity {
	private long mExitTime = 3000;
	private FragmentManager fm = getSupportFragmentManager();
	private Fragment[] fa = new Fragment[] { new FragMent_1(), new CoachMain_FragmentKe2(), new CoachMain_FragmentKe3(),
			new FragMent_4(), new FragMent_3() };
	private int[] itme = new int[] { R.id.coach_main_RadioButton1, R.id.coach_main_RadioButton2,
			R.id.coach_main_RadioButton3, R.id.coach_main_RadioButton4, R.id.coach_main_RadioButton5 };
	private RadioGroup CoachRG;

	// �໬
	private static SlidingMenu menu;
	private TextView topLeftV;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// ֻ������
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);// ����ײ����ⰴ����ס������

		if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
			AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
		}

		setContentView(R.layout.activity_coach_main);
		// �ж��Ƿ��еײ����ⰴ��
		if (!Utils.checkDeviceHasNavigationBar(this)) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				setTranslucentStatus(true);
			}
			SystemBarTintManager tintManager = new SystemBarTintManager(this);
			tintManager.setStatusBarTintEnabled(true);
			tintManager.setNavigationBarTintEnabled(true);
			// �Զ�����ɫ
			tintManager.setTintColor(Color.parseColor("#12b7f5"));
		}

		if (savedInstanceState != null) {
			for (int i = 0; i < fa.length; i++) {
				Fragment fragtag = fm.findFragmentByTag("tag" + i);
				if (fragtag != null) {
					fa[i] = fragtag;
				}
			}
		}

		menu = new SlidingMenu(this);
		// ���ò໬�˵����� LEFT RIGHT LEFT_RIGHT
		menu.setMode(SlidingMenu.LEFT);
		// ���ô�����Ļ��ģʽTOUCHMODE_FULLSCREEN TOUCHMODE_MARGIN TOUCHMODE_NONE
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		// ���û����˵���ͼ�Ŀ��(һ�������Ļ�������)
		menu.setBehindOffset(300);
		// ���ý��뽥��Ч����ֵ
		menu.setFadeDegree(0.5f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		// Ϊ�໬�˵����ò��֣���ߣ�
		View leftV = getView(R.layout.coach_main_cehua_view);
		setCehuaView(leftV);
		menu.setMenu(leftV);

		showLoadDialog("���ݼ�����,�����Ժ�!");
		getVerCodeAndNmae();// ��ȡ��ǰ�汾�İ汾��
		CoachInfo.updateInfo(handler);
		initView();
		initData();
		initListener();
	}

	// ״̬��
	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	private void initView() {
		CoachRG = (RadioGroup) findViewById(R.id.coach_main_RadioGroup);
		topLeftV = (TextView) findViewById(R.id.coach_main_TopLeftV);
	}

	private void initData() {
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(R.id.coach_main_FrameLayout, fa[0]);
		ft.add(R.id.coach_main_FrameLayout, fa[1]);
		ft.add(R.id.coach_main_FrameLayout, fa[2]);
		ft.add(R.id.coach_main_FrameLayout, fa[3]);
		ft.add(R.id.coach_main_FrameLayout, fa[4]);
		ft.show(fa[0]).hide(fa[1]).hide(fa[2]).hide(fa[3]).hide(fa[4]);
		ft.commit();
		
		inflater = LayoutInflater.from(this);
	}

	private void initListener() {
		CoachRG.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				changFragment(arg1);
			}
		});
		topLeftV.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				menu.toggle();// ��������໬�˵�
			}
		});

	}

	public void changFragment(int id) {
		FragmentTransaction ft = fm.beginTransaction();
		for (int i = 0; i < itme.length; i++) {
			if (id == itme[i]) {
				ft.show(fa[i]);
			} else
				ft.hide(fa[i]);
		}
		ft.commit();
	}

	public View getView(int id) {
		View view = LayoutInflater.from(this).inflate(id, null);
		return view;
	}

	/**
	 * ���ò໬�˵�������
	 */

	private ImageView headimg;
	private TextView coachname, coachKe, GenXinMsg;
	private MarqueeTextView place, qianming;
	private LinearLayout GenXin;

	private void setCehuaView(View v) {
		// ͷ����
		headimg = (ImageView) v.findViewById(R.id.coach_main_cehua_head_image);
		// X��ܼ�������ͷ��
		headimg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//Utils.showToast(CoachMainActivity.this, "��ʱ��֧�ָ���ͷ��!");
				//1. �ȵ�������ͷ��ĵײ�������
				//Ȼ��ѡ�����ջ������
				//Ȼ���ϴ�
				showdHeadImgPopo();
				
			}
		});

		// ��������
		coachname = (TextView) v.findViewById(R.id.coach_main_cehua_head_coachname);
		// ����������Ŀ
		coachKe = (TextView) v.findViewById(R.id.coach_main_cehua_head_ke);

		// ����
		place = (MarqueeTextView) v.findViewById(R.id.coach_main_cehua_head_place);

		// ǩ�����
		qianming = (MarqueeTextView) v.findViewById(R.id.coach_main_cehua_head_coachtext);
		qianming.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {

				showAddBBS();

			}
		});
		// ���°汾����Ϣ
		GenXinMsg = (TextView) v.findViewById(R.id.coach_main_cehua_head_gengxinMsg);

		// �ҵ����ϵ��

		v.findViewById(R.id.coach_main_cehua_head_Myinfo).setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
				Utils.showToast(CoachMainActivity.this, "��ʱ�޷��鿴�ҵ�����!");
			}

		});

		// �ҵ�ѧԱ���
		v.findViewById(R.id.coach_main_cehua_head_Mystu).setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
				Utils.showToast(CoachMainActivity.this, "��ʱ�޷��鿴�ҵ�ѧԱ!");
			}

		});

		// ѧԱ���۵��
		v.findViewById(R.id.coach_main_cehua_head_Stupingjia).setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
				Utils.showToast(CoachMainActivity.this, "��ʱ�޷��鿴ѧԱ����!");
			}

		});

		v.findViewById(R.id.coach_main_cehua_head_thisdayorder).setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
				Utils.showToast(CoachMainActivity.this, "����ԤԼ���");
			}

		});
		
		// ���԰���
		v.findViewById(R.id.coach_main_cehua_head_Messageboard).setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
				Utils.showToast(CoachMainActivity.this, "��ʱ�޷��鿴���԰�!");
			}

		});

		// ���°汾��LinearLayout

		GenXin = (LinearLayout) v.findViewById(R.id.coach_main_cehua_head_gengxin);

		// �˳���¼���
		v.findViewById(R.id.coach_main_cehua_head_exit).setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {

				showFinshDialog();

			}
		});
	}
/**
 * ����ͷ��
 */
	private static final int CODE_GALLERY_REQUEST = 0xa0;// �����ѡ���ʶ
	private static final int CODE_RESULT_REQUEST = 0xa2;// ���ղü���Ľ�� �����ѡ���ʶ
	
	private static final int CODE_GALLERY_CLAP = 0xa3;// ���յ�ѡ���ʶ
	private static final int CODE_RESULT_CLAP  = 0xa4;// ���ղü���Ľ�� �����յı�ʶ
	
	private static int output_X = 100;
	private static int output_Y = 100;

	private Uri paizhaoName=null; //�������պ����ƬUri ֵ
	
	public Dialog DRunHead = null;
	public View VRunHead = null;
	public void showdHeadImgPopo() {
		if (DRunHead == null)
			DRunHead = new Dialog(this, R.style.ActionSheetDialogStyle);
		if (VRunHead == null)
			{VRunHead = inflater.inflate(R.layout.fragment_5_roundhead_dialog, null);
			 VRunHead.findViewById(R.id.fragment_5_roundhead_dialog_quxiao).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					DRunHead.dismiss();
				}
			});
			VRunHead.findViewById(R.id.fragment_5_roundhead_dialog_paizhao).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					/**
					 * ��һ��
					 */	
				
					File file = new File(Filepath.imgPath + "/" +System.currentTimeMillis()+ ".jpg");
					if (!file.getParentFile().exists()) {
						file.getParentFile().mkdirs();
					}
		            paizhaoName = Uri.fromFile(file);	
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, paizhaoName);
					startActivityForResult(intent,CODE_GALLERY_CLAP);


					DRunHead.dismiss();
				}
			});
			VRunHead.findViewById(R.id.fragment_5_roundhead_dialog_xiangce).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					Intent intentFromGallery = new Intent(Intent.ACTION_PICK, null);
					intentFromGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
					startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);

				}
			});
			
			}
		DRunHead.setContentView(VRunHead);
		Window dialogWindow = DRunHead.getWindow();
		dialogWindow.setGravity(Gravity.BOTTOM);
		DRunHead.show();
		WindowManager.LayoutParams lp = DRunHead.getWindow().getAttributes();
		lp.width = MainApplication.phoneWidth;
		DRunHead.getWindow().setAttributes(lp);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case CODE_GALLERY_REQUEST:// ��������Ա��ص����
			if (data != null) {
				cropRawPhoto(data.getData());// ֱ�Ӳü�ͼƬ
			}
			break;
		case CODE_RESULT_REQUEST:
			
			if (data != null) {
				setHeadImg(data);// ����ͼƬ��
				Log.e("data", "data!=null");
			}	
			break;
	 //�������������
		case CODE_GALLERY_CLAP:
			if(paizhaoName!=null)
			  cropRawclap(paizhaoName);
			
			break;
		case CODE_RESULT_CLAP:
			if (data != null) {
				setHeadImg(data);// ����ͼƬ��
			}
			break;
		}

		super.onActivityResult(requestCode, resultCode, data);
		
		
	}
	//�����������յ�ͼƬ����
			public void cropRawclap(Uri uri) {
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(uri, "image/*");
				intent.putExtra("crop", "true");
				// aspectX aspectY �ǿ�ߵı���
				intent.putExtra("aspectX", 1);
				intent.putExtra("aspectY", 1);
				// outputX outputY �ǲü�ͼƬ���
				intent.putExtra("outputX", output_X);
				intent.putExtra("outputY", output_Y);
				intent.putExtra("return-data", true);
				startActivityForResult(intent, CODE_RESULT_CLAP);
			}
	//�����������ѡ���ͼƬ����
		public void cropRawPhoto(Uri uri) {
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(uri, "image/*");
			intent.putExtra("crop", "true");
			// aspectX aspectY �ǿ�ߵı���
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			// outputX outputY �ǲü�ͼƬ���
			intent.putExtra("outputX", output_X);
			intent.putExtra("outputY", output_Y);
			intent.putExtra("return-data", true);
			startActivityForResult(intent, CODE_RESULT_REQUEST);
		}
		private void setHeadImg(Intent intent) {
			Bundle extras = intent.getExtras();
			if (extras != null) {
				Bitmap photo = extras.getParcelable("data");
				
				headimg.setImageBitmap(photo);

				try {
					
					ByteArrayOutputStream stream=new ByteArrayOutputStream();
					photo.compress(Bitmap.CompressFormat.JPEG, 90, stream);// 100����ѹ��
					byte[] byte_arr = stream.toByteArray();
					// Base64ͼƬת��ΪString
					String image = Base64.encodeToString(byte_arr, 0);
					stream.close();
	      // Log.e("head image", image);
					if (NetConn.isNetworkAvailable(this))
						{
						 String userimgname=System.currentTimeMillis()+"";
						 AccessInternet.uploadCoachimg(image,userimgname, CoachInfo.id+"");
						 Utils.showToast(this, "ͷ���޸ĳɹ�!");
					
						// x.image().bind(CiecleImg, MainApplication.csXSJX+"HeadPortraitImg/"+userimgname+".jpeg", Utils.Xheadimg());
						 
						 /**
						  * ����ͼƬ������ ����X��ܼ���
						  */
						
							File f = new File(Filepath.imgPath + "/" + userimgname + ".jpeg");

							String Webpath = f.getPath();
							
							Intent intent1 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
							Uri uri = Uri.fromFile(f);
							intent1.setData(uri);
							this.sendBroadcast(intent1);

							FileOutputStream out = null;
							try {
								// ������� ��ͼƬ���������ļ���
								out = new FileOutputStream(f);
								photo.compress(Bitmap.CompressFormat.PNG, 90, out);
								try {
									out.flush();
									out.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							}
							
						 x.image().bind(headimg,Webpath, Utils.XCoachHeadimg());
						 
						}
					else {
						Utils.showToast(this, "����������!ͼƬ�ϴ�������ʧ��!");
					}
					
					
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
			else {
				Utils.showToast(this, "�ϴ�ʧ��,����������~");
			}
			DRunHead.dismiss();
		}
		
	
	/**
	 * �鿴��ǰ�汾�ŵ�code
	 */

	int versionCode;

	public void getVerCodeAndNmae() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo pinfo = pm.getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS);
			versionCode = pinfo.versionCode;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

	}

	/**
	 * �����޸�ǩ���ĵ�����
	 * 
	 */

	private LayoutInflater inflater = null;
	private Dialog Dcoach = null;
	private View Vcoach = null;
	private EditText AddConten = null;
	private TextView AddnameTV = null;

	private void showAddBBS() {
		if (inflater == null)
			inflater = LayoutInflater.from(this);
		if (Dcoach == null)
			Dcoach = new Dialog(this, R.style.testDialog);
		if (Vcoach == null)
			Vcoach = inflater.inflate(R.layout.logdo_coach_pwd_dialog, null);

		Dcoach.setContentView(Vcoach);
		Dcoach.show();

		WindowManager.LayoutParams lp = Dcoach.getWindow().getAttributes();
		lp.width = MainApplication.phoneWidth;
		// lp.height = MainApplication.phoneHeight;

		Dcoach.getWindow().setAttributes(lp);

		if (AddnameTV == null)
			AddnameTV = (TextView) Vcoach.findViewById(R.id.logdo_coach_pwd_dialog_name);

		AddnameTV.setText("�޸�ǩ��");

		if (AddConten == null) {
			AddConten = (EditText) Vcoach.findViewById(R.id.logdo_coach_pwd_dialog_et);
			AddConten.setHint("д����ף����ѧԱ��!");
			AddConten.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_NORMAL);
			AddConten.setMaxLines(250);
			AddConten.setFilters(Utils.getInputFilter(this, 250));
		}

		AddConten.setText(qianming.getText().toString());

		Vcoach.findViewById(R.id.logdo_coach_pwd_dialog_quxiao).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Dcoach.dismiss();
			}
		});

		Vcoach.findViewById(R.id.logdo_coach_pwd_dialog_queding).setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {

				showLoadDialog("�޸���..");
				AccessInternet.updataCoachqianming(handler, CoachInfo.id, AddConten.getText().toString());

				Dcoach.dismiss();
			}
		});

	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			// �رռ��ؿ� ��ʾ��Ϣ���³ɹ�
			case 0:
				loaddialog.dismiss();// �رռ��ؿ�

				x.image().bind(headimg, CoachInfo.headimg, Utils.XCoachHeadimg());// ����ͷ��
				coachname.setText(CoachInfo.name);// ���ý�������
				// �����Ա�ͼ��
				if (CoachInfo.sex.equals("��")) {
					coachname.setCompoundDrawablesWithIntrinsicBounds(null, null,
							ContextCompat.getDrawable(CoachMainActivity.this, R.drawable.my_male), null);
				} else
					coachname.setCompoundDrawablesWithIntrinsicBounds(null, null,
							ContextCompat.getDrawable(CoachMainActivity.this, R.drawable.my_famale), null);
				// ���ÿ�Ŀ���Ľ���
				if (CoachInfo.ke == 2) {
					coachKe.setText("��Ŀ������");
				} else {
					coachKe.setText("��Ŀ������");
				}

				place.setText(CoachInfo.palce);
				qianming.setText(CoachInfo.qianming);

				// ���ø��°汾����Ϣ
				if (DaySeasonDate.apkEntity.verCode != null
						&& Integer.parseInt(DaySeasonDate.apkEntity.verCode) > versionCode) {
					GenXinMsg.setText("1");
					GenXinMsg.setVisibility(View.VISIBLE);
					GenXin.setOnClickListener(new NoDoubleClickListener() {
						@Override
						public void onNoDoubleClick(View v) {

							// ��������Apk�ķ��� �������֮��װ
							UpdateApkDiloag();
						}

					});
				} else {
					GenXin.setOnClickListener(new NoDoubleClickListener() {
						@Override
						public void onNoDoubleClick(View v) {
							Utils.showToast(CoachMainActivity.this, "��ǰΪ���°汾!�������!");

						}
					});
				}
				// Utils.showToast(CoachMainActivity.this,DaySeasonDate.seasonlist.size()+"==");

				((CoachMain_FragmentKe2) fa[1]).MainHandler();
				((CoachMain_FragmentKe3) fa[2]).MainHandler();

				break;

			case 1:
				loaddialog.dismiss();// �رռ��ؿ�
				Utils.showToast(CoachMainActivity.this, msg.obj + "");

				break;
			// �����޸�ǩ���ɹ���ʧ��
			case 2:
				loaddialog.dismiss();// �رռ��ؿ�
				Utils.showToast(CoachMainActivity.this, "�޸ĳɹ�!");
				CoachInfo.qianming = AddConten.getText().toString();
				qianming.setText(CoachInfo.qianming);

				break;
			case 3:
				loaddialog.dismiss();// �رռ��ؿ�
				Utils.showToast(CoachMainActivity.this, msg.obj + "");
				break;
			default:
				break;
			}

		}
	};
	/**
	 * �����Ƿ�����apk�ĵ�����
	 */
	Builder bu11 = null;

	public void UpdateApkDiloag() {
		downloadName = Filepath.DownloadPath + "/" + DaySeasonDate.apkEntity.apkname;
		if (bu11 == null) {
			bu11 = new Builder(this);
			bu11.setTitle("��ʾ:");
			bu11.setMessage("�Ƿ����?");
			bu11.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
				}
			});

			bu11.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {

					if (FileUtils.isFileExist(downloadName)) {
						APKUpdate(downloadName);
					} else
						downloadApk();

				}
			});
			bu11.create();
		}
		bu11.show();

	}

	/**
	 * ����apk
	 */
	ProgressDialog progressDialog;
	String apkurl, downloadName;

	private void downloadApk() {
		apkurl = DaySeasonDate.apkEntity.apkurl + DaySeasonDate.apkEntity.apkname;
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("������..");
		progressDialog.setCancelable(false);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// STYLE_HORIZONTAL
		progressDialog.setIndeterminate(false);
		progressDialog.show();
		RequestParams params = new RequestParams(apkurl);

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
				// ���ð�װAPk�ķ���
				APKUpdate(downloadName);
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

	/**
	 * �Զ�����װ����
	 */
	public void APKUpdate(String updatePath) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(updatePath)), "application/vnd.android.package-archive");
		startActivity(intent);
	}

	/**
	 * ��ʾ���ؿ�
	 */
	LayoutInflater inflatera = null;
	Dialog loaddialog = null;

	private void showLoadDialog(String c) {
		if (inflatera == null)
			inflatera = LayoutInflater.from(this);
		if (loaddialog == null) {
			loaddialog = new Builder(this).create();
			loaddialog.setCancelable(false);
		}
		loaddialog.show();
		// ע��˴�Ҫ����show֮�� ����ᱨ�쳣
		View v = inflatera.inflate(R.layout.loading_process_dialog_anim, null);
		TextView stv = (TextView) v.findViewById(R.id.loading_dialog_tv);
		stv.setText(c);
		// stv.setTextColor(ContextCompat.getColor(this,R.color.gary));

		loaddialog.setContentView(v);
	}

	/**
	 * ��ʾ�Ƿ�ע����½�ĶԻ���
	 * 
	 */
	Builder bu1 = null;

	public void showFinshDialog() {
		if (bu11 == null) {
			bu11 = new Builder(this);
			bu11.setTitle("��ʾ:");
			bu11.setMessage("\n�Ƿ��˳���½?\n");
			bu11.setNegativeButton("ȡ��", null);
			bu11.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {

					SharedUtils.getInstance().putInt("LogDoTypes", 0);// �����Ϣ��½���
					SharedUtils.getInstance().putInt("LogDoTypes", 0);

					Intent intent = new Intent(CoachMainActivity.this, LogoDoActivity.class);
					startActivity(intent);
					CoachMainActivity.this.finish();
				}
			});
			bu11.create();
		}
		bu11.show();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// super.onSaveInstanceState(outState);
		// ��ֹ����״̬
	}

	/**
	 * ��дϵͳ���ط��� �Լ�����exit���� 2018��1��27�� ����11:40:28
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			exit();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * �˳�����
	 * 
	 * @Date 2018��1��27�� ����11:40:28
	 */
	private void exit() {
		if ((System.currentTimeMillis() - mExitTime) > 2000) {
			// �������һ�η��ؼ� �����˵�Ҳ����ʾ ��ô�ùر� ���� Toast
			if (menu.isMenuShowing()) {
				menu.toggle();
			}
			Toast.makeText(CoachMainActivity.this, "�ٰ�һ���˳�����", 1).show();
			mExitTime = System.currentTimeMillis();
		} else {
			this.finish();
			System.exit(0);
		}
	}

}
