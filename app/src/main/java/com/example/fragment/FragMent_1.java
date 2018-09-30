package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;

import com.example.adapter.MessageAdapter;
import com.example.adapter.VpAdapter;
import com.example.dao.NewsDo;
import com.example.entity.Fragmen1_MessageEntity;
import com.example.internet.AccessInternet;
import com.example.internet.NetConn;
import com.example.json.entity.SchoolNewsEntity;
import com.example.json.entity.SchoolTopImgEntity;
import com.example.utils.Filepath;
import com.example.utils.NoDoubleClickListener;
import com.example.utils.SharedUtils;
import com.example.utils.UrlGoImg;
import com.example.utils.Utils;
import com.example.xsjx.MainApplication;
import com.example.xsjx.R;
import com.example.xsjx.WebActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class FragMent_1 extends Fragment {
	public RadioGroup Rg;
	public ViewPager vp;
	public List<View> imglist = new ArrayList<View>();
	LayoutInflater inflater;
    public NewsDo newsdao;
    
    //ͷ����img����
    public VpAdapter vpadapter;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View content = inflater.inflate(R.layout.fragment_1, container, false);
		Log.v("Fragment", "fragment1����");
		initView(content);
		return content;
	}

	public PullToRefreshListView lv;
	public List<SchoolNewsEntity> newslist = new ArrayList<SchoolNewsEntity>();
	public MessageAdapter messageAdapter;

	public void initView(View view) {
		
		newsdao=new NewsDo(getActivity());
	
		vp = (ViewPager) view.findViewById(R.id.fragmen_1_ViewPager);
		Rg = (RadioGroup) view.findViewById(R.id.fragment_1_RG);
		inflater = LayoutInflater.from(getActivity());
		
		getSchoolTopImg();

		vp.addOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				setRgPosition(arg0);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		vpadapter = new VpAdapter(getActivity(), imglist);
		vp.setAdapter(vpadapter);
		
		//startvp(); ��ʱ�������ֲ�

		lv = (PullToRefreshListView) view.findViewById(R.id.fragment_1_lv);
		lv.setMode(Mode.PULL_FROM_END);// PULL_FROM_START PULL_FROM_END
		
		
		messageAdapter = new MessageAdapter(newslist, getActivity());
		lv.setAdapter(messageAdapter);
		getNewsList();
		
		lv.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				getNewsList();

			}
		});

	}

	/**
	 * ��ȡ�ֲ�ͼƬ����
	 */
	List<SchoolTopImgEntity> TopImglist=new ArrayList<SchoolTopImgEntity>();

	public void getSchoolTopImg() {
		AccessInternet.SchoolTopImgServlet(fragment2Handler);
	}

	public void setXTopImg(){
		for (final SchoolTopImgEntity b : TopImglist) {
		   View view = inflater.inflate(R.layout.fragment_1_topimg, null);
		   ImageView viewimg = (ImageView) view.findViewById(R.id.fragment_1_topimg_img);
		   viewimg.setMaxWidth(MainApplication.phoneWidth);
		   
		   x.image().bind(viewimg,b.ImgUrl,Utils.XShcoolTopimg());
		   
		   
		   view.setOnClickListener(new NoDoubleClickListener() {
				@Override
				public void onNoDoubleClick(View v) {
					Intent intent = new Intent(getActivity(), WebActivity.class);
					intent.putExtra("Url", b.WebUrl);
					intent.putExtra("Webname", "У����Ѷ ");
					startActivity(intent);
				}

			});
		   imglist.add(view);
		   
		   }
		vpadapter.notifyDataSetChanged();
		startvp(); 
		
	}
	/**
	 * ��������ص�ͷ�����
	 */
	public void setNonetXTopImg(){
		for (int i=0;i<4;i++) {
		   View view = inflater.inflate(R.layout.fragment_1_topimg, null);
		   ImageView viewimg = (ImageView) view.findViewById(R.id.fragment_1_topimg_img);
		   viewimg.setMaxWidth(MainApplication.phoneWidth);
		   
		   x.image().bind(viewimg,"null",Utils.XShcoolTopimg());
		   
		   
		   view.setOnClickListener(new NoDoubleClickListener() {
				@Override
				public void onNoDoubleClick(View v) {
					Intent intent = new Intent(getActivity(), WebActivity.class);
					intent.putExtra("Url", MainApplication.XSJX_jianjie);
					intent.putExtra("Webname", "У����Ѷ ");
					startActivity(intent);
				}

			});
		   imglist.add(view);
		  }
		
		vpadapter.notifyDataSetChanged();
		startvp(); 
	}
	
	
	
	/**
	 * �����ֲ�ͼƬ���� ���� ��λ��˳��
	 */
	public void setSchoolTopImg() {

		// ����ܷ��ʵ��ֲ�ͼƬ�ӿ� ��ô�ñ���ͼƬ������ ���������������ӻ��߶���������±��ز鿴

		for (SchoolTopImgEntity c : TopImglist) {
			String imgname = Utils.isUrlFileName(c.ImgUrl);
			SharedUtils.getInstance().putString("SchoolTopImgName" + c.tf, imgname);
			SharedUtils.getInstance().putString("SchoolTopWebUrl" + c.tf, c.WebUrl);
		}
		// ����
		for (final SchoolTopImgEntity b : TopImglist) {

			View view = inflater.inflate(R.layout.fragment_1_topimg, null);
			ImageView viewimg = (ImageView) view.findViewById(R.id.fragment_1_topimg_img);
			viewimg.setMaxWidth(MainApplication.phoneWidth);

			String imgname = Utils.isUrlFileName(b.ImgUrl);
			if (imgname != null) {
				Bitmap bitmap = BitmapFactory.decodeFile(Filepath.imgPath + "/" + imgname);
				if (bitmap != null) {
					viewimg.setImageBitmap(bitmap);
					view.setOnClickListener(new NoDoubleClickListener() {
						@Override
						public void onNoDoubleClick(View v) {
							Intent intent = new Intent(getActivity(), WebActivity.class);
							intent.putExtra("Url", b.WebUrl);
							intent.putExtra("Webname", "У����Ѷ ");
							startActivity(intent);
						}

					});

				} else {
					Utils.ImgDoload(b.ImgUrl, Filepath.imgPath + "/" + imgname);

					viewimg.setImageResource(R.drawable.scholltopimg);
					view.setOnClickListener(new NoDoubleClickListener() {
						@Override
						public void onNoDoubleClick(View v) {
							Intent intent = new Intent(getActivity(), WebActivity.class);
							intent.putExtra("Url", MainApplication.XSJX_jianjie);
							intent.putExtra("Webname", "У����Ѷ ");
							startActivity(intent);
						}

					});
				}
			} else {
				viewimg.setImageResource(R.drawable.scholltopimg);
				view.setOnClickListener(new NoDoubleClickListener() {
					@Override
					public void onNoDoubleClick(View v) {
						Intent intent = new Intent(getActivity(), WebActivity.class);
						intent.putExtra("Url", MainApplication.XSJX_jianjie);
						intent.putExtra("Webname", "У����Ѷ ");
						startActivity(intent);
					}

				});
			}

			imglist.add(view);
		}

		VpAdapter adapter1 = new VpAdapter(getActivity(), imglist);
		vp.setAdapter(adapter1);
		startvp();
	}

	// �������������ҵ��ϴα����IMG���ֽ�������
	public void setNoInterTopImg() {
		for (int i = 1; i <= 4; i++) {
			View view = inflater.inflate(R.layout.fragment_1_topimg, null);
			ImageView viewimg = (ImageView) view.findViewById(R.id.fragment_1_topimg_img);
			viewimg.setMaxWidth(MainApplication.phoneWidth);
			String imgname = SharedUtils.getInstance().getString("SchoolTopImgName" + i);
			final String imgWebUrl = SharedUtils.getInstance().getString("SchoolTopWebUrl" + i);
			if (imgname != null) {
				Bitmap bitmap = BitmapFactory.decodeFile(Filepath.imgPath + "/" + imgname);
				if (bitmap != null) {
					viewimg.setImageBitmap(bitmap);
					view.setOnClickListener(new NoDoubleClickListener() {
						@Override
						public void onNoDoubleClick(View v) {
							Intent intent = new Intent(getActivity(), WebActivity.class);
							intent.putExtra("Url", imgWebUrl);
							intent.putExtra("Webname", "У����Ѷ ");
							startActivity(intent);
						}
					});

				} else {
					viewimg.setImageResource(R.drawable.scholltopimg);
					view.setOnClickListener(new NoDoubleClickListener() {
						@Override
						public void onNoDoubleClick(View v) {
							Intent intent = new Intent(getActivity(), WebActivity.class);
							intent.putExtra("Url", MainApplication.XSJX_jianjie);
							intent.putExtra("Webname", "У����Ѷ ");
							startActivity(intent);
						}

					});

				}

			} else {
				viewimg.setImageResource(R.drawable.scholltopimg);
				view.setOnClickListener(new NoDoubleClickListener() {
					@Override
					public void onNoDoubleClick(View v) {
						Intent intent = new Intent(getActivity(), WebActivity.class);
						intent.putExtra("Url", MainApplication.XSJX_jianjie);
						intent.putExtra("Webname", "У����Ѷ ");
						startActivity(intent);
					}

				});

			}
			imglist.add(view);
		}

		VpAdapter adapter1 = new VpAdapter(getActivity(), imglist);
		vp.setAdapter(adapter1);
		startvp();

	}

	Handler fragment2Handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			// �����ֲ�ͼƬ��JSONֵ
			case 0:
				TopImglist.clear();
				TopImglist.addAll((List<SchoolTopImgEntity>) msg.obj);
				
				//setXTopImg(); x
				
				// �õ�ͼƬ�ֲ����� �������غ�����
				setSchoolTopImg(); //1.�Լ�д�ķ���
				
				break;
			// ����JSON����ֵ�Ĵ���!
			case 2:
				Toast.makeText(getActivity(), msg.obj + "", 1).show();
				setNoInterTopImg();// ���������������»�ȡ�ϴα����ͼƬ
				//setNonetXTopImg(); x
				
				break;
			// ͼƬ�ֲ��õ�
			case 1:
				vp.setCurrentItem(msg.arg1);
				setRgPosition(msg.arg1);
				break;
			// ��ȡ������Ѷ�ɹ�ʹ��
			case 3:
				List<SchoolNewsEntity> hlisi = (List<SchoolNewsEntity>) msg.obj;
				for (SchoolNewsEntity a : hlisi) {
			      if(newsdao.selectID(a.id+""))
					{
				     newsdao.AddNews(a.id, a.newsTitle, a.newsContent, a.newsUrl, a.newsAuthor, a.newsDate, a.newsUpdate);
					}
                   newslist.add(a);
					
				}
				pageNo++;
				coloselvComplete();
				break;
			// ��ȡ������Ѷʧ��ʹ��
			case 4:
		
				Toast.makeText(getActivity(), msg.obj + "", 1).show();
				coloselvComplete();
				break;
				
			default:
				break;
			}
		}

	};
	

	// �ر�ˢ�½���
	private void coloselvComplete() {

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				
				messageAdapter.notifyDataSetChanged();
				lv.onRefreshComplete();// ����ɹ�֮��2���رյ�ˢ�µĽ���...
				
			}
		}, 2000);
	}

	int pageNo = 1; // ��һҳ

	// �����������
	public void getNewsList() {
		//����ܷ������� ��ô�÷��ʷ���������
		//������ܷ������� �ʹӱ��ػ����ȡ��������
		if(NetConn.isNetworkAvailable(getActivity()))
		 AccessInternet.SchoolNewsServlet(fragment2Handler, pageNo);
	else {
			newslist.clear();
			newslist.addAll(newsdao.getnewsAll());
			pageNo=(newslist.size()/5)+1;
			messageAdapter.notifyDataSetChanged();
			coloselvComplete();
			Toast.makeText(getActivity(), "�޷��������磬����������������!", 1).show();
			
		}
	}

	/*
	 * ����vp�İ�ťѡ��״̬
	 */
	public void setRgPosition(int position) {
		RadioButton rb = (RadioButton) Rg.getChildAt(position);
		rb.setChecked(true);
	}

	/*
	 * �������߳� ����vp�Ķ����ֲ�
	 */
	public void startvp() {
		new Thread() {
			public void run() {
				try {
					// �ӳ�4������ ����run����
					Thread.sleep(4000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				while (true) {
					try {
						int i = vp.getCurrentItem();
						switch (i) {
						case 0:
							Message msg = new Message();
							msg.what = 1;
							msg.arg1 = 1;
							fragment2Handler.sendMessage(msg);
							break;
						case 1:
							Message msg1 = new Message();
							msg1.what = 1;
							msg1.arg1 = 2;
							fragment2Handler.sendMessage(msg1);
							break;
						case 2:
							Message msg2 = new Message();
							msg2.what = 1;
							msg2.arg1 = 3;
							fragment2Handler.sendMessage(msg2);
							break;
						case 3:
							Message msg3 = new Message();
							msg3.what = 1;
							msg3.arg1 = 0;
							fragment2Handler.sendMessage(msg3);
							break;
						}
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

				}

			}

		}.start();

	}

}
