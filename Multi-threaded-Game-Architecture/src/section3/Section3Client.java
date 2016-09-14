package section3;

import assignment1.Thing;
import java.io.*;
import java.net.*;

import assignment1.Thing;

public class Section3Client {

	public static void main(String argv[]) throws Exception {
        
        Socket clientSocket = new Socket("localhost", 9000);
        ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
        
        Thing t = (Thing)ois.readObject();
        System.out.println(t.name);
        
        t.name ="b";
        oos.writeObject(t);
        
        
        clientSocket.close();
        
        }

}
