package tt;

public class Package {
	private int packageID;
	private String packageName;
	private String departureDate;
	private String arrivalDate;
	private int packPrice;
	private String pImage;
	private int destID;
	private String destinationName;

	public Package(int packageID, String packageName, String departureDate, String arrivalDate, int packPrice,
			String pImage, int destID) {
		super();
		this.packageID = packageID;
		this.packageName = packageName;
		this.departureDate = departureDate;
		this.arrivalDate = arrivalDate;
		this.packPrice = packPrice;
		this.pImage = pImage;
		this.destID = destID;
	}
	public Package(int packageID, String packageName, String departureDate, String arrivalDate, int packPrice,
			String pImage, int destID, String destinationName) {
		super();
		this.packageID = packageID;
		this.packageName = packageName;
		this.departureDate = departureDate;
		this.arrivalDate = arrivalDate;
		this.packPrice = packPrice;
		this.pImage = pImage;
		this.destID = destID;
		this.destinationName = destinationName;
	}
	

	public Package(int packageID, String packageName, String departureDate, String arrivalDate, int packPrice,
			String destinationName) {
		super();
		this.packageID = packageID;
		this.packageName = packageName;
		this.departureDate = departureDate;
		this.arrivalDate = arrivalDate;
		this.packPrice = packPrice;
		this.destinationName = destinationName;
	}
	public int getPackageID() {
		return packageID;
	}


	public void setPackageID(int packageID) {
		this.packageID = packageID;
	}


	public String getPackageName() {
		return packageName;
	}


	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}


	public String getDepartureDate() {
		return departureDate;
	}


	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}


	public String getArrivalDate() {
		return arrivalDate;
	}


	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}


	public int getPackPrice() {
		return packPrice;
	}


	public void setPackPrice(int packPrice) {
		this.packPrice = packPrice;
	}


	public String getpImage() {
		return pImage;
	}


	public void setpImage(String pImage) {
		this.pImage = pImage;
	}


	public int getDestID() {
		return destID;
	}


	public void setDestID(int destID) {
		this.destID = destID;
	}
	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}
	@Override
	public String toString() {
		return "Package [packageID=" + packageID + ", packageName=" + packageName + ", departureDate=" + departureDate
				+ ", arrivalDate=" + arrivalDate + ", packPrice=" + packPrice + ", pImage=" + pImage + ", destID="
				+ destID + ", destinationName=" + destinationName + "]";
	}
	
	
	
	
	
}