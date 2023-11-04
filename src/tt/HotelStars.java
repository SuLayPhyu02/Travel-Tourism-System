package tt;

public class HotelStars {
	private int hsID;
	private int star;
	public HotelStars(int hsID, int star) {
		super();
		this.hsID = hsID;
		this.star = star;
	}
	public HotelStars(int star) {
		super();
		this.star = star;
	}

	public int getHsID() {
		return hsID;
	}
	public void setHsID(int hsID) {
		this.hsID = hsID;
	}
	public int getStar() {
		return star;
	}
	public void setStar(int star) {
		this.star = star;
	}
	@Override
	public String toString() {
		return "star = [" + star + "]";
	}
	

}
