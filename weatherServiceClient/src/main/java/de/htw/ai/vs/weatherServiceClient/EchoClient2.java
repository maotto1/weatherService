package de.htw.ai.vs.weatherServiceClient;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EchoClient2 {
	String hostName = "localhost";
	int portNumber = 6002;
	private Frame f;

	private Socket echoSocket;
	private PrintWriter out;
	private BufferedReader in;

	
	EchoClient2(ClientMain main){		
		new Runnable() {
			public void run() {
				f = new Frame(main);
			}
		}.run();
	}
	EchoClient2(String host, int port, ClientMain main){
		hostName = host;
		portNumber = port;
		new Runnable() {
			public void run() {
				f = new Frame(main);
			}
		}.run();
	}
	EchoClient2(Frame frame){
		f =frame;
		portNumber = f.askPort();
		hostName = f.askHostName();
	}

	
    public void start() {
        try {
			echoSocket = new Socket(hostName, portNumber);
	    	out = new PrintWriter(echoSocket.getOutputStream(), true);
	        in =
	            new BufferedReader(
	                new InputStreamReader(echoSocket.getInputStream()));
	        run();
		} catch (UnknownHostException e) {
			f.println("Don't know about host " + hostName);
            //System.exit(1);
		} catch (IOException e) {
        	f.println("Couldn't get I/O for the connection to " +
                    hostName);
        	e.printStackTrace();
             //System.exit(1);
		}

    }
	
	
	public void run() throws IOException {
            	
            String userInput, serverInput=null;
            while (true) {
            	try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Automatisch generierter Erfassungsblock
					e.printStackTrace();
				}
            if ((userInput = f.readLine()) != null ) {
            	out.println(userInput);
            	//serverInput = in.readLine();
                while (!userInput.equals(serverInput= in.readLine())) {
                	if (serverInput != null)
                		f.println(serverInput);
                }
        
                System.out.println("echo: " + serverInput);

            }}
            
            
     
    }
	
	public void close() {
		try {
			in.close();
			out.println("close");
			out.close();
			echoSocket.close();
			System.out.println(echoSocket.isClosed());
			
		} catch (IOException e) {
			// TODO Automatisch generierter Erfassungsblock
			e.printStackTrace();
		}

	}
	public void changeServer() {
		portNumber = f.askPort();
		hostName = f.askHostName();
		System.out.println(portNumber);
		
	}
	
	public Frame getF() {
		return f;
	}
    
}