package id.fruity.usg;

import id.fruity.usg.database.USGDBHelper;
import id.fruity.usg.model.PatientOverview;
import id.fruity.usg.util.DateUtils;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class PatientListFragment extends SherlockFragment {
	private ArrayList<PatientInfo> infos;
	private PatientInfoAdapter mAdapter;
	private ListView list;
	private USGDBHelper helper;
	private int puskesmasId;
	private String userId;
	ArrayList<PatientOverview> pos;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        puskesmasId = b.getInt("puskesmasId");
        userId = b.getString("userId");
        Log.d("PatientList", userId);
        helper = new USGDBHelper(this.getActivity());
        helper.open();
        PatientOverview p = new PatientOverview();
        pos = p.getItems(helper.getPatientOverview(puskesmasId));
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
        
        
        return v;
    }
    
    @Override
    public void onStart(){
    	helper.open();
        PatientOverview p = new PatientOverview();
        pos = p.getItems(helper.getPatientOverview(puskesmasId));
        helper.close();
        
        infos = new ArrayList<PatientInfo>();
        for(int i = 0; i < pos.size(); i++){
        	Log.d("Patient Date", i + ": "+pos.get(i).getLastPhoto());
        	infos.add(new PatientInfo(pos.get(i).getIdPasien() ,R.drawable.mother_icon, pos.get(i).getName(), "Alamat a", "454545", "12 Mei 1980", "Kehamilan 1", DateUtils.getStringOfCalendarFromLong(pos.get(i).getLastPhoto()), pos.get(i).getTotalPhoto()));
        }
        Log.d("Patient List Fragment", "Onstart:"+userId);
        mAdapter = new PatientInfoAdapter(infos, getActivity().getLayoutInflater(), getActivity(), userId);
        list.setAdapter(mAdapter);
        
        super.onStart();
    }

    
    
    
}
