package section123;
/*This class handles outgoing communication to client*/


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

public class ServerOut implements Runnable {

	private Vector messageQueue = new Vector();

	private ClientListener clientListener;

	private ClientInfo c;

	private ObjectOutputStream out;

	public ServerOut(ClientInfo c, ClientListener clientListener)throws IOException{

		this.c = c;
		this.clientListener = clientListener;
		Socket socket = c.socket;
		this.out = new ObjectOutputStream(socket.getOutputStream());

	}

	public synchronized void sendMessage(Message message){

		messageQueue.add(message);
		notify();

	}

	private synchronized Message getNextMessageFromQueue() throws InterruptedException{

		while(messageQueue.size() == 0)
			wait();

		Message message = (Message) messageQueue.get(0);
		messageQueue.removeElementAt(0);
		return message;

	}

	public void sendMessageToClient(Message message) throws IOException{

		out.writeObject(message);
		out.reset();

	}

	public void run(){

		try {

			while(!Thread.interrupted()) {
				
				Message message = getNextMessageFromQueue();
				sendMessageToClient(message);
			
			}

		} catch (Exception e) {

			// Commuication problem

		}

		// Communication is broken. Interrupt both listener and sender threads

		Thread.currentThread().interrupt();
		clientListener.deleteClient(c);

	}
}
