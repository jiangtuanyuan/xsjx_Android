package com.example.Coach.adapter;

import java.util.List;

import com.example.Coach.Activity.CoachOrderKe2Activity;
import com.example.Coach.entity.OrderAdapterEntity;
import com.example.Coach.entity.Stuinfo;
import com.example.Coach.entity.Timeorder;
import com.example.utils.NoDoubleClickListener;
import com.example.utils.Utils;
import com.example.xsjx.LogoDoActivity;
import com.example.xsjx.MainApplication;
import com.example.xsjx.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.R.integer;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class OrderaAdapter extends BaseAdapter {
	public Context context;
	
	public List<Timeorder> list;

	public OrderaAdapter(List<Timeorder> list,Context context) {
		this.context = context;
		this.list=list;
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
	public View getView(final int p, View view, ViewGroup arg2) {
		HorldView h = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.coach_order_ke2_order_lv_item, null);
			h = new HorldView(view);
			view.setTag(h);

		} else {
			h = (HorldView) view.getTag();
		}
		
		
		final Timeorder timeorder=list.get(p);
		
		h.starttime.setText(timeorder.getCcstarttime());
		h.endtime.setText(timeorder.getCcendtime());
		
		List<Stuinfo> listStu=timeorder.getStuinfo();
	
	if(listStu.size()==0){
			h.orderMsg.setText("0������!");
			h.chakanMsg.setVisibility(View.GONE);
			h.chakan.setBackgroundResource(R.drawable.cehua_gary_bj);
			h.chakan.setTextColor(ContextCompat.getColor(context, R.color.gary));
			
			h.Delect.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					
					//Utils.showToast(context, "�Ƿ�ɾ��0��ԤԼ��ʱ���?");
					
					removeTimedate(p);
					
					
				}
			});
		}
		else{//����ԤԼ
			int jujue=0;//�ܾ�������
			
			int sum=listStu.size();
			int num=0;
			for(int i=0;i<sum;i++){
				final Stuinfo stuinfo=listStu.get(i);
				 if(stuinfo.getOrderstate()==1 || stuinfo.getOrderstate()==2){
  				    
					 if(stuinfo.getOrderstate()==2){
						 h.chakanMsg.setVisibility(View.VISIBLE);
						 h.chakanMsg.setText(1+"");
						
					 } else  h.chakanMsg.setVisibility(View.GONE);
					 //��ѧԱԤԼ�ɹ��� �ͽ�������ѭ��
					
					 //�鿴
					 h.chakan.setOnClickListener(new NoDoubleClickListener() {
				    	 @Override
				    	 public void onNoDoubleClick(View v) {
				    		// Utils.showToast(context, "�鿴�Ѿ�ԤԼ�ɹ���ѧԱ!");
				    		 
				    		 showstuinfo(timeorder.getCcke2id(),timeorder.getCcstarttime(),timeorder.getCcendtime(),stuinfo);
				    		 
				    		 
				    		}
					});
					 //ɾ��
				     h.Delect.setTextColor(ContextCompat.getColor(context, R.color.gary));
				     h.Delect.setBackgroundResource(R.drawable.cehua_gary_bj);
				     h.Delect.setOnClickListener(new NoDoubleClickListener() {
				    	 @Override
				    	 public void onNoDoubleClick(View v) {
				    		 Utils.showToast(context, "����ѧԱԤԼ,����ɾ��!");
				    		 
				    		}
					});
				     
					 h.orderMsg.setText(stuinfo.getUsername());
					 h.orderMsg.setTextColor(ContextCompat.getColor(context, R.color.blue));
					 num=0;
  				   break;
  			   }
				 else{
					 if(stuinfo.getOrderstate()==0)
					    num++;
					 if(stuinfo.getOrderstate()==3)
						 jujue++;
				 }
				
				 
  			   if(num!=0){
  				   
  				 h.orderMsg.setText(num+"������!");
  				 
  				 h.chakanMsg.setVisibility(View.VISIBLE);
  				 h.chakanMsg.setText(num+"");
  				 
  				 h.chakan.setOnClickListener(new NoDoubleClickListener() {
  					@Override
			    	 public void onNoDoubleClick(View v) {
			    		// Utils.showToast(context, "�鿴N���˵�����");
			    		 showstuorder(timeorder.getCcke2id(),timeorder.getCcstarttime(),timeorder.getCcendtime(),timeorder.getStuinfo(),p);
			    		}
				});
  				 
  			     h.Delect.setTextColor(ContextCompat.getColor(context, R.color.gary));
			     h.Delect.setBackgroundResource(R.drawable.cehua_gary_bj);
			     h.Delect.setOnClickListener(new NoDoubleClickListener() {
			    	 @Override
			    	 public void onNoDoubleClick(View v) {
			    		 Utils.showToast(context, "����ѧԱԤԼ,����ɾ��!");
			    		 
			    		}
				   });
  				 
  			   }
  			   
  			   else if(jujue!=0){
  				   
                 h.orderMsg.setText("���ܾ���"+jujue+"������!");
  				 h.chakanMsg.setVisibility(View.GONE);
  				 h.chakan.setOnClickListener(new NoDoubleClickListener() {
   					@Override
 			    	 public void onNoDoubleClick(View v) {
 			    		// Utils.showToast(context, "�鿴0���˵�����");//�鿴�ܾ���Ա
			   showstuorder(timeorder.getCcke2id(),timeorder.getCcstarttime(),timeorder.getCcendtime(),timeorder.getStuinfo(),p);
		 
 			    		}
 				});
  			
  				 
  				 h.Delect.setTextColor(ContextCompat.getColor(context, R.color.gary));
			     h.Delect.setBackgroundResource(R.drawable.cehua_gary_bj);
			     h.Delect.setOnClickListener(new NoDoubleClickListener() {
			    	 @Override
			    	 public void onNoDoubleClick(View v) {
			    		 Utils.showToast(context, "�оܾ�ѧԱ��ԤԼ,����ɾ��!");
			    		 
			    		}
				   });
  				 
  				 
  				 
  			   }
  		   
				
				
			}	
		}
	
		return view;
	}
	/**
	 * ѯ���Ƿ�ɾ��û��ԤԼ��ʱ���
	 */
	Builder bu1=null; 
	public void removeTimedate(final int p){
		if(bu1==null){
		bu1= new Builder(context);
		bu1.setTitle("��ʾ:");
		bu1.setMessage("�Ƿ�ɾ����ǰ����?");
		bu1.setNegativeButton("ȡ��", null);
		bu1.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				
				//1.�ȴӽ�����ɾ�� ���ӷ�����ɾ��
				CoachOrderKe2Activity.ke2order.getTimeorder().remove(p);
				//���½�������
				CoachOrderKe2Activity.oadapter.notifyDataSetChanged();
				((CoachOrderKe2Activity) context).setLisetViewHeight();
				
				Utils.showToast(context, "ɾ���ɹ�,�뷵������ˢ�±��!");

			}
		});
		bu1.create();
	 }
 bu1.show();	
}
	
	/**
	 * �鿴ԤԼ�ɹ� ��ѧԱ ��������
	 */
	private LayoutInflater inflater = null;
	private Dialog Dstu = null;
	private View Vstu = null;
	private TextView Sdate,Stime,Stimeleng,Sstuname,Sliuyan,Sstate;
	private Button Byes,Bno,Bfanhui;
	
	public void showstuinfo(int ccke2id,String startime,String endtime,Stuinfo stuinfo)
	{ 
	  if (inflater == null)
			inflater = LayoutInflater.from(context);
		if (Dstu == null)
			Dstu = new Dialog(context, R.style.testDialog);
		if (Vstu == null)
			{  Vstu = inflater.inflate(R.layout.coach_order_ke2_order_lv_showstu_dialog, null);
			Sdate=(TextView) Vstu.findViewById(R.id.coach_order_ke2_order_lv_showstu_dialog_date);
			Stime=(TextView) Vstu.findViewById(R.id.coach_order_ke2_order_lv_showstu_dialog_time);
			Stimeleng=(TextView) Vstu.findViewById(R.id.coach_order_ke2_order_lv_showstu_dialog_timeleng);
			Sstuname=(TextView) Vstu.findViewById(R.id.coach_order_ke2_order_lv_showstu_dialog_stu);
			Sstuname.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
			Sliuyan=(TextView) Vstu.findViewById(R.id.coach_order_ke2_order_lv_showstu_dialog_liuyan);
			Sstate=(TextView) Vstu.findViewById(R.id.coach_order_ke2_order_lv_showstu_dialog_state);
			
			Byes=(Button) Vstu.findViewById(R.id.coach_order_ke2_order_lv_showstu_dialog_yes);
			Bno=(Button) Vstu.findViewById(R.id.coach_order_ke2_order_lv_showstu_dialog_no);
			Bfanhui=(Button) Vstu.findViewById(R.id.coach_order_ke2_order_lv_showstu_dialog_fanhui);
			Bfanhui.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
				
					Dstu.dismiss();
				}
			  });
			
			}
		
		Dstu.setContentView(Vstu);
		Dstu.show();
		WindowManager.LayoutParams lp = Dstu.getWindow().getAttributes();
		lp.width = MainApplication.phoneWidth;
		//lp.height=MainApplication.phoneHeight;
		Dstu.getWindow().setAttributes(lp);
