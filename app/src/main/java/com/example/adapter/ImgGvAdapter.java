package com.example.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.utils.Utils;
import com.example.xsjx.R;

import org.xutils.x;

import java.util.List;

public class ImgGvAdapter extends BaseAdapter {
	private Context context;
	private List<String> imageUrls;

	public ImgGvAdapter(Context context,List<String> postingImg){
		this.context=context;
		this.imageUrls=postingImg;
	}
	
	@Override
	public int getCount() {

		return imageUrls == null ? 0 : imageUrls.size();
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
	public View getView(final int p, View v, ViewGroup arg2) {
		
		v = View.inflate(context, R.layout.posting_gv_item, null);

		ImageView imageView = (ImageView) v.findViewById(R.id.posting_gv_item_image);

		x.image().bind(imageView,imageUrls.get(p) ,Utils.Xgvimgno());
	
		return v;
	}

}
