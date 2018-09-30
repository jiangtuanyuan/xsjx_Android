package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.json.entity.tel;
import com.example.xsjx.EnrollActivity;
import com.example.xsjx.R;

import java.util.List;

/**
 * 
 * @author 蒋团圆
 * 报名模块-在线报名
 * 驾照类型以及价格报名班价格  adapter
 */

public class EnrollPhoneItemAdapter extends BaseAdapter {

	public List<tel> list;
	public Context context;

	public EnrollPhoneItemAdapter(List<tel> list, Context context) {
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
	@Override
	public View getView(int postion, View view, ViewGroup arg2) {
		HorldView h = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.enroll_phone_item, null);
			h = new HorldView(view);
			view.setTag(h);

		} else {
			h = (HorldView) view.getTag();
		}
		final tel p=list.get(postion);
		h.p.setText((postion+1)+". "+p.telname);
	    h.phoneimg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
		    ((EnrollActivity) context).phoneUrl(p.tel);
				
			}
		});
		
   /*  
   
		h.usertype.setTextColor(h.usertype.getResources().getColor(R.color.yellow));	
	
*/
		return view;
	}

	class HorldView {
		public TextView p,phoneimg;
		public HorldView(View view) {
     	p=(TextView) view.findViewById(R.id.Enroll_phone_item_tv);
     	phoneimg=(TextView) view.findViewById(R.id.enroll_phone_item_phoneimg);
		}

	}

}
