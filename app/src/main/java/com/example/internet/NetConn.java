package com.example.internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;



public class NetConn {
	/**
	 * 
	 * @MethodsNmae:  isNetworkAvailable 
	 * @Description:  ��������Ƿ���ͨ  ���ҿ��Է��ʻ�����
	 * @param:        @param context
	 * @param:        @return
	 * @return:       boolean     
	 * @author        ����Բ
	 * @Date          2017��11��13�� ����2:32:24
	 */
	 public static boolean isNetworkAvailable(Context context) {  
	        ConnectivityManager connectivity = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        if (connectivity != null) {  
	            NetworkInfo info = connectivity.getActiveNetworkInfo();  
	            if (info != null && info.isConnected())   
	            {  
	                // ��ǰ���������ӵ�  
	                if (info.getState() == NetworkInfo.State.CONNECTED)   
	                {  
	                    // ��ǰ�����ӵ��������  
	                    return true;  
	                }  
	            }  
	        }  
	        return false;  
	    }  
}
