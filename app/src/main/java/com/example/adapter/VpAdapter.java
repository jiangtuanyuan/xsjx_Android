package com.example.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class VpAdapter extends PagerAdapter{
	public List<View> list;
	public Context context;
	public VpAdapter(Context context ,List<View>  list) {
		this.list=list;
		this.context = context;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager)container).removeView(list.get(position));
	}


	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		
		((ViewPager)container).addView(list.get(position));
		
		return list.get(position);
	}
	@Override
	public int getCount() {
		return list.size();
	}
	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0==arg1;
	}

}
