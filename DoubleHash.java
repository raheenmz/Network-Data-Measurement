package common;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.TreeMap; ;

public class DoubleHash {
	
	public static void main(String args[]) {
		
		FileOper fo = new FileOper();
		Hashtable<String, Hashtable<String,Integer>> doubleHash = new Hashtable<String,Hashtable<String, Integer>> ();
		TreeMap<String,Integer> spread = new TreeMap<String,Integer> ();
		
		ArrayList<Packet> data = fo.readFile();
		System.out.println("Input Read\n");
		
		System.out.println("Performing Online Operation...");
		for (Packet pckt : data) {
			// check if source is contained in hashtable
			if (doubleHash.containsKey(pckt.srcIP)) {
				// if source is contained
				
				// check if destion is contained 
				Hashtable<String,Integer> ht = doubleHash.get(pckt.srcIP);
				if (!ht.containsKey(pckt.destIP)) {
					// if not add declaration
					ht.put(pckt.destIP, new Integer(1));
					doubleHash.put(pckt.srcIP, ht);
				}
			}
			else {
				// if source is not contained add 
				Hashtable<String,Integer> ht = new Hashtable<String,Integer> ();
				ht.put(pckt.destIP, new Integer(1));
				doubleHash.put(pckt.srcIP, ht);
			}
			
		}
		System.out.println("Online Operation Completed\n");
		
		System.out.println("Performing Offline Operation...");
		for(Map.Entry<String,Hashtable<String,Integer>> entry : doubleHash.entrySet()) {
			spread.put(entry.getKey(),entry.getValue().size()) ;
		}
		System.out.println("Offline Operation Completed\n");
		
		fo.writeSpread(spread, "DoubleHash");
		System.out.println("Output written to DoubleHash.txt");
	}	
}
