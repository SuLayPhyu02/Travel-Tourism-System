package tt;

public class Staff {
	private int sid;
	private String sname;
	private String semail;
	private String spassword;
	private String sposition;
	private String enrollDate;
	private String staffImage;
	public Staff(int sid, String sname, String semail, String spassword, String sposition, String enrollDate,
			String staffImage) {
		super();
		this.sid = sid;
		this.sname = sname;
		this.semail = semail;
		this.spassword = spassword;
		this.sposition = sposition;
		this.enrollDate = enrollDate;
		this.staffImage = staffImage;
	}

	public Staff(int sid, String sname, String semail, String sposition, String enrollDate) {
		super();
		this.sid = sid;
		this.sname = sname;
		this.semail = semail;
		this.sposition = sposition;
		this.enrollDate = enrollDate;
	}

	public int getSid() {
		return this.sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getSemail() {
		return semail;
	}
	public void setSemail(String semail) {
		this.semail = semail;
	}
	public String getSpassword() {
		return spassword;
	}
	public void setSpassword(String spassword) {
		this.spassword = spassword;
	}
	public String getSposition() {
		return sposition;
	}
	public void setSposition(String sposition) {
		this.sposition = sposition;
	}
	public String getEnrollDate() {
		return enrollDate;
	}
	public void setEnrollDate(String enrollDate) {
		this.enrollDate = enrollDate;
	}
	public String getStaffImage() {
		return staffImage;
	}
	public void setStaffImage(String staffImage) {
		this.staffImage = staffImage;
	}

	@Override
	public String toString() {
		return "Staff [sid=" + sid + ", sname=" + sname + ", semail=" + semail + ", spassword=" + spassword
				+ ", sposition=" + sposition + ", enrollDate=" + enrollDate + ", staffImage=" + staffImage + "]";
	}
	
	
	

}
