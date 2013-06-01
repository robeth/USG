package id.fruity.usg.database.table.entry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import android.content.ContentValues;

public class USGTableEntry {
	/**
	 * Primary key
	 */
	
	private boolean isDirty;
	@Expose
	@SerializedName("is_active")
	private boolean isActive;
	@Expose
	@SerializedName("modify_timestamp")
	private long modifyTimestampLong;
	@Expose
	@SerializedName("create_timestamp")
	private long createTimestampLong;
	
	public USGTableEntry(){
		this.isDirty = false;
		this.isActive = false;
		modifyTimestampLong = 0;
		createTimestampLong = 0;
	}
	public USGTableEntry(boolean isDirty, boolean isActive, long modifyDate, long createDate){
		this.isDirty = isDirty;
		this.isActive = isActive;
		modifyTimestampLong = modifyDate;
		createTimestampLong = createDate;
	}




	public boolean isDirty() {
		return isDirty;
	}

	public void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public long getModifyTimestampLong() {
		return modifyTimestampLong;
	}

	public void setModifyTimestampLong(long modifyTimestampLong) {
		this.modifyTimestampLong = modifyTimestampLong;
	}

	public long getCreateTimestampLong() {
		return createTimestampLong;
	}

	public void setCreateTimestampLong(long createTimestampLong) {
		this.createTimestampLong = createTimestampLong;
	}

	
	public ContentValues getContentValues(){return null;}
	
	public String[] getPrimaryArgs(){return null;}
	
	public String[] getServerIdArgs(){return null;}
	
	public void onSentSuccess(){};
	
	
}
