package utp.manu.file;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class CreateChartCMRMeanMultiTimeSlots extends Application {
	ArrayList<Double> LLC_Loads_List = new ArrayList<>();
	ArrayList<Double> LLC_Loads_misses_List = new ArrayList<>();
	
	ArrayList<Double> means = new ArrayList<>();
	

	public static void main(String[] args) {
		/*CreateChartCMRMeanMultiTimeSlots ccmt = new CreateChartCMRMeanMultiTimeSlots();
		ccmt.means = ccmt.GenerateMeanRSA();*/
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		stage.setTitle("Cache based side channel attack in cloud");
		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Time Slots");
		final LineChart<String, Number> lineChart = new LineChart<String, Number>(xAxis, yAxis);
		lineChart.setTitle("CMR mean of GPG with Elgamal");   
		yAxis.setLabel("mean");

		lineChart.setCreateSymbols(false);
		lineChart.setAlternativeRowFillVisible(false);

		XYChart.Series series1 = new XYChart.Series();
		series1.setName("mean");

		// getting CMR for normal environment
		
		
		
		ArrayList<Double> means = new ArrayList<>();
		ArrayList<Double> diffrence = new ArrayList<>();
		double mean = 0;

		
		
		for (int i = 4000; i <= 5000; i++) {
			mean = 0;
			diffrence.clear();
			
			readlinebyline("data/data_4000_TS/csca_data_10_200_times_only_gpg_dsa.dat");
			Iterator<Double> GPG_CMR_List_Iterator = caculateCMR().iterator();
			readlinebyline("data/data_4000_TS/data_time_slots_dsa/csca_data_2500_" + i + ".dat");
			Iterator<Double> RSA_CMR_List_Iterator = caculateCMR().iterator();
			
			System.out.println("here");
			while (RSA_CMR_List_Iterator.hasNext() && GPG_CMR_List_Iterator.hasNext()) {
				double RSA_CMR = RSA_CMR_List_Iterator.next();
				double GPG_CMR = GPG_CMR_List_Iterator.next();
				//System.out.println(RSA_CMR + "-" + GPG_CMR);
				diffrence.add(RSA_CMR - GPG_CMR);
			}

			Iterator<Double> diffrence_Iterator = diffrence.iterator();
			System.out.println("here");
			while (diffrence_Iterator.hasNext()) {
				mean = mean + diffrence_Iterator.next();
			}
			mean = mean / 200;
			System.out.println(mean);
			means.add(mean);
		}
		Iterator<Double> means_Iterator = means.iterator();
		int tslots = 4000;
		while (means_Iterator.hasNext()) {
			double mean1 = means_Iterator.next();

			series1.getData().add(new XYChart.Data("" + tslots, mean1));
			tslots++;
		}

		Scene scene = new Scene(lineChart);
		lineChart.getData().addAll(series1);
		scene.getStylesheets().add(getClass().getResource("Chart.css").toExternalForm());

		stage.setScene(scene);
		stage.show();

	}

	/*ArrayList<Double> GenerateMeanRSA() {
		ArrayList<Double> means = new ArrayList<>();
		// ArrayList<Double> RSA_CMR_List = new ArrayList<>();
		ArrayList<Double> diffrence = new ArrayList<>();
		double mean = 0;

		// ArrayList<Double> GPG_CMR_List = new ArrayList<>();

		// printCMR();
		// GPG_CMR_List = CMR_List;

		for (int i = 4000; i <= 5000; i++) {
			mean = 0;
			// RSA_CMR_List.clear();
			diffrence.clear();
			readlinebyline("data/data_4000_TS/csca_data_10_200_times_only_gpg.dat");
			caculateCMR();
			Iterator<Double> GPG_CMR_List_Iterator = caculateCMR().iterator();

			readlinebyline("data/data_4000_TS/data_time_slots_rsa/csca_data_2500_" + i + ".dat");

			Iterator<Double> RSA_CMR_List_Iterator = caculateCMR().iterator();

			System.out.println("here");
			while (RSA_CMR_List_Iterator.hasNext() && GPG_CMR_List_Iterator.hasNext()) {
				double RSA_CMR = RSA_CMR_List_Iterator.next();
				double GPG_CMR = GPG_CMR_List_Iterator.next();
				System.out.println(RSA_CMR + "-" + GPG_CMR);
				diffrence.add(RSA_CMR - GPG_CMR);
			}

			Iterator<Double> diffrence_Iterator = diffrence.iterator();
			System.out.println("here");
			while (diffrence_Iterator.hasNext()) {
				mean += diffrence_Iterator.next();
			}
			mean = mean / 200;
			System.out.println(mean);
			means.add(mean);
		}

		return means;
	}*/

	public void readlinebyline(String filename) {
		try {
			LLC_Loads_List.clear();
			LLC_Loads_misses_List.clear();
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = "";

			while ((line = br.readLine()) != null) {
				if (line.isEmpty())
					continue;
				if (Character.isDigit(line.charAt(0))) {
					String SplitLine[] = line.split(",");
					// System.err.println(SplitLine[0]);
					if (SplitLine[1].trim().equalsIgnoreCase("LLC-loads")) {

						LLC_Loads_List.add(Double.parseDouble(SplitLine[0]));
					} else if (SplitLine[1].trim().equalsIgnoreCase("LLC-load-misses")) {
						LLC_Loads_misses_List.add(Double.parseDouble(SplitLine[0]));
					} else {
						// System.out.println("Data missing");
					}

				}

			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error while reading the file:" + e.getMessage());
		}

		// System.err.println(i);
	}

	public ArrayList<Double> caculateCMR() {
		Iterator<Double> LLC_Loads_List_Iterator = LLC_Loads_List.iterator();
		Iterator<Double> LLC_Loads_misses_List_Iterator = LLC_Loads_misses_List.iterator();
		ArrayList<Double> CMR_List = new ArrayList<>();
		CMR_List.clear();
		while (LLC_Loads_List_Iterator.hasNext() && LLC_Loads_misses_List_Iterator.hasNext()) {
			Double CMR = LLC_Loads_misses_List_Iterator.next() / LLC_Loads_List_Iterator.next();
			CMR_List.add(CMR);
		}
		return CMR_List;
	}

	public void printCMR() {
		Iterator<Double> CMR_List_Iterator = caculateCMR().iterator();
		// Iterator<Double> cycles_Iterator = cycles.iterator();
		System.err.println("printing CMR");
		while (CMR_List_Iterator.hasNext()) {
			System.out.println(CMR_List_Iterator.next());
			// System.out.println(CMR_List_Iterator.next()+","+cycles_Iterator.next());

		}
	}
}
