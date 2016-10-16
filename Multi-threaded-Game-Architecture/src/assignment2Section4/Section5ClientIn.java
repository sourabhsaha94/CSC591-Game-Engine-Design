package assignment2Section4;

import java.awt.Rectangle;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class Section5ClientIn implements Runnable {

	ObjectInputStream in;
	Player player;
	ConcurrentHashMap<Integer, Player> pList;
	int playerID=0;
	
	Section5Client c;

	public Section5ClientIn(int id,ObjectInputStream in, ConcurrentHashMap<Integer, Player> pList, Section5Client c1) {
		this.in = in;
		this.pList = pList;
		this.playerID = id;
		this.c=c1;
	}

	@Override
	public void run() {

		Message m;

		while (!Thread.interrupted()) {
			
			try {
				m = (Message) in.readObject();
				
				
				if((m.id!=playerID))		//update only for other players
				{
					if(m.id==9090){
						System.out.println(m.sp.size());
						/*c.sPlatformList.addAll(m.sp);
						c.mPlatformList.addAll(m.mp);
						c.player.collisionComponent.dzones.addAll(m.dz);*/
					}
				

					if(pList.containsKey(m.id)){	//check if player exists
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
