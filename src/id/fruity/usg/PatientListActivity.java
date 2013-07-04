package id.fruity.usg;

import id.fruity.usg.database.USGDBHelper;
import id.fruity.usg.database.converter.ClinicConverter;
import id.fruity.usg.database.converter.UserConverter;
import id.fruity.usg.database.table.entry.Clinic;
import id.fruity.usg.database.table.entry.User;
import id.fruity.usg.remote.RemoteUtils;
import id.fruity.usg.remote.RemoteUtils.SyncCallback;
import id.fruity.usg.remote.Synchonization;
import id.fruity.usg.util.Analyzer;
import id.fruity.usg.util.DateUtils;
import id.fruity.usg.util.Preference;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.actionbarsherlock.widget.SearchView;

public class PatientListActivity extends SherlockFragmentActivity {
	private static final String SYNC_TITLE = "Sync Now",
			ADD_PATIENT_TITLE = "Add Patient", SORT_CHAR_TITLE = "Sort A-Z",
			SORT_DATE_TITLE = "Urut tanggal", INFO_TITLE = "Info", LOGOUT = "Logout";

	private int fragmentId;
	private Fragment listFragment;
	private USGDBHelper helper;
	private int clinicId;
	private String userId;
	private boolean isDoctor;
	private User u;
	private Clinic c;
	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		Preference.A = this;
		Synchonization.A = this;
		Analyzer.A = this;
		Bundle b = getIntent().getExtras();
		String username = b.getString("username");
		
//		this.deleteDatabase(USGDBHelper.DATABASE_NAME);
		helper = USGDBHelper.getInstance(this);
		helper.open();
//		helper.test2();
		
		Log.d("HOmescreen", "Check:"+helper.isUserExist(username));
		if(!helper.isUserExist(username)){
			logout();
			return;
		}
		userId = helper.getIdOfUsername(username);
		isDoctor = helper.isDoctor(userId);
		long lastSync = Preference.getLastSync();
		
		String lastSyncString = DateUtils.getStringOfCalendarFromLong(lastSync);
		Log.d("HOmescreen", "Check Last SYnc:"+lastSync);
		String title = "";
		if (isDoctor) {
			u = UserConverter.convert(helper.getUser(userId));
			title = u.getName();
		} else {
			clinicId = helper.getPuskesmasId(userId);
			c = ClinicConverter.convert(helper.getClinic(clinicId));
			title = c.getName();
		}
		helper.close();

		getSupportActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_STANDARD);
		getSupportActionBar().setIcon(R.drawable.puskesmas_icon);
		if(lastSync == -1){
			getSupportActionBar().setSubtitle("Please sync");
		} else {
			getSupportActionBar().setSubtitle(lastSyncString);
		}
		getSupportActionBar().setTitle(title);

		setContentView(R.layout.patients);
		fragmentId = R.id.content;
		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		Bundle bb = new Bundle();
		bb.putBoolean("isDoctor", isDoctor);
		bb.putString("userId", userId);
		if (!isDoctor) {
			bb.putInt("puskesmasId", clinicId);
		}

		listFragment = new PatientListFragment();
		listFragment.setArguments(bb);

		ft.add(fragmentId, listFragment, null);
		ft.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		SearchView searchView = new SearchView(getSupportActionBar()
				.getThemedContext());
		searchView.setQueryHint("Search Patient Name");

		menu.add("Search").setIcon(R.drawable.action_search)
				.setActionView(searchView)
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		SubMenu subMenu1 = menu.addSubMenu("Action Items");
		subMenu1.add(SYNC_TITLE).setIcon(R.drawable.navigation_refresh);
		if (!isDoctor) {
			subMenu1.add(ADD_PATIENT_TITLE).setIcon(
					R.drawable.social_add_person);
		}
		subMenu1.add(SORT_CHAR_TITLE).setIcon(
				R.drawable.collections_sort_by_size);
		subMenu1.add(SORT_DATE_TITLE).setIcon(
				R.drawable.collections_go_to_today);
		subMenu1.add(INFO_TITLE).setIcon(R.drawable.action_about);
		subMenu1.add(LOGOUT).setIcon(R.drawable.content_discard);

		MenuItem subMenu1Item = subMenu1.getItem();
		subMenu1Item.setIcon(R.drawable.device_access_storage);
		subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);

		return true;
	}
	
	private void logout(){
		Preference.setLastUsername("-1");
		Preference.setLoggedIn(false);
		Preference.setLastSync(-1);
		Intent i = new Intent(this, LoginActivity.class);
		startActivity(i);
		finish();
		return;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals(ADD_PATIENT_TITLE)) {
			Intent i = new Intent(this, AddPatientActivity.class);
			Bundle b = new Bundle();
			b.putInt("puskesmasId", clinicId);
			b.putString("userId", userId);
			i.putExtras(b);
			startActivity(i);
		} else if (item.getTitle().equals(SYNC_TITLE)) {
			SyncTask d = new SyncTask();
			d.execute();
		} else if (item.getTitle().equals(INFO_TITLE)) {
			Intent i = new Intent(this, ExperimentActivity.class);
			startActivity(i);
		} else if (item.getTitle().equals(SORT_CHAR_TITLE)){
			
		} else if (item.getTitle().equals(SORT_DATE_TITLE)){
			RemoteUtils.startPhotoSync(this);
		} else if (item.getTitle().equals(LOGOUT)){
			logout();
		}
		return true;
	}

	public static void notifyResult(String result) {
		Log.d(PatientListActivity.class.getName(), "res:" + result);
	}

	// To use the AsyncTask, it must be subclassed
	private class SyncTask extends AsyncTask<Void, Integer, Void> {
		// Before running code in separate thread
		private SyncCallback sc = new SyncCallback() {

			@Override
			public void onSyncProgress(int i, boolean result) {
				publishProgress(i);
			}
		};

		@Override
		protected void onPreExecute() {
			progressDialog = new ProgressDialog(PatientListActivity.this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setTitle("Synchronizing...");
			progressDialog
					.setMessage("Loading application View, please wait...");
			progressDialog.setCancelable(false);
			progressDialog.setIndeterminate(false);
			progressDialog.setMax(12);
			progressDialog.setProgress(0);
			progressDialog.show();
		}

		// The code to be executed in a background thread.
		@Override
		protected Void doInBackground(Void... params) {
			boolean result = false;
			int counter = 0;
			try {
				synchronized (this) {
					final long timestamp = DateUtils.getCurrentLong();
					result = RemoteUtils.syncUser(timestamp);
					publishProgress(++counter);
					this.wait(100);
					result = RemoteUtils.syncDoctor(timestamp);
					publishProgress(++counter);
					this.wait(100);
					result = RemoteUtils.syncClinic(timestamp);
					publishProgress(++counter);
					this.wait(100);
					result = RemoteUtils.syncOfficer(timestamp);
					publishProgress(++counter);
					this.wait(100);
					result = RemoteUtils.syncPatient(timestamp);
					publishProgress(++counter);
					this.wait(100);
					result = RemoteUtils.syncPregnancy(timestamp);
					publishProgress(++counter);
					this.wait(100);
					result = RemoteUtils.syncPhoto(timestamp);
					publishProgress(++counter);
					this.wait(100);
					result = RemoteUtils.syncServe(timestamp);
					publishProgress(++counter);
					this.wait(100);
					result = RemoteUtils.syncWorksOn(timestamp);
					publishProgress(++counter);
					this.wait(100);
					result = RemoteUtils.syncComment(timestamp);
					publishProgress(++counter);
					this.wait(100);
					result = RemoteUtils.syncValidation(timestamp);
					publishProgress(++counter);
					RemoteUtils.syncPhotoFile();
					publishProgress(++counter);
					Preference.setLastSync(timestamp);
					PatientListActivity.this.runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							String lastSyncString = DateUtils.getStringOfCalendarFromLong(timestamp);
							getSupportActionBar().setSubtitle(lastSyncString);
							Log.d("Timestamp", "::"+timestamp+"---"+Preference.getLastSync());
						}
					});
					
					
				}
				
			} catch (InterruptedException e) {
				Log.e("Errorrr", e.getMessage());
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			progressDialog.setProgress(values[0]);
		}

		@Override
		protected void onPostExecute(Void result) {
			progressDialog.dismiss();
			refreshFragmentData();
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		refreshFragmentData();
	}

	private void refreshFragmentData(){
		((PatientListFragment )listFragment).reloadData();
	}

}
