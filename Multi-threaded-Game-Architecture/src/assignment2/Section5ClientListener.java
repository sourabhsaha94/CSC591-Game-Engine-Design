/*This client is responsible for managing the list of connected clients,
 * managing the queue which stores the incoming and outgoing messages
 * add and delete clients.
 * Vector used here is part of java collections and is synchronized collection.
 * Several methods here are synchronized to make them thread safe as they
 * serve as critical update points.
 * */

package assignment2;

import java.net.Socket;
import java.util.Vector;

public class Section5ClientListener implements Runnable {

	private Vector messageQueue = new Vector();
	private Vector clients = new Vector();
	
	public synchronized void addClient(ClientInfo c){
		
		clients.add(c);
	}
	
	
	public synchronized void deleteClient(ClientInfo c){
		
		int index = clients.indexOf(c);
		if(index != -1){
			clients.removeElementAt(index);
		}
	}
	
	
	public synchronized void sendMessage(ClientInfo c, Message m){
	
		Socket socket = c.socket;
		//message = socket.getRemoteSocketAddress()+" : "+message;
		messageQueue.add(m);
		notify();
	}
	
	private synchronized Message getNextMessagefromQueue() throws InterruptedException{
		
		while(messageQueue.size()==0)
			wait();
		
		Message m = (Message) messageQueue.get(0);
		messageQueue.removeElementAt(0);
		return m;
	}
	
	
	private synchronized void sendMessagetoAllClients(Message m){
		
		for(int i=0;i<clients.size();i++){
			ClientInfo c = (ClientInfo)clients.get(i);
			c.out.sendMessage(m);
		}
	}
	@Override
	public void run() {
		
		while(true){
			try {
				Message m = getNextMessagefromQueue();
				sendMessagetoAllClients(m);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}


}
