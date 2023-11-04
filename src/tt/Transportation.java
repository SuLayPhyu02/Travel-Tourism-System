package tt;

public class Transportation {
	private int tid;
	private String tname;
	private String temail;
	private String taddress;
	private String tImage;
	private int typeID;
	private String ttName;
	private String tphNo;
	public Transportation(int tid, String tname, String temail, String taddress, String ttName, String tphNo) {
		super();
		this.tid = tid;
		this.tname = tname;
		this.temail = temail;
		this.taddress = taddress;
		this.ttName = ttName;
		this.tphNo = tphNo;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getTemail() {
		return temail;
	}
	public void setTemail(String temail) {
		this.temail = temail;
	}
	public String getTaddress() {
		return taddress;
	}
	public void setTaddress(String taddress) {
		this.taddress = taddress;
	}
	public String gettImage() {
		return tImage;
	}
	public void settImage(String tImage) {
		this.tImage = tImage;
	}
	public int getTypeID() {
		return typeID;
	}
	public void setTypeID(int typeID) {
		this.typeID = typeID;
	}
	public String getTtName() {
		return ttName;
	}
	public void setTtName(String ttName) {
		this.ttName = ttName;
	}
	public String getTphNo() {
		return tphNo;
	}
	public void setTphNo(String tphNo) {
		this.tphNo = tphNo;
	}
	
	
}