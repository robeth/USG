package id.fruity.usg;

import id.fruity.usg.PatientInfoAdapter.PatientInfoAdapterInterface;
import id.fruity.usg.database.USGDBHelper;
import id.fruity.usg.database.table.entry.Pregnancy;
import id.fruity.usg.model.PatientOverview;
import id.fruity.usg.util.DateUtils;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class PatientListFragment extends SherlockFragment {
	private ArrayList<PatientInfo> infos;
	private PatientInfoAdapter mAdapter;
	private ListView list;
	private USGDBHelper helper;
	private int puskesmasId;
	private String userId;
	private boolean isDoctor;
	ArrayList<PatientOverview> pos;
	private PatientInfoAdapterInterface adapterInterface = new PatientInfoAdapterInterface() {
		
		@Override
		public void onLongPress(String iPatientId, String iPatientName) {
			popUpDeletePatientDialog(iPatientId, iPatientName);
		}
		
		@Override
		public void onClick(String iPatientId, String iUserId) {
			Intent i = new Intent(PatientListFragment.this.getActivity(), PatientActivity.class);
			Bundle b = new Bundle();
			b.putString("patientId", iPatientId);
			b.putString("userId", iUserId);
			b.putBoolean("isDoctor", isDoctor);
			i.putExtras(b);
			PatientListFragment.this.getActivity().startActivity(i);
		}
	};
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        isDoctor = b.getBoolean("isDoctor");
        userId = b.getString("userId");
        if(!isDoctor){
        	puskesmasId = b.getInt("puskesmasId");
        }
        helper = USGDBHelper.getInstance(this.getActivity());
        helper.open();
        PatientOverview p = new PatientOverview();
        Cursor c = null;
        if(isDoctor){
        	c = helper.getPatientOverviewDoctor(userId);
        } else {
        	c = helper.getPatientOverview(puskesmasId);
        }
        pos = p.getItems(c);
        helper.close();
    }

    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.patient_list, container, false);
        list = (ListView) v.findViewById(R.id.patient_list);
        
        infos = new ArrayList<PatientInfo>();
        for(int i = 0; i < pos.size(); i++){
        	Log.d("Patient Date", i + ": "+pos.get(i).getLastPhoto());
        	infos.add(new PatientInfo(pos.get(i).getIdPasien() ,R.drawable.mother_icon, pos.get(i).getName(), "Alamat a", "454545", "12 Mei 1980", "Kehamilan 1", DateUtils.getStringOfCalendarFromLong(pos.get(i).getLastPhoto()), pos.get(i).getTotalPhoto()));
        }
        Log.d("Patient List Fragment", "Onstart:"+userId);
        mAdapter = new PatientInfoAdapter(infos, getActivity().getLayoutInflater(), getActivity(), userId, adapterInterface);
        list.setAdapter(mAdapter);
        return v;
    }
    
    private void popUpDeletePatientDialog(final String iPatientId, final String iPatientName) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this.getActivity());
		alert.setTitle("Delete "+iPatientName);
		alert.setMessage("All related pregnancy and USG photo data will also be deleted.");

		alert.setPositiveButton("Confirm",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						helper.open();
						helper.deletePatient(iPatientId);
						helper.close();
					}
				});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// do nothing
			}
		});
		alert.show();
	}
}
