package com.example.xsjx;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.internet.AccessInternet;
import com.example.internet.NetConn;
import com.example.utils.EditTextClearTools;
import com.example.utils.NoDoubleClickListener;
import com.example.utils.NoOnFocusChangeListener;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

/*
 * ʱ�䣺2017.9.18
 * ���ߣ�����Բ
 * ���ã�ע���������Ӧ��Activity
 * */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class RegistrationActivity extends Activity {

	private TextView banquan;
	private ImageView fanhui;
	private EditText e1, e2, e3, e4;
	private ImageView m1, m2, m3, m4;
	private Button rebtn;
	private String AddNmae, UserPwd, UserTel, UserSex = "��";
	private RadioGroup rg;
	private ProgressDialog dialog;
	boolean name = false, pwd = false, tel = false;// ���ע����Ϣ�Ƿ�ͨ��

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_registration);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		init();

	}

	public void init() {
		rebtn = (Button) findViewById(R.id.re_zhuce);
		rg = (RadioGroup) findViewById(R.id.re_RG);

		e1 = (EditText) findViewById(R.id.re_et_addusername);
		e2 = (EditText) findViewById(R.id.re_et_adduserpwd);
		e3 = (EditText) findViewById(R.id.re_et_adduserpwd2);
		e4 = (EditText) findViewById(R.id.re_et_addusertel);

		m1 = (ImageView) findViewById(R.id.re_m1);
		m2 = (ImageView) findViewById(R.id.re_m2);
		m3 = (ImageView) findViewById(R.id.re_m3);
		m4 = (ImageView) findViewById(R.id.re_m4);
		// ����ʾX

		EditTextClearTools.addclerListener(e1, m1);
		EditTextClearTools.addclerListener(e2, m2);
		EditTextClearTools.addclerListener(e3, m3);
		EditTextClearTools.addclerListener(e4, m4);

		// �����˳��ı����ʱ����һ���û����ظ��Ե���֤
		e1.setOnFocusChangeListener(new NoOnFocusChangeListener() {
		
		@Override
		public void	onNoDoubleClick(View arg0, boolean hasFocus) {
				if (!hasFocus) {
					String e1str = e1.getText().toString();
					if (e1str != null && !e1str.equals(""))
						if (isUserNameNumber(e1str)) {
							// ��֤ͨ��
							if (NetConn.isNetworkAvailable(RegistrationActivity.this)) {
								// �ж��Ƿ��ܹ����ӵ�������
								AccessInternet.DoUserName(handler, e1str);
							} else {
								Toast.makeText(RegistrationActivity.this, "�޷����ӵ�������������������������!", 1).show();
							}
						}
						else {
							Toast.makeText(RegistrationActivity.this, "�û�������Ϊ����ֻ��Ϊ���ġ����֡���ĸ�����!", 1).show();
							m1.setImageResource(R.drawable.img_dele);
							m1.setVisibility(View.VISIBLE);
						}
				} else {
					m1.setImageResource(R.drawable.text_del);
					m1.setVisibility(View.INVISIBLE);
				}

			}
		});

		// EditTextClearTools.addEditTextisYes(handler, e2, e3, m2, m3);

	/*	e2.addTextChangedListener(new TextWatcher() {
			// �ı��Ⱥ�ִ��before-on-after��213��
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// �ı��ı�

				Log.v("e2", "1");

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// �ı�֮��ı�֮ǰ
				Log.v("e2", "2");

			}

			@Override
			public void afterTextChanged(Editable arg0) {
				// �ı�֮��ı�֮��
				Log.v("e2", "3");
			}
		});
*/
		e4.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean hasFocus) {
				if (!hasFocus) {
					String e4str = e4.getText().toString();
					if (e4str != null && !e4str.equals(""))
						if (isTelPhoneNumber(e4str)) {
							m4.setImageResource(R.drawable.img_gou);
							m4.setVisibility(View.VISIBLE);

						} else {
							m4.setImageResource(R.drawable.img_dele);
							m4.setVisibility(View.VISIBLE);
						}
				}

				else {
					m4.setImageResource(R.drawable.text_del);
					m4.setVisibility(View.INVISIBLE);
				}

			}
		});
		banquan = (TextView) findViewById(R.id.re_banquan);
		banquan.setText(MainApplication.banquan);

		// ���ذ�ť
		fanhui = (ImageView) findViewById(R.id.re_fanhui);
		fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent ReIntent = new Intent(RegistrationActivity.this, LogoDoActivity.class);
				RegistrationActivity.this.startActivity(ReIntent);
				RegistrationActivity.this.finish();
			}
		});

		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				RadioButton rb = (RadioButton) findViewById(arg0.getCheckedRadioButtonId());
				UserSex = rb.getText().toString();

			}
		});

		rebtn.setOnClickListener(new NoDoubleClickListener() {

			@Override
			public void onNoDoubleClick(View v) {

				dialog = ProgressDialog.show(RegistrationActivity.this, "", "���ڼ��ע����Ϣ..");
				if (isUserNameNumber(e1.getText().toString())) {
				    AccessInternet.DoUserName(handler, e1.getText().toString());
				  }
				else {
					Toast.makeText(RegistrationActivity.this, "�û�������Ϊ����ֻ��Ϊ���ġ����֡���ĸ�����!", 1).show();
					m1.setImageResource(R.drawable.img_dele);
					m1.setVisibility(View.VISIBLE);
					name=false;
					
				}
				  closeDialog();
			}
		});

	}

	/**
	 * �ر�ע����Ϣ��鴰�� 1.5��
	 */
	public void closeDialog() {

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				
				  dialog.dismiss();
				if (isUserPwdNumber(e2.getText().toString())) {
					m2.setImageResource(R.drawable.img_gou);
					m2.setVisibility(View.VISIBLE);
					if (e3.getText().toString().equals(e2.getText().toString())) {
						m3.setImageResource(R.drawable.img_gou);
						m3.setVisibility(View.VISIBLE);
						pwd=true;
					} else {
						m3.setImageResource(R.drawable.img_dele);
						m3.setVisibility(View.VISIBLE);
						
						pwd=false;
						Toast.makeText(RegistrationActivity.this, "�����������벻һ��!", 1).show();
					}

				} else {
					m2.setImageResource(R.drawable.img_dele);
					m2.setVisibility(View.VISIBLE);
					Toast.makeText(RegistrationActivity.this, "����ֻ��Ϊ����6λ�������֡���ĸ�����!", 1).show();
				}

				if(isTelPhoneNumber(e4.getText().toString()))
				{
					m4.setImageResource(R.drawable.img_gou);
					m4.setVisibility(View.VISIBLE);
					tel=true;
					
				}
				else {
					m4.setImageResource(R.drawable.img_dele);
					m4.setVisibility(View.VISIBLE);
					tel=false;
					Toast.makeText(RegistrationActivity.this, "��������ȷ�ĵ绰����!(��Ҫ)", 1).show();
				}
			
			
				if(name&&pwd&&tel){	
				
					showRegisDialog();	
				}
			
				
			}
		}, 3000);

	}
	
	Builder bu1=null; 
	public void showRegisDialog(){
		if(bu1==null){
			bu1= new Builder(RegistrationActivity.this);
			bu1.setTitle("ע����ʾ:");
			bu1.setCancelable(false);
		}
			bu1.setMessage("�û���:"+e1.getText().toString()+"\n"+"��    ��:"+e2.getText().toString()+"\n"+"����ע��,�Ƿ�ע����û�����");
			bu1.setNegativeButton("������", null);
			bu1.setPositiveButton("ע    ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					dialog = ProgressDialog.show(RegistrationActivity.this, "", "ע����..");
				
					AddNmae=e1.getText().toString();
					UserPwd=e2.getText().toString();
				    UserTel=e4.getText().toString();
				    //�ύע����Ϣ��������
			      	AccessInternet.AddUser(handler, AddNmae, UserPwd, UserTel, UserSex);
					
				}
			});
		bu1.create();
		bu1.show();
	}
	
	/**
	 * ����ע��Ľ���  3���ر�
	 */
	
	
	private void CloseReOKdialog(){
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(RegistrationActivity.this, "ע��ɹ�!�뷵�ص�½!", 1).show();
				dialog.dismiss();
			}
		},3000);
		
	}
	

	/**
	 * ��֤�û����Ƿ������� ���� ��ĸ
	 */

	public boolean isUserNameNumber(String Name) {
		if (Name != null && Name.length() <= 10 && Name.length() > 0) {
			Pattern pattern = Pattern.compile("^[a-zA-Z0-9\u4e00-\u9fa5]+$");
			Matcher matcher = pattern.matcher(Name);
			return matcher.matches();
		}
		return false;
	}

	/**
	 * ��֤�����Ƿ��� ���ֺ���ĸ�����
	 */

	public boolean isUserPwdNumber(String pwd) {
		if (pwd != null && pwd.length() <= 12 && pwd.length() >=6) {
			Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
			Matcher matcher = pattern.matcher(pwd);
			return matcher.matches();
		}
		return false;
	}
	/**
	 * ��֤�ֻ������Ƿ���ȷ������11λ����
	 */

	public boolean isTelPhoneNumber(String TelStr) {
		if (TelStr != null && TelStr.length() == 11) {
			Pattern pattern = Pattern.compile("^1[3|4|5|6|7|8][0-9]\\d{8}$");
			Matcher matcher = pattern.matcher(TelStr);

			return matcher.matches();
		}
		return false;
	}

	/**
	 * 
	 * @MethodsNmae: isUserNameRepetition ��֤�û����Ƿ��ظ�
	 * @Date 2017��11��16�� ����9:47:48
	 */
	public void isUserNameRepetition(String Name) {

	}

	/**
	 * Handler 0Ϊ��ǰ�û�������ע�� 1Ϊ��ǰ���û����Ѿ�����
	 */
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				m1.setImageResource(R.drawable.img_gou);
				m1.setVisibility(View.VISIBLE);
				name=true;
				break;
			case 1:
				m1.setImageResource(R.drawable.img_dele);
				m1.setVisibility(View.VISIBLE);
				name=false;
				Toast.makeText(RegistrationActivity.this, msg.obj + "", 1).show();
				break;
			case 2:
				Toast.makeText(RegistrationActivity.this, msg.obj + "", 1).show();
				break;
				
			//����Ϊ�û�ע����ʷ�������Handel���ݽ���
			case 3:
				//����ע��ɹ���Ϣ
				CloseReOKdialog();
				break;
				//����ע��ʧ�ܵ���Ϣ
			case 4:
				dialog.dismiss();
				Toast.makeText(RegistrationActivity.this, msg.obj + "", 1).show();
				break;
				
			default:
				break;

			}

		}
	};

	// ����
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent ReIntent = new Intent(RegistrationActivity.this, LogoDoActivity.class);
			RegistrationActivity.this.startActivity(ReIntent);
			RegistrationActivity.this.finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
