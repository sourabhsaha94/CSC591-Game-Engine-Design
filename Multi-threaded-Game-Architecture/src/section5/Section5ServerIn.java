/*This class handles incoming communication from client*/


package section5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Section5ServerIn implements Runnable {

	Section5ClientListener clientListener;
	ClientInfo c;
	BufferedReader input;
	
	public Section5ServerIn(ClientInfo c, Section5ClientListener cl) throws IOException {
		this.c = c;
		this.clientListener=cl;
		Socket socket = c.socket;
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	@Override
	public void run() {
		
		try{
			while(!Thread.interrupted()){
				String message = input.readLine();
				if(message==null)
					break;
				clientListener.sendMessage(c, message);
				
			}
		}catch(IOException e){
			e.printStackTrace();
		}
		
		Thread.currentThread().interrupt();
		clientListener.deleteClient(c);
	}


}
