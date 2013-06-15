package id.fruity.usg;

import id.fruity.usg.database.USGDBHelper;
import id.fruity.usg.database.converter.PatientConverter;
import id.fruity.usg.database.table.entry.Comment;
import id.fruity.usg.database.table.entry.Patient;
import id.fruity.usg.model.KomentarModel;
import id.fruity.usg.util.DateUtils;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

public class MessageFragment extends SherlockFragment {
	private ArrayList<Message> messages;
	private MessageAdapter mAdapter;
	private ListView messageList;
	private EditText inputMessage;
	private Button sendButton;
	private USGDBHelper helper;
	private String patientId;
	private String petugasId;
	private Patient p;
	private ArrayList<KomentarModel> kms;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        patientId = b.getString("patientId");
        petugasId = b.getString("userId");
        Log.d("Message Fragment", "Patient ID:"+patientId+" - PetugasId:"+petugasId);
        
        helper = USGDBHelper.getInstance(getActivity());
        helper.open();
        p = PatientConverter.convert(helper.getPasien(patientId));
        KomentarModel k = new KomentarModel();
        kms = k.getItems(helper.getKomentarOf(patientId));
        Log.d("Message Fragment", "Komentar size:"+kms.size());
        helper.close();
        
    }

    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.patient_message, container, false);
        messageList = (ListView) v.findViewById(R.id.message_list);
        inputMessage = (EditText) v.findViewById(R.id.message_input);
        sendButton = (Button) v.findViewById(R.id.message_send);
        sendButton.setOnClickListener(sendListener);
        
        messages = new ArrayList<Message>();
        for(int i = 0; i < kms.size();i++){
        	if(kms.get(i).isFromDokter()){
        		messages.add(new Message(kms.get(i).getContent(), DateUtils.getStringOfCalendarFromLong(kms.get(i).getSentDate()), kms.get(i).getDoctorName(), R.drawable.doctor_icon, !kms.get(i).isFromDokter()));
        	} else {
        		messages.add(new Message(kms.get(i).getContent(), DateUtils.getStringOfCalendarFromLong(kms.get(i).getSentDate()), p.getName(), R.drawable.mother_icon, !kms.get(i).isFromDokter()));
        	}
        }
//        messages.add(new Message("Hai Pasien. Bagaimana Kabar?", "1 Januari 2013 7.40 am", "dr. Rapier", R.drawable.doctor_icon, false));
//        messages.add(new Message("Akika baik, dok", "2 Januari 2013 10.40 am", "Pasien 1", R.drawable.mother_icon, true));
//        messages.add(new Message("Belakangan akika sakit flu.. ada saran, dok?", "4 Januari 2013 11.40 am", "Pasien 1", R.drawable.mother_icon, true));
//        messages.add(new Message("Hmm. Kamu beli P*n*dol saja ya", "4 Januari 2013 2.40 pm", "dr. Rapier", R.drawable.doctor_icon, false));
        
        mAdapter = new MessageAdapter(messages, inflater, getActivity());
        messageList.setAdapter(mAdapter);
        
        messageList.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        messageList.setStackFromBottom(true);
        return v;
    }
    
    private OnClickListener sendListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			helper.open();
			Long currentMillis = DateUtils.getCurrentLong();
			String content = inputMessage.getText().toString();
			int lastMessageIndex = helper.getLastIndexofKomentar(patientId);
			Comment k = new Comment(-1, true, true, currentMillis, currentMillis, lastMessageIndex+1, "-1", ""+patientId, ""+petugasId, false, content, false, "-1", "-1", "-1");
			helper.insertComment(k);
			helper.close();
			
			messages.add(new Message(inputMessage.getText().toString(), DateUtils.getCurrentString(), "Pasien 1", R.drawable.mother_icon, true));
			mAdapter.notifyDataSetChanged();
		}
	};
	
	

	@Override
	public void onDestroy() {
		helper.close();
		super.onDestroy();
	}}
