package com.example.xsjx;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.BBSDisAdapter;
import com.example.adapter.CoachInfoStuAdapter;
import com.example.adapter.VpAdapter;
import com.example.entity.CoachInfoStuEntity;
import com.example.entity.Fragment4_BBS_Entity;
import com.example.utils.NoDoubleClickListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
/**
 * 
 * @author 蒋团圆
 * 教练主页
 */
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 教练主页 所对应的Activity
 * 
 * @Description: TODO
 * @ClassName: CoachInfoActivity.java
 * @author 蒋团圆
 * @version V1.0
 * @Date 2017年11月5日 下午3:41:00
 */
public class CoachInfoActivity extends Activity {
	public ViewPager vp1, vp2;
	public RadioGroup RG1, RG2;
	public TextView coach_info_tv_mark;
	public int[] imgitem = new int[] { R.drawable.coach1, R.drawable.coach2, R.drawable.coach3 };// 第一个vp的背景图片
	public List<View> imglist = new ArrayList<View>();// 第一个vp的背景图片View

	public int[] GR2gbitems = new int[] { R.id.coach_info_RG2_rb1, R.id.coach_info_RG2_rb2, R.id.coach_info_RG2_rb3 };
	private List<View> coachlist = new ArrayList<View>();

	public LayoutInflater inflater;
    public  ImageView fanhui;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_coach_info);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
		findView();
		init();
	}

	/**
	 * 
	 * @MethodsNmae: findView
	 * @Description: 找控件
	 * @param:
	 * @return: void
	 * @author 蒋团圆
	 * @Date 2017年11月5日 下午3:37:02
	 */
	private void findView() {
		vp1 = (ViewPager) findViewById(R.id.coach_info_vp1);
		vp2 = (ViewPager) findViewById(R.id.coach_info_vp2);
		RG1 = (RadioGroup) findViewById(R.id.coach_info_RG1);
		RG2 = (RadioGroup) findViewById(R.id.coach_info_RG2);
		coach_info_tv_mark = (TextView) findViewById(R.id.coach_info_tv_mark);
		fanhui=(ImageView) findViewById(R.id.CoachInfo_fanhui);
		fanhui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				CoachInfoActivity.this.finish();
				
				
			}
		});
	}

	/**
	 * 设置控件值
	 */
	int vpmoren = 0;// 记录vp2的上一次位置

	@SuppressWarnings("deprecation")
	private void init() {
		inflater = LayoutInflater.from(this);
		View vp01 = inflater.inflate(R.layout.vp_01, null);
		vp01.setBackgroundResource(imgitem[0]);

		View vp02 = inflater.inflate(R.layout.vp_01, null);
		vp02.setBackgroundResource(imgitem[1]);

		View vp03 = inflater.inflate(R.layout.vp_01, null);
		vp03.setBackgroundResource(imgitem[2]);
		imglist.add(vp01);
		imglist.add(vp02);
		imglist.add(vp03);
		VpAdapter adapter1 = new VpAdapter(this, imglist);

		vp1.setAdapter(adapter1);
		vp1.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				setRgPosition(arg0); // 设置第一个RG 的选中事件
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		// 设置滚动条为屏幕的3分之一
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		width = metrics.widthPixels;
		Log.v("TAG", "width==" + width);
		LayoutParams layout = new LayoutParams(width / 3, 5);
		coach_info_tv_mark.setLayoutParams(layout);

		// 第二个Vp
		View vw1 = inflater.inflate(R.layout.coach_info_introduce, null);

		View vw2 = inflater.inflate(R.layout.coach_info_students, null);
		stufindView(vw2);
		View vw3 = inflater.inflate(R.layout.coach_info_bbs, null);
		bbsfindView(vw3);

		coachlist.add(vw1);
		coachlist.add(vw2);
		coachlist.add(vw3);
		vp2.setAdapter(new VpAdapter(this, coachlist));
		vp2.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				RadioButton rb = (RadioButton) RG2.getChildAt(arg0);
				rb.setChecked(true);
				// 自己也蒙蔽一批
				if (vpmoren == 0) {
					if (arg0 == 1) {
						setAnim(0);
						vpmoren = arg0;
					} else if (arg0 == 2) {
						setAnim(4);
						vpmoren = arg0;
					}
				} else if (vpmoren == 1) {
					if (arg0 == 2) {
						setAnim(1);
						vpmoren = arg0;
					} else if (arg0 == 0) {
						setAnim(3);
						vpmoren = arg0;
					}
				}

				else if (vpmoren == 2) {
					if (arg0 == 1) {
						setAnim(2);
						vpmoren = arg0;
					} else if (arg0 == 0) {
						setAnim(5);
						vpmoren = arg0;
					}
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		// 设置RG2点击事件
		RG2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				for (int i = 0; i < GR2gbitems.length; i++) {
					if (GR2gbitems[i] == arg1) {
						vp2.setCurrentItem(i);
					}
				}
			}
		});

	}

	/**
	 * 设置vp中的RG的按钮选中状态
	 */
	public void setRgPosition(int position) {
		RadioButton rb = (RadioButton) RG1.getChildAt(position);
		rb.setChecked(true);
	}

	/**
	 * 设置平移动画
	 * 
	 * @param index
	 */
	int width;

	public void setAnim(int index) {
		switch (index) {
		// 从第一个滑动到第二个
		case 0:
			TranslateAnimation animation = new TranslateAnimation(0, width / 3, 0, 0);
			animation.setFillAfter(true);
			animation.setDuration(700);
			coach_info_tv_mark.startAnimation(animation);
			break;
		// 从第二个滑动到第3个
		case 1:

			TranslateAnimation animation1 = new TranslateAnimation(width / 3, width - width / 3, 0, 0);
			animation1.setFillAfter(true);
			// animation1.setRepeatMode(Animation.REVERSE);
			animation1.setDuration(700);

			coach_info_tv_mark.startAnimation(animation1);
			break;
		// 从第3个滑动到第2个
		case 2:

			TranslateAnimation animation2 = new TranslateAnimation(width - width / 3, width / 3, 0, 0);
			animation2.setFillAfter(true);
			animation2.setDuration(700);

			coach_info_tv_mark.startAnimation(animation2);
			break;
		// 从第二个滑动到第1个
		case 3:
			TranslateAnimation animation3 = new TranslateAnimation(width / 3, 0, 0, 0);
			animation3.setFillAfter(true);
			animation3.setDuration(700);
			coach_info_tv_mark.startAnimation(animation3);
			break;

		// 从第一个滑动到第三个
		case 4:
			TranslateAnimation animation4 = new TranslateAnimation(0, width - width / 3, 0, 0);
			animation4.setFillAfter(true);
			animation4.setDuration(700);
			coach_info_tv_mark.startAnimation(animation4);

			break;
		// 从第3个滑动到第1个
		case 5:
			TranslateAnimation animation5 = new TranslateAnimation(width - width / 3, 0, 0, 0);
			animation5.setFillAfter(true);
			animation5.setDuration(700);
			coach_info_tv_mark.startAnimation(animation5);

			break;
		}
	}

	/**
	 * 
	 * @MethodsNmae: bbsfindView
	 * @Description: 找BBS View里面的控件值 然后设置
	 * @param: View
	 *             v
	 * @return: void
	 * @author 蒋团圆
	 * @Date 2017年11月5日 下午5:25:26
	 */
	List<Fragment4_BBS_Entity> dislist;
	PullToRefreshListView bbslv;
	Button bbsbtn;
	EditText bbset;

	public void bbsfindView(View v) {
		bbsbtn = (Button) v.findViewById(R.id.coach_info_bbs_bt);

		bbset = (EditText) v.findViewById(R.id.coach_info_bbs_et);
		bbset.addTextChangedListener(etwatcher);
		// 提交按钮的单击事件
		bbsbtn.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
				String etstr = bbset.getText().toString().replace(" ", "").replace("\n", "");

				if (!etstr.equals("")) {
					Toast.makeText(getApplicationContext(), "数据可以提交", 1).show();

				} else
					Toast.makeText(getApplicationContext(), "数据不可以提交 有空格或者全是换行", 1).show();

			}

		});

		bbslv = (PullToRefreshListView) v.findViewById(R.id.coach_info_bbs_lv);
		bbslv.setMode(Mode.PULL_FROM_END);

		dislist = new ArrayList<Fragment4_BBS_Entity>();
		getbbsdata();
		//BBSDisAdapter adapter = new BBSDisAdapter(dislist, this);
	//	bbslv.setAdapter(adapter);
		bbslv.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {

			}
		});
	}

	private TextWatcher etwatcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {

			if (bbset.getText().toString().equals("")) {
				bbsbtn.setBackgroundResource(R.drawable.bbs_gary_bt_bg);

			} else
				bbsbtn.setBackgroundResource(R.drawable.btn_bg);

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			if (bbset.getText().toString().equals("")) {
				bbsbtn.setBackgroundResource(R.drawable.bbs_gary_bt_bg);

			} else
				bbsbtn.setBackgroundResource(R.drawable.btn_bg);

		}

		@Override
		public void afterTextChanged(Editable s) {

			if (bbset.getText().toString().equals("")) {
				bbsbtn.setBackgroundResource(R.drawable.bbs_gary_bt_bg);

			} else
				bbsbtn.setBackgroundResource(R.drawable.btn_bg);
		}
	};

	/**
	 * 
	 * @MethodsNmae: getbbsdata
	 * @Description: 获取留言板的值
	 * @param:
	 * @return: void
	 * @author 蒋团圆
	 * @Date 2017年11月5日 下午5:43:48
	 */
	private void getbbsdata() {
		for (int i = 0; i < 10; i++) {
			Fragment4_BBS_Entity entity = new Fragment4_BBS_Entity();
			entity.id = i;
			entity.headUrl = "www.jtyrl.cn/headimg/1.jpg";
			entity.userName = "用户" + i;
			if (i % 2 == 0)
				entity.userType = "教练";
			else
				entity.userType = "学员";
			
			entity.datetime = "2017-8-8 20:01";
			entity.BBStitle = "教练你好帅好帅好帅你好帅好帅好帅你好帅好帅好帅你好帅好帅好帅你好帅好帅好帅你好帅好帅好帅" + i;
			dislist.add(entity);
		}
	}

