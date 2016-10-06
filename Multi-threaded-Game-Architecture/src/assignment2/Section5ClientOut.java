/*This class is responsible for sending message to the server
 * */

package assignment2;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Section5ClientOut implements Runnable {
	
	private ObjectOutputStream out;
	ConcurrentHashMap<String,Thing> pList;
	Socket s=null;
	
	public Section5ClientOut(Socket s,ObjectOutputStream out, ConcurrentHashMap<String,Thing> pList){

		this.s=s;
		this.out = out;
		this.pList = pList;

	}

	public void run(){

		try {


			while(!Thread.interrupted()) {
				
				Message message = new Message(s.getLocalSocketAddress().toString(),pList.get(s.getLocalSocketAddress().toString()).R.x,pList.get(s.getLocalSocketAddress().toString()).R.y);
				
				out.writeObject(message);
				out.reset();

			}

		} catch (IOException ioe) {

			// Communication is broken

		}

	}
}
