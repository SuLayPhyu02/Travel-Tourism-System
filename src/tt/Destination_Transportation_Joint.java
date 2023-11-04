package tt;

public class Destination_Transportation_Joint {
	private int dtid;
	private int destinationID;
	private int transportationID;
	private int price;
	public Destination_Transportation_Joint(int dtid, int destinationID, int transportationID, int price) {
		super();
		this.dtid = dtid;
		this.destinationID = destinationID;
		this.transportationID = transportationID;
		this.price = price;
	}
	public int getDtid() {
		return dtid;
	}
	public void setDtid(int dtid) {
		this.dtid = dtid;
	}
	public int getDestinationID() {
		return destinationID;
	}
	public void setDestinationID(int destinationID) {
		this.destinationID = destinationID;
	}
	public int getTransportationID() {
		return transportationID;
	}
	public void setTransportationID(int transportationID) {
		this.transportationID = transportationID;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

}
