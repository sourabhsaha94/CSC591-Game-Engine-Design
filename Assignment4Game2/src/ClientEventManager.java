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
		
	}

	public void registerEvent(EventHandler eh){
		eventList.get(EventType.COLLISION).add(eh);
		eventList.get(EventType.HID).add(eh);
		eventList.get(EventType.SPAWN).add(eh);
		eventList.get(EventType.DEATH).add(eh);
		
		
	}
	
	public static ClientEventManager getInstance(){
		if(em == null)
			em = new ClientEventManager();

		return em;
	}
	public synchronized void addEvent(Event e){

		eventQueue.add(e);
	}

	private synchronized Event getNextEventfromQueue() throws InterruptedException{

		if(!eventQueue.isEmpty())
		{Event e = eventQueue.poll();
		return e;}
		return null;
	}

	
	@Override
	public void run() {

		while(!Thread.interrupted()){
		
				try {

					Event e = getNextEventfromQueue();
					if(e!=null){
						eventList.get(e.type).forEach(v->v.handleEvent(e));
					}
						
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	}


}
