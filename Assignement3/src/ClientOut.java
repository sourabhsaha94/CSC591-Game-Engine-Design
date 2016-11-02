/*This class is responsible for sending message to the server
 * */



import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientOut implements Runnable {

	private ObjectOutputStream out;
	CopyOnWriteArrayList<Player> pList;
	
	public ClientOut(ObjectOutputStream out, CopyOnWriteArrayList<Player> pList) {
		
		this.out = out;
		this.pList = pList;

	}

	public void run() {

		try {
			Message message=new Message(9000);
			
			boolean first_run = true;

			Message player_info_message=new Message(9090,(Player)null,0,0,0);

			while (!Thread.interrupted()) {

				if (first_run) {
					first_run = false;
					out.writeObject(player_info_message);
					out.reset();
				} else {
					out.writeObject(message);
					out.reset();
				}

			}

		} catch (IOException ioe) {

			// Communication is broken

		}

	}
}
