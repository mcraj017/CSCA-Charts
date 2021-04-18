package utp.manu.file;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class CompareDetectionTimeofSimulationandTestCloud extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		stage.setTitle("Comparision of detection time in simulation and cloud");
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Number of samples");
		final LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);

		yAxis.setLabel("Execution Time (ms)");
		lineChart.setTitle("Comparision of detection time in simulation and cloud");   

		lineChart.setCreateSymbols(false);
		lineChart.setAlternativeRowFillVisible(false);

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("Execution Time in simulation");

		// getting CMR for normal environment

		FileInputStream fstream = new FileInputStream("data/ExecutiontimeCMS101samples.csv");
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String line = "";
		
		int samples =0;
		while ((line = br.readLine()) != null) {
			if (line.isEmpty())
				continue;
			if (Character.isDigit(line.charAt(0))) {
				String SplitLine[] = line.split(",");
				series1.getData().add(new XYChart.Data(SplitLine[0].trim(), Double.parseDouble(SplitLine[1].trim())));
			}
		}
		// getting CMR for normal environment
		ExtractOutputValues eov = new ExtractOutputValues();
		eov.readlinebylineexetime("data/detection_time.data");
		eov.caculateCMR();

		XYChart.Series series2 = new XYChart.Series();
		series2.setName("Execution Time in test cloud");
		Iterator<Double> exe_time_Iterator = eov.exe_time.iterator();
		int i = 1;
		while (exe_time_Iterator.hasNext()) {
			series2.getData().add(new XYChart.Data("" + i, exe_time_Iterator.next()));
			i++;
		}
		Scene scene = new Scene(lineChart);
		lineChart.getData().addAll(series2,series1);
		scene.getStylesheets().add(getClass().getResource("Chart.css").toExternalForm());

		stage.setScene(scene);
		stage.show();

	}
}
