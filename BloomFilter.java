package common;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class BloomFilter {

	public static void main(String[] args) {
		
		int n = 10000; // number of elements
		float p = 0.001f; // false positive ratio
		
		// Reading ips
		HashSet<String> ips = new HashSet< > ();
		FileOper fo = new FileOper ();
		ips =  fo.readFileForMemTest(2*n); // returns first 15000 src ips of traffic.txt
		
		
		// Getting training set
		List<String> ipList = new ArrayList<String>(ips);
		ArrayList<String> onlineList = new ArrayList<String> (ipList.subList(0, n));
		Set<String> onlineSet = new HashSet<String>(onlineList);
		
		// Getting testing set
		ArrayList<String> offlineList = new ArrayList<String> (ipList.subList(n, 2*n));
		Set<String> offlineSet = new HashSet<String>(offlineList);
		
		int m = (int) Math.ceil(-n*Math.log(p) / Math.pow(Math.log(2),2)); // size of bitmap
		int k = (int) Math.ceil(m/n * Math.log(2));  // hash functions
		
		// Creating bitmap of size m
		BitSet bitmap = new BitSet(m);
		
		// generating random seeds for hash functions
		int seed[] = generateRandomSeed(k);
		
		
		/** Online Operation : For each src calculate hash functions and set appropriate bits **/
		
		int index;
		for (String ip: onlineSet) {
			for (int i= 0; i<k; i++) {
				index = MurmurHash.hash32(ip, seed[i])%m;
				bitmap.set(index);
			}
		}
		
		/** Offline Operation : For each src calculate hash functions check if all bits are set  **/
		
		int falsePositive = 0;
		int falseNegative = 0;
		boolean isPresent;
		
		for (String ip: offlineSet) {
			 isPresent = true;
			
			for (int i= 0; i<k; i++) {
				index = MurmurHash.hash32(ip, seed[i])%m;
				if (!bitmap.get(index)) {
					isPresent = false;
					break;
				}
			}
			
			if (isPresent && !onlineSet.contains(ip)) {
				falsePositive++;
			}
			if (!isPresent && onlineSet.contains(ip)) {
				falseNegative++;
			}
		}
		
		double falsePositiveRatio = (double)falsePositive/n;
		double falseNegativeRatio = (double)falseNegative/n;

		System.out.println("Number of Elements Encoded: "+n);
		System.out.println("Number of Elements Tested: "+n);
		System.out.println("Number of Hash Functions: "+k);
		System.out.println("Size of Bloom Filter: "+m);
		System.out.println("Theoretical False Positive Ratio: "+p);
		System.out.println("Actual False Positive Ratio: "+falsePositiveRatio);
		System.out.println("Actual False Negative Ratio: "+falseNegativeRatio);
	}
	
	public static int[] generateRandomSeed(int size){
		
		int r[] = new int[size];
		Random rand = new Random(10000000);
		for(int i=0; i<size; i++){	
			r[i] = (int) rand.nextInt(Integer.MAX_VALUE);
		}
		
		return r;
	}

}
