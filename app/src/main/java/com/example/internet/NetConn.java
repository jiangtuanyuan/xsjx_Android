package com.example.internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;



public class NetConn {
	/**
	 * 
	 * @MethodsNmae:  isNetworkAvailable 
	 * @Description:  检查网络是否连通  并且可以访问互联网
	 * @param:        @param context
	 * @param:        @return
	 * @return:       boolean     
	 * @author        蒋团圆
	 * @Date          2017年11月13日 下午2:32:24
	 */
	 public static boolean isNetworkAvailable(Context context) {  
	        ConnectivityManager connectivity = (ConnectivityManager) context  
	                .getSystemService(Context.CONNECTIVITY_SERVICE);  
	        if (connectivity != null) {  
	            NetworkInfo info = connectivity.getActiveNetworkInfo();  
	            if (info != null && info.isConnected())   
	            {  
	                // 当前网络是连接的  
	                if (info.getState() == NetworkInfo.State.CONNECTED)   
	                {  
	                    // 当前所连接的网络可用  
	                    return true;  
	                }  
	            }  
	        }  
	        return false;  
	    }  
}
