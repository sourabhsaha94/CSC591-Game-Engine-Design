/*This client is responsible for managing the list of connected clients,
 * managing the queue which stores the incoming and outgoing messages
 * add and delete clients.
 * Vector used here is part of java collections and is synchronized collection.
 * Several methods here are synchronized to make them thread safe as they
 * serve as critical update points.
 * */



import java.util.Comparator;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.PriorityBlockingQueue;

public class ClientListener implements Runnable {

	Comparator<Message> comparator = new MessageIdComparator();
	private PriorityBlockingQueue<Message> messageQueue = new PriorityBlockingQueue<>(100, comparator);
	Vector clients = new Vector();
	Set<Integer> tempSet = null;
	CopyOnWriteArrayList<StaticPlatform> spList = new CopyOnWriteArrayList<>();
	CopyOnWriteArrayList<MovingPlatform> mpList = new CopyOnWriteArrayList<>();
	CopyOnWriteArrayList<Player> allPlayerList = new CopyOnWriteArrayList<>();
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
		
		Message m = (Message) messageQueue.poll();
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
		
		while(!Thread.interrupted()){
			
			try {
					
					Message m = getNextMessagefromQueue();
					sendMessagetoAllClients(m);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}


}
