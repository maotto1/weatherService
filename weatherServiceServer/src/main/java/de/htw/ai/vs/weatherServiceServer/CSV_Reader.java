package de.htw.ai.vs.weatherServiceServer;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class CSV_Reader {
	
	private File file;
		
	public CSV_Reader(){
        String fileName = "src\\main\\ressources\\wetterdaten.csv";
        this.file = new File(fileName);
        
        // Verz�gern
        try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}
	}

	CSV_Reader(File file){
		this.file = file;
	}
	
	/**
	 * 
	 * @param cal
	 * @return
	 */
	private String parseGregorianCalendarToString(GregorianCalendar cal) {
		int year = cal.get(GregorianCalendar.YEAR)%100;
		int month = cal.get(GregorianCalendar.MONTH)+1;
		int day = cal.get(GregorianCalendar.DAY_OF_MONTH);
		return (day<10 ? "0" :"") + Integer.toString(day)+"." 
			+( month<10 ? "0":"") + Integer.toString(month)+"."
			+ Integer.toString(year);
	}
	
	public WeatherDate readValuesOf(GregorianCalendar cal){ 
		return readValuesOf(parseGregorianCalendarToString(cal));
		
	}
	
	
	public WeatherDate readValuesOf(String date){    
		WeatherDate wetter = new WeatherDate(date);
		
		try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {             
            	if(date.equals(nextLine[0])) {
            		wetter.addTemperature(Integer.parseInt(nextLine[1]), Double.parseDouble(nextLine[2]));    
            	}
            }
        } catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        return wetter;
	}
	
	/**
	 * sucht, ob Tag im Datensatz vorkommt und pr�ft, ob jede Stunde im Datensatz vorkommt 
	 * @param date
	 * @return
	 */
	public boolean areThereCompleteDatesTo(String date){
		
		int checksum=0;
		//String date;
        
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] nextLine;
            
            while ((nextLine = reader.readNext()) != null) {
            	if(date.equals(nextLine[0])) {
            		checksum += Integer.parseInt( nextLine[1]);
            	}
            }
        } catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			// return false; �berfl�ssig
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        if (checksum != (int)(23 *24 / 2) ) 
        	return false;
        return true;
	}
	
	public boolean areThereCompleteDatesTo(GregorianCalendar date){
		return areThereCompleteDatesTo(parseGregorianCalendarToString(date));
	}
}
