package com.example.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.json.entity.PComments;
import com.example.utils.Utils;
import com.example.xsjx.R;
import com.example.xsjx.UserHomeActivity;

import org.xutils.x;

import java.util.List;

public class BBSDisAdapter extends BaseAdapter {

	public List<PComments> list;
	public Context context;

	public BBSDisAdapter(List<PComments> list, Context context) {
		this.list = list;
		this.context = context;

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
			view = LayoutInflater.from(context).inflate(R.layout.bbsdis_lv_item, null);
			h = new HorldView(view);
			view.setTag(h);

		} else {
			h = (HorldView) view.getTag();
		}

		final PComments bbs = list.get(postion);

		// h.headview.setImageResource(R.drawable.log);

		//x.image().bind(h.headview, bbs.headImg, Utils.Xheadimg());
		
		if(bbs.identityID==2)
			x.image().bind(h.headview, bbs.headImg, Utils.XCoachCireHeadimg());
		
		else x.image().bind(h.headview, bbs.headImg, Utils.Xheadimg());
		
		
		h.headview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				switch (bbs.identityID) {
				case 2:
					Utils.showToast(context, "无法查看该用户资料~!");
					break;
				case 3:

					Intent userhome = new Intent(context, UserHomeActivity.class);
					userhome.putExtra("username", bbs.name);
					userhome.putExtra("identityID", bbs.identityID);
					userhome.putExtra("userid", bbs.typesID);
					userhome.putExtra("headimg", bbs.headImg);
					context.startActivity(userhome);
					break;
				case 5:

					Intent userhome5 = new Intent(context, UserHomeActivity.class);
					userhome5.putExtra("username", bbs.name);
					userhome5.putExtra("identityID", bbs.identityID);
					userhome5.putExtra("userid", bbs.typesID);
					userhome5.putExtra("headimg", bbs.headImg);
					context.startActivity(userhome5);
					break;
				case 6:

					Intent userhome6 = new Intent(context, UserHomeActivity.class);
					userhome6.putExtra("username", bbs.name);
					userhome6.putExtra("identityID", bbs.identityID);
					userhome6.putExtra("userid", bbs.typesID);
					userhome6.putExtra("headimg", bbs.headImg);
					context.startActivity(userhome6);
					break;
				default:
					Utils.showToast(context, "无法查看该用户资料~!");
					break;
				}
			}
		});

		h.bbsdisname.setText(bbs.name);
		// h.bbsdisname.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);����»���
		
		h.bbsdistype.setText(bbs.identity);

		switch (bbs.identityID) {
		case 1:
			h.bbsdistype.setText("匿名");
			h.bbsdistype.setTextColor(h.bbsdistype.getResources().getColor(R.color.black));
			break;
		case 2:

			h.bbsdistype.setText("教练");
			h.bbsdistype.setTextColor(h.bbsdistype.getResources().getColor(R.color.green));
			break;
		case 3:

			h.bbsdistype.setText("学员");
			h.bbsdistype.setTextColor(h.bbsdistype.getResources().getColor(R.color.yellow));
			break;
		case 4:

			h.bbsdistype.setText("管理员");
			h.bbsdistype.setTextColor(h.bbsdistype.getResources().getColor(R.color.red));
			break;
		case 5:

			h.bbsdistype.setText("游客");
			h.bbsdistype.setTextColor(h.bbsdistype.getResources().getColor(R.color.blue));
			break;

		case 6:
			h.bbsdistype.setText("已拿证");
			h.bbsdistype.setTextColor(h.bbsdistype.getResources().getColor(R.color.red));
			break;
		default:

			h.bbsdistype.setText("�ο�");
			h.bbsdistype.setTextColor(h.bbsdistype.getResources().getColor(R.color.blue));
			break;
		}

		h.bbsdistime.setText(bbs.date.substring(0, 16));
		h.bbsdisnum.setText((postion + 1) + "¥");
		h.bbsdiscentent.setText(bbs.conten.replace("\\n", "\n"));

		return view;
	}

	class HorldView {

		public ImageView headview;
		public TextView bbsdisname, bbsdistype, bbsdistime, bbsdisnum, bbsdiscentent;

		public HorldView(View view) {
			this.headview = (ImageView) view.findViewById(R.id.bbsdic_CiecleImg);
			this.bbsdisname = (TextView) view.findViewById(R.id.bbsdis_username);
			this.bbsdistype = (TextView) view.findViewById(R.id.bbsdis_usertypr);
			this.bbsdistime = (TextView) view.findViewById(R.id.bbsdis_time);
			this.bbsdisnum = (TextView) view.findViewById(R.id.bbsdis_num);
			this.bbsdiscentent = (TextView) view.findViewById(R.id.bbsdis_centent);
		}

	}

}
