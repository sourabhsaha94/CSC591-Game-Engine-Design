package section4;


import java.io.*;
import java.net.*;
import java.util.Scanner;


public class Section4Client {

	public static void main(String argv[]) throws Exception {
        
		int id=0;
		
		Scanner input = new Scanner(System.in);
        Socket clientSocket = new Socket("localhost", 9000);
        ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());        
        
        id = (int)ois.readObject();
        
        String s = "Client id is "+id;
        System.out.println(s);
        
        while(!s.equalsIgnoreCase("bye")){
        	     	
        	s = input.nextLine();
        	oos.writeObject(s);
        	s = (String)ois.readObject();
        	System.out.println(s + "recieved from server");
        }
        
        clientSocket.close();
        
        }

}
