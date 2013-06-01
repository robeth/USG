package id.fruity.usg.model;

import java.util.ArrayList;

import android.database.Cursor;

public class KomentarModel extends USGModel<KomentarModel> {
	private String doctorName;
	private boolean isFromDokter;
	private String content;
	private Long sentDate;
	private boolean hasSeen;
	
	

	public KomentarModel(String doctorName, boolean isFromDokter,
			String content, Long sentDate, boolean hasSeen) {
		super();
		this.doctorName = doctorName;
		this.isFromDokter = isFromDokter;
		this.content = content;
		this.sentDate = sentDate;
		this.hasSeen = hasSeen;
	}


	public KomentarModel() {
	}


	public String getDoctorName() {
		return doctorName;
	}


	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}


	public boolean isFromDokter() {
		return isFromDokter;
	}


	public void setFromDokter(boolean isFromDokter) {
		this.isFromDokter = isFromDokter;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Long getSentDate() {
		return sentDate;
	}


	public void setSentDate(Long sentDate) {
		this.sentDate = sentDate;
	}


	public boolean isHasSeen() {
		return hasSeen;
	}


	public void setHasSeen(boolean hasSeen) {
		this.hasSeen = hasSeen;
	}


	@Override
	public ArrayList<KomentarModel> getItems(Cursor c) {
		ArrayList<KomentarModel> koms = new ArrayList<KomentarModel>();
		c.moveToFirst();
		while(!c.isAfterLast()){
			KomentarModel kom = cursorToItem(c);
			koms.add(kom);
			c.moveToNext();
		}
		c.close();
		return koms;
	}

	@Override
	public KomentarModel cursorToItem(Cursor c) {
		return new KomentarModel(c.getString(0), c.getInt(1) == 1, c.getString(2), c.getLong(3), c.getInt(4) == 1);
	}

}
