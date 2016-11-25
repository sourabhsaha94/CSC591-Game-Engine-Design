/*This class is responsible for instantiating the main server and also
 * the listener and clientWorker threads*/

import java.awt.Rectangle;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Random;

import processing.core.PApplet;

public class Server extends PApplet {

	ClientListener scl;
	int distance_from_ground;

	public void settings() {
		size(10, 10);
	}

	public void setup() {

		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(9000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Server started at 9000...");


		Thread eM = new Thread(EventManager.getInstance());
		eM.start();

		ClientListener clientListener = new ClientListener(); 

		Thread cL = new Thread(clientListener);
		cL.start();

		Thread timeline = new Thread(Timeline.getInstance());
		timeline.start();

		Random r = new Random();
		int id;
		/************************************************ world_init *****************************************/
		id = r.nextInt(1000) + 1500; // bottom
		StaticPlatform s1 = new StaticPlatform(id);
		s1.setPlatformColor(255, 0, 0);
		s1.R = new Rectangle(50, 550, 300, 20);

		clientListener.spList.add(s1);

		id = r.nextInt(1000) + 1500; // top
		StaticPlatform s2 = new StaticPlatform(id);
		s2.setPlatformColor(0, 0, 255);
		s2.R = new Rectangle(400, 350, 300, 20);

		clientListener.spList.add(s2);

		id = 0; // mid
		MovingPlatform m1 = new MovingPlatform(id);
		m1.setPlatformColor(0, 255, 0);
		m1.setPlatformVelocity(1, 0);
		m1.R = new Rectangle(150, 450, 300, 20);

		clientListener.mpList.add(m1);

		id = 1; // bottom
		MovingPlatform m2 = new MovingPlatform(id);
		m2.setPlatformColor(0, 255, 0);
		m2.setPlatformVelocity(1, 0);
		m2.R = new Rectangle(150, 700, 300, 20);

		clientListener.mpList.add(m2);

		DeathZone downDZ = new DeathZone(1);
		DeathZone leftDZ = new DeathZone(1);
		DeathZone rightDZ = new DeathZone(1);
		downDZ.setDeathZoneXY(0, 800 - 10);
		leftDZ.setDeathZoneXY(0, 0);
		leftDZ.setDeathZoneWH(10, 800);
		rightDZ.setDeathZoneXY(800 - 10, 0);
		rightDZ.setDeathZoneWH(10, 800);

		clientListener.dzList.add(leftDZ);
		clientListener.dzList.add(downDZ);
		clientListener.dzList.add(rightDZ);

		ClientWorker clientWorker = new ClientWorker(serverSocket, clientListener); 

		Thread clientWorkerThread = new Thread(clientWorker);
		clientWorkerThread.start();

		this.scl = clientListener;

	}

	public void draw() {

	}

	public static void main(String args[]) {
		PApplet.main("Server");
	}

}
