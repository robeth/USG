package id.fruity.usg.util;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Preference {
	private static final String SYNC_PREF= "usg_sync";
	private static final String LAST_SYNC = "last_sync";
	private static final String LOGGED_IN = "logged_in";
	private static final String LAST_USERNAME = "last_username";
	public static Activity A;
	
	public static Long getLastSync(){
		SharedPreferences p = A.getSharedPreferences(SYNC_PREF, 0);
		return p.getLong(LAST_SYNC, 0);
	}
	
	public static void setLastSync(long timestamp){
		SharedPreferences p = A.getSharedPreferences(SYNC_PREF, 0);
		Editor e = p.edit();
		e.putLong(LAST_SYNC, timestamp);
		e.commit();
	}
	
	public static boolean isLoggedIn(){
		SharedPreferences p = A.getSharedPreferences(SYNC_PREF, 0);
		return p.getBoolean(LOGGED_IN, false);
	}
	
	public static void setLoggedIn(boolean status){
		SharedPreferences p = A.getSharedPreferences(SYNC_PREF, 0);
		Editor e = p.edit();
		e.putBoolean(LOGGED_IN, status);
		e.commit();
	}
	
	public static String getLastUsername(){
		SharedPreferences p = A.getSharedPreferences(SYNC_PREF, 0);
		return p.getString(LAST_USERNAME, "");
	}
	
	public static void setLastUsername(String username){
		SharedPreferences p = A.getSharedPreferences(SYNC_PREF, 0);
		Editor e = p.edit();
		e.putString(LAST_USERNAME, username);
		e.commit();
	}
	
}
