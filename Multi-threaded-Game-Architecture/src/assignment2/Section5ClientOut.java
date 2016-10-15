/*This class is responsible for sending message to the server
 * */

package assignment2;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentHashMap;

public class Section5ClientOut implements Runnable {

	private ObjectOutputStream out;
	ConcurrentHashMap<Integer, Player> pList;
	int playerID=0;;

	public Section5ClientOut(int id, ObjectOutputStream out, ConcurrentHashMap<Integer, Player> pList) {

		this.playerID = id;
		this.out = out;
		this.pList = pList;

	}

	public void run() {

		try {
			Message message;
			boolean first_run=true;
			
			
			while (!Thread.interrupted()) {

				if(first_run){
					message = new Message(playerID,999,0,0,0,0);
					first_run=false;
				}
				else{
				
					message = new Message(playerID,
						pList.get(playerID).R.x,
						pList.get(playerID).R.y,
						pList.get(playerID).colorComponent.getR(),
						pList.get(playerID).colorComponent.getG(),
						pList.get(playerID).colorComponent.getB());
				}
				out.writeObject(message);
				out.reset();
			}

		} catch (IOException ioe) {

			// Communication is broken

		}

	}
}
