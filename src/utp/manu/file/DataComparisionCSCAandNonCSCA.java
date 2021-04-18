package utp.manu.file;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

public class DataComparisionCSCAandNonCSCA {
	ArrayList<Double> LLC_Loads_List = new ArrayList<>();
	ArrayList<Double> LLC_Loads_misses_List = new ArrayList<>();
	ArrayList<Double> CMR_List = new ArrayList<>();
	ArrayList<Double> CMRS_List = new ArrayList<>();
	public static void main(String[] args) {
		
		DataComparisionCSCAandNonCSCA DCNC = new DataComparisionCSCAandNonCSCA();
		DCNC.readlinebyline("data/csca_data");
		DCNC.caculateCMR();
		DCNC.printCMR();
	}
	
	public void readlinebyline(String directory)
	{
		try{
			String filename="";
			File folder = new File(directory);
			File[] listOfFiles = folder.listFiles();

			    for (int i = 0; i < listOfFiles.length; i++) {
			      if (listOfFiles[i].isFile()) {
			    	  filename = listOfFiles[i].getName();
			      } else if (listOfFiles[i].isDirectory()) {
			        System.out.println("Directory " + listOfFiles[i].getName());
			      }
			FileInputStream fstream = new FileInputStream(directory+"/"+filename);
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
		}
			}catch(Exception e){
			System.out.println("Error while reading the file:" + e.getMessage());
			}
			
		
		//System.err.println(i);
	}

	public void caculateCMR()
	{
		Iterator<Double> LLC_Loads_List_Iterator = LLC_Loads_List.iterator();
		Iterator<Double> LLC_Loads_misses_List_Iterator = LLC_Loads_misses_List.iterator();
		while(LLC_Loads_List_Iterator.hasNext()&&LLC_Loads_misses_List_Iterator.hasNext())
		{
		Double CMR = LLC_Loads_misses_List_Iterator.next()/LLC_Loads_List_Iterator.next();
		CMR_List.add(CMR);
		}
		
	}
	public void printCMR()
	{
		Iterator<Double> CMR_List_Iterator = CMR_List.iterator();
		System.err.println("printing CMR");
		while(CMR_List_Iterator.hasNext())
		{
			
			System.out.println(CMR_List_Iterator.next());
			
		}
	}
}
