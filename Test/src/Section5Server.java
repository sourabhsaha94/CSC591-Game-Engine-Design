




import java.awt.Rectangle;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;

import processing.core.PApplet;

public class Section5Server extends PApplet{

	Section5ClientListener scl;
	
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
		int id; 
		
		for(int i=0;i<2;i++)
		{
			id = r.nextInt(1000)+1500;
			StaticPlatform s1 = new StaticPlatform(id);
			s1.setPlatformColor(255, 0, 0);
			s1.R = new Rectangle(50,600,200,50);
			clientListener.spList.add(s1);
		}
		
		for(int i=0;i<2;i++){
			id = r.nextInt(1000)+1500;
			MovingPlatform m1 = new MovingPlatform(id);
			m1.setPlatformColor(0, 255, 0);
			m1.setPlatformVelocity(1, 0);
			m1.R = new Rectangle(150,500,50,50);
			clientListener.mpList.add(m1);
		}
		ClientWorker clientWorker = new ClientWorker(serverSocket,clientListener);	//manage client connections
		
		Thread clientWorkerThread = new Thread(clientWorker);
		clientWorkerThread.start();
		
		this.scl = clientListener;
		
	}
	
	public void draw(){
		for(MovingPlatform t: scl.mpList){		//maintain the position of the moving platform and send updated loc info to new clients
			t.R.x+=t.motionComponent.vx;
			t.R.y+=t.motionComponent.vy;
			t.motionComponent.update();
		}
	}
	
	public static void main(String args[]){
		PApplet.main("Section5Server");
	}
	
}
