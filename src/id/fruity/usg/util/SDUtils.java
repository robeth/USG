package id.fruity.usg.util;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

public class SDUtils {
	public static boolean saveUsgImage(Context c){
		return false;
	}
	
	public static boolean savePhotoImage(Context c){
		return false;
	}
	
	public static Drawable getPhotoDrawable(String path){
		Drawable d = null; 
		
		File f = new File(path);
		if(f.exists()){
			d = Drawable.createFromPath(f.getAbsolutePath());
		}
		return d;
	}
	
}
