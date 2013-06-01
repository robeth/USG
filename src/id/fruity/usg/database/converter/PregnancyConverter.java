package id.fruity.usg.database.converter;

import id.fruity.usg.database.table.entry.Pregnancy;

import java.util.ArrayList;

import android.database.Cursor;

public class PregnancyConverter {
	public static Pregnancy convert(Cursor c){
		if (c.getCount() < 1)
			return null;
		c.moveToFirst();
		Pregnancy k = new Pregnancy(c.getInt(7), c.getInt(2) == 1, c.getInt(3)==1, c.getLong(5), c.getLong(6), c.getInt(0), c.getString(1), c.getInt(2) == 1, c.getString(8));
		c.close();
		return k;
	}
	
	public static ArrayList<Pregnancy> convertAll(Cursor c){
		c.moveToFirst();
		ArrayList<Pregnancy> ks = new ArrayList<Pregnancy>();
		while(!c.isAfterLast()){
			Pregnancy k = new Pregnancy(c.getInt(7), c.getInt(2) == 1, c.getInt(3)==1, c.getLong(5), c.getLong(6), c.getInt(0), c.getString(1), c.getInt(2) == 1, c.getString(8));
			ks.add(k);
			c.moveToNext();
		}
		c.close();
		return ks;
	}
}
