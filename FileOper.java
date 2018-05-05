package common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.TreeMap;

public class FileOper {
	
	 ArrayList<Packet> readFile() {
		
		ArrayList<Packet> data = new ArrayList<Packet> (); 
		String fileName = "C:\\Users\\rahee\\Desktop\\Spring 2018\\NA\\traffic.txt" ; 
		BufferedReader br = null; 
		
		try {
			br = new BufferedReader(new FileReader(fileName));
			String line;
			line = br.readLine(); // to remove header line
		
			while ((line = br.readLine()) != null) {
				String s[] = line.split("\\s+");
				data.add(new Packet(s[0],s[1],Integer.parseInt(s[2])));
			}
		}
		catch (IOException e) {
			e.getMessage();
		}
		finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.getMessage();
			}
		}
			
	   return data;
	}

	 ArrayList<Packet> readFileForSizeCalc() {
			
		ArrayList<Packet> data = new ArrayList<Packet> (); 
		String fileName = "C:\\Users\\rahee\\Desktop\\Spring 2018\\NA\\traffic.txt" ; 
		BufferedReader br = null; 
		
		try {
			br = new BufferedReader(new FileReader(fileName));
			String line;
			line = br.readLine(); // to remove header line
		
			while ((line = br.readLine()) != null) {
				String s[] = line.split("\\s+");
				for (int i=0; i<Integer.parseInt(s[2]); i++) {
					data.add(new Packet(s[0],s[1],Integer.parseInt(s[2])));
				}
			}
		}
		catch (IOException e) {
			e.getMessage();
		}
		finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.getMessage();
			}
		}
			
	   return data;
	}
	 
	 HashSet<String> readFileForMemTest(int limit) {
			 
		HashSet<String> srcIP = new HashSet< > ();
		String fileName = "C:\\Users\\rahee\\Desktop\\Spring 2018\\NA\\traffic.txt" ; 
		BufferedReader br = null; 
		
		try {
			br = new BufferedReader(new FileReader(fileName));
			String line;
			line = br.readLine(); // to remove header line
		
			while ((line = br.readLine()) != null) {
				String s[] = line.split("\\s+");
					srcIP.add(s[0]+s[1]);
					if (srcIP.size() == limit)
						break;
			}
		}
		catch (IOException e) {
			e.getMessage();
		}
		finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.getMessage();
			}
		}
	
		return srcIP;
	}
	 
	TreeMap<String, Integer> readTrafficSpread() {
		
		TreeMap<String, Integer> trafficSpread = new TreeMap<String, Integer> (); 
		String fileName = "C:\\Users\\rahee\\Desktop\\Spring 2018\\NA\\traffic_spread.txt" ; 
		BufferedReader br = null; 
		
		try {
			br = new BufferedReader(new FileReader(fileName));
			String line;

			while ((line = br.readLine()) != null) {
				String s[] = line.split("\\s+");
				trafficSpread.put(s[0],Integer.parseInt(s[1]));
			}
		}
		catch (IOException e) {
			e.getMessage();
		}
		finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.getMessage();
			}
		}
			
	   return trafficSpread;
	}
	 
	void writeSpread(TreeMap<String,Integer> spread, String algo) {
		
		TreeMap<String,Integer> actualSpread = readTrafficSpread(); 
		
		
		String fileName = "C:\\Users\\rahee\\Desktop\\Spring 2018\\NA\\"+algo+".txt";
		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter(new FileWriter(fileName));
			for (Entry<String, Integer> entry : spread.entrySet()) {
			    //System.out.print(entry.getKey()+"\t"+entry.getValue()+"\n");
			    bw.write(entry.getKey()+"\t"+actualSpread.get(entry.getKey())+"\t"+entry.getValue()+"\n");
			    bw.newLine();
			}
		}
		catch(IOException e)  {
			e.getMessage();
		}
		finally {
			try {
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				e.getMessage();
			}
		}
		
	}

	
	void writeSize(ArrayList<Packet> actualData, HashMap<SrcDstPair,Integer> estimatedData, String algo) { 
		
		String fileName = "C:\\Users\\rahee\\Desktop\\Spring 2018\\NA\\"+algo+".txt";
		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter(new FileWriter(fileName));
			
			for (Packet pckt : actualData) {
				int estimatedSize = estimatedData.get(new SrcDstPair(pckt.srcIP, pckt.destIP)); 
				bw.write(pckt.srcIP+"\t"+pckt.destIP+"\t"+pckt.flowSize+"\t"+estimatedSize+"\n");
			    bw.newLine();
			}
			
		}
		catch(IOException e)  {
			e.getMessage();
		}
		finally {
			try {
				if (bw != null)
					bw.close();
			} catch (IOException e) {
				e.getMessage();
			}
		}
	}

	public static void main(String args[]) {
		FileOper fo = new FileOper();
		//List<Packet> data = fo.readFile();
		//System.out.println(data.size());
		fo.readFileForMemTest(15000);
		
		//System.out.println(Integer.hashCode(16843513));
		
		
		//System.out.println(fo.convertIpToInteger("6.171.156.124"));
	}

}
