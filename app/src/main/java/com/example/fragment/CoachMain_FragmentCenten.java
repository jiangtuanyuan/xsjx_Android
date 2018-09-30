package com.example.fragment;

import com.example.xsjx.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CoachMain_FragmentCenten extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View content = inflater.inflate(R.layout.coach_main_coachcenten, container, false);
		initView(content);
		initData();
		initListener();
		return content;
	}

	private void initView(View content) {
	}

	private void initData() {

	}

	private void initListener() {

	}

}
