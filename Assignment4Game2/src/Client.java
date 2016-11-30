/*
 * This class is responsible for instantiating a client
 *  and its input and output streams*/



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
	int down_speed=1;
	Player player;
	CopyOnWriteArrayList<Bullet> bulletList = new CopyOnWriteArrayList<>();
	CopyOnWriteArrayList<Enemy> enemyList = new CopyOnWriteArrayList<>();

	public void settings() {
		size(800, 800);
	}

	public void setup() {

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

			textSize(32);
			text("Score", 10, 30);
			textSize(32);
			text(String.valueOf(player.score), 10, 60);
			
			textSize(32);
			text("Bullets", 700, 30);
			textSize(32);
			text(String.valueOf(player.num_bullets), 700, 60);
			
			
			fill(player.colorComponent.getR(), player.colorComponent.getG(), player.colorComponent.getB());
			rect(player.R.x, player.R.y, player.R.width, player.R.height);
			
			player.move();

			player.collisionComponent.update();

			

			if(player.fireComponent.fire_flag){
				fill(player.bullet.colorComponent.getR(), player.bullet.colorComponent.getG(), player.bullet.colorComponent.getB());
				rect(player.bullet.R.x, player.bullet.R.y, player.bullet.R.width, player.bullet.R.height);
				player.fireComponent.fire(player.bullet);
			}
			
			if(ClientTimeline.getInstance().rightTimeMillis() && !player.collided){
				ScriptManager.loadScript("platform.js");
				for(Enemy t:player.collisionComponent.mPlatformList){
					ScriptManager.bindArgument("enemy", t.R);
					ScriptManager.executeScript();
				}
			}
			else{
				for(Enemy t:player.collisionComponent.mPlatformList){
					t.motionComponent.vy=0;
				}
			}
			
			if(player.collisionComponent.mPlatformList.size()==0){
				fill(0, 255, 0);
				System.out.println("Exit");
				textSize(32);
				text("YOU WON!!", 350, 400);
				

			}
			
			if(player.collided){
				fill(0, 255, 0);
				System.out.println("Exit");
				textSize(32);
				text("GAME OVER", 350, 60);
				
			}
			else{
				fill(0, 255, 0);
				textSize(20);
				text("HIT:+5 MISS:-1", 300, 30);
				fill(0, 255, 0);
				textSize(20);
				text("MOVE:RIGHT/LEFT ARROW FIRE:UP ARROW", 200, 60);
				
			}
			
			if(!player.collided)
			for(Enemy t:player.collisionComponent.mPlatformList){
				fill(t.colorComponent.getR(),t.colorComponent.getG(),t.colorComponent.getB());
				rect(t.R.x+=t.motionComponent.vx,t.R.y,t.R.width,t.R.height);
				if(direction==1 && t.R.getMaxX()>=800){
					direction = -1;
				}
				else if(direction==-1 && t.R.x<=0){
					direction = 1;
				}
				t.motionComponent.update(direction);
			}
		}
	}

	public static void main(String argv[]) {
		PApplet.main("Client");
	}

	public void keyPressed(){
		if (key == CODED) {

			if (keyCode == RIGHT) {	//move right
				ClientEventManager.getInstance().addEvent(new HIDEvent(Timeline.getInstance().getTime(), player,1));

			} else if (keyCode == LEFT) {	//move left
				ClientEventManager.getInstance().addEvent(new HIDEvent(Timeline.getInstance().getTime(), player,-1));
			}
			else if (keyCode == UP) {	//jump
				if(!player.fireComponent.fire_flag)
					ClientEventManager.getInstance().addEvent(new HIDEvent(Timeline.getInstance().getTime(), player,101));
			}
		}
		else if(key == 'c'){//color change
			ClientEventManager.getInstance().addEvent(new HIDEvent(Timeline.getInstance().getTime(), player,102));
		}
	}
}
