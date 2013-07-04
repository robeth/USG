package id.fruity.usg;

import id.fruity.usg.database.table.entry.Photo;
import id.fruity.usg.model.KandunganModel;
import id.fruity.usg.util.Analyzer;
import id.fruity.usg.util.DateUtils;
import id.fruity.usg.util.SDUtils;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class UsgAdapter extends BaseExpandableListAdapter {

	private ArrayList<KandunganModel> groupItem;
	private LayoutInflater minflater;
	private Activity activity;
	private boolean isDoctor;

	public UsgAdapter(ArrayList<KandunganModel> grList, boolean isDoctor) {
		this.groupItem = grList;
		this.isDoctor = isDoctor;
		
	}

	public void setInflater(LayoutInflater mInflater, Activity act) {
		this.minflater = mInflater;
		this.activity = act;
	}

	@Override
	public Photo getChild(int groupPosition, int childPosition) {
		return groupItem.get(groupPosition).getFotos().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {

		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		TextView text = null;
		ImageView i = null;
		ImageView check = null;
		if (convertView == null) {
			convertView = minflater.inflate(R.layout.usg_child_row, null);
		}
		Photo temp = groupItem.get(groupPosition).getFotos().get(childPosition);
		text = (TextView) convertView.findViewById(R.id.usg_date);
		i = (ImageView) convertView.findViewById(R.id.usg_image);
		check = (ImageView) convertView.findViewById(R.id.usg_accepted);
		text.setText(DateUtils.getStringOfCalendarFromLong(temp.getCreateTimestampLong()));
		Drawable d = SDUtils.getDrawable(activity, SDUtils.getSmallPhotoBitmap(SDUtils.getAbsolutePath(temp.getFilename())));
		i.setImageDrawable(d);
		check.setVisibility(groupItem.get(groupPosition).getValidateStatuses().get(childPosition) ? View.VISIBLE : View.INVISIBLE);
//		 convertView.setClickable(true)l
//		convertView.setLongClickable(true);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if(groupItem.size() == 0) return 0;
		return groupItem.get(groupPosition).getFotos().size();
	}

	@Override
	public KandunganModel getGroup(int groupPosition) {
		return groupItem.get(groupPosition);
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
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		
		if (convertView == null) {
			convertView = minflater.inflate(R.layout.usg_group_row, null);
		}
		TextView groupTitle = (TextView) convertView.findViewById(R.id.group_title);
		ImageView indicatorImage = (ImageView) convertView.findViewById(R.id.group_indicator);
		groupTitle.setText("Kandungan "+groupItem.get(groupPosition).getNoKandungan()+"");
		
		int status = groupItem.get(groupPosition).getStatus();
		if(status == Analyzer.NO_GROWTH){
			indicatorImage.setImageResource(R.drawable.circle_red_indicator);
		} else if (status == Analyzer.OVERGROWTH || status == Analyzer.UNDER_GROWTH) {
			indicatorImage.setImageResource(R.drawable.circle_yellow_indicator);
		} else if (status == Analyzer.NORMAL_GROWTH) {
			indicatorImage.setImageResource(R.drawable.circle_indicator);
		}
		

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

	

}
