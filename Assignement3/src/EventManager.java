import java.util.Vector;

public class EventManager implements Runnable{
	private Vector eventQueue = new Vector();
	//list of events recognized
	//every event will have list of subscribers
	//on event raise, list is iterated
	
	ClientListener clientListener = new ClientListener();

	public synchronized void addEvent(Message m){
		
		//Socket socket = c.socket;
		//message = socket.getRemoteSocketAddress()+" : "+message;
		eventQueue.add(m);
		notify();
	}
	
	private synchronized Message getNextEventfromQueue() throws InterruptedException{
		
		while(eventQueue.size()==0)
			wait();
		
		Message m = (Message) eventQueue.get(0);
		eventQueue.removeElementAt(0);
		return m;
	}
	
	
	private synchronized void sendEventtoAllClients(Message m){
		
		for(int i=0;i<clientListener.clients.size();i++){
			ClientInfo c = (ClientInfo)clientListener.clients.get(i);
			if(c.id!=m.id)
				c.out.sendMessage(m);
		}
	}
	
	@Override
	public void run() {

		while(!Thread.interrupted()){

			try {

				Message m = getNextEventfromQueue();
				sendEventtoAllClients(m);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
}
