package id.fruity.usg;

import id.fruity.usg.PatientOverviewAdapter.PatientInfoAdapterInterface;
import id.fruity.usg.database.USGDBHelper;
import id.fruity.usg.model.PatientOverview;

import java.util.ArrayList;
import java.util.Collections;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.widget.SearchView;

public class PatientListFragment extends SherlockFragment {
	private PatientOverviewAdapter mAdapter;
	private ListView list;
	private USGDBHelper helper;
	private int puskesmasId;
	private String userId;
	private boolean isDoctor;
	ArrayList<PatientOverview> pos,pos2,pos3;
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
        
        Log.d("Patient List Fragment", "Onstart:"+userId);
        mAdapter = new PatientOverviewAdapter(pos, getActivity().getLayoutInflater(), getActivity(), userId, adapterInterface);
        list.setAdapter(mAdapter);
        //mAdapter.setInfos(pos2)
       // mAdapter.notifyDataSetChanged()
        
        return v;
    }
    
    
    
    @Override
	public void onResume() {
		super.onResume();
		reloadData();
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
						reloadData();
					}
				});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// do nothing
			}
		});
		alert.show();
	}
    
    public void reloadData(){
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
        mAdapter.setInfos(pos);
        mAdapter.notifyDataSetChanged();
    }

	public void sortByName() {
		// TODO Auto-generated method stub
		pos2 = (ArrayList<PatientOverview>) pos.clone();
		Collections.sort(pos2);
		
		mAdapter.setInfos(pos2);
	    mAdapter.notifyDataSetChanged();
	        
	}
	
	public void sortByLastPhoto() {
		pos2 = (ArrayList<PatientOverview>) pos.clone();
		Collections.sort(pos2, PatientOverview.PatientLastPhoto);
		
		mAdapter.setInfos(pos2);
	    mAdapter.notifyDataSetChanged();
	}
	
	
	
	public void filter(String s) {
		// TODO Auto-generated method stub
	
	//	Log.d("pencet"+mAdapter.getCount(),s);
	    pos3 = new ArrayList<PatientOverview>();
		
        
		for (int i=0; i < pos.size();i++){
			
			if(pos.get(i).getName().toLowerCase().contains(s.toLowerCase())){
				pos3.add(pos.get(i));				
			}
		}
       
        mAdapter.setInfos(pos3);
        mAdapter.notifyDataSetChanged();    
	}
	
}
