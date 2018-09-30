package com.example.Coach.Utils;

import java.util.ArrayList;
import java.util.List;

import com.example.Coach.entity.makedate;
import com.example.Coach.entity.season;
import com.example.json.entity.UpdateApkEntity;

public class DaySeasonDate {
	public static int seasontf = 0;//默认第0个 春季
	public static List<season> seasonlist = new ArrayList<season>();//季节
	
	public static List<makedate> makelist=new ArrayList<makedate>();//时间
	
	//教练版本更新
	public static UpdateApkEntity apkEntity=new UpdateApkEntity();
}
