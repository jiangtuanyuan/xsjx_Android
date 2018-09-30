package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.Coach.Activity.CoachOrderKe2Activity;
import com.example.Coach.Utils.CoachInfo;
import com.example.Coach.Utils.DaySeasonDate;
import com.example.Coach.entity.daythree;
import com.example.Coach.entity.makedate;
import com.example.Coach.entity.season;
import com.example.fragment.CoachMain_FragmentKe2.orderview;
import com.example.utils.Utils;
import com.example.xsjx.MainApplication;
import com.example.xsjx.R;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CoachMain_FragmentKe3 extends Fragment implements OnClickListener {

	private RelativeLayout re;
	private LinearLayout li;
	private TextView notext;

	private TextView Season, showSeason;
	private SwipeRefreshLayout swipelayout;
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View content = inflater.inflate(R.layout.coach_main_ke2, container, false);
		initView(content);

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
			order.conten.setText("��δ����!");
			order.msg = (TextView) v.findViewById(orderlayout1ID).findViewById(headBottomTVMsg[i]);
			order.msg.setVisibility(View.GONE);
			
			list.add(order);
		}

	}

	private void initListener() {
		swipelayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				//����ˢ��
				//Video1SR.setRefreshing(false);
				handler.sendEmptyMessageDelayed(1, 2000);
			}
		});
	}

	private void initData() {
		showSeason.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		showSeason.setOnClickListener(this);
		
		swipelayout.setColorSchemeColors(Color.BLUE,Color.RED,Color.YELLOW);
		// ������ָ����Ļ�������پ���ᴥ������ˢ��
		swipelayout.setDistanceToTriggerSync(200);
		// �趨����ԲȦ�ı���
		swipelayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
		// ����ԲȦ�Ĵ�С
		swipelayout.setSize(SwipeRefreshLayout.LARGE);
		
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
		

		for(int i=0;i<DaySeasonDate.seasonlist.get(DaySeasonDate.seasontf).daythree.size();i++){
			if(i>3)
				break;
			daythree day=DaySeasonDate.seasonlist.get(DaySeasonDate.seasontf).daythree.get(i);
			orderLefeTextView[i].setText(day.daythreename+"\n("+day.starttime+"-"+day.endtime+")");
			orderLefeTextView[i].setTag(day.daythreeid);
		}
		
		for (int i = 0,j=5;i< DaySeasonDate.makelist.size(); i++,j--) {
			if (i >5)
				break;
			makedate make = DaySeasonDate.makelist.get(i);
			
			orderHeadTextView[j].setText(make.date + "\n" + make.week);
			orderHeadTextView[j].setTag(make.id);

		}
		

		list.get(5).conten.setText("����!");
		list.get(5).msg.setText("99");
		list.get(5).re.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getActivity(), CoachOrderKe2Activity.class);
				startActivity(intent);

			}
		});

	}

	public Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:// �޸��Ƿ���ʾ�������� ��Ŀ������
				if (CoachInfo.ke == 3) {
					re.setVisibility(View.GONE);
					li.setVisibility(View.VISIBLE);
					initData();

				} else {
					li.setVisibility(View.GONE);
					re.setVisibility(View.VISIBLE);
					notext.setText("��Ǹ,�����ǿ�Ŀ������,��ʱ�޷��鿴��Ŀ����ԤԼ���!");
				}
				break;
			case 1:
				swipelayout.setRefreshing(false);
				break;
				
				
			default:
				break;
			}
		}
	};

	public void MainHandler() {
		handler.sendEmptyMessage(0);
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
		
		Context conten=getActivity();
		for(int i=0;i<DaySeasonDate.seasonlist.size();i++){
			season se=DaySeasonDate.seasonlist.get(i);
			for(int j=0;j<DaySeasonDate.seasonlist.get(i).daythree.size();j++){
				TableRow tatow=getTableRow(conten);
				daythree day=DaySeasonDate.seasonlist.get(i).daythree.get(j);
				switch (se.seasonid) {
				case 1:// ���� ��ɫ
					tatow.addView(getTextView(conten,se.seasonname, R.color.green)); 
					tatow.addView(getTextView(conten,day.daythreename, R.color.green)); 
					tatow.addView(getTextView(conten,day.starttime, R.color.green)); 
					tatow.addView(getTextView(conten,day.endtime, R.color.green)); 
					
					
					break;
				case 2:
					// �����ɫ
					tatow.addView(getTextView(conten,se.seasonname, R.color.yellow)); 
					tatow.addView(getTextView(conten,day.daythreename, R.color.yellow)); 
					tatow.addView(getTextView(conten,day.starttime, R.color.yellow)); 
					tatow.addView(getTextView(conten,day.endtime, R.color.yellow)); 
					break;
				case 3:
					// �����ɫ
					tatow.addView(getTextView(conten,se.seasonname, R.color.jinse)); 
					tatow.addView(getTextView(conten,day.daythreename, R.color.jinse)); 
					tatow.addView(getTextView(conten,day.starttime, R.color.jinse)); 
					tatow.addView(getTextView(conten,day.endtime, R.color.jinse)); 
					break;

				case 4:
					// �����ɫ
					tatow.addView(getTextView(conten,se.seasonname, R.color.gary)); 
					tatow.addView(getTextView(conten,day.daythreename, R.color.gary));
					tatow.addView(getTextView(conten,day.starttime, R.color.gary)); 
					tatow.addView(getTextView(conten,day.endtime, R.color.gary)); 
					break;
				default:
					tatow.addView(getTextView(conten,se.seasonname, R.color.green)); 
					tatow.addView(getTextView(conten,day.daythreename, R.color.green)); 
					tatow.addView(getTextView(conten,day.starttime, R.color.green)); 
					tatow.addView(getTextView(conten,day.endtime, R.color.green)); 
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

		//lp.height = MainApplication.phoneHeight;
		Dcoach.getWindow().setAttributes(lp);
		
	}
   //��ȡ������
	public TableRow getTableRow(Context context) {
		TableRow tatow = new TableRow(getActivity());
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.setMargins(0, 5, 0, 0);
		tatow.setLayoutParams(layoutParams);

		tatow.setOrientation(LinearLayout.HORIZONTAL);
		return tatow;
	}
	
	public TextView getTextView(Context context,String conten,int color) {
		TextView text=new TextView(context);
		text.setText(conten);
		text.setTextColor(ContextCompat.getColor(context, color));
		text.setTextSize(15);
		/*
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
		//text.setLayoutParams(layoutParams);
          */
		
		text.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,1.0f));

		
		text.setGravity(Gravity.CENTER_HORIZONTAL);
		return text;
	}
	
	
	
	
	class orderview {
		RelativeLayout re;
		TextView conten, msg;
	}

	@Override
	public void onClick(View vv) {
		switch (vv.getId()) {
		case R.id.coach_main_ke2_ShowSeason:
			showsessondateDialog();
			break;

		default:
			break;
		}

	}

}
