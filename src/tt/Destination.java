package tt;

public class Destination {
	private int dID;
	private String detName;
	public Destination(int dID, String detName) {
		super();
		this.dID = dID;
		this.detName = detName;
	}
	public Destination(String detName) {
		super();
		this.detName = detName;
	}
	public int getdID() {
		return dID;
	}
	public void setdID(int dID) {
		this.dID = dID;
	}
	public String getDetName() {
		return detName;
	}
	public void setDetName(String detName) {
		this.detName = detName;
	}
	@Override
	public String toString() {
		return "Destination [dID=" + dID + ", detName=" + detName + "]";
	}
	
}
