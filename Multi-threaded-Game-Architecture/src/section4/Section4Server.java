package section4;


import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

import assignment1.Thing;
import java.util.concurrent.*;
import java.util.*;
public class Section4Server{

	
	
	public static void main(String args[]) throws IOException{
		
		ConcurrentHashMap<Integer,ClientWorker> clientList = new ConcurrentHashMap<Integer,ClientWorker>();
		
		Scanner in = new Scanner(System.in);
		String s ="";
		
		ServerSocket server = new ServerSocket(9000);
		boolean stopped = true;
		
		Section4ClientListener clisten = new Section4ClientListener(server,true);
		
		Thread cl = new Thread(clisten);
		cl.start();
		
		clientList = clisten.clientList;
		
		while(!s.equals("exit")){
			
			s = in.nextLine();
			if(s.equalsIgnoreCase("get list size")){
				System.out.println(clientList.size());
			}
			else if(s.contains("get client at")){	//type get client at <index>
				System.out.println(s.split(" ")[3]);
				if(clientList.containsKey(Integer.parseInt(s.split(" ")[3]))){
					System.out.println(clientList.get(Integer.parseInt(s.split(" ")[3])).id);
				}
			}
			else{
				System.out.println("next input or exit");
			}
		}
	}

}
