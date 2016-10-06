/*
 * This class is responsible for instantiating a client
 *  and its input and output streams*/

package assignment2;


import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import processing.core.PApplet;




public class Section5Client extends PApplet{

	Thing player,tempPlayer;
	ConcurrentHashMap<String,Thing> playerList;
	
	public void settings(){
		size(800, 800);
	}
	
	public void setup(){
		
		playerList = new ConcurrentHashMap<>();
		
		ObjectInputStream in =null;
		ObjectOutputStream out = null;
	
		Socket socket = null;
		try {
			socket = new Socket("localhost",9000);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Random random = new Random();
		
		player = new Thing(socket.getLocalSocketAddress().toString(),new Rectangle(random.nextInt(500),random.nextInt(500),50,50),0,0,255,255,0);
		playerList.put(socket.getLocalSocketAddress().toString(), player);
		
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println(socket.getLocalSocketAddress()+" connected to server..");	//print unique client identifier
		
		Section5ClientOut sender = new Section5ClientOut(socket,out,playerList);	//start sender thread
		Thread t_sender = new Thread(sender);
		t_sender.setDaemon(true);
		t_sender.start();
		
		Section5ClientIn recieve = new Section5ClientIn(in,playerList);	//start receiver thread
		Thread t_recieve = new Thread(recieve);
		t_recieve.setDaemon(true);
		t_recieve.start();

	}
	
	public void draw(){
		clear();
		
		Collection<Thing> t = playerList.values();
		
		
		Iterator<Thing> list = t.iterator();
		
		while(list.hasNext()){
			fill(255,0,0);
			tempPlayer = list.next();
			rect(tempPlayer.R.x,tempPlayer.R.y,tempPlayer.R.width,tempPlayer.R.height);
		}
		
		System.out.println("Current size of playerList "+playerList.size());
	}
	
	public static void main(String argv[]){
	
		//Section5Client c = new Section5Client();
		PApplet.main("assignment2.Section5Client");		
	}

}
