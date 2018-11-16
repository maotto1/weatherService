package de.htw.ai.vs.weatherServiceServer;

//import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Date;

public class WeatherDate{
	
	final String day;
	private double[] temperatures;
	private double meanTemp, maxTemp, minTemp;
	private boolean maxCalculated=false, minCalculated=false, meanCalculated=false;
	
	WeatherDate(String date){
		day = date;
		temperatures = new double[24];		
	}
	
	void addTemperature(int hour, double temp) {
		
		 assert (0.0 == temperatures[hour]);
		 temperatures[hour] = temp;
		
	}
	
	
	public double calculateMaximumTemperature() {
		if (!maxCalculated) {
			double max=-Double.MAX_VALUE; 
			for (int i=0; i<24; i++) {
				if (max < temperatures[i])
					max = temperatures[i];
			}
			maxTemp = max;
			maxCalculated = true;
		}
		return maxTemp;
	}
	
	public double calculateMinimumTemperature() {
		if (!minCalculated) {
			double min=Double.MAX_VALUE; 
			for (int i=0; i<24; i++) {
				if (min > temperatures[i])
					min = temperatures[i];
			}
			minTemp = min;
			minCalculated = true;
		}
		return minTemp;
	}
	
	public double calculateMeanTemperature() {
		if (!meanCalculated) {
			double sum=0; 
			for (int i=0; i<24; i++) {
				sum += temperatures[i];
			}
			meanTemp = sum / 24;
			//NumberFormat n = 
			meanCalculated = true;
		}
		return meanTemp;
	}

	public String print() {
		if (!minCalculated)
			calculateMinimumTemperature();
		if (!maxCalculated)
			calculateMaximumTemperature();
		if (!meanCalculated)
			calculateMeanTemperature();
		String result = "\nWetterdaten vom  "+day+"\n";
		for (int i=0; i<24; i++) {
			result+= "\n" + i + " Uhr\t" + temperatures[i]+" �C";
		}
		result += "\nMinimum:\t"+minTemp+" �C" 
		 + "\nMaximum:\t"+maxTemp+" �C" 
		 + "\nMean Value:\t"+meanTemp+" �C"; 
		return result;
	}

}
