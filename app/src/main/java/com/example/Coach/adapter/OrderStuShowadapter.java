package com.example.Coach.adapter;

import java.util.List;

import com.example.Coach.Activity.CoachOrderKe2Activity;
import com.example.Coach.adapter.OrderaAdapter.HorldView;
import com.example.Coach.entity.Stuinfo;
import com.example.Coach.entity.Timeorder;
import com.example.utils.CollapsibleTextView;
import com.example.utils.CustomVideoView;
import com.example.utils.NoDoubleClickListener;
import com.example.utils.Utils;
import com.example.xsjx.R;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

public class OrderStuShowadapter extends BaseAdapter{

	public Context context;
	public List<Stuinfo> list;
	public int timep;
	public OrderStuShowadapter(List<Stuinfo> list,Context context,int timep) {
		this.context = context;
		this.list=list;
		this.timep=timep;
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int p, View view, ViewGroup arg2) {
		HorldView h = null;
		
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.coach_order_ke2_order_lv_showstuorder_dialog_item, null);
			h = new HorldView(view);
			view.setTag(h);

		} else {
			h = (HorldView) view.getTag();
		}
		
		final Stuinfo stuinfo=list.get(p);
		
		h.num.setText((p+1)+"");
		h.stuname.setText(stuinfo.getUsername());
		h.stuname.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//设置下划线
		
		h.stuname.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Utils.showToast(context, "暂时无法查看学员资料!");
				
			}
		});
		/*
		h.liuyan.setText(stuinfo.getUserliuyan());
		h.liuyan.setMovementMethod(ScrollingMovementMethod.getInstance());
		*/
		h.liuyan.setFlag(false);
		h.liuyan.setDesc(stuinfo.getUserliuyan(), BufferType.NORMAL);
		
	if(stuinfo.getOrderstate()==3){
			//教练已经拒绝过 显示已拒绝 然后和更改
			
	    	h.state.setText("您已拒绝!");
	    	h.state.setTextColor(ContextCompat.getColor(context, R.color.red));
		
			h.tongyi.setVisibility(View.GONE);
			h.jujue.setVisibility(View.GONE);
			
			h.yjujue.setVisibility(View.VISIBLE);
			h.genggai.setVisibility(View.VISIBLE);
			h.genggai.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//设置下划线
			
			h.genggai.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					/*h.genggai.setVisibility(View.GONE);
					h.tongyi.setVisibility(View.GONE);
					h.jujue.setVisibility(View.GONE);*/
					
					updatestustate(p);
					
					
				}
			});
			
		}
		
	
	h.tongyi.setOnClickListener(new NoDoubleClickListener() {
		@Override
		public void onNoDoubleClick(View v) {
			Utils.showToast(context, "同意");
		}
		
	});
	h.jujue.setOnClickListener(new NoDoubleClickListener() {
		@Override
		public void onNoDoubleClick(View v) {
			Utils.showToast(context, "拒绝");
		}
		
	});
		
		
		
		
		return view;
	}

	/**
	 * 询问是否更改
	 */
	Builder bu1=null; 
	public void updatestustate(final int p){
		if(bu1==null){
		bu1= new Builder(context);
		bu1.setTitle("提示:");
		bu1.setMessage("是否重置该学员的预约状态?");
		bu1.setNegativeButton("取消", null);
		bu1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
				//1.先从界面中删除 最后从服务器删除
				CoachOrderKe2Activity.ke2order.getTimeorder().get(timep).getStuinfo().get(p).setOrderstate(0);;
				//更新界面数据
			
				CoachOrderKe2Activity.oadapter.notifyDataSetChanged();

				
				Utils.showToast(context, "重置成功,请返回下拉刷新表格!");

			}
		});
		bu1.create();
	 }
 bu1.show();	
}
	
	
	class HorldView{

		public TextView num,stuname,tongyi,jujue,yjujue,genggai,state;
        public CollapsibleTextView liuyan;
		public HorldView(View v) {
			num = (TextView) v.findViewById(R.id.coach_order_ke2_order_lv_showstuorder_dialog_item_num);// 数量
			stuname = (TextView) v.findViewById(R.id.coach_order_ke2_order_lv_showstuorder_dialog_item_stuname);
			liuyan = (CollapsibleTextView) v.findViewById(R.id.coach_order_ke2_order_lv_showstuorder_dialog_item_stuliuyan);
			state=(TextView) v.findViewById(R.id.coach_order_ke2_order_lv_showstuorder_dialog_item_state);
			
			tongyi = (TextView) v.findViewById(R.id.coach_order_ke2_order_lv_showstuorder_dialog_item_tongyi);//同意
			jujue = (TextView) v.findViewById(R.id.coach_order_ke2_order_lv_showstuorder_dialog_item_jujue);//拒绝
			
			
			yjujue=(TextView) v.findViewById(R.id.coach_order_ke2_order_lv_showstuorder_dialog_item_yjujue);//已拒绝
			genggai=(TextView) v.findViewById(R.id.coach_order_ke2_order_lv_showstuorder_dialog_item_genggai);//更改
	
		}

		
	}
	
	
	
}
