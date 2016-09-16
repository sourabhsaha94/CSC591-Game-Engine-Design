package section4;


import java.net.*;

import assignment1.Thing;
import java.util.concurrent.*;
import java.util.*;
import java.io.*;

public class Section4Server {

	ConcurrentHashMap<Integer,ClientWorker> clientList;
	int id=0;
	
	Section4Server(){
		this.clientList = new ConcurrentHashMap<Integer, ClientWorker>();
	}
	
	public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException{
		
		
		Scanner input = new Scanner(System.in);
		
		ServerSocket serverSocket = new ServerSocket(9000);
		
		int count=0;
		
		Section4Server s = new Section4Server();
		
		Section4ClientListener clisten = new Section4ClientListener(s,serverSocket,true);
		
		Thread cl = new Thread(clisten);
		
		cl.start();	//this thread handles client connection asynchronously
		
		while(true){
			//do whatever server wants to do apart from communication
		}
		
		
           
	}
}
