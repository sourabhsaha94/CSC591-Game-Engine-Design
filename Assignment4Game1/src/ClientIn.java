

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
		String msg;

		while (!Thread.interrupted()) {

			try {
				m = (Message) in.readObject();

				switch(m.id){
				case 9090:
					c.sPlatformList.clear();
					c.mPlatformList.clear();
					c.player = m.player;
					ClientEventManager.getInstance().registerEvent(c.player);
					c.sPlatformList.addAll(m.spList);
					c.mPlatformList.addAll(m.mpList);
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
