package id.fruity.usg.util;

import android.app.Activity;
import android.content.SharedPreferences;

public class Preference {
	private static final String SYNC_PREF= "usg_sync";
	private static final String LAST_SYNC = "last_sync";
	public static Activity A;
	
	public static Long getLastSync(){
		SharedPreferences p = A.getSharedPreferences(SYNC_PREF, 0);
		return p.getLong(LAST_SYNC, 0);
	}
}
