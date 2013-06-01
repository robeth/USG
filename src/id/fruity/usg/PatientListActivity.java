package id.fruity.usg;

import id.fruity.usg.database.USGDBHelper;
import id.fruity.usg.remote.RemoteUtils;
import id.fruity.usg.remote.Synchonization;
import id.fruity.usg.util.Preference;
import android.content.Intent;
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
	private int fragmentId;
	private Fragment listFragment;
	private USGDBHelper helper;
	private int puskesmasId;
	private int currentId;
	private String userId;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		Preference.A = this;
		Synchonization.A = this;
		
		Bundle b = getIntent().getExtras();
		String username = "user6";//b.getString("username");
		this.deleteDatabase(USGDBHelper.DATABASE_NAME);
		helper = new USGDBHelper(this);
		helper.open();
		helper.test();
		userId = helper.getIdOfUsername(username);
		boolean isDokter = helper.isDoctor(userId);
		boolean isPetugas = helper.isPetugas(userId);
		if(isDokter){
			currentId = helper.getDokterId(userId);
		} else{
			currentId = helper.getPetugasId(userId);
		}
		puskesmasId = helper.getPuskesmasId(userId);
		helper.close();
		
		setContentView(R.layout.patients);
		fragmentId = R.id.content;

		getSupportActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_STANDARD);
		getSupportActionBar().setIcon(R.drawable.puskesmas_icon);
		getSupportActionBar().setSubtitle("Last sync: 15 Apr 2013 7:55 am");
		getSupportActionBar().setTitle("Puskesmas Wailolo");

		FragmentTransaction ft = this.getSupportFragmentManager()
				.beginTransaction();
		Bundle bb = new Bundle();
		bb.putInt("puskesmasId", puskesmasId);
		bb.putString("userId", userId);
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
		subMenu1.add("Sinkronisasi").setIcon(R.drawable.navigation_refresh);
		subMenu1.add("Tambah Pasien").setIcon(R.drawable.social_add_person);
		subMenu1.add("Urut A-Z").setIcon(R.drawable.collections_sort_by_size);
		subMenu1.add("Urut Tanggal")
				.setIcon(R.drawable.collections_go_to_today);
		subMenu1.add("Informasi").setIcon(R.drawable.action_about);

		MenuItem subMenu1Item = subMenu1.getItem();
		subMenu1Item.setIcon(R.drawable.device_access_storage);
		subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getTitle().equals("Tambah Pasien")){
			Intent i = new Intent(this, AddPatientActivity.class);
			Bundle b = new Bundle();
			b.putInt("puskesmasId", puskesmasId);
			b.putString("userId", userId);
			i.putExtras(b);
			startActivity(i);
		} else if (item.getTitle().equals("Sinkronisasi")){
			RemoteUtils.startAsyn(this);
			
		}
		return true;
	}

	public static void notifyResult(String result) {
		Log.d(PatientListActivity.class.getName(), "res:"+result);
	}
	
	


}
