package id.fruity.usg;

import id.fruity.usg.database.table.entry.Validation;

import java.util.ArrayList;

import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ValidationSpinnerAdapter extends BaseAdapter{
	private ArrayList<Validation> validations;
	private LayoutInflater minflater;
	
	 public ValidationSpinnerAdapter(ArrayList<Validation> validations, LayoutInflater minflater){
		 this.validations = validations;
		 this.minflater = minflater;
	 }
	 
	@Override
	public int getCount() {
		return validations.size();
	}

	@Override
	public Validation getItem(int position) {
		return validations.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = minflater.inflate(R.layout.pregnancy_item, null);
		}
		TextView text = (TextView) convertView.findViewById(R.id.pregnancy_text);
		Validation v = validations.get(position);
		if(v.getIdDokter().equals("dummy")){
			text.setText(v.getNoPasien());
		} else {
			text.setText(v.getIdDokter());
		}
		return convertView;
	}

	@Override
	public int getViewTypeCount() {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isEmpty() {
		return validations.isEmpty();
	}

	@Override
	public void registerDataSetObserver(DataSetObserver observer) {
		
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		return getView(position, convertView, parent);
	}

}
