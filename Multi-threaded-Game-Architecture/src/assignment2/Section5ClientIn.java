package assignment2;

import java.awt.Rectangle;
import java.io.ObjectInputStream;
import java.util.concurrent.ConcurrentHashMap;

public class Section5ClientIn implements Runnable {

	ObjectInputStream in;
	Thing player;
	ConcurrentHashMap<Integer, Thing> pList;
	int playerID=0;
	
	public Section5ClientIn(int id,ObjectInputStream in, ConcurrentHashMap<Integer, Thing> pList) {
		this.in = in;
		this.pList = pList;
		this.playerID = id;
	}

	@Override
	public void run() {

		Message m;

		while (!Thread.interrupted()) {

			try {
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
						System.out.println(true);
						player = new Player(m.id,new Rectangle(m.x,m.y,50,50),0,0,m.r,m.g,m.b);	//create a new player
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
