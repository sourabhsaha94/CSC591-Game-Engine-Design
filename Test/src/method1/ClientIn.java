package method1;

import java.awt.Rectangle;
import java.io.ObjectInputStream;
import java.util.concurrent.ConcurrentHashMap;

public class ClientIn implements Runnable {

	ObjectInputStream in;
	Player player;
	ConcurrentHashMap<Integer, Player> pList;
	int playerID = 0;

	Client c;
	boolean flag = true;

	public ClientIn(int id, ObjectInputStream in, ConcurrentHashMap<Integer, Player> pList, Client c1) {
		this.in = in;
		this.pList = pList;
		this.playerID = id;
		this.c = c1;
	}

	@Override
	public void run() {

		Message m;
		this.c.startTime = System.currentTimeMillis();
		while (!Thread.interrupted()) {

			try {
				m = (Message) in.readObject();

				if (m.id == 9090) {				//get world init message
					c.sPlatformList.add(m.sp);
					c.mPlatformList.add(m.mp);
				}

				else if (pList.containsKey(m.id)) { // check if player exists
					player = pList.get(m.id);
					player.R.x = m.x;
					player.R.y = m.y;
					pList.put(m.id, player);
					if (c.sPlatformList.size() < 500 || c.mPlatformList.size() < 500) {
						c.sPlatformList.add(m.sp);
						c.mPlatformList.add(m.mp);
					} else {
						if (flag) {
							this.c.endTime = System.currentTimeMillis();
							System.out.println(this.c.endTime - this.c.startTime);
							flag = false;
						}
					}
				}

				else {

					player = new Player(m.id); // create a new player
					player.R = new Rectangle(m.x, m.y, 50, 50);
					player.setPlayerVelocity(0, 0);
					player.setPlayerColor(m.r, m.g, m.b);
					pList.put(m.id, player);
				}

				// System.out.println(m.id+": "+m.x);
			} catch (Exception e) {
				e.printStackTrace();
			}
			// System.out.println(newPos);
		}

	}
}
