package com.example.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.json.entity.SchoolNewsEntity;
import com.example.utils.MarqueeTextView;
import com.example.utils.NoDoubleClickListener;
import com.example.utils.Utils;
import com.example.xsjx.R;

import java.util.List;

public class MessageAdapter extends BaseAdapter {

	public List<SchoolNewsEntity> list;
	public Context context;

	public MessageAdapter(List<SchoolNewsEntity> list, Context context) {
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

	@SuppressLint("InflateParams")
	@Override
	public View getView(int postion, View view, ViewGroup arg2) {
		HorldView h = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.fragment_1_lv_item, null);
			h = new HorldView(view);
			view.setTag(h);

		} else {
			h = (HorldView) view.getTag();
		}

		final SchoolNewsEntity Meg = list.get(postion);

		  /*if(Meg.newsTitle.length()>=15)
	      h.title.setText(Meg.newsTitle.substring(0,15)+"...");
		  else   */
		
			  h.title.setText(Meg.newsTitle);
		  
		h.time.setText(Meg.newsDate.substring(0, 10));	
		
	//	h.mesglayou.setTag(Meg.id);
		h.mesglayou.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
				// Toast.makeText(context, Meg.id+"", 1).show();
			
				Utils.intentWebActity(context, Meg.newsUrl, "校内资讯");
				
				//((MainActivity) context).intentWebActity(Meg.newsUrl,"��?????");

			}
		});

		return view;
	}
	class HorldView {
		public RelativeLayout mesglayou;
		public MarqueeTextView title;
		public TextView time;

		public HorldView(View view) {
			this.title = (MarqueeTextView) view.findViewById(R.id.framgne_1_lv_title);
			this.time = (TextView) view.findViewById(R.id.framgne_1_lv_datetiem);
			this.mesglayou = (RelativeLayout) view.findViewById(R.id.Mesg_layou);
		}

	}

}
