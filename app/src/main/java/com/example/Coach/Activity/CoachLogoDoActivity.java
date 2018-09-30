package com.example.Coach.Activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import org.xutils.x;

import com.example.Coach.Utils.CoachInfo;
import com.example.base.Base2Activity;
import com.example.internet.AccessInternet;
import com.example.utils.EditTextClearTools;
import com.example.utils.NoDoubleClickListener;
import com.example.utils.SharedUtils;
import com.example.utils.Utils;
import com.example.xsjx.LogoDoActivity;
import com.example.xsjx.R;
import com.example.xsjx.RegistrationActivity;
import com.example.xsjx.R.layout;
import android.support.v4.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 教练登陆入口的Activity
 * 
 * @Date 2017年11月17日 下午4:02:46
 */
public class CoachLogoDoActivity extends Base2Activity {
	private EditText e1, e2;
	private ImageView m1, m2;
	private Button logo;
	private ImageView fanhui;
    private String Key="";
	@Override
	protected void initView() {
		setContentView(R.layout.activity_coach_logo_do);
		Key=getIntent().getStringExtra("Key");
		if(Key.length()!=32)
		{ 
			Utils.showToast(this, "未检测到教练信息,自动此关闭!");
			this.finish();
		}
		//else Utils.showToast(this, "Key="+Key);
	
		e1 = (EditText) findViewById(R.id.CoachLogoDo_CoachName);
		e2 = (EditText) findViewById(R.id.CoachLogoDo_CoachPwd);
		m1 = (ImageView) findViewById(R.id.CoachLogoDo_del_name);
		m2 = (ImageView) findViewById(R.id.CoachLogoDo_del_pwd);
		logo = (Button) findViewById(R.id.CoachLogoDo_loginButton);
		fanhui = (ImageView) findViewById(R.id.CoachLogoDo_fanhui);
	}

	@Override
	protected void initData() {
		EditTextClearTools.addclerListener(e1, e2, m1, logo);
		EditTextClearTools.addclerListener(e2, e1, m2, logo);	
	}

	@Override
	protected void initListener() {
		fanhui.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent ReIntent = new Intent(CoachLogoDoActivity.this, LogoDoActivity.class);
				CoachLogoDoActivity.this.startActivity(ReIntent);
				CoachLogoDoActivity.this.finish();
			}
		});
		
		logo.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
				String e1s=e1.getText().toString();
				String e2s=e2.getText().toString();
				if(!e1s.equals("") && e1s.length()>0 && !e2s.equals("") && e2s.length()>0){
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					if (imm != null) {
					    imm.hideSoftInputFromWindow(e2.getWindowToken(), 0);
					}
					showDialog("登陆中..");
					AccessInternet.LogoDoCoach(handler,e1s, e2s,Key);
				}
			}
		});	
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				dialog.dismiss();
				//Utils.showToast(CoachLogoDoActivity.this, "0==登陆成功==id"+CoachInfo.id);
				
				// 教练登陆 SharedUtils.getInstance().putInt("LogDoTypes",2);
				SharedUtils.getInstance().putString("CoachNmae",e1.getText().toString());
				SharedUtils.getInstance().putString("CoachPwd",e2.getText().toString());
				
				Intent intent=new Intent(CoachLogoDoActivity.this,CoachMainActivity.class);
				startActivity(intent);
				CoachLogoDoActivity.this.finish();
				
				break;
			case 1:
				dialog.dismiss();
				Utils.showToast(CoachLogoDoActivity.this, ""+msg.obj);
				break;
			default:
				break;
			}
		}
	};
	
	
	
	/**
	 * 显示加载框
	 */
	LayoutInflater inflatera = null;
	Dialog dialog=null;
	private void showDialog(String c) {
		if (inflatera == null)
			inflatera = LayoutInflater.from(this);
		if(dialog==null)
		{dialog = new AlertDialog.Builder(this).create();
		 dialog.setCancelable(false);}
		dialog.show();
		// 注意此处要放在show之后 否则会报异常
	    View v=inflatera.inflate(R.layout.loading_process_dialog_anim, null);
	    TextView stv=(TextView) v.findViewById(R.id.loading_dialog_tv);
	    stv.setText(c);
		dialog.setContentView(v);
	}
	
	
	// 返回用户登录界面
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent ReIntent = new Intent(CoachLogoDoActivity.this, LogoDoActivity.class);
			CoachLogoDoActivity.this.startActivity(ReIntent);
			CoachLogoDoActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
