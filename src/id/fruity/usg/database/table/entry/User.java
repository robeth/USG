package id.fruity.usg.database.table.entry;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.fruity.usg.database.table.UserTable;
import android.content.ContentValues;

public class User extends USGTableEntry {
	@Expose
	@SerializedName("local_ktp")
	private String idUser;
	@Expose
	@SerializedName("username")
	private String username;
	@Expose
	@SerializedName("password")
	private String password;
	@Expose
	@SerializedName("name")
	private String name;
	@Expose
	@SerializedName("address")
	private String address;
	@Expose
	@SerializedName("email")
	private String email;
	@Expose
	@SerializedName("phone")
	private String phone;
	@Expose
	@SerializedName("description")
	private String description;
	@Expose
	@SerializedName("local_photo_timestamp")
	private long photoTimestamp;
	@Expose
	@SerializedName("photo_timestamp")
	private long serverPhotoTimestamp;
	@Expose
	@SerializedName("ktp")
	private String serverId;
	
	public User(){}
	
	public User(String serverId, boolean isDirty, boolean isActive,
			long modifyDate, long createDate, String idUser, String username,
			String password, String name, String address, String email, String phone,
			String description, long photoTimestamp, long serverPhotoTimestamp) {
		super(isDirty, isActive, modifyDate, createDate);
		this.idUser = idUser;
		this.username = username;
		this.password = password;
		this.name = name;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.description = description;
		this.serverId = serverId;
		this.photoTimestamp = photoTimestamp;
		this.serverPhotoTimestamp = serverPhotoTimestamp;
	}
	@Override
	public ContentValues getContentValues() {
		ContentValues cv = new ContentValues();
		cv.put(UserTable.C_ID, idUser);
		cv.put(UserTable.C_NAME, name);
		cv.put(UserTable.C_ADDRESS, address);
		cv.put(UserTable.C_USERNAME, username);
		cv.put(UserTable.C_PASSWORD, password);
		cv.put(UserTable.C_EMAIL, email);
		cv.put(UserTable.C_PHONE, phone);
		cv.put(UserTable.C_DESCRIPTION, description);
		cv.put(UserTable.C_PHOTOTIMESTAMP, photoTimestamp);
		cv.put(UserTable.C_SERVERPHOTOTIMESTAMP, serverPhotoTimestamp);
		
		cv.put(UserTable.C_DIRTY, isDirty()?1:0);
		cv.put(UserTable.C_ISACTIVE, isActive()?1:0);
		cv.put(UserTable.C_MODIFYSTAMP, getModifyTimestampLong());
		cv.put(UserTable.C_CREATESTAMP, getCreateTimestampLong());
		cv.put(UserTable.C_SERVER_ID, serverId);
		return cv;
	}
	
	
	
	public String getIdUser() {
		return idUser;
	}

	public void setIdUser(String idUser) {
		this.idUser = idUser;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String[] getPrimaryArgs() {
		return new String[]{""+idUser};
	}
	
	public long getPhotoTimestamp() {
		return photoTimestamp;
	}

	public void setPhotoTimestamp(long photoTimestamp) {
		this.photoTimestamp = photoTimestamp;
	}

	public long getServerPhotoTimestamp() {
		return serverPhotoTimestamp;
	}

	public void setServerPhotoTimestamp(long serverPhotoTimestamp) {
		this.serverPhotoTimestamp = serverPhotoTimestamp;
	}

	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", username=" + username
				+ ", password=" + password + ", name=" + name + ", address="
				+ address + ", email=" + email + ", phone=" + phone
				+ ", description=" + description + ", photoTimestamp="
				+ photoTimestamp + ", serverPhotoTimestamp="
				+ serverPhotoTimestamp + ", serverId=" + serverId + "]";
	}

	public void onServerAdd(){
		idUser = serverId;
		setDirty(false);
	}
	@Override
	public void onSentSuccess(){
		serverId = idUser;
		setDirty(false);
	}
	
	public void onUpdatedByServer(User other){
		this.username = other.getUsername();
		this.password = other.getPassword();
		this.name = other.getName();
		this.address = other.getAddress();
		this.email = other.getEmail();
		this.phone = other.getPhone();
		this.description = other.getDescription();
		this.serverPhotoTimestamp = other.getPhotoTimestamp();
		setDirty(false);
		setActive(other.isActive());
		setModifyTimestampLong(other.getModifyTimestampLong());
	}

}
