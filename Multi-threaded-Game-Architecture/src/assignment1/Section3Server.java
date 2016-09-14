package assignment1;

import java.io.*;
import java.net.*;

public class Section3Server {

	public static void main(String args[]) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9000);

        while(true) {
            Socket connectionSocket = serverSocket.accept();
            
            ObjectOutputStream oos =  new ObjectOutputStream(connectionSocket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(connectionSocket.getInputStream());
            
            oos.writeObject(new Thing("a"));
            Thing t = (Thing)ois.readObject();
            System.out.println(t.name);
            
        }
    }

}
