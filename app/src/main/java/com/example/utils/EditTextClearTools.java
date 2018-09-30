package com.example.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.xsjx.R;
import com.example.xsjx.RegistrationActivity;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EditTextClearTools {

	public static void addclerListener(final EditText e1, final ImageView m1, final Button bt) {
		e1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

				// ����������봮���ȴ���0��ô����ʾclear��ť��
				String s1 = s + "";
				if (s.length() > 0) {
					m1.setVisibility(View.VISIBLE);
					bt.setBackgroundResource(R.drawable.login_button_selector);

				} else {

					bt.setBackgroundResource(R.drawable.shape_touch_bule_button);
					m1.setVisibility(View.INVISIBLE);
				}

			}
		});

		m1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ��������
				e1.setText("");

			}
		});

	}

	/**
	 * ��½���������ı����໥���� ע�ͣ���������ı����ֵ����Ϊ�գ���ô��ť�ͱ�����ύ����ɫ ����ͳɻ�ɫ
	 * 
	 * @Date 2017��11��16�� ����9:59:32
	 */
	public static void addclerListener(final EditText e1, final EditText e2, final ImageView m1, final Button bt) {
		e1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				// ����������봮���ȴ���0��ô����ʾclear��ť��
				String s1 = s + "";
				if (s.length() > 0) {
					m1.setVisibility(View.VISIBLE);

					String e2str = e2.getText().toString();
					if (e2str != null && !e2str.equals(""))
						bt.setBackgroundResource(R.drawable.login_button_selector);

				} else {

					bt.setBackgroundResource(R.drawable.shape_touch_bule_button);
					m1.setVisibility(View.INVISIBLE);
				}

			}
		});
		m1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��������
				e1.setText("");

			}
		});

	}

	// �ı�������ͼƬ �������ı����������ť
	public static void addclerListener(final EditText e1, final ImageView m1) {
		e1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				// ����������봮���ȴ���0��ô����ʾclear��ť��
				String s1 = s + "";
				if (s.length() > 0) {
					m1.setVisibility(View.VISIBLE);

				} else {
					m1.setVisibility(View.INVISIBLE);
				}

			}
		});

		m1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// ��������
				e1.setText("");

			}
		});

	}

	public static void addEditTextisYes(final Handler handler, final EditText e1, final EditText e2, final ImageView m1,
			final ImageView m2) {
		e1.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean hasFocus) {
				String e1str = e1.getText().toString();

				if (hasFocus) {
					m1.setImageResource(R.drawable.text_del);
					m1.setVisibility(View.INVISIBLE);
				}

				if (e1str.length() >= 6 && !e1str.equals("")) {
					if (isUserPwdNumber(e1str)) {
						m1.setImageResource(R.drawable.img_gou);
						m1.setVisibility(View.INVISIBLE);

					}

				} else {
					m1.setImageResource(R.drawable.img_dele);
					m1.setVisibility(View.VISIBLE);
				}

			}
		});

		e2.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View arg0, boolean hasFocus) {

				if (hasFocus) {
					m2.setImageResource(R.drawable.text_del);
					m2.setVisibility(View.INVISIBLE);
				}
				String e1str = e1.getText().toString();
				String e2str = e2.getText().toString();

				if (e2str != null && !e2str.equals(""))
					if (isUserPwdNumber(e1str) && isUserPwdNumber(e2str) && e2str.equals(e1str)) {
						m2.setImageResource(R.drawable.img_gou);
						m2.setVisibility(View.VISIBLE);
						m1.setImageResource(R.drawable.img_gou);
						m1.setVisibility(View.VISIBLE);

					} else {
						Message msg = new Message();
						msg.what = 2;
						msg.obj = "�����������벻һ��!";
						handler.sendMessage(msg);

						m2.setImageResource(R.drawable.img_dele);
						m2.setVisibility(View.VISIBLE);
						m1.setImageResource(R.drawable.img_dele);
						m1.setVisibility(View.VISIBLE);
					}
			}
		});
	}

	/**
	 * �������Ͻǵ� ���� ��
	 */
	public static void addEditTextText(final EditText e1, final EditText e2, final TextView tv) {
		e1.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				// ����������봮���ȴ���0��ô����ʾclear��ť��
				String s1 = s + "";
				String s2 = e2.getText().toString();
				if (s.length() > 0 && s2.length() > 0) {
					tv.setTextColor(tv.getResources().getColor(R.drawable.posting_submit_item));

				} else {
					tv.setTextColor(tv.getResources().getColor(R.color.gary));
				}

			}
		});

		e2.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				// ����������봮���ȴ���0��ô����ʾclear��ť��
				String s1 = s + "";
				String s2 = e1.getText().toString();
				if (s.length() > 0 && s2.length() > 0) {

					tv.setTextColor(tv.getResources().getColor(R.drawable.posting_submit_item));
				} else {
					tv.setTextColor(tv.getResources().getColor(R.color.gary));
				}

			}
		});

	}
	/**
	 * �����ı�����ύ��İ�
	 */
	
	public static void addEditTextText(EditText e1,final Button bt){
		e1.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (s.length() > 0) {
					bt.setBackgroundResource(R.drawable.btn_bg);

				} else {
					bt.setBackgroundResource(R.drawable.bbs_gary_bt_bg);

				}

			}
		});
		
		
		
	}
	
	
	

	/**
	 * ��֤�����Ƿ��� ���ֺ���ĸ�����
	 */

	public static boolean isUserPwdNumber(String pwd) {
		if (pwd != null && pwd.length() <= 12 && pwd.length() > 6) {
			Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
			Matcher matcher = pattern.matcher(pwd);
			return matcher.matches();
		}
		return false;
	}

}
