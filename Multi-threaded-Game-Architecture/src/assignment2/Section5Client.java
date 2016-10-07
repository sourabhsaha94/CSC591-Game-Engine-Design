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
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import processing.core.PApplet;

public class Section5Client extends PApplet {

	Thing player, tempPlayer;
	ConcurrentHashMap<Integer, Thing> playerList;

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

	}

	public void draw() {
		clear();

		Collection<Thing> t = playerList.values();
		Iterator<Thing> list = t.iterator();

		while (list.hasNext()) {
			tempPlayer = list.next();
			fill(tempPlayer.r, tempPlayer.g, tempPlayer.b);
			rect(tempPlayer.R.x, tempPlayer.R.y, tempPlayer.R.width, tempPlayer.R.height);
			//System.out.println(tempPlayer.id+":"+tempPlayer.r+" "+tempPlayer.g+" "+tempPlayer.b);
		}
		// System.out.println("Current size of playerList "+playerList.size());
	}

	public static void main(String argv[]) {

		// Section5Client c = new Section5Client();
		PApplet.main("assignment2.Section5Client");
	}

	public void keyPressed() {
		if (key == CODED) {

			if (keyCode == RIGHT) { // move right
				player.vx = 1;

			} else if (keyCode == LEFT) { // move left
				player.vx = -1;

			}
			if (keyCode == UP) { // move up
					player.vy = -1;

				}
			 else if (keyCode == DOWN) { // move down
					player.vy = 1;

				}
			player.R.x += player.vx;
			player.R.y += player.vy;
		}
	}

	public void keyReleased() {
		if (key == CODED) {
			if (keyCode == RIGHT) { // move right
				player.vx = 0;

			} else if (keyCode == LEFT) { // move left
				player.vx = 0;

			}
			if (keyCode == UP) { // move up
					player.vy = 0;

				}
			 else if (keyCode == DOWN) { // move down
					player.vy = 0;

				}
			player.R.x += player.vx;
		}

	}

}
