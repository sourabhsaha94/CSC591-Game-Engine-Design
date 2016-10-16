package assignment2;

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

	boolean world_loaded=false;
	
	public Section5ClientIn(int id,ObjectInputStream in, ConcurrentHashMap<Integer, Player> pList, Section5Client c) {
		this.in = in;
		this.pList = pList;
		this.playerID = id;
		this.c =c;
	}

	@Override
	public void run() {

		InitialMessage im;
		Message m;

		while (!Thread.interrupted()) {
			
			try {
				while(!world_loaded){
					im = (InitialMessage) in.readObject();
					System.out.println(im.s.id+" rec");
					world_loaded = true;
				}
				
				m = (Message) in.readObject();
				
				
				if((m.id!=playerID))		//update only for other players
				{
				

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
