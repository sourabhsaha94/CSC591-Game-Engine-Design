package method1;





import java.awt.Rectangle;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;

import processing.core.PApplet;

public class Server extends PApplet{

	ClientListener scl;
	
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
		
		ClientListener clientListener = new ClientListener();	//start client listener to manage communication b/w clients
		
		Thread cL = new Thread(clientListener);
		cL.start();
		
		Random r = new Random();
		int id; 
		
		id = r.nextInt(1000)+1500;
		StaticPlatform s1 = new StaticPlatform(id);
		s1.setPlatformColor(255, 0, 0);
		s1.R = new Rectangle(50,600,200,50);
		clientListener.spList.add(s1);
	
		id = r.nextInt(1000)+1500;
		MovingPlatform m1 = new MovingPlatform(id);
		m1.setPlatformColor(0, 255, 0);
		m1.setPlatformVelocity(1, 0);
		m1.R = new Rectangle(150,500,50,50);
		clientListener.mpList.add(m1);
	
		ClientWorker clientWorker = new ClientWorker(serverSocket,clientListener);	//manage client connections
		
		Thread clientWorkerThread = new Thread(clientWorker);
		clientWorkerThread.start();
		
		this.scl = clientListener;
		
	}
	
	public void draw(){
	}
	
	public static void main(String args[]){
		PApplet.main("method1.Server");
	}
	
}
