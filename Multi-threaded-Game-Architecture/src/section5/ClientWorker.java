/*This class is responsible for accepting connections, creating a client reference, 
 * creating i/p o/p streams and assigning it to the client ref,
 * adding the client to the clientListener list,
 * and finally making sure that the streams are connected to the clientListener as well
 */


package section5;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientWorker implements Runnable {

	ServerSocket serverSocket;
	Section5ClientListener scl;
	
	public ClientWorker(ServerSocket serverSocket, Section5ClientListener scl) {
		this.serverSocket = serverSocket;
		this.scl = scl;
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run() {
		
		int count=0;
		
		while(true){
		try {
			
			Socket socket = serverSocket.accept();
			
			ClientInfo c = new ClientInfo();
			c.socket = socket;
			
			c.id=count++;
			
			Section5ServerIn si = new Section5ServerIn(c, scl);
			Section5ServerOut so = new Section5ServerOut(c, scl);
			c.in = si;
			c.out=so;
			
			Thread t_si = new Thread(si);
			Thread t_so = new Thread(so);
			
			t_si.start();
			t_so.start();
			
			scl.addClient(c);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
	}



}
