package com.example.fragment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import org.xutils.x;

import com.example.base.BaseFragment;
import com.example.dao.SqliteDo;
import com.example.dao.UserInfoDo;
import com.example.internet.AccessInternet;
import com.example.internet.NetConn;
import com.example.service.XsjxService;
import com.example.utils.CircleImageView;
import com.example.utils.Filepath;
import com.example.utils.SharedUtils;
import com.example.utils.UserInfo;
import com.example.utils.Utils;
import com.example.xsjx.CoachInfoActivity;
import com.example.xsjx.EnrollActivity;
import com.example.xsjx.LogoDoActivity;
import com.example.xsjx.MainActivity;
import com.example.xsjx.MainApplication;
import com.example.xsjx.R;
import com.example.xsjx.RunActivity;
import com.example.xsjx.StartActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("unused")
public class FragMent_5 extends Fragment {
	private static String TAG = "FragMent_5";
	public RelativeLayout Info, Coach, Keshi, Yuyue, Dingdan, APP, Finsh;

	public LayoutInflater inflater = LayoutInflater.from(MainApplication.baseContext);
	public ImageView CiecleImg;
	public TextView tv_username,tv_idtity;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View content = inflater.inflate(R.layout.fragment_5, container, false);
		Log.v("Fragment", "fragment5����");
		initView(content);
		return content;
	}

	public void initView(View view) {
		CiecleImg = (ImageView) view.findViewById(R.id.fragment5_CiecleImg);
		
		
		// ��������ͷ�� 2017 12 13 ��ʱ�Ȳ���ȡ����ͷ��	
		CiecleImg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showdHeadImgPopo();
			}
		});
	
		tv_idtity=(TextView) view.findViewById(R.id.fragment5_idtity);
		tv_username = (TextView) view.findViewById(R.id.fragment5_userName);
		
		tv_username.setText(SharedUtils.getInstance().getString("username"));
	
		
		//getUrlHeadimg();
		
		// ������Ϣ
		Info = (RelativeLayout) view.findViewById(R.id.Fragement5_Info);
		Info.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Toast.makeText(getActivity(), "����˻�����Ϣ", 1).show();
				
				//showInfoWindows();

			}
		});
		// �ҵĽ���
		Coach = (RelativeLayout) view.findViewById(R.id.Fragement5_Coach);
		Coach.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Toast.makeText(getActivity(), "������ҵĽ���", 1).show();
				/* showCoachWindows();

				UserInfo.UserCoachID = new Random().nextInt(6);

				if (UserInfo.UserCoachID != 0) {
					Intent Intent = new Intent(getActivity(), CoachInfoActivity.class);
					startActivity(Intent);
				} else {
					showCoachWindows();
				}
            */
				
			}
		});
		// �ҵĿ�ʱ
		Keshi = (RelativeLayout) view.findViewById(R.id.Fragement5_Mykeshi);
		Keshi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Toast.makeText(getActivity(), "������ҵĿ�ʱ", 1).show();
				//showKeshiWindows();
			}
		});
		// �ҵ�ԤԼ
		Yuyue = (RelativeLayout) view.findViewById(R.id.Fragement5_yu);
		Yuyue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Toast.makeText(getActivity(), "������ҵ�ԤԼ", 1).show();
				//showYuyueWindows();
			}
		});

		// �ҵĶ���
		Dingdan = (RelativeLayout) view.findViewById(R.id.Fragement5_ding);
		Dingdan.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Toast.makeText(getActivity(), "������ҵĶ���", 1).show();
				//showDingdanWindows();
			}
		});
		
		
		// ����APP
		APP = (RelativeLayout) view.findViewById(R.id.Fragement5_app);
		APP.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Toast.makeText(getActivity(), "������ҵĽ���", 1).show();
				showAppWindows();
			}
		});

		// ע����½
		Finsh = (RelativeLayout) view.findViewById(R.id.Fragement5_zhuxiao);
		Finsh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				showFinshDialog();
			}
		});
	}
	/**
	 * Main����� �����û�ͷ��
	 */

	public void LoadliongUserHeadimg(){
		x.image().bind(CiecleImg, UserInfo.UserHeadPortrait, Utils.Xheadimg());
		
		switch (UserInfo.IdentityID) {
		case 2:// ����
			tv_idtity.setText("����");
			tv_idtity.setTextColor(getResources().getColor(R.color.green));
			break;
		case 3:// ѧԱ
			tv_idtity.setText("ѧԱ");
			tv_idtity.setTextColor(ContextCompat.getColor(getActivity(), R.color.yellow));
			break;
		case 4:// ����Ա
			tv_idtity.setText("����Ա");
			tv_idtity.setTextColor(getResources().getColor(R.color.red));
			break;
		case 5:// �ο�
			tv_idtity.setText("�ο�");
			tv_idtity.setTextColor(getResources().getColor(R.color.blue));
			break;
		case 6:// ����֤
			tv_idtity.setText("����֤");
			tv_idtity.setTextColor(getResources().getColor(R.color.red));
			break;
		default:
			tv_idtity.setText("�ο�");
			tv_idtity.setTextColor(getResources().getColor(R.color.blue));
			break;
		}
		
		
		
	}
	
	/** ----���ʷ������ϵ��û���Ϣͷ��----- */
