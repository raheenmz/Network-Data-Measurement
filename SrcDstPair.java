package common;

public class SrcDstPair {
	String srcIP;
	String destIP;
	
	public SrcDstPair(String srcIP, String destIP) {
		super();
		this.srcIP = srcIP;
		this.destIP = destIP;
	}

	@Override
	public String toString() {
		return "SrcDstPair [srcIP=" + srcIP + ", destIP=" + destIP + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destIP == null) ? 0 : destIP.hashCode());
		result = prime * result + ((srcIP == null) ? 0 : srcIP.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SrcDstPair other = (SrcDstPair) obj;
		if (destIP == null) {
			if (other.destIP != null)
				return false;
		} else if (!destIP.equals(other.destIP))
			return false;
		if (srcIP == null) {
			if (other.srcIP != null)
				return false;
		} else if (!srcIP.equals(other.srcIP))
			return false;
		return true;
	}

}
