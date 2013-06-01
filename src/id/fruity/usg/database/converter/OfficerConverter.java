package id.fruity.usg.database.converter;

import id.fruity.usg.database.table.entry.Officer;

import java.util.ArrayList;

import android.database.Cursor;

public class OfficerConverter {
	public static Officer convert(Cursor c) {
		if (c.getCount() < 1)
			return null;
		c.moveToFirst();
		Officer p = new Officer(c.getString(7), c.getInt(3) == 1,
				c.getInt(4) == 1, c.getLong(5), c.getLong(6), c.getString(0),
				c.getInt(1), c.getInt(2), c.getInt(8));
		c.close();
		return p;
	}

	public static ArrayList<Officer> convertAll(Cursor c) {
		c.moveToFirst();
		ArrayList<Officer> ps = new ArrayList<Officer>();
		while (!c.isAfterLast()) {
			Officer p = new Officer(c.getString(7), c.getInt(3) == 1,
					c.getInt(4) == 1, c.getLong(5), c.getLong(6), c.getString(0),
					c.getInt(1), c.getInt(2), c.getInt(8));
			ps.add(p);
			c.moveToNext();
		}
		c.close();
		return ps;
	}
}
