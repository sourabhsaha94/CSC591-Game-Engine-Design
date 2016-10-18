package section4method2;




import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import processing.core.PApplet;

public class Client extends PApplet {

	Player tempPlayer;
	Player player;
	CopyOnWriteArrayList<StaticPlatform> sPlatformList = new CopyOnWriteArrayList<>();	//clients copy of platforms and players
	CopyOnWriteArrayList<MovingPlatform> mPlatformList = new CopyOnWriteArrayList<>();
	ConcurrentHashMap<Integer, Player> playerList;
	
	long startTime=0,endTime=0;

	SpawnPoint sp = new SpawnPoint(1);
	
	int distance_from_ground;
	int direction=1; //Left 1 Up 2 Down 3 Right 4
	int displayx=800;
	int displayy=800;
	
	boolean intersect=false;
	
	public void settings() {
		size(800, 800);
	}

	public void setup() {

		playerList = new ConcurrentHashMap<>();	//init player list

		ObjectInputStream in = null;
		ObjectOutputStream out = null;

		Socket socket = null;
		try {
			socket = new Socket("localhost", 9000);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		/***********************************************world init*************************************************************/
		Random random = new Random();

		int playerID = random.nextInt(100);

		player = new Player(playerID); // create a new player
		player.R = new Rectangle(50,400,50,50);
		player.setPlayerColor(random.nextInt(255), random.nextInt(255), random.nextInt(255));

		
		playerList.put(playerID, player);	//put the player in the list of players
		
		/*******************************************************************************************************************/
		
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(playerID + " connected to server.."); // print unique
		// client
		// identifier

		ClientOut sender = new ClientOut(playerID, out, playerList, this); // start
		// sender
		// thread
		Thread t_sender = new Thread(sender);
		t_sender.setDaemon(true);
		t_sender.start();

		ClientIn recieve = new ClientIn(playerID, in, playerList, this); // start
		// receiver
		// thread
		
		Thread t_recieve = new Thread(recieve);
		t_recieve.setDaemon(true);
		t_recieve.start();

		distance_from_ground = 200;	//800-600
		

	}

	public void draw() {
		clear();
		if(sPlatformList.size()<100 || mPlatformList.size()<100)
		{
			//System.out.println(mPlatformList.size());
			//System.out.println(sPlatformList.size());			
		}
		
	}

	public static void main(String argv[]) {
		PApplet.main("section4method2.Client");
	}
	
}
