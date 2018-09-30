package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.entity.Fragment2_all_GV_CarEntity;
import com.example.xsjx.R;

import java.util.List;

/*
 * 蒋团圆
 * 汽车的adapter
 * */

public class GvAdapter extends BaseAdapter {
	public Context context;
	public List<Fragment2_all_GV_CarEntity> list;
	public LayoutInflater inflater;

	public GvAdapter(Context context, List<Fragment2_all_GV_CarEntity> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);

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
	public View getView(int arg0, View view, ViewGroup arg2) {
		horldView h = null;
		if (view == null) {
			view = inflater.inflate(R.layout.fragment2_all_gv_item, null);
			h = new horldView(view);
			view.setTag(h);
		} else {
			h = (horldView) view.getTag();
		}
		Fragment2_all_GV_CarEntity CarEntitylist = list.get(arg0);
		
		//h.CarImg.setImageBitmap(ImageUtil.getBitmapThumbnail(CarEntitylist.CarImg,400,400));
		
		//先设置图片为系统图片
		h.CarImg.setBackgroundResource(R.drawable.car);
	
		
	
		
		h.CarNum.setText("车牌："+CarEntitylist.CarNum);
		
		
		h.CarCoach.setText("教练："+CarEntitylist.CarCoach+"教练");
	
		h.CarPlaces.setText("预约总名额:"+CarEntitylist.CarPlaces+"人");
	
		
		h.CarAdvPlaces.setText("已预约名额:"+CarEntitylist.CarAdvPlaces+"人");
		
		h.CarRemainPlaces.setText("剩 余 名 额:"+CarEntitylist.CarRemainPlaces+"人");
	
		
		
		
	    h.CarNum.setTag(CarEntitylist.CarId);
	
		
		
		return view;
	}

	class horldView {
		
		public ImageView CarImg;
		public TextView  CarNum,CarCoach,CarPlaces,CarAdvPlaces,CarRemainPlaces;
		
		public horldView(View view) {
			CarImg=(ImageView) view.findViewById(R.id.fragment2_all_gv_carimg);
			CarNum=(TextView) view.findViewById(R.id.fragment2_all_gv_carNum);
			CarCoach=(TextView) view.findViewById(R.id.fragment2_all_gv_carCoach);
			
			CarPlaces=(TextView) view.findViewById(R.id.fragment2_all_gv_carPlaces);
			CarAdvPlaces=(TextView) view.findViewById(R.id.fragment2_all_gv_carAdvPlaces);
			CarRemainPlaces=(TextView) view.findViewById(R.id.fragment2_all_gv_carRemainPlaces);
			
		}

	}

}
