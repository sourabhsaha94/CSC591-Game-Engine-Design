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
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import processing.core.PApplet;

public class Section5Client extends PApplet {

	Player tempPlayer;
	Player player;
	LinkedList<StaticPlatform> sPlatformList = new LinkedList<>();
	LinkedList<MovingPlatform> mPlatformList = new LinkedList<>();
	ConcurrentHashMap<Integer, Player> playerList;

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
		
		Random random = new Random();

		int playerID = random.nextInt(100);

		player = new Player(playerID); // create a new player
		player.R = new Rectangle(50,50,50,50);
		player.setPlayerColor(random.nextInt(255), random.nextInt(255), random.nextInt(255));
		
		playerList.put(playerID, player);	//put the player in the list of players

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

		Section5ClientIn recieve = new Section5ClientIn(playerID, in, playerList, sPlatformList, mPlatformList); // start
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

		while (list.hasNext()) {
			tempPlayer = list.next();
			fill(tempPlayer.colorComponent.getR(), tempPlayer.colorComponent.getG(), tempPlayer.colorComponent.getB());
			rect(tempPlayer.R.x, tempPlayer.R.y, tempPlayer.R.width, tempPlayer.R.height);

		}
		
		System.out.println(playerList.size());
		/*for(StaticPlatform t:sPlatformList){		//loop to display
			fill(t.colorComponent.getR(),t.colorComponent.getG(),t.colorComponent.getB());
			rect(t.R.x,t.R.y,t.R.width,t.R.height);
		}
		
		for(MovingPlatform t:mPlatformList){
			fill(t.colorComponent.getR(),t.colorComponent.getG(),t.colorComponent.getB());
			rect(t.R.x+=t.motionComponent.getVx(),t.R.y+t.motionComponent.getVy(),t.R.width,t.R.height);
		}*/	
		
		
		player.R.x+=player.motionComponent.getVx();
		player.R.y+=player.motionComponent.getVy();

		distance_from_ground = (int) (displayy - player.R.getMaxY());

		if( !intersect && distance_from_ground>=2 && !player.jumpComponent.jump_flag)
		{
			player.motionComponent.setVy(2);
			player.motionComponent.setVx(0);;
		}
		if(distance_from_ground==2){
			player.motionComponent.setVy(0);
		}

		if(player.R.x<=2||player.R.getMaxX()>=displayx-2){	//dont cross the edges of the screen
			player.motionComponent.setVx(0);;
			player.jumpComponent.jump_flag=false;
		}
		if(player.R.y<=2||player.R.getMaxY()>=displayy-2){	//dont cross the edges of the screen
			player.motionComponent.setVy(0);;
			player.jumpComponent.jump_flag=false;
		}

		/*for(Platform t:platformList){
			if(player.R.intersects(t.R)){
				if(player.motionComponent.getVy()>0){	//coming down
					direction = 4;
				}
				else if(player.motionComponent.getVy()<0){	//going up
					direction = 2;
				}
				else if(player.motionComponent.getVy()<0 && player.motionComponent.getVx()>0){	//up right
					direction = 10;
				}
				else if(player.motionComponent.getVy()>0 && player.motionComponent.getVx()>0){	//down right
					direction = 12;
				}
				else if(player.motionComponent.getVy()<0 && player.motionComponent.getVx()<0){	//up left
					direction = 3;
				}
				else if(player.motionComponent.getVy()>0 && player.motionComponent.getVx()<0){	//down left
					direction = 5;
				}
				intersect = true;
				break;
			}
			intersect=false;
		}*/

		if(direction==2){	//dont go up
			player.motionComponent.setVy(2);
			player.motionComponent.setVx(-5);
			player.jumpComponent.jump_flag=false;
			direction=0;
		}
		if(direction==4){	//stop downward motion while coming down
			player.motionComponent.setVy(0);
			player.jumpComponent.jump_flag=false;
			direction=0;
		}

		if(direction==12){	//stop motion on collision
			player.motionComponent.setVy(0);
			player.motionComponent.setVx(0);	
			player.jumpComponent.jump_flag=false;
			direction=0;
		}

		if(direction==5){	//stop motion on collision
			player.motionComponent.setVy(0);
			player.motionComponent.setVx(0);	
			player.jumpComponent.jump_flag=false;
			direction=0;
		}

		if(direction==10){	//rebound on side hit
			player.motionComponent.setVy(2);
			player.motionComponent.setVx(-5);
			player.jumpComponent.jump_flag=false;
			direction=0;
		}

		if(direction==3){	//rebound on side hit
			player.motionComponent.setVy(2);
			player.motionComponent.setVx(5);
			player.jumpComponent.jump_flag=false;
			direction=0;
		}

		if(player.jumpComponent.jump_flag){	//main jump logic

			player.jumpComponent.jump(frameCount);
		}

	}

	public static void main(String argv[]) {

		// Section5Client c = new Section5Client();
		PApplet.main("assignment2.Section5Client");
	}

	public void keyPressed(){
		player.hidComponent.keyPressed();
	}
	
	public void keyReleased() {
		player.hidComponent.keyReleased();	
	}
}
