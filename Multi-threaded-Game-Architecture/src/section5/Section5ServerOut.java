package section5;

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

	private PrintWriter out;

	public Section5ServerOut(ClientInfo c, Section5ClientListener clientListener)throws IOException{

		this.c = c;

		this.clientListener = clientListener;

		Socket socket = c.socket;

		this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));

	}

	public synchronized void sendMessage(String message){

		messageQueue.add(message);

		notify();

	}

	private synchronized String getNextMessageFromQueue() throws InterruptedException{

		while (messageQueue.size() == 0)

			wait();

		String message = (String) messageQueue.get(0);

		messageQueue.removeElementAt(0);

		return message;

	}

	private void sendMessageToClient(String message){

		out.println(message);

		out.flush();

	}

	public void run(){

		try {

			while (!Thread.interrupted()) {

				String message = getNextMessageFromQueue();

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
