package com.example.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.entity.CoachInfoStuEntity;
import com.example.utils.CircleImageView;
import com.example.xsjx.R;

import java.util.List;

/**
 * 
 * @Description:   教练主页中-他的学员适配器
 * @ClassName:     CoachInfoBBSAdapter.java
 * @author          蒋团圆
 * @version         V1.0  
 * @Date           2017年11月5日 下午6:13:23
 */

public class CoachInfoStuAdapter extends BaseAdapter {

	public List<CoachInfoStuEntity> list;
	public Context context;

	public CoachInfoStuAdapter(List<CoachInfoStuEntity> list, Context context) {
		this.list = list;
		this.context = context;

	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@SuppressLint({ "InflateParams", "ResourceAsColor" })
	@Override
	public View getView(int postion, View view, ViewGroup arg2) {
		HorldView h = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.coach_info_students_item, null);
			h = new HorldView(view);
			view.setTag(h);

		} else {
			h = (HorldView) view.getTag();
		}

		final CoachInfoStuEntity stu = list.get(postion);
		
        h.headview.setImageResource(R.drawable.log);//先默认设置Logo标志  后期通过网址下载图片转化成位图下载设置
        
		h.stuNmae.setText(stu.sNmae);
		h.stuNmae.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		h.stuNmae.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			 //跳转到学员主页 stu.sID
				
				
			}
		});
		
		
		if(stu.sSex.equals("男"))
        h.stuSex.setImageResource(R.drawable.my_male);
		else   h.stuSex.setImageResource(R.drawable.my_famale);
		
		
		if(stu.sState.equals("学习中"))
		{
		h.stuState.setText(stu.sState);	
		h.stuState.setTextColor(h.stuState.getResources().getColor(R.color.green));	
		}
		else 
		{h.stuState.setText(stu.sState);	
		h.stuState.setTextColor(h.stuState.getResources().getColor(R.color.red));		}
		

		
		
	
	
		
	

		return view;
	}

	class HorldView {
	
		public CircleImageView headview;
		public TextView stuNmae,stuState;
		public ImageView stuSex;
		
		public HorldView(View view) {
		
			this.headview=(CircleImageView) view.findViewById(R.id.coach_info_students_item_img);
			
			this.stuNmae=(TextView) view.findViewById(R.id.coach_info_students_item_name);
			this.stuSex=(ImageView) view.findViewById(R.id.coach_info_students_item_sex);
			this.stuState=(TextView) view.findViewById(R.id.coach_info_students_item_state);
			
		
			
		}

	}

}