//��������
//����
   Sdate.setText(CoachOrderKe2Activity.ke2order.getDate()+" "+CoachOrderKe2Activity.ke2order.getWeek()+" "+CoachOrderKe2Activity.ke2order.getDaythreeName());
   //ʱ��

     Stime.setText(startime+"-"+endtime);	
    //ʱ��
     Stimeleng.setText(Utils.getTimeLeng(startime,endtime)+"����");
     //ѧԱ����
     Sliuyan.setText(stuinfo.getUserliuyan());
     Sstuname.setText(stuinfo.getUsername());
     Sstuname.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Utils.showToast(context, "��Ǹ,��ʱ�޷��鿴ѧԱ����!");
			}
		});
     switch (stuinfo.getOrderstate()) {
		case 1:
			//ԤԼ�ɹ�
			Sstate.setText("ԤԼ�ɹ�!");
			Byes.setVisibility(View.GONE);
			Bno.setText("ȡ����ѧԱ��ԤԼ");
			Bno.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Utils.showToast(context, "ȡ����ѧԱ��ԤԼ��..");
					
				}
			});
			
			
			break;
     case 2:
			//ѧԱ����ȡ��ԤԼ
     	Sstate.setText("ѧԱ����ȡ��ԤԼ");
     	Byes.setVisibility(View.VISIBLE);
     	Byes.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Utils.showToast(context, "ͬ��ѧԱȡ��");
				}
			});
        Bno.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Utils.showToast(context, "�ܾ�ѧԱȡ��!");
					
				}
			});
     	
     	
			break;
			
		default:
			Sstate.setText("�����쳣!");
			Byes.setEnabled(false);
			Bno.setEnabled(false);
			
			break;
		}
 	 
	}
	/**
	 * �鿴ԤԼ��ѧԱ
	 */
	private Dialog Dstuorder = null;
	private View Vstuorder = null;
	private TextView odate,otime,otimeleng;
	private Button ofanhui;
	private ListView olistview;
	private OrderStuShowadapter oshowadapter;
	private void showstuorder(int ccke2id,String startime,String endtime,List<Stuinfo> liststu,int timep){
		if (inflater == null)
			inflater = LayoutInflater.from(context);
		if (Dstuorder == null)
			Dstuorder = new Dialog(context, R.style.testDialog);
		if (Vstuorder == null){
			Vstuorder = inflater.inflate(R.layout.coach_order_ke2_order_lv_showstuorder_dialog, null);
			odate=(TextView) Vstuorder.findViewById(R.id.coach_order_ke2_order_lv_showstuorder_dialog_date);
			otime=(TextView) Vstuorder.findViewById(R.id.coach_order_ke2_order_lv_showstuorder_dialog_time);
			otimeleng=(TextView) Vstuorder.findViewById(R.id.coach_order_ke2_order_lv_showstuorder_dialog_timeleng);
			ofanhui=(Button) Vstuorder.findViewById(R.id.coach_order_ke2_order_lv_showstuorder_dialog_fanhui);
			olistview= (ListView) Vstuorder.findViewById(R.id.coach_order_ke2_order_lv_showstuorder_dialog_listview);
			
			odate.setText(CoachOrderKe2Activity.ke2order.getDate()+" "+CoachOrderKe2Activity.ke2order.getWeek()+" "+CoachOrderKe2Activity.ke2order.getDaythreeName());
			otime.setText(startime+"-"+endtime);	
		    //ʱ��
			otimeleng.setText(Utils.getTimeLeng(startime,endtime)+"����");

			oshowadapter=new OrderStuShowadapter(liststu, context,timep);
			olistview.setAdapter(oshowadapter);
			//
			
		        }
		Dstuorder.setContentView(Vstuorder);
		Dstuorder.show();
		WindowManager.LayoutParams lp = Dstuorder.getWindow().getAttributes();
		lp.width = MainApplication.phoneWidth;
		//lp.height=MainApplication.phoneHeight;
		Dstuorder.getWindow().setAttributes(lp);
		
		Utils.setListViewHeightBasedOnChildren(olistview);
		Log.e("list height", olistview.getHeight()+"");
		
		ofanhui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Dstuorder.dismiss();
				
			}
		});
		
		
		
	}
	
	
	/**
	 * ��ʾ���ؿ�
	 */
	Dialog loaddialog=null;
	private void showLoadDialog(String c) {
		if (inflater == null)
			inflater = LayoutInflater.from(context);
		if(loaddialog==null)
		{loaddialog = new Builder(context).create();
		loaddialog.setCancelable(false);}
		
		loaddialog.show();
		// ע��˴�Ҫ����show֮�� ����ᱨ�쳣
	    View v=inflater.inflate(R.layout.loading_process_dialog_anim, null);
	    TextView stv=(TextView) v.findViewById(R.id.loading_dialog_tv);
	    stv.setText(c);
	    loaddialog.setContentView(v);
	  
   	}
	
	
	
	
	class HorldView {
		public RelativeLayout wRE;
		public RelativeLayout RE;
		
		public TextView starttime, endtime, orderMsg, chakan, chakanMsg, Delect;

		public HorldView(View v) {
			wRE = (RelativeLayout) v.findViewById(R.id.coach_order_ke2_order_lv_item_li);// �����Ĳ���
			RE = (RelativeLayout) v.findViewById(R.id.coach_order_ke2_order_lv_item_chakanRE);// ����鿴�Ĳ���

			starttime = (TextView) v.findViewById(R.id.coach_order_ke2_order_lv_item_starttime);// ��ʼʱ��
			endtime = (TextView) v.findViewById(R.id.coach_order_ke2_order_lv_item_endtime);// ����ʱ��
			orderMsg = (TextView) v.findViewById(R.id.coach_order_ke2_order_lv_item_orderMsg);// ԤԼ��Ϣ
			chakan = (TextView) v.findViewById(R.id.coach_order_ke2_order_lv_item_chakan);// �鿴TVview
			
			chakanMsg = (TextView) v.findViewById(R.id.coach_order_ke2_order_lv_item_chakan_Msg);// �鿴�ĺ�ɫ��Ϣ
			Delect = (TextView) v.findViewById(R.id.coach_order_ke2_order_lv_item_delet);// ɾ��

		}

	}

}
