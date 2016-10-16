/*This client is responsible for managing the list of connected clients,
 * managing the queue which stores the incoming and outgoing messages
 * add and delete clients.
 * Vector used here is part of java collections and is synchronized collection.
 * Several methods here are synchronized to make them thread safe as they
 * serve as critical update points.
 * */



import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Section5ClientListener implements Runnable {

	private Vector messageQueue = new Vector();
	private Vector clients = new Vector();
	Set<Integer> tempSet = null;
	CopyOnWriteArrayList<Integer> allPlayerList = new CopyOnWriteArrayList<>();
	CopyOnWriteArrayList<StaticPlatform> spList = new CopyOnWriteArrayList<>();
	CopyOnWriteArrayList<MovingPlatform> mpList = new CopyOnWriteArrayList<>();
	CopyOnWriteArrayList<DeathZone> dzList = new CopyOnWriteArrayList<>();
	ConcurrentHashMap<Integer,Set<Integer>> pInfo = new ConcurrentHashMap<>();
	
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
	
		//Socket socket = c.socket;
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
			if(c.id!=m.id)
			{
				System.out.println("sending to client "+c.id);
				c.out.sendMessage(m);
			}
		}
	}
	
	
	@Override
	public void run() {
		
	
		
		while(!Thread.interrupted()){
			
			try {
					
					Message m = getNextMessagefromQueue();
					sendMessagetoAllClients(m);
				
				//System.out.println(((ClientInfo)clients.lastElement()).id);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}


}
