/*This class is responsible for accepting connections, creating a client reference, 
 * creating i/p o/p streams and assigning it to the client ref,
 * adding the client to the clientListener list,
 * and finally making sure that the streams are connected to the clientListener as well
 */

package assignment2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientWorker implements Runnable {

	ServerSocket serverSocket;
	Section5ClientListener scl;

	public ClientWorker(ServerSocket serverSocket, Section5ClientListener scl) {

		this.serverSocket = serverSocket;
		this.scl = scl;

	}

	@Override
	public void run() {

		while (true) {
			try {

				Socket socket = serverSocket.accept();

				ClientInfo c = new ClientInfo();
				c.socket = socket;

				Section5ServerOut so = new Section5ServerOut(c, scl);
				Section5ServerIn si = new Section5ServerIn(c, scl);

				c.out = so;
				c.in = si;

				Thread t_so = new Thread(so);
				Thread t_si = new Thread(si);

				t_so.start();
				t_si.start();

				for(StaticPlatform s:scl.sPlatformList){
					Message m = new Message(s.id, s.R.x,
							s.R.y, s.colorComponent.getR(),
							s.colorComponent.getG(), s.colorComponent.getB());
					m.width = s.R.width;
					m.height = s.R.height;
					System.out.println("sent sp");
					c.out.sendMessage(m);

				}
				for(MovingPlatform s:scl.mPlatformList){
					Message m = new Message(s.id, s.R.x,
							s.R.y, s.colorComponent.getR(),
							s.colorComponent.getG(), s.colorComponent.getB());
					m.width = s.R.width;
					m.height = s.R.height;
					m.vx=s.motionComponent.getVx();
					System.out.println("sent mp");
					c.out.sendMessage(m);

				}
				for(DeathZone s:scl.dzList){
					Message m = new Message(s.id, s.R.x,
							s.R.y, 0,
							0, 0);
					m.width = s.R.width;
					m.height = s.R.height;
					System.out.println("sent dz");
					c.out.sendMessage(m);
				}
				
				scl.addClient(c);

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
