package com.example.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.example.json.entity.Posting;
import com.example.testpic.NoScrollGridView;
import com.example.utils.CollapsibleTextView;
import com.example.utils.NoDoubleClickListener;
import com.example.utils.Utils;
import com.example.xsjx.BBSdetailsActivity;
import com.example.xsjx.ImagePagerActivity;
import com.example.xsjx.R;
import com.example.xsjx.UserHomeActivity;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * ??????
 * 
 * @Description: TODO
 * @ClassName: BBSAdapter.java
 * @author ?????
 * @version V1.0
 * @Date 2018??1??11?? ????1:59:36
 */
public class PostingAdapter extends BaseAdapter {

	public List<Posting> list;
	public Context context;
   public int at;//?§Ø??????at ??????????? ???????????????
	public PostingAdapter(List<Posting> list, Context context,int at) {
		this.list = list;
		this.context = context;
		this.at=at;
	}

	@Override
	public int getCount() {

		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@SuppressLint({ "InflateParams", "ResourceAsColor" })
	@Override
	public View getView(int postion, View view, ViewGroup arg2) {
		HorldView h = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.fragment_4_lv_item, null);
			h = new HorldView(view);
			view.setTag(h);

		} else {
			h = (HorldView) view.getTag();
		}

		final Posting p = list.get(postion);

		// h.headview.setImageResource(R.drawable.log);//?????????Logo???
		// ?????????????????????¦Ë?????????

		if(p.identityID==2)
			x.image().bind(h.headview, p.headImg, Utils.XCoachCireHeadimg());
		
		else x.image().bind(h.headview, p.headImg, Utils.Xheadimg());
		
		
		h.username.setText(p.name);

		h.datetime.setText(p.date.substring(0, 16));

		h.title.setText(p.PostingTitle);

	//	h.conten.setText(p.PostingConten);
		
		h.conten.setFlag(false);
		h.conten.setDesc(p.PostingConten, BufferType.NORMAL);
		
	/*	
		if(at==0){
			//????????
			h.conten.setVisibility(View.GONE);
		}
		else h.conten.setVisibility(View.VISIBLE);
		
         */
		
		
		h.usertype.setText(p.identity);
		switch (p.identityID) {
		case 1:// ???????
			h.usertype.setText("????");
			h.usertype.setTextColor(h.usertype.getResources().getColor(R.color.black));
			break;
		case 2:
			// ????
			h.usertype.setText("????");
			h.usertype.setTextColor(h.usertype.getResources().getColor(R.color.green));
			break;
		case 3:
			// ??
			h.usertype.setText("??");
			h.usertype.setTextColor(h.usertype.getResources().getColor(R.color.yellow));
			break;
		case 4:
			// ?????
			h.usertype.setText("?????");
			h.usertype.setTextColor(h.usertype.getResources().getColor(R.color.red));
			break;
		case 5:
			// ?¦Ï?
			h.usertype.setText("?¦Ï?");
			h.usertype.setTextColor(h.usertype.getResources().getColor(R.color.blue));
			break;
		case 6:
			// ?????
			h.usertype.setText("?????");
			h.usertype.setTextColor(h.usertype.getResources().getColor(R.color.red));
			break;
		default:
			// ????¦Ï?
			h.usertype.setText("?¦Ï?");
			h.usertype.setTextColor(h.usertype.getResources().getColor(R.color.blue));
			break;
		}

		// ??
		h.gv.setSelector(new ColorDrawable(Color.TRANSPARENT));
		if (p.PostingImg.size() > 0) {
			h.gv.setVisibility(View.VISIBLE);
			h.gv.setAdapter(new ImgGvAdapter(context, p.PostingImg));

		} else
			h.gv.setVisibility(View.GONE);

		h.bbslayou.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {

				/*Utils.showToast(context, "???????????");
				
				 ((MainActivity) context).intentBBS(p);*/
				 
				    Intent bbsintent = new Intent(context, BBSdetailsActivity.class);
				   
					bbsintent.putExtra("PostingID", p.PostingID);
					bbsintent.putExtra("PostingTitle",p.PostingTitle);
					bbsintent.putExtra("PostingConten", p.PostingConten);
					bbsintent.putExtra("date", p.date);
					bbsintent.putExtra("identityID", p.identityID);
					bbsintent.putExtra("identity", p.identity);
					bbsintent.putExtra("typesID", p.typesID);
					bbsintent.putExtra("name", p.name);
					bbsintent.putExtra("headImg", p.headImg);
				
					bbsintent.putStringArrayListExtra("PostingImg", p.PostingImg);
					
					context.startActivity(bbsintent);

				 
				 
				
			}
		});

	h.headview.setOnClickListener(new NoDoubleClickListener() {
			@Override
			public void onNoDoubleClick(View v) {

				//Utils.showToast(context, "????????");
				
				switch (p.identityID) {
				case 2:
					//????????????
					/*
					Intent coach=new Intent(context,CoachInfoActivity.class);
					coach.putExtra("coachid", p.typesID);
					context.startActivity(coach);
                           */
					Utils.showToast(context, "Sorry,????????????????!");
					
					break;
				case 3:
                   //???????
					Intent userhome=new Intent(context,UserHomeActivity.class);
					userhome.putExtra("username", p.name);
					userhome.putExtra("identityID", p.identityID);
					userhome.putExtra("userid", p.typesID);
					userhome.putExtra("headimg", p.headImg);
					context.startActivity(userhome);
					break;
				case 5:
					//?¦Ï?
					Intent userhome5=new Intent(context,UserHomeActivity.class);
					userhome5.putExtra("username", p.name);
					userhome5.putExtra("identityID", p.identityID);
					userhome5.putExtra("userid", p.typesID);
					userhome5.putExtra("headimg", p.headImg);
					context.startActivity(userhome5);
					break;
				case 6:
              //??????
					Intent userhome6=new Intent(context,UserHomeActivity.class);
					userhome6.putExtra("username", p.name);
					userhome6.putExtra("identityID", p.identityID);
					userhome6.putExtra("userid", p.typesID);
					userhome6.putExtra("headimg", p.headImg);
					context.startActivity(userhome6);
					break;
            	default:
            		Utils.showToast(context, "??????????????!");
					break;
				}
				
				
			

			}
		});

		// ????????????????
		h.gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				imageBrower(position, p.PostingImg);
			}
		});

		return view;
	}

	/**
	 *
	 * 
	 * @param position
	 */
	protected void imageBrower(int position, ArrayList<String> urls2) {
		Intent intent = new Intent(context, ImagePagerActivity.class);
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);// ??URL
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);// ?????
		context.startActivity(intent);
	}

	class HorldView {
		public LinearLayout bbslayou;
		public ImageView headview;
		public TextView username, usertype, datetime, title;//,conten;
		public CollapsibleTextView conten;
		public NoScrollGridView gv;

		public HorldView(View view) {
			this.bbslayou = (LinearLayout) view.findViewById(R.id.BBS_layou);
			this.headview = (ImageView) view.findViewById(R.id.fragment4_lv_CiecleImg);
   
			this.username = (TextView) view.findViewById(R.id.fragment4_lv_username);
			this.usertype = (TextView) view.findViewById(R.id.fragment4_lv_usertypr);
			this.datetime = (TextView) view.findViewById(R.id.fragment4_lv_datetime);
			this.title = (TextView) view.findViewById(R.id.fragment4_lv_tile);
			this.conten=(CollapsibleTextView) view.findViewById(R.id.fragment4_lv_conten);
			this.gv = (NoScrollGridView) view.findViewById(R.id.fragment4_lv_GridView);
		}

	}

}
