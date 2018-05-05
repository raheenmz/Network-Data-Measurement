package common;

public class Packet {
	String srcIP;
	String destIP;
	int flowSize;
	
	public Packet(String srcIP, String destIP, int flowSize) {
		super();
		this.srcIP = srcIP;
		this.destIP = destIP;
		this.flowSize = flowSize;
	}

	@Override
	public String toString() {
		return "Packet [srcIP=" + srcIP + ", destIP=" + destIP + ", flowSize=" + flowSize + "]";
	}
	
}
