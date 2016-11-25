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

	ClientOut sender;
	ClientIn recieve;
	int distance_from_ground;
	Player player;
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

		Thread timeline = new Thread(ReplayTimeline.getInstance());
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

			fill(player.colorComponent.getR(), player.colorComponent.getG(), player.colorComponent.getB());
			rect(player.R.x, player.R.y, player.R.width, player.R.height);
			
			player.move();

			distance_from_ground = (int) (800 - player.R.getMaxY());

			player.collisionComponent.update(800,800);


			if(player.fireComponent.jump_flag){	
				player.fireComponent.fire();
			}
			for(Enemy t:enemyList){
				fill(t.colorComponent.getR(),t.colorComponent.getG(),t.colorComponent.getB());
				rect(t.R.x+=t.motionComponent.vx,t.R.y,t.R.width,t.R.height);
				t.motionComponent.update();
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
				ClientEventManager.getInstance().addEvent(new HIDEvent(Timeline.getInstance().getTime(), player,101));
			}
		}
		else if(key == 'c'){//color change
			ClientEventManager.getInstance().addEvent(new HIDEvent(Timeline.getInstance().getTime(), player,102));
		}
	}
}
