package id.fruity.usg;

import id.fruity.usg.database.USGDBHelper;
import id.fruity.usg.database.table.entry.Serve;
import id.fruity.usg.database.table.entry.Patient;
import id.fruity.usg.util.DateUtils;
import id.fruity.usg.util.SDUtils;

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

public class AddPatientActivity extends SherlockActivity {
	private USGDBHelper helper;
	private ImageView photoView;
	private EditText ktpText, nameText, addressText, phoneText, infoText;
	public static final int MEDIA_TYPE_IMAGE = 1;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private File imageFile;
	private Bitmap bitmap;
	private Uri fileUri;
	private int puskesmasId;
	private String userId;
	private String tempRealFileName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setTheme(R.style.Theme_Sherlock_Light);
		setContentView(R.layout.patient_profile);
		Bundle b = getIntent().getExtras();
		puskesmasId = b.getInt("puskesmasId");
		userId = b.getString("userId");

		getSupportActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_STANDARD);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setIcon(R.drawable.social_add_person);

		helper = USGDBHelper.getInstance(this);

		photoView = (ImageView) findViewById(R.id.patient_photo);
		ktpText = (EditText) findViewById(R.id.patient_ktp);
		nameText = (EditText) findViewById(R.id.patient_name);
		addressText = (EditText) findViewById(R.id.patient_address);
		phoneText = (EditText) findViewById(R.id.patient_phone);
		infoText = (EditText) findViewById(R.id.patient_info);
		photoView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (imageFile != null) {
					imageFile.delete();
				}
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

				fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a
																	// file to
																	// save the
																	// image
				intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the
																	// image
																	// file name

				// start the image capture Intent
				startActivityForResult(intent,
						CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			if (isValidInputs()) {
				helper.open();
				Long currentTime = DateUtils.getCurrentLong();
				String ktp = ktpText.getText().toString();
				String name = nameText.getText().toString();
				String address = addressText.getText().toString();
				String phone = phoneText.getText().toString();
				String description = infoText.getText().toString();
				String imagePath = null;
				long photoCurrentTime = currentTime;
				if (imageFile != null) {
					imagePath = tempRealFileName;
				} else {
					imagePath = "-1";
					photoCurrentTime = -1;
				}
				Patient p = new Patient("-1", true, true, currentTime,
						currentTime, ktp, name, address, phone,
						-1, imagePath, description, photoCurrentTime, -1);
				Serve m = new Serve(-1, true, true, currentTime, currentTime,
						puskesmasId, "" + ktp, "-1");
				helper.insertPatient(p);
				helper.insertServe(m);
				helper.close();
				finish();
			} else {
				Toast.makeText(this, "Pastikan nama pasien terisi",
						Toast.LENGTH_SHORT).show();
			}
		}
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Image captured and saved to fileUri specified in the Intent
				Toast.makeText(this, "Image saved to:\n", Toast.LENGTH_LONG)
						.show();
				if (bitmap != null) {
					bitmap.recycle();
				}
				bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
				photoView.setImageBitmap(bitmap);
				Log.d("Photo Action",
						"Image file absolute path:"
								+ imageFile.getAbsolutePath());
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
			}
		}
	}

	/** Create a file Uri for saving an image or video */
	private Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private File getOutputMediaFile(int type) {
		File mediaStorageDir = new File(SDUtils.getDefaultPath());
		String timeStamp = DateUtils.getSimpleCurrentString();
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			tempRealFileName = "usg_" + timeStamp + ".jpg";
			mediaFile = new File(mediaStorageDir.getPath() + File.separator+tempRealFileName);
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

	private void cancelAddPatient() {
		if (imageFile != null) {
			imageFile.delete();
		}
	}

	private boolean isValidInputs() {
		if (nameText.getText().toString().trim().length() > 0) {
			return true;
		}
		return false;
	}

}
