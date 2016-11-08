import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;

public class EventManager implements Runnable{

	int id;

	Comparator<Event> comparator = new EventComparator();
	PriorityBlockingQueue<Event> eventQueue = new PriorityBlockingQueue<>(100, comparator);
	
	LinkedList<EventHandler> tempList = new LinkedList<>();
	
	private Map<EventPriority,LinkedList<EventHandler>> eventList = new HashMap<>();

	//list of events recognized
	//every event will have list of subscribers
	//on event raise, list is iterated

	private static EventManager em;

	private EventManager(){
		System.out.println("Event Manager started");
		this.id=1234;
	}
	
	public synchronized void registerEvent(EventHandler eh){
		
		tempList.add(eh);
		this.eventList.put(EventPriority.COLLISION, tempList);
		this.eventList.put(EventPriority.DEATH, tempList);
		this.eventList.put(EventPriority.SPAWN, tempList);
		this.eventList.put(EventPriority.HID, tempList);
		System.out.println("event handler registered");
	}

	public static EventManager getInstance(){
		if(em == null)
			em = new EventManager();

		return em;
	}
	public synchronized void addEvent(Event e){

		//Socket socket = c.socket;
		//message = socket.getRemoteSocketAddress()+" : "+message;
		eventQueue.add(e);
	}

	private synchronized Event getNextEventfromQueue() throws InterruptedException{

		if(!eventQueue.isEmpty())
		{Event e = eventQueue.poll();
		return e;}
		return null;
	}

	/*private synchronized void sendEventtoAllClients(Message m){

		for(int i=0;i<clientListener.clients.size();i++){
			ClientInfo c = (ClientInfo)clientListener.clients.get(i);
			c.out.sendMessage(m);
		}
	}*/

	@Override
	public void run() {

		while(!Thread.interrupted()){
		
			try {

					Event e = getNextEventfromQueue();
					if(e!=null){
						eventList.get(e.priority).peek().handleEvent(e);
						System.out.println(e.priority);
					}
						
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	}


}
