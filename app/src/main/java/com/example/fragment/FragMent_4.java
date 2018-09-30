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
 * @author 蒋团圆 论坛模块对应的Fragment
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
		Log.v("Fragment", "fragment4创建");
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
		// 设置手指在屏幕下拉多少距离会触发下拉刷新
		
		// 设定下拉圆圈的背景
		swipeLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);

		// 设置圆圈的大小
		swipeLayout.setSize(SwipeRefreshLayout.LARGE);
		swipeLayout.setDistanceToTriggerSync(200);
		swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {	
			@Override
			public void onRefresh() {
				
				if(sefx==0){
					checkRB(0);
			     swipeLayout.setRefreshing(true);
			     //访问第一页数据
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
		
	
		adapter = new PostingAdapter(Plist, getActivity(),0);//0 隐藏内容
		
		lv.setAdapter(adapter);
		lv.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
			/*	Toast.makeText(getActivity(), "上拉刷新", 1).show();
				closelv();*/
				//lv.onRefreshComplete();
				if(!max)
				{
					checkRB(0);
					AccessInternet.getPosting(handler, pageNo);}
				else{
					Utils.showToast(getActivity(), "已经加载完了哟!");
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
		          //当ListView 滚到就顶部 就设置可以下拉刷新
		            swipeLayout.setEnabled(true);
		            sefx=0;
   
		        } else if ((firstVisibleItem + visibleItemCount) == totalItemCount) {  
		        	  //当ListView 滚到就底部  就设置不可以下拉刷新
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
					// 全部
					showAllPosting();

					break;
				case R.id.fragment_4_Rb2:
					// 学员
					showStudenPosting();

					break;
				case R.id.fragment_4_Rb3:
					// 教练
					showCoachPosting();

					break;
				case R.id.fragment_4_Rb4:

					// 游客
					showYoukePosting();

					break;
				case R.id.fragment_4_Rb5:
					// 匿名
					showNmPosting();

					break;
				case R.id.fragment_4_Rb6:
					// 其他

					showQitaPosting();
					break;
				case R.id.fragment_4_Rb7:
					// 我的
					showMyPosting();
					break;
				}

			}

		});

	}
	private void checkRB(int i){
		switch (i) {
		case 0:
			// 全部
			RG.check(R.id.fragment_4_Rb1);

			break;
		case 1:
			// 学员
			RG.check(R.id.fragment_4_Rb2);
			break;
		case 2:
			// 教练
			RG.check(R.id.fragment_4_Rb3);
			break;
		case 3:

			// 游客
			RG.check(R.id.fragment_4_Rb4);

			break;
		case 4:
			// 匿名
			RG.check(R.id.fragment_4_Rb5);

			break;
		case 5:
			// 其他

			RG.check(R.id.fragment_4_Rb6);
			break;
		case 6:
			// 我的
			RG.check(R.id.fragment_4_Rb7);
			break;
		}
		
	}
	
	/**
	 * 分类封装数据
	 */
	List<Posting> p=new ArrayList<Posting>();//全部发帖
	List<Posting> p1=new ArrayList<Posting>();//匿名用户
	List<Posting> p2=new ArrayList<Posting>();//教练
	List<Posting> p3=new ArrayList<Posting>();//学员
	List<Posting> p4=new ArrayList<Posting>();//其他（管理员啊 拿证的啊）
	List<Posting> p5=new ArrayList<Posting>();//游客
	List<Posting> p6=new ArrayList<Posting>();//我的发帖
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
			case 1://匿名用户
				p1.add(a);
				break;
			case 2://教练
				 p2.add(a);
				 if(CoachInfo.id==a.typesID)
					{
					 p6.add(a);	
					}
				 
				 
				break;
			case 3://学员
				p3.add(a);
				
				if(UserInfo.IdentityID==3 && UserInfo.UserID.equals(a.typesID+""))
				{
					p6.add(a);	
				}
				
				break;
			case 4://管理员
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
	 * 显示全部发帖
	 */
	private void showAllPosting() {
		Plist.clear();
		Plist.addAll(p);
		adapter.notifyDataSetChanged();
		
	}
	/**
	 * 显示学员发帖
	 */
	private void showStudenPosting() {
		Plist.clear();
		Plist.addAll(p3);
		adapter.notifyDataSetChanged();
	}
	/**
	 * 显示教练发帖
	 */
	private void showCoachPosting() {
		Plist.clear();
		Plist.addAll(p2);
		adapter.notifyDataSetChanged();
		
	}
	/**
	 * 显示游客发帖
	 */
	private void showYoukePosting() {
		Plist.clear();
		Plist.addAll(p5);
		adapter.notifyDataSetChanged();
	}
	/**
	 * 显示匿名发帖
	 */
	private void showNmPosting() {
		Plist.clear();
		Plist.addAll(p1);
		adapter.notifyDataSetChanged();
		
	}
	
	/**
	 * 显示管理员发帖
	 */
	private void showQitaPosting() {
		Plist.clear();
		Plist.addAll(p4);
		adapter.notifyDataSetChanged();
		
	}
	/**
	 * 显示我的发帖
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
				Utils.showToast(getActivity(), "刷新成功!");
				sw=1;
				max=false;
				 nodate.setVisibility(View.GONE);
				Ptype();
				break;

            case 1://接收错误
				String cy = msg.obj + "";
				if (cy.equals("没有此页!")) {
					Utils.showToast(getActivity(), "已经加载完了哟!");
					lv.onRefreshComplete();
					max = true;
				}

				else if (cy.equals("论坛关闭!")) {
					
					nodate.setVisibility(View.VISIBLE);
				} else
					Utils.showToast(getActivity(), cy + "");

				swipeLayout.setRefreshing(false);
				lv.onRefreshComplete();
				break;
            case 2://关闭下拉刷新的框
            	  swipeLayout.setRefreshing(false);
   
            	break;
            case 3://上拉刷新
				List<Posting> slp=(List<Posting>) msg.obj;
				for(Posting a:slp){
					Plist.add(a);
				}
				Ptype();
            	adapter.notifyDataSetChanged();
            	Utils.showToast(getActivity(),"更新了"+slp.size()+"条数据!");
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
