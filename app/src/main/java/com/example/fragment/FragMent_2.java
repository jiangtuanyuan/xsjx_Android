package com.example.fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.example.adapter.GvAdapter;
import com.example.adapter.VpAdapter;
import com.example.entity.Fragmen2_Mycoach_Table_ShowEntity;
import com.example.entity.Fragment2_all_GV_CarEntity;
import com.example.entity.Order_Spinner_TimeEntity;

import com.example.utils.NoDoubleClickListener;
import com.example.utils.Utils;
import com.example.xsjx.CheckStandActivity;
import com.example.xsjx.CoachInfoActivity;
import com.example.xsjx.EnrollDetailsActivity;
import com.example.xsjx.LogoActivity;
import com.example.xsjx.MainActivity;
import com.example.xsjx.MainApplication;
import com.example.xsjx.OrderActivity;
import com.example.xsjx.R;
import com.example.xsjx.VideoActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

@SuppressWarnings("unused")
public class FragMent_2 extends Fragment {

	private TextView tvContent, Frafment2_tv_mark;
	public RadioGroup RG;
	private int[] items = new int[] { R.id.Fragment2_rg_rb_all, R.id.Fragment2_rb_Mycoach };
	private ViewPager vp;
	private List<View> FragMnet_list = new ArrayList<View>();
	public LayoutInflater inflater;

