package com.example.xsjx;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xutils.x;

import com.example.adapter.BBSDisAdapter;
import com.example.adapter.ImgGvAdapter;

import com.example.internet.AccessInternet;
import com.example.json.entity.PComments;
import com.example.json.entity.Posting;
import com.example.testpic.NoScrollGridView;
import com.example.testpic.PublishedActivity;
import com.example.utils.CollapsibleTextView;
import com.example.utils.EditTextClearTools;
import com.example.utils.NoDoubleClickListener;
import com.example.utils.SharedUtils;
import com.example.utils.UserInfo;
import com.example.utils.Utils;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;

import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import android.widget.AdapterView;

import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.BufferType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

import android.widget.TextView;

/**
 * 
 * @author ����Բ ��̳ģ����� ����
 */
public class BBSdetailsActivity extends Activity {
	public Posting p;
	public ImageView fanhui, CiecleImg;
	public TextView name, type, date, title, contennum;//conten,
	public CollapsibleTextView conten;
	public NoScrollGridView gv;
    //�ָ���
	public EditText et;
	public Button submit;
	//�ָ���
	public ListView commentsList;
	public List<PComments> pcList=new ArrayList<PComments>();
	public BBSDisAdapter adapter;
	public LayoutInflater inflatera = null;
	//���Ͻǵĵ��TV
	public TextView dian;
	//
	int LogDoTypes = 0, LogDoTypesID = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// ���ƴ�ҳ��ֻ��������ʾ
		setContentView(R.layout.activity_bbsdetails2);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
				| WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);// ��������������뷨֮��
		// ״̬������ 1
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
					setTranslucentStatus(true);
				}
				SystemBarTintManager tintManager = new SystemBarTintManager(this);
				tintManager.setStatusBarTintEnabled(true);
				tintManager.setNavigationBarTintEnabled(true);
				// �Զ�����ɫ
				tintManager.setTintColor(Color.parseColor("#12b7f5"));
		
		
		// �ҵ���������Posting ֵ
		getbbsValue();
		if (inflatera == null)
			inflatera = LayoutInflater.from(this);
		showDialog("加载中...");

		
		if(p.PostingID!=0){
		
			AccessInternet.getPostingComments(handler, p.PostingID);
		// ��ʼ��ui
		initView();
		// ��ʼ������
		initData();
		// ��Ӽ�����
		initListener();
		}
		else{
			Utils.showToast(this, "��������!");
			this.finish();
		}
		
		
		LogDoTypes = SharedUtils.getInstance().getInt("LogDoTypes");
		LogDoTypesID = SharedUtils.getInstance().getInt("LogDoTypesID");
		if (LogDoTypes > 0 && LogDoTypesID > 0) {
			if (LogDoTypes != 2 && UserInfo.postingbanned == 1) {
				Utils.showToast(this, "���Ѿ�������!");
				submit.setEnabled(false);
				et.setHint("���Ѿ�������!");
				et.setEnabled(false);
			}
			EditTextClearTools.addEditTextText(et, submit);//���ύ��ť���¼� �����ݾ���ɫ û���ݾͻ�ɫ
			
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
	
	/**
	 * ��ʾ���ؿ�
	 */
	Dialog dialog=null;
	private void showDialog(String c) {
		if(dialog==null)
		{dialog = new Builder(this).create();
		 dialog.setCancelable(false);}
		 
		dialog.show();
		// ע��˴�Ҫ����show֮�� ����ᱨ�쳣
	    View v=inflatera.inflate(R.layout.loading_process_dialog_anim, null);
	    TextView stv=(TextView) v.findViewById(R.id.loading_dialog_tv);
	    stv.setText(c);
		dialog.setContentView(v);
	}

	/**
	 * ��ʼ��UI
	 */
	public void initView() {
		fanhui = (ImageView) findViewById(R.id.activity_bbsdetail2_fanhui);// ����
		dian=(TextView) findViewById(R.id.activity_bbsdetail2_dian);
		
		CiecleImg = (ImageView) findViewById(R.id.activity_bbsdetail2_layou_item)
				.findViewById(R.id.fragment4_lv_CiecleImg);// ͷ��
		name = (TextView) findViewById(R.id.activity_bbsdetail2_layou_item).findViewById(R.id.fragment4_lv_username);// �û���
		type = (TextView) findViewById(R.id.activity_bbsdetail2_layou_item).findViewById(R.id.fragment4_lv_usertypr);// ���
		date = (TextView) findViewById(R.id.activity_bbsdetail2_layou_item).findViewById(R.id.fragment4_lv_datetime);// ����ʱ��
		title = (TextView) findViewById(R.id.activity_bbsdetail2_layou_item).findViewById(R.id.fragment4_lv_tile);// ��������
		conten = (CollapsibleTextView) findViewById(R.id.activity_bbsdetail2_layou_item).findViewById(R.id.fragment4_lv_conten);// ��������
		gv = (NoScrollGridView) findViewById(R.id.activity_bbsdetail2_layou_item)
				.findViewById(R.id.fragment4_lv_GridView);// ͼƬǽ
//�ָ���
		et=(EditText) findViewById(R.id.activity_bbsdetail2_et);
		submit=(Button) findViewById(R.id.activity_bbsdetail2_submit);
		
//�ָ���
		contennum=(TextView) findViewById(R.id.activity_bbsdetail2_bbsnum);
		
		commentsList=(ListView) findViewById(R.id.activity_bbsdetail2_list);
		adapter=new BBSDisAdapter(pcList, this);
		commentsList.setAdapter(adapter);
		
	}

	/**
	 * ��ʼ������
	 */
	public void initData() {
		
		if(p.identityID==2)
			x.image().bind(CiecleImg, p.headImg, Utils.XCoachCireHeadimg());// ����ͷ��
		else x.image().bind(CiecleImg, p.headImg, Utils.Xheadimg());// ����ͷ��
		
		name.setText(p.name);// �����û���
		type.setText(p.identity);// �������
		switch (p.identityID) {
		case 1:// �����û�
			type.setTextColor(getResources().getColor(R.color.black));
			break;
		case 2:
			// ����
			type.setTextColor(getResources().getColor(R.color.green));
			break;
		case 3:
			// ѧԱ
			type.setTextColor(getResources().getColor(R.color.yellow));
			break;
		case 4:
			// ����Ա
			type.setTextColor(getResources().getColor(R.color.red));
			break;
		case 5:
			// �ο�
			type.setTextColor(getResources().getColor(R.color.blue));
			break;
      case 6:
    	  type.setTextColor(getResources().getColor(R.color.red));
    	
			break;	

		default:
			// Ĭ���ο�3
			type.setTextColor(getResources().getColor(R.color.blue));
			break;
		}
		date.setText(p.date.subSequence(0, 16));// ����ʱ�� ��ȡ16λ
		title.setText(p.PostingTitle);// ���ñ���
		//conten.setText(p.PostingConten);// ��������
		
		conten.setFlag(false);
		conten.setDesc(p.PostingConten, BufferType.NORMAL);
		
		
		gv.setSelector(new ColorDrawable(Color.TRANSPARENT));// ����ѡ�е�ʱ��Ϊ͸��
		gv.setAdapter(new ImgGvAdapter(this, p.PostingImg));// ����������

		//�ָ���
		
		
		//��ֹ�������
		   et.setFilters(Utils.getInputFilter(this, 250));  
	}

	/**
	 * ����¼�������
	 */
	public void initListener() {
		fanhui.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				BBSdetailsActivity.this.finish();
			}
		});
		dian.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
				
				//����ˢ�º�ɾ����
				showDelectBBS();
			}
		});
		
		//���ͷ��
		CiecleImg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				switch (p.identityID) {
				case 2:
					/*��ת������ҳ��
					
					Intent coach=new Intent(BBSdetailsActivity.this,CoachInfoActivity.class);
					coach.putExtra("coachid", p.typesID);
					startActivity(coach);
					*/
					Utils.showToast(BBSdetailsActivity.this, "Sorry,暂时无法查看该用户资料!");
					
					break;
				case 3:
                   //��ת��ѧԱ
					Intent userhome=new Intent(BBSdetailsActivity.this,UserHomeActivity.class);
					userhome.putExtra("username", p.name);
					userhome.putExtra("identityID", p.identityID);
					userhome.putExtra("userid", p.typesID);
					userhome.putExtra("headimg", p.headImg);
					startActivity(userhome);
					break;
				case 5:
					//�ο�
					Intent userhome5=new Intent(BBSdetailsActivity.this,UserHomeActivity.class);
					userhome5.putExtra("username", p.name);
					userhome5.putExtra("identityID", p.identityID);
					userhome5.putExtra("userid", p.typesID);
					userhome5.putExtra("headimg", p.headImg);
				    startActivity(userhome5);
					break;
				case 6:
              //�Ѿ���֤
					Intent userhome6=new Intent(BBSdetailsActivity.this,UserHomeActivity.class);
					userhome6.putExtra("username", p.name);
					userhome6.putExtra("identityID", p.identityID);
					userhome6.putExtra("userid", p.typesID);
					userhome6.putExtra("headimg", p.headImg);
					startActivity(userhome6);
					break;
            	default:
            		Utils.showToast(BBSdetailsActivity.this, "�޷��鿴���û�����!");
					break;
				}
				
			}
		});
		
		// ��������Ź��񣬲鿴��ͼ
		gv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(BBSdetailsActivity.this, ImagePagerActivity.class);
				intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, p.PostingImg);// ͼƬURL
				intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);// �ڼ���
				startActivity(intent);
			}
		});
		submit.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
				UploadPostingComments();
			}
		});

	}
	/**
	 * ����ˢ�»���ɾ�����ӵķ���
	 */
	private Dialog Dbbs = null;
	private View Vbbs = null;
	private TextView HeadTV,shuaxin,shanchu,quxiao;
	
	private void showDelectBBS(){
		if (Dbbs == null)
			Dbbs = new Dialog(this, R.style.ActionSheetDialogStyle);
		if (Vbbs == null)
			{ Vbbs = inflatera.inflate(R.layout.fragment_5_roundhead_dialog, null);
			  HeadTV = (TextView) Vbbs.findViewById(R.id.fragment_5_roundhead_dialog_head);
			  HeadTV.setText("��ѡ�����Ĳ���");
			  shuaxin=(TextView) Vbbs.findViewById(R.id.fragment_5_roundhead_dialog_paizhao);
			  shanchu=(TextView) Vbbs.findViewById(R.id.fragment_5_roundhead_dialog_xiangce);
			  quxiao=(TextView) Vbbs.findViewById(R.id.fragment_5_roundhead_dialog_quxiao);
			  }
		Dbbs.setContentView(Vbbs);
		
		Window dialogWindow = Dbbs.getWindow();
		dialogWindow.setGravity(Gravity.BOTTOM);
		Dbbs.show();
		WindowManager.LayoutParams lp = Dbbs.getWindow().getAttributes();
		lp.width = MainApplication.phoneWidth;
		Dbbs.getWindow().setAttributes(lp);
		
		//����Ա���
	   if(LogDoTypes==4){
		   shuaxin.setText("ˢ  ��");
		   shanchu.setText("ɾ  ��");
		   shanchu.setTextColor(ContextCompat.getColor(this, R.color.red));
		   shanchu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Dbbs.dismiss();
				DelectPosting();
			}
		});   
	   }
	   else {
		   shuaxin.setText("ˢ  ��");
		   shanchu.setVisibility(View.GONE);
	   }
		
	   shuaxin.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			Dbbs.dismiss();
			shuaxinI=1;
			showDialog("ˢ����..");
			AccessInternet.getPostingComments(handler, p.PostingID);
			
		}
	});	
	   quxiao.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			Dbbs.dismiss();
			
		}
	});	
		
		
		
	}
	/**
	 * �����Ƿ�ɾ��������
	 */
	Builder bu1=null; 
	public void DelectPosting(){
		if(bu1==null){
			bu1= new Builder(this);
			bu1.setTitle("��ʾ:");
			bu1.setMessage("�Ƿ�ɾ��������?");
			bu1.setNegativeButton("ȡ��",new DialogInterface.OnClickListener() {	
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
									
				}
			});
		
	        bu1.setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {	
					showDialog("ɾ����..");
					AccessInternet.DelectPosting(handler, p.PostingID);
				}
			});
			bu1.create();
		}
		bu1.show();
		
	}		
	
	
	/**
	 * �������ӵķ���
	 */
	public void UploadPostingComments(){
		//��������
		String conten=et.getText().toString().replace(" ", "");
		if(conten.length()>0&&conten.length()<=250){
			showDialog("����������..");
			if(LogDoTypes!=0 && LogDoTypesID!=0)

			AccessInternet.uploadpostingComments(handler, p.PostingID, conten, LogDoTypes, LogDoTypesID+"");	
			else {
				Message msg=new Message();
				msg.what=1;
				msg.obj="����δ֪����,������APP!";
				handler.sendMessage(msg);}
		}
		else{
			Utils.showToast(this, "�������ݲ���Ϊ��,�Ҳ���ȫ�ǿո�!");
		}
		
	}
	
	
	/**
	 * Handler 
	 */
	int shuaxinI=0;
	Handler handler=new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
		switch (msg.what) {
		case 0://��������
			 dialog.dismiss();
			 pcList.clear();
			 pcList.addAll((List<PComments>)msg.obj);
			 adapter.notifyDataSetChanged();
			 
			 setListViewHeightBasedOnChildren(commentsList);
			 contennum.setText("�� �� ( "+pcList.size()+" )");
			  if(shuaxinI!=0)
			  Utils.showToast(BBSdetailsActivity.this,"����ˢ�³ɹ�!");
			 
			 //Utils.showToast(BBSdetailsActivity.this, "�ɹ���������");
			break;
	   case 1://���մ���
		   dialog.dismiss();
		   Utils.showToast(BBSdetailsActivity.this, msg.obj+"");
			break;
	   case 2://���� ���۳ɹ�
		   dialog.dismiss();
		   et.setText("");
		   AccessInternet.getPostingComments(handler, p.PostingID);
		   
		   Utils.showToast(BBSdetailsActivity.this, msg.obj+"");
			break;
	   case 3://ɾ���ɹ�  ����Ա��
		    dialog.dismiss();
		    Utils.showToast(BBSdetailsActivity.this,"ɾ���ɹ�,�뷵��ˢ�²鿴!");
		    submit.setEnabled(false);
			et.setHint("�����Ѿ���ɾ��!");
			et.setEnabled(false);
		   
		   break;
	   case 4://ɾ��ʧ��  ����Ա��
		   dialog.dismiss();
		   Utils.showToast(BBSdetailsActivity.this,"ɾ��ʧ��!������!");
		   break; 
		   
		   
		default:
			break;
		}
		}
		
	};
	
	/**
	 * ����ListView�ĸ߶�    �����õĻ��ᵼ��ListView ֻ��ʾһ������
	 */
   public void setListViewHeightBasedOnChildren(ListView listView) {   
	        // ��ȡListView��Ӧ��Adapter   
	        ListAdapter listAdapter = listView.getAdapter();   
	        if (listAdapter == null) {   
	            return;   
	        }   
	   
	        int totalHeight = 0;   
	        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {   
	            // listAdapter.getCount()�������������Ŀ   
	            View listItem = listAdapter.getView(i, null, listView);   
	            // ��������View �Ŀ��   
	            listItem.measure(0, 0);    
	            // ͳ������������ܸ߶�   
	            totalHeight += listItem.getMeasuredHeight();    
	        }   
	        ViewGroup.LayoutParams params = listView.getLayoutParams();   
	        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));   
	        // listView.getDividerHeight()��ȡ�����ָ���ռ�õĸ߶�   
	        // params.height���õ�����ListView������ʾ��Ҫ�ĸ߶�   
	        listView.setLayoutParams(params);   
	    } 
	
	/**
	 * ��ȡ�������BBS��ֵ
	 */
	public void getbbsValue() {
		p = new Posting();
		p.PostingID = getIntent().getIntExtra("PostingID", 0);
		p.PostingTitle = getIntent().getStringExtra("PostingTitle");
		p.PostingConten = getIntent().getStringExtra("PostingConten");
		p.date = getIntent().getStringExtra("date");
		p.identityID = getIntent().getIntExtra("identityID", 0);
		p.identity = getIntent().getStringExtra("identity");
		p.typesID = getIntent().getIntExtra("typesID", 0);
		p.name = getIntent().getStringExtra("name");
		p.headImg = getIntent().getStringExtra("headImg");
		p.PostingImg = getIntent().getStringArrayListExtra("PostingImg");
	}

}
