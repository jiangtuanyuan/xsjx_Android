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
	 * ��ʾ���ؿ�
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
		// ע��˴�Ҫ����show֮�� ����ᱨ�쳣
	    View v=inflatera.inflate(R.layout.loading_process_dialog_anim, null);
	    TextView stv=(TextView) v.findViewById(R.id.loading_dialog_tv);
	    stv.setText(c);
		dialog.setContentView(v);
	}
	
	
	/**
	 *��ʼ��
	 */
	@Override
	protected void initView() {
		setContentView(R.layout.activity_user_home);
		getValue();
		showDialog("������");
		
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
 * ����
 */
	@Override
	protected void initData() {
		x.image().bind(HeadImg, HeadimgStr, Utils.Xheadimg());
		Coach.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//���»���
		Name.setText(NameStr); 
		switch (IdentityID) {
		case 2:
			Identity.setText("����");
			Identity.setTextColor(getResources().getColor(R.color.green));
			break;
		case 3:
			Identity.setText("ѧԱ");
			Identity.setTextColor(getResources().getColor(R.color.yellow));
			break;
		case 5:
			Identity.setText("�ο�");
			//Identity.setTextColor(getResources().getColor(R.color.green));
			break;
		case 6:
			Identity.setText("����֤");
			Identity.setTextColor(getResources().getColor(R.color.red));
			break;
		default:
			Identity.setText("�ο�");
			break;
		}
		
		
		
		

	}
/**
 * �¼�
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
		case 0://���سɹ�
			dialog.dismiss();
			Plist.clear();
			Plist.addAll((List<Posting>) msg.obj);
		//	Utils.showToast(getActivity(), "plis.size()="+Plist.size());
			adapter.notifyDataSetChanged();
			PostingNum.setText(" ("+Plist.size()+"��) ");
			
			break;
		case 1:
			dialog.dismiss();
			//����ʧ��
			Utils.showToast(UserHomeActivity.this, msg.obj+"");
			break;
		   }
		}
		};
	/**
	 * �Ҵ�������ֵ
	 */
	private void getValue(){
		NameStr=getIntent().getStringExtra("username");
		HeadimgStr=getIntent().getStringExtra("headimg");
		IdentityID=getIntent().getIntExtra("identityID", 0);
		UserId=getIntent().getIntExtra("userid", 0);
		
	}
	

}
