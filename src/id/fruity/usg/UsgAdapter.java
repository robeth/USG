package id.fruity.usg;

import id.fruity.usg.model.KandunganModel;
import id.fruity.usg.util.DateUtils;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class UsgAdapter extends BaseExpandableListAdapter {

	private ArrayList<KandunganModel> groupItem;
	private LayoutInflater minflater;
	private Activity activity;
	

	public UsgAdapter(ArrayList<KandunganModel> grList) {
		this.groupItem = grList;
	}

	public void setInflater(LayoutInflater mInflater, Activity act) {
		this.minflater = mInflater;
		this.activity = act;
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		TextView text = null;
		if (convertView == null) {
			convertView = minflater.inflate(R.layout.usg_child_row, null);
		}
		text = (TextView) convertView.findViewById(R.id.usg_date);
		text.setText(DateUtils.getStringOfCalendarFromLong(groupItem.get(groupPosition).getFotos().get(childPosition).getCreateTimestampLong()));
		
		convertView.setOnClickListener(new PhotoOnClickListener(groupItem.get(groupPosition).getIdPasien(), groupItem.get(groupPosition).getNoKandungan(), groupItem.get(groupPosition).getFotos().get(childPosition).getNoPhoto()));
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if(groupItem.size()< 1) return 0;
		return groupItem.get(groupPosition).getFotos().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return groupItem.size();
	}

	@Override
	public void onGroupCollapsed(int groupPosition) {
		super.onGroupCollapsed(groupPosition);
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		super.onGroupExpanded(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = minflater.inflate(R.layout.usg_group_row, null);
		}
		TextView groupTitle = (TextView) convertView.findViewById(R.id.group_title);
		groupTitle.setText("Kandungan "+groupItem.get(groupPosition).getNoKandungan()+"");
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	private class PhotoOnClickListener implements OnClickListener{
		private String patientId;
		private int kandunganId;
		private int photoId;
		
		public PhotoOnClickListener(String patientId, int kandunganId, int photoId) {
			super();
			this.patientId = patientId;
			this.kandunganId = kandunganId;
			this.photoId = photoId;
		}

		@Override
		public void onClick(View v) {
			Intent i = new Intent(activity, UsgActivity.class);
			Bundle b = new Bundle();
			b.putString("patientId", patientId);
			b.putInt("kandunganId", kandunganId);
			b.putInt("photoId", photoId);
			
			i.putExtras(b);
			activity.startActivity(i);
		}
		
		
	};
}
