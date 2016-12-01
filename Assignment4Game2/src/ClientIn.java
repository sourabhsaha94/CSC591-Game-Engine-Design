

import java.io.ObjectInputStream;

public class ClientIn implements Runnable {

	ObjectInputStream in;
	int playerID=0;
	Client c;

	public ClientIn(ObjectInputStream in, Client c1) {
		this.in = in;
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
					c.enemyList.clear();
					c.player = m.player;
					ClientEventManager.getInstance().registerEvent(c.player);
					c.enemyList.addAll(m.mpList);
					c.bullet.collisionComponent.cSPlatform = c.bullet;
					c.bullet.collisionComponent.player = m.player;
					c.bullet.collisionComponent.mPlatformList.add(m.mpList.get(0));
					c.bullet.p = m.player;
					ClientEventManager.getInstance().registerEvent(c.bullet);
					System.out.println("got 9090");
					break;
				case 9000:
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
