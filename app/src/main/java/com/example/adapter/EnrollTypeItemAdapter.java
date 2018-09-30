package com.example.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.json.entity.JzInfoEntity;
import com.example.xsjx.EnrollActivity;
import com.example.xsjx.R;

import java.util.List;

/**
 * 
 * @author 蒋团圆 报名模块-在线报名 驾照类型以及价格报名班价格 adapter
 */

public class EnrollTypeItemAdapter extends BaseAdapter {

	public List<JzInfoEntity> list;
	public Context context;

	public EnrollTypeItemAdapter(List<JzInfoEntity> list, Context context) {
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
			view = LayoutInflater.from(context).inflate(R.layout.enroll_type_item, null);
			h = new HorldView(view);
			view.setTag(h);

		} else {
			h = (HorldView) view.getTag();
		}

		final JzInfoEntity DL = list.get(postion);
		h.DLtype.setText(DL.type); // 驾照类型

		h.DLClass.setText(DL.typeclass);// 驾照班级

		h.DLMoney.setText(DL.money + " ¥");// 钱

		h.DLlayou.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
           ((EnrollActivity) context).GoEnrollDetails(DL);

			}
		});

		/*
		 * 
		 * h.usertype.setTextColor(h.usertype.getResources().getColor(R.color.
		 * yellow));
		 * 
		 */
		return view;
	}

	class HorldView {
		public LinearLayout DLlayou;
		public TextView DLtype, DLClass, DLMoney;

		public HorldView(View view) {
			this.DLlayou = (LinearLayout) view.findViewById(R.id.enroll_item_LinLayout);
			this.DLtype = (TextView) view.findViewById(R.id.enroll_item_type);
			this.DLClass = (TextView) view.findViewById(R.id.enroll_item_class);
			this.DLMoney = (TextView) view.findViewById(R.id.enroll_item_money);
		}

	}

}
