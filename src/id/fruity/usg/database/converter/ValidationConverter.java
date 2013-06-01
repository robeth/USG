package id.fruity.usg.database.converter;

import id.fruity.usg.database.table.entry.Validation;

import java.util.ArrayList;

import android.database.Cursor;

public class ValidationConverter {
	public static Validation convert(Cursor c) {
		if (c.getCount() < 1)
			return null;
		c.moveToFirst();
		
		Validation p = new Validation(c.getString(14), c.getInt(10) == 1,
				c.getInt(11) == 1, c.getLong(12), c.getLong(13), c.getString(0),
				c.getInt(1), c.getInt(2), c.getString(3), c.getFloat(6),
				c.getFloat(7), c.getFloat(4), c.getFloat(5), c.getFloat(8),
				c.getInt(9) == 1, c.getInt(15), c.getInt(16), c.getString(17));
		c.close();
		return p;
	}

	public static ArrayList<Validation> convertAll(Cursor c) {
		c.moveToFirst();
		ArrayList<Validation> ps = new ArrayList<Validation>();
		while (!c.isAfterLast()) {
			Validation p = new Validation(c.getString(14), c.getInt(10) == 1,
					c.getInt(11) == 1, c.getLong(12), c.getLong(13), c.getString(0),
					c.getInt(1), c.getInt(2), c.getString(3), c.getFloat(6),
					c.getFloat(7), c.getFloat(4), c.getFloat(5), c.getFloat(8),
					c.getInt(9) == 1, c.getInt(15), c.getInt(16), c.getString(17));
			ps.add(p);
			c.moveToNext();
		}
		c.close();
		return ps;
	}
}
