package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.Coach.Utils.CoachInfo;
import com.example.adapter.BBSAdapter;
import com.example.adapter.PostingAdapter;
import com.example.entity.Fragment4_BBS_Entity;
import com.example.internet.AccessInternet;
import com.example.internet.NetConn;
import com.example.json.entity.Posting;
import com.example.testpic.PublishedActivity;
import com.example.utils.NoDoubleClickListener;
import com.example.utils.SharedUtils;
import com.example.utils.UserInfo;
import com.example.utils.Utils;
import com.example.xsjx.LogoActivity;
import com.example.xsjx.MainActivity;
import com.example.xsjx.PostingActivity;
import com.example.xsjx.PostingAlbumActivity;
import com.example.xsjx.R;
import com.example.xsjx.RunActivity;
import com.example.xsjx.StartActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.squareup.okhttp.internal.Util;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

/**
 * 
 * @author ����Բ ��̳ģ���Ӧ��Fragment
 */
                                                                @SuppressWarnings("unused")
public class FragMent_4 extends Fragment {

	public Button posting;
	public PullToRefreshListView lv;
	public List<Fragment4_BBS_Entity> BBSlist = new ArrayList<Fragment4_BBS_Entity>();
	public PostingAdapter adapter;
	public RadioGroup RG;
	private SwipeRefreshLayout swipeLayout;
	public List<Posting> Plist=new ArrayList<Posting>();
	
