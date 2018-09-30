package com.example.utils;

import java.util.Calendar;

import android.view.View;
import android.view.View.OnFocusChangeListener;
/**
 * 这个是为了在注册界面   验证用户名是否存在  3秒内只能提交一次离开文本框事件
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
