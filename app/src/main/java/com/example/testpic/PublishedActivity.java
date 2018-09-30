package com.example.testpic;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.xutils.http.RequestParams;

import com.example.internet.AccessInternet;
import com.example.internet.NetConn;
import com.example.json.entity.Postingtity;
import com.example.service.XsjxService;
import com.example.utils.EditTextClearTools;
import com.example.utils.Filepath;
import com.example.utils.NoDoubleClickListener;
import com.example.utils.SharedUtils;
import com.example.utils.UserInfo;
import com.example.utils.Utils;
import com.example.xsjx.LogoDoActivity;
import com.example.xsjx.MainApplication;
import com.example.xsjx.R;

import android.Manifest;
//import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class PublishedActivity extends Activity {
	private String TAG = "PublishedActivity";

	private GridView noScrollgridview;
	private GridAdapter adapter;

	private EditText title, conten;
	private TextView submit, sf, ms;
	private CheckBox cb;
	private ImageView fanhui;

	// 身份和ID
	int LogDoTypes = 0, LogDoTypesID = 0;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 限制此页面只能竖屏显示
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_posting);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}

		clerBimpSD();// 删除所有图片缓存文件和文件夹
		UserInfo.updateUserInfo();
		if (inflatera == null)
			inflatera = LayoutInflater.from(this);

		Init();
		initData();
		initListener();

		LogDoTypes = SharedUtils.getInstance().getInt("LogDoTypes");
		LogDoTypesID = SharedUtils.getInstance().getInt("LogDoTypesID");

		if (LogDoTypes > 0 && LogDoTypesID > 0) {
			if (LogDoTypes != 2 && UserInfo.postingbanned == 1) {

				closePosting("Sorry,由于您的言行,您已经被系统禁止发帖与评论!");
				title.setHint("您已经被禁言!");
				title.setEnabled(false);
				conten.setHint("您已经被禁言!");
				conten.setEnabled(false);
			}

			if (NetConn.isNetworkAvailable(this)) {
				AccessInternet.getPostingtity(Handler);
				showDialog("正在玩命加载中..");
			} else
				closePosting("  无法连接到服务器的发帖模块!请检查您的网络设置!");

		} else {
			closePosting("身份未知,无法发帖!");
		}
	}

	/**
	 * 显示加载框
	 */
	Dialog dialog;

	private void showDialog(String c) {
		dialog = new Builder(this).create();
		dialog.setCancelable(false);
		dialog.show();
		// 注意此处要放在show之后 否则会报异常
		View v = inflatera.inflate(R.layout.loading_process_dialog_anim, null);
		TextView stv = (TextView) v.findViewById(R.id.loading_dialog_tv);
		stv.setText(c);
		dialog.setContentView(v);
	}

	public void Init() {

		title = (EditText) findViewById(R.id.Posting_title);

		conten = (EditText) findViewById(R.id.Posting_conten);

		submit = (TextView) findViewById(R.id.Posting_submit);
		fanhui = (ImageView) findViewById(R.id.Posting_fanhui);
		cb = (CheckBox) findViewById(R.id.Posting_niming);
		sf = (TextView) findViewById(R.id.Posting_sf);
		ms = (TextView) findViewById(R.id.Posting_ms);

		noScrollgridview = (GridView) findViewById(R.id.Posting_noScrollgridview);
		noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));

		adapter = new GridAdapter(this);
		adapter.update1();
		noScrollgridview.setAdapter(adapter);
		noScrollgridview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if (arg2 == Bimp.bmp.size()) {
					if (p.PostingMsId != 3)// 非禁止发帖模式就显示添加图片的POPO窗口
						showPopupWindows();
					// new PopupWindows(PublishedActivity.this,
					// noScrollgridview);
				} else {
					Intent intent = new Intent(PublishedActivity.this, PhotoActivity.class);
					intent.putExtra("ID", arg2);
					startActivity(intent);
				}
			}
		});

	}

	/**
	 * 设置数据
	 */
	public void initData() {
		// 有数据就显示 发表为白色
		EditTextClearTools.addEditTextText(title, conten, submit);
		// 过滤掉表情等特殊字符
		title.setFilters(Utils.getInputFilter(this, 25));
		conten.setFilters(Utils.getInputFilter(this, 1024));
	}

	/**
	 * 设置事件
	 */
	public void initListener() {
		submit.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
				// 1.检查标题内容是否为空
				// 2.检查是否有图片添加
				// 3.弹出发表的窗口

				String titleStr = title.getText().toString().replace(" ", "");
				String contenStr = conten.getText().toString().replace(" ", "");

				if (titleStr != null && !titleStr.equals("") && contenStr != null && !contenStr.equals("")) {

					UploadPosting(titleStr, contenStr);

				} else {
					Utils.showToast(PublishedActivity.this, "标题或者内容不能为空且不能有空格!");
				}

				// Utils.showToast(PublishedActivity.this,
				// FileUtils.getImagePathFromSD().get(1).toString() + "");

			}
		});

		cb.setOnClickListener(new OnClickListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				/*if (LogDoTypes == 2) {
					Utils.showToast(PublishedActivity.this, "教练不可匿名发帖");
					cb.setEnabled(false);
				}

				else*/if (cb.isChecked()) {
					cb.setTextColor(getResources().getColor(R.color.blue));

					sf.setText("匿名");
					sf.setTextColor(getResources().getColor(R.color.blue));

					ms.setText("匿名发帖!");
					ms.setTextColor(getResources().getColor(R.color.lightsteelblue));

				} else {

					cb.setTextColor(getResources().getColor(R.color.lightsteelblue));

					if (p != null) {

						switch (LogDoTypes) {
						case 2:// 教练
							sf.setText("教练");
							sf.setTextColor(getResources().getColor(R.color.green));
							break;
						case 3:// 学员
							sf.setText("学员");
							sf.setTextColor(getResources().getColor(R.color.yellow));
							break;
						case 4:// 管理员
							sf.setText("管理员");
							sf.setTextColor(getResources().getColor(R.color.red));
							break;
						case 5:// 游客
							sf.setText("游客");
							sf.setTextColor(getResources().getColor(R.color.blue));
							break;
						case 6:// 已拿证
							sf.setText("已拿证");
							sf.setTextColor(getResources().getColor(R.color.red));
							break;
						default:
							closePosting("无法确定身份,禁止发贴!");
							break;
						}

						switch (p.PostingMsId) {
						case 0:
							ms.setText(p.PostingMsInfo);
							break;
						case 1:
							ms.setText(p.PostingMsInfo + "(需要审核)");
							ms.setTextColor(getResources().getColor(R.color.blue));

							break;
						case 2:
							ms.setText(p.PostingMsInfo + "(自由发帖)");
							ms.setTextColor(getResources().getColor(R.color.green));
							break;
						case 3:
							ms.setText(p.PostingMsInfo + "(禁止发帖)");
							ms.setTextColor(getResources().getColor(R.color.red));
							break;
						default:
							ms.setText(p.PostingMsInfo);
							break;
						}
					} else {
						sf.setText("匿名");
						sf.setTextColor(getResources().getColor(R.color.blue));

						ms.setText("匿名发帖!");
						ms.setTextColor(getResources().getColor(R.color.lightsteelblue));
					}
				}
			}

		});

		fanhui.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
				
				Atfinish();
				/*clerBimpSD();
				PublishedActivity.this.finish();*/
			}
		});

	}

	Postingtity p;// P
	Handler Handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:// 接收访问Postingtity成功信息
				p = (Postingtity) msg.obj;
				// 身份设置
				switch (LogDoTypes) {
				case 2:// 教练
					sf.setText("教练");
					sf.setTextColor(getResources().getColor(R.color.green));
					break;
				case 3:// 学员
					sf.setText("学员");
					sf.setTextColor(getResources().getColor(R.color.yellow));
					break;
				case 4:// 管理员
					sf.setText("管理员");
					sf.setTextColor(getResources().getColor(R.color.red));
					break;
				case 5:// 游客
					sf.setText("游客");
					sf.setTextColor(getResources().getColor(R.color.blue));
					break;
				case 6:// 已拿证
					sf.setText("已拿证");
					sf.setTextColor(getResources().getColor(R.color.red));
					break;
				default:
					closePosting("无法确定身份,禁止发贴!");
					break;
				}

				switch (p.PostingMsId) {
				case 0:
					ms.setText(p.PostingMsInfo);

					closePosting("发帖模块维护中..");
					// 设置不可编辑模式
					title.setFocusable(false);
					title.setFocusableInTouchMode(false);
					conten.setFocusable(false);
					conten.setFocusableInTouchMode(false);

					cb.setClickable(false);
					submit.setClickable(false);
					break;

				case 1:
					ms.setText(p.PostingMsInfo + "(需要审核)");
					ms.setTextColor(getResources().getColor(R.color.blue));
					break;
				case 2:
					ms.setText(p.PostingMsInfo + "(自由发帖)");
					ms.setTextColor(getResources().getColor(R.color.green));
					break;
				case 3:
					ms.setText(p.PostingMsInfo + "(禁止发帖)");
					ms.setTextColor(getResources().getColor(R.color.red));

					// 设置不可编辑模式
					title.setFocusable(false);
					title.setFocusableInTouchMode(false);
					conten.setFocusable(false);
					conten.setFocusableInTouchMode(false);

					cb.setClickable(false);
					submit.setClickable(false);

					// closePosting(" 禁止发帖");
					break;
				default:
					ms.setText("当前发帖模式为:" + p.PostingMsInfo);
					break;
				}
				dialog.dismiss();
				break;

			case 2:// 接收访问Postingtity失败信息
				dialog.dismiss();
				closePosting("错误:" + msg.obj);
				break;

			case 3:// 接收发贴成功信息
				dialog.dismiss();
				// 成功之后清空 标题和内容还有图片信息
				title.setText("");
				conten.setText("");
				clerBimpSD();
				adapter.loading1();// 清空图片数据

				if (cb.isChecked()) {
					Utils.showToast(PublishedActivity.this, "匿名发表成功!");
				} else if (p.PostingMsId == 1)
					Utils.showToast(PublishedActivity.this, "发表成功!审核通过后将显示您的发帖!");
				else
					Utils.showToast(PublishedActivity.this, "发表成功!请返回下拉刷新即可看到您的发帖!");

				break;

			case 4:// 接收发贴失败信息
				dialog.dismiss();
				Utils.showToast(PublishedActivity.this, msg.obj + "");
				break;
			}

		}
	};

	// @SuppressLint("HandlerLeak")
	public class GridAdapter extends BaseAdapter {
		private LayoutInflater inflater; // 打气筒
		private int selectedPosition = -1;// 选中位置
		private boolean shape;

		public boolean isShape() {
			return shape;
		}

		public void setShape(boolean shape) {
			this.shape = shape;
		}

		public GridAdapter(Context context) {
			inflater = LayoutInflater.from(context);
		}

		public void update1() {
			loading1();
		}

		public int getCount() {
			return (Bimp.bmp.size() + 1);
		}

		public Object getItem(int arg0) {

			return null;
		}

		public long getItemId(int arg0) {

			return 0;
		}

		public void setSelectedPosition(int position) {
			selectedPosition = position;
		}

		public int getSelectedPosition() {
			return selectedPosition;
		}

		/**
		 * ListView Item
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			// final int coord = position;
			ViewHolder holder = null;

			System.out.println("position=" + position);
			if (convertView == null) {

				convertView = inflater.inflate(R.layout.item_published_grida, parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView.findViewById(R.id.item_grida_image);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.image.setVisibility(View.VISIBLE);

			if (position == Bimp.bmp.size()) {
				if (Bimp.max != 9)
					holder.image.setImageBitmap(
							BitmapFactory.decodeResource(getResources(), R.drawable.icon_addpic_unfocused));
				// holder.image.setBackgroundResource(R.drawable.postingimg_item);
				else
					holder.image.setVisibility(View.GONE);

			} else {
				holder.image.setImageBitmap(Bimp.bmp.get(position));
			}

			return convertView;
		}

		public class ViewHolder {
			public ImageView image;
		}

		Handler handler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 1:
					adapter.notifyDataSetChanged();
					break;
				}
				super.handleMessage(msg);
			}
		};

		public void loading1() {
			new Thread(new Runnable() {
				public void run() {

					while (true) {
						if (Bimp.max == Bimp.drr.size()) {
							Message message = new Message();
							message.what = 1;
							handler.sendMessage(message);
							break;
						} else {
							try {
								String path = Bimp.drr.get(Bimp.max);
								System.out.println(path);
								Bitmap bm = Bimp.revitionImageSize(path);
								Bimp.bmp.add(bm);
								String newStr = path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
								FileUtils.saveBitmap(bm, "" + newStr);
								Bimp.max += 1;

								// Bimp.imageName.add(newStr+".jpeg");

								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							} catch (IOException e) {

								e.printStackTrace();
							}
						}
					}
				}
			}).start();
		}
	}

	/**
	 * At 重新加载  调用的方法 更新数据
	 */
	protected void onRestart() {
		adapter.update1();
		super.onRestart();
	}

	private static final int TAKE_PICTURE = 0x000000;
	private String path = "";

	public void onConfigurationChanged(Configuration config) {
		super.onConfigurationChanged(config);
	}

	public void photo() {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			File dir = new File(Filepath.imgPath + "/");
			if (!dir.exists())
				dir.mkdirs();

			Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			File file = new File(dir, String.valueOf(System.currentTimeMillis()) + ".jpg");
			path = file.getPath();
			Uri imageUri = Uri.fromFile(file);
			openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
			openCameraIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
			startActivityForResult(openCameraIntent, TAKE_PICTURE);
		} else {
			Toast.makeText(PublishedActivity.this, "没有存储卡,无法拍照!", Toast.LENGTH_LONG).show();
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case TAKE_PICTURE:
			if (Bimp.drr.size() < 9 && resultCode == -1) {
				Bimp.drr.add(path);
			}
			break;
		}
	}

	/**
	 * 显示选择相册或者拍照的窗口
	 */
	public Dialog DRunHead = null;
	public View VRunHead = null;
	public LayoutInflater inflatera = null;

	public void showPopupWindows() {

		if (DRunHead == null)
			DRunHead = new Dialog(this, R.style.ActionSheetDialogStyle);
		if (VRunHead == null)
			VRunHead = inflatera.inflate(R.layout.fragment_5_roundhead_dialog, null);
		DRunHead.setContentView(VRunHead);

		Window dialogWindow = DRunHead.getWindow();
		dialogWindow.setGravity(Gravity.BOTTOM);
		DRunHead.show();
		WindowManager.LayoutParams lp = DRunHead.getWindow().getAttributes();
		lp.width = MainApplication.phoneWidth;
		DRunHead.getWindow().setAttributes(lp);

		// 设置头部Text
		TextView tv = (TextView) VRunHead.findViewById(R.id.fragment_5_roundhead_dialog_head);
		tv.setText("添加图片");
		// 取消关闭
		VRunHead.findViewById(R.id.fragment_5_roundhead_dialog_quxiao).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DRunHead.dismiss();
			}
		});
		// 拍一张
		VRunHead.findViewById(R.id.fragment_5_roundhead_dialog_paizhao).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				photo();
				DRunHead.dismiss();
			}
		});
		// 从相册选择
		VRunHead.findViewById(R.id.fragment_5_roundhead_dialog_xiangce).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				// 检查是否有文件读取权限
				if (Build.VERSION.SDK_INT >= 23)
					CheckStoeragePermission();
				else {
					Intent intent = new Intent(PublishedActivity.this, TestPicActivity.class);
					startActivity(intent);
				}

				DRunHead.dismiss();
			}
		});

	}

	/**
	 * 检查文件读写权限
	 */
	int CODE_FOR_WRITE_PERMISSION = 0; // 文件读写权限标识

	public void CheckStoeragePermission() {
		if (ActivityCompat.checkSelfPermission(PublishedActivity.this,
				Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
			// 已经有权限
			Intent intent = new Intent(PublishedActivity.this, TestPicActivity.class);
			startActivity(intent);
		} else {
			// 如果没有 就询问用户
			ActivityCompat.requestPermissions(PublishedActivity.this,
					new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, CODE_FOR_WRITE_PERMISSION);
		}

	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
			@NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == CODE_FOR_WRITE_PERMISSION) {
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				Intent intent = new Intent(PublishedActivity.this, TestPicActivity.class);
				startActivity(intent);
			} else {
				// 用户拒绝了权限申请

				// 是否已经拒绝过此权限
				if (!ActivityCompat.shouldShowRequestPermissionRationale(PublishedActivity.this,
						Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
					Utils.showToast(PublishedActivity.this, "您已拒绝过权限授权,无法启动此功能!\n请您到应用权限管理手动开启(读取手机媒体存储权限)!");
				} else {
					Utils.showToast(PublishedActivity.this, "您拒绝了权限授权,此功能将无法启动!");
				}
			}

		}
	}

	/**
	 * 关闭发帖界面 弹出框
	 */
	private Dialog Dcoach = null;
	private View Vcoach = null;

	public void closePosting(String c) {

		if (Dcoach == null) {
			Dcoach = new Dialog(this, R.style.testDialog);
			Dcoach.setCancelable(false);

		}
		if (Vcoach == null)
			Vcoach = inflatera.inflate(R.layout.posting_close_dialog, null);

		Dcoach.setContentView(Vcoach);
		Dcoach.show();

		WindowManager.LayoutParams lp = Dcoach.getWindow().getAttributes();
		lp.width = MainApplication.phoneWidth;

		Dcoach.getWindow().setAttributes(lp);

		TextView t = (TextView) Vcoach.findViewById(R.id.posting_close_dialog_conten);
		t.setText(c);

		Vcoach.findViewById(R.id.posting_close_dialog_closeButton).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				PublishedActivity.this.finish();
				Bimp.bmp.clear();
				Bimp.drr.clear();
				Bimp.max = 0;
				Bimp.act_bool = true;

			}
		});

	}

	// 关闭此界面并且删除所有缓存文件
	public void clerBimpSD() {

		Bimp.bmp.clear();
		Bimp.drr.clear();
		Bimp.max = 0;
		Bimp.act_bool = true;
		FileUtils.deleteDir();// 删除缓存的图片压缩文件

	}

	/**
	 * 发布
	 */

	public void UploadPosting(String t, String c) {

		// String identity;
		/*
		 * if (cb.isChecked()) { identity = "1"; } else if
		 * (p.Identity.equals("学员")) { identity = "3"; } else identity = "5";
		 
		Utils.showToast(this,Bimp.max+":LogDoTypes="+LogDoTypes+":LogDoTypesID="+LogDoTypesID);
          */
		// 无图发帖
		if (Bimp.max == 0) {
			showDialog("发表中,请耐心等待!");
			if(!cb.isChecked())
	AccessInternet.UpLoadPosting(Handler, t, c, LogDoTypes + "", LogDoTypesID + "", p.PostingMsId + "", null);
	
			else AccessInternet.UpLoadPosting(Handler, t, c, "1", LogDoTypesID + "", p.PostingMsId + "", null);
		
		}
//后台的post请求提交图片String 数据需要设置Tomcat的大小
		// 有图发帖
		else {

			List<String> imgList = new ArrayList<String>();
			try {
				
				for (int i = 0; i < Bimp.bmp.size(); i++) {
					// 压缩图片
					ByteArrayOutputStream stream=new ByteArrayOutputStream();
					Bimp.bmp.get(i).compress(Bitmap.CompressFormat.JPEG, 90, stream);// 100代表不压缩
					byte[] byte_arr = stream.toByteArray();
					// Base64图片转码为String
					String image = Base64.encodeToString(byte_arr, 0);
					imgList.add(image);
					stream.close();
				 }
				
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			showDialog("发表中,请耐心等待!");
			
			if(!cb.isChecked())
			AccessInternet.UpLoadPosting(Handler, t, c, LogDoTypes + "", LogDoTypesID + "", p.PostingMsId + "",
					imgList);
			else AccessInternet.UpLoadPosting(Handler, t, c,"1", LogDoTypesID + "", p.PostingMsId + "",
					imgList);

		}

	}
	/**
	 * 弹出是否退出此页面  并且不保存信息
	 */
	Builder bu1=null; 
	public void Atfinish(){
		if(bu1==null){
			bu1= new Builder(this);
			bu1.setTitle("提示:");
			bu1.setMessage("是否退出?退出将不会保存您输入的信息!");
			bu1.setNegativeButton("取消",new DialogInterface.OnClickListener() {	
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
									
				}
			});
		
	        bu1.setPositiveButton("确定",new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface arg0, int arg1) {	
					clerBimpSD();
					PublishedActivity.this.finish();
				}
			});
			bu1.create();
		}
		bu1.show();
		
	}	

	// 监听返回事件
	// 返回
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
			Atfinish();
			
			
			/*clerBimpSD();
			PublishedActivity.this.finish();*/
		}
		return super.onKeyDown(keyCode, event);
	}

}
