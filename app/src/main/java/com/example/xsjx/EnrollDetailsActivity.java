package com.example.xsjx;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.entity.EnrollTypeItemEntity;
import com.example.internet.AccessInternet;
import com.example.json.entity.JzInfoEntity;
import com.example.testpic.PublishedActivity;
import com.example.utils.NoDoubleClickListener;
import com.example.utils.SharedUtils;
import com.example.utils.UserInfo;
import com.example.utils.Utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 
 * @author 蒋团圆 报名详情 界面对应的Activity
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class EnrollDetailsActivity extends Activity {
	public JzInfoEntity ELT;
	public TextView EnrollType, EnrollClass, EnrollMoney, EnrollMan;
	public ImageView fanhui;
	public TextView tv1, tv2, tv3, tv4, tv5, tv6;
	public Button EnrollBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_enroll_details);
		initState();
		findvalue();
		findView();
		init();
	}

	/**
	 * 找传替过来的值
	 */
	public void findvalue() {
		ELT = new JzInfoEntity();
		Intent intent = getIntent();
		ELT.id = intent.getIntExtra("id", 1);
		ELT.type = intent.getStringExtra("type");
		ELT.typeclass = intent.getStringExtra("typeclass");
		ELT.money = intent.getDoubleExtra("money", 1);
		ELT.typeinfo = intent.getStringExtra("typeinfo");
		ELT.examcontent = intent.getStringExtra("examcontent");
		ELT.passmuster = intent.getStringExtra("passmuster");
		ELT.moneyinfo = intent.getStringExtra("moneyinfo");
		ELT.nomoneyinfo = intent.getStringExtra("nomoneyinfo");
		ELT.serviceinfo = intent.getStringExtra("serviceinfo");
		ELT.number = intent.getIntExtra("number", 2);
		
		ELT.asof=intent.getIntExtra("asof", 1);
	}

	/**
	 * 找控件ID
	 */
	public void findView() {
		EnrollType = (TextView) findViewById(R.id.Enroll_details_type);
		EnrollClass = (TextView) findViewById(R.id.Enroll_details_class);
		EnrollMoney = (TextView) findViewById(R.id.Enroll_details_money);
		EnrollMan = (TextView) findViewById(R.id.Enroll_details_man);
		fanhui = (ImageView) findViewById(R.id.Enroll_details_fanhui);

		tv1 = (TextView) findViewById(R.id.Enroll_details_type_introduced);
		tv2 = (TextView) findViewById(R.id.Enroll_details_kaoshi_introduced);
		tv3 = (TextView) findViewById(R.id.Enroll_details_hege_introduced);
		tv4 = (TextView) findViewById(R.id.Enroll_details_feiyong_introduced);
		tv5 = (TextView) findViewById(R.id.Enroll_details_nofeiyong_introduced);
		tv6 = (TextView) findViewById(R.id.Enroll_details_fuwu_introduced);

		EnrollBtn = (Button) findViewById(R.id.Enroll_details_Enrollbtn);
	}

	/**
	 * 设置控件值
	 */

	public void init() {
		EnrollType.setText(ELT.type);// +"="+ELT.id
		EnrollClass.setText(ELT.typeclass);
		EnrollMoney.setText(ELT.money + " ¥(RMB)");
		EnrollMan.setText("(" + ELT.number + "人报名)");

		tv1.setText(ELT.typeinfo.replace("\\n", "\n"));
		tv2.setText(ELT.examcontent.replace("\\n", "\n"));
		tv3.setText(ELT.passmuster.replace("\\n", "\n"));
		tv4.setText(ELT.moneyinfo.replace("\\n", "\n"));
		tv5.setText(ELT.nomoneyinfo.replace("\\n", "\n"));
		tv6.setText(ELT.serviceinfo.replace("\\n", "\n"));

		fanhui.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				EnrollDetailsActivity.this.finish();
			}
		});
		if(ELT.asof==1){
			//说明报名截止了 设置报名不可以点击
			EnrollBtn.setBackgroundResource(R.drawable.shape_touch_bule_button);
			EnrollBtn.setText("报名已截止!");
			EnrollBtn.setEnabled(false);
		}
		else{

		// 防止重复点击 3.5秒内智能点击一次
		EnrollBtn.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
				/**
				 * 点击报名按钮后 判断当前用户名是否已经报名过 如果报名了 就不显示填写资料框
				 */
				
				//1.进行身份判断  如果是教练点击了报名 就不弹出
				int LogDoTypes = SharedUtils.getInstance().getInt("LogDoTypes");
				
				if(LogDoTypes!=0 && LogDoTypes==2)
				{
					Utils.showToast(EnrollDetailsActivity.this, "教练不可以报名哟!");
				}
				else {
					if(UserInfo.IdentityID==5)
						  ShowDataDialog();
					
                   else Utils.showToast(EnrollDetailsActivity.this, "您已经是学员或者已经拿证或者是管理员!,无法报名!");
				}
				

				//showJZDialog();
			}
		});
		
		   }
		 
	}

	ProgressDialog dialog;

	public void showJZDialog() {
		dialog = ProgressDialog.show(this, "", "用户信息检查中..");
		if(UserInfo.UserID!=null)
		 AccessInternet.UserJZServlet(handler, UserInfo.UserID);
		else {
			//UserInfo.updateUserInfo();
		   dialog.dismiss();
		}
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				dialog.dismiss();
				Utils.showToast(EnrollDetailsActivity.this, "您已经报名了哟!");

				break;
			case 1:
				dialog.dismiss();
				// 未报名 然后

				//ShowDataDialog();
				break;
			case 2:
				dialog.dismiss();
				Utils.showToast(EnrollDetailsActivity.this, "错误:" + msg.obj);
				break;
			// 以下的cese为popo窗口使用
			case 3:
				// 携带用户ID 和驾照信息跳转到收银台!
				
				// Utils.showToast(EnrollDetailsActivity.this, "跳转到收银台");
				bu2.dismiss();
 
				//暂时不做支付模块
				//Utils.CheckStand(EnrollDetailsActivity.this, "报名学车-"+ELT.type+"-"+ELT.typeclass, ELT.id+"", 1, UserInfo.UserName, UserInfo.UserID, ELT.money);
				Utils.showToast(EnrollDetailsActivity.this, "您的报名信息已经提交成功!驾校工作人员将会与您取得联系!");
				
				break;
			case 4:
				// 错误
				Utils.showToast(EnrollDetailsActivity.this, ""+msg.obj);
				bu2.dismiss();	
				break;	
			default:
				break;
			}

		}

	};


	/**
	 * 点击报名后 显示”填写报名资料窗口“
	 */
	public LayoutInflater inflater;
	Dialog bu2;
	View popo;
	String rbxinda = "否";
	RadioGroup xindarg;
	public void ShowDataDialog() {
		if (inflater == null)
			inflater = LayoutInflater.from(EnrollDetailsActivity.this);
		if (bu2 == null)
			bu2 = new Dialog(EnrollDetailsActivity.this, R.style.testDialog);
		if (popo == null)
			popo = inflater.inflate(R.layout.enroll_info_popo, null);
		// 点击关闭当前popo窗口
		popo.findViewById(R.id.enroll_info_x).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				bu2.dismiss();
			}
		});

		final Button bt = (Button) popo.findViewById(R.id.enroll_info_btn);

		final CheckBox cb = (CheckBox) popo.findViewById(R.id.enroll_info_checkbox);
		cb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (cb.isChecked()) {
					cb.setTextColor(getResources().getColor(R.color.blue));
					bt.setBackgroundResource(R.drawable.login_button_selector);

				} else {
					cb.setTextColor(getResources().getColor(R.color.lightsteelblue));
					bt.setBackgroundResource(R.drawable.shape_touch_bule_button);
				}
			}
		});

		TextView usernme = (TextView) popo.findViewById(R.id.enroll_info_username);
		usernme.setText(UserInfo.UserName);

		xindarg= (RadioGroup) popo.findViewById(R.id.enroll_info_Rg);
		xindarg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
		     @Override 
		     public void onCheckedChanged(RadioGroup group, int checkedId) { 
		    //	RadioButton rb = (RadioButton) findViewById(group.getCheckedRadioButtonId());
		    	if(checkedId==R.id.enroll_info_Rb1)
		    		  rbxinda="是";
		    	if(checkedId==R.id.enroll_info_Rb2)
		    		 rbxinda="否";
		        }
		     });
		 

		final EditText e1, e2, e3, e4, e5;
		e1 = (EditText) popo.findViewById(R.id.enroll_info_et_name);
		e2 = (EditText) popo.findViewById(R.id.enroll_info_et_card);
		e3 = (EditText) popo.findViewById(R.id.enroll_info_et_tel);
		e4 = (EditText) popo.findViewById(R.id.enroll_info_et_weixin);
		e5 = (EditText) popo.findViewById(R.id.enroll_info_et_qq);
		
		
		//过滤掉表情等特殊字符
		 InputFilter emojiFilter = new InputFilter() {
		        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
		                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		        @Override
		        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
		            Matcher emojiMatcher = emoji.matcher(source);
		            if (emojiMatcher.find()) {
		               Utils.showToast(EnrollDetailsActivity.this, "不支持输入表情");
		                return "";
		            }
		            return null;
		        }
		    };
		   InputFilter[] emojiFilters = {emojiFilter};
		   e1.setFilters(emojiFilters);
		   e4.setFilters(emojiFilters);
		   
		   

		bt.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
			
				String s1, s2, s3, s4, s5;
				s1 = e1.getText().toString().replace(" ", "");
				s2 = e2.getText().toString().replace(" ", "");
				s3 = e3.getText().toString().replace(" ", "");
				s4 = e4.getText().toString().replace(" ", "");
				s5 = e5.getText().toString().replace(" ", "");

				// Utils.showToast(EnrollDetailsActivity.this, "请完善必填的信息!");
				if (s1 != null && !s1.equals("") && s2 != null && !s2.equals("") && s3 != null && !s3.equals("")) {
					if (cb.isChecked()) {
				AccessInternet.AddUserEnrolInfo(handler, UserInfo.UserID, s1, s2, s3, s4, s5, rbxinda,ELT.id+"");
						// Utils.showToast(EnrollDetailsActivity.this,
						// "数据可以提交");
					} else {
						Utils.showToast(EnrollDetailsActivity.this, "请勾选-'我已确定报名信息无误!'");
					}
				} else {
					Utils.showToast(EnrollDetailsActivity.this, "请完善必填的信息!");
				}
			}
		});

		bu2.setContentView(popo);
		bu2.show();
		// 设置高宽
		WindowManager.LayoutParams lp = bu2.getWindow().getAttributes();
		lp.width = (int) (MainApplication.getMetrics(this).widthPixels); // 设置宽度
		lp.height = (int) (MainApplication.getMetrics(this).heightPixels); // 设置高度
		bu2.getWindow().setAttributes(lp);
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
