package id.fruity.usg;

import id.fruity.usg.database.USGDBHelper;
import id.fruity.usg.model.PasienModel;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;

public class PatientProfileFragment extends SherlockFragment {
	 private USGDBHelper helper;
	 private PasienModel pm;
	 private String patientId;
	 private ImageView photoView;
	 private TextView ageText;
	 private EditText nameText, addressText, phoneText, infoText;
	 
	@Override
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         helper = new USGDBHelper(getActivity());
         helper.open();
         Bundle b = getArguments();
         patientId = b.getString("patientId");
         Log.d("Profile Fragment", "Patient ID:"+patientId);
         
         PasienModel p = new PasienModel();
         Cursor c = helper.getPasien(patientId);
         c.moveToFirst();
         pm = p.cursorToItem(c);
         c.close();
         helper.close();
     }

     /**
      * The Fragment's UI is just a simple text view showing its
      * instance number.
      */
     @Override
     public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {
         View v = inflater.inflate(R.layout.patient_profile, container, false);
         photoView = (ImageView) v.findViewById(R.id.patient_photo);
         ageText = (TextView) v.findViewById(R.id.patient_age);
         nameText = (EditText) v.findViewById(R.id.patient_name);
         addressText = (EditText) v.findViewById(R.id.patient_address);
         phoneText = (EditText) v.findViewById(R.id.patient_phone);
         infoText = (EditText) v.findViewById(R.id.patient_info);
         
         nameText.setText(pm.getName());
         addressText.setText(pm.getAddress());
         phoneText.setText(pm.getPhone());
         infoText.setText(pm.getDescription());
         
         setEditable(false);
         
         
         return v;
     }
     
     public void setEditable(boolean status){
    	 nameText.setEnabled(status);
    	 nameText.setFocusable(status);
    	 addressText.setEnabled(status);
    	 addressText.setEnabled(status);
    	 phoneText.setEnabled(status);
    	 phoneText.setEnabled(status);
    	 infoText.setEnabled(status);
    	 infoText.setEnabled(status);
    	 
    	 
     }
}
