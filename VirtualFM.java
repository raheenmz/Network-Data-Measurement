package common;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeMap;

public class VirtualFM {

	public static void main(String[] args) {
		
		FileOper fo = new FileOper();
		HashSet<String> sources = new HashSet< > () ;
		TreeMap<String,Integer> spread = new TreeMap<String,Integer> ();
		
		int M = 1000000 ; // Number of physical bitmaps
		int S = 16; // Number of virtual bitmaps
		int m = 32; //size of each bitmap
		final double RHO = 0.77351; 
		
		BitSet[] sketch = initializeSketch(M,m) ; // Initializing physical bitmap
		
		// Creating random seeds array
		int rand[] = setRandomInt(S);
		
		//Reading file
		ArrayList<Packet> data = fo.readFile();
		System.out.println("Input Read\n");
		
		System.out.println("Performing Online Operation...");
		
		//Online Operation
		for (Packet pckt: data) {
			int virtualSketchIndex = (MurmurHash.hash32(pckt.srcIP,0) ^ rand[MurmurHash.hash32(pckt.destIP,0)%S])%M;
			if (virtualSketchIndex<0) {
				virtualSketchIndex+=M ;
			}
			int index = countLeadingZeros(MurmurHash.hash32(pckt.destIP+pckt.srcIP,virtualSketchIndex));
			sketch[virtualSketchIndex].set(index);
			sources.add(pckt.srcIP);
		}
		System.out.println("Online Operation Completed\n");
		
		System.out.println("Performing Offline Operation...");
		int totalCount=0;
		for (int i=0; i<M ;i++) {
			totalCount += sketch[i].nextClearBit(0);
		}
		double zm = totalCount/(double)M;
		
		double noise = (M/RHO)* Math.pow(2,zm);
		
		double z;
		int estimatedCardinality; 
		for (String src: sources) {
			totalCount = 0;
			for (int i=0; i<S ;i++) {
				int virtualSketchIndex = (MurmurHash.hash32(src,0) ^ rand[i])%M;
				if (virtualSketchIndex<0)
					virtualSketchIndex+=M;
				totalCount += sketch[virtualSketchIndex].nextClearBit(0);
			}
			z =  totalCount/(double)S;
			double est = (S/RHO)* Math.pow(2,z);
			estimatedCardinality = (int)(( ((double)M*S) / (double)(M-S) )*( (est/S)-(noise/M) ));
			if (estimatedCardinality < 0)
				estimatedCardinality = 1;
			spread.put(src, estimatedCardinality);
		}
		System.out.println("Offline Operation Completed\n");
		
		fo.writeSpread(spread, "VirtualFM");
		System.out.println("Output written to VirtualFM.txt");
	}
	
	 static int countLeadingZeros(int hash) {
		 
		  int count = 0;
		  int dummy = 1;
          while(count < 31 && ((hash & dummy) == 0)) {
              dummy = dummy << 1;
              count++;
          }
		
		return count;
	}

	public static BitSet[] initializeSketch(int M, int m) {
		BitSet[] sketch = new BitSet[M];
		
		for (int i=0; i<M; i++) {
			sketch[i] = new BitSet(m);
		}
		
		return sketch;
	}
	
	public static int[] setRandomInt(int s){
		
		int rand[] = new int[s];
		Random randGen = new Random();
        for (int i = 0; i < s; i++)
            rand[i] = randGen.nextInt();
        
        return rand;
	}
    
 }