	private TextView nodate;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View content = inflater.inflate(R.layout.fragment_4, container, false);
		Log.v("Fragment", "fragment4����");
		initView(content);
		return content;
	}
    int sefx=1;
	private void initView(View view) {
		nodate=(TextView) view.findViewById(R.id.fragment_4_view_nodata);
		
		posting=(Button) view.findViewById(R.id.fragment_4_Posting);
		posting.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {
			  //  Intent in=new Intent(getActivity(),PostingActivity.class);
			//Intent in=new Intent(getActivity(),PostingAlbumActivity.class);
			Intent in=new Intent(getActivity(),PublishedActivity.class);
				startActivity(in);
			}
		});
		
		
		
		swipeLayout=(SwipeRefreshLayout) view.findViewById(R.id.fragment_4_swipeLayout);
		swipeLayout.setColorSchemeColors(Color.BLACK, Color.BLUE);
		// ������ָ����Ļ�������پ���ᴥ������ˢ��
		
		// �趨����ԲȦ�ı���
		swipeLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);

		// ����ԲȦ�Ĵ�С
		swipeLayout.setSize(SwipeRefreshLayout.LARGE);
		swipeLayout.setDistanceToTriggerSync(200);
		swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {	
			@Override
			public void onRefresh() {
				
				if(sefx==0){
					checkRB(0);
			     swipeLayout.setRefreshing(true);
			     //���ʵ�һҳ����
			     AccessInternet.getPosting(handler, 1);
			     pageNo=2;
			     
				 //handler.sendEmptyMessageDelayed(2, 3000);
				}
				else{
					 swipeLayout.setRefreshing(false);
				}
			}
		});
		
		
		lv = (PullToRefreshListView) view.findViewById(R.id.fragment_4_lv);
		lv.setMode(Mode.PULL_FROM_END);
		
	
		adapter = new PostingAdapter(Plist, getActivity(),0);//0 ��������
		
		lv.setAdapter(adapter);
		lv.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			/*	Toast.makeText(getActivity(), "����ˢ��", 1).show();
				closelv();*/
				//lv.onRefreshComplete();
				if(!max)
				{
					checkRB(0);
					AccessInternet.getPosting(handler, pageNo);}
				else{
					Utils.showToast(getActivity(), "�Ѿ���������Ӵ!");
					lv.onRefreshComplete();
				}

			}
		});
		
		lv.setOnScrollListener(new AbsListView.OnScrollListener() {  
		    @Override  
		    public void onScrollStateChanged(AbsListView view, int scrollState) {  
		    }  
		  
		  
		    @Override  
		    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {  
		        if (firstVisibleItem == 0) {  
		          //��ListView �����Ͷ��� �����ÿ�������ˢ��
		            swipeLayout.setEnabled(true);
		            sefx=0;
   
		        } else if ((firstVisibleItem + visibleItemCount) == totalItemCount) {  
		        	  //��ListView �����͵ײ�  �����ò���������ˢ��
		            swipeLayout.setEnabled(false);
		            sefx=1;
		            
		            //swipeLayout.setRefreshing(false);
		        }  
		        else {
		        	sefx=1;        
		        	swipeLayout.setEnabled(false);
		        }
		    }  
		});  
		AccessInternet.getPosting(handler, 1);

		RG = (RadioGroup) view.findViewById(R.id.fragment_4_RG);
		
		RG.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@SuppressLint("NewApi")
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				switch (arg1) {
				case R.id.fragment_4_Rb1:
					// ȫ��
					showAllPosting();

					break;
				case R.id.fragment_4_Rb2:
					// ѧԱ
					showStudenPosting();

					break;
				case R.id.fragment_4_Rb3:
					// ����
					showCoachPosting();

					break;
				case R.id.fragment_4_Rb4:

					// �ο�
					showYoukePosting();

					break;
				case R.id.fragment_4_Rb5:
					// ����
					showNmPosting();

					break;
				case R.id.fragment_4_Rb6:
					// ����

					showQitaPosting();
					break;
				case R.id.fragment_4_Rb7:
					// �ҵ�
					showMyPosting();
					break;
				}

			}

		});

	}
	private void checkRB(int i){
		switch (i) {
		case 0:
			// ȫ��
			RG.check(R.id.fragment_4_Rb1);

			break;
		case 1:
			// ѧԱ
			RG.check(R.id.fragment_4_Rb2);
			break;
		case 2:
			// ����
			RG.check(R.id.fragment_4_Rb3);
			break;
		case 3:

			// �ο�
			RG.check(R.id.fragment_4_Rb4);

			break;
		case 4:
			// ����
			RG.check(R.id.fragment_4_Rb5);

			break;
		case 5:
			// ����

			RG.check(R.id.fragment_4_Rb6);
			break;
		case 6:
			// �ҵ�
			RG.check(R.id.fragment_4_Rb7);
			break;
		}
		
	}
	
	/**
	 * �����װ����
	 */
	List<Posting> p=new ArrayList<Posting>();//ȫ������
	List<Posting> p1=new ArrayList<Posting>();//�����û�
	List<Posting> p2=new ArrayList<Posting>();//����
	List<Posting> p3=new ArrayList<Posting>();//ѧԱ
	List<Posting> p4=new ArrayList<Posting>();//����������Ա�� ��֤�İ���
	List<Posting> p5=new ArrayList<Posting>();//�ο�
	List<Posting> p6=new ArrayList<Posting>();//�ҵķ���
	private void Ptype(){
		p.clear();
		p.addAll(Plist);
		
		p1.clear();
		p2.clear();
		p3.clear();
		p4.clear();
		p5.clear();
		p6.clear();
		
	for (Posting a : Plist) {
			switch (a.identityID) {
			case 1://�����û�
				p1.add(a);
				break;
			case 2://����
				 p2.add(a);
				 if(CoachInfo.id==a.typesID)
					{
					 p6.add(a);	
					}
				 
				 
				break;
			case 3://ѧԱ
				p3.add(a);
				
				if(UserInfo.IdentityID==3 && UserInfo.UserID.equals(a.typesID+""))
				{
					p6.add(a);	
				}
				
				break;
			case 4://����Ա
				p4.add(a);
				
				if(UserInfo.IdentityID==4 && UserInfo.UserID.equals(a.typesID+""))
				{
					p6.add(a);	
				}
				
				break;
			case 5:
				p5.add(a);
				
				if(UserInfo.IdentityID==5 && UserInfo.UserID.equals(a.typesID+""))
				{
					p6.add(a);	
				}
				
				break;
			case 6:	
				p4.add(a);
				if(UserInfo.IdentityID==6 && UserInfo.UserID.equals(a.typesID+""))
				{
					p6.add(a);	
				}
				
			default:
				break;
			}
	
    
    }
		
	}
	
	/**
	 * ��ʾȫ������
	 */
	private void showAllPosting() {
		Plist.clear();
		Plist.addAll(p);
		adapter.notifyDataSetChanged();
		
	}
	/**
	 * ��ʾѧԱ����
	 */
	private void showStudenPosting() {
		Plist.clear();
		Plist.addAll(p3);
		adapter.notifyDataSetChanged();
	}
	/**
	 * ��ʾ��������
	 */
	private void showCoachPosting() {
		Plist.clear();
		Plist.addAll(p2);
		adapter.notifyDataSetChanged();
		
	}
	/**
	 * ��ʾ�οͷ���
	 */
	private void showYoukePosting() {
		Plist.clear();
		Plist.addAll(p5);
		adapter.notifyDataSetChanged();
	}
	/**
	 * ��ʾ��������
	 */
	private void showNmPosting() {
		Plist.clear();
		Plist.addAll(p1);
		adapter.notifyDataSetChanged();
		
	}
	
	/**
	 * ��ʾ����Ա����
	 */
	private void showQitaPosting() {
		Plist.clear();
		Plist.addAll(p4);
		adapter.notifyDataSetChanged();
		
	}
	/**
	 * ��ʾ�ҵķ���
	 */
	private void showMyPosting() {
		Plist.clear();
		Plist.addAll(p6);
		adapter.notifyDataSetChanged();
	}
	

	
	int pageNo=2;
	boolean max=false;
	int sw=0;
	Handler handler=new Handler(){
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg){
			switch (msg.what) {
			case 0:
				Plist.clear();
				Plist.addAll((List<Posting>) msg.obj);
			
				adapter.notifyDataSetChanged();
				swipeLayout.setRefreshing(false);
				if(sw!=0)
				Utils.showToast(getActivity(), "ˢ�³ɹ�!");
				sw=1;
				max=false;
				 nodate.setVisibility(View.GONE);
				Ptype();
				break;

            case 1://���մ���
				String cy = msg.obj + "";
				if (cy.equals("û�д�ҳ!")) {
					Utils.showToast(getActivity(), "�Ѿ���������Ӵ!");
					lv.onRefreshComplete();
					max = true;
				}

				else if (cy.equals("��̳�ر�!")) {
					
					nodate.setVisibility(View.VISIBLE);
				} else
					Utils.showToast(getActivity(), cy + "");

				swipeLayout.setRefreshing(false);
				lv.onRefreshComplete();
				break;
            case 2://�ر�����ˢ�µĿ�
            	  swipeLayout.setRefreshing(false);
   
            	break;
            case 3://����ˢ��
				List<Posting> slp=(List<Posting>) msg.obj;
				for(Posting a:slp){
					Plist.add(a);
				}
				Ptype();
            	adapter.notifyDataSetChanged();
            	Utils.showToast(getActivity(),"������"+slp.size()+"������!");
                pageNo++;
               
                nodate.setVisibility(View.GONE);
                lv.onRefreshComplete();
            	break;	
			default:
				break;
			}
		}
	
	};	

}
