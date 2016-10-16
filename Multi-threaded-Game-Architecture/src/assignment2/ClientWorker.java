/*This class is responsible for accepting connections, creating a client reference, 
 * creating i/p o/p streams and assigning it to the client ref,
 * adding the client to the clientListener list,
 * and finally making sure that the streams are connected to the clientListener as well
 */


package assignment2;

import java.awt.Rectangle;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ClientWorker implements Runnable {

	ServerSocket serverSocket;
	Section5ClientListener scl;
	
	public ClientWorker(ServerSocket serverSocket, Section5ClientListener scl) {
		
		this.serverSocket = serverSocket;
		this.scl = scl;
		
	}
	
	@Override
	public void run() {
		
	
		while(true){
		try {
			
			Socket socket = serverSocket.accept();
			
			ClientInfo c = new ClientInfo();
			c.socket = socket;
			
			Section5ServerOut so = new Section5ServerOut(c, scl);
			Section5ServerIn si = new Section5ServerIn(c, scl);
			
			c.out=so;
			c.in = si;
			
			Thread t_so = new Thread(so);
			Thread t_si = new Thread(si);
			
			t_so.start();
			t_si.start();
			
			Random r = new Random();
			int id; 
			
			id = r.nextInt(1000)+1500;
			StaticPlatform s1 = new StaticPlatform(id);
			s1.setPlatformColor(255, 0, 0);
			s1.R = new Rectangle(50,600,200,50);
			
			InitialMessage m = new InitialMessage(s1);
			
			c.out.sendiMessage(m);
			System.out.println("sent from client");
			
			scl.addClient(c);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
	}



}
