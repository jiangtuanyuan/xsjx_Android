package com.example.xsjx;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.EnrollPhoneItemAdapter;
import com.example.adapter.EnrollTypeItemAdapter;
import com.example.entity.EnrollPhoneItemEntity;
import com.example.entity.EnrollTypeItemEntity;
import com.example.internet.AccessInternet;
import com.example.json.entity.JzInfoEntity;
import com.example.json.entity.JzTelClass;
import com.example.json.entity.tel;
import com.example.utils.NoDoubleClickListener;
import com.example.utils.SharedUtils;
import com.example.utils.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @author ����Բ ��������
 */

public class EnrollActivity extends Activity {
	public TextView enrolltv,topenrollmoney;

	public ListView dllv;
	public EnrollTypeItemAdapter dladapter;

	public ListView phlv;
	public EnrollPhoneItemAdapter phadapter;
   public ImageView fanhui;
	public JzTelClass jzTelClass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_enroll);
		initState();
		findview();
		showJZDialog();
	}

	/**
	 * �ҿؼ�ID 2017-10-29
	 */

	public void findview() {
		fanhui=(ImageView) findViewById(R.id.enroll_fanhui);
		fanhui.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				EnrollActivity.this.finish();
				
			}
		});
		inflater = LayoutInflater.from(this);
		enrolltv = (TextView) findViewById(R.id.enroll_tv);
		topenrollmoney=(TextView) findViewById(R.id.enroll_tv_enrollmoney);
		
		dllv = (ListView) findViewById(R.id.enroll_DL_lv);
		phlv = (ListView) findViewById(R.id.enroll_Ph_lv);
	}

	/**
	 * ��ʾ���ؿ�
	 * 
	 */
	ProgressDialog dialog;

	public void showJZDialog() {
		dialog = ProgressDialog.show(this, "", "��ȡ������..\n�����Ե�!");
	//	dialog.setCancelable(true);
		AccessInternet.JzInfoServlet(handler);

	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				dialog.dismiss();
				// Utils.showToast(EnrollActivity.this, "������");
				jzTelClass = (JzTelClass) msg.obj;
				init();
				break;
			case 1:
				dialog.dismiss();
				Utils.showToast(EnrollActivity.this, "����ʧ��!����:" + msg.obj);
				break;
			default:
				break;
			}
		}
	};

	/**
	 * ���ÿ���ֵ 2017-10-29
	 */

	public void init() {
		enrolltv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);// ���»���
		enrolltv.setOnClickListener(new NoDoubleClickListener() {
			@SuppressLint({ "InflateParams", "ResourceAsColor" })
			@Override
			public void onNoDoubleClick(View v) {
				showDialog();
			}
		}); // enrolltv�����¼�
		// ��ü������Ͱ༶������
		getdldate();
		dladapter = new EnrollTypeItemAdapter(jzlist, this);
		dllv.setAdapter(dladapter);
		//���ձ���������
		topenrollmoney.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
	 
			
			}
		});
		

		// ��õ绰����
		getphdate();
		phadapter = new EnrollPhoneItemAdapter(tellist, this);
		phlv.setAdapter(phadapter);

		if (SharedUtils.getInstance().getString("enroll").equals("false"))
			showDialog();
	}

	/**
	 * ��ת�������������
	 */
	public void GoEnrollDetails(JzInfoEntity e) {
		Intent dinten = new Intent(this, EnrollDetailsActivity.class);
		dinten.putExtra("id", e.id);
		dinten.putExtra("type", e.type);
		dinten.putExtra("typeclass", e.typeclass);
		dinten.putExtra("money", e.money);
		dinten.putExtra("typeinfo", e.typeinfo);
		dinten.putExtra("examcontent", e.examcontent);
		dinten.putExtra("passmuster", e.passmuster);
		dinten.putExtra("moneyinfo", e.moneyinfo);
		dinten.putExtra("nomoneyinfo", e.nomoneyinfo);
		dinten.putExtra("serviceinfo", e.serviceinfo);
		dinten.putExtra("number", e.number);
		dinten.putExtra("asof", e.asof);
		startActivity(dinten);
	}

	/**
	 * ��ü�������
	 */

	List<JzInfoEntity> jzlist;

	public void getdldate() {
		if (jzTelClass.jzerror == null)
			jzlist = jzTelClass.jzlist;
		else {
			jzlist = new ArrayList<JzInfoEntity>();
			View head = LayoutInflater.from(this).inflate(R.layout.view_nodata, null);
			dllv.addHeaderView(head);
		}

	}

	/**
	 * ��õ绰����
	 */
	List<tel> tellist;
	public void getphdate() {
		if (jzTelClass.telerror == null)
			tellist = jzTelClass.tellist;
		else {
			tellist = new ArrayList<tel>();
			View head = LayoutInflater.from(this).inflate(R.layout.view_nodata, null);
			phlv.addHeaderView(head);
		}

	}

	/**
	 * ����phadapterһ���绰���� ����绰
	 */
	public void phoneUrl(String phone) {
		Intent intent = new Intent(Intent.ACTION_DIAL);
		Uri data = Uri.parse("tel:" + phone);
		intent.setData(data);
		startActivity(intent);
	}

	/**
	 * ��ʾ ������֪ ���ݴ���
	 */
	public LayoutInflater inflater;
	public Dialog bu2;
	public View popo;

	public void showDialog() {
		if (inflater == null)
			inflater = LayoutInflater.from(EnrollActivity.this);
		if (bu2 == null)
			bu2 = new Dialog(EnrollActivity.this, R.style.testDialog);
		if (popo == null)
			popo = inflater.inflate(R.layout.enroll_popo, null);

		Button btn = (Button) popo.findViewById(R.id.enroll_popo_btn);
		final CheckBox cb = (CheckBox) popo.findViewById(R.id.enroll_popo_cb);
		btn.setOnClickListener(new NoDoubleClickListener() {
			@SuppressLint("ShowToast")
			@Override
			public void onNoDoubleClick(View v) {
				if (cb.isChecked()) {
					SharedUtils.getInstance().putString("enroll", "true");
					bu2.dismiss();

				} else {
					SharedUtils.getInstance().putString("enroll", "false");
					Toast.makeText(getApplicationContext(), "�빴ѡ��֪����!", 1).show();
				}

			}
		});
		cb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (cb.isChecked()) {
					cb.setTextColor(getResources().getColor(R.color.blue));
				} else {
					cb.setTextColor(getResources().getColor(R.color.lightsteelblue));
				}

			}
		});

		bu2.setContentView(popo);
		bu2.show();

		WindowManager.LayoutParams lp = bu2.getWindow().getAttributes();
		lp.width = (int) (MainApplication.getMetrics(this).widthPixels); // ���ÿ��

		bu2.getWindow().setAttributes(lp);

	}

	/**
	 * ����״̬��
	 */
	private void initState() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			// ͸��״̬��
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// ͸��������
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}
}
