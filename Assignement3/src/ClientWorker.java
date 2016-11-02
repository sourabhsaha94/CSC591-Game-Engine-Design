/*This class is responsible for accepting connections, creating a client reference, 
 * creating i/p o/p streams and assigning it to the client ref,
 * adding the client to the clientListener list,
 * and finally making sure that the streams are connected to the clientListener as well
 */




import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class ClientWorker implements Runnable {

	ServerSocket serverSocket;
	ClientListener scl;
	int count=0;
	
	public ClientWorker(ServerSocket serverSocket, ClientListener scl) {
		
		this.serverSocket = serverSocket;
		this.scl = scl;
		
	}
	
	@Override
	public void run() {
		
	
		while(true){
		try {
			
			Socket socket = serverSocket.accept();
			this.count++;
			ClientInfo c = new ClientInfo();
			c.socket = socket;
			c.id = this.count;
			ServerOut so = new ServerOut(c, scl);
			ServerIn si = new ServerIn(c, scl);
			
			c.out=so;
			c.in = si;
			
			Thread t_so = new Thread(so);
			Thread t_si = new Thread(si);
			
			t_so.start();
			t_si.start();
			
			scl.addClient(c);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
	}



}
