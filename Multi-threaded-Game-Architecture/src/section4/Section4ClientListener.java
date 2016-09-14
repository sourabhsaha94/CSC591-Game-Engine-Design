package section4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Section4ClientListener implements Runnable {

	ServerSocket server;
	boolean running;
	int count=0;
	
	ConcurrentHashMap<Integer,ClientWorker> clientList;
	
	public Section4ClientListener(ServerSocket server,boolean running) {
		this.server = server;
		this.running = running;
		
		clientList = new ConcurrentHashMap<Integer,ClientWorker>();
	}
	
	@Override
	public void run() {
		
		while(running){
			try {
				Socket socket = server.accept();
				
				ClientWorker cw = new ClientWorker(socket,count);
				
				clientList.put(count,cw);
				
				Thread t = new Thread(cw);
				t.start();
				
				count++;
			} catch (IOException e) {
			
				e.printStackTrace();
			}
			
			
		}
		
		
	}

}
