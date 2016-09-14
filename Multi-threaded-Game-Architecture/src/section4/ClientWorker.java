package section4;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientWorker implements Runnable {

	int id=0;
	Socket client;
	ObjectInputStream ois;
	ObjectOutputStream oos;
	
	ClientWorker(Socket socket,int count) throws IOException{
		this.id = count;
		this.client=socket;
		oos = new ObjectOutputStream(client.getOutputStream());
		ois = new ObjectInputStream(client.getInputStream());
	}
	
	@Override
	public void run() {
		
		try {
			String s="";
			
			oos.writeObject(id);
			
			while(!s.equals("bye")){
				s = (String)ois.readObject();
				System.out.println(s+" from client "+id);
			}
			System.out.println("done with client "+id);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}
