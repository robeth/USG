package id.fruity.usg.database.converter;

import id.fruity.usg.database.table.entry.Comment;

import java.util.ArrayList;

import android.database.Cursor;

public class CommentConverter {

	
	public static Comment convert(Cursor c){
		if (c.getCount() < 1)
			return null;
		c.moveToFirst();
		Comment k = new Comment(c.getInt(11), c.getInt(7) == 1, c.getInt(8) == 1, c.getLong(9), c.getLong(10), 
				c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getInt(4) == 1, c.getString(5), 
				c.getInt(6) == 1, c.getString(12), c.getString(13), c.getString(14));
		c.close();
		return k;
	}
	public static ArrayList<Comment> convertAll(Cursor c){
		c.moveToFirst();
		ArrayList<Comment> ks = new ArrayList<Comment>();
		while(!c.isAfterLast()){
			Comment k = new Comment(c.getInt(11), c.getInt(7) == 1, c.getInt(8) == 1, c.getLong(9), c.getLong(10), 
					c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getInt(4) == 1, c.getString(5), 
					c.getInt(6) == 1, c.getString(12), c.getString(13), c.getString(14));
			ks.add(k);
			c.moveToNext();
		}
		c.close();
		return ks;
	}
}
