package com.example.Coach.Activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.xmlpull.v1.XmlPullParserException;
import org.xutils.x;

import com.example.Coach.Utils.CoachInfo;
import com.example.Coach.Utils.DaySeasonDate;
import com.example.Coach.adapter.OrderaAdapter;
import com.example.Coach.entity.Ke2order;
import com.example.Coach.entity.OrderAdapterEntity;
import com.example.Coach.entity.Stuinfo;
import com.example.Coach.entity.Timeorder;
import com.example.base.Base2Activity;
import com.example.fragment.CoachMain_FragmentKe2;
import com.example.internet.AccessInternet;
import com.example.internet.NetConn;
import com.example.utils.MarqueeTextView;
import com.example.utils.NoDoubleClickListener;
import com.example.utils.Utils;
import com.example.xsjx.LogoDoActivity;
import com.example.xsjx.MainApplication;
import com.example.xsjx.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

public class CoachOrderKe2Activity extends Activity implements OnClickListener {

	private TextView topdate,newchoose, addbbs, addbbshow;
	private ImageView fanhui;
	private ListView listView;
	private Button adddate;
	private Switch switc;
	private MarqueeTextView Boottomts;
    public static Ke2order ke2order=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 限制此页面只能竖屏显示
		setContentView(R.layout.activity_coach_order_ke2);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
				| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);// 设置输入框在输入法之上

		// 状态栏沉浸 1
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setNavigationBarTintEnabled(true);
		// 自定义颜色
		tintManager.setTintColor(Color.parseColor("#12b7f5"));

		if (inflater == null)
			inflater = LayoutInflater.from(this);

		//ListKe2ID=getIntent().getIntExtra("ListKe2ID", 0);
		//Log.e("CoachOrderKe2ActivityListKe2ID",  ke2order.getDate()+"");
		
		
		
		if(ke2order==null){
			Utils.showToast(this, "数据异常!");
			this.finish();
		}else {
		initView();
		initData();
		initListener();
		}
	}

	@TargetApi(19)
	private void setTranslucentStatus(boolean on) {
		Window win = getWindow();
		WindowManager.LayoutParams winParams = win.getAttributes();
		final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		if (on) {
			winParams.flags |= bits;
		} else {
			winParams.flags &= ~bits;
		}
		win.setAttributes(winParams);
	}

	/*at 销毁时候  ke2order=null
 @Override
  protected void onDestroy (){
	 if(ke2order!=null)
	    ke2order=null;
  }*/
	private void initView() {
		topdate=(TextView) findViewById(R.id.coach_order_ke2_date);
		switc=(Switch) findViewById(R.id.coach_order_ke2_switch);
		newchoose = (TextView) findViewById(R.id.coach_order_ke2_newchoose);
		fanhui = (ImageView) findViewById(R.id.coach_order_ke2_fanhui);
		addbbs = (TextView) findViewById(R.id.coach_order_ke2_addbbs);
		addbbshow = (TextView) findViewById(R.id.coach_order_ke2_addbbsshow);
		adddate = (Button) findViewById(R.id.coach_order_ke2_adddate);
		listView = (ListView) findViewById(R.id.coach_order_ke2_listview);
		
		Boottomts=(MarqueeTextView) findViewById(R.id.coach_order_ke2_Boottomts);
	}
	public static OrderaAdapter oadapter;
	private void initData() {
		topdate.setText(ke2order.getDate()+" "+ke2order.getWeek()+" "+ke2order.getDaythreeName());
		if(ke2order.getState()==1){
			
			switc.setChecked(true);
			
			//getlvdata();// 获得时间预约数据
			
			list=ke2order.getTimeorder();
		}
		else{ switc.setChecked(false);  
		View head = LayoutInflater.from(this).inflate(R.layout.view_nodata, null);
		listView.addHeaderView(head);
		}
		
		
		newchoose.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		addbbs.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		
		if(ke2order.getLiuyan().equals("暂无留言!"))
		  addbbshow.setText("您添加的留言将会显示在这里哟~");
		else  addbbshow.setText(ke2order.getLiuyan()+"!");
		
		
		
		oadapter = new OrderaAdapter(list, this);
		listView.setAdapter(oadapter);
		
    if(list.size()==0){
    	View head = LayoutInflater.from(this).inflate(R.layout.view_nodata, null);
		listView.addHeaderView(head);
		
     }
		
		// 重新设置LIstView 的高度
        setLisetViewHeight();
		
		//底部的提示
		
		String seasonname=DaySeasonDate.seasonlist.get(DaySeasonDate.seasontf).seasonname;//季节名字
		String dayname=ke2order.getDaythreeName();
		String starttime=DaySeasonDate.seasonlist.get(DaySeasonDate.seasontf).daythree.get(ke2order.getDaythreeid()-1).starttime;
		String endtime=DaySeasonDate.seasonlist.get(DaySeasonDate.seasontf).daythree.get(ke2order.getDaythreeid()-1).endtime;
		Boottomts.setText("*系统提示:"+seasonname+dayname+"的预约时间为("+starttime+"~"+endtime+")");
		
	
		

	}
	/**
	 * 重新计算ListView的高度
	*/
	
	public  void setLisetViewHeight(){

		Utils.setListViewHeightBasedOnChildren(listView);
	}

	private void initListener() {
		newchoose.setOnClickListener(this);
		fanhui.setOnClickListener(this);
		addbbs.setOnClickListener(this);
		adddate.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
				showADDdate();
			}

		});
		switc.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
            
            @Override  
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {  
                if(isChecked) {  
                    //选中时 do some thing   
                   // statusText.setText("开");  
                	 if(list.size()==0){
                		 Utils.showToast(CoachOrderKe2Activity.this, "您已经开启当前场次的预约!");
                	 }
                } else {  
                   if(list.size()>0){
                	   Utils.showToast(CoachOrderKe2Activity.this, "您有预约消息,不可关闭当前场次!");
                	   switc.setChecked(true);
                   }
                   else{
                	   Utils.showToast(CoachOrderKe2Activity.this, "您已经关闭当前场次的预约!");
                   }
                   
                }  
                  
            }  
        });
	
		
		
	}

	private List<Timeorder> list = new ArrayList<Timeorder>();
	
	
	private void getlvdata() {
		
	   /*	List<Timeorder> listtme=ke2order.getTimeorder();
		
		for(int i=0;i<listtme.size();i++){
			Timeorder timeorder=listtme.get(i);
			
		    OrderAdapterEntity o = new OrderAdapterEntity();
			o.id=timeorder.getCcke2id();
		    o.StartTime=timeorder.getCcstarttime();	
		    o.EndTime=timeorder.getCcendtime();
		    
		    int stuok=0;
		    int setnum=0;
		    List<Stuinfo> liststu=timeorder.getStuinfo();
		    for(int j=0;j<liststu.size();j++){
		    	Stuinfo stuinfo=liststu.get(j);
		    
		    	if(stuinfo.getOrderstate()==1){
		    		 o.Tag=2;
		    		 o.OrderMsg=stuinfo.getUsername();
		    		 o.ChakanMsg=0;
		    		 
		    		 break;
		    	}
		    	else if(stuinfo.getOrderstate()==2){
		    		 o.Tag=1;
		    		 o.ChakanMsg=1;
		    		
		    	}
		    	else{
		    		 setnum++;	
		    	}
		    }
		    if(setnum==0){
		    	o.Tag=0;
		        o.OrderMsg=setnum+"人申请!";
		    }
		    else{

		    }  
		  list.add(o);
		}
		
	*/

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 重新选择
		case R.id.coach_order_ke2_newchoose:
			CoachOrderKe2Activity.this.finish();
			break;
		// 返回i
		case R.id.coach_order_ke2_fanhui:
			CoachOrderKe2Activity.this.finish();
			break;
		// 添加留言
		case R.id.coach_order_ke2_addbbs:
			showAddBBS();
			break;
		default:
			break;
		}

	}

	private LayoutInflater inflater = null;
	private Dialog Dcoach = null;
	private View Vcoach = null;
	private EditText AddConten = null;
	private TextView AddnameTV = null;
	private void showAddBBS() {

		if (Dcoach == null)
			Dcoach = new Dialog(this, R.style.testDialog);
		if (Vcoach == null)
			Vcoach = inflater.inflate(R.layout.logdo_coach_pwd_dialog, null);

		Dcoach.setContentView(Vcoach);
		Dcoach.show();

		WindowManager.LayoutParams lp = Dcoach.getWindow().getAttributes();
		lp.width = MainApplication.phoneWidth;
		// lp.height = MainApplication.phoneHeight;

		Dcoach.getWindow().setAttributes(lp);

		if (AddnameTV == null)
			AddnameTV = (TextView) Vcoach.findViewById(R.id.logdo_coach_pwd_dialog_name);

		AddnameTV.setText("添加留言");
		if (AddConten == null) {
			AddConten = (EditText) Vcoach.findViewById(R.id.logdo_coach_pwd_dialog_et);
			AddConten.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
			AddConten.setSingleLine(false);
			//水平滚动设置为False  
			AddConten.setHorizontallyScrolling(false);
			
			    int newHeight = MainApplication.phoneHeight/4;
		        ViewGroup.LayoutParams lp1 = AddConten.getLayoutParams();
		        lp1.height = newHeight;
		        
		    AddConten.setLayoutParams(lp1);
		    AddConten.setGravity(Gravity.TOP| Gravity.LEFT); 
			AddConten.setMaxLines(250);
			AddConten.setFilters(Utils.getInputFilter(this, 250));
		}

		 AddConten.setHint("请输入您要在本场次的留言~(250字内)");
		 AddConten.setText(ke2order.getLiuyan());

		Vcoach.findViewById(R.id.logdo_coach_pwd_dialog_quxiao).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Dcoach.dismiss();
			}
		});

		Vcoach.findViewById(R.id.logdo_coach_pwd_dialog_queding).setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
				String str = AddConten.getText().toString().replace(" ", "");
				if (!str.equals("") && str.length() > 0)
					{addbbshow.setText(AddConten.getText().toString());
					
					ke2order.setLiuyan(AddConten.getText().toString());
					}
				
				Dcoach.dismiss();
			}
		});

	}

	/**
	 * 显示添加时间段的方法
	 */
	private Dialog Ddate = null;
	private View Vdate = null;
	private TimePicker starttime, endttime;
	private int sh = 0, sm = 0, eh = 0, em = 0;
	private TextView timeshow;
	private int diferr=0;//练车时间
	private void showADDdate() {
		if (Ddate == null)
			Ddate = new Dialog(this, R.style.testDialog);
		if (Vdate == null) {
			Vdate = inflater.inflate(R.layout.coach_order_ke2_start_end_date_dialog, null);

			starttime = (TimePicker) Vdate.findViewById(R.id.coach_order_ke2_start_end_date_dialog_starttime);
			endttime = (TimePicker) Vdate.findViewById(R.id.coach_order_ke2_start_end_date_dialog_endtime);
			timeshow = (TextView) Vdate.findViewById(R.id.coach_order_ke2_start_end_date_dialog_time_leng);
			starttime.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);
			endttime.setDescendantFocusability(TimePicker.FOCUS_BLOCK_DESCENDANTS);

			starttime.setIs24HourView(true);
			endttime.setIs24HourView(true);
		}

		Ddate.setContentView(Vdate);
		Ddate.show();
		WindowManager.LayoutParams lp = Ddate.getWindow().getAttributes();
		lp.width = MainApplication.phoneWidth;

		Ddate.getWindow().setAttributes(lp);

		starttime.setOnTimeChangedListener(new OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker arg0, int h, int m) {
				sh = h;
				sm = m;
				diferr = (eh - sh) * 60 + (em - sm);
				timeshow.setText("练车时长: " + diferr + " 分钟");
				if(diferr>0 && diferr <=60)
					timeshow.setTextColor(ContextCompat.getColor(CoachOrderKe2Activity.this, R.color.green));
				else timeshow.setTextColor(ContextCompat.getColor(CoachOrderKe2Activity.this, R.color.red));
			}
		});

		endttime.setOnTimeChangedListener(new OnTimeChangedListener() {
			@Override
			public void onTimeChanged(TimePicker arg0, int h, int m) {
				eh = h;
				em = m;
				diferr= (eh - sh) * 60 + (em - sm); 
				timeshow.setText("练车时长: " + diferr + " 分钟");
				if(diferr>0 && diferr <=60)
					timeshow.setTextColor(ContextCompat.getColor(CoachOrderKe2Activity.this, R.color.green));
				else timeshow.setTextColor(ContextCompat.getColor(CoachOrderKe2Activity.this, R.color.red));
			}
		});

	}

}
