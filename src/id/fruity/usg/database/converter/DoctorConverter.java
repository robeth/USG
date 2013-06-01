package id.fruity.usg.database.converter;

import id.fruity.usg.database.table.entry.Doctor;

import java.util.ArrayList;

import android.database.Cursor;

public class DoctorConverter {
	public static Doctor convert(Cursor c){
		if (c.getCount() < 1)
			return null;
		c.moveToFirst();
		Doctor d = new Doctor(c.getString(6), c.getInt(2) == 1, c.getInt(3) == 1, c.getLong(4), c.getLong(5), c.getString(0), c.getInt(1));
		c.close();
		return d;
	}
	public static ArrayList<Doctor> convertAll(Cursor c){
		c.moveToFirst();
		ArrayList<Doctor> ds = new ArrayList<Doctor>();
		while(!c.isAfterLast()){
			Doctor d = new Doctor(c.getString(6), c.getInt(2) == 1, c.getInt(3) == 1, c.getLong(4), c.getLong(5), c.getString(0), c.getInt(1));
			ds.add(d);
			c.moveToNext();
		}
		c.close();
		return ds;
	}
}

