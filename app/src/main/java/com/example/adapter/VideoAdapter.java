package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.entity.Video;
import com.example.utils.Utils;
import com.example.xsjx.R;

import org.xutils.x;

import java.util.List;

public class VideoAdapter extends BaseAdapter {
	private LayoutInflater inflater; // ��ͼ����
	private List<Video> list;
	private Context Context;

	public VideoAdapter(Context context, List<Video> list) {
		this.inflater = LayoutInflater.from(context);
		this.list = list;
		this.Context = context;
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

	@Override
	public View getView(int p, View v, ViewGroup vg) {
		ViewHolder holder = null;
		if (v == null) {
			v = inflater.inflate(R.layout.video_1_lv_item, vg, false);
			holder = new ViewHolder();
			holder.name = (TextView) v.findViewById(R.id.video1_item_name);
			holder.videoimg = (ImageView) v.findViewById(R.id.video1_item_videoimg);
			holder.author = (TextView) v.findViewById(R.id.video1_item_author);
			holder.date = (TextView) v.findViewById(R.id.video1_item_date);
			holder.playbtn = (ImageButton) v.findViewById(R.id.video1_item_play_btn);

			v.setTag(holder);
		} else {
			holder = (ViewHolder) v.getTag();
		}

		final Video video = list.get(p);

		holder.name.setText(video.name);
		holder.author.setText(video.author);
		holder.date.setText(video.date.substring(0, 10));
		if(video.imgurl.equals("null")){
			holder.videoimg.setBackgroundResource(R.drawable.video_jzsb);	
			holder.playbtn.setVisibility(View.INVISIBLE);
			
		}
	else 
		x.image().bind(holder.videoimg, video.imgurl, Utils.Ximage());// ����ͼƬ
    
		holder.playbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			
				 Utils.startVideoPlayAT(Context, video);

			}
		});

		return v;
	}

	public class ViewHolder {
		TextView name, author, date;
		ImageView videoimg;
		ImageButton playbtn;
	}

}
