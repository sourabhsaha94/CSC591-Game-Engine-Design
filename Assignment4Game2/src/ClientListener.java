/*This client is responsible for managing the list of connected clients,
 * managing the queue which stores the incoming and outgoing messages
 * add and delete clients.
 * Vector used here is part of java collections and is synchronized collection.
 * Several methods here are synchronized to make them thread safe as they
 * serve as critical update points.
 * */



import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.PriorityBlockingQueue;

public class ClientListener implements Runnable {

	Comparator<Message> comparator = new MessageIdComparator();
	private PriorityBlockingQueue<Message> messageQueue = new PriorityBlockingQueue<>(100, comparator);
	ClientInfo c;
	Player player;
	Set<Integer> tempSet = null;
	CopyOnWriteArrayList<Enemy> mpList = new CopyOnWriteArrayList<>();
	ConcurrentHashMap<Integer,Set<Integer>> pInfo = new ConcurrentHashMap<>();

	public synchronized void addClient(ClientInfo c){

		this.c = c;
	}

	public synchronized void sendMessage(ClientInfo c, Message m){

		messageQueue.add(m);

	}

	private synchronized Message getNextMessagefromQueue() throws InterruptedException{

		if(!messageQueue.isEmpty())
		{Message m = (Message) messageQueue.poll();
		return m;}
		return null;
	}


	private synchronized void sendMessagetoAllClients(Message m){

			c.out.sendMessage(m);
	}


	@Override
	public void run() {

		while(!Thread.interrupted()){
		
				try {

					Message m = getNextMessagefromQueue();
					if(m!=null)
						sendMessagetoAllClients(m);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	}


}
