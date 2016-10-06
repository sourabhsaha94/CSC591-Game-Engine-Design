package assignment2;

import java.awt.Rectangle;
import java.io.ObjectInputStream;
import java.util.concurrent.ConcurrentHashMap;

public class Section5ClientIn implements Runnable {

	ObjectInputStream in;
	String message;
	Thing player;
	ConcurrentHashMap<String, Thing> pList;

	public Section5ClientIn(ObjectInputStream in, ConcurrentHashMap<String, Thing> pList) {
		this.in = in;
		this.pList = pList;
	}

	@Override
	public void run() {

		Message m;

		while (!Thread.interrupted()) {

			try {
				m = (Message) in.readObject();
				
				if(pList.containsKey(m.socketAddress)){
					player = pList.get(m.socketAddress);
					player.R.x=m.x;
					player.R.y=m.y;
					pList.put(m.socketAddress, player);
				}
				else{
					player = new Thing(m.socketAddress,new Rectangle(m.x,m.y,100,100),0,0,0,0,255);
					pList.put(m.socketAddress, player);
				}
				
			} catch (Exception e) {
				break;
			}
			// System.out.println(newPos);
		}

	}
}
