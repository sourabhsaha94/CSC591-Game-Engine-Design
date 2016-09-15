package section4;


import java.net.*;

import assignment1.Thing;
import java.util.concurrent.*;
import java.util.*;
import java.io.*;

public class Section4Server {

	ConcurrentHashMap<Integer,Socket> clientList;
	int id=0;
	
	Section4Server(){
		this.clientList = new ConcurrentHashMap<Integer, Socket>();
	}
	
	public static void main(String args[]) throws IOException, ClassNotFoundException, InterruptedException{
		
		
		Scanner input = new Scanner(System.in);
		
		ServerSocket serverSocket = new ServerSocket(9000);
		
		int count=0;
		
		Section4Server s = new Section4Server();
		
		Section4ClientListener clisten = new Section4ClientListener(s,serverSocket,true);
		
		Thread cl = new Thread(clisten);
		
		cl.start();
		
		while(s.clientList.size()<1){
			//wait for clients to connect
		}
		
		System.out.println(s.clientList.size());
		
		ObjectOutputStream oos = new ObjectOutputStream(s.clientList.get(0).getOutputStream());
		ObjectInputStream ois = new ObjectInputStream(s.clientList.get(0).getInputStream());
		
		oos.writeObject(0);
		
		String str="";
		
		while(!str.equals("bye")){
			str = (String)ois.readObject();
			System.out.println(str);
		}
		
           
	}
}
