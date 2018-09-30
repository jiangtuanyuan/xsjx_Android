package com.example.utils;

import com.example.xsjx.MainApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedUtils {
	private SharedPreferences share;
	private String SHARED_NAME = "spUtils";
	private SharedUtils() {
		share = MainApplication.baseContext.getSharedPreferences(SHARED_NAME, Context.MODE_PRIVATE);
	}

	private static SharedUtils instance;
	public static SharedUtils getInstance() {
		if (instance == null) {
			instance = new SharedUtils();

		}
		return instance;
	}

	public void putInt(String spName, int value) {
		Editor e = share.edit();
		e.putInt(spName, value);
		e.commit();
	}

	public int getInt(String spName, int defaultvalue) {
		return share.getInt(spName, defaultvalue);
	}

	public int getInt(String spName) {
		return share.getInt(spName, -1);
	}

	public void putString(String spName, String value) {
		Editor e = share.edit();
		e.putString(spName, value);
		e.commit();

	}

	public String getString(String spName) {
		return share.getString(spName, "");
	}

}
