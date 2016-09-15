package section4;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class Section4ClientListener implements Runnable {

	ServerSocket server;
	boolean running;
	int count=0;
	Section4Server s;
	
	
	public Section4ClientListener(Section4Server s,ServerSocket server,boolean running) {
		this.server = server;
		this.running = running;
		this.s = s;
	}
	
	@Override
	public void run() {
		
		System.out.println(count);
		
		while(running){
			try {
				Socket socket = server.accept();
				s.clientList.put(count, socket);
				count++;
				
			} catch (IOException e) {
			
				e.printStackTrace();
			}
			
			
		}
		
		
	}

}
