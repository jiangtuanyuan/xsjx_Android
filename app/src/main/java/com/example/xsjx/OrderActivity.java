package com.example.xsjx;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.Order_Spinner_Adapter;
import com.example.adapter.VpAdapter;
import com.example.entity.Fragmen2_Mycoach_Table_ShowEntity;
import com.example.entity.Order_Spinner_TimeEntity;
import com.example.utils.AndroidWorkaround;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 蒋团圆
 * 2017.10.13   8:51
 * 预约科目二科目三的界面所对应的Activity
 */

@SuppressLint("InflateParams")
public class OrderActivity extends Activity {
	public List<String> TagList;// 接收从表格传过来的List<String> 值
	public List<Order_Spinner_TimeEntity> spinnerlist = new ArrayList<Order_Spinner_TimeEntity>();
	public List<String> itme_list;
	public Button Btn_Order, Btn_Cancel, Order_Time_Btn;
	public TextView Order_tv_CarNum, Order_tv_CarType, Order_tv_Coach, Order_tv_DateDay,Order_tv_changdi, Order_tv_DayTimeStr;
	public TextView Oredr_Phone;
	public ViewPager Order_Vp;
	public Spinner Order_Spinner;
	public RadioGroup Order_Rg;
    public String timeDateStr;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_order);
		
		// 解决底部屏幕按键适配
				if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
					AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
				}
		
		initState();// 状态栏沉浸
		getdata();// 获取传递过来的值
		findView();// 获得ID
	}

	// 获得初始化的数据
	public void getdata() {
		TagList = getIntent().getStringArrayListExtra("TagList");
		// 从第10个开始是预约的时间 三个为一组
		int k = 0;
		int Si = 12;
		itme_list = new ArrayList<String>();
		for (; Si < TagList.size();) {
			String str1 = TagList.get(Si) + "-" + TagList.get(Si + 1) + TagList.get(Si + 2);
			itme_list.add(str1);
			Log.v("time=", TagList.get(Si) + "-" + TagList.get(Si + 1) + TagList.get(Si + 2));
			Order_Spinner_TimeEntity spinner_TimeEntity = new Order_Spinner_TimeEntity();
			spinner_TimeEntity.StratTime = TagList.get(Si);
			spinner_TimeEntity.EndTime = TagList.get(Si + 1);
			spinner_TimeEntity.Order = TagList.get(Si + 2);
			spinnerlist.add(spinner_TimeEntity);
			Si = Si + 3;
		}
		//Toast.makeText(this, "spinnerlist=" + spinnerlist.size() + "list=" + itme_list.size(), 1).show();
	}

	// 获得组件ID
	@SuppressLint("InflateParams")
	public void findView() {
		// 获得车牌
		Order_tv_CarNum = (TextView) findViewById(R.id.Order_tv_CarNum);
		Order_tv_CarNum.setText("车牌:" + TagList.get(3) + "id");
		// 获得车型
		Order_tv_CarType = (TextView) findViewById(R.id.Order_tv_CarType);
		Order_tv_CarType.setText("车型:" + TagList.get(3) + "id");
		// 获得教练
		Order_tv_Coach = (TextView) findViewById(R.id.Order_tv_coach);
		Order_tv_Coach.setText("教练:" + TagList.get(5) + "id");
		// 获得日期
		Order_tv_DateDay = (TextView) findViewById(R.id.Order_tv_DateDay);
		String datestr = TagList.get(0).replaceAll("\n", "   ");
		Order_tv_DateDay.setText("日期:" + datestr);
		//获得场地
		Order_tv_changdi=(TextView) findViewById(R.id.Order_tv_changdi);
		Order_tv_changdi.setText("场地:" + TagList.get(11));
		// 获得场次
		Order_tv_DayTimeStr = (TextView) findViewById(R.id.Order_tv_DayTimeStr);
		Order_tv_DayTimeStr.setText("场次:" + TagList.get(1));
		// 预约场次
		Btn_Order = (Button) findViewById(R.id.Btn_Order);
		
		final Builder Order_Bu = new Builder(this);
		
		Btn_Order.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
			
				if(timeDateStr.substring(timeDateStr.length()-5).equals("(可预约)")){
					Order_Bu.setTitle("是否预约该场次?");
					Order_Bu.setMessage(Order_tv_CarNum.getText()+"\n\n"+
							Order_tv_Coach.getText()+"\n\n"+
							Order_tv_DateDay.getText()+"\n\n"+
							Order_tv_changdi.getText()+"\n\n"+
							Order_tv_DayTimeStr.getText()+"\n\n"+
							"时间:"+timeDateStr+"\n"
							);
					Order_Bu.setNegativeButton("取消", null);
					Order_Bu.setPositiveButton("确定", new AlertDialog.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
						
							/*
							 * 提交预约的数据!
							 * 
							 * */
							Toast.makeText(OrderActivity.this, "预约提交成功!请等待您的教练确定！", 1).show();
							
						}
					} );
				}
				else{

					Order_Bu.setTitle("提示:");
					Order_Bu.setMessage("选择该场的时间段内已有人预约!"+"\n\n"+"请选择其他时间段!"+"\n");
					Order_Bu.setNegativeButton("取消", null);
					Order_Bu.setPositiveButton("好的", null);
				}
				
				Order_Bu.create();
				Order_Bu.show();

			}
		});

		// 返回
		Btn_Cancel = (Button) findViewById(R.id.Btn_Cancel);
		Btn_Cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				ColoseThisAt();

			}
		});

		// 拨打教练电话图片按钮
		Oredr_Phone = (TextView) findViewById(R.id.Order_Phone);
		Oredr_Phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String CoachPhone = "15074667947";
				Intent intent = new Intent(Intent.ACTION_DIAL);
				Uri data = Uri.parse("tel:" + CoachPhone);
				intent.setData(data);
				startActivity(intent);

			}
		});

		final String[] items = itme_list.toArray(new String[itme_list.size()]);
		// Log.v("itme_list=", items[0]);

		Order_Spinner = (Spinner) findViewById(R.id.Order_Spinner);
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.order_spinner_list_itme_textview,
				items);
		adapter.setDropDownViewResource(R.layout.order_spinner_dropdown_stytle);

		Order_Spinner.setAdapter(adapter);
		Order_Spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				// 显示Spinner选中的值
				// Toast.makeText(getApplicationContext(), items[arg2],
				// 1).show();
				timeDateStr=items[arg2];
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		Order_Rg = (RadioGroup) findViewById(R.id.Order_RG);
		// 设置ViewPager 图
		int[] imgitem = new int[] { R.drawable.yingdao1, R.drawable.my_back_img };
		List<View> imglist = new ArrayList<View>();
		Order_Vp = (ViewPager) findViewById(R.id.Order_vp);

		LayoutInflater inflater = LayoutInflater.from(this);

		View vp01 = inflater.inflate(R.layout.vp_01, null);
		vp01.setBackgroundResource(imgitem[0]);

		View vp02 = inflater.inflate(R.layout.vp_01, null);
		vp02.setBackgroundResource(imgitem[1]);

		imglist.add(vp01);
		imglist.add(vp02);

		VpAdapter adapter1 = new VpAdapter(this, imglist);

		Order_Vp.setAdapter(adapter1);
		Order_Vp.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				setRgPosition(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		// Order_Vp.setCurrentItem(1);设置显示第几个View

		/*
		 * LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams)
		 * Order_Vp.getLayoutParams();
		 * linearParams.height=MainApplication.getMetrics(this).heightPixels/4;
		 * 
		 * Order_Vp.setLayoutParams(linearParams);
		 */
		// http://www.jtyrl.cn/Training/images/DuanXin.png

	}

	public void setRgPosition(int position) {
		RadioButton rb = (RadioButton) Order_Rg.getChildAt(position);
		rb.setChecked(true);
	}

	/*--------------------------------------------------------------------------------------------------------*/
	/**
	 * 关闭当前的Activity
	 * 
	 */
	public void ColoseThisAt(){
		this.finish();
	}
	// 重写系统方法 以及调用exit方法
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
		    ColoseThisAt();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	// 重写系统方法 以及调用exit方法

	// -------------------------隐藏状态栏
	@SuppressLint("InlinedApi")
	private void initState() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// 透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// 透明导航栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}

	// -------------------------隐藏状态栏 END
}
