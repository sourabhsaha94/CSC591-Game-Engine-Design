/*This class handles incoming communication from client*/


package assignment2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Section5ServerIn implements Runnable {

	Section5ClientListener clientListener;
	ClientInfo c;
	ObjectInputStream input;
	
	public Section5ServerIn(ClientInfo c, Section5ClientListener cl) throws IOException {
		this.c = c;
		this.clientListener=cl;
		Socket socket = c.socket;
		input = new ObjectInputStream(socket.getInputStream());
	}
	
	@Override
	public void run() {
		
		Message m;
		
		while(!Thread.interrupted()){
			
			
				try{
					m = (Message)input.readObject();
				}
				catch(Exception e){
					break;
				}
				clientListener.sendMessage(c, m);
			
		}
		
		Thread.currentThread().interrupt();
		clientListener.deleteClient(c);
	}

}
