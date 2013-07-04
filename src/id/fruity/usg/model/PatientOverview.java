package id.fruity.usg.model;

import java.util.ArrayList;
import java.util.Comparator;



import android.database.Cursor;
import android.util.Log;

public class PatientOverview extends USGModel implements Comparable<PatientOverview>{
	private String idPasien;
	private String name;
	private long lastPhoto;
	private int newMessageCount;
	private int newValidasiCount;
	private int totalPhoto;
	
	public PatientOverview(){
		
	}
	
	public PatientOverview(String idPasien, String name, long lastPhoto, int newMessageCount,
			int newValidasiCount, int totalPhoto) {
		super();
		
		this.idPasien = idPasien;
		this.name = name;
		this.lastPhoto = lastPhoto;
		this.newMessageCount = newMessageCount;
		this.newValidasiCount = newValidasiCount;
		this.totalPhoto = totalPhoto;
		Log.d("Patient Overview", this.toString());
	}



	public String getIdPasien() {
		return idPasien;
	}



	public void setIdPasien(String idPasien) {
		this.idPasien = idPasien;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public long getLastPhoto() {
		return lastPhoto;
	}



	public void setLastPhoto(long lastPhoto) {
		this.lastPhoto = lastPhoto;
	}



	public int getNewMessageCount() {
		return newMessageCount;
	}



	public void setNewMessageCount(int newMessageCount) {
		this.newMessageCount = newMessageCount;
	}



	public int getNewValidasiCount() {
		return newValidasiCount;
	}



	public void setNewValidasiCount(int newValidasiCount) {
		this.newValidasiCount = newValidasiCount;
	}



	public int getTotalPhoto() {
		return totalPhoto;
	}



	public void setTotalPhoto(int totalPhoto) {
		this.totalPhoto = totalPhoto;
	}



	@Override
	public ArrayList<PatientOverview> getItems(Cursor c) {
		ArrayList<PatientOverview> pos = new ArrayList<PatientOverview>();
		c.moveToFirst();
		while(!c.isAfterLast()){
			PatientOverview po = cursorToItem(c);
			pos.add(po);
			c.moveToNext();
		}
		c.close();
		return pos;
	}

	@Override
	public PatientOverview cursorToItem(Cursor c) {
		return new PatientOverview(c.getString(0), c.getString(1), c.getLong(3), c.getInt(4), c.getInt(5), c.getInt(2));
	}

	@Override
	public String toString() {
		return "PatientOverview [idPasien=" + idPasien + ", name=" + name
				+ ", lastPhoto=" + lastPhoto + ", newMessageCount="
				+ newMessageCount + ", newValidasiCount=" + newValidasiCount
				+ ", totalPhoto=" + totalPhoto + "]";
	}

	@Override
	public int compareTo(PatientOverview another) {		
		return this.name.compareTo(another.name);
	}
	
	public static Comparator<PatientOverview> PatientLastPhoto = new Comparator<PatientOverview>() {
		
	
		@Override
		public int compare(PatientOverview lhs, PatientOverview rhs) {
			// TODO Auto-generated method stub
			long tanggal1 = lhs.lastPhoto;
			long tanggal2 = rhs.lastPhoto;
			return (int) (tanggal2-tanggal1);
		}

	};
	
	
	
}
