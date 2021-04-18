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
public class CreateChartForCMRwithGPG extends Application{
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
			       
			        lineChart.setTitle("Cache usage Monitoring");      
			        
			        lineChart.setCreateSymbols(false);     
			        lineChart.setAlternativeRowFillVisible(false);
			        
			        XYChart.Series series1 = new XYChart.Series();
			        series1.setName("CMR for GPG with DSA");
			        
			        //getting CMR for normal environment
			        ExtractOutputValues eov = new ExtractOutputValues();
					eov.readlinebyline("data/data_4000_TS/csca_data_10_200_times_only_gpg_elgamal.dat");
					eov.caculateCMR();
			        
			        Iterator<Double> CMR_List_Iterator = eov.CMR_List.iterator();
					int i=1;
					while(CMR_List_Iterator.hasNext())
					{
						series1.getData().add(new XYChart.Data(""+i, CMR_List_Iterator.next()));
						i++;
						
					}
					
					//getting CMR with CSCA
					XYChart.Series series2 = new XYChart.Series();
			        series2.setName("CMR for GPG with DSA during CSCA");
			        
					eov.readlinebyline("data/data_4000_TS/csca_data_10_200_times_elgamal.dat");
					eov.caculateCMR();
					CMR_List_Iterator = eov.CMR_List.iterator();
					
					i=1;
					while(CMR_List_Iterator.hasNext())
					{
						series2.getData().add(new XYChart.Data(""+i, CMR_List_Iterator.next()));
						i++;
						
					}
					
					Scene scene  = new Scene(lineChart);       
			        lineChart.getData().addAll(series1,series2);
			        scene.getStylesheets().add(getClass().getResource("Chart.css").toExternalForm());
			  
			        stage.setScene(scene);
			        stage.show();
			     
			        
			}
	}
	

