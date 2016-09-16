/*This client is responsible for managing the list of connected clients,
 * managing the queue which stores the incoming and outgoing messages
 * add and delete clients.
 * Vector used here is part of java collections and is synchronized collection.
 * Several methods here are synchronized to make them thread safe as they
 * serve as critical update points.
 * */

package section5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import assignment1.Thing;

public class Section5ClientListener implements Runnable {

	private Vector<Thing> messageQueue = new Vector<Thing>();
	private Vector<ClientInfo> clients = new Vector<ClientInfo>();
	
	public synchronized void addClient(ClientInfo c){
		clients.add(c);
	}
	
	
	public synchronized void deleteClient(ClientInfo c){
		int index = clients.indexOf(c);
		if(index != -1){
			clients.removeElementAt(index);
		}
	}
	
	
	public synchronized void sendMessage(ClientInfo c,Thing thing){
		messageQueue.add(thing);
		notify();
	}
	
	private synchronized Thing getNextMessagefromQueue() throws InterruptedException{
		while(messageQueue.size()==0)
			wait();
		
		Thing thing = (Thing) messageQueue.get(0);
		messageQueue.removeElementAt(0);
		return thing;
	}
	
	
	private synchronized void sendMessagetoAllClients(Thing thing){
		for(int i=0;i<clients.size();i++){
			ClientInfo c = (ClientInfo)clients.get(i);
			c.out.sendMessage(thing);
		}
	}
	@Override
	public void run() {
		while(true){
			try {
				Thing thing = getNextMessagefromQueue();
				sendMessagetoAllClients(thing);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}


}
