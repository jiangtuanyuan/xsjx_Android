package com.example.internet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Coach.Utils.CoachInfo;
import com.example.Coach.Utils.DaySeasonDate;
import com.example.Coach.entity.Ke2order;
import com.example.Coach.entity.Ke3order;
import com.example.Coach.entity.Stuinfo;
import com.example.Coach.entity.Timeorder;
import com.example.Coach.entity.daythree;
import com.example.Coach.entity.makedate;
import com.example.Coach.entity.season;
import com.example.entity.Video;
import com.example.json.entity.JzInfoEntity;
import com.example.json.entity.JzTelClass;
import com.example.json.entity.PComments;
import com.example.json.entity.Posting;
import com.example.json.entity.Postingtity;
import com.example.json.entity.Run_img_json_entity;
import com.example.json.entity.SchoolNewsEntity;
import com.example.json.entity.SchoolTopImgEntity;
import com.example.json.entity.UpdateApkEntity;
import com.example.json.entity.UserJzentity;
import com.example.json.entity.tel;
import com.example.utils.LocationUtils;
import com.example.utils.SharedUtils;
import com.example.utils.UserInfo;

import com.example.xsjx.MainApplication;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import okio.*;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

public class AccessInternet {

	private static final String TAG = "AccessInternet";

	/**
	 * ���ʷ������ϵ�APP���ؼ�¼�ӿ� ������ݵ����������ֻ�Ʒ�� �ֻ��ͺ� ��γ�ȣ�
	 *
	 */

