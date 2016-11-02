/*This class is responsible for instantiating the main server and also
 * the listener and clientWorker threads*/

package assignment2;


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
		
		int displayx=800,displayy=800; //dimensions of client screen
		
		Random r = new Random();
		int id; 
		
		id = r.nextInt(1000)+1500;
		StaticPlatform s1 = new StaticPlatform(id);
		s1.setPlatformColor(255, 0, 0);
		s1.R = new Rectangle(50,600,200,50);
		
		clientListener.spList.add(s1);
		
		id = r.nextInt(1000)+1500;
		StaticPlatform s2 = new StaticPlatform(id);
		s2.setPlatformColor(0, 0, 255);
		s2.R = new Rectangle(500,400,150,50);
		
		clientListener.spList.add(s2);
		
		id = r.nextInt(1000)+1500;
		MovingPlatform m1 = new MovingPlatform(id);
		m1.setPlatformColor(0, 255, 0);
		m1.setPlatformVelocity(1, 0);
		m1.R = new Rectangle(150,500,50,50);

		clientListener.mpList.add(m1);

		DeathZone downDZ = new DeathZone(1);
		DeathZone leftDZ = new DeathZone(1);
		DeathZone rightDZ = new DeathZone(1);
		downDZ.setDeathZoneXY(0, displayy-10);
		leftDZ.setDeathZoneXY(0, 0);
		leftDZ.setDeathZoneWH(10, displayy);
		rightDZ.setDeathZoneXY(displayx-10, 0);
		rightDZ.setDeathZoneWH(10, displayy);
		
		clientListener.dzList.add(leftDZ);
		clientListener.dzList.add(downDZ);
		clientListener.dzList.add(rightDZ);
		
		ClientWorker clientWorker = new ClientWorker(serverSocket,clientListener);	//manage client connections
		
		Thread clientWorkerThread = new Thread(clientWorker);
		clientWorkerThread.start();
		
		this.scl = clientListener;
		
	}
	
	public void draw(){
		for(MovingPlatform t: scl.mpList){		//maintain the position of the moving platform and send updated loc info to new clients
			rect(t.R.x+=t.motionComponent.vx,t.R.y+t.motionComponent.vy,t.R.width,t.R.height);
			t.motionComponent.update();
		}
	}
	
	public static void main(String args[]){
		PApplet.main("assignment2.Section5Server");
	}
	
}
