package id.fruity.usg;

public class Message {
	private String content;
	private String date;
	private String sender;
	private int imageId;
	private boolean isMine;
	public Message(String content, String date, String sender, int imageId,
			boolean isMine) {
		super();
		this.content = content;
		this.date = date;
		this.sender = sender;
		this.imageId = imageId;
		this.isMine = isMine;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public int getImageId() {
		return imageId;
	}
	public void setImageId(int imageId) {
		this.imageId = imageId;
	}
	public boolean isMine() {
		return isMine;
	}
	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}
	
	
}
