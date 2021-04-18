package utp.manu.file;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class CreateChartmeanandcachemisses extends Application  {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		 stage.setTitle("Cache based side channel attack in cloud");
	        final CategoryAxis xAxis = new CategoryAxis();
	        final NumberAxis yAxis = new NumberAxis();
	         xAxis.setLabel("Mean");
	        final LineChart<String,Number> lineChart = 
	                new LineChart<String,Number>(xAxis,yAxis);
	       
	yAxis.setLabel("Number of cache miss");    
	        
	        lineChart.setCreateSymbols(false);     
	        lineChart.setAlternativeRowFillVisible(false);
	        
	        XYChart.Series series1 = new XYChart.Series();
	        series1.setName("mean");
	        
	        //getting CMR for normal environment

	        FileInputStream fstream = new FileInputStream("data/meanandcachemisses.csv");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader( new InputStreamReader(in));
			String line="";
			
			while((line = br.readLine()) != null){
				if(line.isEmpty())
					continue;
				if(Character.isDigit(line.charAt(0)))
				{
					String SplitLine[] = line.split(",");
				series1.getData().add(new XYChart.Data(SplitLine[0].trim(), Double.parseDouble(SplitLine[1].trim())));			
			}
			}
			
			Scene scene  = new Scene(lineChart);       
	        lineChart.getData().addAll(series1);
	        scene.getStylesheets().add(getClass().getResource("Chart.css").toExternalForm());
	  
	        stage.setScene(scene);
	        stage.show();
		
	}

}
