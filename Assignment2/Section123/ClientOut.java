/*This class is responsible for sending message to the server
 * */



import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.concurrent.ConcurrentHashMap;

public class ClientOut implements Runnable {

	private ObjectOutputStream out;
	ConcurrentHashMap<Integer, Player> pList;
	int playerID = 0;;

	public ClientOut(int id, ObjectOutputStream out, ConcurrentHashMap<Integer, Player> pList) {

		this.playerID = id;
		this.out = out;
		this.pList = pList;

	}

	public void run() {

		try {
			Message message = new Message(playerID, pList.get(playerID).R.x, pList.get(playerID).R.y,
					pList.get(playerID).colorComponent.getR(), pList.get(playerID).colorComponent.getG(),
					pList.get(playerID).colorComponent.getB());
			
			boolean first_run = true;

			Message player_info_message;

			while (!Thread.interrupted()) {

				if (first_run) {
					player_info_message = new Message(playerID, 999, 0, 0, 0, 0);
					first_run = false;
					out.writeObject(player_info_message);
					out.reset();
				} else {
					message.putValues(playerID, pList.get(playerID).R.x, pList.get(playerID).R.y,
							pList.get(playerID).colorComponent.getR(), pList.get(playerID).colorComponent.getG(),
							pList.get(playerID).colorComponent.getB());
					out.writeObject(message);
					out.reset();
				}

			}

		} catch (IOException ioe) {

			// Communication is broken

		}

	}
}