	public static void APPDownloadInfoServlet() {
		Log.v(TAG, TAG + "-APPDownloadInfoServlet()");

		/*
		 * String mtype = android.os.Build.MODEL; // �ֻ��ͺ� String mtyb =
		 * android.os.Build.BRAND;// �ֻ�Ʒ��
		 */
		String phonelogo = android.os.Build.BRAND;
		String phonetype = android.os.Build.MODEL;

		// android.os.Build.VERSION.SDK SDK�汾 23
		// android.os.Build.VERSION.RELEASE ϵͳ�汾 6.0.1
		String sdk = android.os.Build.VERSION.SDK + "";
		String windows = android.os.Build.VERSION.RELEASE;

		Location location = null;
		if (ActivityCompat.checkSelfPermission(MainApplication.baseContext,
				android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
			location = LocationUtils.getInstance(MainApplication.baseContext).showLocation();
		}

		String jingdu, weidu;
		if (location != null) {
			jingdu = location.getLatitude() + "";
			weidu = location.getLongitude() + "";
		} else {
			jingdu = "0";
			weidu = "0";
		}

		OkHttpClient mOkHttpClient = new OkHttpClient();

		mOkHttpClient.setConnectTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setWriteTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setReadTimeout(3500, TimeUnit.SECONDS);

		FormEncodingBuilder builder = new FormEncodingBuilder();

		builder.add("phonelogo", phonelogo);// ��ȡ�ֻ���Ϣ�;�γ��
		builder.add("phonetype", phonetype);
		builder.add("jingdu", jingdu);
		builder.add("weidu", weidu);
		builder.add("sdk", sdk);
		builder.add("windows", windows);

		Request request = new Request.Builder().url(MainApplication.csXSJX + "APPDownloadInfoServlet")
				.post(builder.build()).build();
		mOkHttpClient.newCall(request).enqueue(new Callback() {

			@Override
			public void onResponse(Response arg0) throws IOException {

				Log.v(TAG, "APPDownloadInfoServlet()" + "��ӳɹ�");

				LocationUtils.getInstance(MainApplication.baseContext).removeLocationUpdatesListener();
				Log.v(TAG, "APPDownloadInfoServlet()--location" + "�رն�̬��λ!");
			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {

				Log.v(TAG, "APPDownloadInfoServlet()" + "���ʧ��");
				LocationUtils.getInstance(MainApplication.baseContext).removeLocationUpdatesListener();
				Log.v(TAG, "APPDownloadInfoServlet()--location" + "�رն�̬��λ!");

			}
		});

	}

	/**
	 * 2017 11 19 21:36
	 * 
	 * �°汾���µĽӿڷ���
	 * 
	 */
	public static void UpdateAPKServlet(final Handler handler) {
		Log.v(TAG, "UpdateAPKServlet() ����!");
		OkHttpClient mOkHttpClient = new OkHttpClient();

		mOkHttpClient.setConnectTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setWriteTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setReadTimeout(3500, TimeUnit.SECONDS);

		Request request = new Request.Builder().url(MainApplication.XSJX_UPDATE_APK).build();
		Call call = mOkHttpClient.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onResponse(Response arg0) throws IOException {

				try {
					JSONObject JSON = new JSONObject(arg0.body().string());

					// Log.v(TAG, "UpdateAPKServlet() JSON=" + JSON.toString());

					UpdateApkEntity apkEntity = new UpdateApkEntity();
					if (JSON.optInt("Error_code") == 0) {

						apkEntity.Error_code = JSON.optInt("Error_code");
						apkEntity.appname = JSON.optString("appname");
						apkEntity.apkname = JSON.optString("apkname");
						apkEntity.verName = JSON.optString("verName");
						apkEntity.verCode = JSON.optString("verCode");
						apkEntity.apkurl = JSON.optString("apkurl");
						apkEntity.updateinfo = JSON.optString("updateinfo");

						Message msg = new Message();
						msg.what = 0;
						msg.obj = apkEntity;
						handler.sendMessage(msg);
					} else {
						apkEntity.Error_code = JSON.optInt("Error_code");

						Message msg = new Message();
						msg.what = 1;
						msg.obj = "Error_code=1";
						handler.sendMessage(msg);
					}

				} catch (JSONException e) {

					Message msg = new Message();
					msg.what = 1;
					msg.obj = "json=" + e.toString();
					handler.sendMessage(msg);

					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {

				Message msg = new Message();
				msg.what = 1;
				msg.obj = arg1.toString() + "" + arg0;// ������Ի�ȡ�ܶ�����
				handler.sendMessage(msg);

			}
		});

	}

	/**
	 * ����Բ 2017.10.21 18:00 ��ȡ����ͼƬjson���� return Run_img_json_entity
	 * 
	 * ���磺{"Error_code":0,"Url":"http://www.jtyrl.cn/XSJX/Run_Images/","ImgName"
	 * :"Run.jpg"}
	 */

	public static Run_img_json_entity XSJXRunServlet(final Handler handler) {

		Log.v(TAG, TAG + "-XSJXRunServlet()");

		final Run_img_json_entity imgentity = new Run_img_json_entity();
		new Thread(new Runnable() {
			@Override
			public void run() {
				StringBuffer sb = new StringBuffer();
				try {
					URL uri = new URL(MainApplication.XSJX_Run_img);
					HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
					conn.setReadTimeout(5000);
					conn.setConnectTimeout(5000);
					if (conn.getResponseCode() == 200) {
						InputStream in = conn.getInputStream();
						InputStreamReader isr = new InputStreamReader(in);
						BufferedReader br = new BufferedReader(isr);
						String temp = "";
						while ((temp = br.readLine()) != null) {
							sb.append(temp);
						}
						Log.v("AccessInternet", "XSJXRunServlet() sb=" + sb.toString());

						// JSON ����
						JSONObject obj = new JSONObject(sb.toString());

						if (obj.getInt("Error_code") == 0) {
							imgentity.Error_code = obj.getInt("Error_code");
							imgentity.UrlStr = obj.optString("Url");
							imgentity.ImgName = obj.optString("ImgName");

							
		
							SharedUtils.getInstance().putString("XrunImgUrl", obj.optString("Url")+obj.optString("ImgName"));
							
							
							
							Message msg = new Message();
							msg.what = 0;
							msg.obj = imgentity;
							handler.sendMessage(msg);

						}

						else {
							imgentity.Error_code = 1;
							imgentity.UrlStr = "";
							imgentity.ImgName = "";

							Message msg = new Message();
							msg.what = 1;
							msg.obj = imgentity;
							handler.sendMessage(msg);

						}
					} else {
						imgentity.Error_code = 1;
						imgentity.UrlStr = "";
						imgentity.ImgName = "";

						Message msg = new Message();
						msg.what = 1;
						msg.obj = imgentity;
						handler.sendMessage(msg);
					}

				} catch (MalformedURLException e) {

					e.printStackTrace();
					imgentity.Error_code = 1;
					imgentity.UrlStr = "";
					imgentity.ImgName = "";

					Message msg = new Message();
					msg.what = 1;
					msg.obj = imgentity;
					handler.sendMessage(msg);
				} catch (IOException e) {
					imgentity.Error_code = 1;
					imgentity.UrlStr = "";
					imgentity.ImgName = "";

					Message msg = new Message();
					msg.what = 1;
					msg.obj = imgentity;
					handler.sendMessage(msg);
					e.printStackTrace();
				} catch (JSONException e) {
					imgentity.Error_code = 1;
					imgentity.UrlStr = "";
					imgentity.ImgName = "";

					Message msg = new Message();
					msg.what = 1;
					msg.obj = imgentity;
					handler.sendMessage(msg);
					e.printStackTrace();
				}

			}
		}).start();

		return imgentity;
	}

	/**
	 * ��½��֤ �û��� ����
	 * 
	 * @return
	 */
	public static void LogoDo(final Handler handler, final String UserName, final String UserPwd) {

		Log.v(TAG, TAG + "-LogoDo()");

		OkHttpClient mOkHttpClient = new OkHttpClient();

		mOkHttpClient.setConnectTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setWriteTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setReadTimeout(3500, TimeUnit.SECONDS);

		FormEncodingBuilder builder = new FormEncodingBuilder();

		builder.add("username", UserName);// ��Ӽ�ֵ��
		builder.add("userpwd", UserPwd);

		Request request1 = new Request.Builder().url(MainApplication.csXSJX + "LogoServlet").post(builder.build())
				.build();

		mOkHttpClient.newCall(request1).enqueue(new Callback() {

			@Override
			public void onResponse(Response arg0) throws IOException {

				try {
					JSONObject json = new JSONObject(arg0.body().string());
					if (json.getInt("Error_code") == 0) {

						Log.v("LogoDo  ", " UserInfo.UserID=" + json.optInt("UserID"));
						UserInfo.UserID = json.optInt("UserID") + "";

						
						int identityid=json.optInt("IdentityID");
						if(identityid==2){
							Message msg = new Message();
							msg.what = 1;
							msg.obj = "���û���ݲ���Ϊ����!";
							handler.sendMessage(msg);
						}
						else SharedUtils.getInstance().putInt("LogDoTypes", identityid);
						
						SharedUtils.getInstance().putInt("LogDoTypesID", json.optInt("UserID"));

						UserInfo.UserName = UserName;// json.optString("UserNmae")
						UserInfo.UserPwd = UserPwd;

						UserInfo.UserSex = json.optString("UserSex");
						UserInfo.UserTel = json.optString("UserTel");
						UserInfo.UserHeadPortrait = json.optString("UserHeadImg");
						UserInfo.IdentityID = json.optInt("IdentityID");
						UserInfo.postingbanned = json.optInt("postingbanned");

						Log.v("LogoDo  ", " UserInfo.UserTel=" + UserInfo.UserTel);

						/*
						 * UserInfoDo dao=new
						 * UserInfoDo(MainApplication.baseContext);
						 * if(dao.selectID(UserInfo.UserID)) {
						 * dao.AddUserInfo(Integer.parseInt(UserInfo.UserID),
						 * UserName, UserPwd, UserInfo.UserSex,
						 * UserInfo.UserTel, UserInfo.UserHeadPortrait);
						 * Log.v(TAG+"=LogoDo", "������û����������ݿ�ɹ�!"); }
						 */

						/*
						 * List<String> infolist = new ArrayList<String>();
						 * 
						 * infolist.add(json.optInt("UserID") + "");
						 * infolist.add(json.optString("UserNmae"));
						 * infolist.add(json.optString("UserSex"));
						 * infolist.add(json.optString("UserTel"));
						 * infolist.add(json.optString("UserHeadImg"));
						 * infolist.add(json.optInt("IdentityID") + "");
						 */

						Message msg = new Message();
						msg.what = 0;
						// msg.obj = infolist;
						handler.sendMessageDelayed(msg, 1500);
						//handler.sendMessage(msg);

					} else {
						Message msg = new Message();
						msg.what = 1;
						msg.obj = "��½ʧ�ܣ������û���������!";
						handler.sendMessage(msg);

					}
				} catch (JSONException e) {

					Message msg = new Message();
					msg.what = 1;
					msg.obj = e.toString();
					handler.sendMessage(msg);
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(Request arg0, IOException t) {

				Message msg = new Message();
				msg.what = 1;
				msg.obj = "���ӳ�ʱ!������������!";
				handler.sendMessage(msg);
				/*
				 * if(t instanceof SocketTimeoutException){
				 * 
				 * Message msg1 = new Message(); msg1.what = 1;
				 * msg1.obj="���ӳ�ʱ!������������!"; handler.sendMessage(msg1);
				 * 
				 * }
				 * 
				 */
			}

		});

	}

	/**
	 * 
	 * @MethodsNmae: DoUserName
	 * @Description: ��֤�û����Ƿ�������ݿ���
	 * @param: @param
	 *             handler
	 * @param: @param
	 *             UserName
	 * @return: void
	 * @author ����Բ
	 * @Date 2017��11��14�� ����11:32:35
	 */
	public static void DoUserName(final Handler handler, String UserName) {

		Log.v(TAG, TAG + "-DoUserName()");

		OkHttpClient mOkHttpClient = new OkHttpClient();

		mOkHttpClient.setConnectTimeout(3000, TimeUnit.SECONDS);
		mOkHttpClient.setWriteTimeout(3000, TimeUnit.SECONDS);
		mOkHttpClient.setReadTimeout(3000, TimeUnit.SECONDS);

		FormEncodingBuilder builder = new FormEncodingBuilder();

		builder.add("username", UserName);// ��Ӽ�ֵ��

		Request request1 = new Request.Builder().url(MainApplication.csXSJX + "LogoServlet").post(builder.build())
				.build();

		mOkHttpClient.newCall(request1).enqueue(new Callback() {
			@Override
			public void onResponse(Response arg0) throws IOException {

				try {
					JSONObject json = new JSONObject(arg0.body().string());
					if (json.optInt("Error_code") == 0) {
						// ���û�������
						Message msg = new Message();
						msg.what = 1;
						msg.obj = "�û����Ѵ���!";// json.opt("UserID")
						handler.sendMessage(msg);
					} else {
						// ���û�������
						Message msg = new Message();
						msg.what = 0;
						handler.sendMessage(msg);

					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(Request arg0, IOException t) {

				Message msg = new Message();
				msg.what = 1;
				msg.obj = "�����쳣������������������!";
				handler.sendMessage(msg);
				/*
				 * if(t instanceof SocketTimeoutException){
				 * 
				 * Message msg1 = new Message(); msg1.what = 1;
				 * msg1.obj="���ӳ�ʱ!������������!"; handler.sendMessage(msg1);
				 * 
				 * }
				 * 
				 */
			}

		});

	}

	/**
	 * 
	 * @MethodsNmae: AddUser
	 * @Description: ��������ύע�����û�������
	 * @param: @param
	 *             handler
	 * @param: @param
	 *             AddName
	 * @param: @param
	 *             UserPwd
	 * @param: @param
	 *             UserTel
	 * @param: @param
	 *             UserSex
	 * @param: @return
	 * @return: boolean
	 * @author ����Բ
	 * @Date 2017��11��16�� ����7:32:50
	 */
	public static void AddUser(final Handler handler, String AddName, String UserPwd, String UserTel, String UserSex) {
		Log.v(TAG, TAG + "-AddUser()");

		OkHttpClient mOkHttpClient = new OkHttpClient();
		mOkHttpClient.setConnectTimeout(3000, TimeUnit.SECONDS);
		mOkHttpClient.setWriteTimeout(3000, TimeUnit.SECONDS);
		mOkHttpClient.setReadTimeout(3000, TimeUnit.SECONDS);

		FormEncodingBuilder builder = new FormEncodingBuilder();
		builder.add("AddNmae", AddName);// ��Ӽ�ֵ��
		builder.add("UserPwd", UserPwd);
		builder.add("UserTel", UserTel);
		builder.add("UserSex", UserSex);

		Request request1 = new Request.Builder().url(MainApplication.csXSJX + "AddUserServlet").post(builder.build())
				.build();

		mOkHttpClient.newCall(request1).enqueue(new Callback() {

			@Override
			public void onResponse(Response arg0) throws IOException {

				try {
					JSONObject json = new JSONObject(arg0.body().string());
					if (json.optInt("Error_code") == 0) {
						// ���û�ע��ɹ�
						Message msg = new Message();
						msg.what = 3;
						msg.obj = json.optInt("UserID") + "";
						handler.sendMessage(msg);

					} else {
						// ���û�ע��ʧ��
						Message msg = new Message();
						msg.what = 4;
						msg.obj = json.opt("Error_info");
						handler.sendMessage(msg);

					}
				} catch (JSONException e) {

					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {

				Message msg = new Message();
				msg.what = 1;
				msg.obj = "�����쳣������������������!";
				handler.sendMessage(msg);

			}
		});

	}

	/**
	 * �ֲ�ͼƬ�ӿ�
	 */
	public static void SchoolTopImgServlet(final Handler handler) {
		OkHttpClient mOkHttpClient = new OkHttpClient();
		mOkHttpClient.setConnectTimeout(3000, TimeUnit.SECONDS);
		mOkHttpClient.setWriteTimeout(3000, TimeUnit.SECONDS);
		mOkHttpClient.setReadTimeout(3000, TimeUnit.SECONDS);

		Request request = new Request.Builder().url(MainApplication.XSJX_SchoolTopImgServlet).build();
		Call call = mOkHttpClient.newCall(request);
		call.enqueue(new Callback() {
			@Override
			public void onResponse(Response arg0) throws IOException {

				try {
					JSONObject JSON = new JSONObject(arg0.body().string());

					List<SchoolTopImgEntity> list = new ArrayList<SchoolTopImgEntity>();
					if (JSON.optInt("Error_code") == 0) {
						JSONArray TopImg = JSON.optJSONArray("TopImg");

						for (int i = 0; i < TopImg.length(); i++) {
							SchoolTopImgEntity entity = new SchoolTopImgEntity();
							JSONObject arry = TopImg.getJSONObject(i);
							entity.ImgUrl = arry.optString("ImgUrl");
							entity.WebUrl = arry.optString("WebUrl");
							entity.tf = arry.optInt("tf");
							list.add(entity);

						}
						Log.v("SchoolTopImgServlet", "list=" + list.size() + "");

						Message msg = new Message();
						msg.what = 0;
						msg.obj = list;
						handler.sendMessage(msg);
					} else {
						Message msg = new Message();
						msg.what = 2;
						msg.obj = "" + JSON.optInt("Error_info");
						handler.sendMessage(msg);
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {

				Message msg = new Message();
				msg.what = 2;
				msg.obj = "�����쳣������������������!";
				handler.sendMessage(msg);
			}
		});

	}

	/**
	 * ���ʷ������ϵ�У��--������Ѷģ��
	 */

	public static void SchoolNewsServlet(final Handler handler, int pageNo) {

		OkHttpClient mOkHttpClient = new OkHttpClient();
		mOkHttpClient.setConnectTimeout(3000, TimeUnit.SECONDS);
		mOkHttpClient.setWriteTimeout(3000, TimeUnit.SECONDS);
		mOkHttpClient.setReadTimeout(3000, TimeUnit.SECONDS);

		FormEncodingBuilder builder = new FormEncodingBuilder();
		builder.add("pageNo", pageNo + "");// ��Ӽ�ֵ��

		Request request = new Request.Builder().url(MainApplication.XSJX_SchoolNewsServlet).post(builder.build())
				.build();

		Call call = mOkHttpClient.newCall(request);
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Response arg0) throws IOException {
				try {
					JSONObject JSON = new JSONObject(arg0.body().string());

					if (JSON.optInt("Error_code") == 0) {
						List<SchoolNewsEntity> list = new ArrayList<SchoolNewsEntity>();
						JSONArray news = JSON.optJSONArray("news");

						for (int i = 0; i < news.length(); i++) {
							SchoolNewsEntity entity = new SchoolNewsEntity();
							JSONObject obj = news.optJSONObject(i);
							entity.id = obj.optInt("id");
							entity.newsTitle = obj.optString("newsTitle");
							entity.newsContent = obj.optString("newsContent");
							entity.newsUrl = obj.optString("newsUrl");
							entity.newsAuthor = obj.optString("newsAuthor");
							entity.newsDate = obj.optString("newsDate");
							entity.newsUpdate = obj.optString("newsUpdate");

							list.add(entity);
						}
						Message msg = new Message();
						msg.what = 3;
						msg.obj = list;
						handler.sendMessage(msg);
					} else {

						Message msg = new Message();
						msg.what = 4;
						msg.obj = "" + JSON.optString("Error_info");
						handler.sendMessage(msg);

					}

				} catch (JSONException e) {
					Message msg = new Message();
					msg.what = 4;
					msg.obj = "���ݽ�������!";
					handler.sendMessage(msg);
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {

				Message msg = new Message();
				msg.what = 4;
				msg.obj = "�����쳣������������������!";
				handler.sendMessage(msg);
			}
		});

	}

	/**
	 * ��ѯ�û���Ϣ
	 */
	public void QueryUserInfo(int id) {

	}

	/**
	 * �ϴ��û�ͷ�� 2017 12 13
	 * 
	 * @return ͼƬ64��������image ͼƬ���� �����Ǹ��û�
	 */
	public static void uploadUserimg(String image, String imagename, String userid) {
		OkHttpClient mOkHttpClient = new OkHttpClient();
		FormEncodingBuilder builder = new FormEncodingBuilder();
		builder.add("image", image);// ��Ӽ�ֵ��
		builder.add("imagename", imagename);
		builder.add("id", userid);
		Request request1 = new Request.Builder().url(MainApplication.csXSJX + "UploadImgServlet").post(builder.build())
				.build();
		mOkHttpClient.newCall(request1).enqueue(new Callback() {
			@Override
			public void onResponse(Response arg0) throws IOException {

				try {
					JSONObject JSON = new JSONObject(arg0.body().string());
					int code = JSON.optInt("Error_code");
					if (code == 0) {
						Log.v("uploadUserimg", "ͼƬ�ϴ��ɹ�");
						// Utils.showToast(context, "�޸ĳɹ�!");
					} else
						Log.v("uploadUserimg", "ͼƬ�ϴ�ʧ��" + JSON.optString("Error_info"));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Request arg0, IOException t) {
				Log.v("uploadUserimg", "ͼƬ�ϴ�ʧ��" + arg0.toString());

			}

		});

	}

	/**
	 * ���ʼ������ͺ���ѯ�绰�������ݽӿ� 1017 12 14 17:07 ����Բ
	 */
	public static void JzInfoServlet(final Handler handler) {
		OkHttpClient httpClient = new OkHttpClient();

		httpClient.setConnectTimeout(3500, TimeUnit.SECONDS);
		httpClient.setWriteTimeout(3500, TimeUnit.SECONDS);
		httpClient.setReadTimeout(3500, TimeUnit.SECONDS);

		Request request = new Request.Builder().url(MainApplication.XSJX_JzInfoServlet).build();
		Call call = httpClient.newCall(request);
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Response arg0) throws IOException {
				Log.v(TAG, "JzInfoServlet() onResponse");

				// ����ɹ�
				try {
					JSONObject json = new JSONObject(arg0.body().string());
					JzTelClass jzTelClass = new JzTelClass();
					List<JzInfoEntity> jzlist = null;
					List<tel> tellist = null;
					if (json.optInt("Error_code") == 0) {
						JSONArray jzinfo = json.optJSONArray("jzinfo");
						jzlist = new ArrayList<JzInfoEntity>();
						for (int i = 0; i < jzinfo.length(); i++) {
							JSONObject obj = jzinfo.optJSONObject(i);
							JzInfoEntity entity = new JzInfoEntity();
							entity.id = obj.optInt("id");// id
							entity.type = obj.optString("type");// ��������
							entity.typeclass = obj.optString("typeclass");// ����
							entity.money = obj.optDouble("money");// �۸�
							entity.typeinfo = obj.optString("typeinfo"); // ���ͽ���
							entity.examcontent = obj.optString("examcontent");// ��������
							entity.passmuster = obj.optString("passmuster");// �ϸ��׼
							entity.moneyinfo = obj.optString("moneyinfo");// ������ϸ���
							entity.nomoneyinfo = obj.optString("nomoneyinfo");// �������ķ���
							entity.serviceinfo = obj.optString("serviceinfo");// ������Ŀ
							entity.number = obj.optInt("number");// �ܱ�������
							
							entity.asof=obj.optInt("asof");// �Ƿ��ֹ����
							
							jzlist.add(entity);
						}
						jzTelClass.jzlist = jzlist;

					} else
						jzTelClass.jzerror = json.optString("Error_info");
					// ����ѯ�绰����

					if (json.optInt("telEorr_code") == 0) {
						JSONArray tel = json.optJSONArray("tel");
						tellist = new ArrayList<tel>();
						for (int i = 0; i < tel.length(); i++) {
							JSONObject obj = tel.optJSONObject(i);
							tel en = new tel();
							en.id = obj.optInt("id");
							en.telname = obj.optString("telname");
							en.tel = obj.optString("tel");
							tellist.add(en);
						}
						jzTelClass.tellist = tellist;

					} else
						jzTelClass.telerror = json.optString("tel");

					// ͨ��Handler ��������
					Message msg = new Message();
					msg.what = 0;
					msg.obj = jzTelClass;
					handler.sendMessage(msg);

				} catch (JSONException e) {
					e.printStackTrace();
					Message msg = new Message();
					msg.what = 1;
					msg.obj = e.toString();
					handler.sendMessage(msg);
				}

			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {

				// ����ʧ��
				Message msg = new Message();
				msg.what = 1;
				msg.obj = arg1.toString();
				handler.sendMessage(msg);
			}
		});

	}

	/**
	 * ����û��Ƿ�����
	 */
	public static void UserJZServlet(final Handler handler, String userid) {
		OkHttpClient httpClient = new OkHttpClient();
		httpClient.setConnectTimeout(3500, TimeUnit.SECONDS);
		httpClient.setWriteTimeout(3500, TimeUnit.SECONDS);
		httpClient.setReadTimeout(3500, TimeUnit.SECONDS);
		FormEncodingBuilder builder = new FormEncodingBuilder();

		builder.add("Userid", userid);// ��Ӽ�ֵ��
		Request request1 = new Request.Builder().url(MainApplication.XSJX_UserJZServlet).post(builder.build()).build();

		httpClient.newCall(request1).enqueue(new Callback() {

			@Override
			public void onResponse(Response arg0) throws IOException {
				try {
					JSONObject json = new JSONObject(arg0.body().string());
					UserJzentity userjz = new UserJzentity();
					if (json.optInt("Error_code") == 0) {
						userjz.userid = json.optInt("UserID");
						userjz.jzid = json.optInt("JzID");
						userjz.type = json.optString("type");
						userjz.typeclass = json.optString("typeclass");
						userjz.money = json.optDouble("money");

						Message msg = new Message();
						msg.what = 0;
						msg.obj = userjz;
						handler.sendMessage(msg);

					} else {
						String str = json.optString("Error_info");
						if (str.equals("�޸��û��ı�������!")) {
							handler.sendEmptyMessage(1);
						} else {
							Message msg = new Message();
							msg.what = 2;
							msg.obj = json.optString("Error_info");
							handler.sendMessage(msg);
						}

					}

				} catch (JSONException e) {

					Message msg = new Message();
					msg.what = 2;
					msg.obj = e.toString();
					handler.sendMessage(msg);
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {

				Message msg = new Message();
				msg.what = 2;
				msg.obj = "�޷����ӵ�������!";
				handler.sendMessage(msg);

			}
		});
	}

	/**
	 * ���»�������û���ϸ��Ϣ��
	 */
	/**
	 * ����û��Ƿ�����
	 */
	public static void AddUserEnrolInfo(final Handler handler, String userid, String name, String card, String tel,
			String weixin, String qq, String xinda, String jzid) {

		OkHttpClient httpClient = new OkHttpClient();
		httpClient.setConnectTimeout(3500, TimeUnit.SECONDS);
		httpClient.setWriteTimeout(3500, TimeUnit.SECONDS);
		httpClient.setReadTimeout(3500, TimeUnit.SECONDS);
		FormEncodingBuilder builder = new FormEncodingBuilder();

		builder.add("userid", userid);// ��Ӽ�ֵ�� id
		builder.add("name", name);// ��ʵ����
		builder.add("card", card); // ���֤
		builder.add("tel", tel); // �绰
		builder.add("weixin", weixin); // ΢��
		builder.add("qq", qq); // QQ
		builder.add("xinda", xinda); // �Ƿ����Ŵ��
		builder.add("jzid", jzid); // ������Ǹ��������Ͱ�

		Request request1 = new Request.Builder().url(MainApplication.XSJX_AddUserEnrolInfo).post(builder.build())
				.build();
		httpClient.newCall(request1).enqueue(new Callback() {

			@Override
			public void onResponse(Response arg0) throws IOException {

				try {
					JSONObject json = new JSONObject(arg0.body().string());
					if (json.optInt("Error_code") == 0) {
						handler.sendEmptyMessage(3);

					} else {

						Message msg = new Message();
						msg.what = 4;
						msg.obj = json.optString("Error_info");
						handler.sendMessage(msg);

					}

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {
			}
		});

	}

	/**
	 * ��ȡ�û�������ͺͷ���ģʽ 2018 1 1 22:28
	 */
	public static void getPostingtity(final Handler handler) {
		OkHttpClient httpClient = new OkHttpClient();
		httpClient.setConnectTimeout(3000, TimeUnit.SECONDS);
		httpClient.setWriteTimeout(3000, TimeUnit.SECONDS);
		httpClient.setReadTimeout(3000, TimeUnit.SECONDS);

		FormEncodingBuilder builder = new FormEncodingBuilder();

		// builder.add("userid", UserID);// id

		Request re = new Request.Builder().url(MainApplication.XSJX_Postingtity).post(builder.build()).build();
		httpClient.newCall(re).enqueue(new Callback() {

			@Override
			public void onResponse(Response arg0) throws IOException {
				try {
					JSONObject json = new JSONObject(arg0.body().string());
					if (json.optInt("Error_code") == 0) {
						Postingtity p = new Postingtity();

						p.PostingMsId = json.optInt("PostingMsId");
						p.PostingMsInfo = json.optString("PostingMsInfo");

						Message msg = new Message();
						msg.what = 0;
						msg.obj = p;
						handler.sendMessage(msg);

					} else {
						Message msg = new Message();
						msg.what = 2;
						msg.obj = json.optString("Error_info");
						;
						handler.sendMessage(msg);
					}

				} catch (JSONException e) {
					e.printStackTrace();
					Message msg = new Message();
					msg.what = 2;
					msg.obj = e.toString();
					handler.sendMessage(msg);
				}
			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {
				Message msg = new Message();
				msg.what = 2;
				msg.obj = "����ʧ��,����������������!";
				handler.sendMessage(msg);
			}
		});

	}

	/**
	 * ������Ƶ���� 2017 1 5 15:42
	 */
	public static void getVideoAll(final Handler handler) {
		OkHttpClient mOkHttpClient = new OkHttpClient();

		mOkHttpClient.setConnectTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setWriteTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setReadTimeout(3500, TimeUnit.SECONDS);

		Request request = new Request.Builder().url(MainApplication.XSJX_VideoAll).build();
		Call call = mOkHttpClient.newCall(request);
		call.enqueue(new Callback() {

			@Override
			public void onResponse(Response arg0) throws IOException {
				// �ɹ�
				try {
					JSONObject json = new JSONObject(arg0.body().string());
					if (json.optInt("Error_code") == 0) {
						List<Video> list = new ArrayList<Video>();

						JSONArray video = json.optJSONArray("video");
						for (int i = 0; i < video.length(); i++) {
							JSONObject obj = video.getJSONObject(i);
							Video v = new Video();
							v.id = obj.optInt("id");
							v.name = obj.optString("name");
							v.imgurl = obj.optString("imgurl");

							v.url = obj.optString("url");
							v.author = obj.optString("author");
							v.date = obj.optString("date");
							v.ke = obj.optInt("ke");
							v.ksyq = obj.optString("ksyq");
							v.ppbz = obj.optString("ppbz");
							v.czff = obj.optString("czff");
							v.zysx = obj.optString("zysx");
							list.add(v);
						}

						Message msg = new Message();
						msg.what = 0;
						msg.obj = list;
						handler.sendMessage(msg);
					} else {
						Message msg = new Message();
						msg.what = 1;
						msg.obj = json.optString("Error_info");
						handler.sendMessage(msg);
					}

				} catch (JSONException e) {
					e.printStackTrace();
					Message msg = new Message();
					msg.what = 1;
					msg.obj = "������!";
					handler.sendMessage(msg);
				}
			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// �ɹ� ʧ��

				Message msg = new Message();
				msg.what = 1;
				msg.obj = "����ʧ��,����������������!";
				handler.sendMessage(msg);
			}
		});

	}

	/**
	 * �������� ���� ���� ��� ����ģʽ ͼƬ����
	 */
	public static void UpLoadPosting(final Handler handler, String title, String conten, String identity, String id,
			String ms, final List<String> imgList) {
		OkHttpClient mOkHttpClient = new OkHttpClient();

		mOkHttpClient.setConnectTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setWriteTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setReadTimeout(3500, TimeUnit.SECONDS);

		FormEncodingBuilder builder = new FormEncodingBuilder();

		builder.add("title", title); // ����
		builder.add("conten", conten);// ����
		builder.add("identity", identity);// ���
		builder.add("ms", ms);// ģʽ

		builder.add("id", id);// id

		if (imgList != null && imgList.size() > 0) {
			
			builder.add("imgtf", "true");// �Ƿ���ͼ ��
			builder.add("imgnum", imgList.size() + "");// �Ƿ���ͼ �м���

			for (int i = 0; i < imgList.size(); i++) {
				builder.add("img" + (i + 1), imgList.get(i));// ͼƬ���
				Log.e("img"+i, imgList.get(i));
			}
			
			
		} else 
		    {builder.add("imgtf", "false");}// �Ƿ���ͼ ��}
			

		// builder.add("userid", UserID);// id

		Request re = new Request.Builder().url(MainApplication.XSJX_UpLoadPosting).post(builder.build()).build();
		mOkHttpClient.newCall(re).enqueue(new Callback() {

			@Override
			public void onResponse(Response arg0) {

				try {
					JSONObject JSON = new JSONObject(arg0.body().string());

					if (JSON.optInt("Error_code") == 0) {

						// ���ش�����Ϣ
						Message msg = new Message();
						msg.what = 3;
						msg.obj = JSON.optString("PostingInfo") + "";
						handler.sendMessage(msg);

					} else {

						Message msg = new Message();
						msg.what = 4;
						msg.obj = JSON.optString("Error_info") + "";
						handler.sendMessage(msg);

					}

				} catch (JSONException e) {
					e.printStackTrace();
					Message msg = new Message();
					msg.what = 4;
					msg.obj = "����ʧ��!";
					handler.sendMessage(msg);
				} catch (IOException e) {
					// ��

					e.printStackTrace();
					Message msg = new Message();
					msg.what = 4;
					msg.obj = "����ʧ��!";
					handler.sendMessage(msg);
				}
			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// �ɹ� ʧ��

				Message msg = new Message();
				msg.what = 4;
				msg.obj = "����ʧ��,����������������!";
				handler.sendMessage(msg);
			}
		});

	}

	/**
	 * �����̳����
	 */

	public static void getPosting(final Handler handler, final int pageNO) {

		OkHttpClient mOkHttpClient = new OkHttpClient();

		mOkHttpClient.setConnectTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setWriteTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setReadTimeout(3500, TimeUnit.SECONDS);

		FormEncodingBuilder builder = new FormEncodingBuilder();

		builder.add("pageNo", pageNO + ""); // ҳ��
		Request re = new Request.Builder().url(MainApplication.XSJX_PostingServlet).post(builder.build()).build();
		mOkHttpClient.newCall(re).enqueue(new Callback() {

			@Override
			public void onResponse(Response s) {
				try {
					JSONObject JSON = new JSONObject(s.body().string());
					if (JSON.optInt("Error_code") == 0) {

						/**
						 * ������������
						 */
						List<Posting> p = new ArrayList<Posting>();

						JSONArray posting = JSON.optJSONArray("Posting");

						if (posting.length() > 0) {
							for (int i = 0; i < posting.length(); i++) {
								Posting pp = new Posting();
								JSONObject j = posting.getJSONObject(i);// ��õ�������
								pp.PostingID = j.optInt("PostingID");// id
								pp.PostingTitle = j.optString("PostingTitle");// ����
								pp.PostingConten = j.optString("PostingConten");// ����
								pp.date = j.optString("date");// ʱ��
								pp.name = j.optString("name");// �û���
								pp.headImg = j.optString("headImg");// ͷ��
								pp.identity = j.optString("identity");// ���
								pp.identityID = j.optInt("identityID");// ���ID
								pp.typesID = j.optInt("typesID");

								JSONArray img = j.getJSONArray("PostingImg");
								for (int k = 0; k < img.length(); k++) {
									pp.PostingImg.add(img.getString(k));
								}
								p.add(pp);
							}
						} else {
							Message msg = new Message();
							msg.what = 1;
							msg.obj = "���ݼ������";
							handler.sendMessageDelayed(msg, 1500);
							// handler.sendMessage(msg);
						}
						// ��һҳ����
						if (pageNO == 1) {
							Message msg = new Message();
							msg.what = 0;
							msg.obj = p;
							handler.sendMessageDelayed(msg, 1500);
							// handler.sendMessage(msg);
						} else {
							// ����ҳ����
							Message msg = new Message();
							msg.what = 3;
							msg.obj = p;
							handler.sendMessageDelayed(msg, 1500);
							// handler.sendMessage(msg);

						}

					}

					else {
						// ���ش�����Ϣ
						Message msg = new Message();
						msg.what = 1;
						msg.obj = JSON.optString("Error_info");
						handler.sendMessageDelayed(msg, 1500);
						// handler.sendMessage(msg);

					}
				} catch (JSONException e) {

					e.printStackTrace();
					// ���ش�����Ϣ
					Message msg = new Message();
					msg.what = 1;
					msg.obj = e.toString();
					handler.sendMessage(msg);

				} catch (IOException e) {
					// ���ش�����Ϣ
					Message msg = new Message();
					msg.what = 1;
					msg.obj = e.toString();
					handler.sendMessage(msg);

					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {
				// ���ش�����Ϣ
				Message msg = new Message();
				msg.what = 1;
				msg.obj = "����������������!";
				handler.sendMessage(msg);
			}
		});
	}

	/**
	 * �����û����ID��ID���������ж�����
	 */
	public static void getPosting(final Handler handler, int identityID, int ID) {

		OkHttpClient mOkHttpClient = new OkHttpClient();

		mOkHttpClient.setConnectTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setWriteTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setReadTimeout(3500, TimeUnit.SECONDS);

		FormEncodingBuilder builder = new FormEncodingBuilder();

		builder.add("identityID", identityID + "");
		builder.add("id", ID + "");

		Request re = new Request.Builder().url(MainApplication.XSJX_GetStudentPosting).post(builder.build()).build();
		mOkHttpClient.newCall(re).enqueue(new Callback() {

			@Override
			public void onResponse(Response arg0) throws IOException {
				try {
					JSONObject JSON = new JSONObject(arg0.body().string());
					List<Posting> p = new ArrayList<Posting>();
					if (JSON.optInt("Error_code") == 0) {

						/**
						 * ������������
						 */
						JSONArray posting = JSON.optJSONArray("Posting");

						if (posting.length() > 0) {
							for (int i = 0; i < posting.length(); i++) {
								Posting pp = new Posting();
								JSONObject j = posting.getJSONObject(i);// ��õ�������
								pp.PostingID = j.optInt("PostingID");// id
								pp.PostingTitle = j.optString("PostingTitle");// ����
								pp.PostingConten = j.optString("PostingConten");// ����
								pp.date = j.optString("date");// ʱ��
								pp.name = j.optString("name");// �û���
								pp.headImg = j.optString("headImg");// ͷ��
								pp.identity = j.optString("identity");// ���
								pp.identityID = j.optInt("identityID");// ���ID
								pp.typesID = j.optInt("typesID");

								JSONArray img = j.getJSONArray("PostingImg");
								for (int k = 0; k < img.length(); k++) {
									pp.PostingImg.add(img.getString(k));
								}
								p.add(pp);
							}

						}

					}

					else {
						// ���ش�����Ϣ
						Message msg = new Message();
						msg.what = 1;
						msg.obj = JSON.optString("Error_info") + "";
						handler.sendMessage(msg);
					}

					Message msg = new Message();
					msg.what = 0;
					msg.obj = p;
					handler.sendMessage(msg);

				} catch (JSONException e) {
					// ���ش�����Ϣ
					Message msg = new Message();
					msg.what = 1;
					msg.obj = "����������������!";
					handler.sendMessage(msg);
					e.printStackTrace();
				}

			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {

				// ���ش�����Ϣ
				Message msg = new Message();
				msg.what = 1;
				msg.obj = "����������������!";
				handler.sendMessage(msg);
			}
		});

	}

	/**
	 * ��������ID���������б�
	 */

	public static void getPostingComments(final Handler handler, final int PostingID) {

		OkHttpClient mOkHttpClient = new OkHttpClient();

		mOkHttpClient.setConnectTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setWriteTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setReadTimeout(3500, TimeUnit.SECONDS);

		FormEncodingBuilder builder = new FormEncodingBuilder();

		builder.add("PostingID", PostingID + ""); // ҳ��
		Request re = new Request.Builder().url(MainApplication.XSJX_PostingComments).post(builder.build()).build();
		mOkHttpClient.newCall(re).enqueue(new Callback() {

			@Override
			public void onResponse(Response s) throws IOException {
				List<PComments> pc = new ArrayList<PComments>();
				try {
					JSONObject JSON = new JSONObject(s.body().string());
					if (JSON.optInt("Error_code") == 0) {
						int kk = JSON.optInt("PostingID");
						if (PostingID == kk) {
							JSONArray con = JSON.optJSONArray("Comments");
							if (con.length() > 0) {
								for (int i = 0; i < con.length(); i++) {
									PComments c = new PComments();
									JSONObject cjson = con.optJSONObject(i);
									c.conten = cjson.optString("conten");
									c.identityID = cjson.optInt("identityid");
									c.typesID = cjson.optInt("typesid");
									c.identity = cjson.optString("identity");
									c.name = cjson.optString("name");
									c.date = cjson.optString("date");
									c.headImg = cjson.optString("headImg");
									pc.add(c);
								}

							}
							Message msg = new Message();
							msg.what = 0;
							msg.obj = pc;
							handler.sendMessage(msg);
						}

						else {
							Message msg = new Message();
							msg.what = 1;
							msg.obj = "����ID�ͷ���ID��һ��!";
							handler.sendMessage(msg);
						}
					} else {
						Message msg = new Message();
						msg.what = 1;
						msg.obj = JSON.optString("Error_info");
						handler.sendMessage(msg);
					}

				} catch (JSONException e) {

					Message msg = new Message();
					msg.what = 1;
					msg.obj = e.toString() + "";
					handler.sendMessage(msg);
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {

				Message msg = new Message();
				msg.what = 1;
				msg.obj = "����������������!";
				handler.sendMessage(msg);

			}
		});

	}
	/**
	 * ɾ������
	 */
	public static void DelectPosting(final Handler handler, final int PostingID) {

		OkHttpClient mOkHttpClient = new OkHttpClient();

		mOkHttpClient.setConnectTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setWriteTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setReadTimeout(3500, TimeUnit.SECONDS);

		FormEncodingBuilder builder = new FormEncodingBuilder();

		builder.add("PostingID", PostingID + ""); 
		builder.add("Posting","true"); // ҳ��
		Request re = new Request.Builder().url(MainApplication.XSJX_PostingComments).post(builder.build()).build();
		mOkHttpClient.newCall(re).enqueue(new Callback() {

			@Override
			public void onResponse(Response s) throws IOException {
				
				try {
					JSONObject JSON = new JSONObject(s.body().string());
					if (JSON.optInt("Error_code") == 0) {
						handler.sendEmptyMessageDelayed(3, 1500);
					 }
					else{
						
						handler.sendEmptyMessageDelayed(4, 1500);
					}
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {

				

			}
		});

	}
	
	

	/**
	 * �������� 2018-1-14
	 */
	public static void uploadpostingComments(final Handler handler, int PostingID, String Content, int IdentityID,
			String TypeID) {
		OkHttpClient mOkHttpClient = new OkHttpClient();

		mOkHttpClient.setConnectTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setWriteTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setReadTimeout(3500, TimeUnit.SECONDS);

		FormEncodingBuilder builder = new FormEncodingBuilder();

		builder.add("PostingID", PostingID + ""); // id
		builder.add("Content", Content + ""); // ��������
		builder.add("IdentityID", IdentityID + ""); // ���id
		builder.add("TypeID", TypeID); // �û�ID

		Request re = new Request.Builder().url(MainApplication.XSJX_UpLoadPostingComments).post(builder.build())
				.build();
		mOkHttpClient.newCall(re).enqueue(new Callback() {
			@Override
			public void onResponse(Response arg0) throws IOException {
				try {
					JSONObject JSON = new JSONObject(arg0.body().string());
					if (JSON.optInt("Error_code") == 0) {
						Message msg = new Message();
						msg.what = 2;
						msg.obj = JSON.optString("Success");
						handler.sendMessage(msg);
					} else {
						Message msg = new Message();
						msg.what = 1;
						msg.obj = JSON.optString("Error_info");
						handler.sendMessage(msg);
					}
				} catch (JSONException e) {

					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {
				Message msg = new Message();
				msg.what = 1;
				msg.obj = "����������������!";
				handler.sendMessage(msg);
			}
		});
	}

	/*----------------------------��ʤ��У-������Ľӿ� s-------------------------*/
	/**
	 * �ύ������Կ ���շ���ֵ
	 */
	public static void getCoachKey(final Handler handler, String Key) {
		OkHttpClient mOkHttpClient = new OkHttpClient();
		mOkHttpClient.setConnectTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setWriteTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setReadTimeout(3500, TimeUnit.SECONDS);
		FormEncodingBuilder builder = new FormEncodingBuilder();

		builder.add("Key", Key); // Key
		Request re = new Request.Builder().url(APIURL.COACHKEY).post(builder.build()).build();
		mOkHttpClient.newCall(re).enqueue(new Callback() {

			@Override
			public void onResponse(Response str) throws IOException {

				try {
					JSONObject JSON = new JSONObject(str.body().string());
					if (JSON.optInt("Error_code") == 0) {
						Message msg = new Message();
						msg.what = 3;
						msg.obj = "��Կ��ȷ!";

						handler.sendMessageDelayed(msg, 2000);

						// handler.sendMessage(msg);
					} else {
						Message msg = new Message();
						msg.what = 2;
						msg.obj = "��Կ����!";
						handler.sendMessageDelayed(msg, 2000);
					}

				} catch (JSONException e) {
					Message msg = new Message();
					msg.what = 2;
					msg.obj = "����������������!";
					handler.sendMessageDelayed(msg, 2000);
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {
				Message msg = new Message();
				msg.what = 2;
				msg.obj = "����������������!";
				handler.sendMessageDelayed(msg, 2000);
			}
		});
	}

	/**
	 * ������½
	 * 
	 * @author ����Բ
	 * @Date 2018��2��4�� ����5:27:54
	 */
	public static void LogoDoCoach(final Handler handler, String name, String pwd, String Key) {
		OkHttpClient mOkHttpClient = new OkHttpClient();

		mOkHttpClient.setConnectTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setWriteTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setReadTimeout(3500, TimeUnit.SECONDS);

		FormEncodingBuilder builder = new FormEncodingBuilder();

		builder.add("Name", name);
		builder.add("Pwd", pwd);
		builder.add("Key", Key);

		Request re = new Request.Builder().url(APIURL.LOGODOCOACH).post(builder.build()).build();
		mOkHttpClient.newCall(re).enqueue(new Callback() {
			@Override
			public void onResponse(Response str) throws IOException {
				JSONObject JSON;
				try {
					JSON = new JSONObject(str.body().string());
					if (JSON.optInt("Error_code") == 0) {

						CoachInfo.id = JSON.optInt("id");

						SharedUtils.getInstance().putInt("LogDoTypes", 2);//�����½�ɹ� ������Ϊ2
						SharedUtils.getInstance().putInt("LogDoTypesID", JSON.optInt("id"));
						
						CoachInfo.name = JSON.optString("name");
						CoachInfo.tel = JSON.optString("tel");
						CoachInfo.sex = JSON.optString("sex");
						CoachInfo.coachimg = JSON.optString("coachimg");
						CoachInfo.headimg = JSON.optString("headimg");
						CoachInfo.ke = JSON.optInt("ke");
						CoachInfo.palceID = JSON.optInt("placeid");
						CoachInfo.palce = JSON.optString("place");

						CoachInfo.qianming = JSON.optString("qianming");

						// �����ں��������ԤԼʱ��
						JSONObject seasoninfo = JSON.optJSONObject("seasoninfo");

						JSONArray seasonarra = seasoninfo.optJSONArray("season");
						for (int i = 0; i < seasonarra.length(); i++) {
							JSONObject seasonobj = seasonarra.optJSONObject(i);
							season s = new season();
							s.seasonid = seasonobj.optInt("seasonid");
							s.seasonname = seasonobj.optString("seasonNmae");
							s.tf = seasonobj.optInt("tf");
							
							if(s.tf==1){
								DaySeasonDate.seasontf=i;
							}

							JSONArray daythreearra = seasonobj.optJSONArray("daythree");

							for (int j = 0; j < daythreearra.length(); j++) {
								JSONObject dayobj = daythreearra.optJSONObject(j);
								daythree day = new daythree();
								day.daythreeid = dayobj.optInt("daythreeid");
								day.daythreename = dayobj.optString("daythreename");
								day.starttime = dayobj.optString("starttime");
								day.endtime = dayobj.optString("endtime");
								s.daythree.add(day);
							}
							DaySeasonDate.seasonlist.add(s);

						}
						// �����ں��������ԤԼʱ��

						//����δ��6���ʱ������
						JSONObject sixdate=JSON.optJSONObject("DateWeek");
						JSONArray sixweek=sixdate.optJSONArray("orderdate");
						for(int i=0;i<sixweek.length();i++){
							makedate make=new makedate();
							JSONObject mj=sixweek.optJSONObject(i);
							make.id=mj.optInt("dateid");
							make.date=mj.optString("date");
							make.week=mj.optString("week");
							DaySeasonDate.makelist.add(make);
						}
						
						JSONObject updateAPK=JSON.optJSONObject("updateAPK");
					
							DaySeasonDate.apkEntity.Error_code = updateAPK.optInt("Error_code");
							DaySeasonDate.apkEntity.appname = updateAPK.optString("appname");
							DaySeasonDate.apkEntity.apkname = updateAPK.optString("apkname");
							DaySeasonDate.apkEntity.verName = updateAPK.optString("verName");
							DaySeasonDate.apkEntity.verCode = updateAPK.optString("verCode");
							DaySeasonDate.apkEntity.apkurl = updateAPK.optString("apkurl");
							DaySeasonDate.apkEntity.updateinfo = updateAPK.optString("updateinfo");
						
						
						
						Message msg = new Message();
						msg.what = 0;
						msg.obj = "��Կ��ȷ!��½�ɹ�!";
						handler.sendMessageDelayed(msg, 2000);

						// handler.sendMessage(msg);
					} else {
						Message msg = new Message();
						msg.what = 1;
						msg.obj = JSON.optString("Error_info");
						handler.sendMessageDelayed(msg, 2000);
					}

				} catch (JSONException e) {
					e.printStackTrace();
					
					Message msg = new Message();
					msg.what = 1;
					msg.obj = e.toString();
					handler.sendMessageDelayed(msg, 2000);
					
				}

			}

			@Override
			public void onFailure(Request arg0, IOException arg1) {
				Message msg = new Message();
				msg.what = 1;
				msg.obj = "��½ʧ��";
				handler.sendMessageDelayed(msg, 2000);
			}
		});
	}
	
	
	/**
	 * ���ʽ�����Ӧ�Ŀ�Ŀ����Ŀ����ԤԼ��Ϣ
	 */
	public static void getOrderKe23(final Handler handler,int CoachID,final int KeID){
		
		OkHttpClient mOkHttpClient = new OkHttpClient();

		mOkHttpClient.setConnectTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setWriteTimeout(3500, TimeUnit.SECONDS);
		mOkHttpClient.setReadTimeout(3500, TimeUnit.SECONDS);

		FormEncodingBuilder builder = new FormEncodingBuilder();

		builder.add("CoachID", CoachID+"");
		builder.add("KeID", KeID+"");
		builder.add("Key","4cf7a5f249f2eac8721c2cc882724e7001be8c119f5df53202443c1df25bb120618de0c29628f1ea7437b76404b0bd5a86107f6f309d441e3abf1bbf40b3cf37");	
	
		
		Request re = new Request.Builder().url(APIURL.GETORDERKE23).post(builder.build()).build();
		
		mOkHttpClient.newCall(re).enqueue(new Callback(){
			@Override
			public void onResponse(Response str) throws IOException {
			
				 
				
				JSONObject JSON;
					try {
						JSON = new JSONObject(str.body().string());
						
						// Log.e("JSON", JSON.toString());
						
						if (JSON.optInt("Error_code") == 0) {
							
						switch (KeID) {
						case 2:
							List<Ke2order> ListKe2=new ArrayList<Ke2order>();
							
							JSONArray ke2array=JSON.optJSONArray("ke2order");
							for(int i=0;i<ke2array.length();i++){
								JSONObject ke2=ke2array.optJSONObject(i);
								
								Ke2order ke2order=new Ke2order();
								
								ke2order.setCconandoffid(ke2.optInt("cconandoffid"));
								ke2order.setDate(ke2.optString("date"));
								ke2order.setDateid(ke2.optInt("dateid"));
								ke2order.setDaythreeid(ke2.optInt("daythreeid"));
								
								ke2order.setDaythreeName(ke2.optString("daythreeName"));
								ke2order.setLiuyan(ke2.optString("liuyan"));
								ke2order.setState(ke2.optInt("state"));
								ke2order.setWeek(ke2.optString("week"));
								
								
								JSONArray ke2timearry=ke2.optJSONArray("Timeorder");
								if(ke2timearry.length()>0)
								for(int j=0;j<ke2timearry.length();j++){
									JSONObject jtime=ke2timearry.optJSONObject(j);
									
								    Timeorder timeorder=new Timeorder();
								    
								    timeorder.setCcke2id(jtime.optInt("ccke2id"));
								    timeorder.setCcstarttime(jtime.optString("ccstarttime"));
								    timeorder.setCcendtime(jtime.optString("ccendtime"));
								    
								  
								   JSONArray ke2stuarry=jtime.optJSONArray("Stuinfo");  
								
								    for(int k=0;k<ke2stuarry.length();k++){
								    	 JSONObject jstu=ke2stuarry.optJSONObject(k);
								    	 
								    	 Stuinfo stuinfo=new Stuinfo();
								    	 
								    	 stuinfo.setId(jstu.optInt("id"));
								    	 
								    	 stuinfo.setUserid(jstu.optInt("userid"));
								    	 stuinfo.setUsername(jstu.optString("username"));
								    	 stuinfo.setUserliuyan(jstu.optString("userliuyan"));
								    	 stuinfo.setOrderstate(jstu.optInt("orderstate"));
								    	 
								    	 
								    	  
								    	 timeorder.getStuinfo().add(stuinfo);
								         } 
								   
								    
								    ke2order.getTimeorder().add(timeorder);
								     
								    }
								   
								    
								ListKe2.add(ke2order);
								
							   }
	        
		    Log.e("ListKe2", ListKe2.size()+"");
	         
	            Message msgo = new Message();
				msgo.what = 4;
				msgo.obj = ListKe2;
				handler.sendMessageDelayed(msgo, 1500);
	         
							break;

                       case 3:
							//��Ŀ��
                    	   List<Ke3order> ListKe3=new ArrayList<Ke3order>();   
                    	
                    	   JSONArray ke3array=JSON.optJSONArray("ke3order");
                    	   for(int i=0;i<ke3array.length();i++){
                    		   JSONObject jke3=ke3array.optJSONObject(i);
                    		   Ke3order ke3order=new Ke3order();
                    		   
                    		   ke3order.cconandoffid=jke3.optInt("cconandoffid");
                    		   ke3order.dateid=jke3.optInt("dateid");
                    		   ke3order.date=jke3.optString("date");
                    		   ke3order.daythreeid=jke3.optInt("daythreeid");
                    		   ke3order.daythreeName=jke3.optString("daythreename");
                    		   ke3order.state=jke3.optInt("state");
                    		   ke3order.liuyan=jke3.optString("liuyan");
                    		   ke3order.week=jke3.optString("week");
                    		   
                    		   ke3order.ccke3id=jke3.optInt("ccke3id");
                    		   ke3order.num=jke3.optInt("num");
                    		   
                    		   JSONArray stuarry=jke3.optJSONArray("stuinfo");
                    		   if(stuarry.length()>0)
                    		   for(int j=0;j<stuarry.length();j++){
                    			   JSONObject jstu=stuarry.optJSONObject(j);
                    			   
                    			     Stuinfo stuinfo=new Stuinfo();
							    	 stuinfo.setId(jstu.optInt("id"));
							    	 stuinfo.setUserid(jstu.optInt("userid"));
							    	 stuinfo.setUsername(jstu.optString("username"));
							    	 stuinfo.setUserliuyan(jstu.optString("userliuyan"));
							    	 stuinfo.setOrderstate(jstu.optInt("orderstate"));
							    	 ke3order.stuinfo.add(stuinfo);
                    			   
                    		   }

                    		   ListKe3.add(ke3order);   
                    		   
                    	   }
                    	   Log.e("ListKe3", ListKe3.size()+"");
                    	   
                    	Message msg3 = new Message();
                    	msg3.what = 4;
                    	msg3.obj = ListKe3;
           				handler.sendMessageDelayed(msg3, 1500);
                    	   
							break;
						default:
							Message msg = new Message();
							msg.what = 3;
							msg.obj = "δ��ȡ����Ŀ"+KeID+"��ԤԼ��Ϣ!";
							handler.sendMessageDelayed(msg, 2000);
							break;
						  }	
					}
						
						
						else{
							//����������Ϊ1
							Message msg = new Message();
							msg.what = 3;
							msg.obj = JSON.optString("Error_info");
							handler.sendMessageDelayed(msg, 2000);
						}	
						
					} catch (JSONException e) {
						Message msg = new Message();
						msg.what = 3;
						msg.obj = e.toString();
						handler.sendMessageDelayed(msg, 2000);
						e.printStackTrace();
					}

			}
			
			@Override
			public void onFailure(Request arg0, IOException arg1) {
				//ʧ��
				Message msg = new Message();
				msg.what = 3;
				msg.obj = "��ȡʧ�ܣ�����������������!";
				handler.sendMessageDelayed(msg, 2000);
			}
		});


	}
	

	/**
	 * �޸Ľ�����ǩ��
	 * 	
	 */
	public static void updataCoachqianming(final Handler handler,int CoachID,String qianming){
		OkHttpClient mOkHttpClient = new OkHttpClient();

		mOkHttpClient.setConnectTimeout(3000, TimeUnit.SECONDS);
		mOkHttpClient.setWriteTimeout(3000, TimeUnit.SECONDS);
		mOkHttpClient.setReadTimeout(3000, TimeUnit.SECONDS);

		FormEncodingBuilder builder = new FormEncodingBuilder();

		builder.add("CoachID", CoachID+"");
		builder.add("qianming", qianming);
		builder.add("Key","4cf7a5f249f2eac8721c2cc882724e7001be8c119f5df53202443c1df25bb120618de0c29628f1ea7437b76404b0bd5a86107f6f309d441e3abf1bbf40b3cf37");	

		Request re = new Request.Builder().url(APIURL.UPDATECOACHQIANMING).post(builder.build()).build();
		
		mOkHttpClient.newCall(re).enqueue(new Callback() {
			
			@Override
			public void onResponse(Response arg0) throws IOException {
				try {
					JSONObject JSON=new JSONObject(arg0.body().string());
					if(JSON.optInt("Error_code")==0){
						
						handler.sendEmptyMessageDelayed(2, 1500);
					  }
					else{
						Message msg = new Message();
						msg.what = 1;
						msg.obj = "�޸�ʧ��!����������������!";
						handler.sendMessageDelayed(msg, 2000);
					}
					
					
				} catch (JSONException e) {
					e.printStackTrace();
					Message msg = new Message();
					msg.what = 3;
					msg.obj = e.toString();
					handler.sendMessageDelayed(msg, 2000);
					
				}	
			}
			@Override
			public void onFailure(Request arg0, IOException arg1) {
				Message msg = new Message();
				msg.what = 3;
				msg.obj = "��ȡʧ�ܣ�����������������!";
				handler.sendMessageDelayed(msg, 2000);
			}
		});
		
		
	}

	/**
	 * �ϴ�����ͷ��
	 * 
	 * @return ͼƬ64��������image ͼƬ���� �����Ǹ��û�
	 */
	public static void uploadCoachimg(String image, String imagename, String userid) {
		OkHttpClient mOkHttpClient = new OkHttpClient();
		FormEncodingBuilder builder = new FormEncodingBuilder();
		builder.add("image", image);// ��Ӽ�ֵ��
		builder.add("imagename", imagename);
		builder.add("id", userid);
		Request request1 = new Request.Builder().url(APIURL.UPLOADCOACHHEADIMG).post(builder.build())
				.build();
		mOkHttpClient.newCall(request1).enqueue(new Callback() {
			@Override
			public void onResponse(Response arg0) throws IOException {

				try {
					JSONObject JSON = new JSONObject(arg0.body().string());
					int code = JSON.optInt("Error_code");
					if (code == 0) {
						Log.v("uploadCoachimg", "ͼƬ�ϴ��ɹ�");
						// Utils.showToast(context, "�޸ĳɹ�!");
					} else
						Log.v("uploadCoachimg", "ͼƬ�ϴ�ʧ��" + JSON.optString("Error_info"));
					
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onFailure(Request arg0, IOException t) {
				Log.v("uploadUserimg", "ͼƬ�ϴ�ʧ��" + arg0.toString());

			}

		});

	}
	

	/*----------------------------��ʤ��У-������Ľӿ� e-------------------------*/
	/**
	 * ��������Ƿ����Ӳ��ҿ���!(���ǿ��Է��ʻ�����)
	 */

	public boolean isNetworkAvailable(Context context) {
		Log.v(TAG, TAG + "-isNetworkAvailable()");
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (info != null && info.isConnected()) {
				// ��ǰ���������ӵ�
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					// ��ǰ�����ӵ��������
					return true;
				}
			}
		}
		return false;
	}

}
