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
		
		
		generatePlatforms(clientListener);
	
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

	public void generatePlatforms(Section5ClientListener clientListener){
		
		Random r = new Random();
		int id; 
		
		id = r.nextInt(1000)+1500;
		StaticPlatform s1 = new StaticPlatform(id);
		s1.setPlatformColor(0, 0, 255);
		s1.R = new Rectangle(50,600,200,50);
		
		clientListener.sPlatformInfo.put(id, s1);
		
		id = r.nextInt(1000)+1500;
		StaticPlatform s2 = new StaticPlatform(id);
		s2.setPlatformColor(0, 0, 255);
		s2.R = new Rectangle(500,400,150,50);
		
		clientListener.sPlatformInfo.put(id, s2);
		
		id = r.nextInt(1000)+1500;
		MovingPlatform m1 = new MovingPlatform(id);
		m1.setPlatformColor(0, 0, 255);
		m1.setPlatformVelocity(3, 0);
		m1.R = new Rectangle(150,500,350,50);
		
		clientListener.mPlatformInfo.put(id, m1);
	}
	
}
