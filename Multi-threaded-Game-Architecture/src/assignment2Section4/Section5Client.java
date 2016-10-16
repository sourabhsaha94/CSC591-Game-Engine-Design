/*
 * This class is responsible for instantiating a client
 *  and its input and output streams*/

package assignment2Section4;

import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import processing.core.PApplet;

public class Section5Client extends PApplet {

	Player tempPlayer;
	Player player;
	LinkedList<StaticPlatform> sPlatformList = new LinkedList<>();	//clients copy of platforms and players
	LinkedList<MovingPlatform> mPlatformList = new LinkedList<>();
	ConcurrentHashMap<Integer, Player> playerList;

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
		
		
		Random r = new Random();
		
		player.collisionComponent.addPlatforms(sPlatformList, mPlatformList);
		
		sp.setSpawnPoint(r.nextInt(100)+50,r.nextInt(100)+50);
		
		player.addSpawnPoint(sp);
		player.Spawn();
		
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

		Section5ClientOut sender = new Section5ClientOut(playerID, out, playerList); // start
		// sender
		// thread
		Thread t_sender = new Thread(sender);
		t_sender.setDaemon(true);
		t_sender.start();

		Section5ClientIn recieve = new Section5ClientIn(playerID, in, playerList, this); // start
		// receiver
		// thread
		
		Thread t_recieve = new Thread(recieve);
		t_recieve.setDaemon(true);
		t_recieve.start();

		distance_from_ground = 200;	//800-600
		

	}

	public void draw() {
		clear();

		Collection<Player> pList = playerList.values();
		Iterator<Player> list = pList.iterator();

		while (list.hasNext()) {		//loops to display
			tempPlayer = list.next();
			fill(tempPlayer.colorComponent.getR(), tempPlayer.colorComponent.getG(), tempPlayer.colorComponent.getB());
			rect(tempPlayer.R.x, tempPlayer.R.y, tempPlayer.R.width, tempPlayer.R.height);

		}
		
		
		for(StaticPlatform t:sPlatformList){		
			fill(t.colorComponent.getR(),t.colorComponent.getG(),t.colorComponent.getB());
			rect(t.R.x,t.R.y,t.R.width,t.R.height);
		}
		
		for(MovingPlatform t:mPlatformList){
			fill(t.colorComponent.getR(),t.colorComponent.getG(),t.colorComponent.getB());
			rect(t.R.x+=t.motionComponent.vx,t.R.y+t.motionComponent.vy,t.R.width,t.R.height);
			t.motionComponent.update();
		}	
		
		
		player.R.x+=player.motionComponent.vx;
		player.R.y+=player.motionComponent.vy;

		distance_from_ground = (int) (displayy - player.R.getMaxY());

		player.collisionComponent.update(distance_from_ground,displayx,displayy);
		
		
		if(player.jumpComponent.jump_flag){	

			player.jumpComponent.jump(frameCount);
		}

		
	}

	public static void main(String argv[]) {
		PApplet.main("assignment2.Section5Client");
	}
	
	public void keyPressed(){
		if (key == CODED) {
		
			if (keyCode == RIGHT) {	//move right
				player.motionComponent.vx=1;
				
			
			
			} else if (keyCode == LEFT) {	//move left
				player.motionComponent.vx=-1;
						
			}
			else if (keyCode == UP) {	//jump
				player.jumpComponent.jump_flag=true;
			}
			
			
			player.R.x+=player.motionComponent.vx;
		}
		else if(key == 'r' ||key =='R'){	//reset
			player.Spawn();
		}
	}
	
	public void keyReleased() {
		if (key == CODED) {
			if (keyCode == RIGHT) {
				player.motionComponent.vx=0;
				
				
			} else if (keyCode == LEFT) {
				player.motionComponent.vx=0;
			}
			player.R.x+=player.motionComponent.vx;
		}

	}
}