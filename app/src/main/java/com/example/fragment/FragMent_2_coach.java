/*
package com.example.fragment;//package com.example.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.example.entity.Fragmen2_Mycoach_Table_ShowEntity;
import com.example.entity.Order_Spinner_TimeEntity;
import com.example.utils.GetDays;
import com.example.utils.NoDoubleClickListener;
import com.example.xsjx.MainApplication;
import com.example.xsjx.OrderActivity;
import com.example.xsjx.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("unused")
public class FragMent_2_coach extends View{
	public FragMent_2_coach(Context context) {
		super(context);
	}

	public static int[] TV_table_head_item = new int[] { R.id.fagment2_mycoach_tabel_head1, R.id.fagment2_mycoach_tabel_head2,
			R.id.fagment2_mycoach_tabel_head3, R.id.fagment2_mycoach_tabel_head4, R.id.fagment2_mycoach_tabel_head5,
			R.id.fagment2_mycoach_tabel_head6 };// ���ͷ��6��TV���ID
	public static int[] TV_table_left_itme = new int[] { R.id.fragment2_mycoach_left_dataDay,
			R.id.fragment2_mycoach_left_dataDay1, R.id.fragment2_mycoach_left_dataDay2,
			R.id.fragment2_mycoach_left_dataDay3, };// �����ߵ�4�����ID
	public static int[] TV_table_center_item = new int[] { R.id.fagment2_mycoach_tabel_data11,
			R.id.fagment2_mycoach_tabel_data12, R.id.fagment2_mycoach_tabel_data13, R.id.fagment2_mycoach_tabel_data21,
			R.id.fagment2_mycoach_tabel_data22, R.id.fagment2_mycoach_tabel_data23, R.id.fagment2_mycoach_tabel_data31,
			R.id.fagment2_mycoach_tabel_data32, R.id.fagment2_mycoach_tabel_data33, R.id.fagment2_mycoach_tabel_data41,
			R.id.fagment2_mycoach_tabel_data42, R.id.fagment2_mycoach_tabel_data43, R.id.fagment2_mycoach_tabel_data51,
			R.id.fagment2_mycoach_tabel_data52, R.id.fagment2_mycoach_tabel_data53, R.id.fagment2_mycoach_tabel_data61,
			R.id.fagment2_mycoach_tabel_data62, R.id.fagment2_mycoach_tabel_data63 };// ����м��18�����ID

	public static List<String> Tb_head_List = GetDays.getday();
	public static List<String> Tb_Left_List;
	public static List<Fragmen2_Mycoach_Table_ShowEntity> Tb_center_List;



	public static View getView(final Context context){
		 LayoutInflater inflater = LayoutInflater.from(context);
	     View view = inflater.inflate(R.layout.fragment_2_all_layout,null);

	     TextView TV_MyCoach = (TextView) view.findViewById(R.id.Fragment2_mycoach_MyCoach);
			TV_MyCoach.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
			TV_MyCoach.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {

				}
			});
			final int MyKeShi = new Random().nextInt(12) + 1;
			TextView Fragment2_mycoach_MyKeShi = (TextView) view.findViewById(R.id.Fragment2_mycoach_MyKeShi);
			Fragment2_mycoach_MyKeShi.setText(MyKeShi + "��");

			// δ��6��ʱ��
			for (int i = 0; i < 6; i++) {
				TextView TV_vie = (TextView) view.findViewById(TV_table_head_item[i]);
				TV_vie.setText(Tb_head_List.get(i));

			}
			// ��ߵ�ʱ������
			Tb_Left_List = getTableLeftData();
			for (int i = 0; i < 3; i++) {
				TextView TV_vie = (TextView) view.findViewById(TV_table_left_itme[i]);
				TV_vie.setText(Tb_Left_List.get(i));

			}
			// ���ñ���ڵ�����
			Tb_center_List = getTableCenterData();
			for (int k = 0; k < 18; k++) {
				TextView TV_vie = (TextView) view.findViewById(TV_table_center_item[k]);
				TV_vie.setGravity(Gravity.CENTER);
				@SuppressWarnings("unchecked")
			   List<String> TagList=new ArrayList<String>();

				TagList.add(Tb_center_List.get(k).date);
				TagList.add(Tb_center_List.get(k).DayTimeStr);
				TagList.add(Tb_center_List.get(k).SubjectsId+"");
				TagList.add(Tb_center_List.get(k).CarID+"");
				TagList.add(Tb_center_List.get(k).id+"");
				TagList.add(Tb_center_List.get(k).MycoachID+"");
				TagList.add(Tb_center_List.get(k).Sum+"");
				TagList.add(Tb_center_List.get(k).Reduce+"");
				TagList.add(Tb_center_List.get(k).Surplus+"");
				TagList.add(Tb_center_List.get(k).yes_on+"");

				for(int l=0;l<Tb_center_List.get(k).list.size();l++)
				{
					TagList.add(Tb_center_List.get(k).list.get(l).StratTime);
					TagList.add(Tb_center_List.get(k).list.get(l).EndTime);
					TagList.add(Tb_center_List.get(k).list.get(l).Order);

				}


	                       ������, ����, 2, 2, 5, 20, 3, 2, 1, 1,
	            8:00, 9:00, (��ԤԼ),
	            9:00, 10:00, (��ԤԼ),
	            10:00, 11:00, (δԤԼ)]


				TV_vie.setTag(TagList);



				TV_vie.setOnClickListener(new NoDoubleClickListener() {


				});

				TV_vie.setOnClickListener(new NoDoubleClickListener() {
					@SuppressWarnings("unchecked")
					@Override
					public void onNoDoubleClick(View arg0) {
						TextView vv = (TextView) arg0;
						String str = (String) vv.getText();
						if (str.equals("����!")) {
							//Toast.makeText(getActivity(), "��ǸӴ!��ǰ����ԤԼ����!ѡ�������İ�!", 1).show();
							   Builder bu1=new Builder(context);
							   bu1.setTitle("��ʾ:");
							   bu1.setMessage("��ǸӴ!\n��ǰ����ԤԼ����!\nѡ�������İ�!");
							   bu1.setNegativeButton("ȷ��", null);
							   bu1.create();
							   bu1.show();


						} else if (str.equals("����ԤԼ!")) {
						//	Toast.makeText(getActivity(), "��ǸӴ!��ǰ���β���ԤԼӴ!", 1).show();


							   Builder bu1=new Builder(context);
							   bu1.setTitle("��ʾ:");
							   bu1.setMessage("��ǸӴ!\n��ǰ���β���ԤԼӴ!");

							   bu1.setNegativeButton("ȷ��", null);

							   bu1.create();
							   bu1.show();

						}

						else if (MyKeShi > 0) {

							Toast.makeText(context, vv.getTag() + str, 1).show();

							Log.v("TagList", vv.getTag()+"");


							//FragMent_2.startOrder((ArrayList<String>) vv.getTag());



							startActivityForResult(intent, 1);




						} else {

							//Toast.makeText(getActivity(), "��Ǹ,��Ŀ�ʱΪ0!�޷�ԤԼ!", 1).show();
							   Builder bu1=new Builder(context);
							   bu1.setTitle("��ʾ:");
							   bu1.setMessage("�޷�ԤԼ!\n��Ǹ,��Ŀ�ʱΪ0!");
							   bu1.setNegativeButton("ȷ��", null);
							   bu1.create();
							   bu1.show();

						}

					}
				});

			if (Tb_center_List.get(k).yes_on == 1) {

					if (Tb_center_List.get(k).Surplus > 0) {
						String str = "��ԤԼ:" + Tb_center_List.get(k).Sum + "��\n" + "��ԤԼ:" + Tb_center_List.get(k).Reduce
								+ "��\n" + "ʣ    ��:" + Tb_center_List.get(k).Surplus + "��";
						TV_vie.setText(str);
						TV_vie.setTextColor(context.getResources().getColor(R.color.fragmen2_mycoach_table_keyuyue_text_color));
						TV_vie.setBackgroundResource(R.drawable.fragment2_mycoach_table_textview_green_bg);



					} else {
						String str = "����!";
						TV_vie.setText(str);
						TV_vie.setTextColor(context.getResources().getColor(R.color.fragmen2_mycoach_table_yiman_text_color));
						TV_vie.setBackgroundResource(R.drawable.fragment2_mycoach_table_textview_red_bg);
					}
				} else {

					String str = "����ԤԼ!";
					TV_vie.setText(str);
					TV_vie.setTextColor(context.getResources().getColor(R.color.fragmen2_mycoach_table_buke_text_color));
					TV_vie.setBackgroundResource(R.drawable.fragment2_mycoach_table_textview_gary_bg);
				}

			}





		return view;
	}
	// ���json��ߵ�ʱ������
		public static List<String> getTableLeftData() {
			List<String> List = new ArrayList<String>();
			List.add("ʱ��/����");
			List.add("����\n(8:00-12:00)");
			List.add("����\n(14:00-18:00)");
			List.add("���\n(18:30-20:00)");
			return List;
		}

		// ��ñ���м������
		public static List<Fragmen2_Mycoach_Table_ShowEntity> getTableCenterData() {
			int k = 1;
			List<Fragmen2_Mycoach_Table_ShowEntity> list = new ArrayList<Fragmen2_Mycoach_Table_ShowEntity>();
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 3; j++) {
					Fragmen2_Mycoach_Table_ShowEntity entity = new Fragmen2_Mycoach_Table_ShowEntity();
					entity.date = Tb_head_List.get(i);
					entity.DayTimeStr="���糡";
					entity.SubjectsId=2;
					entity.CarID=2;

					entity.id = k++;
					entity.MycoachID = new Random().nextInt(50) + 1;
					entity.Sum = new Random().nextInt(4) + 1;
					entity.Reduce = new Random().nextInt(4) + 1;
					entity.Surplus = entity.Sum - entity.Reduce;
					entity.yes_on = new Random().nextInt(2) + 1;

					String[] timeStr=new String[]{"8:00","9:00","10:00","11:00","12:00","13:00","14:00"
							                      ,"15:00","16:00","17:00","18:00","19:00","20:00","21:00"};


					List<Order_Spinner_TimeEntity> orderlist=new ArrayList<Order_Spinner_TimeEntity>();
					if(entity.Surplus==0){
						 for(int orderi=0;orderi<entity.Sum;orderi++){
					     Order_Spinner_TimeEntity order=new Order_Spinner_TimeEntity();
					     order.StratTime=timeStr[orderi];
					     order.EndTime=timeStr[orderi+1];
					     order.Order="(��ԤԼ)";
					     orderlist.add(order);
						 }
					 }
					else {
						 for(int orderi=0;orderi<entity.Reduce;orderi++){
						     Order_Spinner_TimeEntity order=new Order_Spinner_TimeEntity();
						     order.StratTime=timeStr[orderi];
						     order.EndTime=timeStr[orderi+1];
						     order.Order="(����ԤԼ)";
						     orderlist.add(order);
							 }
						 for(int orderi=0;orderi<entity.Surplus;orderi++){
						     Order_Spinner_TimeEntity order=new Order_Spinner_TimeEntity();
						     order.StratTime=timeStr[entity.Reduce];
						     order.EndTime=timeStr[entity.Reduce+1];
						     order.Order="(��ԤԼ)";
						     orderlist.add(order);
							 }


					}

					entity.list=orderlist;

					list.add(entity);




				}
			}

			return list;
		}





}*/
