package de.htw.ai.vs.weatherServiceServer;

import java.io.IOException;

public class ServerMain {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		EchoServer server = new EchoServer();
		server.start();
	
		/*
		CSV_Reader reader = new CSV_Reader();
		if(reader.gibtEsVollst�ndigeDatenZu("27.10.17")) {
			System.out.println(reader.leseWerteEinesTages("27.10.17").print());
		} */
			
		
	}

}