/**
 * 
 * @MethodsNmae:  stufindView 
 * @Description:  找学员模块控件的值 
 * @param:        @param v
 * @return:       void     
 * @author        蒋团圆
 * @Date          2017年11月5日 下午6:30:21
 */
	public TextView stusum;
	public ListView stulv;
	public List<CoachInfoStuEntity> stuList;

	public void stufindView(View v){
		stusum=(TextView)v.findViewById(R.id.coach_info_students_sum);
		stulv=(ListView)v.findViewById(R.id.coach_info_students_lv);
		stuList=new ArrayList<CoachInfoStuEntity>();
		getStudata();
		CoachInfoStuAdapter stuadapter=new CoachInfoStuAdapter(stuList, this);
		stulv.setAdapter(stuadapter);
		stusum.setText("共"+stuList.size()+"人");
		
	}
	/**
	 * 
	 * @MethodsNmae:  获取学员数据
	 * @Description:  TODO    
	 * @param:        
	 * @return:       void     
	 * @author        蒋团圆
	 * @Date          2017年11月5日 下午6:42:15
	 */
	public void getStudata(){
		for(int i=0;i<10;i++){
			CoachInfoStuEntity entity=new CoachInfoStuEntity();
			entity.sID=i+1;
			entity.sUrl="http://www.jtyrn.cn/img/safa.jpg";
			entity.sNmae="蒋团圆"+i;
			
			if(i%2==0)
			 {entity.sSex="男";
				entity.sState="学习中";
			 
			 }
			else {entity.sSex="女";
				entity.sState="已拿证";}
			
			stuList.add(entity);
		}	
		
	}
	
	//当前Activity销毁
	@Override
	protected void onDestroy() {
		super.onDestroy();
    }
	
	
}
