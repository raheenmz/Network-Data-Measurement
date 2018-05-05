package common;

import java.util.ArrayList;
import java.util.HashMap;

public class CountMin {
	
	public static void main (String args[]) {
		FileOper fo = new FileOper();
		
		// Setting constants delta and epsilon (epsilon = 0.000001f gives perfect results at the cost of increased space)
		float delta = 0.1f;
		float epsilon = 0.000001f;
		
		// Calculating w and d
		int w = (int)Math.ceil(Math.E/epsilon);
	    int d = (int)Math.ceil (Math.log(1/delta));

	    // Creating DS for count-min
	    int counter[][] = new int [d][w];
	    
	    System.out.println(d+" "+w);
	    
	    HashMap<SrcDstPair,Integer> estimatedData = new HashMap<SrcDstPair,Integer> ();
	    
	    //read file
	    ArrayList<Packet> actualData = fo.readFile();
	    System.out.println("Input Read\n");
		
		System.out.println("Performing Online Operation...");
	    /* Online Operation */
	    // for each packet
	    	//find hash of source destination pair (convert srcIP + convert destIP)
	  		// increment counter by 1
	    for (Packet pckt : actualData) {
	    		for(int i = 0; i < d; i++) {
	    			int j = (int)Math.abs(MurmurHash.hash64(pckt.srcIP + pckt.destIP, i+1) % w);
		    		for (int k=0; k<pckt.flowSize; k++) {
			    			counter[i][j]++;
		    		}
		    	}
	    }
	    
	    System.out.println("Online Operation Completed\n");
		
		System.out.println("Performing Offline Operation...");
	 // offline operation
		// for each packet
				//find hash of source destination pair (convert srcIP + convert destIP)
				// select counter with least value
				// add this as size
	    
	    for (Packet pckt : actualData) {
	    	int minValue = Integer.MAX_VALUE; 
	    	for(int i = 0; i<d; i++) {
	    		int j = (int)Math.abs(MurmurHash.hash64(pckt.srcIP + pckt.destIP, i+1) % w);
	    		if (counter[i][j] < minValue)
	    			minValue = counter[i][j];
	    	}
	    	
	    	estimatedData.put(new SrcDstPair(pckt.srcIP, pckt.destIP), minValue);
	    }
	   
	    System.out.println("Offline Operation Completed\n");
	    
	    fo.writeSize(actualData, estimatedData, "CountMin");
	    System.out.println("Output written to CountMin.txt");
	}
		
}

