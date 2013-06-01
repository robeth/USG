package id.fruity.usg;

import id.fruity.usg.database.USGDBHelper;
import id.fruity.usg.database.table.entry.Serve;
import id.fruity.usg.database.table.entry.Patient;
import id.fruity.usg.util.DateUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class AddPatientActivity extends SherlockActivity{
	private USGDBHelper helper;
	private ImageView photoView;
	private TextView ageText;
	private EditText nameText, addressText, phoneText, infoText;
	public static final int MEDIA_TYPE_IMAGE = 1;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private File imageFile;
	private Bitmap bitmap;
	private Uri fileUri;
	private int puskesmasId;
	private String userId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_Sherlock_Light);
		setContentView(R.layout.patient_profile);
		Bundle b = getIntent().getExtras();
		puskesmasId = b.getInt("puskesmasId");
		userId = b.getString("userId");
		
		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setIcon(R.drawable.social_add_person);
		
		helper = new USGDBHelper(this);
		
		photoView = (ImageView) findViewById(R.id.patient_photo);
	    ageText = (TextView) findViewById(R.id.patient_age);
	    nameText = (EditText) findViewById(R.id.patient_name);
	    addressText = (EditText) findViewById(R.id.patient_address);
	    phoneText = (EditText) findViewById(R.id.patient_phone);
	    infoText = (EditText) findViewById(R.id.patient_info);
	    photoView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(imageFile!=null){
					imageFile.delete();
				}
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

			    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
			    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

			    // start the image capture Intent
			    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == android.R.id.home){
			if(isValidInputs()){
				helper.open();
				Long currentTime = DateUtils.getCurrentLong();
				int lastIndex = helper.getLastIndexOfPatient();
				String name = nameText.getText().toString();
				String address = addressText.getText().toString();
				String phone = phoneText.getText().toString();
				String description = infoText.getText().toString();
				String imagePath = null;
				if(imageFile != null){
					imagePath = imageFile.getAbsolutePath();
				} else {
					imagePath = "-1";
				}
				Patient p = new Patient("-1", true, true, currentTime, currentTime, lastIndex + 1+"", name, address, phone, -1, imagePath, description);
				Serve m = new Serve(-1, true, true, currentTime, currentTime, puskesmasId, ""+(lastIndex + 1), "-1");
				helper.insertPatient(p);
				helper.insertServe(m);
				helper.close();
				finish();
			} else {
				Toast.makeText(this, "Pastikan nama pasien terisi", Toast.LENGTH_SHORT).show();
			}
		}
		return true;
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
	        if (resultCode == RESULT_OK) {
	            // Image captured and saved to fileUri specified in the Intent
	            Toast.makeText(this, "Image saved to:\n", Toast.LENGTH_LONG).show();
	            if(bitmap != null){
	            	bitmap.recycle();
	            }
	            bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
	            photoView.setImageBitmap(bitmap);
	            Log.d("Photo Action", "Image file absolute path:"+imageFile.getAbsolutePath());
	        } else if (resultCode == RESULT_CANCELED) {
	            // User cancelled the image capture
	        } else {
	            // Image capture failed, advise user
	        }
	    }
	}
	
	/** Create a file Uri for saving an image or video */
	private Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private File getOutputMediaFile(int type){
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "USG"+File.separator+"Pasien");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "USER_"+ timeStamp + ".jpg");
	    } else {
	        return null;
	    }
	    imageFile = mediaFile;
	    return mediaFile;
	}

	@Override
	public void onBackPressed() {
		cancelAddPatient();
		super.onBackPressed();
	}
	
	private void cancelAddPatient(){
		if(imageFile!= null){
			imageFile.delete();
		}
	}
	
	private boolean isValidInputs(){
		if(nameText.getText().toString().trim().length() > 0){
			return true;
		}
		return false;
	}
	
	
}
