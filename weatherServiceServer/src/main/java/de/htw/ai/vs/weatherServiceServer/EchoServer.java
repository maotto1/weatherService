/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 
package de.htw.ai.vs.weatherServiceServer;

import java.net.*;
import java.util.logging.Logger;
import java.io.*;

public class EchoServer {
	
	private int portNumber = 6002;
	private static final Logger logger = Logger.getLogger( EchoServer.class.getName() );
	
	EchoServer(){
		
	}
	
	EchoServer(int port){
		portNumber = port;
	}
	
	private void handleClient(Socket clientSocket) {
		 try (  
            PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);                   
            BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
            	logger.info("auf Port "+ clientSocket.getPort() + " kam von Client " + clientSocket.getInetAddress().getHostAddress() + " folgende Anfrage: " + inputLine + " wird bearbeitet von "+ Thread.currentThread().getId() );
                if (inputLine.trim().contentEquals("close")) {
                	clientSocket.close();
                	in.close();
                	out.close();
                	System.out.println("Verbindung auf Wunsch vom Client beendet.");
                	Thread.currentThread().interrupt();
                }
            	
            	if (inputLine.trim().length()==8) { // dann k�nnte es sich um ein Datum handeln
                	CSV_Reader reader = new CSV_Reader();
                	
                	if (reader.areThereCompleteDatesTo(inputLine.trim())) {
                		WeatherDate w = reader.readValuesOf(inputLine.trim());
                		out.println(w.print());
                	}
                	else
                		out.println("Datensatz nicht vorhanden oder unvollst�ndig");
                		
                }
                else
                	out.println("kein g�ltiges Datum. Gefordert wird: dd.mm.YY");
                out.println(inputLine);
            }
        } catch (IOException e) {
        	System.out.println("handle");
            System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
	}
		
    public void start() throws IOException {   
    	logger.info("Server gestartet, bearbeitender Thread:  " + Thread.currentThread().getName());
    	ServerSocket serverSocket = new ServerSocket(portNumber);
    	while (true) {
	        try {
	        	Socket clientSocket = serverSocket.accept();   
	        	 Thread thread = new Thread(new Runnable() {
	        		@Override public void run() {
	        			logger.info("neuer Thread gestartet: " + Thread.currentThread().getName());
	        			handleClient(clientSocket);
	        		}
	        	});
	        	 thread.start();
	        	//serverSocket = new ServerSocket(portNumber);
	
	        } catch (IOException e) {
	            System.out.println("Exception caught when trying to listen on port "
	                + portNumber + " or listening for a connection");
	            System.out.println(e.getMessage());
	        }
    	}
    }
}