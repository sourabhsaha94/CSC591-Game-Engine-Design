/*This class is responsible for instantiating the main server and also
 * the listener and clientWorker threads*/

package assignment2;


import java.awt.Rectangle;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;

import processing.core.PApplet;

public class Section5Server extends PApplet{

	public void settings(){
		size(10,10);
	}
	
	public void setup(){
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket(9000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server started at 9000...");
		
		Section5ClientListener clientListener = new Section5ClientListener();	//start client listener to manage communication b/w clients
		
		Thread cL = new Thread(clientListener);
		cL.start();
		
		Random r = new Random();
		
		int id = r.nextInt(1000)+1500;
		clientListener.allPlatformInfo.put(id, new Platform(id,new Rectangle(50,600,200,50),0,0,0,0,255));
		id = r.nextInt(1000)+1500;
		clientListener.allPlatformInfo.put(id, new Platform(id,new Rectangle(150,500,350,50),0,0,0,255,0));
		id = r.nextInt(1000)+1500;
		clientListener.allPlatformInfo.put(id, new Platform(id,new Rectangle(500,400,150,50),0,0,255,0,0));
		
		ClientWorker clientWorker = new ClientWorker(serverSocket,clientListener);	//manage client connections
		
		Thread clientWorkerThread = new Thread(clientWorker);
		clientWorkerThread.start();
	}
	
	public void draw(){
		//main game loop
	}
	
	public static void main(String args[]){
		PApplet.main("assignment2.Section5Server");
	}

}
