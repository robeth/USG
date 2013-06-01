package id.fruity.usg.database.converter;

import id.fruity.usg.database.table.entry.WorksOn;

import java.util.ArrayList;

import android.database.Cursor;

public class WorksOnConverter {
	public static WorksOn convert(Cursor c) {
		if (c.getCount() < 1)
			return null;
		c.moveToFirst();
		WorksOn bj = new WorksOn(c.getString(6), c.getInt(2) == 1,
				c.getInt(3) == 1, c.getLong(4), c.getLong(5), c.getString(0),
				c.getInt(1), c.getInt(7));
		c.close();
		return bj;
	}

	public static ArrayList<WorksOn> convertAll(Cursor c) {
		c.moveToFirst();
		ArrayList<WorksOn> bjs = new ArrayList<WorksOn>();
		while (!c.isAfterLast()) {
			WorksOn bj = new WorksOn(c.getString(6), c.getInt(2) == 1,
					c.getInt(3) == 1, c.getLong(4), c.getLong(5), c.getString(0),
					c.getInt(1), c.getInt(7));
			bjs.add(bj);
			c.moveToNext();
		}
		c.close();
		return bjs;
	}
}