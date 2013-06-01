package id.fruity.usg.database.table;

import id.fruity.usg.database.table.entry.USGTableEntry;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class USGTable <T extends USGTableEntry> {
	public static final String C_DIRTY = "dirty",
			C_ISACTIVE = "is_active",
			C_SERVER_ID = "id_server",
			C_MODIFYSTAMP = "modifystamp",
			C_CREATESTAMP = "createstamp";
			
	private String createStatement,tableName;
	
	public USGTable(String tableName, String createStatement){
		this.tableName = tableName;
		this.createStatement = createStatement;
	}
	public void onCreate(SQLiteDatabase database){
		Log.d(tableName, "Create statement: " + createStatement);
		database.execSQL(createStatement);
	}
	
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
		Log.d(tableName, "ON UPGRADE FROM: "+oldVersion+" TO: "+newVersion);
	}
	
	public int insert(SQLiteDatabase database, T entry){
		long result = database.insert(tableName, null, entry.getContentValues());
		Log.d("Table "+tableName, "Insert: "+entry.toString() + " [result:"+ result+"]");
		return (int)result;
	}
	
	public boolean delete(SQLiteDatabase database, T entry){
		int result = database.delete(tableName, getPrimaryClause() , entry.getPrimaryArgs());
		Log.d("Table "+tableName, "Delete: "+entry.toString()+ " [Deleted: "+result+"]");
		return false;
	}
	
	public boolean update(SQLiteDatabase database, T entry){
		int result = database.update(tableName, entry.getContentValues(), getPrimaryClause(), entry.getPrimaryArgs());
		Log.d("Table "+tableName, "Update: "+entry.toString() + " [Updated: "+result+"]");
		return false;
	}
	
	public boolean isExist(SQLiteDatabase database, T entry){
		Cursor c = database.query(tableName, getAllColumns(), getPrimaryClause(), entry.getPrimaryArgs(), null, null, null);
		boolean result = c.getCount() > 0;
		c.close();
		return result;
	}
	
	public Cursor getCursorOfAll(SQLiteDatabase database){
		Log.d("Table "+tableName, "Retrieve All");
		return database.query(tableName, getAllColumns(), C_ISACTIVE+ "=?" , new String[]{"1"}, null, null, null);
	}
	
	public Cursor getItem(SQLiteDatabase database, String[] primaryArgs){
		Log.d("Table "+tableName, "getItem");
		return database.query(tableName, getAllColumns(), getPrimaryClause(), primaryArgs, null, null, null);
		
	}
	
	public Cursor getCursorOfNewItems( SQLiteDatabase database, String[] primaryArgs){
		Log.d("Table "+tableName, "Retrieve new items");
		
		return database.query(tableName, getAllColumns(), getNewClause(), primaryArgs, null, null, null ) ;
	}
	
	public Cursor getCursorOfUpdateItems( SQLiteDatabase database, String[] primaryArgs){
		Log.d("Table "+tableName, "Retrieve updated items");
		return database.query(tableName, getAllColumns(), getUpdateClause(), primaryArgs, null, null, null ) ;
	}
	
	public void applyNewItems(SQLiteDatabase database, T[] newEntries){
		Log.d("Table "+tableName, "applyNewItems");
	}
	
	public void applyUpdatedItems(SQLiteDatabase database, T[] updateEntries){
		Log.d("Table "+tableName, "applyUpdatedItems");
	}
	
	public boolean confirmDelete(SQLiteDatabase database, ArrayList<T> deletedEntries){
		Log.d("Table "+tableName, "confirm delete all these items");
		return false;
	}
	
	public Cursor getItemOnServerId(SQLiteDatabase database, String[] serverIdArgs){
		Log.d("Table "+tableName, "getItemOnServerId");
		return database.query(tableName, getAllColumns(), getServerIdClause(), serverIdArgs, null, null, null);
	}
	
	
	public String[] getAllColumns(){ return null;}
	
	public String getPrimaryClause(){ return null;}
	
	public String getNewClause(){ return null;}
	
	public String getUpdateClause(){return null;}
	
	public String getServerIdClause(){return null;}
	
}
