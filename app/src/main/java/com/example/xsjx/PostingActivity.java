package com.example.xsjx;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.example.adapter.PostingGridAdapter;
import com.example.adapter.PostingGridAdapter.ViewHolder;
import com.example.base.Base2Activity;
import com.example.internet.AccessInternet;
import com.example.internet.NetConn;
import com.example.json.entity.Postingtity;
import com.example.testpic.Bimp;
import com.example.utils.EditTextClearTools;
import com.example.utils.FileUtils;
import com.example.utils.Filepath;
import com.example.utils.NoDoubleClickListener;
import com.example.utils.UserInfo;
import com.example.utils.Utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @Date 2017��12��21�� ����7:07:07
 * 
 *       ��̳ģ��--��Ҫ����
 */
public class PostingActivity extends Base2Activity {
	private EditText title, conten;
	private TextView submit, sf, ms;
	private CheckBox cb;
	private ImageView fanhui;
	private GridView noScrollgridview;
	private PostingGridAdapter adapter;

	public String TAG = "PostingActivity";

	// private GridView gridView;

	/**
	 * ��ʾ���ؿ�
	 */
	ProgressDialog dialog;

	private void showDialog() {
		dialog = ProgressDialog.show(this, "", "������..");
	}

	@Override
	protected void initView() {
		setContentView(R.layout.activity_posting);
		// �������� ��������
		if (UserInfo.UserID != null && Utils.isNoNumber(UserInfo.UserID)) {
		//	AccessInternet.getPostingtity(UserInfo.UserID, Handler);
			showDialog();
		} else {
			closePosting("   �޷����ӵ��������ķ���ģ��!");
		}

		title = (EditText) findViewById(R.id.Posting_title);
		conten = (EditText) findViewById(R.id.Posting_conten);
		submit = (TextView) findViewById(R.id.Posting_submit);
		fanhui = (ImageView) findViewById(R.id.Posting_fanhui);
		cb = (CheckBox) findViewById(R.id.Posting_niming);
		sf = (TextView) findViewById(R.id.Posting_sf);
		ms = (TextView) findViewById(R.id.Posting_ms);
		noScrollgridview = (GridView) findViewById(R.id.Posting_noScrollgridview);
	}

