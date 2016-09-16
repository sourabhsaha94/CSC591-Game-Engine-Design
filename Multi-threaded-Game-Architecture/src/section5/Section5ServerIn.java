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
		
		try{
			while(!Thread.interrupted()){
				Thing thing = (Thing)input.readObject();
				if(thing==null)
					break;
				clientListener.sendMessage(c, thing);
				
			}
		}catch(IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Thread.currentThread().interrupt();
		clientListener.deleteClient(c);
	}


}
