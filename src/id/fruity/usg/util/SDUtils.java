package id.fruity.usg.util;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;

public class SDUtils {
	private static final int USG_IMAGE_WIDTH_DP = 256;
	private static final int THUMBNAIL_IMAGE_WIDTH_DP = 72;
	
	public static boolean saveUsgImage(Context c) {
		return false;
	}

	public static boolean savePhotoImage(Context c) {
		return false;
	}

	public static Drawable getDrawable(Activity a, Bitmap b){
		Drawable d = null;
		if(b != null){
			d = new BitmapDrawable(a.getResources(),b);
		}
		return d;
	}
	
	public static Bitmap getBigPhotoBitmap(String path) {
		return getPhotoBitmap(path, USG_IMAGE_WIDTH_DP);
	}
	
	public static Bitmap getSmallPhotoBitmap(String path) {
		return getPhotoBitmap(path, THUMBNAIL_IMAGE_WIDTH_DP);
	}
	
	public static Bitmap getPhotoBitmap(String path, int widthDP) {
		Bitmap picture = null;
		File f = new File(path);
		if (f.exists()) {
//			BitmapFactory.Options options = new BitmapFactory.Options();
//			options.inSampleSize = 8;
			picture=BitmapFactory.decodeFile(f.getAbsolutePath());
		    int width = picture.getWidth();
		    int height = picture.getWidth();
		    float aspectRatio = (float) width / (float) height;
		    int newWidth = (int)AndroidUnit.convertDpToPixel(widthDP);
		    int newHeight = (int) (newWidth / aspectRatio);       
		    picture= Bitmap.createScaledBitmap(picture, newWidth, newHeight, true);
		}
		return picture;
	}

	public static String getDefaultPath() {
		return Environment.getExternalStorageDirectory() + File.separator
				+ "USG Apps" + File.separator + "USG" + File.separator;
	}
	
	public static String getAbsolutePath(String filename){
		return getDefaultPath()+filename;
	}
	
	public static void init (){
		File mediaStorageDir = new File(getDefaultPath());
		if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("USG app", "failed to create directory");
	        }
	    }
	}

}
