package section4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Section4ClientListener implements Runnable {

	ServerSocket server;
	boolean running;
	int count=0;
	
	public Section4ClientListener(ServerSocket server,boolean running) {
		this.server = server;
		this.running = running;
	}
	
	@Override
	public void run() {
		
		while(running){
			try {
				Socket socket = server.accept();
				Thread t = new Thread(new ClientWorker(socket,count++));
				t.start();
				
			} catch (IOException e) {
			
				e.printStackTrace();
			}
			
			
		}
		
		
	}

}
