package tt;

public class Hotel {
	private int hotelID;
	private String hotelName;
	private String hemail;
	private String haddress;
	private int star;
	private String detName;
	
	private int hsID;
	private int destinationID;
	
	public Hotel(int hotelID, String hotelName, String hemail, String haddress, int star, String detName) {
		super();
		this.hotelID = hotelID;
		this.hotelName = hotelName;
		this.hemail = hemail;
		this.haddress = haddress;
		this.star = star;
		this.detName = detName;
	}
	public Hotel(int hotelID, String hotelName, String hemail, String haddress) {
		super();
		this.hotelID = hotelID;
		this.hotelName = hotelName;
		this.hemail = hemail;
		this.haddress = haddress;
	}
	public int getHotelID() {
		return hotelID;
	}
	public void setHotelID(int hotelID) {
		this.hotelID = hotelID;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	public String getHemail() {
		return hemail;
	}
	public void setHemail(String hemail) {
		this.hemail = hemail;
	}
	public String getHaddress() {
		return haddress;
	}
	public void setHaddress(String haddress) {
		this.haddress = haddress;
	}
	public int getHsID() {
		return hsID;
	}
	public void setHsID(int hsID) {
		this.hsID = hsID;
	}
	public int getDestinationID() {
		return destinationID;
	}
	public void setDestinationID(int destinationID) {
		this.destinationID = destinationID;
	}
	
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	public String getDetName() {
		return detName;
	}
	public void setDetName(String detName) {
		this.detName = detName;
	}
	@Override
	public String toString() {
		return "Hotel [hotelID=" + hotelID + ", hotelName=" + hotelName + ", hemail=" + hemail + ", haddress="
				+ haddress + "]";
	}
	

}
