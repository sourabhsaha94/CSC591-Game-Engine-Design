package assignment2;

import java.awt.Rectangle;
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;

public class Section5ClientIn implements Runnable {

	ObjectInputStream in;
	Player player;
	ConcurrentHashMap<Integer, Player> pList;
	LinkedList<StaticPlatform> sPlatformList;
	LinkedList<MovingPlatform> mPlatformList;
	int playerID=0;

	public Section5ClientIn(int id,ObjectInputStream in, ConcurrentHashMap<Integer, Player> pList, LinkedList<StaticPlatform> sPlatformList, LinkedList<MovingPlatform> mPlatformList) {
		this.in = in;
		this.pList = pList;
		this.playerID = id;
		this.sPlatformList = sPlatformList;
		this.mPlatformList = mPlatformList;
	}

	@Override
	public void run() {

		Message m;

		while (!Thread.interrupted()) {
			
			try {
				m = (Message) in.readObject();
				
				/*if(m.id==9999){
					this.sPlatformList.addAll(m.splatformInfo.values());
					this.mPlatformList.addAll(m.mplatformInfo.values());
					System.out.println("received from server");
					
				}*/

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
						player = new Player(m.id);	//create a new player
						player.R = new Rectangle(m.x,m.y,50,50);
						player.setPlayerVelocity(0, 0);
						player.setPlayerColor(m.r, m.g, m.b);
						pList.put(m.id, player);
						System.out.println("received from server");
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
