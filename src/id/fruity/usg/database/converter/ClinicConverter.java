package id.fruity.usg.database.converter;

import id.fruity.usg.database.table.entry.Clinic;

import java.util.ArrayList;

import android.database.Cursor;

public class ClinicConverter {

	public static Clinic convert(Cursor c) {
		if (c.getCount() < 1)
			return null;
		c.moveToFirst();
		
		Clinic p = new Clinic(c.getInt(11), c.getInt(7) == 1, c.getInt(8) == 1,
				c.getLong(9), c.getLong(10), c.getInt(0), c.getString(1),
				c.getString(2), c.getString(3), c.getString(4), c.getString(5),
				c.getString(6));
		c.close();
		return p;
	}

	public static ArrayList<Clinic> convertAll(Cursor c) {
		c.moveToFirst();
		ArrayList<Clinic> ps = new ArrayList<Clinic>();
		while (!c.isAfterLast()) {
			Clinic p = new Clinic(c.getInt(11), c.getInt(7) == 1,
					c.getInt(8) == 1, c.getLong(9), c.getLong(10), c.getInt(0),
					c.getString(1), c.getString(2), c.getString(3),
					c.getString(4), c.getString(5), c.getString(6));
			ps.add(p);
			c.moveToNext();
		}
		c.close();
		return ps;
	}
}
