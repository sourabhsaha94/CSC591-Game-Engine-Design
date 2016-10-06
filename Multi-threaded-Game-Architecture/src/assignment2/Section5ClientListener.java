/*This client is responsible for managing the list of connected clients,
 * managing the queue which stores the incoming and outgoing messages
 * add and delete clients.
 * Vector used here is part of java collections and is synchronized collection.
 * Several methods here are synchronized to make them thread safe as they
 * serve as critical update points.
 * */

package assignment2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

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
	
	
	public synchronized void sendMessage(ClientInfo c, Integer pos){
	
		Socket socket = c.socket;
		//message = socket.getRemoteSocketAddress()+" : "+message;
		messageQueue.add(pos);
		notify();
	}
	
	private synchronized Integer getNextMessagefromQueue() throws InterruptedException{
		
		while(messageQueue.size()==0)
			wait();
		
		Integer pos = (Integer) messageQueue.get(0);
		messageQueue.removeElementAt(0);
		return pos+10;
	}
	
	
	private synchronized void sendMessagetoAllClients(Integer pos){
		
		for(int i=0;i<clients.size();i++){
			ClientInfo c = (ClientInfo)clients.get(i);
			c.out.sendMessage(pos);
		}
	}
	@Override
	public void run() {
		
		while(true){
			try {
				Integer newPos = getNextMessagefromQueue();
				sendMessagetoAllClients(newPos);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}


}
