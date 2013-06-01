package id.fruity.usg;

import java.util.ArrayList;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MessageAdapter extends BaseAdapter {
	private ArrayList<Message> messages;
	private LayoutInflater inflater;
	private Activity activity;

	public MessageAdapter(ArrayList<Message> messages, LayoutInflater inflater, Activity activity) {
		super();
		this.messages = messages;
		this.inflater = inflater;
		this.activity = activity;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return messages.size();
	}

	@Override
	public Object getItem(int index) {
		// TODO Auto-generated method stub
		return messages.get(index);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
            convertView = inflater.inflate(R.layout.message_row, null);
		}
        
		TextView sender = (TextView)convertView.findViewById(R.id.message_sender); // title
        TextView content = (TextView)convertView.findViewById(R.id.message_content); // artist name
        TextView date = (TextView)convertView.findViewById(R.id.message_date); // duration
        ImageView senderPhoto=(ImageView)convertView.findViewById(R.id.message_photo); // thumb image
        RelativeLayout messageContainer = (RelativeLayout) convertView.findViewById(R.id.message_container);
        Message item = messages.get(position);
 
        // Setting all values in listview
        sender.setText(item.getSender());
        content.setText(item.getContent());
        date.setText(item.getDate());
        senderPhoto.setImageResource(item.getImageId());
        if(item.isMine()){
        	
        	((LinearLayout)(convertView)).setGravity(Gravity.RIGHT);
        	
        } else {
        	((LinearLayout)(convertView)).setGravity(Gravity.LEFT);
        }
        
        DisplayMetrics dm = new DisplayMetrics();
    	activity.getWindow().getWindowManager().getDefaultDisplay().getMetrics(dm);
    	int width = (int)(0.6 * dm.widthPixels);
    	sender.setMaxWidth(width);
    	content.setMaxWidth(width);
    	
    	
		return convertView;
	}

}
