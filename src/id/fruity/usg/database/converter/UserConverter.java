package id.fruity.usg.database.converter;

import id.fruity.usg.database.table.entry.User;

import java.util.ArrayList;

import android.database.Cursor;

public class UserConverter {
	
	public static User convert(Cursor c) {
		if (c.getCount() < 1)
			return null;
		c.moveToFirst();
		User u = new User(c.getString(12), c.getInt(8) == 1, c.getInt(9) == 1, c.getLong(10), c.getLong(11), c.getString(0), c.getString(1), 
				c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7));
		c.close();
		return u;
	}

	public static ArrayList<User> convertAll(Cursor c) {
		c.moveToFirst();
		ArrayList<User> us = new ArrayList<User>();
		while (!c.isAfterLast()) {
			User u = new User(c.getString(12), c.getInt(8) == 1, c.getInt(9) == 1, c.getLong(10), c.getLong(11), c.getString(0), c.getString(1), 
					c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6), c.getString(7));
			us.add(u);
			c.moveToNext();
		}
		c.close();
		return us;
	}
}
