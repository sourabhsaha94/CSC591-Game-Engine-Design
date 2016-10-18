package section4method2;



import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

public class ServerOut implements Runnable {

	private Vector messageQueue = new Vector();

	private ClientListener clientListener;

	private ClientInfo c;
	
	long count=0;

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
				
				if(clientListener.mpList.size()<500 || clientListener.mpList.size()<500)
				{
					clientListener.mpList.add(new MovingPlatform(2000));
					clientListener.spList.add(new StaticPlatform(2000));
					//System.out.println(clientListener.mpList.size());
					//System.out.println(clientListener.spList.size());
				}
				Message message = getNextMessageFromQueue();
				message.sp = clientListener.spList.get(clientListener.spList.size()-1);
				message.mp = clientListener.mpList.get(clientListener.mpList.size()-1);
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
