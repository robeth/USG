package id.fruity.usg;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PatientInfoAdapter extends BaseAdapter {

	private ArrayList<PatientInfo> infos;
	private LayoutInflater inflater;
	private Activity activity;
	private String userId;

	public PatientInfoAdapter(ArrayList<PatientInfo> infos,
			LayoutInflater inflater, Activity activity, String userId) {
		super();
		this.infos = infos;
		this.inflater = inflater;
		this.activity = activity;
		this.userId = userId;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return infos.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.patient_row, null);
		}

		TextView name = (TextView) convertView.findViewById(R.id.patient_name); // title
		TextView frequency = (TextView) convertView
				.findViewById(R.id.patient_freq); // artist name
		TextView lastUpdate = (TextView) convertView
				.findViewById(R.id.patient_last_date); // duration
		ImageView patientPhoto = (ImageView) convertView
				.findViewById(R.id.patient_photo); // thumb image
		PatientInfo item = infos.get(position);

		// Setting all values in list view
		name.setText(item.getName());
		frequency.setText(item.getNumberOfUSG() + " foto");
		lastUpdate.setText(item.getLastCheck());
		patientPhoto.setImageResource(item.getPhotoId());
		convertView.setOnClickListener(new PatientClickListener(item.getPatientId()));
		return convertView;
	}
	
	private class PatientClickListener implements OnClickListener {
		private String patientId;
		
		public PatientClickListener(String patientId){
			this.patientId = patientId;
		}
		
		@Override
		public void onClick(View v) {
			Intent i = new Intent(activity, PatientActivity.class);
			Bundle b = new Bundle();
			b.putString("patientId", patientId);
			b.putString("userId", userId);
			
			
			i.putExtras(b);
			activity.startActivity(i);
		}
	}; 

}
