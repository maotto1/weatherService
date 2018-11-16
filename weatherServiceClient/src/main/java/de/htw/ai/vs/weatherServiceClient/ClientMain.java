package de.htw.ai.vs.weatherServiceClient;

import java.io.IOException;

public class ClientMain {
	
	EchoClient2 client;

	public static void main(String[] args) throws IOException {
		
		ClientMain main = new  ClientMain();
		main.start();
	}
	
	ClientMain(){
		
	}
	
	private void start() throws IOException{
		client = new EchoClient2(this);
		client.start();
	}

	public void neueVerbindung(Frame f) throws IOException {
		
		client.close();
		client.changeServer();
		client.start();		
	}
	


}
