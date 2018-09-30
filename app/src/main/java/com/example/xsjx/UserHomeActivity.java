package com.example.xsjx;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;

import com.example.adapter.BBSDisAdapter;
import com.example.adapter.PostingAdapter;
import com.example.base.Base2Activity;
import com.example.internet.AccessInternet;
import com.example.json.entity.PComments;
import com.example.json.entity.Posting;
import com.example.utils.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class UserHomeActivity extends Base2Activity {

	private ImageView fanhui;
	private ImageView HeadImg;
	private TextView Name,Sex,Identity,Coach,PostingNum;
	private ListView lv;
	private String NameStr,HeadimgStr;
	private int IdentityID,UserId;
	
	
	public ListView PostListView;
	public List<Posting> Plist=new ArrayList<Posting>();
	public PostingAdapter adapter;

	/**
	 * 显示加载框
	 */
	LayoutInflater inflatera = null;
	Dialog dialog=null;
	private void showDialog(String c) {
		if (inflatera == null)
			inflatera = LayoutInflater.from(this);
		if(dialog==null)
		{dialog = new AlertDialog.Builder(this).create();
		 dialog.setCancelable(false);}
		dialog.show();
		// 注意此处要放在show之后 否则会报异常
	    View v=inflatera.inflate(R.layout.loading_process_dialog_anim, null);
	    TextView stv=(TextView) v.findViewById(R.id.loading_dialog_tv);
	    stv.setText(c);
		dialog.setContentView(v);
	}
	
	
	/**
	 *初始化
	 */
	@Override
	protected void initView() {
		setContentView(R.layout.activity_user_home);
		getValue();
		showDialog("加载中");
		
		AccessInternet.getPosting(handler, IdentityID, UserId);
		
		fanhui = (ImageView) findViewById(R.id.UserHome_fanhui);
		HeadImg=(ImageView) findViewById(R.id.UserHome_HeadImg);
		Name=(TextView) findViewById(R.id.UserHome_UserName);
		Sex=(TextView) findViewById(R.id.UserHome_UserSex);
		Identity=(TextView) findViewById(R.id.UserHome_Identity);
		Coach=(TextView) findViewById(R.id.UserHome_UserCoach);
		PostingNum=(TextView) findViewById(R.id.UserHome_PostingSum);
		
		PostListView=(ListView) findViewById(R.id.UserHome_LV);
		adapter=new PostingAdapter(Plist, this, 1);
		PostListView.setAdapter(adapter);
		
		
	}
/**
 * 数据
 */
	@Override
	protected void initData() {
		x.image().bind(HeadImg, HeadimgStr, Utils.Xheadimg());
		Coach.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//带下划线
		Name.setText(NameStr); 
		switch (IdentityID) {
		case 2:
			Identity.setText("教练");
			Identity.setTextColor(getResources().getColor(R.color.green));
			break;
		case 3:
			Identity.setText("学员");
			Identity.setTextColor(getResources().getColor(R.color.yellow));
			break;
		case 5:
			Identity.setText("游客");
			//Identity.setTextColor(getResources().getColor(R.color.green));
			break;
		case 6:
			Identity.setText("已拿证");
			Identity.setTextColor(getResources().getColor(R.color.red));
			break;
		default:
			Identity.setText("游客");
			break;
		}
		
		
		
		

	}
/**
 * 事件
 */
	@Override
	protected void initListener() {
		fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				UserHomeActivity.this.finish();

			}
		});

	}
Handler handler=new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
		switch (msg.what){
		case 0://加载成功
			dialog.dismiss();
			Plist.clear();
			Plist.addAll((List<Posting>) msg.obj);
		//	Utils.showToast(getActivity(), "plis.size()="+Plist.size());
			adapter.notifyDataSetChanged();
			PostingNum.setText(" ("+Plist.size()+"条) ");
			
			break;
		case 1:
			dialog.dismiss();
			//加载失败
			Utils.showToast(UserHomeActivity.this, msg.obj+"");
			break;
		   }
		}
		};
	/**
	 * 找传过来的值
	 */
	private void getValue(){
		NameStr=getIntent().getStringExtra("username");
		HeadimgStr=getIntent().getStringExtra("headimg");
		IdentityID=getIntent().getIntExtra("identityID", 0);
		UserId=getIntent().getIntExtra("userid", 0);
		
	}
	

}
