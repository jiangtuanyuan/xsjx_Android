package com.example.xsjx;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;


/*
 * 时间：2017.9.17
 * 作者：蒋团圆
 * 作用：注册成功后跳转的界面所对应的Activity
 * */

public class Re_SuccessActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_re_success);
	}

public  void MyOnClick(View view)
{ switch(view.getId())
	{ case R.id.re_success_logdo:
		
		break;
	case R.id.re_sucees_return:
		Intent intent=new Intent(Re_SuccessActivity.this,LogoActivity.class);
		Re_SuccessActivity.this.startActivity(intent);
		Re_SuccessActivity.this.finish();

		break;




	}

}

}
