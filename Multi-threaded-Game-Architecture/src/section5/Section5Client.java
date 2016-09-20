/*
 * This class is responsible for instantiating a client
 *  and its input and output streams*/

package section5;


import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Section5Client {

	public static void main(String argv[]) throws Exception {
	
		ObjectInputStream in =null;
		ObjectOutputStream out = null;
	
		Socket socket = new Socket("localhost",9000);
		
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		
		System.out.println(socket.getLocalSocketAddress()+" connected to server..");	//print unique client identifier
		
		Section5ClientOut sender = new Section5ClientOut(out);	//start sender thread
		Thread t_sender = new Thread(sender);
		t_sender.setDaemon(true);
		t_sender.start();
		
		Section5ClientIn recieve = new Section5ClientIn(in);	//start receiver thread
		Thread t_recieve = new Thread(recieve);
		t_recieve.setDaemon(true);
		t_recieve.start();
		
		while(true);
	}

}
