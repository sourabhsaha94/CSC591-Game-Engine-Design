import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.PriorityBlockingQueue;

public class ClientEventManager implements Runnable{

	int id;

	Comparator<Event> comparator = new EventComparator();
	PriorityBlockingQueue<Event> eventQueue = new PriorityBlockingQueue<>(100, comparator);
	
	Map<EventType,ArrayList<EventHandler>> eventList = new HashMap<>(); 

	//list of events recognized
	//every event will have list of subscribers
	//on event raise, list is iterated

	private static ClientEventManager em;

	private ClientEventManager(){
		this.id=1234;
		eventList.put(EventType.COLLISION, new ArrayList<>());
		eventList.put(EventType.HID, new ArrayList<>());
		eventList.put(EventType.SPAWN, new ArrayList<>());
		eventList.put(EventType.DEATH, new ArrayList<>());
		eventList.put(EventType.RECORD_START_STOP, new ArrayList<>());
		eventList.put(EventType.PLAYBACK, new ArrayList<>());
		eventList.put(EventType.SLOW, new ArrayList<>());
		eventList.put(EventType.FAST, new ArrayList<>());
		eventList.put(EventType.NORMAL, new ArrayList<>());
	}

	public void registerEvent(EventHandler eh){
		eventList.get(EventType.COLLISION).add(eh);
		eventList.get(EventType.HID).add(eh);
		eventList.get(EventType.SPAWN).add(eh);
		eventList.get(EventType.DEATH).add(eh);
		eventList.get(EventType.RECORD_START_STOP).add(eh);
		eventList.get(EventType.PLAYBACK).add(eh);
		eventList.get(EventType.SLOW).add(eh);
		eventList.get(EventType.FAST).add(eh);
		eventList.get(EventType.NORMAL).add(eh);
		
	}
	
	public static ClientEventManager getInstance(){
		if(em == null)
			em = new ClientEventManager();

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
						//handleEvent(e);
						//e.p.handleEvent(e);
						eventList.get(e.type).forEach(v->v.handleEvent(e));
						
						System.out.println(e.type);
					}
						
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	}


}
