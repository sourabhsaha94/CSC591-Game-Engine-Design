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
	Section5ClientListener scl;
	
	public ClientWorker(ServerSocket serverSocket, Section5ClientListener scl) {
		
		this.serverSocket = serverSocket;
		this.scl = scl;
		
	}
	
	@Override
	public void run() {
		 int count=1;
	
		while(true){
		try {
			
			Socket socket = serverSocket.accept();
			
			ClientInfo c = new ClientInfo();
			c.socket = socket;
			c.id=count;
			count++;
			Section5ServerOut so = new Section5ServerOut(c, scl);
			Section5ServerIn si = new Section5ServerIn(c, scl);
			
			c.out=so;
			c.in = si;
			
			Thread t_so = new Thread(so);
			Thread t_si = new Thread(si);
			
			t_so.start();
			t_si.start();
			
			/*Message m = new Message(9090,0,0,0,0,0);
			m.sp = new LinkedList<>();
			m.sp.addAll(scl.spList);
			m.mp = new LinkedList<>();
			m.mp.addAll(scl.mpList);
			m.dz = new LinkedList<>();
			m.dz.addAll(scl.dzList);*/
			//c.out.sendMessageToClient(m);
			scl.addClient(c);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
	}



}
