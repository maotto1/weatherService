package de.htw.ai.vs.weatherServiceClient;

import java.io.*;
import java.net.*;

public class EchoClient {
	String hostName = "localhost";
	int portNumber = 6002;
	
	EchoClient(){}
	EchoClient(String host, int port){
		hostName = host;
		portNumber = port;
	}
	
    public void start() throws IOException {
            	
        try (
            Socket echoSocket = new Socket(hostName, portNumber);
            PrintWriter out =
                new PrintWriter(echoSocket.getOutputStream(), true);
            BufferedReader in =
                new BufferedReader(
                    new InputStreamReader(echoSocket.getInputStream()));
            BufferedReader stdIn =
                new BufferedReader(
                    new InputStreamReader(System.in))
        ) {
            String userInput, serverInput=null;
            while ((userInput = stdIn.readLine()) != null ) {
            	out.println(userInput);
            	//serverInput = in.readLine();
                while (!userInput.equals(serverInput= in.readLine())) {
                	if (serverInput != null)
                		System.out.println(serverInput);
                }
        
                System.out.println("echo: " + serverInput);

            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 
    }
}