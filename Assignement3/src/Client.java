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

	Player tempPlayer;
	Player player;

	Message eventMessage = new Message(9100);

	ClientOut sender;
	ClientIn recieve;

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

		/*ClientEventManager clientEventManager = new ClientEventManager();
		Thread t_em = new Thread(clientEventManager);
		t_em.setDaemon(true);
		t_em.start();*/


		this.sender = new ClientOut(out, playerList); // start
		// sender
		// thread
		Thread t_sender = new Thread(sender);
		t_sender.setDaemon(true);
		t_sender.start();

		this.recieve = new ClientIn(in, playerList, this); // start
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

	public void keyPressed(){
		if (key == CODED) {

			if (keyCode == RIGHT) {	//move right
				eventMessage.putValues(9100, 1, 0);
				eventMessage.messagePriority=MessagePriority.EVENT;
				this.sender.sendMessage(eventMessage);

			} else if (keyCode == LEFT) {	//move left
				eventMessage.putValues(9100, -1, 0);
				eventMessage.messagePriority=MessagePriority.EVENT;
				this.sender.sendMessage(eventMessage);		
			}
			else if (keyCode == UP) {	//jump
				//player.jumpComponent.jump_flag=true;
				eventMessage.putValues(9100, 101, 0);
				eventMessage.messagePriority=MessagePriority.EVENT;
				this.sender.sendMessage(eventMessage);
			}
		}
		else if(key == 's' ||key =='S'){	//reset
			eventMessage.putValues(9100, 666, 0);
			eventMessage.messagePriority=MessagePriority.EVENT;
			this.sender.sendMessage(eventMessage);
		}
		else if(key == 'f' ||key =='F'){	//reset
			eventMessage.putValues(9100, 777, 0);
			eventMessage.messagePriority=MessagePriority.EVENT;
			this.sender.sendMessage(eventMessage);
		}
		else if(key == 'r' || key == 'R'){
			ClientReplayManager.getInstance().start_replay=true;
			ClientReplayManager.getInstance().stop_replay=false;
		}
		else if(key == 'p' || key == 'P'){
			ClientReplayManager.getInstance().start_replay=false;
			ClientReplayManager.getInstance().stop_replay=true;
		}
	}
	/*
	public void keyReleased() {
		if (key == CODED) {
			if (keyCode == RIGHT) {
				eventMessage.putValues(9100, 0, 0);
				eventMessage.messagePriority=MessagePriority.EVENT;
				this.sender.sendMessage(eventMessage);
				System.out.println("Event sent");

			} else if (keyCode == LEFT) {
				eventMessage.putValues(9100, 0, 0);
				eventMessage.messagePriority=MessagePriority.EVENT;
				this.sender.sendMessage(eventMessage);
			}
		}

	}*/
}
