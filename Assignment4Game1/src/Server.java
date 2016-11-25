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

		id = 0; // mid
		Enemy m1 = new Enemy(id);
		m1.setPlatformColor(0, 255, 0);
		m1.setPlatformVelocity(1, 0);
		m1.R = new Rectangle(150, 450, 300, 20);

		clientListener.mpList.add(m1);

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
