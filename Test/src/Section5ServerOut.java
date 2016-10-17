


import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

public class Section5ServerOut implements Runnable {

	private Vector messageQueue = new Vector();

	private Section5ClientListener clientListener;

	private ClientInfo c;
	
	long count=0;

	private ObjectOutputStream out;

	public Section5ServerOut(ClientInfo c, Section5ClientListener clientListener)throws IOException{

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
				message.mp.addAll(clientListener.mpList);
				System.out.println(clientListener.mpList.get(0).R.x);
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
