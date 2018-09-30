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
		h.stuname.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//�����»���
		
		h.stuname.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Utils.showToast(context, "��ʱ�޷��鿴ѧԱ����!");
				
			}
		});
		/*
		h.liuyan.setText(stuinfo.getUserliuyan());
		h.liuyan.setMovementMethod(ScrollingMovementMethod.getInstance());
		*/
		h.liuyan.setFlag(false);
		h.liuyan.setDesc(stuinfo.getUserliuyan(), BufferType.NORMAL);
		
	if(stuinfo.getOrderstate()==3){
			//�����Ѿ��ܾ��� ��ʾ�Ѿܾ� Ȼ��͸���
			
	    	h.state.setText("���Ѿܾ�!");
	    	h.state.setTextColor(ContextCompat.getColor(context, R.color.red));
		
			h.tongyi.setVisibility(View.GONE);
			h.jujue.setVisibility(View.GONE);
			
			h.yjujue.setVisibility(View.VISIBLE);
			h.genggai.setVisibility(View.VISIBLE);
			h.genggai.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//�����»���
			
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
			Utils.showToast(context, "ͬ��");
		}
		
	});
	h.jujue.setOnClickListener(new NoDoubleClickListener() {
		@Override
		public void onNoDoubleClick(View v) {
			Utils.showToast(context, "�ܾ�");
		}
		
	});
		
		
		
		
		return view;
	}

	/**
	 * ѯ���Ƿ����
	 */
	Builder bu1=null; 
	public void updatestustate(final int p){
		if(bu1==null){
		bu1= new Builder(context);
		bu1.setTitle("��ʾ:");
		bu1.setMessage("�Ƿ����ø�ѧԱ��ԤԼ״̬?");
		bu1.setNegativeButton("ȡ��", null);
		bu1.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
				//1.�ȴӽ�����ɾ�� ���ӷ�����ɾ��
				CoachOrderKe2Activity.ke2order.getTimeorder().get(timep).getStuinfo().get(p).setOrderstate(0);;
				//���½�������
			
				CoachOrderKe2Activity.oadapter.notifyDataSetChanged();

				
				Utils.showToast(context, "���óɹ�,�뷵������ˢ�±��!");

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
			num = (TextView) v.findViewById(R.id.coach_order_ke2_order_lv_showstuorder_dialog_item_num);// ����
			stuname = (TextView) v.findViewById(R.id.coach_order_ke2_order_lv_showstuorder_dialog_item_stuname);
			liuyan = (CollapsibleTextView) v.findViewById(R.id.coach_order_ke2_order_lv_showstuorder_dialog_item_stuliuyan);
			state=(TextView) v.findViewById(R.id.coach_order_ke2_order_lv_showstuorder_dialog_item_state);
			
			tongyi = (TextView) v.findViewById(R.id.coach_order_ke2_order_lv_showstuorder_dialog_item_tongyi);//ͬ��
			jujue = (TextView) v.findViewById(R.id.coach_order_ke2_order_lv_showstuorder_dialog_item_jujue);//�ܾ�
			
			
			yjujue=(TextView) v.findViewById(R.id.coach_order_ke2_order_lv_showstuorder_dialog_item_yjujue);//�Ѿܾ�
			genggai=(TextView) v.findViewById(R.id.coach_order_ke2_order_lv_showstuorder_dialog_item_genggai);//����
	
		}

		
	}
	
	
	
}
