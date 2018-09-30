package com.example.adapter;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.example.testpic.Bimp;
import com.example.xsjx.R;

public class PostingGridAdapter extends BaseAdapter {
	private String TAG = "PostingGridAdapter";
	private LayoutInflater inflater; // ��ͼ����
	private int selectedPosition = -1;// ѡ�е�λ��
	private boolean shape;
	private Context context;
	public boolean isShape() {
		return shape;
	}

	public void setShape(boolean shape) {
		this.shape = shape;
	}

	public PostingGridAdapter(Context context) {
		this.inflater = LayoutInflater.from(context);
	   this.context=context;
	}

	public void setSelectedPosition(int position) {
		selectedPosition = position;
	}

	public int getSelectedPosition() {
		return selectedPosition;
	}

	
	public int getCount() {
		
		Log.v(TAG, "Bimp.bmp.size()="+Bimp.bmp.size());
		return (Bimp.bmp.size() + 1);
	}

	
	public Object getItem(int arg0) {

		return null;
	}


	public long getItemId(int arg0) {

		return 0;
	}

	
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		Log.v(TAG, "position="+position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.posting_gv_item, parent, false);
			holder = new ViewHolder();
			
			holder.image = (ImageView) convertView.findViewById(R.id.posting_gv_item_image);
			holder.del=(Button) convertView.findViewById(R.id.posting_gv_image_del);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		//holder.image.setVisibility(View.VISIBLE);
		
		holder.image.setVisibility(View.VISIBLE);
		if(position==Bimp.bmp.size()){
		//	holder.image.setBackgroundResource(R.drawable.postingimg_item);
			holder.image.setBackgroundResource(R.drawable.icon_addpic_unfocused);
			holder.del.setVisibility(View.GONE);
		}
		else {
			holder.image.setImageBitmap(Bimp.bmp.get(position));
			holder.del.setVisibility(View.VISIBLE);
			
		  }
	
		Log.v(TAG, "position =" + position + "= Bimp.bmp.size()=" + Bimp.bmp.size() + "=max=" + Bimp.max);

		
		/*
		 * if (position == 4) { holder.image.setVisibility(View.GONE); }
		 * ���ﲻ֪��Ϊʲô����4�����ص�
		 */

		return convertView;
	}

	public class ViewHolder {
		public ImageView image;
		public Button del;
	}

}
