package com.example.xsjx;

import android.os.Build;
import android.os.Bundle;

import com.example.utils.Utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 
 * @author 蒋团圆
 * 星胜驾校-收银台
 */
public class CheckStandActivity extends Activity {
public Button zfbtn;
public ImageView fanhui;	
public TextView tv1,tv2,tv3,tv4;
public RadioGroup RG;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_check_stand);
		initState();
		findvalue();
	  
	}	
	
	String orderNam;
	String orderID;
	int orderNum;
	String userName;
	String userID;
	Double money;
	private void findvalue() {
		Intent intent = getIntent();
		orderNam=intent.getStringExtra("orderNam");
		orderID=intent.getStringExtra("orderID");
		orderNum=intent.getIntExtra("orderNum", 1);
		userName=intent.getStringExtra("userName");
		userID=intent.getStringExtra("userID");
		money=intent.getDoubleExtra("money", 1);
		findView();
		settvtext();
	}
   int zfcode=1;//默认微信支付为1
	private void settvtext() {
		tv1.setText("订单名称："+orderNam);
		tv2.setText("订单数量："+orderNum);
		tv3.setText("订单所属用户："+userName);
		tv4.setText("订单金额："+money+"¥ (RMB)");	
		zfbtn.setText("支付"+money+"元");
         zfbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {	
				Toast.makeText(CheckStandActivity.this, "提交交易请求...", Toast.LENGTH_LONG).show();
				//执行支付方法:
				zfmoney();
			}

		});
         //返回到上一级界面
     	fanhui.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				CheckStandActivity.this.finish();	
			}
		});
		RG.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				switch (arg1) {
				case R.id.check_Rb1:
					zfcode = 1;
					break;
				case R.id.check_Rb2:
					zfcode = 2;
					break;
				default:
					zfcode = 0;
					break;
				}
			}
		});
     	
     	
	}

	public void findView() {
		tv1=(TextView) findViewById(R.id.Check_stand_tv1);
		tv2=(TextView) findViewById(R.id.Check_stand_tv2);
		tv3=(TextView) findViewById(R.id.Check_stand_tv3);
		tv4=(TextView) findViewById(R.id.Check_stand_tv4);
		RG=(RadioGroup) findViewById(R.id.check_RG);
		zfbtn=(Button) findViewById(R.id.check_stand_btn);
		fanhui=(ImageView) findViewById(R.id.Check_stand_fanhui);
	}
/**
 * 支付
 */

	private void zfmoney() {
		switch (zfcode) {
		case 0:
//错误不执行
			Utils.showToast(this, "支付Code发生错误!");
			break;
		case 1:
//微信支付
			Utils.showToast(this, "微信支付中..");
			break;
		case 2:
//支付宝支付
			Utils.showToast(this, "支付宝支付中..");
			break;
		default:
//出现未知错误	
		    Utils.showToast(this, "出现未知错误!");
			break;
		}
	}


	/**
	 * 隐藏状态栏
	 */
	public void initState() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}
}
