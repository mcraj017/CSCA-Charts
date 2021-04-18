package utp.manu.file;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;


public class ExtractOutputValues {
	
	ArrayList<Double> LLC_Loads_List = new ArrayList<>();
	ArrayList<Double> LLC_Loads_misses_List = new ArrayList<>();
	ArrayList<Double> cycles = new ArrayList<>();
	ArrayList<Double> exe_time = new ArrayList<>();
	ArrayList<Double> CMR_List = new ArrayList<>();
	public static void main(String[] args) {
		ExtractOutputValues eov = new ExtractOutputValues();
		eov.readlinebyline("data/data_4000_TS/csca_data_10_200_times_only_gpg.dat");
		//eov.readlinebylinewithcycles();
		//eov.printData();
		eov.caculateCMR();
		eov.printCMR();
	}
	
	public void readlinebyline(String filename)
	{
		try{
			LLC_Loads_List.clear();
			LLC_Loads_misses_List.clear();
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader( new InputStreamReader(in));
			String line="";
			
			while((line = br.readLine()) != null){
				if(line.isEmpty())
					continue;
				if(Character.isDigit(line.charAt(0)))
				{
					String SplitLine[] = line.split(",");
					//System.err.println(SplitLine[0]);
					if(SplitLine[1].trim().equalsIgnoreCase("LLC-loads"))
					{
						
						LLC_Loads_List.add(Double.parseDouble(SplitLine[0]));
					}
					else if(SplitLine[1].trim().equalsIgnoreCase("LLC-load-misses"))
					{
						LLC_Loads_misses_List.add(Double.parseDouble(SplitLine[0]));
					}
					else
					{
						//System.out.println("Data missing");
					}
					
				}
			 
			}		
			in.close();
			}catch(Exception e){
				e.printStackTrace();
			System.out.println("Error while reading the file:" + e.getMessage());
			}
		
		//System.err.println(i);
	}
	public void readlinebylinewithcycles(String filename)
	{
		try{
			FileInputStream fstream = new FileInputStream(filename);
			FileInputStream fstream1 = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader( new InputStreamReader(in));
			String line="";
			cycles.clear();
			while((line = br.readLine()) != null){
				if(line.isEmpty())
					continue;
				if(Character.isDigit(line.charAt(0)))
				{
					String SplitLine[] = line.split(",");
					//System.err.println(SplitLine[0]);
					if(SplitLine[1].trim().equalsIgnoreCase("cpu-cycles"))
					{
						
						cycles.add(Double.parseDouble(SplitLine[0]));
					}
					else
					{
						//System.out.println("Data missing");
					}
					
				}
			 
			}
			in.close();
			}catch(Exception e){
			System.out.println("Error while reading the file:" + e.getMessage());
			}
		//System.err.println(i);
	}
	public void readlinebylineexetime(String filename)
	{
		try{
			FileInputStream fstream = new FileInputStream(filename);
			FileInputStream fstream1 = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader( new InputStreamReader(in));
			String line="";
			cycles.clear();
			while((line = br.readLine()) != null){
				if(line.isEmpty())
					continue;
				exe_time.add(Double.parseDouble(line));
			}
			in.close();
			}catch(Exception e){
			System.out.println("Error while reading the file:" + e.getMessage());
			}
		//System.err.println(i);
	}
	public void printData()
	{
		Iterator<Double> LLC_Loads_List_Iterator = LLC_Loads_List.iterator();
		Iterator<Double> LLC_Loads_misses_List_Iterator = LLC_Loads_misses_List.iterator();
		Iterator<Double> cycles_Iterator = cycles.iterator();
		System.err.println("printing");
		while(LLC_Loads_List_Iterator.hasNext()&&LLC_Loads_misses_List_Iterator.hasNext())
		{
			
			System.out.println(LLC_Loads_List_Iterator.next()+","+LLC_Loads_misses_List_Iterator.next()+","+cycles_Iterator.next());
			
		}
	}
	
	public void caculateCMR()
	{
		Iterator<Double> LLC_Loads_List_Iterator = LLC_Loads_List.iterator();
		Iterator<Double> LLC_Loads_misses_List_Iterator = LLC_Loads_misses_List.iterator();
		CMR_List.clear();
		while(LLC_Loads_List_Iterator.hasNext()&&LLC_Loads_misses_List_Iterator.hasNext())
		{
		Double CMR = LLC_Loads_misses_List_Iterator.next()/LLC_Loads_List_Iterator.next();
		CMR_List.add(CMR);
		}
		
	}
	public void printCMR()
	{
		Iterator<Double> CMR_List_Iterator = CMR_List.iterator();
		//Iterator<Double> cycles_Iterator = cycles.iterator();
		System.err.println("printing CMR");
		while(CMR_List_Iterator.hasNext())
		{
			System.out.println(CMR_List_Iterator.next());
			//System.out.println(CMR_List_Iterator.next()+","+cycles_Iterator.next());
			
		}
	}
	

}
