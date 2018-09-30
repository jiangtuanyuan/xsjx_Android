package com.example.utils;

import java.util.Calendar;

import android.view.View;
import android.view.View.OnFocusChangeListener;
/**
 * �����Ϊ����ע�����   ��֤�û����Ƿ����  3����ֻ���ύһ���뿪�ı����¼�
 */

public class NoOnFocusChangeListener implements OnFocusChangeListener{

	 public static final int MIN_CLICK_DELAY_TIME = 3000;
     private long lastClickTime = 0;
	@Override
	public void onFocusChange(View v, boolean tf) {
		 long currentTime = Calendar.getInstance().getTimeInMillis();
         if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
             lastClickTime = currentTime;
             onNoDoubleClick(v,tf);
         } 
		
	}
    public void onNoDoubleClick(View v,boolean tf) {
		
	}

}
