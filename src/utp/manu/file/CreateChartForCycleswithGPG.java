package utp.manu.file;
import java.util.Iterator;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
public class CreateChartForCycleswithGPG extends Application{
	public static void main(String[] args) {
		//eov.printCMR();
		launch(args);
	}
	@Override
	public void start(Stage stage) throws Exception {

				 stage.setTitle("Cache based side channel attack in cloud");
			        final CategoryAxis xAxis = new CategoryAxis();
			        final NumberAxis yAxis = new NumberAxis();
			         xAxis.setLabel("Trials Of FLush + Reload");
			        final LineChart<String,Number> lineChart = 
			                new LineChart<String,Number>(xAxis,yAxis);
			       
			        lineChart.setTitle("CPU usage Monitoring");      
			        
			        lineChart.setCreateSymbols(false);     
			        lineChart.setAlternativeRowFillVisible(false);
			        
			        XYChart.Series series1 = new XYChart.Series();
			        series1.setName("CPU-Cycles for GPG");
			        
			        //getting CMR for normal environment
			        ExtractOutputValues eov = new ExtractOutputValues();
					eov.readlinebylinewithcycles("data/single_file_data/csca_data_cpu_cycles_only_gpg.dat");
					eov.caculateCMR();
			        
			        Iterator<Double> cycles_Iterator = eov.cycles.iterator();
					int i=1;
					while(cycles_Iterator.hasNext())
					{
	
						series1.getData().add(new XYChart.Data(""+i, cycles_Iterator.next()));
						i++;
						
					}
					
					//getting CMR with CSCA
					XYChart.Series series2 = new XYChart.Series();
			        series2.setName("CPU-Cycles for GPG during CSCA");
			        
					eov.readlinebylinewithcycles("data/single_file_data/csca_data_cpu_cycles.dat");
					eov.caculateCMR();
					cycles_Iterator = eov.cycles.iterator();
					
					i=1;
					while(cycles_Iterator.hasNext())
					{
						series2.getData().add(new XYChart.Data(""+i, cycles_Iterator.next()));
						i++;
						
					}
					
					Scene scene  = new Scene(lineChart);       
			        lineChart.getData().addAll(series1,series2);
			        scene.getStylesheets().add(getClass().getResource("Chart.css").toExternalForm());
			  
			        stage.setScene(scene);
			        stage.show();
			     
			        
			}
	}
	

