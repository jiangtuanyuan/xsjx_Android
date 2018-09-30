package com.example.utils;

import java.util.Calendar;

import com.example.xsjx.MainApplication;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

/*2017.10.13 9:05
 * View ·ÀÖ¹ÖØ¸´µã»÷
 * ½¯ÍÅÔ²
 * 
 * */
public abstract class NoDoubleClickListener implements OnClickListener{
	private  int MIN_CLICK_DELAY_TIME = 3000;
     private long lastClickTime = 0;
	@Override
	public void onClick(View v) {
		 long currentTime = Calendar.getInstance().getTimeInMillis();
         if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
             lastClickTime = currentTime;
             onNoDoubleClick(v);
         } 
	}
	public void onNoDoubleClick(View v) {
		
	}
}
