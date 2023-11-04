package tt;

public class TransportationContact {
	private int tcID;
	private String tphNo;
	private int transportationID;
	public TransportationContact(int tcID, String tphNo, int transportationID) {
		super();
		this.tcID = tcID;
		this.tphNo = tphNo;
		this.transportationID = transportationID;
	}
	public int getTcID() {
		return tcID;
	}
	public void setTcID(int tcID) {
		this.tcID = tcID;
	}
	public String getTphNo() {
		return tphNo;
	}
	public void setTphNo(String tphNo) {
		this.tphNo = tphNo;
	}
	public int getTransportationID() {
		return transportationID;
	}
	public void setTransportationID(int transportationID) {
		this.transportationID = transportationID;
	}
	
	

}
