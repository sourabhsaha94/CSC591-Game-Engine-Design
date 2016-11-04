/*This class is responsible for sending message to the server
 * */



import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientOut implements Runnable {

	private ObjectOutputStream out;
	CopyOnWriteArrayList<Player> pList;

	Vector<Message> eventQueue = new Vector<>();

	public ClientOut(ObjectOutputStream out, CopyOnWriteArrayList<Player> pList) {

		this.out = out;
		this.pList = pList;

	}

	public synchronized void sendMessage(Message m){
		eventQueue.add(m);

	}

	private synchronized Message getNextMessagefromQueue() throws InterruptedException{

		if(!eventQueue.isEmpty())
		{Message m = (Message) eventQueue.get(0);
		eventQueue.removeElementAt(0);
		return m;}
		return null;
	}

	public void sendMessageToServer(Message message) throws IOException{

		out.writeObject(message);
		out.reset();

	}
	public void run() {

		try {
			Message message=new Message(9000);

			boolean first_run = true;

			Message player_info_message=new Message(9090,(Player)null,0,0,0);

			while (!Thread.interrupted()) {
			
					if (first_run) {
						first_run = false;
						player_info_message.messagePriority = MessagePriority.HIGH;
						out.writeObject(player_info_message);
						out.reset();
					} else {
						Message m = getNextMessagefromQueue();
						if(m!=null){
							sendMessageToServer(m);
						}
						else
							sendMessageToServer(message);
					}
			
			}

		} catch (IOException ioe) {

			// Communication is broken

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
