package com.example.xsjx;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.internet.AccessInternet;
import com.example.internet.NetConn;
import com.example.utils.NoDoubleClickListener;
import com.example.utils.SharedUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import okio.*;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/*
 * ʱ�䣺2017.9.18
 * ���ߣ�����Բ
 * ���ã���½��������Ӧ��Activity
 * */
public class LogoActivity extends Activity {
	EditText username;
	EditText userpwd;
	Button logobtn;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_logo);
		initState();
		// ��Ϣ����
		sendNotification();
		
		username = (EditText) findViewById(R.id.logo_username);
		userpwd = (EditText) findViewById(R.id.logo_userpwd);
		logobtn = (Button) findViewById(R.id.LogDo);
/*		setEditTextInhibitInputSpace(username);
		setEditTextInhibitInputSpace(userpwd);
		
		setEditTextInhibitInputSpeChat(username);
		setEditTextInhibitInputSpeChat(userpwd);
		*/
		
		logobtn.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
			
				final String namestr = username.getText().toString().replace(" ", "");
				final String pwdstr = userpwd.getText().toString().replace(" ", "");
				if (namestr != null && !namestr.equals("") && pwdstr != null && !pwdstr.equals("")) {
					dialog = ProgressDialog.show(LogoActivity.this, "", "���ڵ�½...\n���Ե�!");
					
					if(NetConn.isNetworkAvailable(getApplication())){
					AccessInternet.LogoDo(handler, namestr, pwdstr);
					}
					else {Message msg = new Message();
					msg.what = 1;
					msg.obj = "����������!����������������!";
					handler.sendMessage(msg); }

				} else
					Toast.makeText(LogoActivity.this, "�û��������벻��Ϊ��!", 1).show();

			}
		});

		
	}
	
	private void sendNotification() {
		Intent mainIntent = new Intent(this, EnrollActivity.class);
		PendingIntent mainPendingIntent = PendingIntent.getActivity(this, 0, mainIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		// ��ȡNotificationManagerʵ��
		NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		// ʵ����NotificationCompat.Builde�������������
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
				// ����Сͼ��
				.setSmallIcon(R.drawable.xsjxlog)
				// ����֪ͨ����
				.setContentTitle("������Ϣ!")
				// ����֪ͨ����
				.setContentText("���Ľ��������ظ�ԤԼ��Ϣ��...").setContentIntent(mainPendingIntent)
				.setWhen(System.currentTimeMillis());
		// ����֪ͨʱ�䣬Ĭ��Ϊϵͳ����֪ͨ��ʱ�䣬ͨ����������
		// .setWhen(System.currentTimeMillis());
		// ͨ��builder.build()��������Notification����,������֪ͨ,id=1
		notifyManager.notify(1, builder.build());

	}
	
	/**
	 * ��ֹ�ı�������ո�
	 */
	public void setEditTextInhibitInputSpace(EditText editText){
		InputFilter filter=new InputFilter(){

			@Override
			public CharSequence filter(CharSequence arg0, int arg1, int arg2, Spanned arg3, int arg4, int arg5) {
				
				
				if(arg0.equals(" "))
				 {
					 return ""; 
				 }
				 else return null;	
			}};

			editText.setFilters(new InputFilter[]{filter});    
		
			editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
    }
/**
 * ��ֹ�ı������������ַ�
 */
	   public  void setEditTextInhibitInputSpeChat(EditText editText){

	        InputFilter filter=new InputFilter() {
	            @Override
	            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
	                String speChat="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~��@#��%����&*��������+|{}������������������������]";
	                Pattern pattern = Pattern.compile(speChat);
	                Matcher matcher = pattern.matcher(source.toString());
	                if(matcher.find())
	                	return "";
	                else return null;
	            }
	        };
	      
	     
	        editText.setFilters(new InputFilter[]{filter});
	        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(12)});
	    }	
	
	
	
	
	

	/**
	 * �ر�dialog����
	 */
	public void closeDialog(final String eorr) {
		// 4����ת����ҳ��
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				dialog.dismiss();
				
				Message msg = new Message();
				msg.what = 2;
				msg.obj = eorr;
				handler.sendMessage(msg);
			}
		}, 2000);

	}

	// handler ����
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				Intent mainIntent = new Intent(LogoActivity.this, MainActivity.class);
				LogoActivity.this.startActivity(mainIntent);
				LogoActivity.this.finish();

				break;
			case 1:
				closeDialog(msg.obj + "");
				break;
			case 2:
				Toast.makeText(LogoActivity.this, msg.obj + "", 1).show();
				break;

			}
		}

	};

	public void MyOnClick(View view) {
		switch (view.getId()) {
		case R.id.registration:
			Intent ReIntent = new Intent(LogoActivity.this, RegistrationActivity.class);
			LogoActivity.this.startActivity(ReIntent);
			LogoActivity.this.finish();
			break;
		case R.id.Logo_ForgetPwd:
			Builder bu1 = new Builder(this);
			bu1.setTitle("�������룿");
			
			bu1.setMessage("1.��Я��ע���ֻ����롢���֤����ʤ��У���������ǰ̨��Ϊ���һ�! \n\n 2.����ע���ֻ����벦��ͷ�����(88554818)!\n\n");
			bu1.setNegativeButton("ȡ��", null);
			bu1.setPositiveButton("����", new OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					String phone = "15074667947";
					Intent intent = new Intent(Intent.ACTION_DIAL);
					Uri data = Uri.parse("tel:" + phone);
					intent.setData(data);
					startActivity(intent);
					Toast.makeText(LogoActivity.this, "�����˺���", 1).show();
				}
			});
			bu1.create();
			bu1.show();
			break;
		}
	}

	// ����״̬��
	@SuppressLint("InlinedApi")
	private void initState() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// ͸��״̬��
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// ͸��������
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}

}
