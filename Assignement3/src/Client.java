/*
 * This class is responsible for instantiating a client
 *  and its input and output streams*/



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
import java.util.concurrent.CopyOnWriteArrayList;

import processing.core.PApplet;

public class Client extends PApplet {

	Player tempPlayer;
	Player player;

	CopyOnWriteArrayList<Player> playerList;
	CopyOnWriteArrayList<StaticPlatform> sPlatformList = new CopyOnWriteArrayList<>();	//clients copy of platforms and players
	CopyOnWriteArrayList<MovingPlatform> mPlatformList = new CopyOnWriteArrayList<>();
	
	public void settings() {
		size(800, 800);
	}

	public void setup() {

		playerList = new CopyOnWriteArrayList<>();	//init player list

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
		
		
		try {
			in = new ObjectInputStream(socket.getInputStream());
			out = new ObjectOutputStream(socket.getOutputStream());

		} catch (IOException e) {
			e.printStackTrace();
		}

	
		ClientOut sender = new ClientOut(out, playerList); // start
		// sender
		// thread
		Thread t_sender = new Thread(sender);
		t_sender.setDaemon(true);
		t_sender.start();

		ClientIn recieve = new ClientIn(in, playerList, this); // start
		// receiver
		// thread
		
		Thread t_recieve = new Thread(recieve);
		t_recieve.setDaemon(true);
		t_recieve.start();

		
		

	}

	public void draw() {
		clear();

		for(Player tempPlayer:playerList) {		//loops to display
			fill(tempPlayer.colorComponent.getR(), tempPlayer.colorComponent.getG(), tempPlayer.colorComponent.getB());
			rect(tempPlayer.R.x, tempPlayer.R.y, tempPlayer.R.width, tempPlayer.R.height);

		}
		for(StaticPlatform t:sPlatformList){		
			fill(t.colorComponent.getR(),t.colorComponent.getG(),t.colorComponent.getB());
			rect(t.R.x,t.R.y,t.R.width,t.R.height);
		}
		
		for(MovingPlatform t:mPlatformList){
			fill(t.colorComponent.getR(),t.colorComponent.getG(),t.colorComponent.getB());
			rect(t.R.x,t.R.y,t.R.width,t.R.height);
		}
	}

	public static void main(String argv[]) {
		PApplet.main("Client");
	}
	
	/*public void keyPressed(){
		if (key == CODED) {
		
			if (keyCode == RIGHT) {	//move right
				player.motionComponent.vx=1;
				
			
			
			} else if (keyCode == LEFT) {	//move left
				player.motionComponent.vx=-1;
						
			}
			else if (keyCode == UP) {	//jump
				player.jumpComponent.jump_flag=true;
			}
			
			
			player.move();
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
			player.move();
		}

	}*/
}
