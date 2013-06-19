package id.fruity.usg.database.converter;

import id.fruity.usg.database.table.entry.Patient;

import java.util.ArrayList;

import android.database.Cursor;

public class PatientConverter {
	public static Patient convert(Cursor c){
		if (c.getCount() < 1)
			return null;
		c.moveToFirst();
		Patient p = new Patient(c.getString(11), c.getInt(7) == 1, c.getInt(8) == 1, c.getLong(9), c.getLong(10), 
				c.getString(0), c.getString(1), c.getString(2),c.getString(3), c.getLong(4), c.getString(5), c.getString(6), 
				c.getLong(12), c.getLong(13));
		c.close();
		return p;
	}
	
	public static ArrayList<Patient> convertAll(Cursor c){
		c.moveToFirst();
		ArrayList<Patient> ps = new ArrayList<Patient>();
		while(!c.isAfterLast()){
			Patient p = new Patient(c.getString(11), c.getInt(7) == 1, c.getInt(8) == 1, c.getLong(9), c.getLong(10), 
					c.getString(0), c.getString(1), c.getString(2),c.getString(3), c.getLong(4), c.getString(5), c.getString(6),
					c.getLong(12), c.getLong(13));
			ps.add(p);
			c.moveToNext();
		}
		c.close();
		return ps;
	}
}
