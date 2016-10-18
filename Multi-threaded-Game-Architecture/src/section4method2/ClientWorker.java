package section4method2;





import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientWorker implements Runnable {

	ServerSocket serverSocket;
	ClientListener scl;
	
	public ClientWorker(ServerSocket serverSocket, ClientListener scl) {
		
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
			
			ServerOut so = new ServerOut(c, scl);
			ServerIn si = new ServerIn(c, scl);
			
			c.out=so;
			c.in = si;
			
			Thread t_so = new Thread(so);
			Thread t_si = new Thread(si);
			
			t_so.start();
			t_si.start();
			
			Message m = new Message(9090,null,0,0,0);
			m.sp = scl.spList.get(0);
			m.mp = scl.mpList.get(0);
			
			c.out.sendMessageToClient(m);
			scl.addClient(c);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		}
		
	}



}
