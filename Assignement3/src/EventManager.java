import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

public class EventManager implements Runnable{

	int id;

	Comparator<Event> comparator = new EventComparator();
	PriorityBlockingQueue<Event> eventQueue = new PriorityBlockingQueue<>(100, comparator);

	//list of events recognized
	//every event will have list of subscribers
	//on event raise, list is iterated

	private static EventManager em;

	private EventManager(){
		this.id=1234;
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

	private void handleEvent(Event e) {
		switch(e.priority){
		case COLLISION:
			switch(e.id){
			case 1:

				if(e.p.motionComponent.getVy()>0){	//coming down
					e.p.jumpComponent.jump_flag=false;
					e.p.collisionComponent.direction = 2;
				}
				else if(e.p.motionComponent.getVy()<0){	//going up
					//e.p.Spawn();
					//e.p.jumpComponent.jump_flag=false;
					e.p.collisionComponent.direction=0;
				}

				if(e.p.R.y>e.s.R.y && e.p.motionComponent.getVy()<0){	//going up.. hit on side
					//e.p.Spawn();
					//e.p.jumpComponent.jump_flag=false;
					e.p.collisionComponent.direction=0;
				}
				if((e.p.R.y<e.s.R.getMaxY() && e.p.R.x<e.s.R.x) && e.p.motionComponent.getVy()>0){	//going down.. hit on side
					//e.p.Spawn();
					//e.p.jumpComponent.jump_flag=false;
					e.p.collisionComponent.direction=0;
				}

				break;
			case 2:

				if(e.p.motionComponent.getVy()>0){	//coming down
					e.p.collisionComponent.direction = 2;
				}
				else if(e.p.motionComponent.getVy()<0){	//going up
					//e.p.Spawn();
					//e.p.jumpComponent.jump_flag=false;
					e.p.collisionComponent.direction=0;
				}

				if(e.p.R.y>e.m.R.y && e.p.motionComponent.getVy()<0){	//going up.. hit on side
					//e.p.Spawn();
					//e.p.jumpComponent.jump_flag=false;
					e.p.collisionComponent.direction=0;
				}
				else if((e.p.R.y<e.m.R.getMaxY() && e.p.R.x<e.m.R.x) && e.p.motionComponent.getVy()>0){	//going down.. hit on side
					//e.p.Spawn();
					//e.p.jumpComponent.jump_flag=false;
					e.p.collisionComponent.direction=0;
				}

				break;
			}
			break;
		case DEATH:
			e.p.jumpComponent.jump_flag=false;
			EventManager.getInstance().addEvent(new SpawnEvent(System.nanoTime(), e.p));
			break;
		case HID:

			switch (e.x) {
			case 0:
				e.p.motionComponent.vx=0;
				e.p.move();
				break;
			case 1:
				e.p.motionComponent.vx=2;
				e.p.move();
				e.p.motionComponent.vx=0;
				e.p.move();
				break;
			case -1:

				e.p.motionComponent.vx=-2;
				e.p.move();
				e.p.motionComponent.vx=0;
				e.p.move();
				break;
			case 101:
				e.p.jumpComponent.jump_flag=true;
				e.p.move();
				break;
			case 666:
				Timeline.getInstance().tic_size*=50000;
				break;
			case 777:
				Timeline.getInstance().tic_size/=50000;
				break;
			default:
				break;
			}
			break;
		case SPAWN:
			e.p.Spawn();
			break;
		default:
			break;

		}
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
					if(e!=null)
						handleEvent(e);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	}


}
