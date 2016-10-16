/*This class handles incoming communication from client*/




import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Set;

public class Section5ServerIn implements Runnable {

	Section5ClientListener clientListener;
	ClientInfo c;
	ObjectInputStream input;
	
	public Section5ServerIn(ClientInfo c, Section5ClientListener cl) throws IOException {
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
					m.id=9090;
					m.sp = new LinkedList<>();
					m.sp.addAll(clientListener.spList);
					m.mp = new LinkedList<>();
					m.mp.addAll(clientListener.mpList);
					m.dz = new LinkedList<>();
					m.dz.addAll(clientListener.dzList);
					clientListener.sendMessage(c, m);
				}
				if(clientListener.allPlayerList.size()<2){
					m.id = 0;
					m.mp.clear();
					m.mp.addAll(clientListener.mpList);
					clientListener.sendMessage(c, m);
				}
				else{
					m.mp.clear();
					m.mp.addAll(clientListener.mpList);
					clientListener.sendMessage(c, m);
				}
			
		}
		
		Thread.currentThread().interrupt();
		clientListener.deleteClient(c);
	}

}
