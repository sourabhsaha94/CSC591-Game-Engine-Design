package assignment2;

import java.awt.Rectangle;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class Section5ClientIn implements Runnable {

	ObjectInputStream in;
	Player player;
	Section5Client c;
	ConcurrentHashMap<Integer, Player> pList;
	int playerID=0;

	public Section5ClientIn(int id,ObjectInputStream in, ConcurrentHashMap<Integer, Player> pList, Section5Client c) {
		this.in = in;
		this.pList = pList;
		this.playerID = id;
		this.c =c;
	}

	@Override
	public void run() {

		Message m;

		while (!Thread.interrupted()) {
			
			try {
				m = (Message) in.readObject();
				
				
				if((m.id!=playerID))		//update only for other players
				{
					if(m.id>=1000 && m.id<=2000){
						System.out.println("rec sp");
						StaticPlatform t = new StaticPlatform(m.id);
						t.R.x = m.x;
						t.R.y = m.y;
						t.R.width = m.width;
						t.R.height = m.height;
						t.setPlatformColor(m.r, m.g, m.b);
						c.player.collisionComponent.addSPlatform(t);
						
					}
					else if(m.id>=3000 && m.id<=4000){
						System.out.println("rec mp");
						MovingPlatform t = new MovingPlatform(m.id);
						t.R.x = m.x;
						t.R.y = m.y;
						t.R.width = m.width;
						t.R.height = m.height;
						t.setPlatformColor(m.r, m.g, m.b);
						t.motionComponent.vx=m.vx;
						c.player.collisionComponent.addMPlatform(t);
					}
					
					else if(m.id>=5000 && m.id<=6000){
						System.out.println("rec dz");
						DeathZone t = new DeathZone(m.id);
						t.R.x = m.x;
						t.R.y = m.y;
						t.R.width = m.width;
						t.R.height = m.height;
						c.player.collisionComponent.addDeathZone(t);
					}
				

					else if(pList.containsKey(m.id)){	//check if player exists
						player = pList.get(m.id);
						player.R.x=m.x;
						player.R.y=m.y;
						pList.put(m.id, player);
					}

					else{
					
						player = new Player(m.id);	//create a new player
						player.R = new Rectangle(m.x,m.y,50,50);
						player.setPlayerVelocity(0, 0);
						player.setPlayerColor(m.r, m.g, m.b);
						pList.put(m.id, player);
					}
				}

				//System.out.println(m.id+": "+m.x);
			} catch (Exception e) {
				break;
			}
			// System.out.println(newPos);
		}

	}
}
