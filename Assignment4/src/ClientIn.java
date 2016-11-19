

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
					c.playerList.clear();
					c.mPlatformList.clear();
					c.playerList.addAll(m.pList);
					c.mPlatformList.addAll(m.mpList);
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