public void getUrlHeadimg() {
	/*
	new Handler().postDelayed(new Runnable() {
		@Override
		public void run() {
			
			
			/*
			if(UserInfo.UserSex.equals("Ů")){
				Drawable rightDrawable = getResources().getDrawable(R.drawable.my_famale);
				rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(),
						rightDrawable.getMinimumHeight());
				tv_username.setCompoundDrawables(null, null, rightDrawable, null);
			 }
			
		}
	},3000);
	
	
 new Handler().postDelayed(new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				Log.v(TAG, "getUrlHeadimg()-run()���� ");
				String imgname = SharedUtils.getInstance().getString("UserHeadImg");
				// �ж���Ϣ����û�� �û���Ϣ���¾͸ı�
if (UserInfo.UserHeadPortrait != null && UserInfo.UserName != null && UserInfo.UserSex != null) {

					String urlimgname = Utils.isUrlFileName(UserInfo.UserHeadPortrait);

					
					tv_username.setText(UserInfo.UserName);
					Log.v(TAG, "getUrlHeadimg()-run()���� " + UserInfo.UserSex);
					if (UserInfo.UserSex.equals("Ů")) {
						Drawable rightDrawable = getResources().getDrawable(R.drawable.my_famale);
						rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(),
								rightDrawable.getMinimumHeight());
						tv_username.setCompoundDrawables(null, null, rightDrawable, null);
					}
					

					Log.v(TAG, "getUrlHeadimg()-run()== imgname=" + imgname + "==urlimgname=" + urlimgname);

					Bitmap bitmap1 = BitmapFactory.decodeFile(Filepath.imgPath + "/" + urlimgname);
					if (bitmap1 != null) {
						CiecleImg.setImageBitmap(bitmap1);
					} else

					if (imgname.equals(urlimgname) && urlimgname != null && !urlimgname.equals("") && imgname != null
							&& !imgname.equals("")) {
						Bitmap bitmap = BitmapFactory.decodeFile(Filepath.imgPath + "/" + imgname);
						if (bitmap != null)
							CiecleImg.setImageBitmap(bitmap);
						else {
							CiecleImg.setImageResource(R.drawable.log);
							Utils.ImgDoload(UserInfo.UserHeadPortrait, Filepath.imgPath + "/" + urlimgname);
						}

					} else {
						Utils.ImgDoload(UserInfo.UserHeadPortrait, Filepath.imgPath + "/" + urlimgname);
						SharedUtils.getInstance().putString("UserHeadImg", urlimgname);
					}

				}

			}
		}, 3000);
*/
	}

	/** ------------------------����ͷ��------------------- */

	/**
	 * ����������Ƭ�ķ���
	 */
	
	private static final int CODE_GALLERY_REQUEST = 0xa0;// �����ѡ���ʶ
	private static final int CODE_RESULT_REQUEST = 0xa2;// ���ղü���Ľ�� �����ѡ���ʶ
	
	private static final int CODE_GALLERY_CLAP = 0xa3;// ���յ�ѡ���ʶ
	private static final int CODE_RESULT_CLAP  = 0xa4;// ���ղü���Ľ�� �����յı�ʶ
	
	private static int output_X = 100;
	private static int output_Y = 100;

	private Uri paizhaoName=null; //�������պ����ƬUri ֵ
	
	public Dialog DRunHead = null;
	public View VRunHead = null;
	public void showdHeadImgPopo() {
		if (DRunHead == null)
			DRunHead = new Dialog(getActivity(), R.style.ActionSheetDialogStyle);
		if (VRunHead == null)
			VRunHead = inflater.inflate(R.layout.fragment_5_roundhead_dialog, null);
		DRunHead.setContentView(VRunHead);
		Window dialogWindow = DRunHead.getWindow();
		dialogWindow.setGravity(Gravity.BOTTOM);
		DRunHead.show();
		WindowManager.LayoutParams lp = DRunHead.getWindow().getAttributes();
		lp.width = MainApplication.phoneWidth;
		DRunHead.getWindow().setAttributes(lp);

		VRunHead.findViewById(R.id.fragment_5_roundhead_dialog_quxiao).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				DRunHead.dismiss();
			}
		});
		VRunHead.findViewById(R.id.fragment_5_roundhead_dialog_paizhao).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				/**
				 * ��һ��
				 */	
			
				File file = new File(Filepath.imgPath + "/" +System.currentTimeMillis()+ ".jpg");
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
	            paizhaoName = Uri.fromFile(file);	
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, paizhaoName);
				startActivityForResult(intent,CODE_GALLERY_CLAP);
				
				// System.currentTimeMillis()
				/*File file = new File(Filepath.imgPath + "/" + Utils.isMD5Rand(Utils.getDate()) + ".jpg");
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				Uri imageUri = Uri.fromFile(file);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				
				startActivityForResult(intent, CODE_GALLERY_CLAP);*/

				DRunHead.dismiss();
			}
		});
		VRunHead.findViewById(R.id.fragment_5_roundhead_dialog_xiangce).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {

				/**
				 * �����ѡ�� Intent intent = new Intent(Intent.ACTION_PICK);
				 * intent.setType("image/*"); startActivityForResult(intent, 1);
				 */
				Intent intentFromGallery = new Intent(Intent.ACTION_PICK, null);
				intentFromGallery.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);

			}
		});

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		switch (requestCode) {
		case CODE_GALLERY_REQUEST:// ��������Ա��ص����
			if (data != null) {
				
				cropRawPhoto(data.getData());// ֱ�Ӳü�ͼƬ
			}
			break;
		case CODE_RESULT_REQUEST:
			
			if (data != null) {
				setHeadImg(data);// ����ͼƬ��
				Log.e("data", "data!=null");
			}
			
			
			break;
	 //�������������
		case CODE_GALLERY_CLAP:
			if(paizhaoName!=null)
			 cropRawclap(paizhaoName);
			
			break;
		case CODE_RESULT_CLAP:
			if (data != null) {
				setHeadImg(data);// ����ͼƬ��
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	
	//�����������յ�ͼƬ����
		public void cropRawclap(Uri uri) {
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(uri, "image/*");
			intent.putExtra("crop", "true");
			// aspectX aspectY �ǿ�ߵı���
			intent.putExtra("aspectX", 1);
			intent.putExtra("aspectY", 1);
			// outputX outputY �ǲü�ͼƬ���
			intent.putExtra("outputX", output_X);
			intent.putExtra("outputY", output_Y);
			intent.putExtra("return-data", true);
			startActivityForResult(intent, CODE_RESULT_CLAP);
		}
	
	//�����������ѡ���ͼƬ����
	public void cropRawPhoto(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", output_X);
		intent.putExtra("outputY", output_Y);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, CODE_RESULT_REQUEST);
	}

	/**
	 * ��ȡ����ü�֮���ͼƬ���ݣ�������ͷ�񲿷ֵ�View
	 */
	private void setHeadImg(Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			CiecleImg.setImageBitmap(photo);

			try {
				
				ByteArrayOutputStream stream=new ByteArrayOutputStream();
				photo.compress(Bitmap.CompressFormat.JPEG, 100, stream);// 100����ѹ��
				byte[] byte_arr = stream.toByteArray();
				// Base64ͼƬת��ΪString
				String image = Base64.encodeToString(byte_arr, 0);
				stream.close();
      // Log.e("head image", image);
				if (NetConn.isNetworkAvailable(getActivity()))
					{
					 String userimgname=System.currentTimeMillis()+"";
					 AccessInternet.uploadUserimg(image,userimgname, UserInfo.UserID);
					 Utils.showToast(getActivity(), "ͷ���޸ĳɹ�!");
				
					// x.image().bind(CiecleImg, MainApplication.csXSJX+"HeadPortraitImg/"+userimgname+".jpeg", Utils.Xheadimg());
					 
					 /**
					  * ����ͼƬ������ ����X��ܼ���
					  */
					
						File f = new File(Filepath.imgPath + "/" + userimgname + ".jpeg");

						String Webpath = f.getPath();
						
						Intent intent1 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
						Uri uri = Uri.fromFile(f);
						intent1.setData(uri);
						getActivity().sendBroadcast(intent1);

						FileOutputStream out = null;
						try {
							// ������� ��ͼƬ���������ļ���
							out = new FileOutputStream(f);
							photo.compress(Bitmap.CompressFormat.PNG, 90, out);
							try {
								out.flush();
								out.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						 x.image().bind(CiecleImg,Webpath, Utils.Xheadimg());
					 
					}
				else {
					Utils.showToast(getActivity(), "����������!ͼƬ�ϴ�������ʧ��!");
				}
				
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		else {
			Utils.showToast(getActivity(), "�ϴ�ʧ��,����������~");
		}
		DRunHead.dismiss();
		
		
		
		/*
		if (extras != null) {
			Log.v("setHeadImg", "extras!=null");
			Bitmap photo = extras.getParcelable("data");
			CiecleImg.setImageBitmap(photo);

			
			
			String imgname = Utils.isMD5Rand(Utils.getDate());
			File f = new File(Filepath.imgPath + "/" + imgname + ".jpg");

			String Webpath = f.getPath();
			Log.v("Fragmen5 setHeadImg", Webpath);// ��Ҫ�ϴ���������ͼƬ·��

			Intent intent1 = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			Uri uri = Uri.fromFile(f);
			intent1.setData(uri);
			getActivity().sendBroadcast(intent1);

			FileOutputStream out = null;
			try {
				// ������� ��ͼƬ���������ļ���
				out = new FileOutputStream(f);
				photo.compress(Bitmap.CompressFormat.PNG, 90, out);
				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			Log.v("Fragmen5 setHeadImg", "ͼƬ�Ѿ��浽������");

			//SharedUtils.getInstance().putString("UserHeadImg", imgname);
			DRunHead.dismiss();

			// --ͼƬ���ܲ����ϴ����������ļ���
			BitmapFactory.Options options = null;
			options = new BitmapFactory.Options();
			options.inSampleSize = 1;
		
			Bitmap bitmap = BitmapFactory.decodeFile(Filepath.imgPath + "/" + imgname + ".jpg", options);
			
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			// ѹ��ͼƬ
			if(bitmap!=null){
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);//100����ѹ��
			
			byte[] byte_arr = stream.toByteArray();
			// Base64ͼƬת��ΪString
			String image = Base64.encodeToString(byte_arr, 0);
	
		
			if (NetConn.isNetworkAvailable(getActivity()))
				AccessInternet.uploadUserimg(image, imgname, UserInfo.UserID);
			else {
				Utils.showToast(getActivity(), "����������!ͼƬ�ϴ�������ʧ��!");
			}
			// ----
			     }
			else {Utils.showToast(getActivity(), "����ʧ��,����������~2");
			    
			    }
		}
		else   {Log.v("setHeadImg", "extras==null");
		Utils.showToast(getActivity(), "����ʧ��,����������~3");
		}
		*/
		
	}

	/** ------------------------����ͷ��END------------------- */

	
	
	/**
	 * ��ʾ������Ϣ�Ĵ���
	 */

	public Dialog Dinfo = null;
	public View Vinfo = null;

	public void showInfoWindows() {
		if (Dinfo == null)
			Dinfo = new Dialog(getActivity(), R.style.testDialog);
		if (Vinfo == null)
			Vinfo = inflater.inflate(R.layout.fragment_5_info_layout, null);

		Dinfo.setContentView(Vinfo);
		Dinfo.show();

		WindowManager.LayoutParams lp = Dinfo.getWindow().getAttributes();
		lp.width = MainApplication.phoneWidth;
		lp.height = MainApplication.phoneHeight;

		Dinfo.getWindow().setAttributes(lp);
	}

	/**
	 * ��ʾ�ҵĽ����Ĵ���
	 */

	public Dialog Dcoach = null;
	public View Vcoach = null;

	public void showCoachWindows() {
		if (Dcoach == null)
			Dcoach = new Dialog(getActivity(), R.style.testDialog);
		if (Vcoach == null)
			Vcoach = inflater.inflate(R.layout.fragment_5_coach_layout, null);

		Dcoach.setContentView(Vcoach);
		Dcoach.show();

		WindowManager.LayoutParams lp = Dcoach.getWindow().getAttributes();
		lp.width = MainApplication.phoneWidth;
		lp.height = MainApplication.phoneHeight;

		Dcoach.getWindow().setAttributes(lp);
	}

	/**
	 * ��ʾ�ҵĿ�ʱ�Ĵ���
	 */

	public Dialog Dkeshi = null;
	public View Vkeshi = null;

	public void showKeshiWindows() {
		if (Dkeshi == null)
			Dkeshi = new Dialog(getActivity(), R.style.testDialog);
		if (Vkeshi == null)
			Vkeshi = inflater.inflate(R.layout.fragment_5_keshi_layout, null);

		Dkeshi.setContentView(Vkeshi);
		Dkeshi.show();

		WindowManager.LayoutParams lp = Dkeshi.getWindow().getAttributes();
		lp.width = MainApplication.phoneWidth;
		lp.height = MainApplication.phoneHeight;

		Dkeshi.getWindow().setAttributes(lp);
	}

	/**
	 * ��ʾ�ҵ�ԤԼ�Ĵ���
	 */

	public Dialog Dyuyue = null;
	public View Vyuyue = null;

	public void showYuyueWindows() {
		if (Dyuyue == null)
			Dyuyue = new Dialog(getActivity(), R.style.testDialog);
		if (Vyuyue == null)
			Vyuyue = inflater.inflate(R.layout.fragment_5_keshi_layout, null);

		Dyuyue.setContentView(Vyuyue);
		Dyuyue.show();

		WindowManager.LayoutParams lp = Dyuyue.getWindow().getAttributes();
		lp.width = MainApplication.phoneWidth;
		lp.height = MainApplication.phoneHeight;

		Dyuyue.getWindow().setAttributes(lp);
	}

	/**
	 * ��ʾ�ҵĶ����Ĵ���
	 */

	public Dialog Ddingdan = null;
	public View Vdingdan = null;

	public void showDingdanWindows() {
		if (Ddingdan == null)
			Ddingdan = new Dialog(getActivity(), R.style.testDialog);
		if (Vdingdan == null)
			Vdingdan = inflater.inflate(R.layout.fragment_5_dingdan_layout, null);

		Ddingdan.setContentView(Vdingdan);
		Ddingdan.show();

		WindowManager.LayoutParams lp = Ddingdan.getWindow().getAttributes();
		lp.width = MainApplication.phoneWidth;
		lp.height = MainApplication.phoneHeight;

		Ddingdan.getWindow().setAttributes(lp);
	}

	/**
	 * ��ʾ����APP�Ĵ���
	 */

	public Dialog Dapp = null;
	public View Vapp = null;

	public void showAppWindows() {
		if (Dapp == null)
			Dapp = new Dialog(getActivity(), R.style.testDialog);
		if (Vapp == null)
			Vapp = inflater.inflate(R.layout.fragment_5_app_layout, null);

		Dapp.setContentView(Vapp);
		Dapp.show();

		WindowManager.LayoutParams lp = Dapp.getWindow().getAttributes();
		lp.width = MainApplication.phoneWidth;
		lp.height = MainApplication.phoneHeight;

		Dapp.getWindow().setAttributes(lp);
	}

	/**
	 * ��ʾ�Ƿ�ע����½�ĶԻ���
	 * 
	 */
	Builder bu1 = null;

	public void showFinshDialog() {
		if (bu1 == null) {
			bu1 = new Builder(getActivity());
			bu1.setTitle("��ʾ:");
			bu1.setMessage("\n�Ƿ�ע����½?\n");
			bu1.setNegativeButton("ȡ��", null);
			bu1.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					
					SharedUtils.getInstance().putInt("LogDoTypes",0);
					SharedUtils.getInstance().putInt("LogDoTypesID",0);
					
					
					
					SharedUtils.getInstance().putString("username", "");
					SharedUtils.getInstance().putString("userpwd", "");

					UserInfo.claerUserInfo();
					
					Intent Intent = new Intent(getActivity(), LogoDoActivity.class);
					startActivity(Intent);
					getActivity().finish();
				}
			});
			bu1.create();
		}
		bu1.show();
	}

	

}
