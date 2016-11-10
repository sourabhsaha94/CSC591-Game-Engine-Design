

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
		String msg;

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
						if(!ClientReplayManager.getInstance().playing){
							c.playerList.clear();
							c.playerList.addAll(m.pList);
							c.mPlatformList.clear();
							c.mPlatformList.addAll(m.mpList);
						}else{

							if(ReplayTimeline.getInstance().rightTime())
							{
								if((msg = ClientReplayManager.getInstance().getLine())!=null){
									m = ClientReplayManager.getInstance().playReplay(m,msg,c);
									c.playerList.clear();
									c.mPlatformList.clear();
									c.playerList.addAll(m.pList);
									c.mPlatformList.addAll(m.mpList);
								}
								else{
									ClientEventManager.getInstance().addEvent(new REPLAYEvent(System.nanoTime(), EventType.PLAYBACK));
								}
							}
						}
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
				
			} catch (Exception e) {
				break;
			}
			
		}

	}
}
