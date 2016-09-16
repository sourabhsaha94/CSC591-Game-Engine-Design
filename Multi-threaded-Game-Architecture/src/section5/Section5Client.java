/*
 * This class is responsible for instantiating a client
 *  and its input and output streams*/

package section5;


import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.Vector;

import assignment1.Thing;


public class Section5Client implements Runnable{

	ObjectInputStream in;
	ObjectOutputStream out;
	String name;
	
	Vector<Thing> thingList = new Vector<Thing>();
	
	Socket socket = null;
	
	Section5Client(Socket socket){
		this.socket = socket;
	}
	
	public static void main(String argv[]) throws Exception {
	
		Socket socket = new Socket("localhost",9000);
		
		Section5Client sc = new Section5Client(socket);
		
		sc.name = argv[0];
		Thread t_sc = new Thread(sc);
		t_sc.start();
		while(t_sc.isAlive());
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			
			Thing t = new Thing(name);
			thingList.add(t);
			
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("connected to server..");
		
		Section5ClientOut sender = new Section5ClientOut(this,out);
		Thread t_sender = new Thread(sender);
		t_sender.setDaemon(true);
		t_sender.start();
		
		Section5ClientIn recieve = new Section5ClientIn(this,in);
		Thread t_recieve = new Thread(recieve);
		t_recieve.setDaemon(true);
		t_recieve.start();
		
		while(true){
			
		}
	}

}
