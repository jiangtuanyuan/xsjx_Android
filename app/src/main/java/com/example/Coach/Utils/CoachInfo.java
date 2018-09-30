package com.example.Coach.Utils;

import java.util.ArrayList;
import java.util.List;

import com.example.Coach.entity.Ke2order;
import com.example.Coach.entity.Ke3order;
import com.example.internet.AccessInternet;
import com.example.utils.SharedUtils;

import android.os.Handler;

public class CoachInfo{
public static int id=0;
public static String name="星胜驾校";
public static String tel="";
public static String sex="男";
public static String coachimg="";
public static String headimg="";

public static int ke=2;
public static int palceID=1;//场地ID
public static String palce="信息学院旁(聚缘路)";//场地名称
public static String qianming="教练很懒,什么都没有留下哟!";//场地名称


public static void updateInfo(Handler handler){
	String CoachNmae=SharedUtils.getInstance().getString("CoachNmae");
	String CoachPwd=SharedUtils.getInstance().getString("CoachPwd");
	AccessInternet.LogoDoCoach(handler, CoachNmae, CoachPwd, "4cf7a5f249f2eac8721c2cc882724e7001be8c119f5df53202443c1df25bb120618de0c29628f1ea7437b76404b0bd5a86107f6f309d441e3abf1bbf40b3cf37");
    }




}
