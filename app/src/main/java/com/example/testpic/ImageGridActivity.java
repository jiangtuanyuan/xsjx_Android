package com.example.testpic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.example.testpic.ImageGridAdapter.TextCallback;
import com.example.xsjx.R;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageGridActivity extends Activity
{
	public static final String EXTRA_IMAGE_LIST = "imagelist";


	List<ImageItem> dataList;
	GridView gridView;
	ImageGridAdapter adapter;
	AlbumHelper helper;

	TextView wancheng;
	ImageView fanhui;
	Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case 0:
				Toast.makeText(ImageGridActivity.this, "最多选择9张图片!", 1).show();
				break;

			default:
				break;
			}
		}
	};

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//只能竖屏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	//	setContentView(R.layout.activity_posting);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}

		setContentView(R.layout.activity_image_grid);

		helper = AlbumHelper.getHelper();
		helper.init(getApplicationContext());

		dataList = (List<ImageItem>) getIntent().getSerializableExtra(
				EXTRA_IMAGE_LIST);

		initView();
		fanhui=(ImageView) findViewById(R.id.at_image_griv_fanhui);
		fanhui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ImageGridActivity.this.finish();
			}
		});
		
		wancheng = (TextView) findViewById(R.id.at_image_griv_wancheng);
		wancheng.setOnClickListener(new OnClickListener()
		{

			public void onClick(View v)
			{
				ArrayList<String> list = new ArrayList<String>();
				Collection<String> c = adapter.map.values();
				Iterator<String> it = c.iterator();
				for (; it.hasNext();)
				{
					list.add(it.next());
				}

				if (Bimp.act_bool)
				{
					
					ImageGridActivity.this.finish();
					
					Bimp.act_bool = false;
				}
				for (int i = 0; i < list.size(); i++)
				{
					if (Bimp.drr.size() < 9)
					{
						Bimp.drr.add(list.get(i));
					}
				}
				finish();
			}

		});
	}

	/**
	 * 
	 */
	private void initView()
	{
		gridView = (GridView) findViewById(R.id.gridview);
		gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		adapter = new ImageGridAdapter(ImageGridActivity.this, dataList,
				mHandler);
		gridView.setAdapter(adapter);
		adapter.setTextCallback(new TextCallback()
		{
			public void onListen(int count)
			{
				wancheng.setText("完成" + "(" + (Bimp.max+count) + "/9)");
			}
		});

		gridView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				
				// if(dataList.get(position).isSelected()){
				// dataList.get(position).setSelected(false);
				// }else{
				// dataList.get(position).setSelected(true);
				// }
				
				adapter.notifyDataSetChanged();
			}

		});

	}
	
	// 监听返回事件
			// 返回
			@Override
			public boolean onKeyDown(int keyCode, KeyEvent event) {

				if (keyCode == KeyEvent.KEYCODE_BACK) {

					ImageGridActivity.this.finish();
					
	 
				}
				return super.onKeyDown(keyCode, event);
			}

}
