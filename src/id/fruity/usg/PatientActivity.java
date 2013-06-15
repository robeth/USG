package id.fruity.usg;

import id.fruity.usg.database.USGDBHelper;
import id.fruity.usg.database.table.entry.Photo;
import id.fruity.usg.util.DateUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.SyncStateContract.Helpers;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

public class PatientActivity extends SherlockFragmentActivity implements
		ActionBar.TabListener {

	private Fragment currentFragment;
	private int currentFragmentIndex;
	private int fragmentId;
	private static int PROFILE_MODE = 0, USG_MODE = 1, STAT_MODE = 2,
			MESSAGE_MODE = 3;
	private ActionMode editMode;
	private Bundle bundle;
	private String patientId;
	private String userId;
	private boolean isDoctor;
	private USGDBHelper helper;

	// Get USG Image Properties
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int CHOOSE_IMAGE_ACTIVITY_REQUEST_CODE = 101;
	private File imageFile;
	private Uri fileUri;
	public static final int MEDIA_TYPE_IMAGE = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		Bundle b = getIntent().getExtras();
		patientId = b.getString("patientId");
		userId = b.getString("userId");
		isDoctor = b.getBoolean("isDoctor");
		helper = USGDBHelper.getInstance(this);
		bundle = new Bundle();
		bundle.putString("patientId", patientId);
		bundle.putString("userId", userId);
		bundle.putBoolean("isDoctor", isDoctor);
		setContentView(R.layout.patient);
		fragmentId = R.id.content;

		getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
		getSupportActionBar().setIcon(R.drawable.mother_icon);
		for (int i = 0; i < 4; i++) {
			ActionBar.Tab tab = getSupportActionBar().newTab();
			tab.setTabListener(this);
			tab.setTag(i);
			tab.setIcon(getIconId(i));
			getSupportActionBar().addTab(tab);
		}
		currentFragmentIndex = 0;

		if (currentFragment == null) {
			setActiveFragment(currentFragmentIndex);
		}
	}

	private void setActiveFragment(int fragmentIndex) {
		currentFragmentIndex = fragmentIndex;

		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		if (currentFragment != null) {
			ft.detach(currentFragment);
		}
		switch (fragmentIndex) {
		case 0:
			currentFragment = new PatientProfileFragment();
			break;
		case 1:
			currentFragment = new PatientUSGFragment();
			break;
		case 2:
			currentFragment = new PatientStatFragment();
			break;
		case 3:
			currentFragment = new MessageFragment();
			break;
		}
		if (editMode != null) {
			editMode.finish();
		}
		currentFragment.setArguments(bundle);
		ft.add(fragmentId, currentFragment, null);
		ft.commit();
	}

	private int getIconId(int index) {
		int iconId = 0;
		switch (index) {
		case 0:
			iconId = R.drawable.social_person;
			break;
		case 1:
			iconId = R.drawable.content_picture;
			break;
		case 2:
			iconId = R.drawable.alerts_and_states_error;
			break;
		case 3:
			iconId = R.drawable.social_chat;
			break;
		}

		return iconId;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (currentFragmentIndex == PROFILE_MODE && !isDoctor) {
			menu.add("Edit Profile").setIcon(R.drawable.content_edit)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		} else if (currentFragmentIndex == USG_MODE && !isDoctor) {
			menu.add("Add USG").setIcon(R.drawable.content_new_picture)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
			menu.add("Add Kandungan").setIcon(R.drawable.content_read)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		} else if (currentFragmentIndex == STAT_MODE) {

		} else if (currentFragmentIndex == MESSAGE_MODE) {
			menu.add("Mark As Read").setIcon(R.drawable.content_read)
					.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		}

		return true;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction transaction) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction transaction) {
		currentFragmentIndex = (Integer) tab.getTag();
		setActiveFragment((Integer) tab.getTag());
		supportInvalidateOptionsMenu();
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction transaction) {
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
		} else if (currentFragmentIndex == PROFILE_MODE) {
			editMode = startActionMode(new AnActionModeOfEpicProportions());
			if (currentFragment instanceof PatientProfileFragment) {
				((PatientProfileFragment) currentFragment).setEditable(true);
			}
		} else if (currentFragmentIndex == USG_MODE) {
			if (item.getTitle().equals("Add USG")) {
				((PatientUSGFragment) currentFragment).popUpUSGDialog();
			} else if (item.getTitle().equals("Add Kandungan")) {
				((PatientUSGFragment) currentFragment)
						.popUpAddKandunganDialog();
			}
		} else if (currentFragmentIndex == STAT_MODE) {

		} else if (currentFragmentIndex == MESSAGE_MODE) {

		}
		return true;
	}

	private final class AnActionModeOfEpicProportions implements
			ActionMode.Callback {
		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {

			return true;
		}

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			return true;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			Toast.makeText(PatientActivity.this, "OnDestroyActionMode",
					Toast.LENGTH_SHORT).show();
			if (currentFragment instanceof PatientProfileFragment) {
				((PatientProfileFragment) currentFragment).notifyHasEditted();
				
			}
		}

	}

	public void sendImageRequestCamera() {
		if (imageFile != null) {
			imageFile.delete();
		}
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to
															// save the image
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file
															// name

		// start the image capture Intent
		startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	public void sendImageRequestDummy(String patientId, int pregnancyId) {
		Intent intent = new Intent(this, AddPhotoActivity.class);
		Bundle b = new Bundle();
		b.putString("patientId", patientId);
		b.putInt("pregnancyId", pregnancyId);
		intent.putExtras(b);
		startActivityForResult(intent, CHOOSE_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// Image captured and saved to fileUri specified in the Intent
				Toast.makeText(this, "Image saved to:\n", Toast.LENGTH_LONG)
						.show();
				((PatientUSGFragment) currentFragment)
						.notifyImageCaptured(imageFile.getAbsolutePath());
			} else if (resultCode == RESULT_CANCELED) {
				// User cancelled the image capture
			} else {
				// Image capture failed, advise user
			}
		} else if (requestCode == CHOOSE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Bundle resB = data.getExtras();
				int imageId = resB.getInt("imageId");
				int imageIdRes = resB.getInt("imageIdRes");
				String iPatientId = resB.getString("patientId");
				int iPregnancyId = resB.getInt("pregnancyId");
				Toast.makeText(this, "Image chosen:" + imageId,
						Toast.LENGTH_SHORT).show();
				
				boolean isSdPresent = android.os.Environment.getExternalStorageState().equals(
				        android.os.Environment.MEDIA_MOUNTED);
				String filename = "-1";
				if (isSdPresent) {
					filename = Environment.getExternalStorageDirectory().getAbsolutePath() +
							"USG Apps" + File.separator + "USG";
					File f = getOutputMediaFile(MEDIA_TYPE_IMAGE);
					saveToSdCard(f.getAbsolutePath(), imageIdRes);
					
				}
				((PatientUSGFragment) currentFragment).notifyImageCaptured(filename);
			}
		}
	}

	/** Create a file Uri for saving an image or video */
	private Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private File getOutputMediaFile(int type) {
		// To be safe, you should check that the SDCard is mounted
		// using Environment.getExternalStorageState() before doing this.

		File mediaStorageDir = new File(
				Environment.getExternalStorageDirectory(),
				"USG Apps" + File.separator + "USG");
		// This location works best if you want the created images to be shared
		// between applications and persist after your app has been uninstalled.

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d("MyCameraApp", "failed to create directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
				.format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "USG_" + timeStamp + ".jpg");
		} else {
			return null;
		}
		imageFile = mediaFile;
		return mediaFile;
	}
	
	private void saveToSdCard(String filename, int imageIdRes){
		BitmapFactory.Options bmOptions;
		bmOptions = new BitmapFactory.Options();
		bmOptions.inSampleSize = 1;
		Bitmap bbicon = BitmapFactory.decodeResource(
				getResources(), imageIdRes);

		File file = new File(filename);

		try {
			FileOutputStream outStream = new FileOutputStream(file);
			bbicon.compress(Bitmap.CompressFormat.PNG, 100,
					outStream);
			outStream.flush();
			outStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();

		}
	}

}
