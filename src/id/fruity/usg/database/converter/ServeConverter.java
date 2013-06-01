package id.fruity.usg.database.converter;

import id.fruity.usg.database.table.entry.Serve;

import java.util.ArrayList;

import android.database.Cursor;

public class ServeConverter {
	public static Serve convert(Cursor c){
		if (c.getCount() < 1)
			return null;
		c.moveToFirst();
		Serve m = new Serve(c.getInt(6), c.getInt(2) == 1, c.getInt(3) == 1, c.getLong(4), c.getLong(5), c.getInt(0), c.getString(1), c.getString(7));
		c.close();
		return m;
	}
	public static ArrayList<Serve> convertAll(Cursor c){
		c.moveToFirst();
		ArrayList<Serve> ms = new ArrayList<Serve>();
		while(!c.isAfterLast()){
			Serve m = new Serve(c.getInt(6), c.getInt(2) == 1, c.getInt(3) == 1, c.getLong(4), c.getLong(5), c.getInt(0), c.getString(1), c.getString(7));
			ms.add(m);
			c.moveToNext();
		}
		c.close();
		return ms;
	}
}
