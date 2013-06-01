package id.fruity.usg.model;

import java.util.ArrayList;

import android.database.Cursor;

public abstract class USGModel<T> {
	public abstract ArrayList<T> getItems(Cursor c);
	public abstract T cursorToItem(Cursor c);
}
