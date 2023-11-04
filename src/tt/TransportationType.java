package tt;

public class TransportationType {
	private int ttID;
	private String ttName;
	public TransportationType(String ttName) {
		super();
		this.ttName = ttName;
	}
	public int getTtID() {
		return ttID;
	}
	public void setTtID(int ttID) {
		this.ttID = ttID;
	}
	public String getTtName() {
		return ttName;
	}
	public void setTtName(String ttName) {
		this.ttName = ttName;
	}
	@Override
	public String toString() {
		return ttName;
	}
	

}
