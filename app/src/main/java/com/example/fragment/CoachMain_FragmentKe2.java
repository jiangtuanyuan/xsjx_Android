package com.example.fragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import com.example.Coach.Activity.CoachMainActivity;
import com.example.Coach.Activity.CoachOrderKe2Activity;
import com.example.Coach.Utils.CoachInfo;
import com.example.Coach.Utils.DaySeasonDate;
import com.example.Coach.Utils.Ke2Ke3Utils;
import com.example.Coach.entity.Ke2order;
import com.example.Coach.entity.Stuinfo;
import com.example.Coach.entity.Timeorder;
import com.example.Coach.entity.daythree;
import com.example.Coach.entity.makedate;
import com.example.Coach.entity.season;
import com.example.internet.AccessInternet;
import com.example.utils.Utils;
import com.example.xsjx.EnrollActivity;
import com.example.xsjx.MainApplication;
import com.example.xsjx.R;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CoachMain_FragmentKe2 extends Fragment implements OnClickListener {

	private RelativeLayout re;
	private LinearLayout li;
	private TextView notext;

	private TextView Season, showSeason;
	private SwipeRefreshLayout swipelayout;
	// �ָ���
	private int orderlayout1ID = R.id.order_layout1;

	
	// �������
	private int[] headBottomRE = new int[] { R.id.order_layout1_headBottom1_re1, R.id.order_layout1_headBottom1_re2,
			R.id.order_layout1_headBottom1_re3, R.id.order_layout1_headBottom2_re1, R.id.order_layout1_headBottom2_re2,
			R.id.order_layout1_headBottom2_re3, R.id.order_layout1_headBottom3_re1, R.id.order_layout1_headBottom3_re2,
			R.id.order_layout1_headBottom3_re3, R.id.order_layout1_headBottom4_re1, R.id.order_layout1_headBottom4_re2,
			R.id.order_layout1_headBottom4_re3, R.id.order_layout1_headBottom5_re1, R.id.order_layout1_headBottom5_re2,
			R.id.order_layout1_headBottom5_re3, R.id.order_layout1_headBottom6_re1, R.id.order_layout1_headBottom6_re2,
			R.id.order_layout1_headBottom6_re3 };
	private int[] headBottomTV = new int[] { R.id.order_layout1_headBottom1_re1_ordertv1,
			R.id.order_layout1_headBottom1_re2_ordertv2, R.id.order_layout1_headBottom1_re3_ordertv3,
			R.id.order_layout1_headBottom2_re1_ordertv1, R.id.order_layout1_headBottom2_re2_ordertv2,
			R.id.order_layout1_headBottom2_re3_ordertv3, R.id.order_layout1_headBottom3_re1_ordertv1,
			R.id.order_layout1_headBottom3_re2_ordertv2, R.id.order_layout1_headBottom3_re3_ordertv3,
			R.id.order_layout1_headBottom4_re1_ordertv1, R.id.order_layout1_headBottom4_re2_ordertv2,
			R.id.order_layout1_headBottom4_re3_ordertv3, R.id.order_layout1_headBottom5_re1_ordertv1,
			R.id.order_layout1_headBottom5_re2_ordertv2, R.id.order_layout1_headBottom5_re3_ordertv3,
			R.id.order_layout1_headBottom6_re1_ordertv1, R.id.order_layout1_headBottom6_re2_ordertv2,
			R.id.order_layout1_headBottom6_re3_ordertv3, };

	private int[] headBottomTVMsg = new int[] { R.id.order_layout1_headBottom1_re1_ordertv1_msg,
			R.id.order_layout1_headBottom1_re2_ordertv2_msg, R.id.order_layout1_headBottom1_re3_ordertv3_msg,
			R.id.order_layout1_headBottom2_re1_ordertv1_msg, R.id.order_layout1_headBottom2_re2_ordertv2_msg,
			R.id.order_layout1_headBottom2_re3_ordertv3_msg, R.id.order_layout1_headBottom3_re1_ordertv1_msg,
			R.id.order_layout1_headBottom3_re2_ordertv2_msg, R.id.order_layout1_headBottom3_re3_ordertv3_msg,
			R.id.order_layout1_headBottom4_re1_ordertv1_msg, R.id.order_layout1_headBottom4_re2_ordertv2_msg,
			R.id.order_layout1_headBottom4_re3_ordertv3_msg, R.id.order_layout1_headBottom5_re1_ordertv1_msg,
			R.id.order_layout1_headBottom5_re2_ordertv2_msg, R.id.order_layout1_headBottom5_re3_ordertv3_msg,
			R.id.order_layout1_headBottom6_re1_ordertv1_msg, R.id.order_layout1_headBottom6_re2_ordertv2_msg,
			R.id.order_layout1_headBottom6_re3_ordertv3_msg, };

	private TextView[] orderLefeTextView = new TextView[3];
	private TextView[] orderHeadTextView = new TextView[6];
	private List<orderview> list = new ArrayList<orderview>();

	// �ָ���
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View content = inflater.inflate(R.layout.coach_main_ke2, container, false);
		initView(content);
		// initData();
		initListener();
		return content;
	}

	private void initView(View v) {
		re = (RelativeLayout) v.findViewById(R.id.coach_main_ke2_Re_nocoach);
		li = (LinearLayout) v.findViewById(R.id.coach_main_ke2_Li_yescoach);
		notext = (TextView) v.findViewById(R.id.coach_main_ke2_Re_notextv);

		showSeason = (TextView) v.findViewById(R.id.coach_main_ke2_ShowSeason);
		Season = (TextView) v.findViewById(R.id.coach_main_ke2_Season);
		swipelayout=(SwipeRefreshLayout) v.findViewById(R.id.coach_main_ke2_SwipeRefreshLayout);
		// ��ߵ�ʱ��
		orderLefeTextView[0] = (TextView) v.findViewById(orderlayout1ID).findViewById(R.id.order_layout1_left_shangwu);
		orderLefeTextView[1] = (TextView) v.findViewById(orderlayout1ID).findViewById(R.id.order_layout1_left_xiawu);
		orderLefeTextView[2] = (TextView) v.findViewById(orderlayout1ID).findViewById(R.id.order_layout1_left_yejian);
		// ͷ������������
		orderHeadTextView[0] = (TextView) v.findViewById(orderlayout1ID).findViewById(R.id.order_layout1_head_date1);
		orderHeadTextView[1] = (TextView) v.findViewById(orderlayout1ID).findViewById(R.id.order_layout1_head_date2);
		orderHeadTextView[2] = (TextView) v.findViewById(orderlayout1ID).findViewById(R.id.order_layout1_head_date3);
		orderHeadTextView[3] = (TextView) v.findViewById(orderlayout1ID).findViewById(R.id.order_layout1_head_date4);
		orderHeadTextView[4] = (TextView) v.findViewById(orderlayout1ID).findViewById(R.id.order_layout1_head_date5);
		orderHeadTextView[5] = (TextView) v.findViewById(orderlayout1ID).findViewById(R.id.order_layout1_head_date6);
		// ����м������ID
		for (int i = 0; i < 18; i++) {
			orderview order = new orderview();
			order.re = (RelativeLayout) v.findViewById(orderlayout1ID).findViewById(headBottomRE[i]);
			order.re.setBackgroundResource(R.drawable.fragment2_mycoach_table_textview_gary_bg);
			order.conten = (TextView) v.findViewById(orderlayout1ID).findViewById(headBottomTV[i]);
			order.conten.setText("������..");
			order.msg = (TextView) v.findViewById(orderlayout1ID).findViewById(headBottomTVMsg[i]);
			order.msg.setVisibility(View.GONE);
			
			order.re.setOnClickListener(this);
			
			list.add(order);
		}
	}

	private void initData() {

		showSeason.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		showSeason.setOnClickListener(this);
		
		swipelayout.setColorSchemeColors(Color.BLUE,Color.RED,Color.YELLOW);
		// ������ָ����Ļ�������پ���ᴥ������ˢ��
		swipelayout.setDistanceToTriggerSync(400);
		// �趨����ԲȦ�ı���
		swipelayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
		// ����ԲȦ�Ĵ�С
		swipelayout.setSize(SwipeRefreshLayout.LARGE);
		
		//swipelayout.setEnabled(false);
		
		// ���õ�ǰ����ԤԼʱ��
		season sea = DaySeasonDate.seasonlist.get(DaySeasonDate.seasontf);
		Season.setText(sea.seasonname);
		switch (sea.seasonid) {
		case 1:// ���� ��ɫ
			Season.setTextColor(ContextCompat.getColor(MainApplication.baseContext, R.color.green));
			break;
		case 2:
			// �����ɫ
			Season.setTextColor(ContextCompat.getColor(MainApplication.baseContext, R.color.yellow));
			break;
		case 3:
			// �����ɫ
			Season.setTextColor(ContextCompat.getColor(MainApplication.baseContext, R.color.jinse));
			break;

		case 4:
			// �����ɫ
			Season.setTextColor(ContextCompat.getColor(MainApplication.baseContext, R.color.black));
			break;
		default:
			break;
		}
//�������
		for (int i = 0; i < DaySeasonDate.seasonlist.get(DaySeasonDate.seasontf).daythree.size(); i++) {
			if (i > 3)
				break;
			daythree day = DaySeasonDate.seasonlist.get(DaySeasonDate.seasontf).daythree.get(i);
			orderLefeTextView[i].setText(day.daythreename + "\n(" + day.starttime + "-" + day.endtime + ")");
			orderLefeTextView[i].setTag(day.daythreeid);
		}
  //ͷ������
		for (int i = 0,j=orderHeadTextView.length-1;i<orderHeadTextView.length; i++,j--) {
			makedate make = DaySeasonDate.makelist.get(j);
			orderHeadTextView[i].setText(make.date + "\n" + make.week);
			orderHeadTextView[i].setTag(make.id);
		}

		
		
	}

	private void initListener() {
		
		swipelayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				//����ˢ��
				//Video1SR.setRefreshing(false);
				
				
				//handler.sendEmptyMessageDelayed(1, 2000);
				Utils.showToast(getActivity(),"����ˢ����..");
				
				swiid=1;
				summsg=0;//֪ͨ������Ϣ����
				AccessInternet.getOrderKe23(handler, CoachInfo.id, CoachInfo.ke);
				
			}
		});
		
		
	}
	int swiid=0;
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:// �޸��Ƿ���ʾ�������� ��Ŀ������
				if (CoachInfo.ke == 2) {
					re.setVisibility(View.GONE);
					li.setVisibility(View.VISIBLE);
					// ���ؿؼ�
					initData();		
					AccessInternet.getOrderKe23(handler, CoachInfo.id, CoachInfo.ke);
					
				} else {
					li.setVisibility(View.GONE);
					re.setVisibility(View.VISIBLE);
					notext.setText("��Ǹ,�����ǿ�Ŀ������,��ʱ�޷��鿴��Ŀ����ԤԼ���!");
				}
				break;
			case 1:
				swipelayout.setRefreshing(false);
				break;
			case 3:
				Utils.showToast(getActivity(), msg.obj+"");
				
				Log.e("error", msg.obj+"");
				break;
			case 4://���տ�Ŀ����ԤԼ��Ϣ
				if(swiid!=0)
				Utils.showToast(getActivity(),"ˢ�³ɹ�!");
				swipelayout.setRefreshing(false);
				
				Ke2Ke3Utils.ListKe2.clear();
				Ke2Ke3Utils.ListKe2.addAll((List<Ke2order>) msg.obj);
				
				//Utils.showToast(getActivity(), "������+size()="+CoachInfo.ListKe2.size());
				//ListKe2.addAll(msg.obj);
				
				setTableData();//���ñ������
				
				break;
			case 5:
				setTableData();//OrderKe2 �������ˢ��
				break;

			default:
				break;
			}
		}
	};

	public void MainHandler() {
		handler.sendEmptyMessage(0);
	}


	int ListKe2IDI=0;
	int summsg=0;
	public void setTableData() {
	
		for (ListKe2IDI = 0; ListKe2IDI < Ke2Ke3Utils.ListKe2.size(); ListKe2IDI++) {

	      final Ke2order ke2order = Ke2Ke3Utils.ListKe2.get(ListKe2IDI);

	      switch (ke2order.getState()) {
		case 0:
			//�ó��α������ر�! 
			list.get(ListKe2IDI).conten.setText("���ѹرոó���!(�����������)");
			list.get(ListKe2IDI).re.setBackgroundResource(R.drawable.fragment2_mycoach_table_textview_gary_bg);// ���û�ɫ����
			break;
         case 1://�����ó���ԤԼ��ID!=0��
        	 int sum = ke2order.getTimeorder().size();//1.�����м���ʱ���  ����ʱ��� ���������Ǽ���
        	
        	 int ok=0; //�Ѿ�ԤԼ�õ�����
        	
        	 int msg=0;//��ʾ��ɫ��Ϣ������ 0�Ͳ���ʾ
        	 
        	 for(int i=0;i<sum;i++){
        		 //����ʱ���������ԤԼ��ԤԼѧԱ
        		 Timeorder timeorder=ke2order.getTimeorder().get(i);//�ó���i��ʱ���������ѧԱԤԼ
        		 
        		   int stusum=timeorder.getStuinfo().size();//�鿴���ʱ����ж��ѧԱԤԼ
        		      
        		   int stumsg=0;
        		   for(int k=0;k<stusum;k++){
        			   //�鿴ԤԼѧԱ�����Ƿ�����  orderstate ״̬  0δ���� 1ȷ��  2ѧԱȡ�� 3 �����ܾ�
        			   Stuinfo stuinfo=timeorder.getStuinfo().get(k);
        			   
        			   if(stuinfo.getOrderstate()==1 || stuinfo.getOrderstate()==2){
        				   //��ѧԱԤԼ�ɹ��� �ͽ�������ѭ�� Ȼ�� OK+1
        				   ok+=1;
        				   stumsg=0;
        				   if(stuinfo.getOrderstate()==2)
        				     {stumsg+=1;summsg++;}
        				   
        				   break;
        			   }
        			   else {
        				   if(stuinfo.getOrderstate()==0)
        				      {stumsg++;summsg++;}
        				  
        				   
        			     }  
        			   
        		   }
        		   msg+=stumsg;
        	}

				list.get(ListKe2IDI).conten
						.setText("��ԤԼ:" + sum + "��\n" + "��ԤԼ:" + ok + "��\n" + "ʣ  ��:" + (sum - ok) + "��");
				if(sum==ok)
				list.get(ListKe2IDI).re
				.setBackgroundResource(R.drawable.fragment2_mycoach_table_textview_red_bg);
				else list.get(ListKe2IDI).re
				.setBackgroundResource(R.drawable.fragment2_mycoach_table_textview_green_bg);
				
				
				list.get(ListKe2IDI).msg.setText(msg + "");
				
				if(msg!=0)
				list.get(ListKe2IDI).msg.setVisibility(View.VISIBLE);
				else list.get(ListKe2IDI).msg.setVisibility(View.GONE);

			break;
			
        case 2://δ�����ó���ԤԼ��δ������ݣ�
        	list.get(ListKe2IDI).conten.setText("��δ�����ó���!(�����������)");
			list.get(ListKe2IDI).msg.setVisibility(View.GONE);
			list.get(ListKe2IDI).re.setBackgroundResource(R.drawable.fragment2_mycoach_table_textview_gary_bg);
        	
        	
	     break;
		default:
			list.get(ListKe2IDI).conten.setText("�ó��������쳣!");
			list.get(ListKe2IDI).re.setBackgroundResource(R.drawable.fragment2_mycoach_table_textview_gary_bg);// ���û�ɫ����
			list.get(ListKe2IDI).re.setEnabled(false);
			break;
		}
	      list.get(ListKe2IDI).re.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					 Intent intent = new Intent(getActivity(), CoachOrderKe2Activity.class);
                   CoachOrderKe2Activity.ke2order=ke2order;
					 getActivity().startActivity(intent);
				}
			});
		}
	
		sendNotification(summsg);
	}

	
	
	
	@Override
	public void onClick(View vv) {
		switch (vv.getId()) {
		case R.id.coach_main_ke2_ShowSeason:
			// Utils.showToast(getActivity(), "��ʾ����ԤԼʱ��!");
			showsessondateDialog();
			break;
	
		default:
			break;
		}

	}

	public Dialog Dcoach = null;
	public View Vcoach = null;
	public LayoutInflater inflater = null;
	public TableLayout seasontablayou;

	public void showsessondateDialog() {
		if (inflater == null)
			inflater = LayoutInflater.from(getActivity());
		if (Dcoach == null)
			Dcoach = new Dialog(getActivity(), R.style.testDialog);
		if (Vcoach == null) {
			Vcoach = inflater.inflate(R.layout.coach_main_ke2_show_seasondate, null);
			seasontablayou = (TableLayout) Vcoach.findViewById(R.id.coach_main_ke2_show_seasondate_tab);

			Context conten = getActivity();
			for (int i = 0; i < 4; i++) {
				season se = DaySeasonDate.seasonlist.get(i);
				for (int j = 0; j < DaySeasonDate.seasonlist.get(i).daythree.size(); j++) {
					TableRow tatow = getTableRow(conten);
					daythree day = DaySeasonDate.seasonlist.get(i).daythree.get(j);
					switch (se.seasonid) {
					case 1:// ���� ��ɫ
						tatow.addView(getTextView(conten, se.seasonname, R.color.green));
						tatow.addView(getTextView(conten, day.daythreename, R.color.green));
						tatow.addView(getTextView(conten, day.starttime, R.color.green));
						tatow.addView(getTextView(conten, day.endtime, R.color.green));

						break;
					case 2:
						// �����ɫ
						tatow.addView(getTextView(conten, se.seasonname, R.color.yellow));
						tatow.addView(getTextView(conten, day.daythreename, R.color.yellow));
						tatow.addView(getTextView(conten, day.starttime, R.color.yellow));
						tatow.addView(getTextView(conten, day.endtime, R.color.yellow));
						break;
					case 3:
						// �����ɫ
						tatow.addView(getTextView(conten, se.seasonname, R.color.jinse));
						tatow.addView(getTextView(conten, day.daythreename, R.color.jinse));
						tatow.addView(getTextView(conten, day.starttime, R.color.jinse));
						tatow.addView(getTextView(conten, day.endtime, R.color.jinse));
						break;

					case 4:
						// �����ɫ
						tatow.addView(getTextView(conten, se.seasonname, R.color.gary));
						tatow.addView(getTextView(conten, day.daythreename, R.color.gary));
						tatow.addView(getTextView(conten, day.starttime, R.color.gary));
						tatow.addView(getTextView(conten, day.endtime, R.color.gary));
						break;
					default:
						tatow.addView(getTextView(conten, se.seasonname, R.color.green));
						tatow.addView(getTextView(conten, day.daythreename, R.color.green));
						tatow.addView(getTextView(conten, day.starttime, R.color.green));
						tatow.addView(getTextView(conten, day.endtime, R.color.green));
						break;
					}
					seasontablayou.addView(tatow);
				}
			}
		}
		Dcoach.setContentView(Vcoach);
		Dcoach.show();

		WindowManager.LayoutParams lp = Dcoach.getWindow().getAttributes();
		lp.width = MainApplication.phoneWidth;

		// lp.height = MainApplication.phoneHeight;
		Dcoach.getWindow().setAttributes(lp);

	}

	// ��ȡ������
	public TableRow getTableRow(Context context) {
		TableRow tatow = new TableRow(getActivity());
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0, 5, 0, 0);
		
		tatow.setLayoutParams(layoutParams);

		tatow.setOrientation(LinearLayout.HORIZONTAL);
		return tatow;
	}

	public TextView getTextView(Context context, String conten, int color) {
		TextView text = new TextView(context);
		text.setText(conten);
		text.setTextColor(ContextCompat.getColor(context, color));
/*
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
		text.setLayoutParams(layoutParams);
*/
		text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
				TableRow.LayoutParams.WRAP_CONTENT, 1.0f));

		text.setGravity(Gravity.CENTER_HORIZONTAL);
		return text;
	}

	/**
	 * ֪ͨ����ʾ �ж�����δ�������Ϣ
	 */
	

	public  void sendNotification(int msg) {
		
		if(msg!=0)
		{
		// ��ȡNotificationManagerʵ��
		NotificationManager notifyManager = (NotificationManager)getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
		// ʵ����NotificationCompat.Builde�������������
		NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
				// ����Сͼ��
				.setSmallIcon(R.drawable.xsjxlog)
				// ����֪ͨ����
				.setContentTitle("��Ŀ����ԤԼ��Ϣ!")
				// ����֪ͨ����
				.setContentText("����"+msg+"λѧԱ����ԤԼ,�뾡�촦��!!")
				.setWhen(System.currentTimeMillis())
				;

		// ����֪ͨʱ�䣬Ĭ��Ϊϵͳ����֪ͨ��ʱ�䣬ͨ����������
		// .setWhen(System.currentTimeMillis());
		// ͨ��builder.build()��������Notification����,������֪ͨ,id=1
		
		notifyManager.notify(1, builder.build());
		}

	}
	

	/**
	 * ԤԼ�������
	 */
	class orderview {
		RelativeLayout re;
		TextView conten, msg;
		
	}

}