	int width;
	int vpmoren = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View FragMent2_view = inflater.inflate(R.layout.fragment_2, container, false);
		Log.v("Fragment", "fragment2����");
		iniView(FragMent2_view);
		return FragMent2_view;
	}

	// ����FragMent2_view�ҵ�ID
	public void iniView(View view) {
		Frafment2_tv_mark = (TextView) view.findViewById(R.id.Fragment2_tv_mark);
		vp = (ViewPager) view.findViewById(R.id.FragMent2_vp);
		RG = (RadioGroup) view.findViewById(R.id.Fragment2_rg);
		
		setViewListener();

	}

	// ����view�¼��Լ�����
	@SuppressWarnings("deprecation")
	@SuppressLint("InflateParams")
	public void setViewListener() {
		// ����Frafment2_tv_mark Ϊ��Ļ��һ��
		DisplayMetrics metrics = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
		width = metrics.widthPixels;
		Log.v("TAG", "width==" + width);
		LayoutParams layout = new LayoutParams(width / 2, 10);
		Frafment2_tv_mark.setLayoutParams(layout);
		// ����Frafment2_tv_mark Ϊ��Ļ��һ��
		// ���ֱ�View
		inflater = LayoutInflater.from(getActivity());
		//View vp01 = inflater.inflate(R.layout.fragment_2_all_layout, null);
		
		//View vp01=FragMent_2_all.getView(getActivity());
		View vp01=inflater.inflate(R.layout.fragment2_mycoach_layout, null);
		
		View vp02 = inflater.inflate(R.layout.fragment2_mycoach_layout, null);
		
		//MycoachView(vp02); �ر�
		
		
		FragMnet_list.add(vp01);
		FragMnet_list.add(vp02);
		// ���ֱ�View
		// ViewPager����
		vp.setAdapter(new VpAdapter(getActivity(), FragMnet_list));

		vp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				RadioButton rb = (RadioButton) RG.getChildAt(arg0);
				rb.setChecked(true);

				Log.v("vp", "vp=" + arg0);
				if (arg0 == vpmoren) {
					setAnim(3);
					vpmoren = 2;
				}
				if (arg0 == 1)
					setAnim(0);
				else
					setAnim(1);

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		// ViewPager����

		// RG����
		RG.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				for (int i = 0; i < items.length; i++) {
					if (items[i] == arg1) {
						vp.setCurrentItem(i);
					}
				}
			}
		});
	}

	// ����ƽ�ƶ���
	public void setAnim(int index) {
		switch (index) {
		case 0:/*
				 * Animation anim = AnimationUtils.loadAnimation(getActivity(),
				 * R.anim.pingyi); anim.setInterpolator(new
				 * DecelerateInterpolator(2.0f));
				 */

			TranslateAnimation animation = new TranslateAnimation(0, width / 2, 0, 0);
			animation.setFillAfter(true);
			animation.setDuration(700);
			Frafment2_tv_mark.startAnimation(animation);
			break;
		case 1:/*
				 * Animation anim2 = AnimationUtils.loadAnimation(getActivity(),
				 * R.anim.pingyi2); anim2.setInterpolator(new
				 * DecelerateInterpolator(2.0f));
				 */

			TranslateAnimation animation2 = new TranslateAnimation(width / 2, 0, 0, 0);
			animation2.setFillAfter(true);
			animation2.setDuration(700);
		
			Frafment2_tv_mark.startAnimation(animation2);
			break;
		case 3:
			break;
		}
	}

	// ------------------------------------------ȫ��-------------------------------------------------------//
	// ��������ģ���е�ȫ��
	public void allView(View view) {

		
	}
	
	// ------------------------------------------ȫ��END-------------------------------------------------------//

	//�ҵĽ���
	public int[] TV_table_head_item = new int[] { R.id.fagment2_mycoach_tabel_head1, R.id.fagment2_mycoach_tabel_head2,
			R.id.fagment2_mycoach_tabel_head3, R.id.fagment2_mycoach_tabel_head4, R.id.fagment2_mycoach_tabel_head5,
			R.id.fagment2_mycoach_tabel_head6 };// ���ͷ��6��TV���ID
	public int[] TV_table_left_itme = new int[] { R.id.fragment2_mycoach_left_dataDay,
			R.id.fragment2_mycoach_left_dataDay1, R.id.fragment2_mycoach_left_dataDay2,
			R.id.fragment2_mycoach_left_dataDay3, };// �����ߵ�4�����ID
	public int[] TV_table_center_item = new int[] { R.id.fagment2_mycoach_tabel_data11,
			R.id.fagment2_mycoach_tabel_data12, R.id.fagment2_mycoach_tabel_data13, R.id.fagment2_mycoach_tabel_data21,
			R.id.fagment2_mycoach_tabel_data22, R.id.fagment2_mycoach_tabel_data23, R.id.fagment2_mycoach_tabel_data31,
			R.id.fagment2_mycoach_tabel_data32, R.id.fagment2_mycoach_tabel_data33, R.id.fagment2_mycoach_tabel_data41,
			R.id.fagment2_mycoach_tabel_data42, R.id.fagment2_mycoach_tabel_data43, R.id.fagment2_mycoach_tabel_data51,
			R.id.fagment2_mycoach_tabel_data52, R.id.fagment2_mycoach_tabel_data53, R.id.fagment2_mycoach_tabel_data61,
			R.id.fagment2_mycoach_tabel_data62, R.id.fagment2_mycoach_tabel_data63 };// ����м��18�����ID

	public List<String> Tb_head_List = new ArrayList<String>();
	public List<String> Tb_Left_List;
	public List<Fragmen2_Mycoach_Table_ShowEntity> Tb_center_List;

	// ------------------------------------------�ҵĽ���-------------------------------------------------------//
	@SuppressWarnings({ "deprecation", "unchecked" })
	// ��������ģ���е��ҵĽ���
	@SuppressLint({ "ResourceAsColor", "ShowToast" })
	public void MycoachView(View view) {
		// �����ҵĽ������»���
		TextView TV_MyCoach = (TextView) view.findViewById(R.id.Fragment2_mycoach_MyCoach);
		TV_MyCoach.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		TV_MyCoach.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			Intent intent=new Intent(getActivity(),CoachInfoActivity.class);
	    	startActivity(intent);
				
			}
		});
		Tb_head_List.addAll(Utils.getday(6));

		TextView TV_changdi = (TextView) view.findViewById(R.id.Fragment2_mycoach_changdi);
		TV_changdi.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		TV_changdi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
			
				//http://ditu.amap.com/
				//��У��γ�ȣ�112.817763,28.33847
		Utils.intentWebActity(getActivity(),"//uri.amap.com/marker?position=112.817763,28.33847&name=park&src=mypage&coordinate=gaode&callnative=1", "���ض�λ");
		
		
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
			
			
			TagList.add(Tb_center_List.get(k).cdID+"");
			TagList.add(Tb_center_List.get(k).cdStr);
			
			for(int l=0;l<Tb_center_List.get(k).list.size();l++)
			{
				TagList.add(Tb_center_List.get(k).list.get(l).StratTime);
				TagList.add(Tb_center_List.get(k).list.get(l).EndTime);
				TagList.add(Tb_center_List.get(k).list.get(l).Order);
				
			}
			
			   /*
                       ������, ����, 2, 2, 5, 20, 3, 2, 1, 1, 
            8:00, 9:00, (��ԤԼ),
            9:00, 10:00, (��ԤԼ),
            10:00, 11:00, (δԤԼ)]
            */
			
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
						   Builder bu1=new Builder(getActivity());
						   bu1.setTitle("��ʾ:");
						   bu1.setMessage("��ǸӴ!\n��ǰ����ԤԼ����!\nѡ�������İ�!");
						   bu1.setNegativeButton("ȷ��", null);
						   bu1.create();
						   bu1.show();
						

					} else if (str.equals("����ԤԼ!")) {
					//	Toast.makeText(getActivity(), "��ǸӴ!��ǰ���β���ԤԼӴ!", 1).show();
						

						   Builder bu1=new Builder(getActivity());
						   bu1.setTitle("��ʾ:");
						   bu1.setMessage("��ǸӴ!\n��ǰ���β���ԤԼӴ!");
						
						   bu1.setNegativeButton("ȷ��", null);
						
						   bu1.create();
						   bu1.show();
							
					}

					else if (MyKeShi > 0) {
      
						//Toast.makeText(getActivity(), vv.getTag() + str, 1).show();
						
						Log.v("TagList", vv.getTag()+"");
					
						
						Intent intent=new Intent(getActivity(),OrderActivity.class);
						intent.putStringArrayListExtra("TagList", (ArrayList<String>) vv.getTag());
			            startActivityForResult(intent, 1);
        
				        
					} else {

						//Toast.makeText(getActivity(), "��Ǹ,��Ŀ�ʱΪ0!�޷�ԤԼ!", 1).show();
						   Builder bu1=new Builder(getActivity());
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
					TV_vie.setTextColor(getResources().getColor(R.color.fragmen2_mycoach_table_keyuyue_text_color));
					TV_vie.setBackgroundResource(R.drawable.fragment2_mycoach_table_textview_green_bg);
  
					
				
				} else {
					String str = "����!";
					TV_vie.setText(str);
					TV_vie.setTextColor(getResources().getColor(R.color.fragmen2_mycoach_table_yiman_text_color));
					TV_vie.setBackgroundResource(R.drawable.fragment2_mycoach_table_textview_red_bg);
				}
			} else {

				String str = "����ԤԼ!";
				TV_vie.setText(str);
				TV_vie.setTextColor(getResources().getColor(R.color.fragmen2_mycoach_table_buke_text_color));
				TV_vie.setBackgroundResource(R.drawable.fragment2_mycoach_table_textview_gary_bg);
			}

		}

	}

	// ���json��ߵ�ʱ������
	public List<String> getTableLeftData() {
		List<String> List = new ArrayList<String>();
		List.add("ʱ��/����");
		List.add("����\n(8:00-12:00)");
		List.add("����\n(14:00-18:00)");
		List.add("���\n(18:30-20:00)");
		return List;
	}

	String[] TraininggroundItems = new String[] { "��ϢѧԺ��", "�����г�б����", "��ͨ��˾��" };
	
	// ��ñ���м������
	public List<Fragmen2_Mycoach_Table_ShowEntity> getTableCenterData() {
		int k = 1;
		List<Fragmen2_Mycoach_Table_ShowEntity> list = new ArrayList<Fragmen2_Mycoach_Table_ShowEntity>();
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 3; j++) {
				Fragmen2_Mycoach_Table_ShowEntity entity = new Fragmen2_Mycoach_Table_ShowEntity();
				entity.date = Tb_head_List.get(i);
				if(j==0)
				entity.DayTimeStr="���糡";
				if(j==1)
					entity.DayTimeStr="���糡";
				if(j==2)
					entity.DayTimeStr="��䳡";
				
				entity.SubjectsId=2;
				entity.CarID=2;
				
				entity.cdID=new Random().nextInt(3);//����ID
				entity.cdStr=TraininggroundItems[entity.cdID];//��������
				
				entity.id = k++;
				entity.MycoachID = new Random().nextInt(50) + 1;
				entity.Sum = new Random().nextInt(6) + 1;
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

	
	// ------------------------------------------�ҵĽ���END-------------------------------------------------------//

}
