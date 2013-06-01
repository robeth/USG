package id.fruity.usg.database.converter;

import id.fruity.usg.database.table.entry.Photo;

import java.util.ArrayList;

import android.database.Cursor;

public class PhotoConverter {
	
	public static Photo convert(Cursor c) {
		if (c.getCount() < 1)
			return null;
		c.moveToFirst();
		Photo p = new Photo(c.getInt(17), c.getInt(13) == 1, c.getInt(14) == 1, c.getLong(15), c.getLong(16), c.getInt(0), c.getLong(4), c.getString(5), 
				c.getFloat(6), c.getFloat(7), c.getFloat(8), c.getFloat(9), c.getFloat(10), c.getFloat(11), c.getString(12), 
				c.getString(1), c.getInt(2), c.getString(3), c.getString(18), c.getInt(19), c.getString(20));
		c.close();
		return p;
	}

	public static ArrayList<Photo> convertAll(Cursor c) {
		c.moveToFirst();
		ArrayList<Photo> ps = new ArrayList<Photo>();
		while (!c.isAfterLast()) {
			Photo p = new Photo(c.getInt(17), c.getInt(13) == 1, c.getInt(14) == 1, c.getLong(15), c.getLong(16), c.getInt(0), c.getLong(4), c.getString(5), 
					c.getFloat(6), c.getFloat(7), c.getFloat(8), c.getFloat(9), c.getFloat(10), c.getFloat(11), c.getString(12), 
					c.getString(1), c.getInt(2), c.getString(3), c.getString(18), c.getInt(19), c.getString(20));
			ps.add(p);
			c.moveToNext();
		}
		c.close();
		return ps;
	}
}
