package com.example.utils;

import android.os.Environment;

/**
 * 作者：蒋团圆
 * 时间：2017.10.18
 * 作用：文件存储路径
 * */

public class Filepath {
	
	
	public static  String  ROOT =Environment.getExternalStorageDirectory().getAbsolutePath();
	
	
	
	
	public static  String  filePath = ROOT+"/android/data/com.example.xsjx"; //APP存放的根目录
	public static  String  imgPath = filePath+"/img";// 存放图片的目录
	public static  String  PostingimgPath = filePath+"/postimgCache";// 存放图片的目录
	
	public static  String  DownloadPath = filePath+"/download";// 文件下载路劲
	
	public static  String  Video = filePath+"/Video";// 视频下载路劲
	
	
	

}
