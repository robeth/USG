package id.fruity.usg;

import id.fruity.usg.database.USGDBHelper;
import id.fruity.usg.database.converter.PhotoConverter;
import id.fruity.usg.database.table.entry.Photo;
import id.fruity.usg.database.table.entry.Pregnancy;
import id.fruity.usg.model.KandunganModel;
import id.fruity.usg.util.DateUtils;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class PatientUSGFragment extends SherlockFragment {
	private ExpandableListView elv;
	private ArrayList<KandunganModel> kms;
	private USGDBHelper helper;
	private String patientId;
	private String userId;
	private boolean isDoctor;
	private Dialog usgDialog;
	private int chosenNoKandungan;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getArguments();
		patientId = b.getString("patientId");
		userId = b.getString("userId");
		isDoctor = b.getBoolean("isDoctor");
		helper = USGDBHelper.getInstance(getActivity());
		helper.open();

		KandunganModel km = new KandunganModel();
		kms = km.getItems(helper.getKandunganOfPatient(patientId));

		for (int i = 0; i < kms.size(); i++) {
			ArrayList<Photo> photos = PhotoConverter.convertAll(helper
					.getPhotosOfKandungan(patientId, kms.get(i)
							.getNoKandungan()));
			Log.d("Pasien USG Fragment", "Photos size:" + photos.size());
			kms.get(i).setFotos(photos);
		}
		helper.close();

	}

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.patient_usg, container, false);
		elv = (ExpandableListView) v.findViewById(R.id.usg_group);
		elv.setClickable(true);
		elv.setDividerHeight(2);
		UsgAdapter adapter = new UsgAdapter(kms,isDoctor);
		adapter.setInflater(inflater, getActivity());
		
		elv.setOnItemLongClickListener(new OnItemLongClickListener() {
		    @Override
		    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
		        if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
		            int groupPosition = ExpandableListView.getPackedPositionGroup(id);
		            int childPosition = ExpandableListView.getPackedPositionChild(id);
		            Toast.makeText(getActivity(), "child "+ position+" "+groupPosition+" "+childPosition, Toast.LENGTH_SHORT).show();
		            Photo p = ((UsgAdapter)elv.getExpandableListAdapter()).getChild(groupPosition, childPosition);
		            popUpDeletePhotoDialog(p.getIdPasien(), p.getNoKandungan(), p.getNoPhoto());
		            return true;
		        } else if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_GROUP){
		        	int groupPosition = ExpandableListView.getPackedPositionGroup(id);
		        	KandunganModel km = ((UsgAdapter)elv.getExpandableListAdapter()).getGroup(groupPosition);
		        	Toast.makeText(getActivity(), "group "+position+" "+groupPosition, Toast.LENGTH_SHORT).show();
		        	popUpDeletePregnancyDialog(km.getIdPasien(), km.getNoKandungan());
		        	return true;
		        }

		        return false;
		    }
		});
		elv.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Log.d("ChildClick", groupPosition+" "+childPosition );
				Photo p = ((UsgAdapter)elv.getExpandableListAdapter()).getChild(groupPosition, childPosition);
	            Intent i = new Intent(PatientUSGFragment.this.getActivity(), UsgActivity.class);
				Bundle b = new Bundle();
				b.putString("patientId", patientId);
				b.putInt("pregnancyId", p.getNoKandungan());
				b.putInt("photoId", p.getNoPhoto());
				b.putBoolean("isDoctor", isDoctor);
				b.putString("userId", userId);
				i.putExtras(b);
				PatientUSGFragment.this.getActivity().startActivity(i);
				return true;
			}
		});

		
		elv.setAdapter(adapter);
		elv.expandGroup(0);
		return v;
	}

	private void refreshData() {
		helper.open();
		KandunganModel km = new KandunganModel();
		kms = km.getItems(helper.getKandunganOfPatient(patientId));

		for (int i = 0; i < kms.size(); i++) {
			ArrayList<Photo> fms = PhotoConverter.convertAll(helper
					.getPhotosOfKandungan(patientId, kms.get(i)
							.getNoKandungan()));
			Log.d("Pasien USG Fragment", "Photos size:" + fms.size());
			kms.get(i).setFotos(fms);
		}
		helper.close();
		UsgAdapter adapter = new UsgAdapter(kms, isDoctor);
		adapter.setInflater(getActivity().getLayoutInflater(), getActivity());
		elv.setAdapter(adapter);
	}

	public void popUpAddKandunganDialog() {
		AlertDialog.Builder alert = new AlertDialog.Builder(this.getActivity());
		alert.setTitle("Tambah Kandungan");
		alert.setMessage("Kandungan ke-");

		// Set an EditText view to get user input
		final EditText input = new EditText(this.getActivity());
		input.setFilters(new InputFilter[] {
				// Maximum 2 characters.
				new InputFilter.LengthFilter(2),
				// Digits only.
				DigitsKeyListener.getInstance(), // Not strictly needed, IMHO.
		});

		// Digits only & use numeric soft-keyboard.
		input.setKeyListener(DigitsKeyListener.getInstance());
		alert.setView(input);
		alert.setPositiveButton("Tambah",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						int noKandungan = Integer.parseInt(input.getText()
								.toString().trim());
						helper.open();
						boolean isExist = helper.isKandunganExist(patientId,
								noKandungan);

						if (!isExist) {
							long currentTime = DateUtils.getCurrentLong();
							Pregnancy k = new Pregnancy(-1, true, true,
									currentTime, currentTime, noKandungan,
									""+patientId, false, "-1");
							helper.insertPregnancy(k);
							refreshData();
						} else {
							Toast.makeText(
									PatientUSGFragment.this.getActivity(),
									"Kandungan ke-" + noKandungan
											+ " sudah ada", Toast.LENGTH_SHORT)
									.show();
						}
						helper.close();
					}
				});

		alert.setNegativeButton("Batal", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// do nothing
			}
		});
		alert.show();
	}

	public void popUpUSGDialog() {
		if(kms.isEmpty()){
			Toast.makeText(this.getActivity(), "Please add pregnancy data first. No pregnancy data exists.", Toast.LENGTH_SHORT).show();
			return;
		}
		AlertDialog.Builder builder = new AlertDialog.Builder(
				this.getActivity());
		builder.setTitle("Pilih Kandungan");

		ListView modeList = new ListView(this.getActivity());
		String[] stringArray = new String[kms.size()];
		for (int i = 0; i < kms.size(); i++) {
			stringArray[i] = "Kandungan " + kms.get(i).getNoKandungan();
		}
		ArrayAdapter<String> modeAdapter = new ArrayAdapter<String>(
				this.getActivity(), android.R.layout.simple_list_item_1,
				android.R.id.text1, stringArray);
		modeList.setAdapter(modeAdapter);
		modeList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				chosenNoKandungan = kms.get(position).getNoKandungan();
				Toast.makeText(getActivity(),
						"Chosen kandungan: " + chosenNoKandungan,
						Toast.LENGTH_SHORT).show();
				usgDialog.dismiss();
				((PatientActivity) getActivity()).sendImageRequestDummy(patientId,chosenNoKandungan);
			}

		});

		builder.setView(modeList);
		usgDialog = builder.create();

		usgDialog.show();
	}

	public void notifyImageCaptured(String absolutePath) {
		helper.open();
		long currentTime = DateUtils.getCurrentLong();
		int lastIndex = helper.getLastIndexOfPhotos(patientId,
				chosenNoKandungan);
		
		Photo uf = new Photo(-1, true, true, currentTime, currentTime,
				lastIndex + 1, -1, absolutePath, -1, -1, -1, -1, -1, -1,
				"Not yet", patientId, chosenNoKandungan, userId,
				"-1", -1, "-1", currentTime, -1);
		helper.insertPhoto(uf);
		helper.close();
		refreshData();
	}
	
	private void popUpDeletePregnancyDialog(final String iPatientId, final int iPregnancyId) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this.getActivity());
		alert.setTitle("Delete Pregnancy "+iPregnancyId);
		alert.setMessage("All related USG photo data will also be deleted.");

		alert.setPositiveButton("Confirm",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						helper.open();
						helper.deletePregnancy(iPatientId, iPregnancyId);
						helper.close();
						refreshData();
					}
				});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// do nothing
			}
		});
		alert.show();
	}
	
	private void popUpDeletePhotoDialog(final String iPatientId, final int iPregnancyId, final int iPhotoId) {
		AlertDialog.Builder alert = new AlertDialog.Builder(this.getActivity());
		alert.setTitle("Delete USG Photo");
		alert.setMessage("This USG photo will be deleted.");

		alert.setPositiveButton("Confirm",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						helper.open();
						helper.deletePhoto(iPatientId, iPregnancyId, iPhotoId);
						helper.close();
						refreshData();
					}
				});

		alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				// do nothing
			}
		});
		alert.show();
	}
}