	@Override
	protected void initData() {
		// �����ݾ���ʾ ����Ϊ��ɫ
		EditTextClearTools.addEditTextText(title, conten, submit);

		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));// ����GV�����͸��ɫ
		adapter = new PostingGridAdapter(this);
		noScrollgridview.setAdapter(adapter);

		// 7.0ϵͳ���ε����ͼƬ���� ����7.0ϵͳ
		String sdk = android.os.Build.VERSION.SDK;
		if (Integer.parseInt(sdk) <= 23) {
			noScrollgridview.setVisibility(View.VISIBLE);
		} else
			noScrollgridview.setVisibility(View.GONE);
	    }

	@Override
	protected void initListener() {
		submit.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
				Utils.showToast(PostingActivity.this, "�ύ���ݵ�������");
			}
		});

		cb.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if (cb.isChecked()) {
					cb.setTextColor(getResources().getColor(R.color.blue));

					sf.setText("����");
					sf.setTextColor(getResources().getColor(R.color.blue));

					ms.setText("��������!");
					ms.setTextColor(getResources().getColor(R.color.lightsteelblue));

				} else {
					cb.setTextColor(getResources().getColor(R.color.lightsteelblue));

					if (p != null) {
					/*
						sf.setText(" " + p.Identity);
						if (p.Identity.equals("ѧԱ"))
							sf.setTextColor(getResources().getColor(R.color.yellow));
						else
							sf.setTextColor(getResources().getColor(R.color.blue));
*/
						switch (p.PostingMsId) {
						case 0:
							ms.setText(p.PostingMsInfo);
							break;
						case 1:
							ms.setText(p.PostingMsInfo + "(��Ҫ���)");
							ms.setTextColor(getResources().getColor(R.color.blue));

							break;
						case 2:
							ms.setText(p.PostingMsInfo + "(���ɷ���)");
							ms.setTextColor(getResources().getColor(R.color.green));
							break;
						case 3:
							ms.setText(p.PostingMsInfo + "(��ֹ����)");
							ms.setTextColor(getResources().getColor(R.color.red));
							break;
						default:
							ms.setText(p.PostingMsInfo);
							break;
						}
					} else {
						sf.setText("����");
						sf.setTextColor(getResources().getColor(R.color.blue));

						ms.setText("��������!");
						ms.setTextColor(getResources().getColor(R.color.lightsteelblue));
					}
				}

			}
		});

		fanhui.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
				PostingActivity.this.finish();

			}
		});

		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (arg2 == Bimp.bmp.size()) {
					// Utils.showToast(PostingActivity.this, "�����Ƭ");
					// ����ѡ����Ƭ�����ݴ���
					if (p.PostingMsId != 3)//�ǽ�ֹ����ģʽ����ʾ���ͼƬ��POPO����
						showPopupWindows();
					else
						Utils.showToast(PostingActivity.this, "��ֹ����");
				} else {
					Utils.showToast(PostingActivity.this, "�鿴��Ƭ");
				}
			}
		});

	}

	/**
	 * ��ʾѡ�����������յĴ���
	 */
	public Dialog DRunHead = null;
	public View VRunHead = null;
	public LayoutInflater inflater;
	public String imgPath;
	private static final int CODE_GALLERY_CLAP = 0xa3;// ���յ�ѡ���ʶ

	public void showPopupWindows() {
		if (inflater == null)
			inflater = LayoutInflater.from(this);
		if (DRunHead == null)
			DRunHead = new Dialog(this, R.style.ActionSheetDialogStyle);
		if (VRunHead == null)
			VRunHead = inflater.inflate(R.layout.fragment_5_roundhead_dialog, null);
		DRunHead.setContentView(VRunHead);

		Window dialogWindow = DRunHead.getWindow();
		dialogWindow.setGravity(Gravity.BOTTOM);
		DRunHead.show();
		WindowManager.LayoutParams lp = DRunHead.getWindow().getAttributes();
		lp.width = MainApplication.phoneWidth;
		DRunHead.getWindow().setAttributes(lp);

		// ����ͷ��Text
		TextView tv = (TextView) VRunHead.findViewById(R.id.fragment_5_roundhead_dialog_head);
		tv.setText("���ͼƬ");
		// ȡ���ر�
		VRunHead.findViewById(R.id.fragment_5_roundhead_dialog_quxiao).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DRunHead.dismiss();
			}
		});
		// ��һ��
		VRunHead.findViewById(R.id.fragment_5_roundhead_dialog_paizhao).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String status = Environment.getExternalStorageState();
				if (status.equals(Environment.MEDIA_MOUNTED)) {
					File file = new File(Filepath.imgPath + "/" + System.currentTimeMillis() + ".jpg");
					if (!file.getParentFile().exists()) {
						file.getParentFile().mkdirs();
					}
					imgPath = file.getPath();

					Uri imageUri = Uri.fromFile(file);
					Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
					intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
					intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
					startActivityForResult(intent, CODE_GALLERY_CLAP);

				} else {
					Utils.showToast(PostingActivity.this, "û�д��濨!�޷�ʹ�ô˹���!");
				}
				DRunHead.dismiss();
			}
		});
		// �����ѡ��
		VRunHead.findViewById(R.id.fragment_5_roundhead_dialog_xiangce).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				
				
				
			}
		});

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case CODE_GALLERY_CLAP:
			if (Bimp.drr.size() < 9 && resultCode == -1) {

				Bimp.drr.add(imgPath);

				Log.v(TAG, "imgPath=" + imgPath);

			}
			break;
		}
	}

	Postingtity p;// P
	Handler Handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:// ���շ���Postingtity�ɹ���Ϣ
				p = (Postingtity) msg.obj;
				/*sf.setText(" " + p.Identity);
				if (p.Identity.equals("ѧԱ"))
					sf.setTextColor(getResources().getColor(R.color.yellow));
				else
					sf.setTextColor(getResources().getColor(R.color.blue));*/

				switch (p.PostingMsId) {
				case 0:
					ms.setText(p.PostingMsInfo);
					closePosting("����ģ��ά����..");
					// ���ò��ɱ༭ģʽ
					title.setFocusable(false);
					title.setFocusableInTouchMode(false);
					conten.setFocusable(false);
					conten.setFocusableInTouchMode(false);

					cb.setClickable(false);
					submit.setClickable(false);

					break;
				case 1:
					ms.setText(p.PostingMsInfo + "(��Ҫ���)");
					ms.setTextColor(getResources().getColor(R.color.blue));
					break;
				case 2:
					ms.setText(p.PostingMsInfo + "(���ɷ���)");
					ms.setTextColor(getResources().getColor(R.color.green));
					break;
				case 3:
					ms.setText(p.PostingMsInfo + "(��ֹ����)");
					ms.setTextColor(getResources().getColor(R.color.red));

					// ���ò��ɱ༭ģʽ
					title.setFocusable(false);
					title.setFocusableInTouchMode(false);
					conten.setFocusable(false);
					conten.setFocusableInTouchMode(false);

					cb.setClickable(false);
					submit.setClickable(false);

					// closePosting(" ��ֹ����");
					break;
				default:
					ms.setText("��ǰ����ģʽΪ:" + p.PostingMsInfo);
					break;
				}
				dialog.dismiss();
				break;
			case 2:// ���շ���Postingtityʧ����Ϣ
				dialog.dismiss();
				closePosting("����:" + msg.obj);
				break;

			case 1:

				adapter.notifyDataSetChanged();// ����GV������Դ
			
				Log.v(TAG, "Bimp.max="+Bimp.max);
				break;
			}

		}
	};

	/**
	 * ˢ��GV��ͼƬ��Դ
	 */
	public void loading1() {
		Log.v(TAG, "loading1()");
	new Thread(new Runnable() {
			public void run() {
				while (true) {
					if (Bimp.max == Bimp.drr.size()) {
						Handler.sendEmptyMessage(1);
						Log.v(TAG, "trur loading1() Bimp.max == Bimp.drr.size()==" + Bimp.max);
						break;
					} else {
						try {
							Log.v(TAG, "loading1() false");
							String path = Bimp.drr.get(Bimp.max);
							Log.v(TAG, "false loading1() path=" + path);
							Log.v(TAG, "false loading1() max=" + Bimp.max);
							Bitmap bm = Bimp.revitionImageSize(path);

							Bimp.bmp.add(bm);

							String newStr = System.currentTimeMillis() + "";
							

							FileUtils.saveBitmap(bm, "" + newStr);

						
							Handler.sendEmptyMessage(1);
							Bimp.max+=1;

						} catch (IOException e) {

							e.printStackTrace();
						}
					}

				}
			}
		}).start();
	}

	protected void onRestart() {
		Log.v(TAG, "onRestart() �������¼��� ˢ��GV");
		loading1();
		super.onRestart();

	}

	/**
	 * �رշ������� ������
	 */

	Builder bu1 = null;

	public void closePosting(String msg) {
		if (bu1 == null) {
			bu1 = new Builder(this);
			bu1.setTitle("��ʾ:");
			bu1.setCancelable(false);
		}
		bu1.setMessage(msg);
		bu1.setPositiveButton("�ر�", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				PostingActivity.this.finish();
			}
		});
		bu1.create();

		bu1.show();

	}

	// ���������¼�
	// ����
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			PostingActivity.this.finish();
			/*
			 * Bimp.bmp.clear(); Bimp.drr.clear(); Bimp.max = 0;
			 * Bimp.act_bool=true;
			 */
		}
		return super.onKeyDown(keyCode, event);
	}

}
