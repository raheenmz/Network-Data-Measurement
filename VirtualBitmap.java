package common;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Random;
import java.util.TreeMap;


public class VirtualBitmap {
	
	public static void main (String args[])  {
		
		FileOper fo = new FileOper();
		HashSet<String> sources = new HashSet< > () ;
		TreeMap<String,Integer> spread = new TreeMap<String,Integer> ();
		
		int m = 100000000; // Size of physical bitmap
		int s = 1500; // Size of virtual bitmap
		
		// Creating physical bitmap
		BitSet bitmap = new BitSet(m);
		
		// Creating random seeds array
		int rand[] = setRandomInt(s,m);
		
		ArrayList<Packet> data = fo.readFile();
		System.out.println("Input Read\n");
		
		System.out.println("Performing Online Operation...");
		
		// Online Operation
			// For each src-dest pair set bit in bitmap
		
		for (Packet pckt: data) {
			sources.add(pckt.srcIP);
			int destHash = MurmurHash.hash32(pckt.destIP,0);
			int sourceHash = MurmurHash.hash32(pckt.srcIP,0);
			destHash = destHash % s;
			if (destHash < 0)
				destHash += s;
			int bitToSet = sourceHash ^ rand[destHash];
			bitToSet = bitToSet % m;
			if (bitToSet < 0)
				bitToSet += m;
			bitmap.set(bitToSet);
			
		}
		System.out.println("Online Operation Completed\n");
		
		System.out.println("Performing Offline Operation...");
		// Offline Operation
			// For each src find bits that are set
		int numBitsUnset = m-bitmap.cardinality();
        double noise = s * Math.log(numBitsUnset/(double)m);
		 
		for (String src: sources) {
		
			int numBitsSet = 0;
			int sourceHashCode = MurmurHash.hash32(src,0);
			for (int i = 0; i < s; i++) {
				int bitToGet = sourceHashCode ^ rand[i];
				bitToGet = bitToGet % m;
				if (bitToGet < 0)
					bitToGet += m;
				if (bitmap.get(bitToGet))
					numBitsSet++;
			}
			
			numBitsUnset = s - numBitsSet;
			if(numBitsUnset == 0)
				numBitsUnset = 1;
			int estimatedValue = (int)(noise - s * Math.log(numBitsUnset / (double) s));
			if (estimatedValue < 0) {
				estimatedValue = 0;
			}
			spread.put(src, estimatedValue); 
		}
		System.out.println("Offline Operation Completed\n");
		
		fo.writeSpread(spread, "VirtualBitmap");
		System.out.println("Output written to VirtualBitmap.txt");
	}

	public static int[] setRandomInt(int s, int m){
		
		int rand[] = new int[s];
		Random randGen = new Random();
        for (int i = 0; i < s; i++)
            rand[i] = randGen.nextInt();
        
        return rand;
	}
}
