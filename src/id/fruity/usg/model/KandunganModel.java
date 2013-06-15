package id.fruity.usg.model;

import id.fruity.usg.database.table.entry.Photo;

import java.util.ArrayList;

import android.database.Cursor;

public class KandunganModel extends USGModel<KandunganModel> implements Comparable<KandunganModel> {
	private String idPasien;
	private int noKandungan;
	private boolean isFinish;
	private ArrayList<Photo> fotos;

	public KandunganModel(String idPasien, int noKandungan, boolean isFinish) {
		super();
		this.idPasien = idPasien;
		this.noKandungan = noKandungan;
		this.isFinish = isFinish;
	}

	public KandunganModel() {
	}

	public String getIdPasien() {
		return idPasien;
	}

	public void setIdPasien(String idPasien) {
		this.idPasien = idPasien;
	}

	public int getNoKandungan() {
		return noKandungan;
	}

	public void setNoKandungan(int noKandungan) {
		this.noKandungan = noKandungan;
	}

	public boolean isFinish() {
		return isFinish;
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

	public ArrayList<Photo> getFotos() {
		return fotos;
	}

	public void setFotos(ArrayList<Photo> fotos) {
		this.fotos = fotos;
	}

	@Override
	public ArrayList<KandunganModel> getItems(Cursor c) {
		ArrayList<KandunganModel> kans = new ArrayList<KandunganModel>();
		c.moveToFirst();
		while (!c.isAfterLast()) {
			KandunganModel kan = cursorToItem(c);
			kans.add(kan);
			c.moveToNext();
		}
		c.close();
		return kans;
	}

	@Override
	public KandunganModel cursorToItem(Cursor c) {
		return new KandunganModel(c.getString(1), c.getInt(0), c.getInt(2) == 1);
	}

	@Override
	public int compareTo(KandunganModel another) {
		return getNoKandungan()-another.getNoKandungan();
	}
	
	

}
