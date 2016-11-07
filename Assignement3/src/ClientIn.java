

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientIn implements Runnable {

	ObjectInputStream in;
	Player player;
	CopyOnWriteArrayList<Player> pList;
	int playerID=0;
	Client c;

	public ClientIn(ObjectInputStream in, CopyOnWriteArrayList<Player> pList, Client c1) {
		this.in = in;
		this.pList = pList;
		this.c=c1;
	}

	@Override
	public void run() {

		Message m;

		while (!Thread.interrupted()) {
			
				try {
					m = (Message) in.readObject();

					switch(m.id){
					case 9090:
						c.playerList.clear();
						c.sPlatformList.clear();
						c.mPlatformList.clear();
						c.playerList.addAll(m.pList);
						c.sPlatformList.addAll(m.spList);
						c.mPlatformList.addAll(m.mpList);
						System.out.println("got 9090");
						break;
					case 9000:
						if(!ClientReplayManager.getInstance().start_replay && ClientReplayManager.getInstance().stop_replay)
						{
							if(ClientReplayManager.getInstance().running){
								ClientReplayManager.getInstance().running=false;
								ClientReplayManager.getInstance().bufferedWriter.close();
							}
							c.playerList.clear();
							c.playerList.addAll(m.pList);
							c.mPlatformList.clear();
							c.mPlatformList.addAll(m.mpList);
						}
						else{
							ClientReplayManager.getInstance().running=true;
							c.playerList.clear();
							c.playerList.addAll(m.pList);
							c.mPlatformList.clear();
							c.mPlatformList.addAll(m.mpList);
							ClientReplayManager.getInstance().bufferedWriter.write(m.toString());
							
						}
						break;
					default:
						//do nothing
						break;
					}

					/*if((m.id!=playerID))		//update only for other players
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
				}*/

					//System.out.println(m.id+": "+m.x);
				} catch (Exception e) {
					break;
				}
			// System.out.println(newPos);
		}

	}
}
