package assignment2;

import java.awt.Rectangle;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Section5ClientIn implements Runnable {

	ObjectInputStream in;
	Thing player;
	ConcurrentHashMap<String, Thing> pList;
	Socket s;
	
	public Section5ClientIn(Socket s,ObjectInputStream in, ConcurrentHashMap<String, Thing> pList) {
		this.in = in;
		this.pList = pList;
		this.s = s;
	}

	@Override
	public void run() {

		Message m;

		while (!Thread.interrupted()) {

			try {
				m = (Message) in.readObject();
				
				if(!m.socketAddress.equalsIgnoreCase(s.getLocalSocketAddress().toString()))
				{
					if(pList.containsKey(m.socketAddress)){
						player = pList.get(m.socketAddress);
						player.R.x=m.x;
						player.R.y=m.y;
						pList.put(m.socketAddress, player);
					}
					else{
						player = new Thing(m.socketAddress,new Rectangle(m.x,m.y,100,100),0,0,m.r,m.g,m.b);
						pList.put(m.socketAddress, player);
					}
				}
				
				//System.out.println(m.socketAddress+": "+m.x);
			} catch (Exception e) {
				break;
			}
			// System.out.println(newPos);
		}

	}
}
