package id.fruity.usg;

import id.fruity.usg.database.USGDBHelper;
import id.fruity.usg.database.converter.PatientConverter;
import id.fruity.usg.database.table.entry.Patient;
import id.fruity.usg.util.DateUtils;
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
	 private Patient p;
	 private String patientId;
	 private ImageView photoView;
	 private EditText nameText, addressText, phoneText, infoText, ktpText;
	 private String bName, bAddress, bPhone, bInfo, bKtp;
	 
	@Override
     public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         helper = USGDBHelper.getInstance(getActivity());
         helper.open();
         Bundle b = getArguments();
         patientId = b.getString("patientId");
         Log.d("Profile Fragment", "Patient ID:"+patientId);
         p = PatientConverter.convert(helper.getPasien(patientId));
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
         nameText = (EditText) v.findViewById(R.id.patient_name);
         addressText = (EditText) v.findViewById(R.id.patient_address);
         phoneText = (EditText) v.findViewById(R.id.patient_phone);
         infoText = (EditText) v.findViewById(R.id.patient_info);
         ktpText = (EditText) v.findViewById(R.id.patient_ktp);
         nameText.setText(p.getName());
         addressText.setText(p.getAddress());
         phoneText.setText(p.getPhone());
         infoText.setText(p.getDescription());
         ktpText.setText(p.getIdPasien());
         
         setEditable(false);
         
         
         return v;
     }
     
     public void setEditable(boolean status){
    	 nameText.setEnabled(status);
    	 addressText.setEnabled(status);
    	 phoneText.setEnabled(status);
    	 infoText.setEnabled(status);
    	 ktpText.setEnabled(false);
     }

	public void notifyHasEditted() {
		setEditable(false);
		long currentTime = DateUtils.getCurrentLong();
		p.setName(nameText.getText().toString());
		p.setAddress(addressText.getText().toString());
		p.setPhone(phoneText.getText().toString());
		p.setDescription(infoText.getText().toString());
		p.setDirty(true);
		p.setModifyTimestampLong(currentTime);
		helper.open();
		helper.updatePatient(p);
		helper.close();
		setEditable(false);
	}
	
	public void notifyNotEditted(){
		restoreData();
		setEditable(false);
	}

	public void backupData() {
		// TODO Auto-generated method stub
		bName = nameText.getText().toString();
		bAddress = addressText.getText().toString();
		bPhone = phoneText.getText().toString();
		bInfo = infoText.getText().toString();
		bKtp = ktpText.getText().toString();
	}
	
	private void restoreData(){
		nameText.setText(bName);
		addressText.setText(bAddress);
		phoneText.setText(bPhone);
		infoText.setText(bInfo);
		ktpText.setText(bKtp);
	}
}
