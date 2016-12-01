/*
 * This class is responsible for instantiating a client
 *  and its input and output streams*/



import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.CopyOnWriteArrayList;

import processing.core.PApplet;

public class Client extends PApplet {

	Message eventMessage = new Message(9100);

	int direction=1;

	ClientOut sender;
	ClientIn recieve;
	Player player;
	Bullet bullet;
	CopyOnWriteArrayList<Enemy> enemyList = new CopyOnWriteArrayList<>();

	public void settings() {
		size(800, 800);
	}

	public void setup() {

		ObjectInputStream in = null;
		ObjectOutputStream out = null;

		bullet = new Bullet(1);
		bullet.R = new Rectangle(350,350,20,20);
		bullet.setBulletColor(255, 255, 255);
		bullet.setBulletVelocity(2, 6);
		bullet.collisionComponent.cSPlatform = bullet;
		
		bullet.addSpawnPoint(new SpawnPoint(1));
		bullet.s.setSpawnPoint(300, 300);
		
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

		Thread t_eventManager = new Thread(ClientEventManager.getInstance());
		t_eventManager.setDaemon(true);
		t_eventManager.start();

		Thread timeline = new Thread(ClientTimeline.getInstance());
		timeline.start();

		this.sender = new ClientOut(out); // start
		// sender
		// thread
		Thread t_sender = new Thread(sender);
		t_sender.setDaemon(true);
		t_sender.start();

		this.recieve = new ClientIn(in, this); // start
		// receiver
		// thread

		Thread t_recieve = new Thread(recieve);
		t_recieve.setDaemon(true);
		t_recieve.start();




	}

	public void draw() {

		clear();
		if(player!=null){
			
			if(bullet.ai_score<30 && bullet.player_score<30){

			textSize(32);
			text("Score", 350, 40);
			textSize(32);
			text(String.valueOf(bullet.player_score), 250, 40);
			textSize(32);
			text(String.valueOf(bullet.ai_score), 550, 40);
			
			fill(player.colorComponent.getR(), player.colorComponent.getG(), player.colorComponent.getB());
			rect(player.R.x, player.R.y, player.R.width, player.R.height);
			
			player.move();

			fill(bullet.colorComponent.getR(), bullet.colorComponent.getG(), bullet.colorComponent.getB());
			rect(bullet.R.x, bullet.R.y, bullet.R.width, bullet.R.height);
			
			bullet.move();
			
			bullet.collisionComponent.update();

			fill(this.enemyList.get(0).colorComponent.getR(), this.enemyList.get(0).colorComponent.getG(), this.enemyList.get(0).colorComponent.getB());
			rect(this.enemyList.get(0).R.x, this.enemyList.get(0).R.y, this.enemyList.get(0).R.width, this.enemyList.get(0).R.height);
			
			this.enemyList.get(0).move(bullet);

			}
			else{
				textSize(32);
				text("Score", 350, 40);
				textSize(32);
				text(String.valueOf(bullet.player_score), 250, 40);
				textSize(32);
				text(String.valueOf(bullet.ai_score), 550, 40);
				textSize(50);
				text("Game Over", 320, 340);
				fill(player.colorComponent.getR(), player.colorComponent.getG(), player.colorComponent.getB());
			}
		}
	}

	public static void main(String argv[]) {
		PApplet.main("Client");
	}

	public void keyPressed(){
		if (key == CODED) {

			if (keyCode == UP) {	//move up
				ClientEventManager.getInstance().addEvent(new HIDEvent(Timeline.getInstance().getTime(), player,1));

			} else if (keyCode == DOWN) {	//move down
				ClientEventManager.getInstance().addEvent(new HIDEvent(Timeline.getInstance().getTime(), player,-1));
			}
		}
		else if(key == 'c'){//color change
			ClientEventManager.getInstance().addEvent(new HIDEvent(Timeline.getInstance().getTime(), player,102));
		}
	}
}
