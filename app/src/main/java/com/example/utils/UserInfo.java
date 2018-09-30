package com.example.utils;

import java.util.List;

import com.example.internet.AccessInternet;
import com.example.xsjx.LogoDoActivity;
import com.example.xsjx.MainActivity;
import com.example.xsjx.MainApplication;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class UserInfo {
public static  String UserID="";
public static  String UserName="";
public static  String UserPwd="";
public static  String UserSex="男";
public static  String UserTel="";
public static  String UserHeadPortrait="";//用户头像地址

public static  int IdentityID=5;
public static  String UserIdentity="游客";//用户身份  默认游客
public static int postingbanned=0;//是否被禁止发帖

public static  int UserCoachID=0;


/*
private static UserInfo userinfo;
public static UserInfo getUserInfo(){
	if(userinfo==null)
	{userinfo=new UserInfo();
	}
	return userinfo;
}
*/

public static void updateUserInfo(){
	UserName=SharedUtils.getInstance().getString("username");
	UserPwd=SharedUtils.getInstance().getString("userpwd");
	if(UserName!=null && !UserName.equals("") && UserPwd!=null && !UserPwd.equals("") ){
		AccessInternet.LogoDo(new Handler(){}, UserName, UserPwd);
	}
}

public static void claerUserInfo(){
	UserID="";
	UserName="";
	UserPwd="";
	UserSex="男";
	UserTel="";
	UserHeadPortrait="";
}

}
