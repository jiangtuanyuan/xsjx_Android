package com.example.xsjx;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import com.example.base.BaseActivity;
import com.example.entity.Fragment4_BBS_Entity;
import com.example.fragment.FragMent_1;
import com.example.fragment.FragMent_2;
import com.example.fragment.FragMent_3;
import com.example.fragment.FragMent_4;
import com.example.fragment.FragMent_5;
import com.example.internet.AccessInternet;
import com.example.internet.InternetDownload;
import com.example.internet.NetConn;
import com.example.json.entity.Posting;
import com.example.json.entity.UpdateApkEntity;
import com.example.service.XsjxService;
import com.example.utils.AndroidWorkaround;
import com.example.utils.Filepath;
import com.example.utils.SharedUtils;
import com.example.utils.UserInfo;
import com.example.utils.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap.Config;
import android.graphics.Paint;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

/**
 * ???2017.9.17 ?????????? ??????????????????Activity
 */

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity {
	public static String TAG = "MainActivity";
	// Fragement??????ID????
	public FragmentManager fm = getSupportFragmentManager();
	public RadioGroup rg;
	public Fragment[] fa = new Fragment[] { new FragMent_1(), new FragMent_2(), new FragMent_3(), new FragMent_4(),
			new FragMent_5() };
	public int[] itme = new int[] { R.id.rb_01, R.id.rb_02, R.id.rb_03, R.id.rb_04, R.id.rb_05 };
	// Fragement??????ID????
	// ???¦Ç??????????? ???
	private long mExitTime;

	// ???¦Ç???????????
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		// ?????????????????
		if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
			AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
		}

		// ????????
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// ???????
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// ?????????
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		
		XsjxService.downloadRunImg();//?????????§Ö??????????
		//XsjxService.updateUserInfo();
	  	
		getVerCodeAndNmae();
		
		String UserName=SharedUtils.getInstance().getString("username");
		String UserPwd=SharedUtils.getInstance().getString("userpwd");
		if(UserName!=null && !UserName.equals("") && UserPwd!=null && !UserPwd.equals("") ){
			showDialog();
			AccessInternet.LogoDo(new Handler(){
				@Override
				public void handleMessage(Message msg) {
					switch (msg.what) {
					case 0:
						((FragMent_5)fa[4]).LoadliongUserHeadimg();
						dialog.dismiss();
						AccessInternet.UpdateAPKServlet(handler);
						
						break;
                    case 1:
                    	dialog.dismiss();
                    	Toast.makeText(MainActivity.this,msg.obj+"", Toast.LENGTH_LONG).show();
                    	AccessInternet.UpdateAPKServlet(handler);
						break;
					default:
						dialog.dismiss();
						break;
					}
					
				}	
			}, UserName, UserPwd);
			
			
		}
		else {
			Utils.showToast(this,"¦Ä???????????????????!????????!");
			this.finish();
			
			Intent intent=new Intent(this,LogoDoActivity.class);
			startActivity(intent);
		}
		
		

		// Fragment??????????
		if (savedInstanceState != null) {
			for (int i = 0; i < fa.length; i++) {
				Fragment fragtag = fm.findFragmentByTag("tag" + i);
				if (fragtag != null) {
					fa[i] = fragtag;
				}
			}
		}

		findview();

	}

	/**
	 * ????????
	 */
	ProgressDialog dialog;

	private void showDialog() {
		dialog = ProgressDialog.show(this, "", "????????????...\n?????!");
		//closeDialog();
	}

	/**
	 * ???dialog????
	 */
	public void closeDialog() {
		// 4???????????? ???????????¡ã·Ú
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				dialog.dismiss();
				

			}
		}, 2100);

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		// super.onSaveInstanceState(outState);
		// ????????????????activity????fragment????
	}

	// ?§Ý???????????
	public void findview() {
		rg = (RadioGroup) findViewById(R.id.rg);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@SuppressLint("NewApi")
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				changFragment(arg1);
				Log.v("GR", "===" + arg1);
			}
		});
		// ?????Fragment
		FragmentTransaction ft = fm.beginTransaction();
		ft.add(R.id.Fragment, fa[0]);
		ft.add(R.id.Fragment, fa[1]);
		ft.add(R.id.Fragment, fa[2]);
		ft.add(R.id.Fragment, fa[3]);
		ft.add(R.id.Fragment, fa[4]);
		ft.show(fa[0]).hide(fa[1]).hide(fa[2]).hide(fa[3]).hide(fa[4]);
		ft.commit();
		// ?????Fragment
	}

	// ?§Ý?FragMent
	@SuppressLint("NewApi")
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

	// ?§Ý?FragMent
	// ?§Ý???????????

	/*
	 * §µ?? ?????WebActivity
	 * 
	 * 2017 10 25
	 * 
	 
	public void intentWebActity(String Url, String webname) {
		Intent webintent = new Intent(this, WebActivity.class);
		webintent.putExtra("Url", Url);
		webintent.putExtra("Webname", webname);
		startActivity(webintent);

	}
*/
	// ?????BBS???????
	public void intentBBS(Posting p) {

		Intent bbsintent = new Intent(this, BBSdetailsActivity.class);
   
		bbsintent.putExtra("PostingID", p.PostingID);
		bbsintent.putExtra("PostingTitle",p.PostingTitle);
		bbsintent.putExtra("PostingConten", p.PostingConten);
		bbsintent.putExtra("date", p.date);
		bbsintent.putExtra("identityID", p.identityID);
		bbsintent.putExtra("identity", p.identity);
		bbsintent.putExtra("typesID", p.typesID);
		bbsintent.putExtra("name", p.name);
		bbsintent.putExtra("headImg", p.headImg);
	
		bbsintent.putStringArrayListExtra("PostingImg", p.PostingImg);
		
		startActivity(bbsintent);

		
	}

	/**
	 * ??????????
	 */
	UpdateApkEntity apkEntity;// ????APK???????
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				dialog.dismiss();
				
				apkEntity = (UpdateApkEntity) msg.obj;
				updateAPKCode();
				break;
			case 1:
				dialog.dismiss();
				
				// Toast.makeText(MainActivity.this, "1="+msg.obj+"", 1).show();
				
				break;
			case 2:// ????????????? ???????????
				APKUpdate(msg.obj + "");

				break;
			default:
				break;
			}

		}
	};

	/**
	 * ?????????? ?§Ø?APP?? code??????????????
	 */
	public void updateAPKCode() {
		int upcode = Integer.parseInt(apkEntity.verCode);
		if (upcode > versionCode) { // Toast.makeText(MainActivity.this,"???????"+apkEntity.updateinfo,1).show();
			showUpdateAPK();
		}
		

	}

	/**
	 * ?????????APP??????
	 */
	public Dialog Dapk = null;
	public View Vapk = null;
	public LayoutInflater inflater = null;

	public void showUpdateAPK() {
		if (inflater == null)
			inflater = LayoutInflater.from(this);
		if (Dapk == null)
			Dapk = new Dialog(this, R.style.testDialog);
		if (Vapk == null)
			Vapk = inflater.inflate(R.layout.updateapk_layout, null);

		Dapk.setContentView(Vapk);
		// Dapk.setCanceledOnTouchOutside(false);
		Dapk.setCancelable(false);// ???????????¦Ë????????
		Dapk.show();

		TextView title = (TextView) Vapk.findViewById(R.id.updateapk_title);
		title.setText("?????¡ã·Ú(" + apkEntity.verName + ")");

		final TextView msg = (TextView) Vapk.findViewById(R.id.updateapk_msg);

		msg.setText("" + apkEntity.updateinfo.toString().replace("\\n", "\n"));
		// ???????
		Vapk.findViewById(R.id.updateapk_esc).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Dapk.dismiss();
			}
		});
		// ????????
		Vapk.findViewById(R.id.updateapk_ok).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// ???????????????§Õ???????
				if (ActivityCompat.checkSelfPermission(MainActivity.this,
						Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
					Dapk.dismiss();
					quanxiandownapk();

				} else {
					ActivityCompat.requestPermissions(MainActivity.this,
							new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE,
									Manifest.permission.READ_EXTERNAL_STORAGE },
							0);
				}
			}
		});

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
			@NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 0) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				//???????
				Dapk.dismiss();
				quanxiandownapk();
			} else {
				// ???????????????
				Message msg3 = new Message();
				msg3.what = 3;
				msg3.obj = "¦Ä??????,???????§Õ???!";
				pdHandler.sendMessage(msg3);

			}

}
	
	}
	
	// ????APK???¡ã? ???
	public void quanxiandownapk() {
		apkdownload(apkEntity.apkurl + apkEntity.apkname, Filepath.DownloadPath + "/" + apkEntity.apkname);
	}

	Handler pdHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				ShowPD();
				break;
			case 1:
				pd.setProgress(downLoadFileSize);
				break;
			case 2:
				pd.dismiss();
				APKUpdate(msg.obj + "");
				break;
			case 3:
				Toast.makeText(MainActivity.this, msg.obj + "", 1).show();

				if (Dapk != null)
					Dapk.dismiss();
				if (pd != null)
					pd.dismiss();
				break;
			default:
				break;
			}

		}
	};

	/**
	 * ????????????????
	 */
	ProgressDialog pd = null;
	int PDMAX = 0;
	int downLoadFileSize = 0;

	public void ShowPD() {
		if (pd == null)
			pd = new ProgressDialog(this);
		pd.setMax(PDMAX);
		pd.setTitle("??????..");
		pd.setCancelable(false);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// STYLE_HORIZONTAL
		pd.setIndeterminate(false);
		pd.show();
	}

	public void apkdownload(final String urlstr, final String downPath) {
		/**
		 * ??????????????? ??????????????????? ?????????
		 */
		new Thread() {
			@SuppressWarnings("resource")
			public void run() {
				try {
					URL url = new URL(urlstr);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setReadTimeout(8000);
					conn.setConnectTimeout(8000);

					if (conn.getResponseCode() == 200) {
						InputStream in = url.openStream();
						PDMAX = conn.getContentLength();
						// ?????????????????????
						pdHandler.sendEmptyMessage(0);

						File file = new File(downPath);
						// !file.getParentFile().mkdirs()
						if (!file.exists()) {

							file.getParentFile().mkdirs();
							FileOutputStream out = new FileOutputStream(file);
							int len = 0;
							byte[] buf = new byte[2048];

							while ((len = in.read(buf)) > 0) {
								out.write(buf, 0, len);
								downLoadFileSize += len;
								pdHandler.sendEmptyMessage(1);
								Thread.sleep(5);
							}
							Message msg1 = new Message();
							msg1.what = 2;
							msg1.obj = downPath;
							pdHandler.sendMessage(msg1);

							out.close();
							in.close();

						}

						else {
							/**
							 * ?????????? ?????????§³ ?§Ø????????????????APK????? ?????§³???
							 * ????????????? ??????????§³?????????? ???????§³????? ???????§Õ??? ????????
							 */

							int size = 0;
							FileInputStream fis = new FileInputStream(file);
							size = fis.available();
							fis.close();

							if (size == PDMAX) {
								Message msg1 = new Message();
								msg1.what = 2;
								msg1.obj = downPath;
								pdHandler.sendMessage(msg1);
							} else {

								FileOutputStream out = new FileOutputStream(file);
								int len = 0;
								byte[] buf = new byte[2048];
								while ((len = in.read(buf)) > 0) {
									out.write(buf, 0, len);
									downLoadFileSize += len;
									pdHandler.sendEmptyMessage(1);
									Thread.sleep(10);
								}
								out.close();
								in.close();

								Message msg1 = new Message();
								msg1.what = 2;
								msg1.obj = downPath;
								pdHandler.sendMessage(msg1);

							}

						}

					}

					else {

						Message msg3 = new Message();
						msg3.what = 3;
						msg3.obj = "???????,??????????!";
						pdHandler.sendMessage(msg3);

					}

				} catch (MalformedURLException e) {

					Message msg3 = new Message();
					msg3.what = 3;
					msg3.obj = "???????,APK?????????!";
					pdHandler.sendMessage(msg3);

					e.printStackTrace();
				} catch (IOException e) {
					Message msg3 = new Message();
					msg3.what = 3;
					msg3.obj = "???????,???????§Õ???!";
					pdHandler.sendMessage(msg3);

					e.printStackTrace();
				} catch (InterruptedException e) {
					Message msg3 = new Message();
					msg3.what = 3;
					msg3.obj = "???????,????????????!";
					pdHandler.sendMessage(msg3);
					e.printStackTrace();
				}

			}

		}.start();

	}

	/**
	 * ????????????
	 */
	public void APKUpdate(String updatePath) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(updatePath)), "application/vnd.android.package-archive");
		startActivity(intent);
	}

	/**
	 * 1.???¡ã·Ú?????????????? ??????????????????? ????????????????? 1.1 ?????????????????1.?????????¡ã·Ú
	 * ??????InternetDownload.DownloadAPK????????????? ????? ????????????? 1.2 ???????????????1.???????????
	 * ???????????????????? ???????????????? ?????????????????????
	 */

	/**
	 * ??????APP??????·Ú???????·Ú?????
	 */
	String versionName;
	
	int versionCode;
	public void getVerCodeAndNmae() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo pinfo = pm.getPackageInfo(getPackageName(), PackageManager.GET_CONFIGURATIONS);
			versionCode = pinfo.versionCode;
			versionName = pinfo.versionName;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * ??§Õ????????? ???????exit????
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			exit();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void exit() {
		if ((System.currentTimeMillis() - mExitTime) > 2000) {
			Toast.makeText(MainActivity.this, "?????¦É?????", 1).show();
			mExitTime = System.currentTimeMillis();
		} else {
			this.finish();
			System.exit(0);
		}
	}

}
