package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.GvAdapter;
import com.example.entity.Fragment2_all_GV_CarEntity;
import com.example.utils.Utils;
import com.example.xsjx.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;

@SuppressWarnings("unused")
public class FragMent_2_all extends View {

	public FragMent_2_all(Context context) {
		super(context);
	}

	@SuppressLint("InflateParams")
	public static View getView(final Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.fragment_2_all_layout, null);

		// ?????????????
		RadioGroup dateRG = (RadioGroup) view.findViewById(R.id.Fragment2_all_ScrollView_date);
		final int[] rb = new int[] { R.id.Fragment2_all_RB_date1, R.id.Fragment2_all_RB_date2,
				R.id.Fragment2_all_RB_date3, R.id.Fragment2_all_RB_date4, R.id.Fragment2_all_RB_date5,
				R.id.Fragment2_all_RB_date6, };
		List<String> datelist = new ArrayList<String>();
		
		datelist = Utils.getday(6);
		
		for (int i = 0; i < 6; i++) {
			RadioButton daterb = (RadioButton) view.findViewById(rb[i]);
			daterb.setText(datelist.get(i));
		}
		dateRG.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				for (int i = 0; i < rb.length; i++) {
					if (rb[i] == arg1) {

					}
				}
			}
		});

		// ???????????? END

		// ????
		final Button Trainingground = (Button) view.findViewById(R.id.fragment2_all_Trainingground);
		final String[] TraininggroundItems = new String[] { "???????", "?????§Ô?§Ò????", "????????" };
		Trainingground.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Builder bu3 = new Builder(context);
				bu3.setTitle("????????????:");
				bu3.setSingleChoiceItems(TraininggroundItems, 1, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ListView lw = ((AlertDialog) dialog).getListView();

						// which???????????
						Object checkedItem = lw.getAdapter().getItem(which);

						// ???cancel????ok????????????????item???dialog???
						dialog.dismiss();
						// ????Button ??Text?????
						Trainingground.setText((String) checkedItem);

						/*
						 * if((boolean) checkedItem.equals("???????")){
						 * bu3.create(); bu3.show();
						 * 
						 * }
						 */
						// Toast.makeText(getActivity(),
						// Trainingground.getText(), 1).show();
					}
				});

				bu3.create();
				bu3.show();

			}
		});

		// ???? END

		// GridView ?????
		GridView gridView = (GridView) view.findViewById(R.id.fragment2_all_GridView);
		List<Fragment2_all_GV_CarEntity> gvlist;
		gvlist = getCardate();
		Log.v("gvlist=", gvlist.size() + "");
		GvAdapter gvAdapter = new GvAdapter(context, gvlist);
		gridView.setAdapter(gvAdapter);
		// gridView ???????
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
				// ?????¨®?????ID???
				TextView t = (TextView) v.findViewById(R.id.fragment2_all_gv_carNum);
				int id = (Integer) t.getTag();

				// Toast.makeText(getActivity(), "?????"+arg2+"id="+id, 1).show();

			}
		});
		// GridView ?????END

		return view;
	}

	
	/*
	 * ????GridView??List?????? ?????????????????? 2017.9.26
	 */

	public static List<Fragment2_all_GV_CarEntity> getCardate() {

		List<Fragment2_all_GV_CarEntity> list = new ArrayList<Fragment2_all_GV_CarEntity>();

		list.add(new Fragment2_all_GV_CarEntity(1, "http://www.jtyrl.cn/xsjx/img/1.jpg", "??A??123456", "????", "8", "4",
				"4"));
		list.add(new Fragment2_all_GV_CarEntity(2, "http://www.jtyrl.cn/xsjx/img/2.jpg", "??A??123458", "????", "5", "4",
				"1"));
		list.add(new Fragment2_all_GV_CarEntity(3, "http://www.jtyrl.cn/xsjx/img/2.jpg", "??A??123456", "????", "8", "1",
				"7"));
		list.add(new Fragment2_all_GV_CarEntity(4, "http://www.jtyrl.cn/xsjx/img/3.jpg", "??A??128456", "????", "8", "2",
				"6"));
		return list;
	}

}
