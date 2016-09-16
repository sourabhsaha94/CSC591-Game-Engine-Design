/*This class handles incoming communication from client*/


package section5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

import assignment1.Thing;

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
		
		boolean check=true;
		
			while(check){
				Thing thing = null;
				
				while(check){
				try{
				thing = (Thing)input.readObject();
				System.out.println(thing.name+" in server recieve");
				}
				catch(Exception e){
					break;
				}
				clientListener.sendMessage(c, thing);
				}
			}
		
		
		Thread.currentThread().interrupt();
		clientListener.deleteClient(c);
	}


}
