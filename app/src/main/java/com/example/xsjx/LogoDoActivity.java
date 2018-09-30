package com.example.xsjx;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.Coach.Activity.CoachLogoDoActivity;
import com.example.Coach.Activity.CoachMainActivity;
import com.example.Coach.Utils.CoachInfo;
import com.example.internet.AccessInternet;
import com.example.internet.NetConn;
import com.example.service.XsjxService;
import com.example.utils.EditTextClearTools;
import com.example.utils.Filepath;
import com.example.utils.NoDoubleClickListener;
import com.example.utils.SharedUtils;
import com.example.utils.UserInfo;
import com.example.utils.Utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources.Theme;
import android.graphics.drawable.AnimationDrawable;
import android.hardware.camera2.CameraCharacteristics.Key;
import android.net.Uri;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SearchEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.accessibility.AccessibilityEvent;
import android.view.Window;
import android.view.WindowManager;
import android.view.ActionMode.Callback;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LogoDoActivity extends Activity {

	// login_button_selector
	EditText username, userpwd;
	ImageView m1, m2,portrait;
	TextView banquan, zhuce, wangji,LogoDO_Coach;
	Button logo;
	ProgressDialog dialog;
	CheckBox checkbox;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_logo_do);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		if(Build.VERSION.SDK_INT >= 23)
			CheckStoeragePermission();




		/*
	    //启动循环播放的动画
		portrait=(ImageView) findViewById(R.id.portrait);
		portrait.setImageResource(R.anim.animation_list);
	    AnimationDrawable animationDrawable1 = (AnimationDrawable) portrait.getDrawable();
	    animationDrawable1.start();
	  */

		/**
		 * 判断保存的是学员登陆还是教练登陆
		 */
		int LogDoTypes=SharedUtils.getInstance().getInt("LogDoTypes");

		if(LogDoTypes==2){

			Intent intent=new Intent(LogoDoActivity.this,CoachMainActivity.class);
			startActivity(intent);
			LogoDoActivity.this.finish();
		}

		else if(LogDoTypes>0 && LogDoTypes<7)
		{
			//学员
			//判断本地是否有 账号密码的缓存  如果有 就自动登陆   否则就到主界面
			String usernamestr=SharedUtils.getInstance().getString("username");
			String userpwdstr=SharedUtils.getInstance().getString("userpwd");

			if(usernamestr!=null && !usernamestr.equals("") && userpwdstr!=null && !userpwdstr.equals("") )
			{
				UserInfo.UserName=usernamestr;
				UserInfo.UserPwd=userpwdstr;

				// UserInfo.updateUserInfo();

				Intent mainIntent = new Intent(LogoDoActivity.this, MainActivity.class);
				LogoDoActivity.this.startActivity(mainIntent);
				LogoDoActivity.this.finish();

			}

		}
		init();


		/*

		//判断本地是否有 账号密码的缓存  如果有 就自动登陆   否则就到主界面
		String usernamestr=SharedUtils.getInstance().getString("username");
		String userpwdstr=SharedUtils.getInstance().getString("userpwd");

		if(usernamestr!=null && !usernamestr.equals("") && userpwdstr!=null && !userpwdstr.equals("") )
		{
			UserInfo.UserName=usernamestr;
	      	UserInfo.UserPwd=userpwdstr;

	        UserInfo.updateUserInfo();

	      	Intent mainIntent = new Intent(LogoDoActivity.this, MainActivity.class);
			LogoDoActivity.this.startActivity(mainIntent);
			LogoDoActivity.this.finish();

	       	}


		else{
		   if(Build.VERSION.SDK_INT >= 23)
			CheckStoeragePermission();

		    init();

		}*/
	}




	private void init() {
		checkbox=(CheckBox) findViewById(R.id.LogoDO_Box);

		LogoDO_Coach=(TextView) findViewById(R.id.LogoDO_Coach);
		LogoDO_Coach.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				showCoachPwdDialog();
				/*
				Intent ReIntent = new Intent(LogoDoActivity.this, CoachLogoDoActivity.class);
				LogoDoActivity.this.startActivity(ReIntent);
				LogoDoActivity.this.finish();*/
			}
		});

		logo = (Button) findViewById(R.id.loginButton);
		username = (EditText) findViewById(R.id.phonenumber);
		userpwd = (EditText) findViewById(R.id.password);
		m1 = (ImageView) findViewById(R.id.del_phonenumber);
		m2 = (ImageView) findViewById(R.id.del_password);
		// 添加绑定监听器
		EditTextClearTools.addclerListener(username,userpwd, m1, logo);
		EditTextClearTools.addclerListener(userpwd,username, m2, logo);

		banquan = (TextView) findViewById(R.id.LogoDO_banquan);
		banquan.setText(MainApplication.banquan);

		zhuce = (TextView) findViewById(R.id.logodo_zhuce);
		zhuce.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent ReIntent = new Intent(LogoDoActivity.this, RegistrationActivity.class);
				LogoDoActivity.this.startActivity(ReIntent);
				LogoDoActivity.this.finish();

			}
		});
		wangji = (TextView) findViewById(R.id.LogoDO_ForgetPwd);
		wangji.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showdialogwjmm();

			}
		});

		logo.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {

              /*跳过登陆 进入界面
				Intent mainIntent = new Intent(LogoDoActivity.this, MainActivity.class);
				LogoDoActivity.this.startActivity(mainIntent);
				LogoDoActivity.this.finish();
				*/

				//   这里注释的去掉就可以添加登陆验证
				final String namestr = username.getText().toString().replace(" ", "");
				final String pwdstr = userpwd.getText().toString();
				if (namestr != null && !namestr.equals("") && pwdstr != null && !pwdstr.equals("")) {
					// dialog = ProgressDialog.show(LogoDoActivity.this, "", "正在登陆...\n请稍等!");
					showDialog("正在登陆,请稍后!");

					if(isNamePwd(namestr,pwdstr)){
						if (NetConn.isNetworkAvailable(getApplication())) {
							AccessInternet.LogoDo(handler, namestr, pwdstr);
						} else {
							Message msg = new Message();
							msg.what = 1;
							msg.obj = "无网络连接!请检查您的网络设置!";
							handler.sendMessage(msg);
						}
					}
					else { Message msg = new Message();
						msg.what = 1;
						msg.obj = "请输入正确的用户名和密码!";
						handler.sendMessage(msg);}
				}


			}
		});

	}

	/**
	 * 显示加载框
	 */
	LayoutInflater inflatera = null;
	Dialog loaddialog=null;
	private void showDialog(String c) {
		if (inflatera == null)
			inflatera = LayoutInflater.from(this);
		if(loaddialog==null)
		{loaddialog = new AlertDialog.Builder(this).create();
			loaddialog.setCancelable(false);}
		loaddialog.show();
		// 注意此处要放在show之后 否则会报异常
		View v=inflatera.inflate(R.layout.loading_process_dialog_anim, null);
		TextView stv=(TextView) v.findViewById(R.id.loading_dialog_tv);
		stv.setText(c);
		// stv.setTextColor(ContextCompat.getColor(this,R.color.gary));

		loaddialog.setContentView(v);
	}


	/**
	 * 验证用户名和密码的正则表达式  如果不通过  直接显示用户名或者密码错误
	 */
	public boolean isNamePwd(String Name,String pwd){

		String PName="^[a-zA-Z0-9\u4e00-\u9fa5]+$";
		String PPwd="^[a-zA-Z0-9]+$";
		//先验证用户名是否通过正则表达式
		if (Name.length() > 0){
			Pattern pattern = Pattern.compile(PName);
			Matcher matcher = pattern.matcher(Name);

			if(matcher.matches())
			{
				if (pwd.length() <= 12 && pwd.length() >=6) {
					Pattern pattern2 = Pattern.compile(PPwd);
					Matcher matcher2 = pattern2.matcher(pwd);

					return matcher2.matches();
				}
				else return false;

			}
		}

		else return false;

		return false;
	}




	/**
	 * 显示忘记密码的方法
	 *
	 */
	Builder bu1=null;
	public void showdialogwjmm(){
		if(bu1==null){
			bu1= new Builder(LogoDoActivity.this);
			bu1.setTitle("忘记密码？");
			//bu1.setMessage("\n1.请携带注册手机号码、身份证到星胜驾校南院服务大厅，前台将为您找回! \n \n 2.请用注册手机号码拨打客服热线:0731-88082822");
			bu1.setMessage("暂时不支持密码找回功能!如有需要,请添加Q330844495!");

			bu1.setNegativeButton("确定", null);
		/*bu1.setPositiveButton("拨打", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				String phone = "0731-88082822";
				Intent intent = new Intent(Intent.ACTION_DIAL);
				Uri data = Uri.parse("tel:" + phone);
				intent.setData(data);
				startActivity(intent);
			}
		});
		*/
			bu1.create();
		}

		bu1.show();

	}

	// handler 创建
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case 0:


					if(checkbox.isChecked())
					{
						//如果选中 记住密码  就把密码账户写入到本地缓存
						SharedUtils.getInstance().putString("username",username.getText().toString());
						SharedUtils.getInstance().putString("userpwd",userpwd.getText().toString());

					}

					//	UserInfo.updateUserInfo();


					Intent mainIntent = new Intent(LogoDoActivity.this, MainActivity.class);
					LogoDoActivity.this.startActivity(mainIntent);
					LogoDoActivity.this.finish();

					break;
				case 1:
					closeDialog(msg.obj + "");
					break;

				//错误
				case 2:
					Toast.makeText(LogoDoActivity.this, msg.obj + "", 1).show();
					//	dialog.dismiss();
					loaddialog.dismiss();
					break;
				//教练密钥 验证成功 成功接收
				case 3:
					//	dialog.dismiss();

					loaddialog.dismiss();

					Intent ReIntent = new Intent(LogoDoActivity.this, CoachLogoDoActivity.class);
					ReIntent.putExtra("Key",Keyet.getText().toString());
					LogoDoActivity.this.startActivity(ReIntent);
					LogoDoActivity.this.finish();


					break;

			}
		}

	};

	/**
	 * 关闭dialog方法
	 */
	public void closeDialog(final String eorr) {
		// 4秒跳转到主页面
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				//dialog.dismiss();

				loaddialog.dismiss();
				Message msg = new Message();
				msg.what = 2;
				msg.obj = eorr;
				handler.sendMessage(msg);
			}
		}, 2000);

	}

	/**
	 * 检查文件读写权限
	 */
	int CODE_FOR_WRITE_PERMISSION=0; //文件读写权限标识
	public void CheckStoeragePermission(){
		if(ActivityCompat.checkSelfPermission(LogoDoActivity.this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
		{
			//如果有文件读写权限 就创建文件夹
			MainApplication.newFile(Filepath.imgPath);//创建存放图片的文件夹
			MainApplication.newFile(Filepath.DownloadPath);//创建存文件下载的文件夹
			XsjxService.downloadRunImg();//启动服务中的开机动画下载
		}
		else {
			//如果没有  就询问用户
			ActivityCompat.requestPermissions(LogoDoActivity.this,
					new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},CODE_FOR_WRITE_PERMISSION);

		}

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode,permissions,grantResults);
		if(requestCode==CODE_FOR_WRITE_PERMISSION){
			if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
				//用户接受了权限
				MainApplication.newFile(Filepath.imgPath);//创建存放图片的文件夹
				MainApplication.newFile(Filepath.DownloadPath);//创建存文件下载的文件夹
				XsjxService.downloadRunImg();  //启动服务中的开机动画下载

			}else{
				//用户拒绝了权限申请

				if(!ActivityCompat.shouldShowRequestPermissionRationale(LogoDoActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE))
				{
					Toast.makeText(LogoDoActivity.this, "星胜驾校 需要您赋予储存读取权限!否则将无法正常工作!(请到权应用的权限管理开启相应权限!)", 1).show();
				}
			}

		}
	}
	//防止WindowLeaked 内存泄漏  Dialog
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (dialog != null) {
			dialog.dismiss();
		}
		if (loaddialog != null) {
			loaddialog.dismiss();
		}


	}

	/**
	 * 弹出输入教练密钥窗口
	 */
	private LayoutInflater inflater = null;
	private Dialog Dcoach = null;
	private View Vcoach = null;
	private EditText Keyet=null;
	private void showCoachPwdDialog() {
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
		//lp.height = MainApplication.phoneHeight;

		Dcoach.getWindow().setAttributes(lp);

		Keyet=(EditText) Vcoach.findViewById(R.id.logdo_coach_pwd_dialog_et);
		//
		//Keyet.setText("aeb837b0fb7036716e8a74fa33770899");

		Vcoach.findViewById(R.id.logdo_coach_pwd_dialog_quxiao).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Dcoach.dismiss();
			}
		});

		Vcoach.findViewById(R.id.logdo_coach_pwd_dialog_queding).setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {

				//handler.sendEmptyMessage(3);
				String etstr=Keyet.getText().toString();
				if(etstr.length()>0 && etstr.length()==32){
					if (NetConn.isNetworkAvailable(MainApplication.baseContext)){
						//dialog = ProgressDialog.show(LogoDoActivity.this, "", "验证中..");

						showDialog("验证中..");
						AccessInternet.getCoachKey(handler, etstr);
					}
					else{
						Utils.showToast(LogoDoActivity.this, "无网络访问!");
					}
				}
				else  Utils.showToast(LogoDoActivity.this, "请输入正确的密钥!");
			}
		});
	}
}
