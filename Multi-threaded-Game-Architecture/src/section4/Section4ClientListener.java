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
				Socket socket = server.accept();	//this part waits for client to connect
				
				count++;
				System.out.println(count + " client(s) connected");
				ClientWorker cw = new ClientWorker(socket,count);	//ClientWorker handles synchronous communication
				Thread t = new Thread(cw);
				t.start();
				s.clientList.put(count, cw);	//this is list of clients connected
				
			} catch (IOException e) {
			
				e.printStackTrace();
			}
			
			
		}
		
		
	}

}
