/*This class handles outgoing communication to client*/
package assignment2;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class Section5ServerOut implements Runnable {

	private Vector messageQueue = new Vector();

	private Section5ClientListener clientListener;

	private ClientInfo c;

	private ObjectOutputStream out;

	public Section5ServerOut(ClientInfo c, Section5ClientListener clientListener)throws IOException{

		this.c = c;
		this.clientListener = clientListener;
		Socket socket = c.socket;
		this.out = new ObjectOutputStream(socket.getOutputStream());

	}

	public synchronized void sendMessage(Integer message){

		messageQueue.add(message);
		notify();

	}

	private synchronized Integer getNextMessageFromQueue() throws InterruptedException{

		while(messageQueue.size() == 0)
			wait();

		Integer message = (Integer) messageQueue.get(0);
		messageQueue.removeElementAt(0);
		return message;

	}

	private void sendMessageToClient(Integer message) throws IOException{

		out.writeObject(message);

	}

	public void run(){

		try {

			while(!Thread.interrupted()) {
				Integer message = getNextMessageFromQueue();
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
