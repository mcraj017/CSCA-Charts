package utp.manu.file;

import java.util.Iterator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class createdetectiontimechart extends Application{
	public static void main(String[] args) {
		//eov.printCMR();
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {

				 stage.setTitle("Cache based side channel attack in cloud");
			        final CategoryAxis xAxis = new CategoryAxis();
			        final NumberAxis yAxis = new NumberAxis();
			         xAxis.setLabel("Number of samples");
			         yAxis.setLabel("Time (milliseconds)");
			        final LineChart<String,Number> lineChart = 
			                new LineChart<String,Number>(xAxis,yAxis);
			       
			        lineChart.setTitle("Executoin time monitoring");      
			        
			        lineChart.setCreateSymbols(false);     
			        lineChart.setAlternativeRowFillVisible(false);
			        
			        XYChart.Series series1 = new XYChart.Series();
			        series1.setName("execution time of detection");
			        
			        //getting CMR for normal environment
			        ExtractOutputValues eov = new ExtractOutputValues();
					eov.readlinebylineexetime("data/detection_time.data");
					eov.caculateCMR();
			        
			        Iterator<Double> exe_time_Iterator = eov.exe_time.iterator();
					int i=1;
					while(exe_time_Iterator.hasNext())
					{
						series1.getData().add(new XYChart.Data(""+i, exe_time_Iterator.next()));
						i++;
						
					}
					Scene scene  = new Scene(lineChart);       
			        lineChart.getData().addAll(series1);
			        scene.getStylesheets().add(getClass().getResource("Chart.css").toExternalForm());
			  
			        stage.setScene(scene);
			        stage.show();
			     
			        
			}
	}
	
