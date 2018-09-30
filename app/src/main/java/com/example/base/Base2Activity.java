package com.example.base;

import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

/**
 * 
 * @Description:   TODO 
 * @ClassName:     PostingActivity.java
 * @author          ����Բ
 * @version         V1.0  
 * @Date           2017��12��21�� ����7:07:07
 * ��̳ģ��--��Ҫ����
 */
public abstract class Base2Activity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//ֻ������
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
				| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);// ��������������뷨֮��
		
		initState();
		// ��ʼ��ui
        initView();
        // ��ʼ������
        initData();
        // ��Ӽ�����
        initListener();
		
	}
	  // ��ʼ��ui
    protected abstract void initView();

    // ��ʼ������
    protected abstract void initData();

    // ��Ӽ�����
    protected abstract void initListener();
    /**
	 * ����״̬��
	 */
	public void initState() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}
}
