package section3;

import assignment1.Thing;
import java.io.*;
import java.net.*;

import assignment1.Thing;

public class Section3Server {

	private static ServerSocket serverSocket;

	public static void main(String args[]) throws Exception {
        serverSocket = new ServerSocket(9000);

        boolean stopped =true;
        
        while(stopped) {
            Socket connectionSocket = serverSocket.accept();
            
            ObjectOutputStream oos =  new ObjectOutputStream(connectionSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(connectionSocket.getInputStream());
            
            oos.writeObject(new Thing("a"));
            Thing t = (Thing)ois.readObject();
            System.out.println(t.name);
            
        }
        serverSocket.close();
    }

}
