/*This class is responsible for instantiating the main server and also
 * the listener and clientWorker threads*/

package assignment2;


import java.io.IOException;

import java.net.*;

import assignment1.Thing;
import java.util.concurrent.*;
import java.util.*;

public class Section5Server{

	
	
	public static void main(String args[]) throws IOException{
		
		ServerSocket serverSocket = null;
		
		serverSocket = new ServerSocket(9000);
		System.out.println("Server started at 9000...");
		
		Section5ClientListener clientListener = new Section5ClientListener();	//start client listener to manage communication b/w clients
		
		Thread cL = new Thread(clientListener);
		cL.start();
		
		ClientWorker clientWorker = new ClientWorker(serverSocket,clientListener);	//manage client connections
		
		Thread clientWorkerThread = new Thread(clientWorker);
		clientWorkerThread.start();
	
		
		
		while(true);
		
	}

}
