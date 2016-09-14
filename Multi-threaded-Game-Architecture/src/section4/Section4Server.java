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

	ConcurrentHashMap<Thread,Integer> clientList;
	
	
	public static void main(String args[]) throws IOException{
		
		
		ServerSocket server = new ServerSocket(9000);
		boolean stopped = true;
		
		
		Thread cl = new Thread(new Section4ClientListener(server,true));
		cl.start();
		while(stopped);
	}

}
