package utp.manu.file;


import java.util.Iterator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class CreatingCharts extends Application {

	private String filename1="";
	private String filename2="";
	public CreatingCharts(String filename1, String filename2)
	{
		this.filename1 = filename1;
		this.filename2 = filename2;
	}
	public static void main(String[] args) {
		//eov.printCMR();
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		 stage.setTitle("Cache based side channel attack in cloud");
	        final CategoryAxis xAxis = new CategoryAxis();
	        final NumberAxis yAxis = new NumberAxis();
	         xAxis.setLabel("Time");
	        final LineChart<String,Number> lineChart = 
	                new LineChart<String,Number>(xAxis,yAxis);
	       
	        lineChart.setTitle("Cache usage Monitoring");      
	        
	        lineChart.setCreateSymbols(false);     
	        lineChart.setAlternativeRowFillVisible(false);
	        
	        XYChart.Series series1 = new XYChart.Series();
	        series1.setName("CMR for GPG");
	        
	        //getting CMR for normal environment
	        ExtractOutputValues eov = new ExtractOutputValues();
			eov.readlinebyline("data/"+filename1);
			eov.caculateCMR();
	        
	        Iterator<Double> CMR_List_Iterator = eov.CMR_List.iterator();
			int i=1;
			while(CMR_List_Iterator.hasNext())
			{
				System.err.println(CMR_List_Iterator.next());
				series1.getData().add(new XYChart.Data(""+i, CMR_List_Iterator.next()));
				i++;
				
			}
			
			//getting CMR with CSCA
			XYChart.Series series2 = new XYChart.Series();
	        series2.setName("CMR for GPG during CSCA");
	        
			eov.readlinebyline("data/"+filename2);
			eov.caculateCMR();
			CMR_List_Iterator = eov.CMR_List.iterator();
			
			i=1;
			while(CMR_List_Iterator.hasNext())
			{
				System.err.println(CMR_List_Iterator.next());
				series2.getData().add(new XYChart.Data(""+i, CMR_List_Iterator.next()));
				i++;
				
			}
			
			Scene scene  = new Scene(lineChart);       
	        lineChart.getData().addAll(series1,series2);
	        scene.getStylesheets().add("Chart.css");                      
	        stage.setScene(scene);
	        stage.show();
	        
	}

}
