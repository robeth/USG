package id.fruity.usg;

import id.fruity.usg.database.USGDBHelper;
import id.fruity.usg.database.converter.PatientConverter;
import id.fruity.usg.database.converter.PhotoConverter;
import id.fruity.usg.database.table.entry.Patient;
import id.fruity.usg.database.table.entry.Photo;
import id.fruity.usg.util.DateUtils;
import id.fruity.usg.util.Ellipse;
import id.fruity.usg.util.SDUtils;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class UsgActivity extends SherlockActivity{
	private Spinner methodSpinner;
	private String patientId;
	private int kandunganId;
	private int photoId;
	private static final String    TAG = "OCVSample::Activity";
    private Mat m1, m2;
    
    private int selectedMethod;
    private Photo foto;
    private USGDBHelper helper;
    
    //Android View variables
    private ImageView usgImage;
    private TextView hc, bpd, x, y, a, b, theta, scale;
    private Bitmap sourceBitmap, tempBitmap;
    
    
	private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
        	Log.d(TAG, "OpenCV Onmanagerconnected");
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    Log.i(TAG, "OpenCV loaded successfully");

                    // Load native library after(!) OpenCV initialization
                    System.loadLibrary("mixed_sample");
                  //Mark the previous ellipse if exist
            		if(foto.getA() > -1 && foto.getB() > -1 && foto.getX() > -1 &&
            				foto.getY() > -1){
            			markEllipse();
            		}
                    
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		Bundle bb = getIntent().getExtras();
		patientId = bb.getString("patientId");
		kandunganId = bb.getInt("kandunganId");
		photoId = bb.getInt("photoId");
		helper = new USGDBHelper(this);
		helper.open();
		
		Cursor c = helper.getPhotos(patientId, kandunganId, photoId);
		foto = PhotoConverter.convert(c);
		Log.d("Tes", "fot0 == null" + (foto == null));
		
		c = helper.getPasien(patientId);
		Patient p = PatientConverter.convert(c);
		
		helper.close();
		
		setContentView(R.layout.usg);
		hc = (TextView) findViewById(R.id.usg_hc_value);
		bpd = (TextView) findViewById(R.id.usg_bpd_value);
		scale = (TextView) findViewById(R.id.usg_scale_value);
		a = (TextView) findViewById(R.id.usg_params_a_value);
		b = (TextView) findViewById(R.id.usg_params_b_value);
		x = (TextView) findViewById(R.id.usg_params_x_value);
		y = (TextView) findViewById(R.id.usg_params_y_value);
		theta = (TextView) findViewById(R.id.usg_params_t_value);
		methodSpinner = (Spinner) findViewById(R.id.usg_methods);
		methodSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parentView, View selectedItemView, 
					int position, long id) {
				selectedMethod = position;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		usgImage = (ImageView) findViewById(R.id.usg_photo);
		Drawable usgPhoto = SDUtils.getPhotoDrawable(foto.getFilename());
		if(usgPhoto != null){
			usgImage.setImageDrawable(usgPhoto);
		}
		sourceBitmap = ((BitmapDrawable)usgImage.getDrawable()).getBitmap();
		

		methodSpinner.setSelection(getCurrentMethod(foto.getMethod()));
		hc.setText(Ellipse.keliling(foto.getA(), foto.getB())+" cm");
		bpd.setText(Ellipse.area(foto.getA(), foto.getB()) + " cm");
		a.setText(foto.getA()+"");
		b.setText(foto.getB()+"");
		x.setText(foto.getX()+ "");
		y.setText(foto.getY()+ "");
		theta.setText(foto.getTheta()+"");
		scale.setText(foto.getScale()+"");
		
		
		//Configure action bar
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		Drawable patientPhoto = SDUtils.getPhotoDrawable(p.getFilename());
		if(patientPhoto != null){
			getSupportActionBar().setIcon(patientPhoto);
		} else {
			getSupportActionBar().setIcon(R.drawable.content_picture);
		}
		getSupportActionBar().setTitle(p.getName());
		getSupportActionBar().setSubtitle(DateUtils.getStringOfCalendarFromLong(foto.getCreateTimestampLong()));
	}
	
	private int getCurrentMethod(String method) {
		int res = 0;
		if(method.equals("RHT")){
			res= 0;
		} else if(method.equals("IRHT")){
			res = 1;
		}
		return res;
	}


	@Override
    public void onResume()
    {
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_4, this, mLoaderCallback);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
			menu.add("Accept").setIcon(R.drawable.navigation_accept)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().equals("Accept")){
			approxEllipse();
		}
		return true;
	}
	
	/*
	 * <item>RHT</item>
	 * <item>IRHT</item>
	 */
    
	
	private void approxEllipse(){
		recycleTempBitmap();
		if(m1 != null){
			m1.release();
		}
		m1 = new Mat();
		Utils.bitmapToMat(tempBitmap, m1);
		
		float[] rs = null;
		if(selectedMethod == 0){
			rs = RHT(m1.getNativeObjAddr(), 10000);
		} else if(selectedMethod == 1){
			rs = IRHT(m1.getNativeObjAddr(), 10000, 100, 50);
		}
		
		Log.d("Result", "length" + rs.length + "["+rs[0]+","+rs[1]+","+rs[2]+","+rs[3]+","+rs[4]+","+rs[5]+"]");
		
//		Bitmap bmp = Bitmap.createBitmap(m1.cols(), m1.rows(), Bitmap.Config.ARGB_8888);

		foto.setX(rs[0]);
		foto.setY(rs[1]);
		foto.setA(rs[2]);
		foto.setB(rs[3]);
		foto.setTheta(rs[4]);
		foto.setMethod((String)methodSpinner.getItemAtPosition(selectedMethod));
		foto.setAutoDateLong(DateUtils.getCurrentLong());
		
		a.setText(foto.getA()+"");
		b.setText(foto.getB()+"");
		x.setText(foto.getX()+ "");
		y.setText(foto.getY()+ "");
		theta.setText(foto.getTheta()+"");
		scale.setText(foto.getScale()+"");
		
		helper.open();
		helper.updatePhoto(foto);
		helper.close();
		
		Utils.matToBitmap(m1, tempBitmap);
		usgImage.setImageBitmap(tempBitmap);
		
	}
	
	private void markEllipse(){
		recycleTempBitmap();
		if(m1 != null){
			m1.release();
		}
		m1 = new Mat();
		Utils.bitmapToMat(tempBitmap, m1);
		Mark(m1.getNativeObjAddr(),foto.getA(), foto.getB(), foto.getX(), foto.getY(), foto.getTheta());
		Utils.matToBitmap(m1, tempBitmap);
		usgImage.setImageBitmap(tempBitmap);
	}
	
	private void recycleTempBitmap(){
		if(tempBitmap != null){
			tempBitmap.recycle();
		}
		tempBitmap = sourceBitmap.copy(sourceBitmap.getConfig(), true);
		
		
	}
	
	public native float[] RHT(long matAddrGr, int sample);
	public native float[] IRHT(long matAddrGr, int sample, int interval, float delta);
	public native void Mark(long matAddrrGr, float a, float b, float x, float y, float theta);
}
