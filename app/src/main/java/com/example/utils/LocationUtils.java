package com.example.utils;

import java.util.List;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 *��λ�Ĺ����� 
 *
 */
public class LocationUtils {
	 private volatile static LocationUtils uniqueInstance;
	  private LocationManager locationManager;
	  private String locationProvider;
	  private Location location;
	  private Context mContext;
	  private  String TAG="LocationUtils";
	  private LocationUtils(Context context) {
	    mContext = context;
	    getLocation();
	  }
	 
	  
	  //����Double CheckLock(DCL)ʵ�ֵ���
	  public static LocationUtils getInstance(Context context) {
	    if (uniqueInstance == null) {
	      synchronized (LocationUtils.class) {
	        if (uniqueInstance == null) {
	          uniqueInstance = new LocationUtils( context );
	        }
	      }
	    }
	    return uniqueInstance;
	  }
	 
	  private void getLocation() {
	    //1.��ȡλ�ù�����
	    locationManager = (LocationManager) mContext.getSystemService( Context.LOCATION_SERVICE );
	    //2.��ȡλ���ṩ����GPS����NetWork
	    List<String> providers = locationManager.getProviders( true );
	    if (providers.contains( LocationManager.NETWORK_PROVIDER )) {
	      //��������綨λ
	      Log.d(TAG, "��������綨λ" );
	      locationProvider = LocationManager.NETWORK_PROVIDER;
	    } else if (providers.contains( LocationManager.GPS_PROVIDER )) {
	      //�����GPS��λ
	      Log.d(TAG, "�����GPS��λ" );
	      locationProvider = LocationManager.GPS_PROVIDER;
	    } else {
	      Log.d( TAG, "û�п��õ�λ���ṩ��" );
	      return;
	    }
	
	  
	    //3.��ȡ�ϴε�λ�ã�һ���һ�����У���ֵΪnull
	    Location location = locationManager.getLastKnownLocation( locationProvider );
	    if (location != null) {
	      setLocation( location );
	    }
	    // ���ӵ���λ�ñ仯���ڶ����͵����������ֱ�Ϊ���µ����ʱ��minTime����̾���minDistace
	    locationManager.requestLocationUpdates(locationProvider, 0, 0, locationListener);
	  }
	 
	  private void setLocation(Location location) {
	    this.location = location;
	    String address = "γ�ȣ�" + location.getLatitude() + "���ȣ�" + location.getLongitude();
	    Log.d(TAG, address );
	  }
	 
	  //��ȡ��γ��
	  public Location showLocation() {
	    return location;
	  }
	 
	  // �Ƴ���λ����
	  public void removeLocationUpdatesListener() {
	    // ��Ҫ���Ȩ��,������벻��
		  
	    if (locationManager != null) {
	      uniqueInstance = null;
	      locationManager.removeUpdates(locationListener);
	    }
	  }
	 
	  /**
	  * LocationListern������
	  * ����������λ���ṩ��������λ�ñ仯��ʱ������λ�ñ仯�ľ�������LocationListener������
	  */
	 
	  LocationListener locationListener = new LocationListener() {
	 
	    /**
	    * ��ĳ��λ���ṩ�ߵ�״̬�����ı�ʱ
	    */
	    @Override
	    public void onStatusChanged(String provider, int status, Bundle arg2) {
	 
	    }
	 
	    /**
	    * ĳ���豸��ʱ
	    */
	    @Override
	    public void onProviderEnabled(String provider) {
	 
	    }
	 
	    /**
	    * ĳ���豸�ر�ʱ
	    */
	    @Override
	    public void onProviderDisabled(String provider) {
	 
	    }
	 
	    /**
	    * �ֻ�λ�÷����䶯
	    */
	    @Override
	    public void onLocationChanged(Location location) {
	      location.getAccuracy();//��ȷ��
	      setLocation(location);
	    }
	  };
	 
}
