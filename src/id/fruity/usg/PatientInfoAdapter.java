package id.fruity.usg;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
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
	private PatientInfoAdapterInterface adapterInterface;

	public PatientInfoAdapter(ArrayList<PatientInfo> infos,
			LayoutInflater inflater, Activity activity, String userId, PatientInfoAdapterInterface adapterInterface) {
		super();
		this.infos = infos;
		this.inflater = inflater;
		this.activity = activity;
		this.userId = userId;
		this.adapterInterface = adapterInterface;
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
		convertView.setOnLongClickListener(new PatientLongClickListener(item.getPatientId(), item.getName()));
		return convertView;
	}
	
	private class PatientClickListener implements OnClickListener {
		private String patientId;
		
		public PatientClickListener(String patientId){
			this.patientId = patientId;
		}
		
		@Override
		public void onClick(View v) {
			if(adapterInterface != null){
				adapterInterface.onClick(patientId,userId);
				return;
			}
		}
	};
	
	private class PatientLongClickListener implements OnLongClickListener {
		private String patientId;
		private String patientName;
		
		public PatientLongClickListener(String patientId, String patientName){
			this.patientId = patientId;
			this.patientName = patientName;
		}

		@Override
		public boolean onLongClick(View v) {
			if(adapterInterface != null){
				adapterInterface.onLongPress(patientId, patientName);
			}
			return true;
		}
	};
	
	public static abstract class PatientInfoAdapterInterface{
		public abstract void onClick(String patientId, String userId);
		public abstract void onLongPress(String patientId, String patientName);
	}

}
