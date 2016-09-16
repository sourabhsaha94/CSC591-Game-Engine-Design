package section3;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;

public class Section3Server implements Runnable{

	
	Socket socket = null;
	int id =0; 
	
	public Section3Server(Socket socket,int count){
		this.socket = socket;
		this.id = count;
	}
	
	private static ServerSocket serverSocket;

	public static void main(String args[]) throws Exception {
        serverSocket = new ServerSocket(9000);
        
        
        System.out.println("Server started.. Waiting for connections");
        int count=0;
        boolean stopped =false;
        
        while(!stopped) {
            Socket connectionSocket = serverSocket.accept();	//This part is synchronous and blocking
            
           
            Thread t = new Thread(new Section3Server(connectionSocket,count++));
            System.out.println(count+" client(s) connected");
            
            t.start();	//this is to handle synchronous communication from clients
            
           
        }
        serverSocket.close();
    }

	@Override
	public void run() {
		
		try {
			String s="";
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
			
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
