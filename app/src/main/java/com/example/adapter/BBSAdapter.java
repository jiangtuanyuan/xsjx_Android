package com.example.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.entity.Fragment4_BBS_Entity;
import com.example.utils.CircleImageView;
import com.example.utils.NoDoubleClickListener;
import com.example.xsjx.R;

import java.util.List;

public class BBSAdapter extends BaseAdapter {

	public List<Fragment4_BBS_Entity> list;
	public Context context;

	public BBSAdapter(List<Fragment4_BBS_Entity> list, Context context) {
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
			view = LayoutInflater.from(context).inflate(R.layout.fragment_4_lv_item, null);
			h = new HorldView(view);
			view.setTag(h);

		} else {
			h = (HorldView) view.getTag();
		}

		final Fragment4_BBS_Entity bbs = list.get(postion);
		
        h.headview.setImageResource(R.drawable.log);
		h.username.setText(bbs.userName);
        
		if(bbs.userType.equals("??"))
		{
		h.usertype.setText(bbs.userType);	
		h.usertype.setTextColor(h.usertype.getResources().getColor(R.color.yellow));	
		}
		else 
		{h.usertype.setText(bbs.userType);	
		h.usertype.setTextColor(h.usertype.getResources().getColor(R.color.green));		}
		
		h.datetime.setText(bbs.datetime);
		h.title.setText(bbs.BBStitle);
		
		
		h.bbslayou.setTag(bbs.id);
	
		
		h.bbslayou.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
				// Toast.makeText(context, Meg.id+"", 1).show();
				//((MainActivity) context).intentBBS(bbs);

			}
		});

		return view;
	}

	class HorldView {
		public LinearLayout bbslayou;
		public CircleImageView headview;
		public TextView username,usertype,datetime,title;
		public HorldView(View view) {
			this.bbslayou=(LinearLayout) view.findViewById(R.id.BBS_layou);
			this.headview=(CircleImageView) view.findViewById(R.id.fragment4_lv_CiecleImg);
			this.username=(TextView) view.findViewById(R.id.fragment4_lv_username);
			this.usertype=(TextView) view.findViewById(R.id.fragment4_lv_usertypr);
			this.datetime=(TextView) view.findViewById(R.id.fragment4_lv_datetime);
			this.title=(TextView) view.findViewById(R.id.fragment4_lv_tile);
			
		}

	}

}
