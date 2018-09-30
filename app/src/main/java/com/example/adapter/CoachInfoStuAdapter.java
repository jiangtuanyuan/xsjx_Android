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
 * @Description:   ������ҳ��-����ѧԱ������
 * @ClassName:     CoachInfoBBSAdapter.java
 * @author          ����Բ
 * @version         V1.0  
 * @Date           2017��11��5�� ����6:13:23
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
		
        h.headview.setImageResource(R.drawable.log);//��Ĭ������Logo��־  ����ͨ����ַ����ͼƬת����λͼ��������
        
		h.stuNmae.setText(stu.sNmae);
		h.stuNmae.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		h.stuNmae.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			 //��ת��ѧԱ��ҳ stu.sID
				
				
			}
		});
		
		
		if(stu.sSex.equals("��"))
        h.stuSex.setImageResource(R.drawable.my_male);
		else   h.stuSex.setImageResource(R.drawable.my_famale);
		
		
		if(stu.sState.equals("ѧϰ��"))
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
