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

	Thing player, tempPlayer;
	LinkedList<Thing> ThingList;
	ConcurrentHashMap<Integer, Thing> playerList;

	int distance_from_ground;
	int direction=1; //Left 1 Up 2 Down 3 Right 4
	int displayx=800;
	int displayy=800;
	
	boolean jump_flag = false;
	int jump_start=0;
	int init_pos=0;
	
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

		int playerID = random.nextInt(1000);

		player = new Thing(playerID, new Rectangle(random.nextInt(500), random.nextInt(500), 100, 100), 0, 0,
				random.nextInt(255), random.nextInt(255), random.nextInt(255)); // create a new player

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

		Section5ClientIn recieve = new Section5ClientIn(playerID, in, playerList); // start
		// receiver
		// thread
		Thread t_recieve = new Thread(recieve);
		t_recieve.setDaemon(true);
		t_recieve.start();

		ThingList = new LinkedList<Thing>();
		ThingList.add(new Thing(1,new Rectangle(50,600,200,50),0,0,0,0,255));	//(width,height);width means height;height means width
		ThingList.add(new Thing(2,new Rectangle(150,500,350,50),0,0,0,255,0));
		ThingList.add(new Thing(3,new Rectangle(500,400,150,50),0,0,255,0,0));
		
		distance_from_ground = 200;	//800-600

	}

	public void draw() {
		clear();

		Collection<Thing> pList = playerList.values();
		Iterator<Thing> list = pList.iterator();

		while (list.hasNext()) {
			tempPlayer = list.next();
			fill(tempPlayer.r, tempPlayer.g, tempPlayer.b);
			rect(tempPlayer.R.x, tempPlayer.R.y, tempPlayer.R.width, tempPlayer.R.height);

		}
		for(Thing t:ThingList){		//loop to display
			fill(t.r,t.g,t.b);
			rect(t.R.x+=t.vx,t.R.y+=t.vy,t.R.width,t.R.height);
		}
		
		player.R.x+=player.vx;
		player.R.y+=player.vy;

		distance_from_ground = (int) (displayy - player.R.getMaxY());

		if( !intersect && distance_from_ground>=2 && !jump_flag)
		{
			player.vy=2;
			player.vx=0;
		}
		if(distance_from_ground==2){
			player.vy=0;
		}

		if(player.R.x<=2||player.R.getMaxX()>=displayx-2){	//dont cross the edges of the screen
			player.vx=0;
			jump_flag=false;
		}
		if(player.R.y<=2||player.R.getMaxY()>=displayy-2){	//dont cross the edges of the screen
			player.vy=0;
			jump_flag=false;
		}

		for(Thing t:ThingList){
			if(player.R.intersects(t.R)){
				if(player.vy>0){	//coming down
					direction = 4;
				}
				else if(player.vy<0){	//going up
					direction = 2;
				}
				else if(player.vy<0 && player.vx>0){	//up right
					direction = 10;
				}
				else if(player.vy>0 && player.vx>0){	//down right
					direction = 12;
				}
				else if(player.vy<0 && player.vx<0){	//up left
					direction = 3;
				}
				else if(player.vy>0 && player.vx<0){	//down left
					direction = 5;
				}
				intersect = true;
				break;
			}
			intersect=false;
		}

		System.out.println("vx:"+player.vx+" vy:"+player.vy+" direction:"+direction+" intersected:"+intersect);

		if(direction==2){	//dont go up
			player.vy=2;
			player.vx=0;
			jump_flag=false;
			direction=0;
		}
		if(direction==4){	//stop downward motion while coming down
			player.vy=0;
			jump_flag=false;
			direction=0;
		}

		if(direction==12){	//stop motion on collision
			player.vy=0;
			player.vx=0;	
			jump_flag=false;
			direction=0;
		}

		if(direction==5){	//stop motion on collision
			player.vy=0;
			player.vx=0;	
			jump_flag=false;
			direction=0;
		}

		if(direction==10){	//rebound on side hit
			player.vy=2;
			player.vx=5;
			jump_flag=false;
			direction=0;
		}

		if(direction==3){	//rebound on side hit
			player.vy=2;
			player.vx=0;
			jump_flag=false;
			direction=0;
		}

		if(jump_flag){	//main jump logic

			if(player.vy==0){
				player.vy=-2;
				init_pos=player.R.y;
				jump_start=frameCount;
			}
			else if(frameCount-jump_start == 100){
				player.vy=2;
			}
			else if(frameCount-jump_start==200){
				player.vy=0;
				jump_flag=false;
			}
		}

	}

	public static void main(String argv[]) {

		// Section5Client c = new Section5Client();
		PApplet.main("assignment2.Section5Client");
	}

	public void keyPressed(){
		if (key == CODED) {
		
			if (keyCode == RIGHT) {	//move right
				if(intersect || jump_flag)
					player.vx=1;
				
			} else if (keyCode == LEFT) {	//move left
				if(intersect || jump_flag)
					player.vx=-1;
				
			}
			else if (keyCode == UP) {	//jump
				jump_flag=true;
			}
			
			player.R.x+=player.vx;
		}
		else if(key == 'r' ||key =='R'){	//reset
			player.R.x=50;
			player.R.y=550;
			player.vx=0;
			player.vy=0;
		}
	}
	
	public void keyReleased() {
		if (key == CODED) {
			if (keyCode == RIGHT) {
				player.vx=0;
			} else if (keyCode == LEFT) {
				player.vx=0;
			}
			player.R.x+=player.vx;
		}

	}
}
