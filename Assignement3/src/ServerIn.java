/*This class handles incoming communication from client*/




import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Random;
import java.util.Set;

public class ServerIn implements Runnable {

	ClientListener clientListener;
	ClientInfo c;
	ObjectInputStream input;
	Random random;

	public ServerIn(ClientInfo c, ClientListener cl) throws IOException {
		this.c = c;
		this.clientListener=cl;
		Socket socket = c.socket;
		input = new ObjectInputStream(socket.getInputStream());
		random = new Random();
	}

	@Override
	public void run() {

		Message m;

		while(!Thread.interrupted()){

				try{
					m = (Message)input.readObject();
				}
				catch(Exception e){
					break;
				}

				//clientListener.pInfo.put(c, m);

				switch(m.id){
				case 9090:
					Player p = new Player(c.id);
					p.R = new Rectangle(random.nextInt(800),random.nextInt(100),50,50);
					p.setPlayerColor(random.nextInt(255), random.nextInt(255), random.nextInt(255));
					SpawnPoint s = new SpawnPoint(1);
					s.setSpawnPoint(500, 10);
					p.addSpawnPoint(s);
					p.collisionComponent.addPlatforms(clientListener.spList, clientListener.mpList);
					p.collisionComponent.dzones.addAll(clientListener.dzList);
					EventManager.getInstance().registerEvent(p);
					clientListener.allPlayerList.add(p);
					m.pList.clear();
					m.spList.clear();
					m.mpList.clear();
					m.pList.addAll(clientListener.allPlayerList);
					m.spList.addAll(clientListener.spList);
					m.mpList.addAll(clientListener.mpList);
					clientListener.sendMessage(c, m);
					break;
				case 9000:
					m.pList.clear();
					m.mpList.clear();
					m.pList.addAll(clientListener.allPlayerList);
					m.mpList.addAll(clientListener.mpList);
					clientListener.sendMessage(c, m);
					break;
				case 9100:
					EventManager.getInstance().addEvent(new HIDEvent(Timeline.getInstance().getTime(), clientListener.allPlayerList.get(c.id-1),m.x));
					//System.out.println("HID: "+Timeline.getInstance().getTime());
					break;
				default:
					//do nothing
					break;
				}
				/*
				if(m.x==999){
					c.id=m.id;
					clientListener.allPlayerList.add(m.id);	//add player id to main list
				}
				else
				clientListener.sendMessage(c, m);*/
		}

		Thread.currentThread().interrupt();
		clientListener.deleteClient(c);
	}

}
