package id.fruity.usg;

import id.fruity.usg.database.USGDBHelper;
import id.fruity.usg.database.converter.ClinicConverter;
import id.fruity.usg.database.converter.UserConverter;
import id.fruity.usg.database.table.entry.Clinic;
import id.fruity.usg.database.table.entry.User;
import id.fruity.usg.remote.RemoteUtils;
import id.fruity.usg.remote.RemoteUtils.SyncCallback;
import id.fruity.usg.remote.Synchonization;
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
			SORT_DATE_TITLE = "Urut tanggal", INFO_TITLE = "Info";

	private int fragmentId;
	private Fragment listFragment;
	private USGDBHelper helper;
	private int clinicId;
	private int currentId;
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

		Bundle b = getIntent().getExtras();
		String username = "user6";// b.getString("username");

		helper = USGDBHelper.getInstance(this);
		helper.open();
		userId = helper.getIdOfUsername(username);
		isDoctor = helper.isDoctor(userId);
		if (isDoctor) {
			currentId = helper.getDokterId(userId);
		} else {
			currentId = helper.getPetugasId(userId);
		}

		// Fix header
		long lastSync = Preference.getLastSync();
		String lastSyncString = DateUtils.getStringOfCalendarFromLong(lastSync);
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
		getSupportActionBar().setSubtitle("Last sync: " + lastSyncString);
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
				.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

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

		MenuItem subMenu1Item = subMenu1.getItem();
		subMenu1Item.setIcon(R.drawable.device_access_storage);
		subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		return true;
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
			this.deleteDatabase(USGDBHelper.DATABASE_NAME);
			helper.open();
			helper.test();
			helper.close();
		} else if (item.getTitle().equals(SORT_CHAR_TITLE)){
			RemoteUtils.startAsyn2(this);
		} else if (item.getTitle().equals(SORT_DATE_TITLE)){
			RemoteUtils.startAsyn3(this);
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
			progressDialog.setMax(11);
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
					result = RemoteUtils.syncUser();
					publishProgress(++counter);
					this.wait(500);
//					result = RemoteUtils.syncDoctor();
//					publishProgress(++counter);
//					this.wait(500);
//					result = RemoteUtils.syncClinic();
//					publishProgress(++counter);
//					this.wait(500);
//					result = RemoteUtils.syncOfficer();
//					publishProgress(++counter);
//					this.wait(500);
//					result = RemoteUtils.syncPatient();
//					publishProgress(++counter);
//					this.wait(500);
//					result = RemoteUtils.syncPregnancy();
//					publishProgress(++counter);
//					this.wait(500);
//					result = RemoteUtils.syncPhoto();
//					publishProgress(++counter);
//					this.wait(500);
//					result = RemoteUtils.syncServe();
//					publishProgress(++counter);
//					this.wait(500);
//					result = RemoteUtils.syncWorksOn();
//					publishProgress(++counter);
//					this.wait(500);
//					result = RemoteUtils.syncComment();
//					publishProgress(++counter);
//					this.wait(500);
//					result = RemoteUtils.syncValidation();
//					publishProgress(++counter);
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
		}
	}

}
