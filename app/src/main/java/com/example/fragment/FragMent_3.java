package com.example.fragment;

import com.example.utils.NoDoubleClickListener;
import com.example.xsjx.EnrollActivity;
import com.example.xsjx.MainActivity;
import com.example.xsjx.R;
import com.example.xsjx.VideoActivity;
import com.example.xsjx.VideoPlayActivity;
import com.example.xsjx.WebActivity;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

@SuppressWarnings("unused")
public class FragMent_3 extends Fragment {
	public RadioGroup rg;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View content = inflater.inflate(R.layout.fragment_3, container, false);
		Log.v("Fragment", "fragment3创建");
		initView(content);
		return content;
	}

	private void initView(View view) {
		/*  
		 * rg = (RadioGroup) view.findViewById(R.id.fragment3_RG);
		rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int key) {
				switch (key) {
				case R.id.fragment3_enroll_btn:
					Intent intent = new Intent(getActivity(), EnrollActivity.class);
					startActivity(intent);
					break;
				case R.id.fragment3_Video_btn:
					Intent intent1 = new Intent(getActivity(), VideoActivity.class);
					startActivity(intent1);
					
					break;
				case R.id.fragment3_order_btn:
					
					Intent webintent = new Intent(getActivity(), WebActivity.class);
					webintent.putExtra("Url", "http://hn.122.gov.cn/m/login");
					webintent.putExtra("Webname", "预约考试");
					startActivity(webintent);
					break;
				default:
					break;
				}

			}
		});
		
	
	 这里发现多点触屏问题 解决方案
	1.android:splitMotionEvents="false"
	2.<item name="android:windowEnableSplitTouch">false</item>  
      <item name="android:splitMotionEvents">false</item> 
	
		*/
		
		view.findViewById(R.id.fragment3_enroll_btn).setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
				Intent intent = new Intent(getActivity(), EnrollActivity.class);
				startActivity(intent);
			}
			
		});
		
		view.findViewById(R.id.fragment3_Video_btn).setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
			Intent intent1 = new Intent(getActivity(), VideoActivity.class);
			//Intent intent1 = new Intent(getActivity(), VideoPlayActivity.class);
		
			startActivity(intent1);
				
			}
			
		});
		
		view.findViewById(R.id.fragment3_order_btn).setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
				Intent webintent = new Intent(getActivity(), WebActivity.class);
				webintent.putExtra("Url", "http://hn.122.gov.cn/m/login");
				webintent.putExtra("Webname", "预约考试");
				startActivity(webintent);
			}
			
		});
		
		
		
	}
	
	
	


}
