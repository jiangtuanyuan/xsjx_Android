package com.example.utils;

import android.os.Environment;

/**
 * ���ߣ�����Բ
 * ʱ�䣺2017.10.18
 * ���ã��ļ��洢·��
 * */

public class Filepath {
	
	
	public static  String  ROOT =Environment.getExternalStorageDirectory().getAbsolutePath();
	
	
	
	
	public static  String  filePath = ROOT+"/android/data/com.example.xsjx"; //APP��ŵĸ�Ŀ¼
	public static  String  imgPath = filePath+"/img";// ���ͼƬ��Ŀ¼
	public static  String  PostingimgPath = filePath+"/postimgCache";// ���ͼƬ��Ŀ¼
	
	public static  String  DownloadPath = filePath+"/download";// �ļ�����·��
	
	public static  String  Video = filePath+"/Video";// ��Ƶ����·��
	
	
	

}
