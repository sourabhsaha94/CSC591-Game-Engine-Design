package section123;
/*This class handles incoming communication from client*/




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Set;

public class ServerIn implements Runnable {

	ClientListener clientListener;
	ClientInfo c;
	ObjectInputStream input;
	
	public ServerIn(ClientInfo c, ClientListener cl) throws IOException {
		this.c = c;
		this.clientListener=cl;
		Socket socket = c.socket;
		input = new ObjectInputStream(socket.getInputStream());
	}
	
	@Override
	public void run() {
		
		Message m;
		
		while(!Thread.interrupted()){
			
			
				try{
					m = (Message)input.readObject();
				}
				catch(Exception e){
					break;
				}
				//System.out.println("receiving from "+m.id+" :"+m.x);
				//clientListener.pInfo.put(c, m);
				
				if(m.x==999){
					c.id=m.id;
					clientListener.allPlayerList.add(m.id);	//add player id to main list
				}
				else
				clientListener.sendMessage(c, m);
			
		}
		
		Thread.currentThread().interrupt();
		clientListener.deleteClient(c);
	}

}
