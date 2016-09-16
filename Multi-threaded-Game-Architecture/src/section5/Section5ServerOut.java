/*This class handles outgoing communication to client*/
package section5;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

import assignment1.Thing;

public class Section5ServerOut implements Runnable {

	private Vector<Thing> messageQueue = new Vector<Thing>();

	private Section5ClientListener clientListener;

	private ClientInfo c;

	private ObjectOutputStream out;

	public Section5ServerOut(ClientInfo c, Section5ClientListener clientListener)throws IOException{

		this.c = c;

		this.clientListener = clientListener;

		Socket socket = c.socket;

		this.out = new ObjectOutputStream(socket.getOutputStream());

	}

	public synchronized void sendMessage(Thing thing){

		messageQueue.add(thing);

		notify();

	}

	private synchronized Thing getNextMessageFromQueue() throws InterruptedException{

		while (messageQueue.size() == 0)
			wait();

		Thing thing = (Thing) messageQueue.get(0);

		messageQueue.removeElementAt(0);

		return thing;

	}

	private void sendMessageToClient(Thing thing) throws IOException{

		out.writeObject(thing);

	}

	public void run(){

		try {

			while (!Thread.interrupted()) {

				Thing thing = getNextMessageFromQueue();

				sendMessageToClient(thing);

			}

		} catch (Exception e) {

			// Commuication problem

		}

		// Communication is broken. Interrupt both listener and sender threads

		Thread.currentThread().interrupt();

		clientListener.deleteClient(c);

	}
}
