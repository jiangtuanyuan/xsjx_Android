package com.example.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.entity.Order_Spinner_TimeEntity;
import com.example.xsjx.R;

import java.util.List;

public class Order_Spinner_Adapter extends BaseAdapter {
	public Context context;
	public List<Order_Spinner_TimeEntity> list;
	public LayoutInflater inflater;

	public Order_Spinner_Adapter(Context context, List<Order_Spinner_TimeEntity> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);

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

	@SuppressLint("ResourceAsColor")
	@Override
	public View getView(int i, View view, ViewGroup arg2) {
		horldView h = null;
		if (view == null) {
			view = inflater.inflate(R.layout.order_spinner_time,null);
			h = new horldView(view);
			view.setTag(h);
		} else {
			h = (horldView) view.getTag();
		}
		Order_Spinner_TimeEntity splist=list.get(i);
		h.StartTime.setText(splist.StratTime);
		h.EndTime.setText(splist.EndTime);
        h.Order.setText(splist.Order);
	 
        if(splist.Order.equals("(��ԤԼ)"))
        {
        	// h.CheckBox.setClickable(false);
        	 h.LinearLayout.setBackgroundColor(R.color.sblue);
        }
     //   else  h.CheckBox.setClickable(true);
		
	
		
		
		return view;
	}

	class horldView {
		
		public LinearLayout LinearLayout;
		public TextView  StartTime,EndTime,Order;
		//public CheckBox  CheckBox;
		
		public horldView(View view) {
			LinearLayout=(android.widget.LinearLayout) view.findViewById(R.id.Order_Spinner_time_LinearLayout);
			StartTime=(TextView) view.findViewById(R.id.Order_Spinner_time_StartTime);
			EndTime=(TextView) view.findViewById(R.id.Order_Spinner_time_EndTime);
			Order=(TextView) view.findViewById(R.id.Order_Spinner_time_Order);
			//CheckBox=(android.widget.CheckBox) view.findViewById(R.id.Order_Spinner_time_CheckBox);
		}

	}

}
