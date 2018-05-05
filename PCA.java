package common;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap;

public class PCA {
	
	static int m = 1000; //Size of Bitmap
	
public static void main(String args[]) {
		
		FileOper fo = new FileOper();
		Hashtable<String,BitSet> bitHashTable = new Hashtable<String,BitSet> ();
		TreeMap<String,Integer> spread = new TreeMap<String,Integer> ();
		
		ArrayList<Packet> data = fo.readFile();
		System.out.println("Input Read\n");
		
		System.out.println("Performing Online Operation...");
		for (Packet pckt : data) {
			// check if source is contained in hashtable
			if (bitHashTable.containsKey(pckt.srcIP)) {
				// if source is contained
				
				// Retrive bitmap
				BitSet bitmap = bitHashTable.get(pckt.srcIP);
				
				// Find hash of destination
				//int destBit = (convertIpToInteger(pckt.destIP))%m;
				int destBit = (MurmurHash.hash32(pckt.destIP,0))%m;
				if (destBit<0)
					destBit += m; 
				// set this value to 1
				bitmap.set(destBit);
				
				// store bitmap again
				bitHashTable.put(pckt.srcIP, bitmap);
				
			}
			else {
				// if source is not contained add 
				
				// create new bitmap
				BitSet bitmap = new BitSet(m);
				
				// Find hash of destination
				//int destBit = Math.abs(pckt.destIP.hashCode())%m;
				int destBit = (MurmurHash.hash32(pckt.destIP,0))%m;
				if (destBit<0)
					destBit += m;
				
				//System.out.println(destBit);
				
				
				// set this value to 1
				bitmap.set(destBit);
				
				// store bitmap again
				bitHashTable.put(pckt.srcIP, bitmap);
				
			}
			
		}
		System.out.println("Online Operation Completed\n");
		
		System.out.println("Performing Offline Operation...");
		for(Map.Entry<String,BitSet> entry : bitHashTable.entrySet()) {
			double v = (m - entry.getValue().cardinality()) / (double)m;
			//System.out.println("Bits set to 1 "+ v);
			int n = (int)(-m * Math.log(v));
			spread.put(entry.getKey(), n);
		}
		System.out.println("Offline Operation Completed\n");
		
		fo.writeSpread(spread, "PCA"); 
		System.out.println("Output written to PCA.txt");
	}	


}

